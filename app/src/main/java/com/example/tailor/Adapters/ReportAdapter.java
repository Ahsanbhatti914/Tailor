package com.example.tailor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tailor.Models.OrderDetails;
import com.example.tailor.R;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.viewHolder>{
    ArrayList<OrderDetails> orders;
    Context mContext;
    int sno = 1;

    public ReportAdapter(Context mContext, ArrayList<OrderDetails> orders){
        this.mContext = mContext;
        this.orders = orders;
    }
    @NonNull
    @Override
    public ReportAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.monthlyreport,parent,false);
        return new ReportAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.viewHolder holder, int position) {
        OrderDetails order = orders.get(position);
        holder.sinoTv.setText(""+sno);
        holder.idTv.setText(order.getId());
        holder.nameTv.setText(order.getName());
        holder.qntyTv.setText(order.getSuit_Quantity());
        holder.dDateTv.setText(order.getDelivered_date());
        holder.totalTv.setText(order.getTotal_Payment());
        sno++;
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView sinoTv,idTv,nameTv,qntyTv,dDateTv,totalTv;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            sinoTv = itemView.findViewById(R.id.siNoTvReport);
            idTv = itemView.findViewById(R.id.idTvReport);
            nameTv = itemView.findViewById(R.id.nameTvReport);
            qntyTv = itemView.findViewById(R.id.qntyTvReport);
            dDateTv = itemView.findViewById(R.id.dDtaeTvReport);
            totalTv = itemView.findViewById(R.id.totalTvReport);
        }
    }
}
