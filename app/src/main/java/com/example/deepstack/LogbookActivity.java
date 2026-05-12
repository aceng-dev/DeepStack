package com.example.deepstack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LogbookActivity extends AppCompatActivity {

    private LogbookViewModel logbookViewModel;
    private LogbookAdapter logbookAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logbook);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Gear & Spot Log");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressBar = findViewById(R.id.progressBar);
        RecyclerView rvLogbook = findViewById(R.id.rvLogbook);
        FloatingActionButton fabAddLog = findViewById(R.id.fabAddLog);

        logbookAdapter = new LogbookAdapter();
        rvLogbook.setLayoutManager(new LinearLayoutManager(this));
        rvLogbook.setAdapter(logbookAdapter);

        logbookViewModel = new ViewModelProvider(this).get(LogbookViewModel.class);

        logbookViewModel.getLoadingLiveData().observe(this, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });

        logbookViewModel.getErrorLiveData().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        logbookViewModel.getLogbookListLiveData().observe(this, logbooks -> {
            if (logbooks != null) {
                logbookAdapter.setLogbookList(logbooks);
            }
        });

        fabAddLog.setOnClickListener(v -> {
            Intent intent = new Intent(LogbookActivity.this, AddLogbookActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data every time we return to this screen
        if (logbookViewModel != null) {
            logbookViewModel.fetchLogbooks();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
