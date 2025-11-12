package com.example.healthyme.caloriescounter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthyme.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class ApiActivity extends AppCompatActivity {

    String food;
    EditText et;
    TextView tv1, tv2, tv3, tv4, tv5, tv6;
    View v1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        et = findViewById(R.id.editText);
        tv1 = findViewById(R.id.textView13); // Title (e.g., Nutrition Facts)
        tv2 = findViewById(R.id.textView16); // Food name
        tv3 = findViewById(R.id.textView17); // Calories
        tv4 = findViewById(R.id.textView18); // Proteins
        tv5 = findViewById(R.id.textView19); // Carbs
        tv6 = findViewById(R.id.textView20); // Fats (create this in your layout if not present)
    }

    public void calculateClk(View view) {
        food = et.getText().toString();
        if (food.isEmpty()) {
            Toast.makeText(this, "Please enter a food item.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
            new MyAsyncTask().execute();
        }
    }

    /* ##### AsyncTask Subclass ################################################################### */
    public class MyAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String allStrings = "";
            try {
                // Ensure the food input is URL encoded
                String encodedFood = java.net.URLEncoder.encode(food, "UTF-8");

                // Nutritionix API URL for searching by food name
                String urlStr = "https://trackapi.nutritionix.com/v2/natural/nutrients";

                // Open a connection
                URL myUrl = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("x-app-id", "d1e5973c"); // Replace with your App ID
                connection.setRequestProperty("x-app-key", "bacb1af62f0ad3102dcd265c14268c9a"); // Replace with your API Key
                connection.setDoOutput(true);

                // Prepare JSON body
                String jsonInputString = "{\"query\":\"" + food + "\", \"timezone\":\"US/Eastern\"}";

                // Send request
                try (java.io.OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Get the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder stringBuilder = new StringBuilder();

                // Read the API response
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                reader.close();
                allStrings = stringBuilder.toString();
                publishProgress(allStrings);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return allStrings;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            try {
                // Parse the returned JSON response
                JSONObject jsonResponse = new JSONObject(values[0]);
                JSONArray foodsArray = jsonResponse.optJSONArray("foods");

                if (foodsArray != null && foodsArray.length() > 0) {
                    // Get the first food item from the list
                    JSONObject foodItem = foodsArray.getJSONObject(0);

                    String name = foodItem.optString("food_name", "N/A");
                    String servingSize = foodItem.optString("serving_qty", "N/A") + " " +
                            foodItem.optString("serving_unit", "N/A");
                    String calories = foodItem.optString("nf_calories", "N/A");
                    String proteins = foodItem.optString("nf_protein", "N/A");
                    String carbs = foodItem.optString("nf_total_carbohydrate", "N/A");
                    String fats = foodItem.optString("nf_total_fat", "N/A");

                    // Update UI with product details
                    tv1.setText("Nutrition Facts");
                    tv2.setText("Food: " + name);
                    tv3.setText("Serving Size: " + servingSize);
                    tv4.setText("Calories: " + calories + " kcal");
                    tv5.setText("Proteins: " + proteins + " g");
                    tv6.setText("Carbs: " + carbs + " g");

                } else {
                    Toast.makeText(ApiActivity.this, "Food item not found.", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ApiActivity.this, "Error parsing data.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}