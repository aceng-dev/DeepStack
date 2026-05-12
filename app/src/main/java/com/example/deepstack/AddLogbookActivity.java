package com.example.deepstack;

import android.os.Bundle;
import android.view.View;
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

        // Fetch Gears for Spinner
        logbookViewModel.getGearListLiveData().observe(this, gears -> {
            if (gears != null) {
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

        logbookViewModel.getPostResultLiveData().observe(this, isSuccess -> {
            if (isSuccess != null && isSuccess) {
                Toast.makeText(this, "Log successfully added!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });

        logbookViewModel.getErrorLiveData().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });

        btnSave.setOnClickListener(v -> saveLog());
    }

    private void saveLog() {
        String spotName = etSpotName.getText().toString().trim();
        String lat = etLatitude.getText().toString().trim();
        String lon = etLongitude.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        if (spotName.isEmpty() || lat.isEmpty() || lon.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int gearId = 0;
        if (!gearsList.isEmpty()) {
            gearId = gearsList.get(spinnerGear.getSelectedItemPosition()).getId();
        }

        logbookViewModel.addNewLog(spotName, lat, lon, gearId, notes);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
