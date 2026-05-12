package com.example.deepstack;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class LogbookViewModel extends ViewModel {
    private final LogbookRepository logbookRepository;
    private MutableLiveData<List<Logbook>> logbookListLiveData;
    private final MutableLiveData<Boolean> postResultLiveData;
    private final MutableLiveData<String> errorLiveData;
    private final MutableLiveData<Boolean> loadingLiveData;

    public LogbookViewModel() {
        logbookRepository = new LogbookRepository();
        postResultLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
        loadingLiveData = new MutableLiveData<>();
    }

    // --- FITUR GET: Mengambil daftar logbook ---
    public void fetchLogbooks(String apikey, String bearerToken) {
        logbookListLiveData = logbookRepository.getLogbooks(apikey, bearerToken, errorLiveData, loadingLiveData);
    }

    public LiveData<List<Logbook>> getLogbookListLiveData(String apikey, String bearerToken) {
        if (logbookListLiveData == null) {
            logbookListLiveData = new MutableLiveData<>();
            fetchLogbooks(apikey, bearerToken);
        }
        return logbookListLiveData;
    }

    public LiveData<List<Logbook>> getLogbookListLiveData() {
        return logbookListLiveData;
    }

    // --- FITUR POST: Menambahkan log baru (Add New Log) ---
    public void addNewLog(String apikey, String bearerToken, String spotName, String latitude, String longitude, int gearId, String notes) {
        // Membungkus parameter menjadi objek Logbook
        Logbook newLog = new Logbook(spotName, latitude, longitude, gearId, notes);
        
        // Memanggil fungsi addLog di repository
        logbookRepository.addLog(apikey, bearerToken, newLog, postResultLiveData, errorLiveData, loadingLiveData);
    }

    // Mengamati hasil POST (true jika berhasil, false jika gagal)
    public LiveData<Boolean> getPostResultLiveData() {
        return postResultLiveData;
    }

    // --- Status Umum (Loading & Error) ---
    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }
}
