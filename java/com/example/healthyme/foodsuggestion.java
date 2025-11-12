package com.example.healthyme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class foodsuggestion extends AppCompatActivity {
    private EditText etAge;
    private TextView tvSuggestion;
    private Button btnSuggest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodsuggestion);
        etAge = findViewById(R.id.etAge);
        tvSuggestion = findViewById(R.id.tvSuggestion);
        btnSuggest = findViewById(R.id.btnSuggest);

        btnSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ageText = etAge.getText().toString().trim();
                if (ageText.isEmpty()) {
                    etAge.setError("Please enter age");
                    return;
                }

                int age = Integer.parseInt(ageText);
                String suggestion = getSuggestionByAge(age);
                tvSuggestion.setText(suggestion);
            }
        });
    }

    private String getSuggestionByAge(int age) {
        if (age < 1) {
            return "Breast milk is best. If needed, use iron-fortified infant formula. Avoid solid food.";
        } else if (age < 2) {
            return "Breast milk, mashed fruits like bananas and apples, pureed vegetables, and cereals.";
        } else if (age <= 5) {
            return "Soft foods: mashed potatoes, boiled vegetables, porridge, milk, and small portions of rice or bread.";
        } else if (age <= 9) {
            return "A mix of proteins and carbs: milk, rice, eggs, veggies, fruits, whole grain bread, and snacks like yogurt.";
        } else if (age <= 12) {
            return "Balanced diet: cereal, milk, eggs, green leafy vegetables, seasonal fruits, and some nuts.";
        } else if (age <= 15) {
            return "Energy-boosting foods: pulses, lean meat, eggs, fruits, milkshakes, and whole grains to support growth.";
        } else if (age <= 18) {
            return "High-protein and calcium-rich diet: eggs, meat, paneer, dairy, lentils, fruits, and vegetables.";
        } else if (age <= 25) {
            return "Healthy adult diet: include oats, brown rice, lean meats, vegetables, fruits, and avoid junk food.";
        } else if (age <= 35) {
            return "Focus on metabolism-friendly foods: whole grains, lean proteins, nuts, green tea, and regular hydration.";
        } else if (age <= 45) {
            return "Antioxidant-rich foods: berries, green tea, fish, leafy vegetables, and less oily food.";
        } else if (age <= 60) {
            return "Low-fat and high-fiber foods: salads, oatmeal, lentils, vegetables, grilled chicken/fish.";
        } else if (age <= 75) {
            return "Soft and easy-to-digest foods: soups, khichdi, whole grains, well-cooked veggies, and plenty of fluids.";
        } else if (age <= 85) {
            return "Focus on digestion and immunity: porridge, stewed fruits, soups, curd, rice, and cooked vegetables.";
        } else {
            return "Very soft, easily digestible meals: pureed soups, mashed vegetables, oats, bananas, and fluids like coconut water.";
        }
    }

}