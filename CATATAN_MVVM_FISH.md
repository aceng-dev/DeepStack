# Dokumentasi MVVM Data Ikan

File yang dikerjakan:
1. Fish.java : Model data ikan (id, speciesName, scientificName).
2. ApiService.java : Endpoint getFishes untuk mengambil list spesies.
3. FishRepository.java : Eksekusi API menggunakan Retrofit.
4. FishViewModel.java : Penghubung ke UI.

Cara pemanggilan di Activity/Fragment:

```java
FishViewModel viewModel = new ViewModelProvider(this).get(FishViewModel.class);

viewModel.getFishListLiveData().observe(this, data -> {
    // set data ke adapter
});

viewModel.getLoadingLiveData().observe(this, loading -> {
    // show/hide loading
});

viewModel.getErrorLiveData().observe(this, error -> {
    // tampilkan error
});
```
