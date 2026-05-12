package com.example.deepstack;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("gears")
    Call<List<Gear>> getGears();

    @GET("logbooks")
    Call<List<Logbook>> getLogbooks();

    @POST("logbooks")
    Call<Logbook> insertLogbook(@Body Logbook logbook);

    @GET("species")
    Call<List<Fish>> getFishes();
}
