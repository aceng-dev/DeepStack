package com.example.deepstack;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FishRepository {
    private final ApiService apiService;

    public FishRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public MutableLiveData<List<Fish>> getFishes(MutableLiveData<String> errorLiveData, MutableLiveData<Boolean> loadingLiveData) {
        MutableLiveData<List<Fish>> fishData = new MutableLiveData<>();
        
        loadingLiveData.setValue(true);
        
        apiService.getFishes().enqueue(new Callback<List<Fish>>() {
            @Override
            public void onResponse(Call<List<Fish>> call, Response<List<Fish>> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    fishData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Failed: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Fish>> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue("Error: " + t.getMessage());
            }
        });

        return fishData;
    }
}
