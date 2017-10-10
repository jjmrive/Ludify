package com.jjmrive.ludify;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjmrive.ludify.visit.Prize;
import com.jjmrive.ludify.visit.Visit;

import java.util.List;

public class PrizesFragmentAdapter extends RecyclerView.Adapter<PrizesFragmentAdapter.ViewHolder>{

    private Visit visit;
    private List<Prize> prizesList;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView status;
        public TextView description;
        public TextView pts;

        public ViewHolder(View view){
            super(view);
            status = (ImageView) view.findViewById(R.id.prizeStatus);
            description = (TextView) view.findViewById(R.id.prizeDescription);
            pts = (TextView) view.findViewById(R.id.prizePts);
        }
    }

    public PrizesFragmentAdapter(Visit visit){
        this.visit = visit;
        this.prizesList = visit.getPrizes();
    }

    @Override
    public PrizesFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prize_card, parent, false);

        return new PrizesFragmentAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (prizesList.get(position).getPts() <= visit.getScore()){
            holder.status.setImageResource(R.drawable.ic_done_black_64dp);
        } else {
            holder.status.setImageResource(R.drawable.ic_clear_black_64dp);
        }
        holder.description.setText(prizesList.get(position).getName());
        holder.pts.setText(String.valueOf(prizesList.get(position).getPts()));
    }

    @Override
    public int getItemCount() {
        return prizesList.size();
    }
}
