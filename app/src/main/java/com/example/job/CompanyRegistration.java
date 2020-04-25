package com.example.job;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CompanyRegistration extends AppCompatActivity {

    EditText uphone, uemail, username, upass, userid, upassc, etlocation;
    TextView login, userreg;
    Button reg;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbUserRef = database.getReference("users");
    String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_registration);
        uphone = findViewById(R.id.editTextPhone);

        uemail = (EditText) findViewById(R.id.txtEmail);
        username = (EditText) findViewById(R.id.etCompanyName);
        upass = (EditText) findViewById(R.id.txtPwd);
        etlocation = (EditText) findViewById(R.id.etlocation);
        upassc = (EditText) findViewById(R.id.txtcPwd);
        login = (TextView) findViewById(R.id.lnkLogin);
        reg = (Button) findViewById(R.id.btnLogin);
        userreg = findViewById(R.id.tvreguser);


        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CompanyRegistration.this, MainActivity.class);
                startActivity(intent);
            }
        });
        userreg.setMovementMethod(LinkMovementMethod.getInstance());
        userreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CompanyRegistration.this, UserRegistration.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String struname = username.getText().toString().trim();
                String strlocation = etlocation.getText().toString().trim();

                String stremail = uemail.getText().toString().trim();
                String number = uphone.getText().toString().trim();

                String strupass = upass.getText().toString().trim();
                String strupassc = upassc.getText().toString().trim();


                if (struname.isEmpty()) {
                    username.setError("Enter Your Name");
                    username.requestFocus();
                    return;
                }
                if (stremail.isEmpty()) {
                    uemail.setError("Enter your email id");
                    uemail.requestFocus();
                    return;
                }
                if (strupass.isEmpty()) {
                    upass.setError("Create your password");
                    upass.requestFocus();
                    return;
                }
                if (number.isEmpty() || number.length() < 10) {
                    uphone.setError("Valid number is required");
                    uphone.requestFocus();
                    return;
                }
                if (!upass.getText().toString().equals(upassc.getText().toString())) {
                    upassc.setError("Password Mismatch");
                    upassc.requestFocus();
                    return;
                }


                phoneNumber = "+" + "91" + number;

                dbUserRef.orderByKey().equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            callOTPVerify(true);

                        } else {
                            callOTPVerify(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });


    }

    private void callOTPVerify(boolean b) {
        if (b) {
//            Toast.makeText(CompanyRegistration.this, "Phone Number Already Exists.", Toast.LENGTH_SHORT).show();
            uphone.setError("Phone number Already Exists");
            uphone.requestFocus();
        } else {
            Intent intent = new Intent(CompanyRegistration.this, VerifyPhoneActivity.class);


            String struname = username.getText().toString().trim();
            String strlocation = etlocation.getText().toString().trim();

            String stremail = uemail.getText().toString().trim();
            String number = uphone.getText().toString().trim();

            String strupass = upass.getText().toString().trim();

            intent.putExtra("name", struname);
            intent.putExtra("gender", " ");
            intent.putExtra("dob", " ");
            intent.putExtra("location", strlocation);
            intent.putExtra("email", stremail);
            intent.putExtra("phonenumber", "+91" + number);
            intent.putExtra("password", strupass);
            intent.putExtra("type", "Company");

            startActivity(intent);
            finish();
        }
    }
}
