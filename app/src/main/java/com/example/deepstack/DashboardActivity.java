package com.example.deepstack;

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
            // TODO: Nanti diarahkan ke Activity/Fragment Fish Species yang sebenarnya
            Toast.makeText(DashboardActivity.this, "Membuka halaman Fish Species...", Toast.LENGTH_SHORT).show();
        });

        cardGearSpotLog.setOnClickListener(v -> {
            // TODO: Nanti diarahkan ke Activity/Fragment Gear & Spot Log yang sebenarnya
            Toast.makeText(DashboardActivity.this, "Membuka halaman Gear & Spot Log...", Toast.LENGTH_SHORT).show();
        });
    }
}
