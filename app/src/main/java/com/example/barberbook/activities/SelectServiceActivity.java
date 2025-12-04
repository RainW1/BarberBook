package com.example.barberbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barberbook.R;

import java.util.ArrayList;

public class SelectServiceActivity extends AppCompatActivity {

    private ImageView btnBack;
    private CheckBox cbHaircut, cbShaving, cbHairWash, cbHairColoring;
    private TextView tvTotal;
    private Button btnNext;

    private int totalPrice = 0;
    private ArrayList<String> selectedServices = new ArrayList<>();

    // Prices
    private static final int PRICE_HAIRCUT = 35000;
    private static final int PRICE_SHAVING = 15000;
    private static final int PRICE_HAIR_WASH = 20000;
    private static final int PRICE_HAIR_COLORING = 100000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);

        // Find views
        btnBack = findViewById(R.id.btnBack);
        cbHaircut = findViewById(R.id.cbHaircut);
        cbShaving = findViewById(R.id.cbShaving);
        cbHairWash = findViewById(R.id.cbHairWash);
        cbHairColoring = findViewById(R.id.cbHairColoring);
        tvTotal = findViewById(R.id.tvTotal);
        btnNext = findViewById(R.id.btnNext);

        // Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Checkbox listeners
        cbHaircut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotal("Haircut", PRICE_HAIRCUT, isChecked);
            }
        });

        cbShaving.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotal("Shaving", PRICE_SHAVING, isChecked);
            }
        });

        cbHairWash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotal("Hair Wash", PRICE_HAIR_WASH, isChecked);
            }
        });

        cbHairColoring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotal("Hair Coloring", PRICE_HAIR_COLORING, isChecked);
            }
        });

        // Next button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedServices.isEmpty()) {
                    android.widget.Toast.makeText(SelectServiceActivity.this,
                            "Please select at least one service", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }

                // Pass data to next activity
                Intent intent = new Intent(SelectServiceActivity.this, SelectBarberActivity.class);
                intent.putStringArrayListExtra("selectedServices", selectedServices);
                intent.putExtra("totalPrice", totalPrice);
                startActivity(intent);
            }
        });
    }

    private void updateTotal(String serviceName, int price, boolean isChecked) {
        if (isChecked) {
            totalPrice += price;
            selectedServices.add(serviceName);
        } else {
            totalPrice -= price;
            selectedServices.remove(serviceName);
        }
        tvTotal.setText("Rp " + String.format("%,d", totalPrice).replace(",", "."));
    }
}
