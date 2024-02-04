package com.example.tailor.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.tailor.Adapters.MyPendingOrderAdapter;
import com.example.tailor.Adapters.RecyclerItemClickListener;
import com.example.tailor.Utils.CompletedOrderUtils;
import com.example.tailor.Utils.CustomerUtils;
import com.example.tailor.Utils.PendingOrderUtils;
import com.example.tailor.Models.Customer;
import com.example.tailor.Models.OrderDetails;
import com.example.tailor.R;
import com.example.tailor.ViewCustomer;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PendingOrderFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText searchTextField;
    RecyclerView pendingOrderRecyclerView;
    LinearLayoutManager linearLayout;
    MyPendingOrderAdapter myPendingOrderAdapter;
    LinearLayout mainPendingLayout;
    ImageView pendingOrderCompleted;
    String [] searchBy = {"number","name","id"};
    Spinner searchBySpinner;
    ArrayAdapter<String> searchByAdapter;
    String searchByIdNameNumber;
    CustomerUtils customerUtils;
    PendingOrderUtils pendingOrderUtils;
    CompletedOrderUtils completedOrderUtils;

    public PendingOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_order, container, false);
        customerUtils = new CustomerUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        pendingOrderUtils = new PendingOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        completedOrderUtils = new CompletedOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        mainPendingLayout = view.findViewById(R.id.mLayoutPendingFrag);
        mainPendingLayout.getBackground().setAlpha(40);
        searchTextField = view.findViewById(R.id.pendingOrderFragEdtText);
        searchBySpinner = view.findViewById(R.id.pendingOrderFragSpinner);
        searchByAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,searchBy);
        searchBySpinner.setAdapter(searchByAdapter);
        searchBySpinner.setOnItemSelectedListener(this);
        pendingOrderRecyclerView = view.findViewById(R.id.pendingOrderFragRecyclerView);

        FirebaseRecyclerOptions<OrderDetails> options = new FirebaseRecyclerOptions.Builder<OrderDetails>()
                .setQuery(pendingOrderUtils.getReference(), OrderDetails.class).build();

        myPendingOrderAdapter = new MyPendingOrderAdapter(options);
        pendingOrderRecyclerView.setAdapter(myPendingOrderAdapter);
        linearLayout = new LinearLayoutManager(getActivity());
        pendingOrderRecyclerView.setLayoutManager(linearLayout);

        searchTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FirebaseRecyclerOptions<OrderDetails> options = new FirebaseRecyclerOptions.Builder<OrderDetails>()
                        .setQuery(pendingOrderUtils.getReference().orderByChild(getSearchBy()).startAt(s.toString()).endAt(s.toString()+"\uf8ff"), OrderDetails.class).build();

                myPendingOrderAdapter = new MyPendingOrderAdapter(options);
                pendingOrderRecyclerView.setAdapter(myPendingOrderAdapter);
                myPendingOrderAdapter.startListening();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pendingOrderRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getActivity(), pendingOrderRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onItemClick(View v, int position) {

                LinearLayout mainLinearLayout = v.findViewById(R.id.myLayout);
                Button viewMeasurements = v.findViewById(R.id.pendingorderViewMeasurementsBtn);
                pendingOrderCompleted = v.findViewById(R.id.pendingorderCompletedImgView);
                viewMeasurements.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            DatabaseReference mRef = myPendingOrderAdapter.getRef(position);
                            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    OrderDetails order= snapshot.getValue(OrderDetails.class);
                                    moveToViewCustomer(order);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        return true;
                    }
                });


               pendingOrderCompleted.setOnTouchListener(new View.OnTouchListener() {
                   @Override
                   public boolean onTouch(View v, MotionEvent event) {
                       if (event.getAction() == MotionEvent.ACTION_DOWN) {
                           DatabaseReference mRef = myPendingOrderAdapter.getRef(position);
                           mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   OrderDetails order= snapshot.getValue(OrderDetails.class);
                                   addToCompletedOrderList(order);
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
                    pendingOrderCompleted.setVisibility(View.GONE);
                }
                else {
                    mainLinearLayout.setVisibility(View.VISIBLE);
                    viewMeasurements.setVisibility(View.VISIBLE);
                    pendingOrderCompleted.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {}
        }
        ));
        return view;
    }

    public void moveToViewCustomer(OrderDetails order){

        Toast.makeText(getActivity(), "" + order.getName(), Toast.LENGTH_SHORT).show();
        customerUtils.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Customer customer = dataSnapshot.getValue(Customer.class);

                    if(customer.getId().equals(order.getId())){
                        Intent intent = new Intent(getActivity(), ViewCustomer.class);
                        intent.putExtra("Oid",order.getOid());
                        intent.putExtra("CustCid",customer.getCid());
                        intent.putExtra("CustID",customer.getId());
                        intent.putExtra("CustName",customer.getName());
                        intent.putExtra("CustNumber",customer.getNumber());
                        intent.putExtra("CustKameez",customer.getKameez());
                        intent.putExtra("CustKameezFrac",customer.getKameezFrac());
                        intent.putExtra("CustTera",customer.getTera());
                        intent.putExtra("CustTeraFrac",customer.getTeraFrac());
                        intent.putExtra("CustBazo",customer.getBazo());
                        intent.putExtra("CustBazoFrac",customer.getBazoFrac());
                        intent.putExtra("CustModa",customer.getModa());
                        intent.putExtra("CustModaFrac",customer.getModaFrac());
                        intent.putExtra("CustChati",customer.getChati());
                        intent.putExtra("CustChatiFrac",customer.getChatiFrac());
                        intent.putExtra("CustFitting",customer.getFitting());
                        intent.putExtra("CustFittingFrac",customer.getFittingFrac());
                        intent.putExtra("CustGera",customer.getGera());
                        intent.putExtra("CustGeraFrac",customer.getGeraFrac());
                        intent.putExtra("CustGala",customer.getGala());
                        intent.putExtra("CustGalaFrac",customer.getGalaFrac());
                        intent.putExtra("CustShalwar",customer.getShalwar());
                        intent.putExtra("CustShalwarFrac",customer.getShalwarFrac());
                        intent.putExtra("CustPoncha",customer.getPoncha());
                        intent.putExtra("CustPonchaFrac",customer.getPonchaFrac());
                        intent.putExtra("CustFair",customer.getFair());
                        intent.putExtra("CustFairFrac",customer.getFairFrac());
                        intent.putExtra("CustAsan",customer.getAsan());
                        intent.putExtra("CustAsanFrac",customer.getAsanFrac());
                        intent.putExtra("CustTrouser",customer.getTrouser());
                        intent.putExtra("CustTrouserFrac",customer.getTrouserFrac());
                        intent.putExtra("CustTrouserPoncha",customer.getTrouserPoncha());
                        intent.putExtra("CustTrouserPonchaFrac",customer.getTrouserPonchaFrac());
                        intent.putExtra("CustTrouserFair",customer.getTrouserFair());
                        intent.putExtra("CustTrouserFairFrac",customer.getTrouserFairFrac());
                        intent.putExtra("CustTrouserAsan",customer.getTrouserAsan());
                        intent.putExtra("CustTrouserAsanFrac",customer.getTrouserAsanFrac());
                        intent.putExtra("CustCollar",customer.getCollar());
                        intent.putExtra("CustCollarWidth",customer.getCollarWidth());
                        intent.putExtra("CustCollarWidthFrac",customer.getFracCollarWidth());
                        intent.putExtra("CustCollarLength",customer.getCollarLength());
                        intent.putExtra("CustCollarLengthFrac",customer.getFracCollarLength());
                        intent.putExtra("CustKaff",customer.getKaff());
                        intent.putExtra("CustKaffWidth",customer.getKaffWidth());
                        intent.putExtra("CustKaffWidthFrac",customer.getFracKaffWidth());
                        intent.putExtra("CustKaffLength",customer.getKaffLength());
                        intent.putExtra("CustKaffLengthFrac",customer.getFracKaffLength());
                        intent.putExtra("CustSalaei",customer.getSalaei());
                        intent.putExtra("CustGheera",customer.getGheera());
                        intent.putExtra("CustDhaga",customer.getDhaga());
                        intent.putExtra("frontPocket",customer.getFrontPocket());
                        intent.putExtra("sidePocket",customer.getSidePocket());
                        intent.putExtra("shalwarPocket",customer.getShalwarPocket());
                        intent.putExtra("note",customer.getNote());
                        intent.putExtra("LOCATION","PendingOrderFragment");
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addToCompletedOrderList(OrderDetails order){
        try {
            SimpleDateFormat formDate = new SimpleDateFormat("dd-MM-yyyy");
            String completedDate = formDate.format(new Date());
            order.setOrder_Status("Completed");
            order.setCompleted_date(completedDate);
            order.setSeen(false);
            completedOrderUtils.addOrder(order);
            pendingOrderUtils.deleteOrder(order.getOid());
            myPendingOrderAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), "Order Completed", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
    public String getSearchBy(){
        return searchByIdNameNumber;
    }

    @Override
    public void onStart() {
        myPendingOrderAdapter.startListening();
        super.onStart();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        searchByIdNameNumber = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}