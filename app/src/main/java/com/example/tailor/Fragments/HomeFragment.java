package com.example.tailor.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tailor.Utils.CompletedOrderUtils;
import com.example.tailor.Utils.CustomerUtils;
import com.example.tailor.Utils.DelieveredOrderUtils;
import com.example.tailor.Utils.PendingOrderUtils;
import com.example.tailor.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {
    TextView customersTV,pendingTV,completedTV,deliveredTV;
    CustomerUtils customerUtils;
    PendingOrderUtils pendingOrderUtils;
    CompletedOrderUtils completedOrderUtils;
    DelieveredOrderUtils delieveredOrderUtils;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        customerUtils = new CustomerUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        pendingOrderUtils = new PendingOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        completedOrderUtils = new CompletedOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        delieveredOrderUtils = new DelieveredOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        LinearLayout linearLayout = view.findViewById(R.id.mLayout);
        LinearLayout customerLayout = view.findViewById(R.id.customersLayout);
        LinearLayout pendingOrderLayout = view.findViewById(R.id.pendingOrderLayout);
        LinearLayout completedOrderLayout = view.findViewById(R.id.completedOrderLayout);
        LinearLayout deliveredOrderLayout = view.findViewById(R.id.deliveredOrderLayout);
        linearLayout.getBackground().setAlpha(40);
//        customerLayout.getBackground().setAlpha(25);
//        pendingOrderLayout.getBackground().setAlpha(25);
//        completedOrderLayout.getBackground().setAlpha(25);
//        deliveredOrderLayout.getBackground().setAlpha(25);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Al Attari Fabrics & Stichers");

        customersTV = view.findViewById(R.id.totalCustomersTV);
        pendingTV = view.findViewById(R.id.pendingTV);
        completedTV = view.findViewById(R.id.completedTV);
        deliveredTV = view.findViewById(R.id.deliveredTV);

        setTextViewData();

        return view;
    }

    public void setTextViewData() {
        try {
            customersTV.setText(""+ customerUtils.getCount());
            pendingTV.setText(""+ pendingOrderUtils.getCount());
            completedTV.setText(""+ completedOrderUtils.getCount());
            deliveredTV.setText(""+ delieveredOrderUtils.getCount());

        }catch (Exception e){
            Log.d("mTag", "setTextViewData Exception: " + e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setTextViewData();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTextViewData();
    }

    @Override
    public void onPause() {
        super.onPause();
        setTextViewData();
    }
}