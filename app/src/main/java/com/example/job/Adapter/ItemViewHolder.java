package com.example.job.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.job.Interface.IRecyclerClickListener;
import com.example.job.R;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView event_name;
    public TextView ngo_name;
    public TextView date;
    public TextView time;
    public TextView city;
    public Button btnVolList;
    public ImageView imgevent;
    IRecyclerClickListener listener;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        event_name = (TextView) itemView.findViewById(R.id.event_name);
        date = (TextView) itemView.findViewById(R.id.event_date);
        ngo_name = (TextView) itemView.findViewById(R.id.ngo_name);
        city = (TextView) itemView.findViewById(R.id.event_city);
        time = (TextView) itemView.findViewById(R.id.event_time);
        imgevent = itemView.findViewById(R.id.itemeventImage);
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

