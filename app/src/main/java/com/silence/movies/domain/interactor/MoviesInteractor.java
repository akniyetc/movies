package com.silence.movies.domain.interactor;

import com.silence.movies.domain.entity.Movie;
import com.silence.movies.domain.repository.MoviesRepository;
import com.silence.movies.system.SchedulersProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MoviesInteractor {

    private MoviesRepository moviesRepository;
    private SchedulersProvider schedulersProvider;

    @Inject
    public MoviesInteractor(MoviesRepository repository, SchedulersProvider schedulersProvider) {
        moviesRepository = repository;
        this.schedulersProvider = schedulersProvider;
    }

    public Single<List<Movie>> getMovies(String search) {
        return moviesRepository.getMovies(search)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui());
    }
}
