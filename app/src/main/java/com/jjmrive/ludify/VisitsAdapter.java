package com.jjmrive.ludify;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjmrive.ludify.visit.Visit;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VisitsAdapter extends RecyclerView.Adapter<VisitsAdapter.MyViewHolder> {

    private Context context;
    private List<Visit> visitsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView photo, statusIcon;
        public TextView name, date, score, status;

        public MyViewHolder(View view) {
            super(view);
            photo = (ImageView) view.findViewById(R.id.photo);
            name = (TextView) view.findViewById(R.id.name);
            date = (TextView) view.findViewById(R.id.date);
            score = (TextView) view.findViewById(R.id.score);
            status = (TextView) view.findViewById(R.id.status);
            statusIcon = (ImageView) view.findViewById(R.id.statusIcon);
        }
    }

    public VisitsAdapter (Context context, List<Visit> visitsList){
        this.context = context;
        this.visitsList = visitsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visit_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Visit visit = visitsList.get(position);

        holder.name.setText(visit.getName());
        holder.date.setText(visit.getDate());
        holder.score.setText(String.valueOf(visit.getScore()) + " pts");
        if (!visit.getStatus()){
            holder.status.setText(R.string.pending_status);
            holder.statusIcon.setImageResource(R.drawable.ic_timelapse_black_24dp);
        } else {
            holder.status.setText(R.string.ended_status);
            holder.statusIcon.setImageResource(R.drawable.ic_done_black_24dp);
        }

        Picasso.with(context).load(visit.getUrlPhoto()).into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return visitsList.size();
    }
}
