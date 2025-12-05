package com.example.barberbook.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.barberbook.R;
import com.example.barberbook.database.DatabaseHelper;
import com.example.barberbook.models.Barber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SelectBarberActivity extends AppCompatActivity {

    private ImageView btnBack;
    private LinearLayout layoutBarbers;
    private CardView layoutDate;
    private TextView tvSelectedDate;
    private Button btnTime1, btnTime2, btnTime3, btnTime4, btnTime5, btnTime6, btnTime7, btnTime8, btnTime9;
    private Button btnNext;

    private DatabaseHelper databaseHelper;
    private List<Barber> barberList;
    private List<RadioButton> radioButtons = new ArrayList<>();

    private ArrayList<String> selectedServices;
    private int totalPrice;
    private int selectedBarberId = -1;
    private String selectedBarberName = "";
    private String selectedDate = "";
    private String selectedTime = "";
    private Button lastSelectedTimeButton = null;
    private RadioButton lastSelectedRadioButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_barber);

        // Initialize database
        databaseHelper = new DatabaseHelper(this);

        // Get data from previous activity
        selectedServices = getIntent().getStringArrayListExtra("selectedServices");
        totalPrice = getIntent().getIntExtra("totalPrice", 0);

        // Find views
        btnBack = findViewById(R.id.btnBack);
        layoutBarbers = findViewById(R.id.layoutBarbers);
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

        // Load barbers from database
        loadBarbers();

        // Back button
        btnBack.setOnClickListener(v -> finish());

        // Date picker
        layoutDate.setOnClickListener(v -> showDatePicker());

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
        btnNext.setOnClickListener(v -> {
            if (validateSelection()) {
                goToConfirmation();
            }
        });
    }

    private void loadBarbers() {
        barberList = databaseHelper.getAllBarbers();
        layoutBarbers.removeAllViews();
        radioButtons.clear();

        for (Barber barber : barberList) {
            addBarberCard(barber);
        }
    }

    private void addBarberCard(final Barber barber) {
        // Inflate card layout
        View cardView = LayoutInflater.from(this).inflate(R.layout.item_barber, layoutBarbers, false);

        // Find views in card
        RadioButton rbBarber = cardView.findViewById(R.id.rbBarber);
        ImageView ivBarberPhoto = cardView.findViewById(R.id.ivBarberPhoto);
        TextView tvBarberName = cardView.findViewById(R.id.tvBarberName);
        TextView tvBarberRating = cardView.findViewById(R.id.tvBarberRating);
        TextView tvBarberStatus = cardView.findViewById(R.id.tvBarberStatus);

        // Set data
        tvBarberName.setText(barber.getName());
        tvBarberRating.setText("⭐ " + barber.getRating() + " • " + barber.getExperience() + " years exp");

        // Set availability
        if (barber.isAvailable()) {
            tvBarberStatus.setText("Available");
            tvBarberStatus.setTextColor(ContextCompat.getColor(this, R.color.status_upcoming));
            rbBarber.setEnabled(true);
            cardView.setAlpha(1f);
        } else {
            tvBarberStatus.setText("Not Available");
            tvBarberStatus.setTextColor(ContextCompat.getColor(this, R.color.status_cancelled));
            rbBarber.setEnabled(false);
            cardView.setAlpha(0.5f);
        }

        // Radio button click
        rbBarber.setOnClickListener(v -> {
            if (!barber.isAvailable()) {
                Toast.makeText(this, barber.getName() + " is not available", Toast.LENGTH_SHORT).show();
                rbBarber.setChecked(false);
                return;
            }

            // Uncheck previous selection
            if (lastSelectedRadioButton != null && lastSelectedRadioButton != rbBarber) {
                lastSelectedRadioButton.setChecked(false);
            }

            // Set new selection
            selectedBarberId = barber.getId();
            selectedBarberName = barber.getName();
            lastSelectedRadioButton = rbBarber;
        });

        // Card click (same as radio button)
        cardView.setOnClickListener(v -> {
            if (!barber.isAvailable()) {
                Toast.makeText(this, barber.getName() + " is not available", Toast.LENGTH_SHORT).show();
                return;
            }

            rbBarber.setChecked(true);

            // Uncheck previous selection
            if (lastSelectedRadioButton != null && lastSelectedRadioButton != rbBarber) {
                lastSelectedRadioButton.setChecked(false);
            }

            // Set new selection
            selectedBarberId = barber.getId();
            selectedBarberName = barber.getName();
            lastSelectedRadioButton = rbBarber;
        });

        radioButtons.add(rbBarber);
        layoutBarbers.addView(cardView);
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedCal = Calendar.getInstance();
                    selectedCal.set(year, month, dayOfMonth);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                    selectedDate = sdf.format(selectedCal.getTime());
                    tvSelectedDate.setText(selectedDate);
                    tvSelectedDate.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        // Set minimum date to today
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void setupTimeButton(final Button button, final String time) {
        button.setOnClickListener(v -> {
            // Reset previous button
            if (lastSelectedTimeButton != null) {
                lastSelectedTimeButton.setBackgroundTintList(
                        ContextCompat.getColorStateList(this, R.color.card_background));
                lastSelectedTimeButton.setTextColor(
                        ContextCompat.getColor(this, R.color.text_primary));
            }

            // Highlight selected button
            button.setBackgroundTintList(
                    ContextCompat.getColorStateList(this, R.color.accent));
            button.setTextColor(
                    ContextCompat.getColor(this, R.color.text_on_primary));

            selectedTime = time;
            lastSelectedTimeButton = button;
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

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh barbers when returning to this screen
        loadBarbers();
    }
}