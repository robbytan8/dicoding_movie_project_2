package com.robby.dicoding_movie_project_2.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.robby.dicoding_movie_project_2.R;
import com.robby.dicoding_movie_project_2.entity.Movie;
import com.robby.dicoding_movie_project_2.util.MovieEnum;
import com.robby.dicoding_movie_project_2.util.TmdbUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;

public class PopularMovieFragment extends MovieFragment {

    @Override
    protected void populateMovieData(String movieTitle) {
        pbMovies.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                TmdbUtil.getMovieUrl(MovieEnum.POPULAR, movieTitle),
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
