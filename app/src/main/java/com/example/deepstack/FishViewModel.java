package com.example.deepstack;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class FishViewModel extends ViewModel {
    private final FishRepository fishRepository;
    private MutableLiveData<List<Fish>> fishListLiveData;
    private final MutableLiveData<String> errorLiveData;
    private final MutableLiveData<Boolean> loadingLiveData;

    public FishViewModel() {
        fishRepository = new FishRepository();
        errorLiveData = new MutableLiveData<>();
        loadingLiveData = new MutableLiveData<>();
    }

    public void fetchFishes() {
        fishListLiveData = fishRepository.getFishes(errorLiveData, loadingLiveData);
    }

    public LiveData<List<Fish>> getFishListLiveData() {
        if (fishListLiveData == null) {
            fishListLiveData = new MutableLiveData<>();
            fetchFishes();
        }
        return fishListLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }
}
