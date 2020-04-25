package com.example.job;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.job.Adapter.EventImageAdapter;

public class ViewEventSocialWall extends AppCompatActivity {

    EventImageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_social_wall);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvsocialimgwall);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> tmp = Common.selectedEvent.getPhotos();
        tmp.remove(0);
        adapter = new EventImageAdapter(recyclerView, this, tmp);
        recyclerView.setAdapter(adapter);
    }
}
