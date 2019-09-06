package com.silence.movies.di.module;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.provider.SearchRecentSuggestions;

import com.silence.movies.di.scope.MoviesScreenScope;
import com.silence.movies.ui.base.SearchHistoryProvider;
import com.silence.movies.ui.movies.MoviesActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesFragmentModule {

    @MoviesScreenScope
    @Provides
    SearchManager provideSearchManager(Context context) {
        return (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
    }

    @MoviesScreenScope
    @Provides
    ComponentName provideComponentName(Context context) {
        return new ComponentName(context, MoviesActivity.class);
    }

    @MoviesScreenScope
    @Provides
    SearchableInfo provideSearchableInfo(ComponentName componentName, SearchManager searchManager) {
        return searchManager.getSearchableInfo(componentName);
    }

    @MoviesScreenScope
    @Provides
    SearchRecentSuggestions provideSearchRecentSuggestions(Context context) {
        return new SearchRecentSuggestions(context, SearchHistoryProvider.AUTHORITY, SearchHistoryProvider.MODE);
    }
}
