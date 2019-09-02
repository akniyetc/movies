package com.silence.movies.di.component;

import com.silence.movies.di.scope.MoviesScreenScope;
import com.silence.movies.presentation.movies.MoviesPresenter;
import com.silence.movies.ui.movies.MoviesFragment;

import dagger.Subcomponent;

@MoviesScreenScope
@Subcomponent()
public interface MoviesScreenComponent {

    void inject(MoviesFragment fragment);

    MoviesPresenter getMoviesPresenter();
}
