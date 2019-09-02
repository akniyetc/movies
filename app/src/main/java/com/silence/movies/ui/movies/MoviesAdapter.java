package com.silence.movies.ui.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.silence.movies.R;
import com.silence.movies.domain.entity.Movie;
import com.silence.movies.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> items;

    public MoviesAdapter() {
        items = new ArrayList<>();
    }

    public void setMovies(List<Movie> movies) {
        items = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class MoviesViewHolder extends BaseViewHolder {
        @BindView(R.id.imgPoster)
        ImageView imgPoster;

        MoviesViewHolder(View itemView) {
            super(itemView);
        }

        void bind(Movie movie) {
            Glide.with(itemView)
                    .load(movie.getPoster())
                    .placeholder(R.drawable.ic_image)
                    .into(imgPoster);
        }
    }
}
