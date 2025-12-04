package com.example.barberbook.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barberbook.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SelectBarberActivity extends AppCompatActivity {

    private ImageView btnBack;
    private RadioButton rbBarber1, rbBarber2, rbBarber3;
    private LinearLayout layoutDate;
    private TextView tvSelectedDate;
    private Button btnTime1, btnTime2, btnTime3, btnTime4, btnTime5, btnTime6, btnTime7, btnTime8, btnTime9;
    private Button btnNext;

    private ArrayList<String> selectedServices;
    private int totalPrice;
    private int selectedBarberId = -1;
    private String selectedBarberName = "";
    private String selectedDate = "";
    private String selectedTime = "";
    private Button lastSelectedTimeButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_barber);

        // Get data from previous activity
        selectedServices = getIntent().getStringArrayListExtra("selectedServices");
        totalPrice = getIntent().getIntExtra("totalPrice", 0);

        // Find views
        btnBack = findViewById(R.id.btnBack);
        rbBarber1 = findViewById(R.id.rbBarber1);
        rbBarber2 = findViewById(R.id.rbBarber2);
        rbBarber3 = findViewById(R.id.rbBarber3);
        layoutDate = findViewById(R.id.layoutDate);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        btnTime1 = findViewById(R.id.btnTime1);
        btnTime2 = findViewById(R.id.btnTime2);
        btnTime3 = findViewById(R.id.btnTime3);
        btnTime4 = findViewById(R.id.btnTime4);
        btnTime5 = findViewById(R.id.btnTime5);
        btnTime6 = findViewById(R.id.btnTime6);
        btnTime7 = findViewById(R.id.btnTime7);
        btnTime8 = findViewById(R.id.btnTime8);
        btnTime9 = findViewById(R.id.btnTime9);
        btnNext = findViewById(R.id.btnNext);

        // Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Barber selection
        rbBarber1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBarber(1, "Mas Andi");
                rbBarber2.setChecked(false);
                rbBarber3.setChecked(false);
            }
        });

        rbBarber2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBarber(2, "Mas Budi");
                rbBarber1.setChecked(false);
                rbBarber3.setChecked(false);
            }
        });

        rbBarber3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mas Chandra is busy
                Toast.makeText(SelectBarberActivity.this, "This barber is currently busy", Toast.LENGTH_SHORT).show();
                rbBarber3.setChecked(false);
            }
        });

        // Date picker
        layoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Time buttons
        setupTimeButton(btnTime1, "09:00");
        setupTimeButton(btnTime2, "10:00");
        setupTimeButton(btnTime3, "11:00");
        setupTimeButton(btnTime4, "13:00");
        setupTimeButton(btnTime5, "14:00");
        setupTimeButton(btnTime6, "15:00");
        setupTimeButton(btnTime7, "16:00");
        setupTimeButton(btnTime8, "17:00");
        setupTimeButton(btnTime9, "18:00");

        // Next button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateSelection()) {
                    goToConfirmation();
                }
            }
        });
    }

    private void selectBarber(int id, String name) {
        selectedBarberId = id;
        selectedBarberName = name;
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedCal = Calendar.getInstance();
                        selectedCal.set(year, month, dayOfMonth);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        selectedDate = sdf.format(selectedCal.getTime());
                        tvSelectedDate.setText(selectedDate);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        // Set minimum date to today
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void setupTimeButton(final Button button, final String time) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset previous button
                if (lastSelectedTimeButton != null) {
                    lastSelectedTimeButton.setBackgroundTintList(
                            getResources().getColorStateList(R.color.card_background));
                    lastSelectedTimeButton.setTextColor(getResources().getColor(R.color.text_primary));
                }

                // Highlight selected button
                button.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                button.setTextColor(getResources().getColor(R.color.text_on_primary));

                selectedTime = time;
                lastSelectedTimeButton = button;
            }
        });
    }

    private boolean validateSelection() {
        if (selectedBarberId == -1) {
            Toast.makeText(this, "Please select a barber", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (selectedTime.isEmpty()) {
            Toast.makeText(this, "Please select a time slot", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void goToConfirmation() {
        Intent intent = new Intent(SelectBarberActivity.this, ConfirmationActivity.class);
        intent.putStringArrayListExtra("selectedServices", selectedServices);
        intent.putExtra("totalPrice", totalPrice);
        intent.putExtra("barberId", selectedBarberId);
        intent.putExtra("barberName", selectedBarberName);
        intent.putExtra("selectedDate", selectedDate);
        intent.putExtra("selectedTime", selectedTime);
        startActivity(intent);
    }
}