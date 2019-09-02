package com.silence.movies.presentation.base;

import com.silence.movies.R;
import com.silence.movies.data.network.NetworkConnectionException;

import javax.inject.Inject;

import retrofit2.HttpException;

public class ErrorHandler {

    private ResourceManager resourceManager;

    @Inject
    public ErrorHandler(ResourceManager resourceManager){
        this.resourceManager = resourceManager;
    }

    public String getErrorMessage(Throwable error) {
        if (error instanceof NetworkConnectionException) {
            return resourceManager.getString(R.string.error_message_network);
        } else if (error instanceof HttpException) {
            return resourceManager.getString(R.string.error_message_server);
        } else {
            return resourceManager.getString(R.string.error_message_unknown);
        }
    }
}
