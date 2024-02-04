package com.example.tailor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tailor.Models.OrderDetails;
import com.example.tailor.R;

import java.util.ArrayList;

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.viewHolder> {
    private Context context;
    private ArrayList<OrderDetails> pendingOrderList;

    public PendingOrderAdapter(FragmentActivity context, ArrayList<OrderDetails> pendingOrderList){
        this.context = context;
        this.pendingOrderList = pendingOrderList;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pendingorder,parent,false);
        return new PendingOrderAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
       // Customer customer = customerList.get(position);
        OrderDetails order = pendingOrderList.get(position);
        holder.ID.setText("#" + order.getId());
        holder.Name.setText(order.getName());
        holder.Number.setText(order.getNumber());
        holder.orderStatus.setText(order.getOrder_Status());
        holder.dueDate.setText(order.getSelected_Date());
        holder.startedDate.setText(order.getStart_date());
        holder.suitQuantity.setText(order.getSuit_Quantity());
        holder.totalPayment.setText(order.getTotal_Payment());
        holder.paidPayment.setText(order.getPaid_Payment());
    }

    @Override
    public int getItemCount() {
        return pendingOrderList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView ID, Name, Number,orderStatus,dueDate,startedDate,suitQuantity,totalPayment,paidPayment;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ID = itemView.findViewById(R.id.pendingorderID);
            Name = itemView.findViewById(R.id.pendingorderName);
            Number = itemView.findViewById(R.id.pendingorderNumber);
            orderStatus = itemView.findViewById(R.id.pendingorderstatus);
            dueDate = itemView.findViewById(R.id.pendingorderDueDate);
            startedDate = itemView.findViewById(R.id.pendingorderStartedDate);
            totalPayment = itemView.findViewById(R.id.pendingorderTotalPayment);
            paidPayment = itemView.findViewById(R.id.pendingorderPaidPayment);
            suitQuantity = itemView.findViewById(R.id.pendingorderSuitQuantity);
        }
    }
}
