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

public class CompletedOrderAdapter extends RecyclerView.Adapter<CompletedOrderAdapter.viewHolder> {

    private Context context;
    private ArrayList<OrderDetails> completedOrderList;

    public CompletedOrderAdapter(FragmentActivity context, ArrayList<OrderDetails> completedOrderList){
        this.context = context;
        this.completedOrderList = completedOrderList;
    }

    @NonNull
    @Override
    public CompletedOrderAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.completed_order,parent,false);
        return new CompletedOrderAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedOrderAdapter.viewHolder holder, int position) {
        OrderDetails order = completedOrderList.get(position);
        holder.ID.setText("#" + order.getId());
        holder.Name.setText(order.getName());
        holder.Number.setText(order.getNumber());
        holder.orderStatus.setText(order.getOrder_Status());
        holder.dueDate.setText(order.getSelected_Date());
        holder.startedDate.setText(order.getStart_date());
        holder.completedDate.setText(order.getCompleted_date());
        holder.suitQuantity.setText(order.getSuit_Quantity());
        holder.totalPayment.setText(order.getTotal_Payment());
        holder.paidPayment.setText(order.getPaid_Payment());
    }

    @Override
    public int getItemCount() {
        return completedOrderList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView ID, Name, Number,orderStatus,dueDate,startedDate,completedDate,suitQuantity,totalPayment,paidPayment;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ID = itemView.findViewById(R.id.completedorderID);
            Name = itemView.findViewById(R.id.completedorderName);
            Number = itemView.findViewById(R.id.completedorderNumber);
            orderStatus = itemView.findViewById(R.id.completedorderstatus);
            dueDate = itemView.findViewById(R.id.completedorderDueDate);
            startedDate = itemView.findViewById(R.id.compledorderStartedDate);
            completedDate = itemView.findViewById(R.id.compledorderCompletedDate);
            totalPayment = itemView.findViewById(R.id.completedorderTotalPayment);
            paidPayment = itemView.findViewById(R.id.completedorderPaidPayment);
            suitQuantity = itemView.findViewById(R.id.completedorderSuitQuality);
        }
    }
}
