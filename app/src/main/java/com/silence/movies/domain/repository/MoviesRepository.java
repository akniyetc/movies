package com.silence.movies.domain.repository;

import com.silence.movies.domain.entity.Movie;

import java.util.List;

import io.reactivex.Single;

public interface MoviesRepository {

    Single<List<Movie>> getMovies(String search);
}
