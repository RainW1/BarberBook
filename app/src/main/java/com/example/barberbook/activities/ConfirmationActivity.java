package com.example.barberbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barberbook.R;

public class ConfirmationActivity extends AppCompatActivity {

    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        btnConfirm = findViewById(R.id.btnConfirmBooking);

        btnConfirm.setOnClickListener(v -> {
            // TODO: Insert booking later
            startActivity(new Intent(ConfirmationActivity.this, MainActivity.class));
            finish();
        });
    }
}
