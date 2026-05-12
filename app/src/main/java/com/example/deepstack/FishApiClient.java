package com.example.deepstack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FishApiClient {
    private static Retrofit retrofit = null;
    private static final String FISH_BASE_URL = "https://api.api-ninjas.com/v1/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            try {
                // Trust manager yang menerima semua sertifikat (untuk dev/emulator)
                X509TrustManager trustAllCerts = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] chain, String authType)
                            throws CertificateException {}

                    @Override
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] chain, String authType)
                            throws CertificateException {}

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                };

                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{trustAllCerts}, new java.security.SecureRandom());

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .sslSocketFactory(sslContext.getSocketFactory(), trustAllCerts)
                        .hostnameVerifier((hostname, session) -> true)
                        .addInterceptor(logging)
                        .build();

                Gson gson = new GsonBuilder().setLenient().create();

                retrofit = new Retrofit.Builder()
                        .baseUrl(FISH_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(client)
                        .build();

            } catch (Exception e) {
                android.util.Log.e("API_ERROR", "SSL Setup gagal: " + e.getMessage());
            }
        }
        return retrofit;
    }
}