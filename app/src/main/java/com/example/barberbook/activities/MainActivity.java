package com.example.barberbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.barberbook.R;
import com.example.barberbook.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private TextView tvUserName;
    private ImageView ivProfile;
    private CardView cardBookNow, cardMyAppointments, cardProfile;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize
        sessionManager = new SessionManager(this);

        // Find views
        tvUserName = findViewById(R.id.tvUserName);
        ivProfile = findViewById(R.id.ivProfile);
        cardBookNow = findViewById(R.id.cardBookNow);
        cardMyAppointments = findViewById(R.id.cardMyAppointments);
        cardProfile = findViewById(R.id.cardProfile);

        // Set user name
        tvUserName.setText(sessionManager.getUserName());

        // Book Now click
        cardBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectServiceActivity.class));
            }
        });

        // My Appointments click
        cardMyAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyAppointmentsActivity.class));
            }
        });

        // Profile click
        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        // Profile image click (same as profile card)
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
    }
}