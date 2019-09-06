package com.silence.movies.presentation.movies;

import com.arellomobile.mvp.InjectViewState;
import com.silence.movies.domain.entity.Movie;
import com.silence.movies.domain.interactor.MoviesInteractor;
import com.silence.movies.presentation.base.BasePresenter;
import com.silence.movies.presentation.base.ErrorHandler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class MoviesPresenter extends BasePresenter<MoviesView> {

    private MoviesInteractor moviesInteractor;
    private ErrorHandler errorHandler;

    @Inject
    public MoviesPresenter(MoviesInteractor moviesInteractor, ErrorHandler errorHandler) {
        this.moviesInteractor = moviesInteractor;
        this.errorHandler = errorHandler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().showStartSearchMessage(true);
    }

    public void searchMovies(String text) {
        getViewState().showLoading(true);
        getViewState().showStartSearchMessage(false);

        Disposable searchDisposable = moviesInteractor.getMovies(text)
                .subscribe(
                        this::handleMovies,
                        this::handleError
                );

        connect(searchDisposable);
    }

    private void handleMovies(List<Movie> movies) {
        getViewState().showLoading(false);
        getViewState().showMovies(movies);
    }

    private void handleError(Throwable error) {
        getViewState().showLoading(false);
        getViewState().showMessage(errorHandler.getErrorMessage(error));
    }
}
