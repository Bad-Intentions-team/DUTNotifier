package com.example.dutnotifier;

import android.app.Activity;
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
        final ModelNoti notification = listNoti.get(position);
        holder.tvTitle.setText(notification.getTitle());
        holder.tvContent.setText(notification.getContent());
    }

    @Override
    public int getItemCount() {
        return listNoti.size();
    }

    class NotiHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvContent;
        public NotiHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
