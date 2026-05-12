package com.example.deepstack;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://yiuonpbvnhvhcbfyyswn.supabase.co/rest/v1/";

    public static String supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlpdW9ucGJ2bmh2aGNiZnl5c3duIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Nzg0MTQxODMsImV4cCI6MjA5Mzk5MDE4M30.g_d5OyTZWCn1sNFLtCRRdbV4z5K_AvvTQUJ0ZI_b08k";

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("apikey", supabaseKey)
                                .header("Authorization", "Bearer " + supabaseKey)
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
