package com.silence.movies.di.module;

import com.silence.movies.data.MoviesRepositoryImpl;
import com.silence.movies.domain.interactor.MoviesInteractor;
import com.silence.movies.domain.repository.MoviesRepository;
import com.silence.movies.system.SchedulersProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Singleton
    @Provides
    MoviesRepository provideMoviesRepository(MoviesRepositoryImpl moviesRepository) {
        return moviesRepository;
    }

    @Singleton
    @Provides
    MoviesInteractor provideMoviesInteractor(MoviesRepository moviesRepository, SchedulersProvider schedulersProvider) {
        return new MoviesInteractor(moviesRepository, schedulersProvider);
    }

}
