package com.example.job.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.example.job.Interface.ILoadMore;
import com.example.job.Interface.IRecyclerClickListener;
import com.example.job.R;
//import com.example.job.event_details;


class ItemViewHolder5 extends RecyclerView.ViewHolder implements View.OnClickListener {

    public Button btneventimgshare, btneventimgdowload;
    ImageView iveventimg;
    IRecyclerClickListener listener;

    public ItemViewHolder5(@NonNull View itemView) {
        super(itemView);

        iveventimg = itemView.findViewById(R.id.ivitemeventphoto);
        btneventimgshare = itemView.findViewById(R.id.btneventphotoshare);
        btneventimgdowload = itemView.findViewById(R.id.btneventphotodownload);
    }

    public void setListener(IRecyclerClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        listener.onItemCliickListener(v, getAdapterPosition());
    }
}

public class EventImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM_ = 0, VIEW_TYPE_LOADING = 1;
    ILoadMore iLoadMore;
    boolean isLoading;
    Activity activity;
    List<String> items;
    int visibleThreshold = 4;
    int lastVisibleItem, totalItemCount;

    public EventImageAdapter(RecyclerView recyclerView, Activity activity, List<String> items) {
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


    public void shareImage(String url, final Context context) {
        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context));
                //TODO:: Replace Text Here
//                i.putExtra(Intent.EXTRA_TEXT, "Check this Photo");
                context.startActivity(Intent.createChooser(i, "Share Image"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }


            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }


    public Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = FileProvider.getUriForFile(context, "com.example.job.provider", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public void SaveImagetoGallery(String url, final Context context) {
        Picasso.get().load(url).into(new Target() {
                                         @Override
                                         public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                             try {
                                                 String root = Environment.getExternalStorageDirectory().toString();
                                                 File myDir = new File(root + "/EventImages");
                                                 if (!myDir.exists()) {
                                                     boolean res =
                                                             myDir.mkdirs();

                                                 }

                                                 String name = "IMG_" + new Date().getTime() + ".jpg";
                                                 myDir = new File(myDir, name);
                                                 FileOutputStream out = new FileOutputStream(myDir);
                                                 bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

                                                 out.flush();
                                                 out.close();

                                                 Toast.makeText(context, "Save Successfully in EventImages", Toast.LENGTH_SHORT).show();
                                             } catch (Exception e) {
                                                 // some action
                                                 Log.e("img", e.toString());

                                             }
                                         }

                                         @Override
                                         public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                         }


                                         @Override
                                         public void onPrepareLoad(Drawable placeHolderDrawable) {
                                         }
                                     }
        );
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
                    .inflate(R.layout.itemsocialimglist, parent, false);
            return new ItemViewHolder5(view);
        } else if (viewType == VIEW_TYPE_LOADING) {

            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.itemsocialimglist, parent, false);
            return new ItemViewHolder5(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder5) {

            final String item = items.get(position);
            ItemViewHolder5 viewHolder = (ItemViewHolder5) holder;

//            viewHolder.person_name.setText(items.get(position).getName());
            if (!item.equals(" "))
                Glide.with(activity).load(item).into(viewHolder.iveventimg);
            viewHolder.btneventimgshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareImage(item, activity);
                }
            });
            viewHolder.btneventimgdowload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveImagetoGallery(item, activity);
                }
            });

            ((ItemViewHolder5) holder).setListener(new IRecyclerClickListener() {
                @Override
                public void onItemCliickListener(View view, int pos) {
                    //Common.selectedEvent = items.get(pos);
                    //   Intent intent = new Intent(activity, EventDetail.class);
                    //  activity.startActivity(intent);

                    Toast.makeText(activity, "Click on ", Toast.LENGTH_SHORT).show();
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
