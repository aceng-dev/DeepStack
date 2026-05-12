package com.example.deepstack;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class LogbookViewModel extends ViewModel {
    private final LogbookRepository logbookRepository;

    private final MutableLiveData<List<Logbook>> logbookListLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Gear>> gearListLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> postResultLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> deleteResultLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    public LogbookViewModel() {
        logbookRepository = new LogbookRepository();
    }

    public void fetchLogbooks() {
        logbookRepository.fetchLogbooks(logbookListLiveData, errorLiveData, loadingLiveData);
    }

    public void fetchGears() {
        logbookRepository.fetchGears(gearListLiveData, errorLiveData, loadingLiveData);
    }

    public LiveData<List<Logbook>> getLogbookListLiveData() {
        if (logbookListLiveData.getValue() == null) fetchLogbooks();
        return logbookListLiveData;
    }

    public LiveData<List<Gear>> getGearListLiveData() {
        if (gearListLiveData.getValue() == null) fetchGears();
        return gearListLiveData;
    }

    // Bagian method addNewLog saja yang berubah tipe datanya
    public void addNewLog(String spotName, Double latitude, Double longitude, Long gearId, String notes) {
        Logbook newLog = new Logbook(spotName, latitude, longitude, gearId, notes);
        logbookRepository.addLog(newLog, postResultLiveData, errorLiveData, loadingLiveData);
    }

    public void deleteLog(Long id) {
        logbookRepository.deleteLog(id, deleteResultLiveData, errorLiveData, loadingLiveData);
    }

    public LiveData<Boolean> getPostResultLiveData() { return postResultLiveData; }
    public LiveData<Boolean> getDeleteResultLiveData() { return deleteResultLiveData; }
    public LiveData<String> getErrorLiveData() { return errorLiveData; }
    public LiveData<Boolean> getLoadingLiveData() { return loadingLiveData; }

    public void resetPostResult() {
        postResultLiveData.setValue(null);
    }
}