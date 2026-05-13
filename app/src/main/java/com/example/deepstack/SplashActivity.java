package com.example.deepstack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.ActivityOptions;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        // Hide ActionBar if exists
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ivLogo), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Tunggu 2 detik lalu pindah ke Dashboard
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);

            // Konfigurasi Shared Element Transition
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    SplashActivity.this,
                    findViewById(R.id.ivLogo),
                    "logo_morph"
            );

            startActivity(intent, options.toBundle());

            new Handler(Looper.getMainLooper()).postDelayed(this::finish, 1000);

        }, 2000); // 2000 ms = 2 detik delay di splash screen
    }
}