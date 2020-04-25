package com.example.job.Adapter;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.job.Common;
import com.example.job.Interface.ILoadMore;
import com.example.job.Interface.IRecyclerClickListener;
import com.example.job.Model.User;
import com.example.job.R;
//import com.example.job.event_details;


class ItemViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView person_name;
    public TextView person_phone;
    public TextView person_mail;
    public TextView date;
    public TextView time;
    public ImageButton chat;
    public ImageButton mail;

    public Button btnVolList;
    IRecyclerClickListener listener;

    public ItemViewHolder2(@NonNull View itemView) {
        super(itemView);

        person_name = (TextView) itemView.findViewById(R.id.pplname);
        person_phone = (TextView) itemView.findViewById(R.id.pplphone);
        chat = (ImageButton) itemView.findViewById(R.id.itempplgoingchat);
        mail = (ImageButton) itemView.findViewById(R.id.itempplgoingmail);
        person_mail = (TextView) itemView.findViewById(R.id.pplmail);
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
    List<User> items;
    int visibleThreshold = 4;
    int lastVisibleItem, totalItemCount;

    public ListofPplGoingAdapter(RecyclerView recyclerView, Activity activity, List<User> items) {
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder2) {

            final User item = items.get(position);
            ItemViewHolder2 viewHolder = (ItemViewHolder2) holder;

            Log.d("users", items.get(position).getName());
            viewHolder.person_name.setText(items.get(position).getName());
            viewHolder.person_phone.setText(items.get(position).getUserphone());
            viewHolder.person_mail.setText(items.get(position).getEmail());
            final String tmpphonme = items.get(position).getUserphone().substring(1);
            final String tmpmail = items.get(position).getEmail();

            Log.d("whnum", tmpphonme);
            viewHolder.chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendmsgtowhatsapp = new Intent(Intent.ACTION_VIEW);
                    //TODO:: Replace msg text
                    String msg = "Hey!, I am " + Common.currentuser.getName();
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
            ((ItemViewHolder2) holder).setListener(new IRecyclerClickListener() {
                @Override
                public void onItemCliickListener(View view, int pos) {
                    //Common.selectedEvent = items.get(pos);
                    //   Intent intent = new Intent(activity, EventDetail.class);
                    //  activity.startActivity(intent);

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
