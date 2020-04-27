package com.example.job;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.job.Model.Job;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import com.example.job.Adapter.ItemViewHolder;
import com.example.job.Adapter.SearchCategory;
import com.example.job.Interface.IRecyclerClickListener;
import com.example.job.Model.Event;

public class Search extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference jobref, dbcatref;
    FirebaseRecyclerAdapter<Job, ItemViewHolder> adapter;
    FirebaseRecyclerAdapter<Job, ItemViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    List<String> suggestList1 = new ArrayList<>();
    List<String> suggestList2 = new ArrayList<>();
    List<String> suggestList3 = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    MaterialSearchBar materialSearchBar1;
    MaterialSearchBar materialSearchBar2;
    MaterialSearchBar materialSearchBar3;
    CardView card;
    SearchCategory adaptercat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        database = FirebaseDatabase.getInstance();
        jobref = database.getReference("Job");

        card = findViewById(R.id.cardTodayEvent);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat f = new SimpleDateFormat("dd MMM yyyy");
//                f.;
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
                String string = simpleDateFormat.format(date);


                Intent intent = new Intent(Search.this, SearchResult.class);
                intent.putExtra("category", "NONE");
                intent.putExtra("date", string);
                startActivity(intent);
            }
        });


        getSupportActionBar().hide();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_search);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        materialSearchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        materialSearchBar1 = (MaterialSearchBar) findViewById(R.id.searchBar1);
        materialSearchBar2 = (MaterialSearchBar) findViewById(R.id.searchBar2);
        materialSearchBar3 = (MaterialSearchBar) findViewById(R.id.searchBar3);
        loadSuggest();


//////////////////////////////////////////////////
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // When user type thier text , we will change suggest list

                List<String> suggest = new ArrayList<String>();
                for (String search : suggestList) //loop in suggest List :D sorry my mistake
                {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //When search bar is close
                //Restore original adapter
                if (!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //When search finish
                //Show result of search adapter
                startSearch(text, "skill");
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

//////////////////////////////////////////////////
        materialSearchBar1.setLastSuggestions(suggestList1);
        materialSearchBar1.setCardViewElevation(10);
        materialSearchBar1.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // When user type thier text , we will change suggest list

                List<String> suggest = new ArrayList<String>();
                for (String search : suggestList1) //loop in suggest List :D sorry my mistake
                {
                    if (search.toLowerCase().contains(materialSearchBar1.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar1.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar1.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //When search bar is close
                //Restore original adapter
                if (!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //When search finish
                //Show result of search adapter
                startSearch(text, "location");
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


//////////////////////////////////////////////////
        materialSearchBar2.setLastSuggestions(suggestList2);
        materialSearchBar2.setCardViewElevation(10);
        materialSearchBar2.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // When user type thier text , we will change suggest list

                List<String> suggest = new ArrayList<String>();
                for (String search : suggestList2) //loop in suggest List :D sorry my mistake
                {
                    if (search.toLowerCase().contains(materialSearchBar2.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar2.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar2.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //When search bar is close
                //Restore original adapter
                if (!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //When search finish
                //Show result of search adapter
                startSearch(text, "designation");
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


//////////////////////////////////////////////////
        materialSearchBar3.setLastSuggestions(suggestList3);
        materialSearchBar3.setCardViewElevation(10);
        materialSearchBar3.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // When user type thier text , we will change suggest list

                List<String> suggest = new ArrayList<String>();
                for (String search : suggestList3) //loop in suggest List :D sorry my mistake
                {
                    if (search.toLowerCase().contains(materialSearchBar3.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar3.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar3.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //When search bar is close
                //Restore original adapter
                if (!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //When search finish
                //Show result of search adapter
                startSearch(text, "jobtype");
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        //load all foods
        //  loadAllEvents();

//        dbcatref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<String> cat = new ArrayList<>();
//
//                if (dataSnapshot.exists()) {
//                    int size = (int) dataSnapshot.getChildrenCount();
//                    int i = 0;
//                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
//
//                        String tmp = ds.getValue(String.class);
//                        cat.add(tmp);
//
//                    }
//                }
//                onLoad(cat);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
    }

    @SuppressLint("SetTextI18n")
    private void onLoad(List<String> c) {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleCategory);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptercat = new SearchCategory(recyclerView, this, c);
        recyclerView.setAdapter(adaptercat);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // adapter.startListening();
    }


    private void loadSuggest() {
        jobref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Job item = postSnapshot.getValue(Job.class);
                    assert item != null;

                    if (!suggestList.contains(item.getSkill()))
                        suggestList.add(item.getSkill());  //Add name  suggest list

                    if (!suggestList1.contains(item.getLocation()))
                        suggestList1.add(item.getLocation());  //Add name  suggest list

                    if (!suggestList2.contains(item.getDesignation()))
                        suggestList2.add(item.getDesignation());  //Add name  suggest list

                    if (!suggestList3.contains(item.getJobtype()))
                        suggestList3.add(item.getJobtype());  //Add name  suggest list
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();
        //  adapter.stopListening();

    }

    private void startSearch(CharSequence text, String cat) {
        Query query = FirebaseDatabase.getInstance().getReference().child("Job").orderByChild(cat).equalTo(text.toString().trim());
        FirebaseRecyclerOptions<Job> options = new FirebaseRecyclerOptions.Builder<Job>()
                .setQuery(query, Job.class).build();

        searchAdapter = new FirebaseRecyclerAdapter<Job, ItemViewHolder>(
                options) {


            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, parent, false);
                return new ItemViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int position, @NonNull final Job model) {
                viewHolder.jobname.setText(model.getName());
                viewHolder.companyname.setText(model.getCompanyname());
                viewHolder.jobexp.setText(model.getWorkexperience());
                viewHolder.joblocation.setText(model.getLocation());
                viewHolder.jobskill.setText(model.getSkill());
                viewHolder.jobtype.setText(model.getJobtype());


                final Job local = model;
                viewHolder.setListener(new IRecyclerClickListener() {
                    @Override
                    public void onItemCliickListener(View view, int position) {
                        // Toast.makeText(EventList.this, ""+local.getName(), Toast.LENGTH_SHORT).show();
                        Common.selectedJob = model;
                        Intent intent = new Intent(Search.this, JobDetail.class);
                        startActivity(intent);
                    }
                });
//
//                viewHolder.ngo_name.setText(model.getNgo_name());
//                viewHolder.event_name.setText(model.getEvent_name());
//                viewHolder.date.setText(model.getDate().substring(0, 7));
//                viewHolder.city.setText(model.getCity());
//                viewHolder.time.setText(model.getTime());
//
//                Glide.with(Search.this).load(model.getImgurl()).centerCrop().into(viewHolder.imgevent);

            }


        };

        searchAdapter.startListening();

        recyclerView.setAdapter(searchAdapter); // Set adapter for Recycler view is search result


    }


}
