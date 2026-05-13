package com.example.deepstack;
import retrofit2.http.Url;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("gears")
    Call<List<Gear>> getGears();

    @GET("logbooks")
    Call<List<Logbook>> getLogbooks();


    @POST("logbooks")
    Call<List<Logbook>> insertLogbook( // Ubah ke List<Logbook>
                                       @Body Logbook logbook,
                                       @Header("Prefer") String prefer
    );

    @DELETE("logbooks")
    Call<Void> deleteLogbook(
            @Query("id") String idFilter // Format: "eq.123"
    );

    // BARU ✅
    @GET("animals")
    Call<List<Fish>> getFishes(
            @Query("name") String name,
            @Header("X-Api-Key") String apiKey
    );
    @GET
    Call<WikiImageResponse> getWikiImage(@Url String url);

}
