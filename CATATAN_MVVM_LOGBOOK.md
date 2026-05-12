# Dokumentasi MVVM Data Logbook

File yang dikerjakan:
1. Logbook.java : Model data dengan constructor dan setter.
2. ApiService.java : Endpoint insertLogbook (POST) dan getLogbooks (GET).
3. LogbookRepository.java : Eksekusi API ke Supabase.
4. LogbookViewModel.java : Penghubung ke UI.

Cara pemanggilan di Activity/Fragment:

1. Menampilkan List Logbook (GET)

```java
LogbookViewModel viewModel = new ViewModelProvider(this).get(LogbookViewModel.class);

String supabaseKey = "ISI_ANON_KEY";
String bearerToken = "Bearer " + supabaseKey;

viewModel.getLogbookListLiveData(supabaseKey, bearerToken).observe(this, data -> {
    // set data ke adapter
});

viewModel.getLoadingLiveData().observe(this, loading -> {
    // show/hide loading
});

viewModel.getErrorLiveData().observe(this, error -> {
    // tampilkan error
});
```

2. Menambahkan Log Baru (POST)

```java
String spot = etSpot.getText().toString();
String lat = etLat.getText().toString();
String lon = etLong.getText().toString();
int gearId = selectedGearId;
String notes = etNotes.getText().toString();

viewModel.addNewLog(supabaseKey, bearerToken, spot, lat, lon, gearId, notes);

viewModel.getPostResultLiveData().observe(this, sukses -> {
    if (sukses != null && sukses) {
        // proses berhasil
    }
});
```
