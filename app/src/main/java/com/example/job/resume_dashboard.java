package com.example.job;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class resume_dashboard extends AppCompatActivity {

    CardView event_card, ngo_all_events, create_group, cvViewSponsorCategory, cardviewLogout,cvcreate_resume,cvview_resume;
    TextView tvmsg;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    //     toolbar.setTitle("Shop");
                    Intent intent = new Intent(resume_dashboard.this, UserDashboard.class);
                    startActivity(intent);
                    finish();
//
//                    Toast.makeText(Home.this, "Home Click", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_search:
//                    Intent intent = new Intent(Home.this, User_Search_Event.class);
//                    startActivity(intent);
//                    finish();
//                    Toast.makeText(Home.this, "Search Click", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_upcomingEvent:
                    Intent intent1 = new Intent(resume_dashboard.this, UserGoingEvent.class);
                    startActivity(intent1);
                    finish();
                    return true;
                case R.id.navigation_resume:
                    //   Intent intent3= new Intent(activity_resume_extracuricular.this, resume_dashboard.class);
                    //    startActivity(intent3);
                    //  finish();
                    return true;
                case R.id.navigation_profile:
                    Intent intent2 = new Intent(resume_dashboard.this, UserProfile.class);
                    startActivity(intent2);
                    finish();
                    return true;
            }
            return true;
        }
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_dashboard);
        tvmsg = (TextView) findViewById(R.id.welcomemsgorg);
        cvcreate_resume=(CardView) findViewById(R.id.create_resume);
        cvview_resume=(CardView) findViewById(R.id.view_resume);
        event_card = (CardView) findViewById(R.id.view_resume);
        ngo_all_events = (CardView) findViewById(R.id.create_resume);
        create_group = (CardView) findViewById(R.id.cvCreateGroup);
        cardviewLogout = (CardView) findViewById(R.id.cardviewLogout);
        cvViewSponsorCategory = (CardView) findViewById(R.id.cvViewSponsorCategory);

//        tvmsg.setText("Hey");
//        try {
//            tvmsg.setText("Hey, " + Common.currentuser.getName());
//        } catch (Exception i) {
//        }

        /*create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resume_dashboard.this, CreateGroup.class);
                startActivity(intent);

            }
        });

      <  cardviewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.remove("userphone");
                editor.remove("password");
                editor.apply();

                Common.currentuser = null;

                Intent intent = new Intent(resume_dashboard.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        event_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resume_dashboard.this, CreateEvent.class);
                startActivity(intent);

            }
        });
        ngo_all_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resume_dashboard.this, Organizer_ViewEventAll.class);
                startActivity(intent);

            }
        });
        cvViewSponsorCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resume_dashboard.this, SponserCategory.class);
                startActivity(intent);

            }
        });*/
        cvcreate_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resume_dashboard.this, activity_resume_personal.class);
                startActivity(intent);

            }
        });
        cvview_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resume_dashboard.this, resume_deatails.class);
                startActivity(intent);

            }
        });

//        tvmsg.setText("Hey, " + Common.currentuser.getName());
    }

}
