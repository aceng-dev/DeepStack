package com.example.deepstack;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {
    private LogbookViewModel logbookViewModel;
    private LogbookAdapter logbookAdapter;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private RecyclerView recyclerView;
    private Button addButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SharedPreferences sharedPreferences;

    // API Credentials
    private static final String PREFS_NAME = "deepstack_prefs";
    private static final String KEY_API_KEY = "api_key";
    private static final String KEY_BEARER_TOKEN = "bearer_token";
    
    // Default values - CHANGE THESE with your actual Supabase credentials
    private static final String DEFAULT_API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlpdW9ucGJ2bmhIdmhjYmZ5eXN3biIsInJvbGUiOiJhbm9uIiwiaWF0IjoxNzEyMzc5NzI3LCJleHAiOjE5Mjc5NTk3Mjd9.DEiL_p0nX_lKGJ8r-zRq-AeJr8VJZ8vvb8r9JW5Z5o0";
    private static final String DEFAULT_BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlpdW9ucGJ2bmhIdmhjYmZ5eXN3biIsInJvbGUiOiJhbm9uIiwiaWF0IjoxNzEyMzc5NzI3LCJleHAiOjE5Mjc5NTk3Mjd9.DEiL_p0nX_lKGJ8r-zRq-AeJr8VJZ8vvb8r9JW5Z5o0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Initialize UI components
        progressBar = findViewById(R.id.progressBar);
        errorTextView = findViewById(R.id.errorTextView);
        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.addButton);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        // Setup RecyclerView with adapter
        logbookAdapter = new LogbookAdapter();
        recyclerView.setAdapter(logbookAdapter);

        // Initialize ViewModel
        logbookViewModel = new ViewModelProvider(this).get(LogbookViewModel.class);

        // Setup SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this::refreshLogbooks);

        // Observe loading state
        logbookViewModel.getLoadingLiveData().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? android.view.View.VISIBLE : android.view.View.GONE);
            swipeRefreshLayout.setRefreshing(isLoading);
        });

        // Observe error state
        logbookViewModel.getErrorLiveData().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                errorTextView.setText(error);
                errorTextView.setVisibility(android.view.View.VISIBLE);
                showToast("Error: " + error);
            } else {
                errorTextView.setVisibility(android.view.View.GONE);
            }
        });

        // Observe logbook list
        logbookViewModel.getLogbookListLiveData().observe(this, logbooks -> {
            if (logbooks != null && !logbooks.isEmpty()) {
                logbookAdapter.setLogbookList(logbooks);
                errorTextView.setVisibility(android.view.View.GONE);
            } else if (logbooks != null && logbooks.isEmpty()) {
                showToast("No logbooks found");
            }
        });

        // Setup add button click listener
        addButton.setOnClickListener(v -> {
            showToast(getString(R.string.add_new_log_clicked));
        });

        // Fetch logbooks on activity creation
        fetchLogbooksData();
    }

    private void fetchLogbooksData() {
        String apikey = sharedPreferences.getString(KEY_API_KEY, DEFAULT_API_KEY);
        String bearerToken = sharedPreferences.getString(KEY_BEARER_TOKEN, DEFAULT_BEARER_TOKEN);

        if (apikey.isEmpty() || bearerToken.isEmpty()) {
            showToast("API credentials not configured. Please set your Supabase credentials.");
            errorTextView.setText("Configuration Error: Missing API credentials");
            errorTextView.setVisibility(android.view.View.VISIBLE);
            return;
        }

        logbookViewModel.fetchLogbooks(apikey, bearerToken);
    }

    private void refreshLogbooks() {
        fetchLogbooksData();
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void updateCredentials(String apikey, String bearerToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_API_KEY, apikey);
        editor.putString(KEY_BEARER_TOKEN, bearerToken);
        editor.apply();
        fetchLogbooksData();
    }
}