package com.robby.dicoding_movie_project_2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.robby.dicoding_movie_project_2.DetailActivity;
import com.robby.dicoding_movie_project_2.R;
import com.robby.dicoding_movie_project_2.adapter.MovieAdapter;
import com.robby.dicoding_movie_project_2.entity.Movie;
import com.robby.dicoding_movie_project_2.util.MovieEnum;
import com.robby.dicoding_movie_project_2.util.TmdbUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Robby Tan
 */

public class SearchFragment extends Fragment implements MovieAdapter.MovieDataListener {

    @BindView(R.id.et_search)
    EditText txtSearch;
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.pb_movies)
    ProgressBar pbMovies;

    private MovieAdapter movieAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                layoutManager.getOrientation());
        rvMovies.setLayoutManager(layoutManager);
        rvMovies.addItemDecoration(itemDecoration);
        rvMovies.setAdapter(getMovieAdapter());
        pbMovies.setVisibility(View.INVISIBLE);
        return view;
    }

    @OnClick(R.id.btn_search)
    public void searchAction() {
        if (TextUtils.isEmpty(txtSearch.getText().toString().trim())) {
            Snackbar snackbar = Snackbar.make(rvMovies, "", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            populateMovieData(txtSearch.getText().toString().trim());
        }
    }

    @Override
    public void onMovieItemClicked(Movie movie) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(getResources().getString(R.string.parcel_movie), movie);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(getResources().getString(R.string.serial_movie), getMovieAdapter().getMovies());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(getResources().getString(R.string.serial_movie))) {
            getMovieAdapter().setMovies((ArrayList<Movie>) savedInstanceState.getSerializable(getResources().getString(R.string.serial_movie)));
        }
    }

    public MovieAdapter getMovieAdapter() {
        if (movieAdapter == null) {
            movieAdapter = new MovieAdapter();
            movieAdapter.setMovieDataListener(this);
        }
        return movieAdapter;
    }

    private void populateMovieData(String movieTitle) {
        pbMovies.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                TmdbUtil.getMovieUrl(MovieEnum.SEARCH, movieTitle),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            Gson gson = new Gson();
                            ArrayList<Movie> movies = new ArrayList<>();
                            movies.addAll(Arrays.asList(gson.fromJson(jsonArray.toString(), Movie[].class)));
                            getMovieAdapter().setMovies(movies);
                            pbMovies.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
    }
}
