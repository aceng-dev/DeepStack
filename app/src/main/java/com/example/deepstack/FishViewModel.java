package com.example.deepstack;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class FishViewModel extends ViewModel {
    private final FishRepository fishRepository;
    private final MutableLiveData<List<Fish>> fishListLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    public FishViewModel() {
        fishRepository = new FishRepository();
    }

    public void fetchFishes() {
        fishRepository.fetchFishes(fishListLiveData, errorLiveData, loadingLiveData);
    }

    public LiveData<List<Fish>> getFishListLiveData() {
        if (fishListLiveData.getValue() == null) fetchFishes();
        return fishListLiveData;
    }

    public LiveData<String> getErrorLiveData() { return errorLiveData; }
    public LiveData<Boolean> getLoadingLiveData() { return loadingLiveData; }
}