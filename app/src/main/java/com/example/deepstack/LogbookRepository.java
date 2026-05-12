package com.example.deepstack;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogbookRepository {
    private final ApiService apiService;

    public LogbookRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    // Fungsi GET untuk mengambil daftar logbook
    public MutableLiveData<List<Logbook>> getLogbooks(String apikey, String bearerToken, MutableLiveData<String> errorLiveData, MutableLiveData<Boolean> loadingLiveData) {
        MutableLiveData<List<Logbook>> logbookData = new MutableLiveData<>();
        
        loadingLiveData.setValue(true);
        
        apiService.getLogbooks(apikey, bearerToken).enqueue(new Callback<List<Logbook>>() {
            @Override
            public void onResponse(Call<List<Logbook>> call, Response<List<Logbook>> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    logbookData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Failed to fetch logbooks: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Logbook>> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue("Error: " + t.getMessage());
            }
        });

        return logbookData;
    }

    // Fungsi POST untuk mengirim data logbook baru (Add New Log)
    public void addLog(String apikey, String bearerToken, Logbook logbook, MutableLiveData<Boolean> postResultLiveData, MutableLiveData<String> errorLiveData, MutableLiveData<Boolean> loadingLiveData) {
        loadingLiveData.setValue(true);

        apiService.insertLogbook(apikey, bearerToken, logbook).enqueue(new Callback<Logbook>() {
            @Override
            public void onResponse(Call<Logbook> call, Response<Logbook> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    postResultLiveData.setValue(true);
                } else {
                    postResultLiveData.setValue(false);
                    errorLiveData.setValue("Gagal menambahkan log: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Logbook> call, Throwable t) {
                loadingLiveData.setValue(false);
                postResultLiveData.setValue(false);
                errorLiveData.setValue("Error jaringan: " + t.getMessage());
            }
        });
    }
}
