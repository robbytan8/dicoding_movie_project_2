package com.robby.dicoding_movie_project_2.util;

import com.robby.dicoding_movie_project_2.BuildConfig;

/**
 * @author Robby Tan
 */

public class TmdbUtil {

    private static final String API_KEY = "?api_key=" + BuildConfig.TMDB_KEY;
    private static final String POPULAR_MOVIES_URL = "https://api.themoviedb.org/3/movie/popular";
    private static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing";
    private static final String UPCOMING_URL = "https://api.themoviedb.org/3/movie/upcoming";
    private static final String SEARCH_URL = "https://api.themoviedb.org/3/search/movie";

    public static String getSearchMoviesUrl(String movieTitle) {
        if (movieTitle == null || movieTitle.isEmpty()) {
            return POPULAR_MOVIES_URL + API_KEY;
        }
        StringBuilder builder = new StringBuilder("");
        for (String query : movieTitle.split(" ")) {
            builder.append(query).append("+");
        }
        builder.deleteCharAt(builder.length() - 1);
        return "https://api.themoviedb.org/3/search/movie?api_key=" + BuildConfig.TMDB_KEY
                + "&query=" + builder.toString();
    }

    public static String getMovieUrl(MovieEnum movieEnum, String movieTitle) {
        switch (movieEnum) {
            case NOW_PLAYING:
                return NOW_PLAYING_URL + API_KEY;
            case POPULAR:
                return POPULAR_MOVIES_URL + API_KEY;
            case UPCOMING:
                return UPCOMING_URL + API_KEY;
            case SEARCH:
                StringBuilder buildQuery = new StringBuilder("&query=");
                for (String query : movieTitle.split(" ")) {
                    buildQuery.append(query).append("+");
                }
                buildQuery.deleteCharAt(buildQuery.length() - 1);
                return SEARCH_URL + API_KEY + buildQuery.toString();
        }
        return POPULAR_MOVIES_URL + API_KEY;
    }
}
