package com.example.job.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.example.job.Common;
import com.example.job.EventDetail;
import com.example.job.Interface.ILoadMore;
import com.example.job.Interface.IRecyclerClickListener;
import com.example.job.Model.Event;
import com.example.job.R;
//import com.example.job.event_details;

class LoadingEvent extends RecyclerView.ViewHolder {

    public ProgressBar progressBar;

    public LoadingEvent(@NonNull View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }
}


public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM_ = 0, VIEW_TYPE_LOADING = 1;
    ILoadMore iLoadMore;
    boolean isLoading;
    Activity activity;
    List<Event> items;
    int visibleThreshold = 4;
    int lastVisibleItem, totalItemCount;

    public MyAdapter(RecyclerView recyclerView, Activity activity, List<Event> items) {
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
                    .inflate(R.layout.event_list, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {

            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.event_list, parent, false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {

            final Event item = items.get(position);
            ItemViewHolder viewHolder = (ItemViewHolder) holder;

            String date = items.get(position).getDate();
            viewHolder.ngo_name.setText(items.get(position).getNgo_name());
            viewHolder.event_name.setText(items.get(position).getEvent_name());
            viewHolder.date.setText(date.substring(0, 7));
            viewHolder.city.setText(items.get(position).getCity());
            viewHolder.time.setText(items.get(position).getTime());

            ((ItemViewHolder) holder).setListener(new IRecyclerClickListener() {
                @Override
                public void onItemCliickListener(View view, int pos) {
                    Common.selectedEvent = items.get(pos);
                    Intent intent = new Intent(activity, EventDetail.class);
                    activity.startActivity(intent);

//                    Toast.makeText(activity, "Click", Toast.LENGTH_SHORT).show();
                }
            });


            Glide.with(activity).load(items.get(position).getImgurl()).centerCrop().into(viewHolder.imgevent);

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
