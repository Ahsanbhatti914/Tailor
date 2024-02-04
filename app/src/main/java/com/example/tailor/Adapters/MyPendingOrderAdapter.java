package com.example.tailor.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tailor.Models.OrderDetails;
import com.example.tailor.R;
import com.example.tailor.Utils.PendingOrderUtils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MyPendingOrderAdapter extends FirebaseRecyclerAdapter<OrderDetails,MyPendingOrderAdapter.pendingViewholder> {

    private static OrderDetails o;
    private static int height;
    private static PendingOrderUtils pendingOrderUtils =  new PendingOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
    public MyPendingOrderAdapter(@NonNull FirebaseRecyclerOptions<OrderDetails> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull pendingViewholder holder, int position, @NonNull OrderDetails order) {
        holder.ID.setText(order.getId());
        holder.Name.setText(order.getName());
        holder.Number.setText(order.getNumber());
        holder.orderStatus.setText(order.getOrder_Status());
        holder.dueDate.setText(order.getSelected_Date());
        holder.startedDate.setText(order.getStart_date());
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
                pendingOrderUtils.updateOrder(order.getOid(),order);
                holder.mlayout.setBackgroundColor(Color.parseColor("#0F773EFA"));
            }
        });

//        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                OrderDetails orderDetails = getItem(position);
//                setOrder(orderDetails);
//                return false;
//            }
//        });
    }

    @NonNull
    @Override
    public pendingViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pendingorder,parent,false);
        return new MyPendingOrderAdapter.pendingViewholder(view);
    }

//    public static OrderDetails getOrder() {
//        return o;
//    }
//
//    public static void setOrder(OrderDetails o) {
//        MyPendingOrderAdapter.o = o;
//    }

//    public static int getHeight() {
//        return height;
//    }
//
//    public static void setHeight(int height) {
//        MyPendingOrderAdapter.height = height;
//    }

    public class pendingViewholder extends RecyclerView.ViewHolder {
        TextView ID, Name, Number,orderStatus,dueDate,startedDate,suitQuantity,totalPayment,paidPayment;
        LinearLayout mlayout;
        public pendingViewholder(@NonNull View itemView) {
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
            mlayout = itemView.findViewById(R.id.pendingOrderXmlLayout);
        }
    }
}