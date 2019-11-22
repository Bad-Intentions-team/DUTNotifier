package com.example.dutnotifier;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.dutnotifier.model.modelNoti;

import java.util.ArrayList;

public class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.NotiHolder> {
    private Activity activity;
    private ArrayList<modelNoti> listNoti;

    public NotiAdapter(Activity activity, ArrayList<modelNoti> listNoti) {
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
        final modelNoti notification = listNoti.get(position);
        holder.tvDate.setText(notification.getDate());
        holder.tvTitle.setText(notification.getTitle());
        holder.tvContent.setText(notification.getContent());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                activity.startActivity(new Intent(activity,DetailArticleActivity.class).putExtra("Article",article));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listNoti.size();
    }

    class NotiHolder extends RecyclerView.ViewHolder{

        private TextView tvDate;
        private TextView tvTitle;
        private TextView tvContent;
        public NotiHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
