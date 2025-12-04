package com.example.barberbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barberbook.R;

public class MainActivity extends AppCompatActivity {

    Button btnBookNow, btnMyAppointments, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBookNow = findViewById(R.id.btnBookNow);
        btnMyAppointments = findViewById(R.id.btnMyAppointments);
        btnProfile = findViewById(R.id.btnProfile);

        btnBookNow.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SelectServiceActivity.class)));

        btnMyAppointments.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MyAppointmentsActivity.class)));

        btnProfile.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ProfileActivity.class)));
    }
}
