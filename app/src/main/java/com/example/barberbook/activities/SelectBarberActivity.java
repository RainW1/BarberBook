package com.example.barberbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barberbook.R;

public class SelectBarberActivity extends AppCompatActivity {

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_barber);

        btnNext = findViewById(R.id.btnNextBarber);

        btnNext.setOnClickListener(v -> {
            // TODO: Pass barber, date, time later
            Intent i = new Intent(SelectBarberActivity.this, ConfirmationActivity.class);
            startActivity(i);
        });
    }
}
