package com.example.tailor.Adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tailor.Models.OrderDetails;
import com.example.tailor.R;
import com.example.tailor.Utils.DelieveredOrderUtils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MyDelieveredOrderAdapter extends FirebaseRecyclerAdapter<OrderDetails,MyDelieveredOrderAdapter.delieveredViewholder> {

    private DelieveredOrderUtils delieveredOrderUtils = new DelieveredOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
    public MyDelieveredOrderAdapter(@NonNull FirebaseRecyclerOptions<OrderDetails> options) {
        super(options);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onBindViewHolder(@NonNull MyDelieveredOrderAdapter.delieveredViewholder holder, int position, @NonNull OrderDetails order) {
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
        if(!order.isSeen()){
            holder.mlayout.setBackgroundColor(Color.parseColor("#a9aaab"));
        }else {
            holder.mlayout.setBackgroundColor(Color.parseColor("#0F773EFA"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setSeen(true);
                delieveredOrderUtils.updateOrder(order.getOid(),order);
                holder.mlayout.setBackgroundColor(Color.parseColor("#0F773EFA"));
            }
        });

    }

    @NonNull
    @Override
    public MyDelieveredOrderAdapter.delieveredViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deliveredorder,parent,false);
        return new MyDelieveredOrderAdapter.delieveredViewholder(view);
    }

    public class delieveredViewholder extends RecyclerView.ViewHolder {
        TextView ID, Name, Number,orderStatus,dueDate,startedDate,completedDate,deliveredDate,suitQuantity,totalPayment,paidPayment;
        LinearLayout mlayout;
        public delieveredViewholder(@NonNull View itemView) {
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
            mlayout = itemView.findViewById(R.id.deliveredOrderXmlLayout);
        }
    }
}

