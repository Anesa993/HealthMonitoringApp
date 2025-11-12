package com.example.healthyme.excersie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.healthyme.R;

public class excercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise);

        // CardViews for each exercise
        CardView pushUpsCard = findViewById(R.id.card_push_ups);
        CardView squatsCard = findViewById(R.id.card_squats);
        CardView lungesCard = findViewById(R.id.card_lunges);
        CardView plankCard = findViewById(R.id.card_plank);
        CardView burpeesCard = findViewById(R.id.card_burpees);

        // Set click listeners for each card
        pushUpsCard.setOnClickListener(v -> openExerciseVideo("https://www.youtube.com/watch?v=IODxDxX7oi4"));
        squatsCard.setOnClickListener(v -> openExerciseVideo("https://www.youtube.com/watch?v=aclHkVaku9U"));
        lungesCard.setOnClickListener(v -> openExerciseVideo("https://www.youtube.com/watch?v=QOVaHwm-Q6U"));
        plankCard.setOnClickListener(v -> openExerciseVideo("https://www.youtube.com/watch?v=pSHjTRCQxIw"));
        burpeesCard.setOnClickListener(v -> openExerciseVideo("https://www.youtube.com/watch?v=TU8QYVW0gDU"));
    }

    // Method to open the exercise video in the WebView activity
    private void openExerciseVideo(String videoUrl) {
        Intent intent = new Intent(excercise.this, receiveexcercise.class);
        intent.putExtra("videoUrl", videoUrl);
        startActivity(intent);
    }
}
