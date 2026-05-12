package com.example.deepstack;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://yiuonpbvnhvhcbfyyswn.supabase.co/rest/v1/";

    // Pastikan ini adalah 'anon' key dari dashboard Supabase Anda
    public static String supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlpdW9ucGJ2bmh2aHZjYmZ5eXN3biIsInJvbGUiOiJhb2wiLCJpYXQiOjE3NDk4NDgyNjAsImV4cCI6MjA2NTQyNDI2MH0.3W4z9-V87p2g9sXJt72T-j0tH0u88X66Rk-f9Rk_J74";

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
