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

public class UserRegistration extends AppCompatActivity {

    EditText uphone, uemail, username, upass, userid, upassc, etDOB, etlocation;
    TextView login, tvregcompany;
    Button reg;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbUserRef = database.getReference("users");
    String phoneNumber;

    Spinner genderspin;

    Calendar c;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        uphone = findViewById(R.id.editTextPhone);

        uemail = (EditText) findViewById(R.id.txtEmail);
        username = (EditText) findViewById(R.id.txtName);
        upass = (EditText) findViewById(R.id.txtPwd);
        etlocation = (EditText) findViewById(R.id.etlocation);
        upassc = (EditText) findViewById(R.id.txtcPwd);
        login = (TextView) findViewById(R.id.lnkLogin);
        tvregcompany = (TextView) findViewById(R.id.tvregcompany);
        reg = (Button) findViewById(R.id.btnLogin);
        etDOB = findViewById(R.id.etDOB);
        genderspin = findViewById(R.id.gender);
        List<String> gen = new ArrayList<>();

        gen.add("Male");
        gen.add("Female");
        gen.add("Other");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gen);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        genderspin.setAdapter(arrayAdapter);
        etDOB.setClickable(true);

        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                Log.e("DAte", "test");

                datePickerDialog = new DatePickerDialog(UserRegistration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {

                        String[] MONTH =
                                {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                        Log.e("Month", mmonth + "");

                        String tmpday = mdayOfMonth + "";


                        etDOB.setText(tmpday + " " + MONTH[mmonth] + " " + myear);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });


        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserRegistration.this, MainActivity.class);
                startActivity(intent);
            }
        });
        tvregcompany.setMovementMethod(LinkMovementMethod.getInstance());
        tvregcompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserRegistration.this, CompanyRegistration.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String struname = username.getText().toString().trim();
                String genderstr = genderspin.getSelectedItem().toString();
                String dob = etDOB.getText().toString();
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
//            Toast.makeText(UserRegistration.this, "Phone Number Already Exists.", Toast.LENGTH_SHORT).show();
            uphone.setError("Phone number Already Exists");
            uphone.requestFocus();
        } else {
            Intent intent = new Intent(UserRegistration.this, VerifyPhoneActivity.class);


            String struname = username.getText().toString().trim();
            String genderstr = genderspin.getSelectedItem().toString();
            String dob = etDOB.getText().toString();
            String strlocation = etlocation.getText().toString().trim();

            String stremail = uemail.getText().toString().trim();
            String number = uphone.getText().toString().trim();

            String strupass = upass.getText().toString().trim();

            intent.putExtra("name", struname);
            intent.putExtra("gender", genderstr);
            intent.putExtra("dob", dob);
            intent.putExtra("location", strlocation);
            intent.putExtra("email", stremail);
            intent.putExtra("phonenumber", "+91" + number);
            intent.putExtra("password", strupass);
            intent.putExtra("type", "User");

            startActivity(intent);
            finish();
        }
    }
}
