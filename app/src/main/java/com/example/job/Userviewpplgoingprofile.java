package com.example.job;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

public class Userviewpplgoingprofile extends AppCompatActivity {
    TextView tvusername, tvuserphone, tvusergoingconnection, tvuserabout;
    TextView tvuserwork, tvusermail, tvuseraddress, tvsendreqbussinessmsg;
    Button btnsendrequest, btnreqsenddone, btnacceptconnection, btnrejectconnection;
    LinearLayout llBussinessProfile;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref1, ref2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userviewpplgoingprofile);

        tvusername = findViewById(R.id.tvusergoingname);
        tvusergoingconnection = findViewById(R.id.tvusergoingconnection);
        tvuserabout = findViewById(R.id.tvuserabout);
        tvuserwork = findViewById(R.id.tvuserwork);
        tvuserphone = findViewById(R.id.tvuserphone);
        tvusermail = findViewById(R.id.tvusermail);
        tvuseraddress = findViewById(R.id.tvuseraddress);
        llBussinessProfile = findViewById(R.id.userbussinessprofile);

        btnsendrequest = findViewById(R.id.btnsendrequestconnection);
        btnreqsenddone = findViewById(R.id.btnreqsenddone);
        btnacceptconnection = findViewById(R.id.btnacceptconnection);
        btnrejectconnection = findViewById(R.id.btnrejectconnection);


        tvsendreqbussinessmsg = findViewById(R.id.tvsendreqbussinessmsg);


        final Intent intent = getIntent();

        tvusername.setText(intent.getStringExtra("username"));
        tvusergoingconnection.setText(intent.getStringExtra("usertotalconnection"));
        tvuserabout.setText(intent.getStringExtra("userabout"));
        tvuserwork.setText(intent.getStringExtra("userwork"));
        tvuserphone.setText(intent.getStringExtra("userphone"));
        tvusermail.setText(intent.getStringExtra("usermail"));
        tvuseraddress.setText(intent.getStringExtra("useraddress"));
        final String usrmail = intent.getStringExtra("usermail");
        tvuserphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendmsgtowhatsapp = new Intent(Intent.ACTION_VIEW);
                //TODO:: Replace msg text
                String msg = "Hey!, This text is send from Event App";
                sendmsgtowhatsapp.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + Objects.requireNonNull(intent.getStringExtra("userphone")).substring(1) + "&text=" + msg));
                startActivity(sendmsgtowhatsapp);
            }
        });

        tvusermail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + usrmail));
                startActivity(intent);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref1 = firebaseDatabase.getReference("users/" + Common.currentuser.getUserphone());
        ref2 = firebaseDatabase.getReference("users/" + intent.getStringExtra("userphone"));

        final List<String> cuserallconnection = Common.currentuser.getConnection();
        final List<String> cuserpendingconnection = Common.currentuser.getPendingconnection();
        final List<String> cuserrequestedconnection = Common.currentuser.getRequestedconnection();
        //Selected User
        final List<String> selectedUserPendingConnection = Common.selecetedUser.getPendingconnection();
        final List<String> selecetedUserConnection = Common.selecetedUser.getConnection();
        final List<String> selecetedUserRequestedConnection = Common.selecetedUser.getRequestedconnection();

        if (cuserrequestedconnection.contains(intent.getStringExtra("userphone"))) {
            btnsendrequest.setVisibility(View.GONE);
            btnreqsenddone.setVisibility(View.VISIBLE);
            tvsendreqbussinessmsg.setText("Waiting for " + intent.getStringExtra("username") + " to accept your request.");
        }
        if (cuserpendingconnection.contains(intent.getStringExtra("userphone"))) {
            btnsendrequest.setVisibility(View.GONE);
            btnrejectconnection.setVisibility(View.VISIBLE);
            btnacceptconnection.setVisibility(View.VISIBLE);
            tvsendreqbussinessmsg.setText("Accept Request to view.");
        }
        if (cuserallconnection.contains(intent.getStringExtra("userphone"))) {
            tvsendreqbussinessmsg.setVisibility(View.GONE);
            llBussinessProfile.setVisibility(View.VISIBLE);
            btnsendrequest.setVisibility(View.GONE);

        }

        btnsendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuserrequestedconnection.add(intent.getStringExtra("userphone"));
                selectedUserPendingConnection.add(Common.currentuser.getUserphone());

                ref1.child("requestedconnection").setValue(cuserrequestedconnection);
                ref2.child("pendingconnection").setValue(selectedUserPendingConnection);
                Toast.makeText(Userviewpplgoingprofile.this, "Request Send", Toast.LENGTH_SHORT).show();
                btnsendrequest.setVisibility(View.GONE);
                btnreqsenddone.setVisibility(View.VISIBLE);
                tvsendreqbussinessmsg.setText("Waiting for " + intent.getStringExtra("username") + " to accept your request.");
            }
        });

        btnacceptconnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuserallconnection.add(Common.selecetedUser.getUserphone());
                ref1.child("connection").setValue(cuserallconnection);

                cuserpendingconnection.remove(intent.getStringExtra("userphone"));
                ref1.child("pendingconnection").setValue(cuserpendingconnection);


                selecetedUserConnection.add(Common.currentuser.getUserphone());
                ref2.child("connection").setValue(selecetedUserConnection);

                selecetedUserRequestedConnection.remove(Common.currentuser.getUserphone());
                ref2.child("requestedconnection").setValue(selecetedUserRequestedConnection);

                tvsendreqbussinessmsg.setVisibility(View.GONE);
                llBussinessProfile.setVisibility(View.VISIBLE);
                btnacceptconnection.setVisibility(View.GONE);
                btnrejectconnection.setVisibility(View.GONE);

                tvusergoingconnection.setText((Integer.parseInt(intent.getStringExtra("usertotalconnection")) + 1) + " ");
            }
        });
    }
}
