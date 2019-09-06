package com.silence.movies.di.module;

import android.content.Context;
import android.net.ConnectivityManager;

import com.silence.movies.BuildConfig;
import com.silence.movies.data.MainService;
import com.silence.movies.data.network.NetworkHandler;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final String BASE_URL = "http://www.omdbapi.com/";
    private static final String API_KEY = "apikey";
    private static final String API_KEY_VALUE = "29f37fcc";
    private static final int TIMEOUT = 60;

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(chain -> {
            Request request = chain.request();
            HttpUrl.Builder url = request.url().newBuilder()
                    .addQueryParameter(API_KEY, API_KEY_VALUE);
            request = request.newBuilder()
                    .url(url.build())
                    .build();
            return chain.proceed(request);
        });
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor =
                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
        }

        return okHttpClientBuilder
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    MainService provideMainService(Retrofit retrofit) {
        return retrofit.create(MainService.class);
    }

    @Singleton
    @Provides
    NetworkHandler provideNetworkHandler(ConnectivityManager connectivityManager) {
        return new NetworkHandler(connectivityManager);
    }

    @Singleton
    @Provides
    ConnectivityManager provideConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
