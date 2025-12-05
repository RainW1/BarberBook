package com.example.barberbook.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barberbook.R;
import com.example.barberbook.database.DatabaseHelper;
import com.example.barberbook.models.User;
import com.example.barberbook.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextInputEditText etFullName, etEmail, etPhone;
    private TextInputEditText etCurrentPassword, etNewPassword, etConfirmNewPassword;
    private Spinner spinnerGender;
    private Button btnSave;

    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize
        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        // Find views
        btnBack = findViewById(R.id.btnBack);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        spinnerGender = findViewById(R.id.spinnerGender);
        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword);
        btnSave = findViewById(R.id.btnSave);

        // Setup gender spinner
        String[] genders = {"Select Gender", "Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        // Load current user data
        loadUserData();

        // Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    private void loadUserData() {
        currentUser = databaseHelper.getUserById(sessionManager.getUserId());

        if (currentUser != null) {
            etFullName.setText(currentUser.getFullName());
            etEmail.setText(currentUser.getEmail());
            etPhone.setText(currentUser.getPhone());

            // Set spinner selection
            String gender = currentUser.getGender();
            if (gender != null) {
                if (gender.equals("Male")) {
                    spinnerGender.setSelection(1);
                } else if (gender.equals("Female")) {
                    spinnerGender.setSelection(2);
                }
            }
        }
    }

    private void saveProfile() {
        String fullName = etFullName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String gender = spinnerGender.getSelectedItem().toString();
        String currentPassword = etCurrentPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmNewPassword = etConfirmNewPassword.getText().toString().trim();

        // Validation
        if (fullName.isEmpty()) {
            etFullName.setError("Full name is required");
            etFullName.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            etPhone.setError("Phone number is required");
            etPhone.requestFocus();
            return;
        }

        if (gender.equals("Select Gender")) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update profile info
        int profileResult = databaseHelper.updateUserProfile(
                sessionManager.getUserId(), fullName, phone, gender);

        if (profileResult <= 0) {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update session with new name
        sessionManager.createLoginSession(
                sessionManager.getUserId(),
                fullName,
                sessionManager.getUserEmail());

        // Check if user wants to change password
        if (!currentPassword.isEmpty() || !newPassword.isEmpty() || !confirmNewPassword.isEmpty()) {
            // All password fields must be filled
            if (currentPassword.isEmpty()) {
                etCurrentPassword.setError("Current password is required");
                etCurrentPassword.requestFocus();
                return;
            }

            if (newPassword.isEmpty()) {
                etNewPassword.setError("New password is required");
                etNewPassword.requestFocus();
                return;
            }

            if (newPassword.length() < 6) {
                etNewPassword.setError("Password must be at least 6 characters");
                etNewPassword.requestFocus();
                return;
            }

            if (!newPassword.equals(confirmNewPassword)) {
                etConfirmNewPassword.setError("Passwords do not match");
                etConfirmNewPassword.requestFocus();
                return;
            }

            // Verify current password
            if (!databaseHelper.verifyPassword(sessionManager.getUserId(), currentPassword)) {
                etCurrentPassword.setError("Current password is incorrect");
                etCurrentPassword.requestFocus();
                return;
            }

            // Update password
            int passwordResult = databaseHelper.updateUserPassword(
                    sessionManager.getUserId(), newPassword);

            if (passwordResult <= 0) {
                Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }
}