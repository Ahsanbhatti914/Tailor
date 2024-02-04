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
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.tailor.Adapters.MyDelieveredOrderAdapter;
import com.example.tailor.Adapters.RecyclerItemClickListener;
import com.example.tailor.DeliveredOrderFurtherInfoActivity;
import com.example.tailor.Utils.DelieveredOrderUtils;
import com.example.tailor.Models.OrderDetails;
import com.example.tailor.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class DeliveredOrderFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText searchTextField;
    RecyclerView deliveredOrderRecyclerView;
    LinearLayoutManager linearLayout;
    LinearLayout mainDeliveredLayout;
    MyDelieveredOrderAdapter myDelieveredOrderAdapter;
    String [] searchBy = {"number","name","id"};
    Spinner searchBySpinner;
    ArrayAdapter<String> searchByAdapter;
    String searchByIdNameNumber;
    DelieveredOrderUtils delieveredOrderUtils;

    public DeliveredOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivered_order, container, false);
        delieveredOrderUtils = new DelieveredOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        mainDeliveredLayout = view.findViewById(R.id.mLayoutDeliveredFrag);
        mainDeliveredLayout.getBackground().setAlpha(25);
        searchTextField = view.findViewById(R.id.deliveredOrderFragEdtText);
        deliveredOrderRecyclerView = view.findViewById(R.id.deliveredOrderFragRecyclerView);
        searchBySpinner = view.findViewById(R.id.delieveredOrderFragSpinner);
        searchByAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,searchBy);
        searchBySpinner.setAdapter(searchByAdapter);
        searchBySpinner.setOnItemSelectedListener(this);

        FirebaseRecyclerOptions<OrderDetails> options = new FirebaseRecyclerOptions.Builder<OrderDetails>()
                .setQuery(delieveredOrderUtils.getReference(), OrderDetails.class).build();

        myDelieveredOrderAdapter = new MyDelieveredOrderAdapter(options);
        deliveredOrderRecyclerView.setAdapter(myDelieveredOrderAdapter);
        linearLayout = new LinearLayoutManager(getActivity());
        deliveredOrderRecyclerView.setLayoutManager(linearLayout);

        searchTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FirebaseRecyclerOptions<OrderDetails> options = new FirebaseRecyclerOptions.Builder<OrderDetails>()
                        .setQuery(delieveredOrderUtils.getReference().orderByChild(getSearchBy()).startAt(s.toString()).endAt(s.toString()+"\uf8ff"), OrderDetails.class).build();


                myDelieveredOrderAdapter = new MyDelieveredOrderAdapter(options);
                deliveredOrderRecyclerView.setAdapter(myDelieveredOrderAdapter);
                myDelieveredOrderAdapter.startListening();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        deliveredOrderRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getActivity(), deliveredOrderRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onItemClick(View v, int position) {

                LinearLayout mainLinearLayout = v.findViewById(R.id.myDeliveredLayout);
                Button viewMeasurements = v.findViewById(R.id.deliveredorderViewMeasurementsBtn);
                viewMeasurements.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            DatabaseReference mRef = myDelieveredOrderAdapter.getRef(position);
                            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    OrderDetails order= snapshot.getValue(OrderDetails.class);
                                    moveToDeliveredOrderFurtherProcess(order);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                        return true;
                    }
                });


                if(mainLinearLayout.getVisibility() == View.VISIBLE) {
                    mainLinearLayout.setVisibility(View.GONE);
                    viewMeasurements.setVisibility(View.GONE);
                }
                else {
                    mainLinearLayout.setVisibility(View.VISIBLE);
                    viewMeasurements.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {}
        }
        ));
        return view;
    }

    public void moveToDeliveredOrderFurtherProcess(OrderDetails order){

        Intent intent = new Intent(getActivity(), DeliveredOrderFurtherInfoActivity.class);
        intent.putExtra("CustID",order.getId());
        intent.putExtra("CustName",order.getName());
        intent.putExtra("CustNumber",order.getNumber());
        intent.putExtra("OrderStatus" , order.getOrder_Status());
        intent.putExtra("OrderStartedDate",order.getStart_date());
        intent.putExtra("OrderDueDate",order.getSelected_Date());
        intent.putExtra("OrderCompletedDate",order.getCompleted_date());
        intent.putExtra("OrderDeliveredDate",order.getDelivered_date());
        intent.putExtra("OrderTotalPayment",order.getTotal_Payment());
        intent.putExtra("OrderPaidPayment",order.getPaid_Payment());
        intent.putExtra("OrderSuitQuantity",order.getSuit_Quantity());
        intent.putExtra("LOCATION","CompletedOrderFragment");
        startActivity(intent);

    }

    @Override
    public void onStart() {
        super.onStart();
        myDelieveredOrderAdapter.startListening();
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