package com.example.job;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SponsorDetails extends AppCompatActivity {

    TextView tvSponsorname;
    TextView tvSponsorDescription;
    TextView tvsponsortype;
    TextView tvsponsoremail;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_details);
        tvSponsorname = findViewById(R.id.tvSponsorname);
        tvSponsorDescription = findViewById(R.id.tvSponsorDescription);
        tvsponsoremail = findViewById(R.id.tvsponsoremail);
        imageView2 = findViewById(R.id.imageView2);

        try {
            Glide.with(this).load(Common.selectedSponsor.getImg()).into(imageView2);

        } catch (Exception e) {
            Log.e("img", e.toString());
        }

        tvSponsorname.setText(Common.selectedSponsor.getName());
        tvSponsorDescription.setText(Common.selectedSponsor.getDescription());
        tvsponsoremail.setText(Common.selectedSponsor.getEmailid());

        tvsponsoremail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + Common.selectedSponsor.getEmailid()));
                startActivity(intent);
            }
        });

    }
}
