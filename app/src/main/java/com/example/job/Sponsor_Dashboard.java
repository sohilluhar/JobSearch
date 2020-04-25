package com.example.job;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Sponsor_Dashboard extends AppCompatActivity {

    CardView cvSponsorViewEvent;
    CardView cardviewLogout;
    CardView cvSponsorProfile;

    TextView tvmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor__dashboard);
        tvmsg = (TextView) findViewById(R.id.welcomemsgorg);

        cvSponsorViewEvent = (CardView) findViewById(R.id.cvSponsorViewEvent);
        cardviewLogout = (CardView) findViewById(R.id.scardviewLogout);
        cvSponsorProfile = (CardView) findViewById(R.id.cvSponsorProfile);

//        tvmsg.setText("Hey");
        tvmsg.setText("Hey ");

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

                Intent intent = new Intent(Sponsor_Dashboard.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        cvSponsorViewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sponsor_Dashboard.this, UserDashboard.class);
                startActivity(intent);

            }
        });
        cvSponsorProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sponsor_Dashboard.this, SponsorProfile.class);
                startActivity(intent);

            }
        });

//        tvmsg.setText("Hey, " + Common.currentuser.getName());
    }

}