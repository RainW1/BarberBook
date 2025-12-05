package com.example.barberbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.barberbook.activities.ManageBarbersActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barberbook.R;
import com.example.barberbook.adapters.AdminBookingAdapter;
import com.example.barberbook.database.DatabaseHelper;
import com.example.barberbook.models.Booking;

import java.util.ArrayList;
import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity implements AdminBookingAdapter.OnAdminActionListener {

    private Button btnLogout, btnAll, btnUpcoming, btnCompleted, btnCancelled;
    private TextView tvUpcomingCount, tvCompletedCount, tvCancelledCount, tvEmpty;
    private RecyclerView recyclerView;

    private DatabaseHelper databaseHelper;
    private AdminBookingAdapter adapter;
    private List<Booking> allBookings;
    private List<Booking> filteredBookings;

    private String currentFilter = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Initialize
        databaseHelper = new DatabaseHelper(this);

        // Find views
        btnLogout = findViewById(R.id.btnLogout);
        btnAll = findViewById(R.id.btnAll);
        btnUpcoming = findViewById(R.id.btnUpcoming);
        btnCompleted = findViewById(R.id.btnCompleted);
        btnCancelled = findViewById(R.id.btnCancelled);
        tvUpcomingCount = findViewById(R.id.tvUpcomingCount);
        tvCompletedCount = findViewById(R.id.tvCompletedCount);
        tvCancelledCount = findViewById(R.id.tvCancelledCount);
        tvEmpty = findViewById(R.id.tvEmpty);
        recyclerView = findViewById(R.id.recyclerView);

        // Manage barbers button
        Button btnManageBarbers = findViewById(R.id.btnManageBarbers);
        btnManageBarbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, ManageBarbersActivity.class));
            }
        });

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        filteredBookings = new ArrayList<>();
        adapter = new AdminBookingAdapter(this, filteredBookings, this);
        recyclerView.setAdapter(adapter);

        // Logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });

        // Filter buttons
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

        btnCancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveTab(btnCancelled);
                currentFilter = "Cancelled";
                filterBookings();
            }
        });

        // Load data
        loadData();
    }

    private void setActiveTab(Button activeBtn) {
        // Reset all buttons
        btnAll.setBackgroundTintList(getResources().getColorStateList(R.color.text_secondary));
        btnUpcoming.setBackgroundTintList(getResources().getColorStateList(R.color.text_secondary));
        btnCompleted.setBackgroundTintList(getResources().getColorStateList(R.color.text_secondary));
        btnCancelled.setBackgroundTintList(getResources().getColorStateList(R.color.text_secondary));

        // Set active button
        activeBtn.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
    }

    private void loadData() {
        // Load statistics
        int upcoming = databaseHelper.getAllBookingsCountByStatus("Upcoming");
        int completed = databaseHelper.getAllBookingsCountByStatus("Completed");
        int cancelled = databaseHelper.getAllBookingsCountByStatus("Cancelled");

        tvUpcomingCount.setText(String.valueOf(upcoming));
        tvCompletedCount.setText(String.valueOf(completed));
        tvCancelledCount.setText(String.valueOf(cancelled));

        // Load all bookings
        allBookings = databaseHelper.getAllBookings();
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
    public void onCompleteClick(Booking booking, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Complete Booking")
                .setMessage("Mark this booking as completed?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    int result = databaseHelper.completeBooking(booking.getId());
                    if (result > 0) {
                        Toast.makeText(this, "Booking marked as completed", Toast.LENGTH_SHORT).show();
                        loadData();
                    } else {
                        Toast.makeText(this, "Failed to update booking", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
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
                        loadData();
                    } else {
                        Toast.makeText(this, "Failed to cancel booking", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}