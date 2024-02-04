package com.example.tailor.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tailor.Models.OrderDetails;
import com.example.tailor.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MonthlyReportAdapter extends FirebaseRecyclerAdapter<OrderDetails, MonthlyReportAdapter.reportViewholder> {

    int sno = 1;
    public MonthlyReportAdapter(@NonNull FirebaseRecyclerOptions<OrderDetails> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MonthlyReportAdapter.reportViewholder holder, int position, @NonNull OrderDetails order) {
        holder.sinoTv.setText(""+sno);
        holder.idTv.setText(order.getId());
        holder.nameTv.setText(order.getName());
        holder.qntyTv.setText(order.getSuit_Quantity());
        holder.dDateTv.setText(order.getDelivered_date());
        holder.totalTv.setText(order.getTotal_Payment());
        sno++;
    }

    @NonNull
    @Override
    public MonthlyReportAdapter.reportViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.monthlyreport,parent,false);
        return new MonthlyReportAdapter.reportViewholder(view);
    }

    public class reportViewholder extends RecyclerView.ViewHolder {
        TextView sinoTv,idTv,nameTv,qntyTv,dDateTv,totalTv;
        public reportViewholder(@NonNull View itemView) {
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
