package com.example.tailor.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tailor.Models.OrderDetails;
import com.example.tailor.R;
import com.example.tailor.Utils.CompletedOrderUtils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MyCompletedOrderAdapter extends FirebaseRecyclerAdapter <OrderDetails,MyCompletedOrderAdapter.completedViewholder> {

    private CompletedOrderUtils completedOrderUtils = new CompletedOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
    public MyCompletedOrderAdapter(@NonNull FirebaseRecyclerOptions<OrderDetails> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyCompletedOrderAdapter.completedViewholder holder, int position, @NonNull OrderDetails order) {
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

        if(!order.isSeen()){
            holder.mlayout.setBackgroundColor(Color.parseColor("#a9aaab"));
        }else {
            holder.mlayout.setBackgroundColor(Color.parseColor("#0F773EFA"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setSeen(true);
                completedOrderUtils.updateOrder(order.getOid(),order);
                holder.mlayout.setBackgroundColor(Color.parseColor("#0F773EFA"));
            }
        });

    }

    @NonNull
    @Override
    public MyCompletedOrderAdapter.completedViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_order,parent,false);
        return new MyCompletedOrderAdapter.completedViewholder(view);
    }
    public class completedViewholder extends RecyclerView.ViewHolder {
        TextView ID, Name, Number,orderStatus,dueDate,startedDate,completedDate,suitQuantity,totalPayment,paidPayment;
        LinearLayout mlayout;
        public completedViewholder(@NonNull View itemView) {
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
            mlayout = itemView.findViewById(R.id.completedOrderXmlLayout);
        }
    }
}
