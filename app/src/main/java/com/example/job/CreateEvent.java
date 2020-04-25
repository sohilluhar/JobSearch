package com.example.job;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.job.Model.Annoucement;
import com.example.job.Model.Event;
import com.example.job.Model.EventGoing;

public class CreateEvent extends AppCompatActivity {
    final int PICK_IMAGE_REQUEST = 71;
    Button addEvent, back, datepickup, btnChoose;
    EditText event_name;
    EditText event_date;
    EditText event_time;
    EditText new_event_city;
    EditText event_description, event_address, event_pincode, event_oragnizer, etTotalNumVol;
    TextView tvVolNumber;
    ImageView previewImg;
    Uri filePath;
    Calendar c;
    DatePickerDialog datePickerDialog;
    FirebaseStorage storage;
    StorageReference storageReference;
    TimePickerDialog timePicker;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, ref1;
    String imgurl = " ";
    Spinner catlistspin;

    FirebaseDatabase database;
    DatabaseReference dbcatref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        addEvent = (Button) findViewById(R.id.btnAddEvent);
        btnChoose = (Button) findViewById(R.id.btnUploadEventImage);
        previewImg = (ImageView) findViewById(R.id.ivEventImage);


        event_name = (EditText) findViewById(R.id.new_event_name);
        event_date = (EditText) findViewById(R.id.new_event_date);
        event_time = (EditText) findViewById(R.id.new_event_time);
        etTotalNumVol = (EditText) findViewById(R.id.new_event_limit);
        tvVolNumber = (TextView) findViewById(R.id.tvnew_event_limit);

        event_description = (EditText) findViewById(R.id.new_event_details);
        event_address = (EditText) findViewById(R.id.new_event_address);
        event_pincode = (EditText) findViewById(R.id.new_event_pincode);
        new_event_city = (EditText) findViewById(R.id.new_event_city);

        event_oragnizer = (EditText) findViewById(R.id.new_event_oragnizer);
        catlistspin = findViewById(R.id.catlist);

        findViewById(R.id.add_job).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateEvent.this, "Job Add Click", Toast.LENGTH_SHORT).show();
            }
        });


        event_date.setFocusable(false);
        event_date.setClickable(true);


        event_time.setFocusable(false);
        event_time.setClickable(true);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Job");
        ref1 = firebaseDatabase.getReference("JobApply");
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        event_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                Log.e("DAte", "test");

                datePickerDialog = new DatePickerDialog(CreateEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {

                        String[] MONTH =
                                {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                        Log.e("Month", mmonth + "");

                        String tmpday = mdayOfMonth + "";


                        event_date.setText(tmpday + " " + MONTH[mmonth] + " " + myear);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        event_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int hr = c.get(Calendar.HOUR);
                int min = c.get(Calendar.MINUTE);

                final String[] format = {""};

                timePicker = new TimePickerDialog(CreateEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {

                        if (hour == 0) {
                            hour += 12;
                            format[0] = "AM";
                        } else if (hour == 12) {
                            format[0] = "PM";
                        } else if (hour > 12) {
                            hour -= 12;
                            format[0] = "PM";
                        } else {
                            format[0] = "AM";
                        }

                        event_time.setText(new StringBuilder().append(hour).append(" : ").append(minute)
                                .append(" ").append(format[0]));
                    }

                }, hr, min, false);

                timePicker.show();
            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filePath != null) {
                    uploadimg();
                } else {
                    addEventtoDB();
                }
            }
        });
    }


    private void uploadimg() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
        ref.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                setimguri(uri);
                            }
                        });
//                            progressDialog.dismiss();
                        // Toast.makeText(CreateEvent.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
                        Toast.makeText(CreateEvent.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploading " + (int) progress + "%");
                    }
                });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                previewImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void addEventtoDB() {

        final String eventnamestr = event_name.getText().toString();
        final String eventdatestr = event_date.getText().toString();
        final String eventtimestr = event_time.getText().toString();

        final String ngo_namestr = Common.currentuser.getName().toString();
        final String ngo_idstr = Common.currentuser.getUserphone();

        final String eventdescriptionstr = event_description.getText().toString();
        final String eventaddressstr = event_address.getText().toString();
        final String eventpincodestr = event_pincode.getText().toString();
        final String event_oragnizerstr = event_oragnizer.getText().toString();
        final String event_limitstr = etTotalNumVol.getText().toString();
        final String city = new_event_city.getText().toString();
        final String catstr = catlistspin.getSelectedItem().toString();
        final String imgurlf = imgurl;


//                ,event_address,event_city,event_pincode;

        final List<Annoucement> annoucemts = new ArrayList<>();
        annoucemts.add(new Annoucement("", "", "", ""));
//        annoucemts.add(" ");
        final List<String> photos = new ArrayList<>();
        photos.add(" ");
        final List<String> comments = new ArrayList<>();
        comments.add(" ");

        if (eventnamestr.isEmpty() || eventdatestr.isEmpty() || eventtimestr.isEmpty() || eventdescriptionstr.isEmpty() || eventaddressstr.isEmpty() || eventpincodestr.isEmpty()) {
            Toast.makeText(CreateEvent.this, "Please fill all details ", Toast.LENGTH_SHORT).show();
        } else {

            long eventkey = new Date().getTime();
            List<String> tmp = new ArrayList<>();
            tmp.add(" ");

            Event newenvent = new Event(
                    eventaddressstr, eventdescriptionstr, city, eventpincodestr,
                    eventnamestr, ngo_namestr, "", eventdatestr, eventtimestr, city,
                    event_oragnizerstr, "", ngo_idstr, "NO", photos, annoucemts, comments,
                    eventkey + "", event_limitstr, imgurlf, Common.currentuser.getEmail(), tmp, tmp, catstr
            );

//                        UUID eventkey= UUID.randomUUID();


            databaseReference.child(eventkey + "").setValue(newenvent);

            ref1.child(eventkey + "").child("1").child("phone").child("123");
            ref1.child(eventkey + "").child("1").child("status").child("pending");


            // databaseReference.child(eventkey + "").child("question").child("1").setValue(tmp);
            Toast.makeText(CreateEvent.this, "Job Added Successfully ", Toast.LENGTH_SHORT).show();
            finish();


        }

    }

    private void setimguri(Uri uri) {
        imgurl = uri.toString();
        addEventtoDB();
    }
}
