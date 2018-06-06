package com.robby.dicoding_movie_project_2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.robby.dicoding_movie_project_2.R;
import com.robby.dicoding_movie_project_2.entity.Movie;
import com.robby.dicoding_movie_project_2.util.ViewUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Robby Tan
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> movies;
    private MovieDataListener movieDataListener;

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final Movie movie = getMovies().get(position);
        holder.lblTitle.setText(movie.getTitle());
        holder.lblPublishedDate.setText(ViewUtil.getEasyReadableDate(movie.getRelease_date(),
                "yyyy-MM-dd", "dd MMM yyyy"));
        holder.lblOverview.setText(movie.getOverview());
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w185" + movie.getPoster_path())
                .error(R.mipmap.ic_launcher)
                .into(holder.imgPoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieDataListener.onMovieItemClicked(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getMovies().size();
    }

    public ArrayList<Movie> getMovies() {
        if (movies == null) {
            movies = new ArrayList<>();
        }
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.getMovies().clear();
        this.getMovies().addAll(movies);
        notifyDataSetChanged();
    }

    public void setMovieDataListener(MovieDataListener movieDataListener) {
        this.movieDataListener = movieDataListener;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_poster)
        ImageView imgPoster;
        @BindView(R.id.tv_movie_title)
        TextView lblTitle;
        @BindView(R.id.tv_movie_overview)
        TextView lblOverview;
        @BindView(R.id.tv_movie_published)
        TextView lblPublishedDate;

        private MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface MovieDataListener {

        void onMovieItemClicked(Movie movie);
    }
}
