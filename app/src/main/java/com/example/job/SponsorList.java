package com.example.job;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.example.job.Model.Sponser;

public class SponsorList extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbUserRef;
    TextView tvsponsornotfound;
    List<Sponser> sponsers;
    com.example.job.Adapter.SponsorList adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_list);
        tvsponsornotfound = findViewById(R.id.tvsponsornotfound);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbUserRef = firebaseDatabase.getReference("Sponsers");
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        assert type != null;
        dbUserRef.orderByChild("category").equalTo(type).addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        sponsers = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            Sponser que = ds.getValue(Sponser.class);
                            sponsers.add(que);

                        }
                        onLoad(sponsers);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


    private void onLoad(List<Sponser> sponserslist) {

        if (sponserslist.size() == 0) {
            tvsponsornotfound.setVisibility(View.VISIBLE);
        } else {
            tvsponsornotfound.setVisibility(View.GONE);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvsponsorlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new com.example.job.Adapter.SponsorList(recyclerView, this, sponserslist);
        recyclerView.setAdapter(adapter);
    }
}
