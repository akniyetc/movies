package com.silence.movies.data;

import com.silence.movies.data.network.NetworkConnectionException;
import com.silence.movies.data.network.NetworkHandler;
import com.silence.movies.domain.entity.Movie;
import com.silence.movies.domain.entity.Movies;
import com.silence.movies.domain.repository.MoviesRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MoviesRepositoryImpl implements MoviesRepository {

    private MainService service;
    private NetworkHandler networkHandler;

    @Inject
    public MoviesRepositoryImpl(MainService service, NetworkHandler networkHandler) {
        this.service = service;
        this.networkHandler = networkHandler;
    }

    @Override
    public Single<List<Movie>> getMovies(String search) {
        if (networkHandler.hasNetworkConnection()) {
            return service.getMovies(search)
                    .map(Movies::getSearch);
        } else {
            return Single.error(new NetworkConnectionException());
        }
    }
}
