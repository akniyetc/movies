package com.silence.movies.presentation.base;

import android.content.Context;

import androidx.annotation.StringRes;

import javax.inject.Inject;

public class ResourceManager {

    private Context context;

    @Inject
    public ResourceManager(Context context) {
        this.context = context;
    }

    public String getString(@StringRes int id) {
        return context.getString(id);
    }
}
