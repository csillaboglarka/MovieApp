package com.example.movieapp.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetMovie {
    @GET("search/movie?api_key=a81fe7f758c6a8cba5a555b4cc66c421")
    Call<Example> getAllData(@Query("query") String query);

    @GET("movie/popular")
    Call<Example> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);




}
