package com.example.barberbook.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barberbook.R;
import com.example.barberbook.adapters.BookingAdapter;
import com.example.barberbook.database.DatabaseHelper;
import com.example.barberbook.models.Booking;
import com.example.barberbook.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class MyAppointmentsActivity extends AppCompatActivity implements BookingAdapter.OnBookingClickListener {

    private ImageView btnBack;
    private Button btnAll, btnUpcoming, btnCompleted;
    private RecyclerView recyclerView;
    private TextView tvEmpty;

    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;
    private BookingAdapter adapter;
    private List<Booking> allBookings;
    private List<Booking> filteredBookings;

    private String currentFilter = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        // Initialize
        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        // Find views
        btnBack = findViewById(R.id.btnBack);
        btnAll = findViewById(R.id.btnAll);
        btnUpcoming = findViewById(R.id.btnUpcoming);
        btnCompleted = findViewById(R.id.btnCompleted);
        recyclerView = findViewById(R.id.recyclerView);
        tvEmpty = findViewById(R.id.tvEmpty);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        filteredBookings = new ArrayList<>();
        adapter = new BookingAdapter(this, filteredBookings, this);
        recyclerView.setAdapter(adapter);

        // Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Tab buttons
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveTab(btnAll);
                currentFilter = "All";
                filterBookings();
            }
        });

        btnUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveTab(btnUpcoming);
                currentFilter = "Upcoming";
                filterBookings();
            }
        });

        btnCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveTab(btnCompleted);
                currentFilter = "Completed";
                filterBookings();
            }
        });

        // Load bookings
        loadBookings();
    }

    private void setActiveTab(Button activeBtn) {
        // Reset all buttons
        btnAll.setBackgroundTintList(getResources().getColorStateList(R.color.text_secondary));
        btnUpcoming.setBackgroundTintList(getResources().getColorStateList(R.color.text_secondary));
        btnCompleted.setBackgroundTintList(getResources().getColorStateList(R.color.text_secondary));

        // Set active button
        activeBtn.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
    }

    private void loadBookings() {
        allBookings = databaseHelper.getBookingsByUserId(sessionManager.getUserId());
        filterBookings();
    }

    private void filterBookings() {
        filteredBookings.clear();

        if (currentFilter.equals("All")) {
            filteredBookings.addAll(allBookings);
        } else {
            for (Booking booking : allBookings) {
                if (booking.getStatus().equals(currentFilter)) {
                    filteredBookings.add(booking);
                }
            }
        }

        adapter.updateList(filteredBookings);

        // Show/hide empty state
        if (filteredBookings.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCancelClick(Booking booking, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Cancel Booking")
                .setMessage("Are you sure you want to cancel this booking?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    int result = databaseHelper.cancelBooking(booking.getId());
                    if (result > 0) {
                        Toast.makeText(this, "Booking cancelled", Toast.LENGTH_SHORT).show();
                        loadBookings(); // Refresh list
                    } else {
                        Toast.makeText(this, "Failed to cancel booking", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBookings();
    }
}