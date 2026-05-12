package com.example.deepstack;import androidx.lifecycle.MutableLiveData;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogbookRepository {
    private final ApiService apiService;

    public LogbookRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    // Mengambil daftar Logbook
    public void fetchLogbooks(MutableLiveData<List<Logbook>> logbookData, MutableLiveData<String> errorLiveData, MutableLiveData<Boolean> loadingLiveData) {
        loadingLiveData.setValue(true);
        apiService.getLogbooks().enqueue(new Callback<List<Logbook>>() {
            @Override
            public void onResponse(Call<List<Logbook>> call, Response<List<Logbook>> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    logbookData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Gagal memuat logbook: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Logbook>> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue("Kesalahan jaringan: " + t.getMessage());
            }
        });
    }

    // Mengambil daftar Gear (untuk Spinner)
    public void fetchGears(MutableLiveData<List<Gear>> gearData, MutableLiveData<String> errorLiveData, MutableLiveData<Boolean> loadingLiveData) {
        loadingLiveData.setValue(true);
        apiService.getGears().enqueue(new Callback<List<Gear>>() {
            @Override
            public void onResponse(Call<List<Gear>> call, Response<List<Gear>> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    gearData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Gagal memuat gear: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Gear>> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue("Kesalahan jaringan gear: " + t.getMessage());
            }
        });
    }

    // Menambah Logbook Baru
    public void addLog(Logbook logbook, MutableLiveData<Boolean> postResultLiveData, MutableLiveData<String> errorLiveData, MutableLiveData<Boolean> loadingLiveData) {
        loadingLiveData.setValue(true);
        // Header Prefer return=representation digunakan agar Supabase mengembalikan data yang diinsert (mencegah GSON error)
        apiService.insertLogbook(logbook, "return=representation").enqueue(new Callback<List<Logbook>>() {
            @Override
            public void onResponse(Call<List<Logbook>> call, Response<List<Logbook>> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful()) {
                    postResultLiveData.setValue(true);
                } else {
                    postResultLiveData.setValue(false);
                    errorLiveData.setValue("Gagal simpan (Error " + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<List<Logbook>> call, Throwable t) {
                loadingLiveData.setValue(false);
                postResultLiveData.setValue(false);
                errorLiveData.setValue("Kesalahan koneksi: " + t.getMessage());
            }
        });
    }

    // Menghapus Logbook berdasarkan ID
    public void deleteLog(Long id, MutableLiveData<Boolean> deleteResultLiveData, MutableLiveData<String> errorLiveData, MutableLiveData<Boolean> loadingLiveData) {
        loadingLiveData.setValue(true);
        // PostgREST Supabase menggunakan filter eq.ID
        String idFilter = "eq." + id;
        apiService.deleteLogbook(idFilter).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful()) {
                    deleteResultLiveData.setValue(true);
                } else {
                    deleteResultLiveData.setValue(false);
                    errorLiveData.setValue("Gagal menghapus: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                loadingLiveData.setValue(false);
                deleteResultLiveData.setValue(false);
                errorLiveData.setValue("Kesalahan koneksi: " + t.getMessage());
            }
        });
    }
}