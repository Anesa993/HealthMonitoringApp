package com.example.healthyme.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthyme.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CaloriesCalculate extends AppCompatActivity {
    private TextInputEditText edtHeight, edtWeight, edtAge;
    private Spinner spinnerActivityLevel;
    private TextView tvResult;
    private Button btnCalculate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_calculate);

        // Initialize views
        edtHeight = findViewById(R.id.edt_height);
        edtWeight = findViewById(R.id.edt_weight);
        edtAge = findViewById(R.id.edt_age);
        spinnerActivityLevel = findViewById(R.id.spinner_activity_level);
        tvResult = findViewById(R.id.tv_result);
        btnCalculate = findViewById(R.id.btn_calculate);

        // Set up activity level dropdown (Spinner)
        String[] activityLevels = {"Sedentary", "Lightly Active", "Moderately Active", "Very Active"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, activityLevels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivityLevel.setAdapter(adapter);

        // Calculate button listener
        btnCalculate.setOnClickListener(v -> calculateCalories());
    }

    private void calculateCalories() {
        String heightStr = Objects.requireNonNull(edtHeight.getText()).toString();
        String weightStr = Objects.requireNonNull(edtWeight.getText()).toString();
        String ageStr = Objects.requireNonNull(edtAge.getText()).toString();
        String activityLevel = spinnerActivityLevel.getSelectedItem().toString();

        if (heightStr.isEmpty() || weightStr.isEmpty() || ageStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float height = Float.parseFloat(heightStr);
            float weight = Float.parseFloat(weightStr);
            int age = Integer.parseInt(ageStr);

            if (height <= 0 || weight <= 0 || age <= 0) {
                Toast.makeText(this, "Height, weight, and age must be greater than zero.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simplified calorie calculation formula (BMR * activity level multiplier)
            float bmr = (10 * weight) + (6.25f * height) - (5 * age) + 5; // Male BMR (for female, subtract 161 instead of adding 5)
            float multiplier = getActivityMultiplier(activityLevel);
            float calories = bmr * multiplier;

            tvResult.setText(String.format("Your recommended daily calorie intake is: %.0f kcal", calories));
            tvResult.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
        }
    }

    private float getActivityMultiplier(String activityLevel) {
        switch (activityLevel) {
            case "Lightly Active":
                return 1.375f;
            case "Moderately Active":
                return 1.55f;
            case "Very Active":
                return 1.725f;
            default:
                return 1.2f; // Sedentary
        }
    }
}
