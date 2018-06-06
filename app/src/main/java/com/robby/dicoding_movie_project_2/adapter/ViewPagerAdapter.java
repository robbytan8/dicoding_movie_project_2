package com.robby.dicoding_movie_project_2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.robby.dicoding_movie_project_2.fragment.PopularMovieFragment;
import com.robby.dicoding_movie_project_2.fragment.NowPlayingFragment;
import com.robby.dicoding_movie_project_2.fragment.SearchFragment;
import com.robby.dicoding_movie_project_2.fragment.UpcomingFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new NowPlayingFragment();
            case 2:
                return new UpcomingFragment();
            case 3:
                return new SearchFragment();
            default:
                return new PopularMovieFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
