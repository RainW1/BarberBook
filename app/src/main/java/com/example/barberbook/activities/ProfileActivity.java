package com.example.barberbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barberbook.R;
import com.example.barberbook.database.DatabaseHelper;
import com.example.barberbook.models.User;
import com.example.barberbook.utils.SessionManager;

public class ProfileActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView tvName, tvEmail, tvPhone, tvGender;
    private TextView tvTotalBookings, tvCompletedBookings, tvCancelledBookings;
    private Button btnEditProfile, btnLogout;

    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize
        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        // Find views
        btnBack = findViewById(R.id.btnBack);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvGender = findViewById(R.id.tvGender);
        tvTotalBookings = findViewById(R.id.tvTotalBookings);
        tvCompletedBookings = findViewById(R.id.tvCompletedBookings);
        tvCancelledBookings = findViewById(R.id.tvCancelledBookings);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);

        // Load user data
        loadUserData();

        // Load statistics
        loadStatistics();

        // Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Edit profile button
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement edit profile
                android.widget.Toast.makeText(ProfileActivity.this,
                        "Edit profile coming soon!", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        // Logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });
    }

    private void loadUserData() {
        User user = databaseHelper.getUserById(sessionManager.getUserId());

        if (user != null) {
            tvName.setText(user.getFullName());
            tvEmail.setText(user.getEmail());
            tvPhone.setText(user.getPhone());
            tvGender.setText(user.getGender());
        }
    }

    private void loadStatistics() {
        int userId = sessionManager.getUserId();

        int total = databaseHelper.getTotalBookingsByUserId(userId);
        int completed = databaseHelper.getBookingCountByStatus(userId, "Completed");
        int cancelled = databaseHelper.getBookingCountByStatus(userId, "Cancelled");

        tvTotalBookings.setText(String.valueOf(total));
        tvCompletedBookings.setText(String.valueOf(completed));
        tvCancelledBookings.setText(String.valueOf(cancelled));
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Clear session
                    sessionManager.logout();

                    // Go to login
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
