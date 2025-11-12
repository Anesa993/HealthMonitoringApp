

package com.example.healthyme.caloriescounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthyme.R;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;




public class Homepage extends AppCompatActivity {

    private Boolean firstTime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //Hide the title bar
        ActionBar actionBar = getSupportActionBar();
       // actionBar.hide();


        //Create the profile button
        Button profileButton = (Button) findViewById(R.id.profileButton);
        //When button pressed start a new page to the profile screen
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent=new Intent(Homepage.this,MacrosCalculator.class);
                startActivity(intent);*/

            if (isFirstTime()) //If it is the first install for the app then send to the macro calculator
                startActivity(new Intent(Homepage.this, MacrosCalculator.class));
            else //If existing user then send to the daily homepage instead
                startActivity(new Intent(Homepage.this, DailyPage.class));
            }
        });

        //Create the take a picture button
      //  Button pictureButton = (Button) findViewById(R.id.pictureButton);
        //When button pressed start a new page to the picture screen
        /*pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the profile activity
                startActivity(new Intent(Homepage.this, Picture.class));
            }
        });*/
    }

    //Check if it is the first time the user has opened the app
    private boolean isFirstTime() {
        if (firstTime == null) {
            SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean("firstTime", true);
            if (firstTime) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.apply();
            }
        }
        return firstTime;
    }


}