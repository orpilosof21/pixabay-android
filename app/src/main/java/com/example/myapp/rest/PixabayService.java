package com.example.myapp.rest;


import com.example.myapp.model.PixabayResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PixabayService {
    @GET("/api/")
    Call<PixabayResponse> getImages(@Query("key") String apiKey, @Query("q") String query, @Query("image_type") String type);
}
