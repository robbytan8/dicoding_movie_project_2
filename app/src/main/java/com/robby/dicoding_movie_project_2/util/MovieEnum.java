package com.robby.dicoding_movie_project_2.util;

/**
 * @author Robby Tan
 */
public enum MovieEnum {

    NOW_PLAYING (1),
    POPULAR (2),
    UPCOMING (3),
    SEARCH (4);

    private int value;

    MovieEnum(int i) {
        this.value = i;
    }
}
