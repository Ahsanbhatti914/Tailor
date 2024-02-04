package com.example.tailor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tailor.Models.Customer;
import com.example.tailor.R;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.viewHolder> {
    private Context context;
    private ArrayList<Customer> customerList;

    public CustomerAdapter(FragmentActivity context, ArrayList<Customer> customerList){
        this.context = context;
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_row,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Customer customer = customerList.get(position);
        holder.ID.setText("#" + customer.getId());
        holder.Name.setText(customer.getName());
        holder.Number.setText(customer.getNumber());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
       TextView ID, Name, Number;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ID = itemView.findViewById(R.id.ID);
            Name = itemView.findViewById(R.id.Name);
            Number = itemView.findViewById(R.id.Number);
        }
    }
}
