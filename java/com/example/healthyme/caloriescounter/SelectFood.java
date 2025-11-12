/**
 * Kah Hau Yap
 *
 * @SelectFood.java
 * After the food is classified by the IBM Watson Visual Recognition API an ArrayList of possible
 * classifications of the food is passed into this Activity. The classifications are displayed in
 * a ListView and when a food item is selected fetch nutritional information from the Nutritionix API.
 * The information is then displayed with the option of saving the item.
 */

package com.example.healthyme.caloriescounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthyme.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class SelectFood extends AppCompatActivity {

    //Nutritionix API endpoint to POST request for nutrition information, and API key verification
    private String API_KEY = "52458920d1de5f031cf765cad4ebdf73";
    private String APP_ID = "09d91a50";
    private String ingredientsURL = "https://trackapi.nutritionix.com/v2/natural/nutrients";

    RequestQueue requestQueue;
    DataBaseHelper db;

    private ListView foodList;
    private double calories, protein, fat, carbohydrates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_food);
        setTitle("Classification Results");
        ActionBar actionBar = getSupportActionBar(); //Set back button on the title bar
//        actionBar.setDisplayHomeAsUpEnabled(true);

        foodList = (ListView) findViewById(R.id.foodList);

        requestQueue = Volley.newRequestQueue(this);
        db = new DataBaseHelper(this); //

        //Initialize an intent to get the ArrayList holding the classification results from Picture activity
        Intent intent = getIntent();
        ArrayList<String> classificationList = intent.getStringArrayListExtra("ArrayList");

        // Check if classificationList is null or empty
        if (classificationList != null && !classificationList.isEmpty()) {
            ArrayAdapter<String> classificationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classificationList); // Bind the ArrayList to an Array Adapter for display
            foodList.setAdapter(classificationAdapter);
        } else {
            // Handle the case when classificationList is null or empty
            // For example, you can show a message or handle this scenario differently.
            new AlertDialog.Builder(this)
                    .setMessage("No food classifications available.")
                    .setPositiveButton("OK", null)
                    .show();
        }

        // Response to user tapping an item on the list
        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selectedItem = (String) parent.getItemAtPosition(position); // Get the item at the position selected

                // If the food is already saved in the Food Table then get nutritional value from the database instead of calling the Nutritionix API
                if (db.foodExists(selectedItem)) {
                    // Create a Food object to store the nutritional information from the database
                    DataBaseHelper.Food food = db.getFood(selectedItem);

                    final int Calories = food.calories;
                    final int Protein = food.protein;
                    final int Fat = food.fat;
                    final int Carbohydrates = food.carbs;

                    AlertDialog.Builder builder;
                    // Determine which alert dialog builder based on API version
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        builder = new AlertDialog.Builder(SelectFood.this, android.R.style.Theme_Material_Dialog_Alert);
                    else
                        builder = new AlertDialog.Builder(SelectFood.this);

                    // Build the dialog message
                    builder.setTitle("*Save " + selectedItem + "?")
                            .setMessage("Calories: " + Calories + "\nProtein: " + Protein + " g\nFat: " + Fat + " g\nCarbohydrates: " + Carbohydrates + " g")
                            .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Save the entry into the user's daily intake
                                    String date = getDate();
                                    if (db.dateExists(date)) { // If there is an entry for that date in the database, update and add food
                                        updateFood(date, Calories, Protein, Fat, Carbohydrates);
                                        startActivity(new Intent(SelectFood.this, DailyPage.class));
                                    } else { // Make new entry in db if one doesn't exist
                                        db.addEntry(date);
                                        updateFood(date, Calories, Protein, Fat, Carbohydrates);
                                    }
                                    db.addFoodHistory(date, selectedItem, Calories, Protein, Fat, Carbohydrates); // Add the food entry into the user history
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing, go back
                                }
                            }).show();
                }
                // Other logic for API call if food is not already in the database
            }
        });

    }

    //Updates the database adding the nutrition from the food to the daily total
    public void updateFood (String date, double calories, double protein, double fat, double carbohydrates) {
        //Convert double values to int to store in database
        int Calories, Protein, Fat, Carbohydrates, Weight;
        Calories = (int) Math.round(calories);
        Protein= (int) Math.round(protein);
        Fat = (int) Math.round(fat);
        Carbohydrates = (int) Math.round(carbohydrates);
        Weight = db.getWeight("0"); //Get the default weight from database

        //Add the food's nutritional values to the daily total
        Calories += db.getCalories(date);
        Protein += db.getProtein(date);
        Fat += db.getFat(date);
        Carbohydrates += db.getCarbs(date);

        db.updateNutrition(date, Calories, Protein, Fat, Carbohydrates, Weight); //Update the nutrition for the day with food added
    }

    //Parse a JSON string from the Nutritionix API to extract information from the API result, and put information into a hashmap
    public HashMap<String, Double> parseJSON (String JSONstring) throws JSONException {
        HashMap<String, Double> hashMap = new HashMap<String, Double>();
        Double calories, protein, fat, carbohydrates;

        //Parse the JSON string to extract nutritional information
        JSONObject jo = new JSONObject(JSONstring);
        JSONArray ja = jo.getJSONArray("foods");
        JSONObject foods = ja.getJSONObject(0);

        calories = foods.getDouble("nf_calories");
        protein = foods.getDouble("nf_protein");
        fat = foods.getDouble("nf_total_fat");
        carbohydrates = foods.getDouble("nf_total_carbohydrate");

        hashMap.put("calories", calories);
        hashMap.put("protein", protein);
        hashMap.put("fat", fat);
        hashMap.put("carbohydrates", carbohydrates);
        return hashMap;
    }

    //Returns the current date
    public String getDate() {
        Date date = Calendar.getInstance().getTime(); //Get the current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy"); //Set up a format to convert the date
        String formattedDate = dateFormat.format(date); //Convert date into the format
        return formattedDate;
    }

    //Go back to the previous activity on back arrow press
    /*public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), Picture.class);
        startActivityForResult(myIntent, 0);
        return true;
    }*/
}
