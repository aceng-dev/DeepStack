package com.example.deepstack;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @GET("gears")
    Call<List<Gear>> getGears(
            @Header("apikey") String apikey,
            @Header("Authorization") String bearerToken
    );
    @GET("logbooks")
    Call<List<Logbook>> getLogbooks(
            @Header("apikey") String apikey,
            @Header("Authorization") String bearerToken
    );

    @POST("logbooks")
    Call<Logbook> insertLogbook(
            @Header("apikey") String apikey,
            @Header("Authorization") String bearerToken,
            @Body Logbook logbook
    );

    // Assuming we're fetching from FishWatch API or mock API
    @GET("species")
    Call<List<Fish>> getFishes();
}
