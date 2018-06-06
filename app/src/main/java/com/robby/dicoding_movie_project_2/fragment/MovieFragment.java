package com.robby.dicoding_movie_project_2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.robby.dicoding_movie_project_2.DetailActivity;
import com.robby.dicoding_movie_project_2.MainActivity;
import com.robby.dicoding_movie_project_2.R;
import com.robby.dicoding_movie_project_2.adapter.MovieAdapter;
import com.robby.dicoding_movie_project_2.entity.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class MovieFragment extends Fragment implements MovieAdapter.MovieDataListener {

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.pb_movies)
    ProgressBar pbMovies;

    private MovieAdapter movieAdapter;

    @Override
    public void onStart() {
        super.onStart();
        populateMovieData(null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_fragment, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                layoutManager.getOrientation());
        rvMovies.setLayoutManager(layoutManager);
        rvMovies.addItemDecoration(itemDecoration);
        rvMovies.setAdapter(getMovieAdapter());
        return view;
    }

    @Override
    public void onMovieItemClicked(Movie movie) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(getResources().getString(R.string.parcel_movie), movie);
        startActivity(intent);
    }

    public MovieAdapter getMovieAdapter() {
        if (movieAdapter == null) {
            movieAdapter = new MovieAdapter();
            movieAdapter.setMovieDataListener(this);
        }
        return movieAdapter;
    }

    protected abstract void populateMovieData(String movieTitle);

}
