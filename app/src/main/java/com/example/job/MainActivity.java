package com.example.job;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.job.Model.User1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import com.example.job.Model.Sponser;
import com.example.job.Model.User;
import com.example.job.Model.resume;

public class MainActivity extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbUserRef = database.getReference("users");
    // DatabaseReference dbUserresumeRef = database.getReference("resume");
    // DatabaseReference dbSponsor = database.getReference("Sponsers");
    ProgressBar pb;
    private EditText etUserPhone;
    private EditText etUserPass;
    private Button login;
    private CheckBox cbRememberme;
    private boolean rememberme = false;

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        saveUser("9833771892","abcd","sohil.l@somaiya.edu");
//        saveUser("98337718782","abcd","sohil.l@somaiya.edu");

//        sendOTP("+919833771892");


        TextView register = (TextView) findViewById(R.id.lnkRegister);
        etUserPhone = (EditText) findViewById(R.id.txtPhone);
        etUserPass = (EditText) findViewById(R.id.txtPwd);
        login = (Button) findViewById(R.id.btnLogin);
        cbRememberme = findViewById(R.id.cbrememberme);


//        SharedPreferences preferences1 = getSharedPreferences("checkbox", MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences1.edit();
//        String ch = preferences1.getString("remember", "");
//        editor.putString("remember", "false");
//        editor.remove("userphone");
//        editor.remove("password");
//        editor.apply();

        if (!isOnline())
            Toast.makeText(this, "No Internet or Poor Connectivity", Toast.LENGTH_LONG).show();
        pb = findViewById(R.id.progressBar2);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String ch = preferences.getString("remember", "");
        ch = "false";
        // ch = "false";//TODO:: replace
        if (ch.equals("true")) {
            pb.setVisibility(View.VISIBLE);
            final String uname = preferences.getString("userphone", "");
            final String password = preferences.getString("password", "");

            Gson gson = new Gson();
            String userjson = preferences.getString("_USER", "");
            User1 currentuser = gson.fromJson(userjson, User1.class);

            Common.currentuser1 = currentuser;
            dbUserRef.child(currentuser.getPhonenumber()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Common.currentuser1 = dataSnapshot.getValue(User1.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
/*
            //----
            dbUserresumeRef.child(Common.currentuser.getUserphone()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        resume tmp = dataSnapshot.getValue(resume.class);
                        Common.userresume=tmp;
                        Toast.makeText(MainActivity.this, "Resume "+tmp.getPersonal_careerobjective(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            //-----
*/
            if (Common.currentuser1.getType().equals("User")) {
                //     Common.currentuser = currentuser;

                Intent intent = new Intent(MainActivity.this, UserDashboard.class);
                startActivity(intent);
                finish();
            }

            if (Common.currentuser1.getType().equals("Company")) {
                //  Common.currentuser = currentuser;

                Intent intent = new Intent(MainActivity.this, Organizer_Dashboard.class);
                startActivity(intent);
                finish();
            }


        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isOnline())
                    Toast.makeText(MainActivity.this, "No Internet or Poor Connectivity", Toast.LENGTH_LONG).show();
                else {
                    pb.setVisibility(View.VISIBLE);
                    checkuser();
                }

            }
        });

        cbRememberme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rememberme = buttonView.isChecked();
            }
        });


        register.setMovementMethod(LinkMovementMethod.getInstance());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, UserRegistration.class);
                startActivity(intent);
            }
        });

    }


    private void checkuser() {


        dbUserRef.orderByKey().equalTo("+91" + etUserPhone.getText().toString()).addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            User1 currentuser = dataSnapshot.child("+91" + etUserPhone.getText().toString()).getValue(User1.class);
                            if (currentuser.getPassword().equals(etUserPass.getText().toString())) {

                                if (currentuser.getType().equals("User")) {
                                    Common.currentuser1 = currentuser;
//                                    Toast.makeText(MainActivity.this, "Check box is " + rememberme, Toast.LENGTH_SHORT).show();
//                                    if (rememberme) {
                                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("remember", "true");
                                    editor.putString("userphone", currentuser.getPhonenumber());
                                    editor.putString("password", currentuser.getPassword());


                                    Gson gson = new Gson();
                                    String userjson = gson.toJson(currentuser);
                                    editor.putString("_USER", userjson);


                                    editor.apply();
//                                    } else {
//                                        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
//                                        SharedPreferences.Editor editor = preferences.edit();
//                                        editor.putString("remember", "false");
//                                        editor.apply();
//                                    }
                                    Intent intent = new Intent(MainActivity.this, UserDashboard.class);
                                    startActivity(intent);
                                    finish();
                                }

                                if (currentuser.getType().equals("Company")) {
                                    Common.currentuser1 = currentuser;
//                                    Toast.makeText(MainActivity.this, "Check box is " + rememberme, Toast.LENGTH_SHORT).show();
//                                    if (rememberme) {
                                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("remember", "true");
                                    editor.putString("userphone", currentuser.getPhonenumber());
                                    editor.putString("password", currentuser.getPassword());

                                    Gson gson = new Gson();
                                    String userjson = gson.toJson(currentuser);
                                    editor.putString("_USER", userjson);

                                    editor.apply();
//                                    } else {
//                                        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
//                                        SharedPreferences.Editor editor = preferences.edit();
//                                        editor.putString("remember", "false");
//                                        editor.apply();
//                                    }
                                    Intent intent = new Intent(MainActivity.this, Organizer_Dashboard.class);
                                    startActivity(intent);
                                    finish();
                                    //
                                }

                                if (currentuser.getType().equals("Pending")) {
                                    Toast.makeText(MainActivity.this, "Verification Pending", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(View.GONE);

                                }
                            } else {
                                maketoast();
                            }

                        } else {
                            maketoast();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


    private void maketoast() {
        Toast.makeText(this, "Wrong Phone Number or Password", Toast.LENGTH_SHORT).show();
        pb.setVisibility(View.GONE);

    }

}
