package com.example.tailor.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tailor.Adapters.MyCompletedOrderAdapter;
import com.example.tailor.Adapters.RecyclerItemClickListener;
import com.example.tailor.CompleteOrderFurtherInfoActivity;
import com.example.tailor.Utils.CompletedOrderUtils;
import com.example.tailor.Utils.DelieveredOrderUtils;
import com.example.tailor.Models.OrderDetails;
import com.example.tailor.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CompletedOrderFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    EditText searchTextField;
    RecyclerView completedOrderRecyclerView;
    LinearLayoutManager linearLayout;
    LinearLayout mainCompletedLayout;
    ImageView completedOrderDelivered;

    MyCompletedOrderAdapter myCompletedOrderAdapter;
    String [] searchBy = {"number","name","id"};
    Spinner searchBySpinner;
    ArrayAdapter<String> searchByAdapter;
    String searchByIdNameNumber;
    CompletedOrderUtils completedOrderUtils;
    DelieveredOrderUtils delieveredOrderUtils;

    public CompletedOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_completed_order, container, false);
        completedOrderUtils = new CompletedOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        delieveredOrderUtils = new DelieveredOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        mainCompletedLayout = view.findViewById(R.id.mLayoutCompletedFrag);
        mainCompletedLayout.getBackground().setAlpha(25);
        searchTextField = view.findViewById(R.id.completedOrderFragEdtText);
        completedOrderRecyclerView = view.findViewById(R.id.completedOrderFragRecyclerView);
        searchBySpinner = view.findViewById(R.id.completedOrderFragSpinner);
        searchByAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,searchBy);
        searchBySpinner.setAdapter(searchByAdapter);
        searchBySpinner.setOnItemSelectedListener(this);

        FirebaseRecyclerOptions<OrderDetails> options = new FirebaseRecyclerOptions.Builder<OrderDetails>()
                .setQuery(completedOrderUtils.getReference(), OrderDetails.class).build();

        myCompletedOrderAdapter = new MyCompletedOrderAdapter(options);
        completedOrderRecyclerView.setAdapter(myCompletedOrderAdapter);
        linearLayout = new LinearLayoutManager(getActivity());
        completedOrderRecyclerView.setLayoutManager(linearLayout);

        searchTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FirebaseRecyclerOptions<OrderDetails> options = new FirebaseRecyclerOptions.Builder<OrderDetails>()
                        .setQuery(completedOrderUtils.getReference().orderByChild(getSearchBy()).startAt(s.toString()).endAt(s.toString()+"\uf8ff"), OrderDetails.class).build();

                myCompletedOrderAdapter = new MyCompletedOrderAdapter(options);
                completedOrderRecyclerView.setAdapter(myCompletedOrderAdapter);
                myCompletedOrderAdapter.startListening();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        completedOrderRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getActivity(), completedOrderRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onItemClick(View v, int position) {

                LinearLayout mainLinearLayout = v.findViewById(R.id.myCompletedLayout);
                Button viewMeasurements = v.findViewById(R.id.completedorderFurtherProcessBtn);
                completedOrderDelivered = v.findViewById(R.id.completedorderDeliveredImgView);
                viewMeasurements.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            DatabaseReference mRef = myCompletedOrderAdapter.getRef(position);
                            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    OrderDetails order= snapshot.getValue(OrderDetails.class);
                                    moveToCustomerFurtherProcess(order);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                        return true;
                    }
                });

                completedOrderDelivered.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            Toast.makeText(getActivity(), "Order Delivered", Toast.LENGTH_SHORT).show();
                            DatabaseReference mRef = myCompletedOrderAdapter.getRef(position);
                            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    OrderDetails order= snapshot.getValue(OrderDetails.class);
                                    addToDeliveredOrderList(order);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        return false;
                    }
                });

                if(mainLinearLayout.getVisibility() == View.VISIBLE) {
                    mainLinearLayout.setVisibility(View.GONE);
                    viewMeasurements.setVisibility(View.GONE);
                    completedOrderDelivered.setVisibility(View.GONE);
                }
                else {
                    mainLinearLayout.setVisibility(View.VISIBLE);
                    viewMeasurements.setVisibility(View.VISIBLE);
                    completedOrderDelivered.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {}
        }
        ));
        return view;
    }

    public void moveToCustomerFurtherProcess(OrderDetails order){

        Intent intent = new Intent(getActivity(), CompleteOrderFurtherInfoActivity.class);
        intent.putExtra("CustID",order.getId());
        intent.putExtra("CustName",order.getName());
        intent.putExtra("CustNumber",order.getNumber());
        intent.putExtra("OrderStatus" , order.getOrder_Status());
        intent.putExtra("OrderStartedDate",order.getStart_date());
        intent.putExtra("OrderDueDate",order.getSelected_Date());
        intent.putExtra("OrderCompletedDate",order.getCompleted_date());
        intent.putExtra("OrderTotalPayment",order.getTotal_Payment());
        intent.putExtra("OrderPaidPayment",order.getPaid_Payment());
        intent.putExtra("OrderSuitQuantity",order.getSuit_Quantity());
        intent.putExtra("LOCATION","CompletedOrderFragment");
        startActivity(intent);

    }

    public void addToDeliveredOrderList( OrderDetails order){
        try {
            SimpleDateFormat formDate = new SimpleDateFormat("dd-MM-yyyy");
            String deliveredDate = formDate.format(new Date());
            order.setOrder_Status("Delivered");
            order.setSeen(false);
            order.setDelivered_date(deliveredDate);
            delieveredOrderUtils.addOrder(order);
            completedOrderUtils.deleteOrder(order.getOid());
            myCompletedOrderAdapter.notifyDataSetChanged();

        }catch (Exception e){
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        myCompletedOrderAdapter.startListening();
    }
    public String getSearchBy(){
        return searchByIdNameNumber;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        searchByIdNameNumber = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}