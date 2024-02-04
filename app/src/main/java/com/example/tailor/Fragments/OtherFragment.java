package com.example.tailor.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tailor.MessageToAllActivity;
import com.example.tailor.MonthlyReportActivity;
import com.example.tailor.R;
import com.example.tailor.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;

public class OtherFragment extends Fragment {

    LinearLayout linearLayout,smsToAll,monthlyReport,signOut;
    TextView profileEmail;

    FirebaseAuth mAuth;

    public OtherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        mAuth = FirebaseAuth.getInstance();
        linearLayout = view.findViewById(R.id.mainLayoutOtherFrag);
        profileEmail = view.findViewById(R.id.profileEmailOtherFrag);
        smsToAll = view.findViewById(R.id.smsToAllOtherFrag);
        monthlyReport = view.findViewById(R.id.monthlyreportOtherFrag);
        signOut = view.findViewById(R.id.signOutOtherFrag);
        linearLayout.getBackground().setAlpha(25);

        profileEmail.setText(mAuth.getCurrentUser().getEmail());
        profileEmail.setSelected(true);

        smsToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageToAllActivity.class);
                startActivity(intent);
            }
        });

        monthlyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MonthlyReportActivity.class);
                startActivity(intent);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(getActivity(), "Sign Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
            }
        });
        return  view;
    }
}