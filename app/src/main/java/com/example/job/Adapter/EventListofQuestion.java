package com.example.job.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.job.Interface.ILoadMore;
import com.example.job.Interface.IRecyclerClickListener;
import com.example.job.R;
//import com.example.job.event_details;


class ItemQuestionList extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvquestion;

    IRecyclerClickListener listener;

    public ItemQuestionList(@NonNull View itemView) {
        super(itemView);

        tvquestion = (TextView) itemView.findViewById(R.id.tveventquestion);
//        itemView.setOnClickListener(this);
    }

    public void setListener(IRecyclerClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        listener.onItemCliickListener(v, getAdapterPosition());
    }
}

public class EventListofQuestion extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM_ = 0, VIEW_TYPE_LOADING = 1;
    ILoadMore iLoadMore;
    boolean isLoading;
    Activity activity;
    List<String> items;
    int visibleThreshold = 4;
    int lastVisibleItem, totalItemCount;

    public EventListofQuestion(RecyclerView recyclerView, Activity activity, List<String> items) {
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
                    .inflate(R.layout.item_questionlist, parent, false);
            return new ItemQuestionList(view);
        } else if (viewType == VIEW_TYPE_LOADING) {

            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_questionlist, parent, false);
            return new ItemQuestionList(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemQuestionList) {

            ItemQuestionList viewHolder = (ItemQuestionList) holder;
            viewHolder.tvquestion.setText(items.get(position));


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
