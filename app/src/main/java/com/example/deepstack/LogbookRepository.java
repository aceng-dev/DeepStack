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

    public MutableLiveData<List<Logbook>> getLogbooks(MutableLiveData<String> errorLiveData, MutableLiveData<Boolean> loadingLiveData) {
        MutableLiveData<List<Logbook>> logbookData = new MutableLiveData<>();
        
        loadingLiveData.setValue(true);
        
        apiService.getLogbooks().enqueue(new Callback<List<Logbook>>() {
            @Override
            public void onResponse(Call<List<Logbook>> call, Response<List<Logbook>> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    logbookData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Failed: " + response.code() + " " + response.message());
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

    public MutableLiveData<List<Gear>> getGears(MutableLiveData<String> errorLiveData, MutableLiveData<Boolean> loadingLiveData) {
        MutableLiveData<List<Gear>> gearData = new MutableLiveData<>();
        loadingLiveData.setValue(true);

        apiService.getGears().enqueue(new Callback<List<Gear>>() {
            @Override
            public void onResponse(Call<List<Gear>> call, Response<List<Gear>> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    gearData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Failed Gears: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Gear>> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue("Error Gears: " + t.getMessage());
            }
        });
        return gearData;
    }

    public void addLog(Logbook logbook, MutableLiveData<Boolean> postResultLiveData, MutableLiveData<String> errorLiveData, MutableLiveData<Boolean> loadingLiveData) {
        loadingLiveData.setValue(true);

        apiService.insertLogbook(logbook).enqueue(new Callback<Logbook>() {
            @Override
            public void onResponse(Call<Logbook> call, Response<Logbook> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    postResultLiveData.setValue(true);
                } else {
                    postResultLiveData.setValue(false);
                    errorLiveData.setValue("Gagal: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Logbook> call, Throwable t) {
                loadingLiveData.setValue(false);
                postResultLiveData.setValue(false);
                errorLiveData.setValue("Error: " + t.getMessage());
            }
        });
    }
}
