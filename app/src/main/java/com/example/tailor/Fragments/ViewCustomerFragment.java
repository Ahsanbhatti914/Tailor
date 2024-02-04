package com.example.tailor.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.tailor.Adapters.MyCustomerAdapter;
import com.example.tailor.AddCustomer;
import com.example.tailor.Utils.CustomerUtils;
import com.example.tailor.Models.Customer;
import com.example.tailor.R;
import com.example.tailor.Adapters.RecyclerItemClickListener;
import com.example.tailor.ViewCustomer;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class ViewCustomerFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText searchTextField;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayout;
    MyCustomerAdapter myCustomerAdapter;
    String [] searchBy = {"number","name","id"};
    Spinner searchBySpinner;
    ArrayAdapter <String> searchByAdapter;
    String searchByIdNameNumber;
    LinearLayout mainLayout;
    CustomerUtils customerUtils;
    FloatingActionButton fab;
    public ViewCustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_customer, container, false);
        customerUtils = new CustomerUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        mainLayout = view.findViewById(R.id.mainLayoutVCFrag);
        mainLayout.getBackground().setAlpha(25);
        searchTextField = view.findViewById(R.id.fragedtTextContact);
        fab = view.findViewById(R.id.fab);
        searchBySpinner = view.findViewById(R.id.fragSpinnerVC);
        searchByAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,searchBy);
        searchBySpinner.setAdapter(searchByAdapter);
        searchBySpinner.setOnItemSelectedListener(this);
        recyclerView = view.findViewById(R.id.fragrecyclerView);

        FirebaseRecyclerOptions<Customer> options = new FirebaseRecyclerOptions.Builder<Customer>()
                .setQuery(customerUtils.getReference(), Customer.class).build();

        myCustomerAdapter = new MyCustomerAdapter(options);
        recyclerView.setAdapter(myCustomerAdapter);
        linearLayout = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayout);

        searchTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FirebaseRecyclerOptions<Customer> options = new FirebaseRecyclerOptions.Builder<Customer>()
                        .setQuery(customerUtils.getReference().orderByChild(getSearchBy()).startAt(s.toString()).endAt(s.toString()+"\uf8ff"), Customer.class).build();
                myCustomerAdapter = new MyCustomerAdapter(options);
                myCustomerAdapter.startListening();
                recyclerView.setAdapter(myCustomerAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCustomer.class);
                startActivity(intent);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Customer customer = MyCustomerAdapter.getCustomer();
                Intent intent = new Intent(getActivity(), ViewCustomer.class);
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
                intent.putExtra("index",position);
                intent.putExtra("LOCATION","ViewCustomerFragment");

                startActivity(intent);
            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }
        ));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        myCustomerAdapter.startListening();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         searchByIdNameNumber = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public String getSearchBy(){
        return searchByIdNameNumber;
    }
}