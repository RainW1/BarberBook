package com.example.barberbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barberbook.R;
import com.example.barberbook.database.DatabaseHelper;
import com.example.barberbook.models.Booking;
import com.example.barberbook.utils.SessionManager;

import java.util.ArrayList;

public class ConfirmationActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView tvBarberName, tvDateTime, tvServices, tvTotalPrice;
    private EditText etNotes;
    private Button btnConfirm;

    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    private ArrayList<String> selectedServices;
    private int totalPrice;
    private int barberId;
    private String barberName;
    private String selectedDate;
    private String selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        // Initialize
        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        // Get data from previous activity
        selectedServices = getIntent().getStringArrayListExtra("selectedServices");
        totalPrice = getIntent().getIntExtra("totalPrice", 0);
        barberId = getIntent().getIntExtra("barberId", 0);
        barberName = getIntent().getStringExtra("barberName");
        selectedDate = getIntent().getStringExtra("selectedDate");
        selectedTime = getIntent().getStringExtra("selectedTime");

        // Find views
        btnBack = findViewById(R.id.btnBack);
        tvBarberName = findViewById(R.id.tvBarberName);
        tvDateTime = findViewById(R.id.tvDateTime);
        tvServices = findViewById(R.id.tvServices);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        etNotes = findViewById(R.id.etNotes);
        btnConfirm = findViewById(R.id.btnConfirm);

        // Set data
        tvBarberName.setText(barberName);
        tvDateTime.setText(selectedDate + ", " + selectedTime);
        tvServices.setText(android.text.TextUtils.join(", ", selectedServices));
        tvTotalPrice.setText("Rp " + String.format("%,d", totalPrice).replace(",", "."));

        // Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Confirm button
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBooking();
            }
        });
    }

    private void createBooking() {
        String notes = etNotes.getText().toString().trim();

        // Create booking object
        Booking booking = new Booking();
        booking.setUserId(sessionManager.getUserId());
        booking.setBarberId(barberId);
        booking.setBarberName(barberName);
        booking.setServices(android.text.TextUtils.join(", ", selectedServices));
        booking.setDate(selectedDate);
        booking.setTime(selectedTime);
        booking.setTotalPrice(totalPrice);
        booking.setStatus("Upcoming");
        booking.setNotes(notes);

        // Save to database
        long result = databaseHelper.createBooking(booking);

        if (result > 0) {
            Toast.makeText(this, "Booking confirmed successfully!", Toast.LENGTH_SHORT).show();

            // Go back to main and clear stack
            Intent intent = new Intent(ConfirmationActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Booking failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}