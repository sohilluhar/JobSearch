//package com.example.job.Adapter;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ProgressBar;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.List;
//
//import com.example.job.Common;
//import com.example.job.EventDetail;
//import com.example.job.Interface.ILoadMore;
//import com.example.job.Interface.IRecyclerClickListener;
//import com.example.job.Model.Event;
//import com.example.job.Model.Job;
//import com.example.job.R;
//
//
//class LoadingEvent1 extends RecyclerView.ViewHolder {
//
//    public ProgressBar progressBar;
//
//    public LoadingEvent1(@NonNull View itemView) {
//        super(itemView);
//        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
//    }
//}
//
//public class FeturedEventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    private final int VIEW_TYPE_ITEM_ = 0, VIEW_TYPE_LOADING = 1;
//    ILoadMore iLoadMore;
//    boolean isLoading;
//    Activity activity;
//    List<Event> items;
//    int visibleThreshold = 4;
//    int lastVisibleItem, totalItemCount;
//    RecyclerView recyclerView;
//
//    public FeturedEventAdapter(RecyclerView recyclerView, Activity activity, List<Event> items) {
//        this.activity = activity;
//        this.items = items;
//
//        this.recyclerView = recyclerView;
//        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                totalItemCount = linearLayoutManager.getItemCount();
//                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                    if (iLoadMore != null) {
//                        iLoadMore.onLoadMore();
//
//                        isLoading = true;
//                    }
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM_;
//    }
//
//    public void setiLoadMore(ILoadMore iLoadMore) {
//        this.iLoadMore = iLoadMore;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == VIEW_TYPE_ITEM_) {
//            View view = LayoutInflater.from(activity)
//                    .inflate(R.layout.event_list, parent, false);
//
//            //     RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.eventRecyclerFeatured);
//            int width = recyclerView.getWidth();
//            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//            layoutParams.width = (int) (width * 0.8);
//            view.setLayoutParams(layoutParams);
//            return new ItemViewHolder(view);
//        } else if (viewType == VIEW_TYPE_LOADING) {
//
//            View view = LayoutInflater.from(activity)
//                    .inflate(R.layout.event_list, parent, false);
//            return new ItemViewHolder(view);
//        }
//        return null;
//    }
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof ItemViewHolder) {
//
//            final Job item = items.get(position);
//            ItemViewHolder viewHolder = (ItemViewHolder) holder;
//
//            viewHolder.companyname.setText(item.getCompanyname());
//            viewHolder.jobname.setText(item.getName());
//            viewHolder.jobexp.setText(item.getWorkexperience());
//            viewHolder.joblocation.setText(item.getLocation());
//            viewHolder.jobskill.setText(item.getSkill());
//
//            ((ItemViewHolder) holder).setListener(new IRecyclerClickListener() {
//                @Override
//                public void onItemCliickListener(View view, int pos) {
//                    Common.selectedEvent = items.get(pos);
//                    Intent intent = new Intent(activity, EventDetail.class);
//                    activity.startActivity(intent);
//
////                    Toast.makeText(activity, "Click", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//
//            Glide.with(activity).load(items.get(position).getImgurl()).centerCrop().into(viewHolder.imgevent);
//
//        } else if (holder instanceof LoadingEvent1) {
//            LoadingEvent1 loadEvent = (LoadingEvent1) holder;
//            loadEvent.progressBar.setIndeterminate(true);
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    public void setLoaded() {
//        isLoading = false;
//    }
//}
