package com.example.deepstack;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddLogbookActivity extends AppCompatActivity {

    private LogbookViewModel logbookViewModel;
    private TextInputEditText etSpotName, etLatitude, etLongitude, etNotes;
    private Spinner spinnerGear;
    private List<Gear> gearsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_logbook);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add New Logbook");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etSpotName = findViewById(R.id.etSpotName);
        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        etNotes = findViewById(R.id.etNotes);
        spinnerGear = findViewById(R.id.spinnerGear);
        MaterialButton btnSave = findViewById(R.id.btnSave);

        logbookViewModel = new ViewModelProvider(this).get(LogbookViewModel.class);

        // Fetch Gears untuk Spinner
        logbookViewModel.getGearListLiveData().observe(this, gears -> {
            if (gears != null && !gears.isEmpty()) {
                this.gearsList = gears;
                List<String> gearNames = new ArrayList<>();
                for (Gear gear : gears) {
                    gearNames.add(gear.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gearNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerGear.setAdapter(adapter);
            }
        });

        // Hasil posting data
        logbookViewModel.getPostResultLiveData().observe(this, isSuccess -> {
            if (isSuccess != null && isSuccess) {
                Toast.makeText(this, "Logbook berhasil disimpan!", Toast.LENGTH_SHORT).show();
                logbookViewModel.resetPostResult();
                finish();
            }
        });

        // Error message
        logbookViewModel.getErrorLiveData().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });

        btnSave.setOnClickListener(v -> saveLog());
    }

    private void saveLog() {
        String spotName = etSpotName.getText().toString().trim();
        String latStr = etLatitude.getText().toString().trim();
        String lonStr = etLongitude.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        // Validasi input wajib
        if (spotName.isEmpty() || latStr.isEmpty() || lonStr.isEmpty()) {
            Toast.makeText(this, "Harap isi semua kolom wajib!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cek data gear
        if (gearsList.isEmpty()) {
            Toast.makeText(this, "Data gear belum tersedia, silakan tunggu...", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // KONVERSI DATA SESUAI SKEMA SUPABASE
            Double latitude = Double.parseDouble(latStr);
            Double longitude = Double.parseDouble(lonStr);

            int selectedPos = spinnerGear.getSelectedItemPosition();
            if (selectedPos < 0) {
                Toast.makeText(this, "Pilih gear terlebih dahulu!", Toast.LENGTH_SHORT).show();
                return;
            }

            Long gearId = (long) gearsList.get(selectedPos).getId();

            // Kirim ke ViewModel
            logbookViewModel.addNewLog(spotName, latitude, longitude, gearId, notes);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Latitude & Longitude harus berupa angka desimal!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}