package com.example.deepstack;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FishActivity extends AppCompatActivity {

    private FishViewModel fishViewModel;
    private FishAdapter fishAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Fish Species");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressBar = findViewById(R.id.progressBar);
        RecyclerView rvFish = findViewById(R.id.rvFish);
        
        fishAdapter = new FishAdapter();
        rvFish.setLayoutManager(new LinearLayoutManager(this));
        rvFish.setAdapter(fishAdapter);

        fishViewModel = new ViewModelProvider(this).get(FishViewModel.class);

        fishViewModel.getLoadingLiveData().observe(this, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });

        fishViewModel.getErrorLiveData().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        fishViewModel.getFishListLiveData().observe(this, fishes -> {
            if (fishes != null) {
                fishAdapter.setFishList(fishes);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
