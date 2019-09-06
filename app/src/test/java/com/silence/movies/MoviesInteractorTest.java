package com.silence.movies;

import com.silence.movies.data.network.NetworkConnectionException;
import com.silence.movies.domain.entity.Movie;
import com.silence.movies.domain.interactor.MoviesInteractor;
import com.silence.movies.domain.repository.MoviesRepository;
import com.silence.movies.system.AppSchedulers;
import com.silence.movies.system.SchedulersProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

public class MoviesInteractorTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public RxSchedulerRule schedulerRule = new RxSchedulerRule();

    private MoviesRepository moviesRepository;
    private MoviesInteractor moviesInteractor;

    private Movie testMovieModel;

    @Before
    public void setUp() {
        testMovieModel = new Movie("type", "year", "444", "posterUrl", "movie");

        moviesRepository = Mockito.mock(MoviesRepository.class);

        SchedulersProvider schedulersProvider = new AppSchedulers();
        moviesInteractor = new MoviesInteractor(moviesRepository, schedulersProvider);
    }

    @Test
    public void testMoviesListSuccess() {
        List<Movie> testMoviesModel = Collections.singletonList(testMovieModel);
        Mockito.when(moviesRepository.getMovies(Mockito.anyString()))
                .thenReturn(Single.just(testMoviesModel));

        TestObserver<List<Movie>> testObserver = moviesInteractor.getMovies("s").test();

        testObserver.assertNoErrors()
                .assertValue(testMoviesModel)
                .assertComplete();
    }

    @Test
    public void testMoviesListError() {
        NetworkConnectionException exception = new NetworkConnectionException();
        Mockito.when(moviesRepository.getMovies(Mockito.anyString()))
                .thenReturn(Single.error(exception));

        TestObserver<List<Movie>> testObserver = moviesInteractor.getMovies("").test();

        Mockito.verify(moviesRepository, Mockito.times(1)).getMovies("");
        Mockito.verifyNoMoreInteractions(moviesRepository);

        testObserver.assertError(exception)
                .assertNoValues();
    }
}
