package com.example.job.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.job.Common;
import com.example.job.Interface.ILoadMore;
import com.example.job.Interface.IRecyclerClickListener;
import com.example.job.Model.JobApply;
import com.example.job.Model.User;
import com.example.job.Model.User1;
import com.example.job.Model.UserResume;
import com.example.job.R;
import com.example.job.ResumeComplete;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.example.job.event_details;


class ItemViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView person_name;
    public TextView person_phone;
    public TextView person_mail;
    public TextView date;
    public TextView status;
    public TextView time;
    public ImageButton chat;
    public ImageButton mail;

    public Button accept;
    public Button reject;
    IRecyclerClickListener listener;

    public ItemViewHolder2(@NonNull View itemView) {
        super(itemView);

        person_name = (TextView) itemView.findViewById(R.id.pplname);
        person_phone = (TextView) itemView.findViewById(R.id.pplphone);
        chat = (ImageButton) itemView.findViewById(R.id.itempplgoingchat);
        mail = (ImageButton) itemView.findViewById(R.id.itempplgoingmail);
        person_mail = (TextView) itemView.findViewById(R.id.pplmail);
        status = (TextView) itemView.findViewById(R.id.cstatus);
        accept = itemView.findViewById(R.id.accept);
        reject = itemView.findViewById(R.id.reject);
        itemView.setOnClickListener(this);
    }

    public void setListener(IRecyclerClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        listener.onItemCliickListener(v, getAdapterPosition());
    }
}

public class ListofPplGoingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM_ = 0, VIEW_TYPE_LOADING = 1;
    ILoadMore iLoadMore;
    boolean isLoading;
    Activity activity;
    List<User1> items;
    int visibleThreshold = 4;
    int lastVisibleItem, totalItemCount;

    public ListofPplGoingAdapter(RecyclerView recyclerView, Activity activity, List<User1> items) {
        this.activity = activity;
        this.items = items;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (iLoadMore != null) {
                        iLoadMore.onLoadMore();

                        isLoading = true;
                    }
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM_;
    }

    public void setiLoadMore(ILoadMore iLoadMore) {
        this.iLoadMore = iLoadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM_) {
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.pplgoingeventlist, parent, false);
            return new ItemViewHolder2(view);
        } else if (viewType == VIEW_TYPE_LOADING) {

            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.pplgoingeventlist, parent, false);
            return new ItemViewHolder2(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder2) {

            final User1 item = items.get(position);
            final ItemViewHolder2 viewHolder = (ItemViewHolder2) holder;
            FirebaseDatabase firebaseDatabase;
            final DatabaseReference jobapplyref, dbUserRef;

            firebaseDatabase = FirebaseDatabase.getInstance();
            dbUserRef = firebaseDatabase.getReference("Resume/" + item.getPhonenumber());
            jobapplyref = firebaseDatabase.getReference("JobApply/" + Common.selectedJob.getKey());

            Log.d("users", items.get(position).getName());
            viewHolder.person_name.setText(items.get(position).getName());
            viewHolder.person_phone.setText(items.get(position).getPhonenumber());
            viewHolder.person_mail.setText(items.get(position).getEmail());
            final String tmpphonme = items.get(position).getPhonenumber().substring(1);
            final String tmpmail = items.get(position).getEmail();

            Log.d("whnum", tmpphonme);


            viewHolder.chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendmsgtowhatsapp = new Intent(Intent.ACTION_VIEW);
                    //TODO:: Replace msg text
                    String msg = "Hey!";
                    sendmsgtowhatsapp.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + tmpphonme + "&text=" + msg));
                    activity.startActivity(sendmsgtowhatsapp);
                }
            });
            viewHolder.mail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:" + tmpmail));
                    activity.startActivity(intent);

                }
            });


            viewHolder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jobapplyref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            List<JobApply> tmp1 = new ArrayList<>();
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

//                    Log.e("ann",announcements.toString());
                                JobApply tmp = dataSnapshot1.getValue(JobApply.class);
                                assert tmp != null;
                                tmp1.add(tmp);
                            }
                            List<JobApply> tmp2 = new ArrayList<>();

                            int l = tmp1.size();
                            for (int i = 0; i < l; i++) {
                                JobApply mm = tmp1.get(i);
                                if (mm.getPhone().equals(item.getPhonenumber())) {
                                    tmp2.add(new JobApply(item.getPhonenumber(), "Accepted"));
                                } else {
                                    tmp2.add(mm);
                                }
                            }
                            jobapplyref.setValue(tmp2);
                            viewHolder.status.setText("Status : Accepted");

                            Toast.makeText(activity, "Updated", Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
            viewHolder.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jobapplyref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            List<JobApply> tmp1 = new ArrayList<>();
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

//                    Log.e("ann",announcements.toString());
                                JobApply tmp = dataSnapshot1.getValue(JobApply.class);
                                assert tmp != null;
                                tmp1.add(tmp);
                            }
                            List<JobApply> tmp2 = new ArrayList<>();

                            int l = tmp1.size();
                            for (int i = 0; i < l; i++) {
                                JobApply mm = tmp1.get(i);
                                if (mm.getPhone().equals(item.getPhonenumber())) {
                                    tmp2.add(new JobApply(item.getPhonenumber(), "Rejected"));
                                } else {
                                    tmp2.add(mm);
                                }
                            }
                            jobapplyref.setValue(tmp2);
                            viewHolder.status.setText("Status : Rejected");

                            Toast.makeText(activity, "Updated", Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });


            ((ItemViewHolder2) holder).setListener(new IRecyclerClickListener() {
                @Override
                public void onItemCliickListener(View view, int pos) {


                    dbUserRef.orderByKey().addListenerForSingleValueEvent
                            (new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {


                                        Common.userresume = dataSnapshot.getValue(UserResume.class);


                                        Intent intent = new Intent(activity, ResumeComplete.class);
                                        activity.startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


//                    Toast.makeText(activity, "Click on " + items.get(pos).getName(), Toast.LENGTH_SHORT).show();
                }
            });


            jobapplyref.addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

//                    Log.e("ann",announcements.toString());
                        JobApply tmp = dataSnapshot1.getValue(JobApply.class);
                        assert tmp != null;
                        if (tmp.getPhone().equals(items.get(position).getPhonenumber())) {
                            viewHolder.status.setText("Status : " + tmp.getStatus());

                            if (!viewHolder.status.getText().toString().contains("Pending")) {
                                viewHolder.accept.setVisibility(View.GONE);
                                viewHolder.reject.setVisibility(View.GONE);
                            }

                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if (holder instanceof LoadingEvent) {
            LoadingEvent loadEvent = (LoadingEvent) holder;
            loadEvent.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setLoaded() {
        isLoading = false;
    }
}
