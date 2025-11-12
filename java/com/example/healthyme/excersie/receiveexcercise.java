package com.example.healthyme.excersie;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthyme.R;

import java.io.IOException;

public class receiveexcercise extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiveexcercise);
        WebView webView = findViewById(R.id.webview);
        String videoUrl = getIntent().getStringExtra("videoUrl");

        // Debug log to verify the URL
        Log.d("WebViewDebug", "Video URL: " + videoUrl);

        // WebView Settings
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        // Load URL
        if (videoUrl != null && !videoUrl.isEmpty()) {
            webView.loadUrl(videoUrl);
        } else {
            Log.e("WebViewDebug", "No URL provided!");
        }
    }
}