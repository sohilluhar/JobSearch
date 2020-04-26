package com.example.job;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.example.job.Adapter.MyAdapter;
import com.example.job.Adapter.MySlideAdapter;
import com.example.job.Adapter.PeopleGoingCirImageView;
import com.example.job.Model.Event;
import com.example.job.Model.EventGoing;
import com.example.job.Model.User;

import ss.com.bannerslider.Slider;

public class EventDetail extends AppCompatActivity {
    public Comparator<Event> dateNewOld = new Comparator<Event>() {
        @Override
        public int compare(Event o1, Event o2) {
            DateFormat f = new SimpleDateFormat("dd MMM yyyy");
            try {
                return f.parse(o2.getDate()).compareTo(f.parse(o1.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }
    };
    TextView orgPhone, orgMail, tvEnventname, tvNGOname, tvEventDate, tvEventTime, tvEventAddress, tvEventCity, tvEventHosted, tvNumberofppl, tvEventDescription, tvNGOOname;
    ImageView ivEventPhoto, ivmapAddress;
    CardView btnusergoing;
    CardView btnshareWA, btnviewsocialwall, btnAskQuestion;
    CardView btnViewAnnouncement, attentEvent;
    MyAdapter adapter;
    List<Event> events;
    List<String> pplgoing;
    List<User> users;
    boolean isUserGoing = false;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, event_going, ref1;
    DatabaseReference everntgoing, dbUserRef;
    LinearLayout linearLayout;
    Slider slider;
    PeopleGoingCirImageView profileimgadapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.actionbar_icon_share, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            Toast.makeText(this, "Search Click", Toast.LENGTH_SHORT).show();
            finish();
        }
//
        if (item.getItemId() == R.id.action_bar_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey,Check this Event. to attend event please download app. \n https://play.google.com/store/apps/"

            );
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);


        tvEnventname = (TextView) findViewById(R.id.entDetail_eventName);
        linearLayout = findViewById(R.id.entDetailgoingppll);
        tvNGOname = (TextView) findViewById(R.id.entDetail_NGOname);
        tvEventDate = (TextView) findViewById(R.id.entDetail_eventDate);
        tvEventTime = (TextView) findViewById(R.id.entDetail_eventTime);
        tvEventAddress = (TextView) findViewById(R.id.entDetail_eventAddress);
        tvEventCity = (TextView) findViewById(R.id.entDetail_eventCity);
        tvEventHosted = (TextView) findViewById(R.id.entDetail_hostedby);
        tvNumberofppl = (TextView) findViewById(R.id.entDetail_nogoingppl);
        tvNGOOname = (TextView) findViewById(R.id.otherNGOName);
        tvEventDescription = (TextView) findViewById(R.id.entDetail_eventDescrption);
        orgPhone = (TextView) findViewById(R.id.eventDetailCall);
        orgMail = (TextView) findViewById(R.id.eventDetailMail);

        ivEventPhoto = (ImageView) findViewById(R.id.entDetail_eventPhoto);
        ivmapAddress = (ImageView) findViewById(R.id.evntmap);
//
//
//        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
//        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
//        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedAppbar);


        attentEvent = findViewById(R.id.attendEvent);
        btnusergoing = findViewById(R.id.attendEventalready);
        btnshareWA = findViewById(R.id.shareonWA);
        btnViewAnnouncement = findViewById(R.id.viewannoucement);
        btnviewsocialwall = findViewById(R.id.btnviewsocialwall);
        btnAskQuestion = findViewById(R.id.btnAskQuestion);

        slider = (Slider) findViewById(R.id.sliderBanner);
        Slider.init(new PicassoLoadingService());


        final String orgPhonenumber = Common.selectedEvent.getNgo_id();

        final Event ent = Common.selectedEvent;


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Events");
        event_going = firebaseDatabase.getReference("EventGoing");
        ref1 = firebaseDatabase.getReference("users");


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(ent.getEvent_name());

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(EventDetail.this, UserViewpplgoing.class);
                startActivity(intent2);
            }
        });

        event_going.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                EventGoing tmp;
                tmp = dataSnapshot.child(ent.getEvent_key()).getValue(EventGoing.class);
                Common.event_going = tmp;
                setPeopleGoing();
                setAttentEventButton();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnViewAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(EventDetail.this, ViewAllEventAnnouncement.class);
                startActivity(intent2);

            }
        });

        btnviewsocialwall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(EventDetail.this, ViewEventSocialWall.class);
                startActivity(intent2);

            }
        });
        btnAskQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(EventDetail.this, ViewListofSpeaker.class);
                startActivity(intent2);

            }
        });

        tvEnventname.setText(ent.getEvent_name());
        tvNGOname.setText(ent.getNgo_name());
        tvEventDate.setText(ent.getDate());
        tvEventTime.setText(ent.getTime());
        tvEventAddress.setText(ent.getAddress());
        tvEventCity.setText(ent.getCity());
        tvEventHosted.setText("Organized By " + ent.getOrganize_by());
        tvEventDescription.setText(ent.getDescription());
        orgPhone.setText(orgPhonenumber);
        Log.e("email is", ent.getMail());
        orgMail.setText(ent.getMail());


        if (!Common.selectedEvent.getImgurl().equals(" "))
            Glide.with(this).load(Common.selectedEvent.getImgurl()).into(ivEventPhoto);

//        if (ent.getVol_req().equals("Yes")) {
//            applyVol.setVisibility(View.VISIBLE);
//            applyVol.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent1 = new Intent(event_details.this, user_applyVol.class);
//                    startActivity(intent1);
//
//
//                }
//            });
//        }

        ivmapAddress.setClickable(true);
        ivmapAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri googlemap = Uri.parse("geo:0,0?q=" + ent.getAddress());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, googlemap);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        tvEventAddress.setClickable(true);
        tvEventAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri googlemap = Uri.parse("geo:0,0?q=" + ent.getAddress());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, googlemap);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        orgPhone.setMovementMethod(LinkMovementMethod.getInstance());
        orgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + orgPhonenumber));
                startActivity(intent);


            }
        });
        orgMail.setMovementMethod(LinkMovementMethod.getInstance());
        orgMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + orgMail.getText().toString()));
                startActivity(intent);


            }
        });


        events = new ArrayList<>();
        pplgoing = new ArrayList<>();


//        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"white\">" + ent.getEvent_name() + "</font>"));


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Event event = dataSnapshot1.getValue(Event.class);
//                    Log.e("event_key", ent.getEvent_key());
//                    Log.e("key", event.getEvent_key());
//                    Log.e("Check", event.getEvent_key().equals(ent.getEvent_key()) + "");
                    if (event.getNgo_id().equals(ent.getNgo_id()) && !event.getEvent_key().equals(ent.getEvent_key()))
                        events.add(event);
                }

                onEventLoad(events);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Failed", databaseError.getMessage());

            }
        });

        attentEvent.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                //  Toast.makeText(EventDetail.this, "Attend event click", Toast.LENGTH_SHORT).show();

                Common.event_going.getGoingppl().add(Common.currentuser.getUserphone());
                EventGoing evnt = new EventGoing(Common.event_going.getGoingppl());
                event_going.child(ent.getEvent_key()).setValue(evnt);

                attentEvent.setVisibility(View.GONE);
                btnusergoing.setVisibility(View.VISIBLE);

                tvNumberofppl.setText((Common.event_going.getGoingppl().size()) + " People Going");


                List<String> tmpgoing = Common.currentuser.getGoingevent();

                tmpgoing.add(ent.getEvent_key());

                ref1.child(Common.currentuser.getUserphone()).child("goingevent").setValue(tmpgoing);
                Intent intent2 = new Intent(EventDetail.this, UserGoingEvent.class);
                startActivity(intent2);
                finish();
                Toast.makeText(EventDetail.this, "Done!", Toast.LENGTH_SHORT).show();


            }
        });


        btnshareWA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                shareImage(Common.selectedEvent.getImgurl(), EventDetail.this);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey,Check this Event " + ent.getEvent_name() + " organized by " + ent.getNgo_name() + " . It is on " + ent.getDate() + " " + ent.getTime()
                        + ".Address is " + ent.getAddress() + " to attend event please download app. \n https://play.google.com/store/apps/details?id=net.sunnidawateislami.app"

                );
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        if (!Common.currentuser.getType().equals("Sponsor")) {

            tvNumberofppl.setMovementMethod(LinkMovementMethod.getInstance());
            tvNumberofppl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(EventDetail.this, UserViewpplgoing.class);
                    startActivity(intent2);


                }
            });
        }
        loadBanner(Common.selectedEvent.getSponsor());

        if (Common.currentuser.getType().equals("Sponsor")) {
            btnViewAnnouncement.setVisibility(View.GONE);
            attentEvent.setVisibility(View.GONE);
            tvNumberofppl.setClickable(false);
            slider.setVisibility(View.GONE);

        }

        //getSupportActionBar().hide();
        final EventGoing[] eventGoingtmp = new EventGoing[1];

        everntgoing = firebaseDatabase.getReference("EventGoing");
        dbUserRef = firebaseDatabase.getReference("users");

        everntgoing.child(Common.selectedEvent.getEvent_key()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users = new ArrayList<User>();
                eventGoingtmp[0] = dataSnapshot.getValue(EventGoing.class);


                for (int i = 0; i < eventGoingtmp[0].getGoingppl().size(); i++) {
                    getUsers(eventGoingtmp[0].getGoingppl().get(i), i, eventGoingtmp[0].getGoingppl().size());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getUsers(final String phone, final int i, final int max) {
        dbUserRef.orderByKey().equalTo(phone).addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            User tmp = dataSnapshot.child(phone).getValue(User.class);
                            users.add(tmp);
                            if (i == max - 1) {
                                setprofileImg(users);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void loadBanner(List<String> banners) {
        banners.remove(0);
        slider.setAdapter(new MySlideAdapter(banners));
    }

    @SuppressLint("SetTextI18n")
    private void setPeopleGoing() {
        tvNumberofppl.setText(Common.event_going.getGoingppl().size() - 1 + " People Going");

    }

    private void setAttentEventButton() {

        if (!Common.event_going.getGoingppl().contains(Common.currentuser.getUserphone())) {
            attentEvent.setVisibility(View.VISIBLE);
        } else {
            btnusergoing.setVisibility(View.VISIBLE);
            btnviewsocialwall.setVisibility(View.VISIBLE);
            btnAskQuestion.setVisibility(View.VISIBLE);
            attentEvent.setVisibility(View.GONE);

        }
        if (Common.currentuser.getType().equals("Sponsor")) {
            attentEvent.setVisibility(View.GONE);
            btnshareWA.setVisibility(View.GONE);
        }

    }


    void setprofileImg(List<User> users) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.userprofileimg);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        profileimgadapter = new PeopleGoingCirImageView(recyclerView, this, users);
        recyclerView.setAdapter(profileimgadapter);
    }

    private void setvalppl(List<String> pplgoing1) {

        pplgoing = pplgoing1;
    }

    private void onEventLoad(List<Event> events) {

        if (events.size() != 0) {
            tvNGOOname.setText("Other Events of " + Common.selectedEvent.getNgo_name());

        }

        //SORT EVENT
        Collections.sort(events, dateNewOld);
        Collections.reverse(events);
        // list.sort( dateNewOld );
//Collections.reverse(list, dateNewOld);
//        print(list);

        //SORT EVENTS DONE

//
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.event_similar);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new MyAdapter(recyclerView, this, events);
//        recyclerView.setAdapter(adapter);
    }

}