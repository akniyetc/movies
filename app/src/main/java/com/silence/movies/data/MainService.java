package com.silence.movies.data;

import com.silence.movies.domain.entity.Movies;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainService {

    @GET("./")
    Single<Movies> getMovies(@Query("s") String search);
}
