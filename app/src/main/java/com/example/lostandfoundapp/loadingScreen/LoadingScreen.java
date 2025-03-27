package com.example.lostandfoundapp.loadingScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.lostandfoundapp.MainActivity;
import com.example.lostandfoundapp.R;

public class LoadingScreen extends AppCompatActivity {

    private static final long LOADING_DELAY = 1500;
    private boolean isLoadingComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            try {
                isLoadingComplete = true;
                Intent intent = new Intent(LoadingScreen.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                finish();
            }
        }, LOADING_DELAY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    public void onBackPressed() {
        if (!isLoadingComplete) {
            // Show a message to the user during loading
            Toast.makeText(this, "Please wait while the app loads...", Toast.LENGTH_SHORT).show();
            // Optionally, you could allow exit after a double press
            // For now, we'll keep it disabled
        } else {
            // If loading is complete but transition hasn't occurred, allow default behavior
            super.onBackPressed();
        }
    }
}