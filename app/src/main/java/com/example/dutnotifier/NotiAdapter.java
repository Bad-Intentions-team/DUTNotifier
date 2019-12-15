package com.example.dutnotifier;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.dutnotifier.model.ModelNoti;

import java.util.ArrayList;

public class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.NotiHolder> {
    private Activity activity;
    private ArrayList<ModelNoti> listNoti;

    public NotiAdapter(Activity activity, ArrayList<ModelNoti> listNoti) {
        this.activity = activity;
        this.listNoti = listNoti;
    }

    @Override
    public NotiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new NotiHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotiHolder holder, int position) {
        final ModelNoti noti = listNoti.get(position);
        holder.mTvTitle.setText(noti.getTitle());
        holder.mTvContent.setText(noti.getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra("TITLE",noti.getTitle());
                intent.putExtra("CONTENT",noti.getContent());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNoti.size();
    }

    class NotiHolder extends RecyclerView.ViewHolder{
        private TextView mTvTitle;
        private TextView mTvContent;
        public NotiHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
