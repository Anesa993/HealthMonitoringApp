package com.example.healthyme.vediosession;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.healthyme.R;

import java.util.ArrayList;
import java.util.List;

public class vediosessionScreen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private List<VideoItem> videoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vediosession_screen);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add 10 YouTube video links
        videoList = new ArrayList<>();
        videoList.add(new VideoItem("Health Tip 1", "https://youtube.com/shorts/0gLEAyN7odo?si=bTF6FVsMUcTs9X6D"));
        videoList.add(new VideoItem("Health Tip 2", "https://youtube.com/shorts/J_RBHj60zy4?si=NKT8OdzfK6Ymki39"));
        videoList.add(new VideoItem("Health Tip 3", "https://youtu.be/cQhLCDxeZnQ?si=AI9inijmSiV9z9to"));
        videoList.add(new VideoItem("Health Tip 4", "https://youtu.be/sfhs_ouun7g?si=rPK7g2dGtq0jeJjq"));
        videoList.add(new VideoItem("Health Tip 5", "https://youtu.be/jhatrxanPjw?si=I7Jg0UH7P63HBcr7"));
        videoList.add(new VideoItem("Health Tip 6", "https://youtu.be/zlPcxGhzq_c?si=mxFSdja7sE-mW6yK"));
        videoList.add(new VideoItem("Health Tip 7", "https://youtube.com/shorts/G258Ubz_0gs?si=kQtmd2Oy9XDWYMe_"));
        videoList.add(new VideoItem("Health Tip 8", "https://youtube.com/shorts/Cbs6k8VUuNI?si=79szVWsti5YaLhzl"));
        videoList.add(new VideoItem("Health Tip 9", "https://www.youtube.com/embed/7wtfhZwyrcc"));
        videoList.add(new VideoItem("Health Tip 10", "https://www.youtube.com/embed/hTWKbfoikeg"));

        videoAdapter = new VideoAdapter(videoList);
        recyclerView.setAdapter(videoAdapter);
    }


}