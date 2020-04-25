package com.example.job.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.job.AddAnnoucement;
import com.example.job.AddEventSpeaker;
import com.example.job.AddSocialPhoto;
import com.example.job.AddSponsorImage;
import com.example.job.Common;
import com.example.job.Interface.ILoadMore;
import com.example.job.Interface.IRecyclerClickListener;
import com.example.job.Model.Event;
import com.example.job.R;
import com.example.job.ViewListofPPlGoing;


class NGOItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView event_name;
    public TextView ngo_name;
    public TextView date;
    public TextView time;

    public Button btnVolList;
    public Button pplList;
    public Button btnaddAnnoucement;
    public Button btnaddSocialPhoto;
    public Button btneventaddspeaker;
    public Button btnAddSponserImage;
    IRecyclerClickListener listener;

    public NGOItemViewHolder(@NonNull View itemView) {
        super(itemView);

        event_name = (TextView) itemView.findViewById(R.id.ngoevent_name);
        date = (TextView) itemView.findViewById(R.id.ngoevent_date);
//        ngo_name = (TextView) itemView.findViewById(R.id.ngo_name);
        time = (TextView) itemView.findViewById(R.id.ngoevent_time);
        btnVolList = (Button) itemView.findViewById(R.id.ngoviewvolreq);
        pplList = (Button) itemView.findViewById(R.id.ngoviewppllist);
        btnaddAnnoucement = (Button) itemView.findViewById(R.id.addAnnoucement);
        btnaddSocialPhoto = (Button) itemView.findViewById(R.id.addsocialphoto);
        btneventaddspeaker = (Button) itemView.findViewById(R.id.btneventaddspeaker);
        btnAddSponserImage = (Button) itemView.findViewById(R.id.btnAddSponserImage);


        //   itemView.setOnClickListener(this);
//        pplList.setOnClickListener(this);
    }

    public void setListener(IRecyclerClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        listener.onItemCliickListener(v, getAdapterPosition());
    }
}


public class Org_View_Eventadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM_ = 0, VIEW_TYPE_LOADING = 1;
    ILoadMore iLoadMore;
    boolean isLoading;
    Activity activity;
    List<Event> items;
    int visibleThreshold = 4;
    int lastVisibleItem, totalItemCount;

    public Org_View_Eventadapter(RecyclerView recyclerView, Activity activity, List<Event> items) {
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
                    .inflate(R.layout.org_event_list, parent, false);
            return new NGOItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {

            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.org_event_list, parent, false);
            return new NGOItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NGOItemViewHolder) {

            final Event item = items.get(position);
            NGOItemViewHolder viewHolder = (NGOItemViewHolder) holder;

//            viewHolder.ngo_name.setText(items.get(position).getNgo_name());
            viewHolder.event_name.setText(items.get(position).getEvent_name());
            viewHolder.date.setText(items.get(position).getDate());
            viewHolder.time.setText(items.get(position).getTime());
            viewHolder.pplList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Common.selectedEvent = items.get(position);
//                    Toast.makeText(activity, "Clicked on " + items.get(position).getEvent_name(), Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(activity, ViewListofPPlGoing.class);
                    activity.startActivity(intent1);
//

                }
            });
            viewHolder.btnaddAnnoucement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Common.selectedEvent = items.get(position);
//                    Toast.makeText(activity, "Clicked on " + items.get(position).getEvent_name(), Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(activity, AddAnnoucement.class);
                    activity.startActivity(intent1);
//

                }
            });
            viewHolder.btnaddSocialPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Common.selectedEvent = items.get(position);
//                    Toast.makeText(activity, "Clicked on " + items.get(position).getEvent_name(), Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(activity, AddSocialPhoto.class);
                    activity.startActivity(intent1);
//

                }
            });
            viewHolder.btnAddSponserImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Common.selectedEvent = items.get(position);
//                    Toast.makeText(activity, "Clicked on " + items.get(position).getEvent_name(), Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(activity, AddSponsorImage.class);
                    activity.startActivity(intent1);
//

                }
            });

            viewHolder.btneventaddspeaker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Common.selectedEvent = items.get(position);
//                    Toast.makeText(activity, "Clicked on " + items.get(position).getEvent_name(), Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(activity, AddEventSpeaker.class);
                    activity.startActivity(intent1);
//

                }
            });


            if (items.get(position).getVol_req().equals("Yes")) {
                viewHolder.btnVolList.setVisibility(View.VISIBLE);
            }

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
