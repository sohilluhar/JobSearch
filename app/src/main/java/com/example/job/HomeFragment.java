package com.example.job;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

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
import java.util.Date;
import java.util.List;

import com.example.job.Adapter.MyAdapter;
import com.example.job.Model.Event;


public class HomeFragment extends Fragment {

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
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, dbUserRef;
    TextView wlcMsg;
    MyAdapter adapter;
    List<Event> events;
    Button btnViewquestion;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        recyclerView = (RecyclerView) view.findViewById(R.id.eventRecyclerFeatured);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Events");
        dbUserRef = firebaseDatabase.getReference("users");

//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        wlcMsg = (TextView) view.findViewById(R.id.wlcmsg);
        btnViewquestion = view.findViewById(R.id.btnViewquestion);


        if (Common.currentuser.getType().equals("Sponsor")) {
            //navigation.setVisibility(View.GONE);
            wlcMsg.setVisibility(View.GONE);
        }
        try {

            if (Common.currentuser.getExtraType().equals("Speaker")) {
                btnViewquestion.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {

        }

        btnViewquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewSpeakersEvent.class);
                startActivity(intent);
            }
        });
//load eventRecyclerFeatured
        databaseReference.orderByChild("ngo_logo").equalTo("True").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                events = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Event event = dataSnapshot1.getValue(Event.class);
                    events.add(event);
                }

                try {
                    featuredEventLoad(events);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Failed", databaseError.getMessage());

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                events = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Event event = dataSnapshot1.getValue(Event.class);
                    events.add(event);
                }

                try {
                    onEventLoad(events);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Failed", databaseError.getMessage());

            }
        });


        wlcMsg.setText("Hey, " + Common.currentuser.getName());

    }

    private void featuredEventLoad(List<Event> events) throws ParseException {


        //SORT EVENT
        Collections.sort(events, dateNewOld);


        Collections.reverse(events);


        // list.sort( dateNewOld );
//Collections.reverse(list, dateNewOld);
//        print(list);

        //SORT EVENTS DONE


//        recyclerView.setNestedScrollingEnabled(false);


        // viewPager.setAdapter(adapter);
        //   viewPager.setPadding(130, 0, 130, 0);
//        //   viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//        @Override
//        public void onPageScrolled ( int position, float positionOffset, int positionOffsetPixels){
//
//            if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
//                viewPager.setBackgroundColor(
//
//                        (Integer) argbEvaluator.evaluate(
//                                positionOffset,
//                                colors[position],
//                                colors[position + 1]
//                        )
//                );
//            } else {
//                viewPager.setBackgroundColor(colors[colors.length - 1]);
//            }
//        }
//
//        @Override
//        public void onPageSelected ( int position){
//
//        }
//
//        @Override
//        public void onPageScrollStateChanged ( int state){
//
//        }
//    });


    }

    private void onEventLoad(List<Event> events) throws ParseException {


        //SORT EVENT
        Collections.sort(events, dateNewOld);
        Collections.reverse(events);


        //remove past events

        for (int i = 0; i < events.size(); i++) {

            //long timestamp = new SimpleDateFormat("dd MMM yyyy").parse(new Date()).getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            Date today = sdf.parse(sdf.format(new Date()));
            long timestamp = today.getTime();
            @SuppressLint("SimpleDateFormat")
            Date dt = new SimpleDateFormat("dd MMM yyyy").parse(events.get(i).getDate());
            long eventtimestamp = dt.getTime();
            if (timestamp > eventtimestamp) {
                events.remove(i);
            }
        }
        // list.sort( dateNewOld );
//Collections.reverse(list, dateNewOld);
//        print(list);

        //SORT EVENTS DONE
//
//
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        adapter = new MyAdapter(recyclerView, getActivity(), events);
//        recyclerView.setAdapter(adapter);
    }

}
