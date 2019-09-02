package com.silence.movies;

import android.app.Application;

import com.silence.movies.di.component.AppComponent;
import com.silence.movies.di.component.DaggerAppComponent;
import com.silence.movies.di.component.MoviesScreenComponent;
import com.silence.movies.di.module.AppModule;
import com.silence.movies.di.module.DataModule;
import com.silence.movies.di.module.NetworkModule;
import com.silence.movies.di.module.UtilsModule;

public class App extends Application {

    private static App instance;
    private static AppComponent appComponent;
    private static MoviesScreenComponent moviesScreenComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        initAppComponent();
    }

    public static App getInstance() {
        return instance;
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .networkModule(new NetworkModule())
                .dataModule(new DataModule())
                .utilsModule(new UtilsModule())
                .build();
    }

    public MoviesScreenComponent moviesScreenComponent() {
        if (moviesScreenComponent == null) {
            moviesScreenComponent = appComponent.moviesScreenComponent();
        }
        return moviesScreenComponent;
    }

    public void closeMoviesComponent() {
        moviesScreenComponent = null;
    }

}
