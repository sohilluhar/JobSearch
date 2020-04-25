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
import java.util.List;

import com.example.job.Adapter.ItemViewHolder;
import com.example.job.Adapter.SearchCategory;
import com.example.job.Interface.IRecyclerClickListener;
import com.example.job.Model.Event;

public class Search extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference eventlist, dbcatref;
    FirebaseRecyclerAdapter<Event, ItemViewHolder> adapter;
    FirebaseRecyclerAdapter<Event, ItemViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    CardView card;
    SearchCategory adaptercat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        database = FirebaseDatabase.getInstance();
        eventlist = database.getReference("Events");
        dbcatref = database.getReference("Category");

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
        materialSearchBar.setHint("Enter event name");
        loadSuggest();
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
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        //load all foods
        //  loadAllEvents();

        dbcatref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> cat = new ArrayList<>();

                if (dataSnapshot.exists()) {
                    int size = (int) dataSnapshot.getChildrenCount();
                    int i = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String tmp = ds.getValue(String.class);
                        cat.add(tmp);

                    }
                }
                onLoad(cat);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

    private void loadAllEvents() {


//        Query query = FirebaseDatabase.getInstance().getReference().child("Events");
        Query query = FirebaseDatabase.getInstance().getReference().child("Events");
        FirebaseRecyclerOptions<Event> options = new FirebaseRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class).build();

        adapter = new FirebaseRecyclerAdapter<Event, ItemViewHolder>(
                options) {
            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, parent, false);
                return new ItemViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int position, @NonNull final Event model) {
                viewHolder.event_name.setText(model.getEvent_name());


                final Event local = model;
                viewHolder.setListener(new IRecyclerClickListener() {
                    @Override
                    public void onItemCliickListener(View view, int position) {
                        // Toast.makeText(EventList.this, ""+local.getName(), Toast.LENGTH_SHORT).show();
                        Common.selectedEvent = model;
                        Intent intent = new Intent(Search.this, EventDetail.class);
                        startActivity(intent);
                    }
                });

                viewHolder.ngo_name.setText(model.getNgo_name());
                viewHolder.event_name.setText(model.getEvent_name());
                viewHolder.date.setText(model.getDate().substring(0, 7));
                viewHolder.city.setText(model.getCity());
                viewHolder.time.setText(model.getTime());

                Glide.with(Search.this).load(model.getImgurl()).centerCrop().into(viewHolder.imgevent);

            }


        };

        //set Adapter
        //Log.d("TAG",""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);

    }


    private void loadSuggest() {
        eventlist.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Event item = postSnapshot.getValue(Event.class);
                    suggestList.add(item.getEvent_name());  //Add name  suggest list
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

    private void startSearch(CharSequence text) {
        Query query = FirebaseDatabase.getInstance().getReference().child("Events").orderByChild("event_name").equalTo(text.toString().trim());
        FirebaseRecyclerOptions<Event> options = new FirebaseRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class).build();

        searchAdapter = new FirebaseRecyclerAdapter<Event, ItemViewHolder>(
                options) {
            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, parent, false);
                return new ItemViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int position, @NonNull final Event model) {
                viewHolder.event_name.setText(model.getEvent_name());


                final Event local = model;
                viewHolder.setListener(new IRecyclerClickListener() {
                    @Override
                    public void onItemCliickListener(View view, int position) {
                        // Toast.makeText(EventList.this, ""+local.getName(), Toast.LENGTH_SHORT).show();
                        Common.selectedEvent = model;
                        Intent intent = new Intent(Search.this, EventDetail.class);
                        startActivity(intent);
                    }
                });

                viewHolder.ngo_name.setText(model.getNgo_name());
                viewHolder.event_name.setText(model.getEvent_name());
                viewHolder.date.setText(model.getDate().substring(0, 7));
                viewHolder.city.setText(model.getCity());
                viewHolder.time.setText(model.getTime());

                Glide.with(Search.this).load(model.getImgurl()).centerCrop().into(viewHolder.imgevent);

            }


        };

        searchAdapter.startListening();

        recyclerView.setAdapter(searchAdapter); // Set adapter for Recycler view is search result


    }


}
