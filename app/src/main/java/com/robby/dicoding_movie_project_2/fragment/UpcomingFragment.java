package com.robby.dicoding_movie_project_2.fragment;

import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.robby.dicoding_movie_project_2.entity.Movie;
import com.robby.dicoding_movie_project_2.util.MovieEnum;
import com.robby.dicoding_movie_project_2.util.TmdbUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Robby Tan
 */

public class UpcomingFragment extends MovieFragment {

    @Override
    protected void populateMovieData(String movieTitle) {
        pbMovies.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                TmdbUtil.getMovieUrl(MovieEnum.UPCOMING, movieTitle),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            Gson gson = new Gson();
                            ArrayList<Movie> movies = new ArrayList<>(Arrays.asList(gson.fromJson(jsonArray.toString(), Movie[].class)));
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
