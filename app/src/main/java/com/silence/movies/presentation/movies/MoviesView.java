package com.silence.movies.presentation.movies;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.silence.movies.domain.entity.Movie;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MoviesView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMessage(String message);

    void showMovies(List<Movie> movies);
    void showLoading(Boolean show);

    void showStartSearchMessage(Boolean show);
}
