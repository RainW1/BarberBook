package com.example.barberbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barberbook.R;

public class SelectServiceActivity extends AppCompatActivity {

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);

        btnNext = findViewById(R.id.btnNextService);

        btnNext.setOnClickListener(v -> {
            // TODO: Pass real selected services later
            Intent i = new Intent(SelectServiceActivity.this, SelectBarberActivity.class);
            startActivity(i);
        });
    }
}
