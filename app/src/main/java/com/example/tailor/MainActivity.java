package com.example.tailor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tailor.Adapters.CustomerAdapter;
import com.example.tailor.Adapters.RecyclerItemClickListener;
import com.example.tailor.Models.Customer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText searchTextField;
    ImageView addBtn;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayout;
    //ArrayList<Customer> customersList = new ArrayList<>();
    ArrayList<Customer> newCustomerList = new ArrayList<>();
    CustomerAdapter customerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchTextField = findViewById(R.id.fragedtTextContact);
        addBtn = findViewById(R.id.fragaddBtn);
        recyclerView = findViewById(R.id.fragrecyclerView);
        //addCustomers();

        customerAdapter = new CustomerAdapter(this,AddCustomer.customersList);
        recyclerView.setAdapter(customerAdapter);
        linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);

        searchTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newCustomerList.removeAll(newCustomerList);
                for (Customer customer:AddCustomer.customersList) {
                    if (customer.getId().contains(s) || customer.getName().contains(s) || customer.getNumber().contains(s))
                    {
                        newCustomerList.add(customer);
                    }
                }
                setCustomerAdapter();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddCustomer.class);
                startActivity(intent);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Customer customer = null;
                searchTextField.setText(searchTextField.getText());
                if (searchTextField.getText().equals(""))
                {
                    customer = AddCustomer.customersList.get(position);
                }
                else {
                    customer= newCustomerList.get(position);
                }
                Intent intent = new Intent(MainActivity.this, ViewCustomer.class);
                intent.putExtra("CustID",customer.getId());
                intent.putExtra("CustName",customer.getName());
                intent.putExtra("CustNumber",customer.getNumber());
                intent.putExtra("CustKameez",customer.getKameez());
                intent.putExtra("CustBazo",customer.getBazo());
                intent.putExtra("CustTera",customer.getTera());
                intent.putExtra("CustGala",customer.getGala());
                intent.putExtra("CustChati",customer.getChati());
                intent.putExtra("CustGera",customer.getGera());
                intent.putExtra("CustShalwar",customer.getShalwar());
                intent.putExtra("CustPoncha",customer.getPoncha());
                intent.putExtra("frontPocket",customer.getFrontPocket());
                intent.putExtra("sidePocket",customer.getSidePocket());
                intent.putExtra("shalwarPocket",customer.getShalwarPocket());

                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }
        ));
    }

    @Override
    protected void onResume() {
        super.onResume();
        customerAdapter = new CustomerAdapter(this,AddCustomer.customersList);
        recyclerView.setAdapter(customerAdapter);
    }

    public void setCustomerAdapter(){
        customerAdapter = new CustomerAdapter(this,newCustomerList);
        recyclerView.setAdapter(customerAdapter);
    }

//    public void addCustomers(){
//        customersList.add(new Customer("111","Ahsan","03001122333"));
//        customersList.add(new Customer("112","Babar","03001122334"));
//        customersList.add(new Customer("113","Chaudhry","03001122335"));
//        customersList.add(new Customer("114","Danish","03001122336"));
//        customersList.add(new Customer("115","Ejaz","03001122337"));
//        customersList.add(new Customer("116","Faizan","03001122338"));
//        customersList.add(new Customer("117","Galib","03001122339"));
//        customersList.add(new Customer("118","Haseeb","03001122340"));
//        customersList.add(new Customer("119","Idrees","03001122341"));
//        customersList.add(new Customer("120","Junaid","03001122342"));
//        customersList.add(new Customer("121","Komal","03001122343"));
//        customersList.add(new Customer("122","Luqman","03001122344"));
//        customersList.add(new Customer("123","Mubeen","03001122345"));
//        customersList.add(new Customer("124","Nouman","03001122346"));
//        customersList.add(new Customer("125","Omer","03001122347"));
//        customersList.add(new Customer("126","Puja","03001122348"));
//        customersList.add(new Customer("127","Qamar","03001122349"));
//        customersList.add(new Customer("128","Rashid","03001122350"));
//        customersList.add(new Customer("129","Sajjad","03001122351"));
//        customersList.add(new Customer("130","Tayyab","03001122352"));
//        customersList.add(new Customer("131","Umar","03001122353"));
//        customersList.add(new Customer("132","Vidiya","03001122354"));
//        customersList.add(new Customer("133","Warda","03001122355"));
//        customersList.add(new Customer("134","Xayyan","03001122356"));
//        customersList.add(new Customer("135","Yadiyyan","03001122357"));
//        customersList.add(new Customer("136","Zain","03001122358"));
//    }
}