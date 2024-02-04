package com.example.tailor.Adapters;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tailor.Models.Customer;
import com.example.tailor.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MyCustomerAdapter extends FirebaseRecyclerAdapter<Customer,MyCustomerAdapter.customerViewholder>{

    private static Customer c;
    public MyCustomerAdapter(@NonNull FirebaseRecyclerOptions<Customer> options){
        super(options);
    }

    @NonNull
    @Override
    public customerViewholder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_row, parent, false);
        return new MyCustomerAdapter.customerViewholder(view);
    }

    @Override
    protected void
    onBindViewHolder(@NonNull customerViewholder holder, int position, @NonNull Customer customer)
    {
        holder.ID.setText(customer.getId());
        holder.Name.setText(customer.getName());
        holder.Number.setText(customer.getNumber());

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Customer cust = getItem(position);
                setCustomer(cust);
                return false;
            }
        });
    }

    public void setCustomer(Customer customer){
        c = customer;
    }
    public static Customer getCustomer(){
        return c;
    }

    class customerViewholder extends RecyclerView.ViewHolder {
        TextView ID, Name, Number;
        LinearLayout linearLayout;
        public customerViewholder(@NonNull View itemView)
        {
            super(itemView);
            ID = itemView.findViewById(R.id.ID);
            Name = itemView.findViewById(R.id.Name);
            Number = itemView.findViewById(R.id.Number);
            linearLayout = itemView.findViewById(R.id.mLayoutCustomerRow);
        }
    }
}
