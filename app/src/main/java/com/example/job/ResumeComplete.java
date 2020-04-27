package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ResumeComplete extends AppCompatActivity {


    TextView compfullname, compgender, compemail, compphone, compdob, compaddress, compskill, compobjecttive;
    ImageView comppersonal_image;
    TextView etsscname, etsscboard, etsscper;
    TextView ethscname, ethscboard, ethscper;
    TextView etugname, etugboard, etugper;
    TextView etpgname, etpgboard, etpgper;
    TextView compproyear, compprodesc, compproname;
    TextView compcompanyname, compdesignation, compdescription, compstartdate, compenddate, comptype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_complete);


        compproyear = findViewById(R.id.compproyear);
        compprodesc = findViewById(R.id.compprodesc);
        compproname = findViewById(R.id.compproname);

        compproyear.setText(Common.userresume.getProjectyear());
        compprodesc.setText(Common.userresume.getProjectdescription());
        compproname.setText(Common.userresume.getProjectname());

        compstartdate = findViewById(R.id.compstartdate);
        compdesignation = findViewById(R.id.compdesignation);
        comptype = findViewById(R.id.comptype);
        compenddate = findViewById(R.id.compenddate);
        compdescription = findViewById(R.id.compdescription);
        compcompanyname = findViewById(R.id.compcompanyname);
        compfullname = findViewById(R.id.compfullname);
        compgender = findViewById(R.id.compgender);
        compemail = findViewById(R.id.compemail);
        compphone = findViewById(R.id.compphone);
        compdob = findViewById(R.id.compdob);
        compaddress = findViewById(R.id.compaddress);
        compobjecttive = findViewById(R.id.compobjecttive);
        compskill = findViewById(R.id.compskill);
        comppersonal_image = findViewById(R.id.comppersonal_image);
        try {

            if (!Common.userresume.getUrl().equalsIgnoreCase("")) {
//                comppersonal_image = Common.userresume.getUrl();
                Picasso.get().load(Common.userresume.getUrl()).into(comppersonal_image);
            }
        } catch (Exception e) {
        }

        if (Common.currentuser1.getType().equals("Company")) {
            findViewById(R.id.editresume).setVisibility(View.GONE);

        }

        compfullname.setText(Common.userresume.getFullname());
        compgender.setText(Common.userresume.getGender());
        compemail.setText(Common.userresume.getEmail());
        compphone.setText(Common.userresume.getPhonenumber());
        compdob.setText(Common.userresume.getDob());
        compaddress.setText(Common.userresume.getLocation());
        compobjecttive.setText(Common.userresume.getObjective());
        compskill.setText(Common.userresume.getSkill());


        etsscname = findViewById(R.id.sscname);
        etsscboard = findViewById(R.id.sscboard);
        etsscper = findViewById(R.id.sscper);


        ethscname = findViewById(R.id.hscname);
        ethscboard = findViewById(R.id.hscboard);
        ethscper = findViewById(R.id.hscper);

        etugname = findViewById(R.id.ugname);
        etugboard = findViewById(R.id.ugboard);
        etugper = findViewById(R.id.ugper);

        etpgname = findViewById(R.id.pgname);
        etpgboard = findViewById(R.id.pgboard);
        etpgper = findViewById(R.id.pgper);


        compcompanyname.setText(Common.userresume.getExpcompanyname());
        compdescription.setText(Common.userresume.getExpdescription());
        compstartdate.setText(Common.userresume.getExpstartdate());
        compenddate.setText(Common.userresume.getExpenddate());
        compdesignation.setText(Common.userresume.getExpdesignation());
        comptype.setText(Common.userresume.getExttype());


        etsscname.setText(Common.userresume.getSscname());
        etsscboard.setText(Common.userresume.getSscboard());
        etsscper.setText(Common.userresume.getSscper());


        ethscname.setText(Common.userresume.getHscname());
        ethscboard.setText(Common.userresume.getHscboard());
        ethscper.setText(Common.userresume.getHscper());


        etpgname.setText(Common.userresume.getPgname());
        etpgboard.setText(Common.userresume.getPgboard());
        etpgper.setText(Common.userresume.getPgper());


        etugname.setText(Common.userresume.getUgname());
        etugboard.setText(Common.userresume.getUgboard());
        etugper.setText(Common.userresume.getUgper());

        findViewById(R.id.editresume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResumeComplete.this, Resume1.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
