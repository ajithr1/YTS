package com.ajith.yts;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("list_movies.json")
    Call<JsonElement> getMovie(@Query("query_term") String query);

    @GET("movie_details.json")
    Call<JsonElement> getMovieDetail(@Query("movie_id") int movieID);

}
