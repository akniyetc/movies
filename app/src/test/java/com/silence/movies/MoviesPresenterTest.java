package com.silence.movies;

import android.content.Context;

import com.silence.movies.data.network.NetworkConnectionException;
import com.silence.movies.domain.entity.Movie;
import com.silence.movies.domain.interactor.MoviesInteractor;
import com.silence.movies.domain.repository.MoviesRepository;
import com.silence.movies.presentation.base.ErrorHandler;
import com.silence.movies.presentation.base.ResourceManager;
import com.silence.movies.presentation.movies.MoviesPresenter;
import com.silence.movies.presentation.movies.MoviesView;
import com.silence.movies.presentation.movies.MoviesView$$State;
import com.silence.movies.system.AppSchedulers;
import com.silence.movies.system.SchedulersProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collections;

import io.reactivex.Single;


public class MoviesPresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public RxSchedulerRule schedulerRule = new RxSchedulerRule();

    private MoviesRepository moviesRepository;
    private MoviesPresenter moviesPresenter;

    private MoviesView moviesView;
    private MoviesView$$State moviesViewState;

    private ErrorHandler errorHandler;

    private Movie testMovieModel;

    @Before
    public void setUp() {
        testMovieModel = new Movie("type", "year", "444", "posterUrl", "movie");

        Context context = Mockito.mock(Context.class);
        ResourceManager resourceManager = new ResourceManager(context);
        errorHandler = new ErrorHandler(resourceManager);

        moviesRepository = Mockito.mock(MoviesRepository.class);

        SchedulersProvider schedulersProvider = new AppSchedulers();

        MoviesInteractor moviesInteractor = new MoviesInteractor(moviesRepository, schedulersProvider);

        moviesView = Mockito.mock(MoviesView.class);
        moviesViewState = Mockito.mock(MoviesView$$State.class);

        moviesPresenter = new MoviesPresenter(moviesInteractor, errorHandler);
        moviesPresenter.setViewState(moviesViewState);
    }

    @Test
    public void testMoviesListSuccess() {
        Mockito.when(moviesRepository.getMovies(Mockito.anyString()))
                .thenReturn(Single.just(Collections.singletonList(testMovieModel)));

        moviesPresenter.attachView(moviesView);
        moviesPresenter.searchMovies("");
        Mockito.verify(moviesViewState).showLoading(true);
        Mockito.verify(moviesViewState).showStartSearchMessage(false);
        Mockito.verify(moviesViewState).showLoading(false);
        Mockito.verify(moviesViewState).showMovies(Collections.singletonList(testMovieModel));
    }


    @Test
    public void testMoviesListError() {
        NetworkConnectionException exception = new NetworkConnectionException();
        Mockito.when(moviesRepository.getMovies(Mockito.anyString()))
                .thenReturn(Single.error(exception));

        moviesPresenter.attachView(moviesView);
        moviesPresenter.searchMovies("");
        Mockito.verify(moviesViewState).showLoading(true);
        Mockito.verify(moviesViewState).showStartSearchMessage(false);
        Mockito.verify(moviesViewState).showLoading(false);
        Mockito.verify(moviesViewState).showMessage(errorHandler.getErrorMessage(exception));
    }
}
