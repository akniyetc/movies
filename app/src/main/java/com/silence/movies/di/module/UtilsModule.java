package com.silence.movies.di.module;

import android.content.Context;

import com.silence.movies.presentation.base.ErrorHandler;
import com.silence.movies.presentation.base.ResourceManager;
import com.silence.movies.system.AppSchedulers;
import com.silence.movies.system.SchedulersProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Singleton
    @Provides
    SchedulersProvider provideSchedulersProvider(AppSchedulers schedulers) {
        return schedulers;
    }

    @Singleton
    @Provides
    ResourceManager provideResourceManager(Context context) {
        return new ResourceManager(context);
    }

    @Singleton
    @Provides
    ErrorHandler provideErrorHandler(ResourceManager resourceManager) {
        return new ErrorHandler(resourceManager);
    }
}
