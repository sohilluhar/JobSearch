package com.example.job;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class SponserCategory extends AppCompatActivity {

    CardView cvsponsortech;
    CardView cvsponsorsport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser_category);

        cvsponsortech = findViewById(R.id.cvsponsortech);
        cvsponsorsport = findViewById(R.id.cvsponsorsport);


        cvsponsortech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SponserCategory.this, SponsorList.class);
                intent.putExtra("type", "Technical");
                startActivity(intent);
                finish();
            }
        });
        cvsponsorsport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SponserCategory.this, SponsorList.class);
                intent.putExtra("type", "Sport");
                startActivity(intent);
                finish();
            }
        });
    }
}
