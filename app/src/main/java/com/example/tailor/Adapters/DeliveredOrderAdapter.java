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

public class DeliveredOrderAdapter  extends RecyclerView.Adapter<DeliveredOrderAdapter.viewHolder>{

    private Context context;
    private ArrayList<OrderDetails> deliveredOrderList;

    public DeliveredOrderAdapter(FragmentActivity context, ArrayList<OrderDetails> deliveredOrderList){
        this.context = context;
        this.deliveredOrderList = deliveredOrderList;
    }

    @NonNull
    @Override
    public DeliveredOrderAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.deliveredorder,parent,false);
        return new DeliveredOrderAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredOrderAdapter.viewHolder holder, int position) {
        OrderDetails order = deliveredOrderList.get(position);
        holder.ID.setText("#" + order.getId());
        holder.Name.setText(order.getName());
        holder.Number.setText(order.getNumber());
        holder.orderStatus.setText(order.getOrder_Status());
        holder.dueDate.setText(order.getSelected_Date());
        holder.startedDate.setText(order.getStart_date());
        holder.completedDate.setText(order.getCompleted_date());
        holder.deliveredDate.setText(order.getDelivered_date());
        holder.suitQuantity.setText(order.getSuit_Quantity());
        holder.totalPayment.setText(order.getTotal_Payment());
        holder.paidPayment.setText(order.getPaid_Payment());
    }

    @Override
    public int getItemCount() {
        return deliveredOrderList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView ID, Name, Number,orderStatus,dueDate,startedDate,completedDate,deliveredDate,suitQuantity,totalPayment,paidPayment;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ID = itemView.findViewById(R.id.deliveredorderID);
            Name = itemView.findViewById(R.id.deliveredorderName);
            Number = itemView.findViewById(R.id.deliveredorderNumber);
            orderStatus = itemView.findViewById(R.id.deliveredorderstatus);
            dueDate = itemView.findViewById(R.id.deliveredorderDueDate);
            startedDate = itemView.findViewById(R.id.deliveredorderStartedDate);
            completedDate = itemView.findViewById(R.id.deliveredorderCompletedDate);
            deliveredDate = itemView.findViewById(R.id.deliveredorderDeliveredDate);
            totalPayment = itemView.findViewById(R.id.deliveredorderTotalPayment);
            paidPayment = itemView.findViewById(R.id.deliveredorderPaidPayment);
            suitQuantity = itemView.findViewById(R.id.deliveredorderSuitQuality);
        }
    }
}
