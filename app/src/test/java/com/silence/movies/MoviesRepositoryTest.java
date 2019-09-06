package com.silence.movies;

import android.content.Context;

import com.silence.movies.data.MainService;
import com.silence.movies.data.MoviesRepositoryImpl;
import com.silence.movies.data.network.NetworkConnectionException;
import com.silence.movies.data.network.NetworkHandler;
import com.silence.movies.domain.entity.Movie;
import com.silence.movies.domain.entity.Movies;
import com.silence.movies.domain.repository.MoviesRepository;

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

public class MoviesRepositoryTest {

    private static final String RESPONSE_TRUE = "True";
    private static final String RESPONSE_FALSE = "False";

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public RxSchedulerRule schedulerRule = new RxSchedulerRule();

    private MainService mainService;
    private MoviesRepository moviesRepository;

    private TestNetworkHandler networkHandler;

    private Movies testMoviesModel;
    private List<Movie> testSearchModel;

    @Before
    public void setUp() {
        Movie testMovieModel = new Movie("type", "year", "444", "posterUrl", "movie");
        testSearchModel = Collections.singletonList(testMovieModel);
        testMoviesModel = new Movies(RESPONSE_TRUE, testSearchModel);

        mainService = Mockito.mock(MainService.class);

        Context context = Mockito.mock(Context.class);
        networkHandler = new TestNetworkHandler(context);

        moviesRepository = new MoviesRepositoryImpl(mainService, networkHandler);
    }

    @Test
    public void testMoviesEntitySuccess() {
        networkHandler.setConnected(true);

        Mockito.when(mainService.getMovies(Mockito.anyString()))
                .thenReturn(Single.just(testMoviesModel));

        TestObserver<List<Movie>> testObserver = moviesRepository.getMovies("").test();

        testObserver.assertNoErrors()
                .assertValue(testSearchModel)
                .assertComplete();
    }

    @Test
    public void testMoviesEntityServerError() {
        networkHandler.setConnected(true);

        testMoviesModel = new Movies(RESPONSE_FALSE, Collections.emptyList());
        Mockito.when(mainService.getMovies(Mockito.anyString()))
                .thenReturn(Single.just(testMoviesModel));

        TestObserver<List<Movie>> testObserver = moviesRepository.getMovies("").test();

        testObserver.assertNoErrors()
                .assertValue(testMoviesModel.getSearch());
    }

    @Test
    public void testMoviesEntityNetworkError() {
        networkHandler.setConnected(false);

        Mockito.when(mainService.getMovies(Mockito.anyString()))
                .thenReturn(Single.just(testMoviesModel));

        TestObserver<List<Movie>> testObserver = moviesRepository.getMovies("").test();

        testObserver.assertNoValues()
                .assertTerminated()
                .assertError(NetworkConnectionException.class);
    }


    public class TestNetworkHandler extends NetworkHandler {

        private boolean isConnected;

        TestNetworkHandler(Context context) {
            super(context);
        }

        void setConnected(boolean connected) {
            isConnected = connected;
        }

        @Override
        public Boolean isConnected() {
            return isConnected;
        }
    }
}
