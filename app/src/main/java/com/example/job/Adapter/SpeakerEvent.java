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

import com.example.job.Common;
import com.example.job.Interface.ILoadMore;
import com.example.job.Interface.IRecyclerClickListener;
import com.example.job.Model.Event;
import com.example.job.R;
import com.example.job.ViewEventQuestion;
//import com.example.job.event_details;

class ItemViewHolder7 extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView event_name;
    public TextView ngo_name;
    public TextView date;
    public TextView time;

    public Button btnVolList;
    IRecyclerClickListener listener;

    public ItemViewHolder7(@NonNull View itemView) {
        super(itemView);

        event_name = (TextView) itemView.findViewById(R.id.event_name);
        date = (TextView) itemView.findViewById(R.id.event_date);
        ngo_name = (TextView) itemView.findViewById(R.id.ngo_name);
        time = (TextView) itemView.findViewById(R.id.event_city);
//        btnVolList = (Button) itemView.findViewById(R.id.viewvolreq);
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

public class SpeakerEvent extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM_ = 0, VIEW_TYPE_LOADING = 1;
    ILoadMore iLoadMore;
    boolean isLoading;
    Activity activity;
    List<Event> items;
    int visibleThreshold = 4;
    int lastVisibleItem, totalItemCount;

    public SpeakerEvent(RecyclerView recyclerView, Activity activity, List<Event> items) {
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
                    .inflate(R.layout.item_speakerevent, parent, false);
            return new ItemViewHolder7(view);
        } else if (viewType == VIEW_TYPE_LOADING) {

            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_speakerevent, parent, false);
            return new ItemViewHolder7(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder7) {

            final Event item = items.get(position);
            ItemViewHolder7 viewHolder = (ItemViewHolder7) holder;

            viewHolder.ngo_name.setText(items.get(position).getNgo_name());
            viewHolder.event_name.setText(items.get(position).getEvent_name());
            viewHolder.date.setText(items.get(position).getDate());
            viewHolder.time.setText(items.get(position).getTime());

            ((ItemViewHolder7) holder).setListener(new IRecyclerClickListener() {
                @Override
                public void onItemCliickListener(View view, int pos) {
                    Common.selectedEvent = items.get(pos);
                    Intent intent = new Intent(activity, ViewEventQuestion.class);
                    activity.startActivity(intent);

//                    Toast.makeText(activity, "Click", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (holder instanceof LoadingEvent) {
            LoadingEvent loadEvent = (com.example.job.Adapter.LoadingEvent) holder;
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
