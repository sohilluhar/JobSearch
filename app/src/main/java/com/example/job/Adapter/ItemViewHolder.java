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

    public TextView companyname;
    public TextView jobname;
    public TextView jobexp;
    public TextView joblocation;
    public TextView jobskill;
    public TextView jobtype;

    IRecyclerClickListener listener;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        companyname = itemView.findViewById(R.id.itemcompanyname);
        jobname = itemView.findViewById(R.id.itemjobname);
        jobexp = itemView.findViewById(R.id.itemjobexperience);
        joblocation = itemView.findViewById(R.id.itemjoblocation);
        jobskill = itemView.findViewById(R.id.itemjobskill);
        jobtype = itemView.findViewById(R.id.itemjobtype);
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

