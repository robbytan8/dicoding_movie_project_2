package com.robby.dicoding_movie_project_2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.robby.dicoding_movie_project_2.entity.Movie;
import com.robby.dicoding_movie_project_2.util.ViewUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Robby Tan
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_overview)
    TextView lblOverview;
    @BindView(R.id.tv_release_date)
    TextView lblReleaseDate;
    @BindView(R.id.iv_backdrop)
    ImageView ivBackDrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rb_movie)
    RatingBar rbMovie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_fragment);
        ButterKnife.bind(this);
        if (getIntent().hasExtra(getResources().getString(R.string.parcel_movie))) {
            Movie movie = getIntent().getParcelableExtra(getResources().getString(R.string.parcel_movie));
            lblOverview.setText(movie.getOverview());
            toolbar.setTitle(movie.getTitle());
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/w342" + movie.getBackdrop_path())
                    .into(ivBackDrop);
            rbMovie.setRating((float) movie.getVote_average());
            lblReleaseDate.setText(ViewUtil.getEasyReadableDate(movie.getRelease_date(),
                    "yyyy-MM-dd", "dd MMM yyyy"));
        }
    }
}
