package com.example.barberbook.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barberbook.R;
import com.example.barberbook.adapters.AdminBarberAdapter;
import com.example.barberbook.database.DatabaseHelper;
import com.example.barberbook.models.Barber;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ManageBarbersActivity extends AppCompatActivity implements AdminBarberAdapter.OnBarberActionListener {

    private ImageView btnBack;
    private RecyclerView recyclerView;

    private DatabaseHelper databaseHelper;
    private AdminBarberAdapter adapter;
    private List<Barber> barberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_barbers);

        // Initialize
        databaseHelper = new DatabaseHelper(this);

        // Find views
        btnBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.recyclerView);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadBarbers();

        // Back button
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadBarbers() {
        barberList = databaseHelper.getAllBarbers();
        adapter = new AdminBarberAdapter(this, barberList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAvailabilityChanged(Barber barber, boolean isAvailable) {
        int result = databaseHelper.updateBarberAvailability(barber.getId(), isAvailable);

        if (result > 0) {
            String status = isAvailable ? "available" : "not available";
            Toast.makeText(this, barber.getName() + " is now " + status, Toast.LENGTH_SHORT).show();
            loadBarbers(); // Refresh list
        } else {
            Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBarberClick(Barber barber, int position) {
        showEditDialog(barber);
    }

    private void showEditDialog(Barber barber) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_barber, null);

        TextInputEditText etName = dialogView.findViewById(R.id.etBarberName);
        TextInputEditText etRating = dialogView.findViewById(R.id.etBarberRating);
        TextInputEditText etExperience = dialogView.findViewById(R.id.etBarberExperience);
        SwitchCompat switchAvailable = dialogView.findViewById(R.id.switchAvailable);

        // Set current values
        etName.setText(barber.getName());
        etRating.setText(String.valueOf(barber.getRating()));
        etExperience.setText(String.valueOf(barber.getExperience()));
        switchAvailable.setChecked(barber.isAvailable());

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String name = etName.getText().toString().trim();
                    String ratingStr = etRating.getText().toString().trim();
                    String expStr = etExperience.getText().toString().trim();
                    boolean available = switchAvailable.isChecked();

                    // Validation
                    if (name.isEmpty() || ratingStr.isEmpty() || expStr.isEmpty()) {
                        Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double rating = Double.parseDouble(ratingStr);
                    int experience = Integer.parseInt(expStr);

                    // Validate rating
                    if (rating < 1 || rating > 5) {
                        Toast.makeText(this, "Rating must be between 1-5", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Update barber
                    int result = databaseHelper.updateBarberInfo(barber.getId(), name, rating, experience, available);

                    if (result > 0) {
                        Toast.makeText(this, "Barber updated successfully", Toast.LENGTH_SHORT).show();
                        loadBarbers();
                    } else {
                        Toast.makeText(this, "Failed to update barber", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}