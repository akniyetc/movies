package com.silence.movies.ui.movies;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.silence.movies.R;
import com.silence.movies.domain.entity.Movie;
import com.silence.movies.ui.base.BaseViewHolder;

import butterknife.BindView;

public class MoviesViewHolder extends BaseViewHolder {

    @BindView(R.id.imgPoster)
    ImageView imgPoster;

    @BindView(R.id.tvMovieTitle)
    TextView tvMovieTitle;

    MoviesViewHolder(View itemView) {
        super(itemView);
    }

    void bind(Movie movie) {
        Glide.with(itemView)
                .load(movie.getPoster())
                .placeholder(R.drawable.ic_image)
                .into(imgPoster);

        tvMovieTitle.setText(movie.getTitle());
    }
}
