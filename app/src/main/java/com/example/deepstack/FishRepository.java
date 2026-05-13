package com.example.deepstack;

import androidx.lifecycle.MutableLiveData;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FishRepository {
    private static final String API_KEY = "kkR4Xnv8U8IgSHqyc2C3u4pW9zyB7lWuMuCj3Om2";
    private final ApiService apiService;

    public FishRepository() {
        apiService = FishApiClient.getClient().create(ApiService.class);
    }

    public void fetchFishes(MutableLiveData<List<Fish>> fishData,
                            MutableLiveData<String> errorLiveData,
                            MutableLiveData<Boolean> loadingLiveData) {

        loadingLiveData.setValue(true);
        // Query "fish" untuk mendapat berbagai spesies ikan
        apiService.getFishes("fish", API_KEY).enqueue(new Callback<List<Fish>>() {
            @Override
            public void onResponse(Call<List<Fish>> call, Response<List<Fish>> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    fishData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Gagal: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Fish>> call, Throwable t) {
                loadingLiveData.setValue(false);
                android.util.Log.e("API_ERROR", "Pesan: " + t.getMessage());
                errorLiveData.setValue("Kesalahan: " + t.getMessage());
            }
        });
    }
}