package com.example.deepstack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class DashboardActivity extends AppCompatActivity {

    private MaterialCardView cardFishSpecies;
    private MaterialCardView cardGearSpotLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setAllowEnterTransitionOverlap(true);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);


        // Hide ActionBar if exists
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cardFishSpecies), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cardFishSpecies = findViewById(R.id.cardFishSpecies);
        cardGearSpotLog = findViewById(R.id.cardGearSpotLog);

        cardFishSpecies.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, FishActivity.class);
            startActivity(intent);
        });

        cardGearSpotLog.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, LogbookActivity.class);
            startActivity(intent);
        });
    }
}
