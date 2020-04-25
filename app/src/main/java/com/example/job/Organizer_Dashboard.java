package com.example.job;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Organizer_Dashboard extends AppCompatActivity {

    CardView event_card, ngo_all_events, create_group, cvViewSponsorCategory, cardviewLogout;
    TextView tvmsg;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer__dashboard);
        tvmsg = (TextView) findViewById(R.id.welcomemsgorg);

        event_card = (CardView) findViewById(R.id.view_resume);
        ngo_all_events = (CardView) findViewById(R.id.create_resume);
        create_group = (CardView) findViewById(R.id.cvCreateGroup);
        cardviewLogout = (CardView) findViewById(R.id.cardviewLogout);
        cvViewSponsorCategory = (CardView) findViewById(R.id.cvViewSponsorCategory);

//        tvmsg.setText("Hey");
        try {
            tvmsg.setText("Hey, " + Common.currentuser1.getName());
        } catch (Exception ignored) {
        }

        create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Organizer_Dashboard.this, CreateGroup.class);
                startActivity(intent);

            }
        });

        cardviewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.remove("userphone");
                editor.remove("password");
                editor.apply();

                Common.currentuser = null;

                Intent intent = new Intent(Organizer_Dashboard.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        event_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Organizer_Dashboard.this, CreateEvent.class);
                startActivity(intent);

            }
        });
        ngo_all_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Organizer_Dashboard.this, Organizer_ViewEventAll.class);
                startActivity(intent);

            }
        });
        cvViewSponsorCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Organizer_Dashboard.this, SponserCategory.class);
                startActivity(intent);

            }
        });

//        tvmsg.setText("Hey, " + Common.currentuser.getName());
    }

}
