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
import com.example.job.Model.Annoucement;
import com.example.job.R;
//import com.example.job.event_details;


class ItemViewHolderAnnouncement extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView announcementtitle;
    public TextView announcemendescription;
    public TextView announcemendate;
    public TextView announcementime;

    IRecyclerClickListener listener;

    public ItemViewHolderAnnouncement(@NonNull View itemView) {
        super(itemView);

        announcementtitle = (TextView) itemView.findViewById(R.id.itemAnnouncementTitle);
        announcemendescription = (TextView) itemView.findViewById(R.id.itemAnnouncementDescription);
        announcemendate = (TextView) itemView.findViewById(R.id.itemAnnouncementDate);
        announcementime = (TextView) itemView.findViewById(R.id.itemAnnouncementTime);
    }

    public void setListener(IRecyclerClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        listener.onItemCliickListener(v, getAdapterPosition());
    }
}

public class ViewAnnouncementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM_ = 0, VIEW_TYPE_LOADING = 1;
    ILoadMore iLoadMore;
    boolean isLoading;
    Activity activity;
    List<Annoucement> items;
    int visibleThreshold = 4;
    int lastVisibleItem, totalItemCount;

    public ViewAnnouncementAdapter(RecyclerView recyclerView, Activity activity, List<Annoucement> items) {
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
                    .inflate(R.layout.announcement_item, parent, false);
            return new ItemViewHolderAnnouncement(view);
        } else if (viewType == VIEW_TYPE_LOADING) {

            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.announcement_item, parent, false);
            return new ItemViewHolderAnnouncement(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolderAnnouncement) {

            final Annoucement item = items.get(position);
            ItemViewHolderAnnouncement viewHolder = (ItemViewHolderAnnouncement) holder;

            viewHolder.announcementtitle.setText(items.get(position).getTitle());
            viewHolder.announcemendescription.setText(items.get(position).getDecsription());
            viewHolder.announcemendate.setText(items.get(position).getDate());
            viewHolder.announcementime.setText(items.get(position).getTime());

//            ((ItemViewHolderAnnouncement) holder).setListener(new IRecyclerClickListener() {
//                @Override
//                public void onItemCliickListener(View view, int pos) {
//                    //Common.selectedEvent = items.get(pos);
//                 //   Intent intent = new Intent(activity, EventDetail.class);
//                  //  activity.startActivity(intent);
//
//                    Toast.makeText(activity, "Click on "+items.get(pos).getName(), Toast.LENGTH_SHORT).show();
//                }
//            });
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
