package com.example.healthyme.Camscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.healthyme.R;

import retrofit2.Retrofit;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CamScanner extends AppCompatActivity {
    private Button buttonScan,Picscanner;
    private TextView textViewResult;
    private ProgressBar progressBar;
    private Retrofit retrofit;
    private OpenFoodFactsApi openFoodFactsApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_scanner);
        buttonScan = findViewById(R.id.buttonScan);
        textViewResult = findViewById(R.id.textViewResult);
        progressBar = findViewById(R.id.progressBar);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://world.openfoodfacts.org/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openFoodFactsApi = retrofit.create(OpenFoodFactsApi.class);

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(CamScanner.this, ScanBarcode.class);
                startActivity(intent);*/
                IntentIntegrator integrator = new IntentIntegrator(CamScanner.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan a barcode");
                integrator.setCameraId(0);
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(true);
                integrator.initiateScan();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() != null) {
                String barcode = result.getContents();
                textViewResult.setText("Please wait...");
                progressBar.setVisibility(View.VISIBLE);
                fetchCalories(barcode);
            } else {
                textViewResult.setText("No result found");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void fetchCalories(String barcode) {
        Call<ProductResponse> call = openFoodFactsApi.getProduct(barcode);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    ProductResponse product = response.body();
                    String productName = product.getProduct().getProductName();
                    String calories = product.getProduct().getNutriments().getEnergyKcal() + " kcal";

                    textViewResult.setText("Product: " + productName + "\nCalories: " + calories);
                } else {
                    textViewResult.setText("Product not found");
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                textViewResult.setText("Failed to fetch data");
            }
        });
    }
}

