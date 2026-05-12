package com.example.deepstack;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class LogbookViewModel extends ViewModel {
    private final LogbookRepository logbookRepository;
    private MutableLiveData<List<Logbook>> logbookListLiveData;
    private MutableLiveData<List<Gear>> gearListLiveData;
    private final MutableLiveData<Boolean> postResultLiveData;
    private final MutableLiveData<String> errorLiveData;
    private final MutableLiveData<Boolean> loadingLiveData;

    public LogbookViewModel() {
        logbookRepository = new LogbookRepository();
        postResultLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
        loadingLiveData = new MutableLiveData<>();
    }

    public void fetchLogbooks() {
        logbookListLiveData = logbookRepository.getLogbooks(errorLiveData, loadingLiveData);
    }

    public void fetchGears() {
        gearListLiveData = logbookRepository.getGears(errorLiveData, loadingLiveData);
    }

    public LiveData<List<Logbook>> getLogbookListLiveData() {
        if (logbookListLiveData == null) {
            logbookListLiveData = new MutableLiveData<>();
            fetchLogbooks();
        }
        return logbookListLiveData;
    }

    public LiveData<List<Gear>> getGearListLiveData() {
        if (gearListLiveData == null) {
            gearListLiveData = new MutableLiveData<>();
            fetchGears();
        }
        return gearListLiveData;
    }

    public void addNewLog(String spotName, String latitude, String longitude, int gearId, String notes) {
        Logbook newLog = new Logbook(spotName, latitude, longitude, gearId, notes);
        logbookRepository.addLog(newLog, postResultLiveData, errorLiveData, loadingLiveData);
    }

    public LiveData<Boolean> getPostResultLiveData() {
        return postResultLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }
}
