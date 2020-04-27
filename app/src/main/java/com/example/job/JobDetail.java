package com.example.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.job.Model.Annoucement;
import com.example.job.Model.Job;
import com.example.job.Model.JobApply;
import com.example.job.Model.User1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JobDetail extends AppCompatActivity {


    TextView jdname;
    TextView jdcompanyname;
    TextView itemjobexperience;
    TextView itemjoblocation;
    TextView itemjobskill;
    TextView jdsalary;
    TextView itemjobtype;
    TextView itemjobdesignation;
    TextView itemjobvac;
    TextView jddescription;
    TextView jdaboutcompany;
    TextView jbchsc;
    TextView jbcug;
    TextView jbcpg;
    TextView jdcontactname;
    TextView jdcompanynumber;
    TextView sss;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference userref, jobapplyref;
    List<JobApply> jobApplies;
    String status;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            Toast.makeText(this, "Search Click", Toast.LENGTH_SHORT).show();
            finish();
        }
//

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Job jb = Common.selectedJob;
        firebaseDatabase = FirebaseDatabase.getInstance();
        userref = firebaseDatabase.getReference("users/" + Common.currentuser1.getPhonenumber());
        jobapplyref = firebaseDatabase.getReference("JobApply/" + Common.selectedJob.getKey());

        ImageView img = findViewById(R.id.jdPhoto);

        jdname = findViewById(R.id.jdname);
        sss = findViewById(R.id.jobstatus);
        jdname.setText(jb.getName());
        ///////////////////////////////////
        try {

            if (Common.currentuser1.getJobapplied().contains(jb.getKey())) {
                findViewById(R.id.applyjob).setVisibility(View.GONE);
                findViewById(R.id.alreadyapplied).setVisibility(View.VISIBLE);
                getstatus();

            }

        } catch (Exception i) {
        }
        if (Common.currentuser1.getType().equals("Company")) {
            findViewById(R.id.applyjob).setVisibility(View.GONE);
            findViewById(R.id.viewapp).setVisibility(View.VISIBLE);


        }

        jdcompanynumber = findViewById(R.id.jdcompanynumber);
        jdcompanynumber.setText(jb.getContactnumber());

        jdcompanynumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendmsgtowhatsapp = new Intent(Intent.ACTION_VIEW);
                //TODO:: Replace msg text
                String msg = "Hey!";
                sendmsgtowhatsapp.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + jdcompanynumber.getText().toString() + "&text=" + msg));
                startActivity(sendmsgtowhatsapp);
            }
        });
        jdcontactname = findViewById(R.id.jdcontactname);
        jdcontactname.setText(jb.getContactname());
        jbcpg = findViewById(R.id.jbcpg);
        jbcpg.setText(jb.getCriteriapostgraduat());
        jbcug = findViewById(R.id.jbcug);
        jbcug.setText(jb.getCriteriagraduate());
        jbchsc = findViewById(R.id.jbchsc);
        jbchsc.setText(jb.getCriteriahsc());

        getSupportActionBar().setTitle(jb.getName());
        jdaboutcompany = findViewById(R.id.jdaboutcompany);
        jdaboutcompany.setText(jb.getCompanyabout());
        jddescription = findViewById(R.id.jddescription);
        jddescription.setText(jb.getDetails());
        itemjobvac = findViewById(R.id.itemjobvac);
        itemjobvac.setText(jb.getVacancy() + " Vacancy");
        itemjobdesignation = findViewById(R.id.itemjobdesignation);
        itemjobdesignation.setText(jb.getDesignation());
        jdcompanyname = findViewById(R.id.jdcompanyname);
        jdcompanyname.setText(jb.getCompanyname());
        itemjobexperience = findViewById(R.id.itemjobexperience);
        itemjobexperience.setText(jb.getWorkexperience());
        itemjoblocation = findViewById(R.id.itemjoblocation);
        itemjoblocation.setText(jb.getLocation());
        itemjobskill = findViewById(R.id.itemjobskill);
        itemjobskill.setText(jb.getSkill());
        jdsalary = findViewById(R.id.jdsalary);
        jdsalary.setText(jb.getSalary());
        itemjobtype = findViewById(R.id.itemjobtype);
        itemjobtype.setText(jb.getJobtype());


        Glide.with(this).load(jb.getImg()).centerCrop().into(img);


        findViewById(R.id.applyjob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.userresume.getStatus().equals("100"))

                    applyforjob();
                else {
                    Toast.makeText(JobDetail.this, "Resume Not Created!! Create resume first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.viewlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JobDetail.this, ViewListofPPlGoing.class));
            }
        });

    }

    private void getstatus() {
        jobapplyref.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jobApplies = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

//                    Log.e("ann",announcements.toString());
                    JobApply tmp = dataSnapshot1.getValue(JobApply.class);
                    assert tmp != null;
                    if (tmp.getPhone().equals(Common.currentuser1.getPhonenumber())) {
                        sss.setText("Status : " + tmp.getStatus());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    void applyforjob() {

        jobapplyref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jobApplies = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

//                    Log.e("ann",announcements.toString());
                    JobApply tmp = dataSnapshot1.getValue(JobApply.class);
                    assert tmp != null;
                    jobApplies.add(tmp);
                }
                JobApply jobApply = new JobApply(Common.currentuser1.getPhonenumber(),
                        "Pending");
                jobApplies.add(jobApply);
                jobapplyref.setValue(jobApplies);

                updateuser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateuser() {
        User1 tmp = Common.currentuser1;
        List<String> t = tmp.getJobapplied();
        if (t == null) {
            t = new ArrayList<>();
        }
        t.add(Common.selectedJob.getKey() + "");
        tmp.setJobapplied(t);
        userref.setValue(tmp);
        Toast.makeText(this, "Applied Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
