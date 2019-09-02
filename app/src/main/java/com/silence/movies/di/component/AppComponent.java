package com.silence.movies.di.component;

import com.silence.movies.di.module.AppModule;
import com.silence.movies.di.module.DataModule;
import com.silence.movies.di.module.NetworkModule;
import com.silence.movies.di.module.UtilsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        DataModule.class,
        NetworkModule.class,
        UtilsModule.class
})
public interface AppComponent {

    MoviesScreenComponent moviesScreenComponent();
}
