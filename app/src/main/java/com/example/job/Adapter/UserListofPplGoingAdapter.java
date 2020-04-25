package com.example.job.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.job.Common;
import com.example.job.Interface.ILoadMore;
import com.example.job.Interface.IRecyclerClickListener;
import com.example.job.Model.User;
import com.example.job.R;
import com.example.job.Userviewpplgoingprofile;
//import com.example.job.event_details;


class ItemViewHolder4 extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView person_name;

    public ImageButton chat;
    public ImageButton mail;

    public Button btnVolList;
    IRecyclerClickListener listener;

    public ItemViewHolder4(@NonNull View itemView) {
        super(itemView);

        person_name = (TextView) itemView.findViewById(R.id.itemuserpplgoingname);
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

public class UserListofPplGoingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM_ = 0, VIEW_TYPE_LOADING = 1;
    ILoadMore iLoadMore;
    boolean isLoading;
    Activity activity;
    List<User> items;
    int visibleThreshold = 4;
    int lastVisibleItem, totalItemCount;

    public UserListofPplGoingAdapter(RecyclerView recyclerView, Activity activity, List<User> items) {
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
                    .inflate(R.layout.itempplgoinguser, parent, false);
            return new ItemViewHolder4(view);
        } else if (viewType == VIEW_TYPE_LOADING) {

            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.itempplgoinguser, parent, false);
            return new ItemViewHolder4(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder4) {

            final User item = items.get(position);
            ItemViewHolder4 viewHolder = (ItemViewHolder4) holder;

            viewHolder.person_name.setText(items.get(position).getName());

            ((ItemViewHolder4) holder).setListener(new IRecyclerClickListener() {
                @Override
                public void onItemCliickListener(View view, int pos) {
                    Intent intent = new Intent(activity, Userviewpplgoingprofile.class);
                    intent.putExtra("username", item.getName());
                    intent.putExtra("usermail", item.getEmail());
                    intent.putExtra("userphone", item.getUserphone());
                    intent.putExtra("usertotalconnection", (item.getConnection().size() - 1) + "");
//                    intent.putExtra("userpendingconnection", (item.getPendingconnection()));
                    intent.putExtra("userabout", (item.getAbout()));
                    intent.putExtra("userwork", (item.getWork()));
                    intent.putExtra("useraddress", (item.getBussinessaddress()));
                    Common.selecetedUser = item;
                    activity.startActivity(intent);

//                    Toast.makeText(activity, "Click on " + items.get(pos).getName(), Toast.LENGTH_SHORT).show();
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
