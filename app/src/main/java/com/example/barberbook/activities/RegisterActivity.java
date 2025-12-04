package com.example.barberbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barberbook.R;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister, btnGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btnRegister);
        btnGoLogin = findViewById(R.id.btnGoLogin);

        btnRegister.setOnClickListener(v -> {
            // TODO: Add real register logic later
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        btnGoLogin.setOnClickListener(v -> finish());
    }
}
