package com.example.tailor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tailor.Adapters.MonthlyReportAdapter;
import com.example.tailor.Adapters.ReportAdapter;
import com.example.tailor.Utils.DelieveredOrderUtils;
import com.example.tailor.Models.OrderDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class MonthlyReportActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    RecyclerView monthlyReportRecyclerView;
    ReportAdapter reportAdapter;
    DelieveredOrderUtils delieveredOrderUtils;
    LinearLayoutManager linearLayout;
    LinearLayout mLayout;
    TextView totalTv,totalQntyTv,showDateTv;

    ImageView datePickerImgV;
    int total = 0,totalQnty = 0;

    ArrayList <OrderDetails> orders = new ArrayList<>();
    private Query query;
    int dayValue = 0,monthValue = 0,yearValue = 0;
    LocalDate today = null;
    String lastDay[] = {"31","28","31","30","31","30","31","31","30","31","30","31"};
    String months [] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    int siNo = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report);
        initialization();
        clickListener();
    }

    private void clickListener() {
        datePickerImgV.setOnClickListener(this);
    }

    private void initialization(){
        mLayout = findViewById(R.id.mainLayoutMonthlyReport);
        mLayout.getBackground().setAlpha(25);
        delieveredOrderUtils = new DelieveredOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        monthlyReportRecyclerView = findViewById(R.id.recyclerViewMonthlyReprt);
        totalTv = findViewById(R.id.totalTvMonthlyReport);
        totalQntyTv = findViewById(R.id.qntyTvMonthlyReport);
        showDateTv = findViewById(R.id.showDatePickerDateTV);
        datePickerImgV = findViewById(R.id.datePickerImgV);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            today = LocalDate.now();
            dayValue = today.getDayOfMonth();
            monthValue = today.getMonthValue();
            yearValue = today.getYear();
        }

        showDateTv.addTextChangedListener(this);
        showDateTv.setText(dayValue+"-"+months[monthValue-1]+"-"+yearValue);

    }
    private void showMonthYearPicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int dayOfMonth) {
                        // Handle the selected date (year and month)
                        // selectedYear is the selected year
                        // selectedMonth is the selected month (0-11)

                        yearValue = selectedYear;
                        monthValue = selectedMonth + 1;
                        dayValue = dayOfMonth;
                        showDateTv.setText(dayValue+"-"+months[monthValue-1]+"-"+yearValue);
                    }
                },
                year,
                month,
                1 // Day is not relevant here, so set it to any value
        );

        datePickerDialog.getDatePicker().findViewById(getResources().getIdentifier("year", "id", "android")).setVisibility(View.VISIBLE);
        datePickerDialog.getDatePicker().findViewById(getResources().getIdentifier("month", "id", "android")).setVisibility(View.VISIBLE);

        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v == findViewById(R.id.datePickerImgV)){
            showMonthYearPicker();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        query = delieveredOrderUtils.getReference().orderByChild("delivered_date").startAt("00-"+monthValue+"-"+yearValue).endAt(lastDay[monthValue-1]+"-"+monthValue+"-"+yearValue);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                orders.clear();
                total = 0;
                totalQnty = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    OrderDetails o = dataSnapshot.getValue(OrderDetails.class);
                    String month = o.getDelivered_date().substring(3,5);
                    String year = o.getDelivered_date().substring(6,10);
                    if(monthValue < 10) {
                        if (month.equals("0"+monthValue) && year.equals(""+yearValue)){
                            orders.add(o);
                            total = total + Integer.valueOf(o.getTotal_Payment());
                            totalQnty = totalQnty + Integer.valueOf(o.getSuit_Quantity());
                        }
                    }else {
                        if (month.equals(""+monthValue) && year.equals(""+yearValue)){
                            orders.add(o);
                            total = total + Integer.valueOf(o.getTotal_Payment());
                            totalQnty = totalQnty + Integer.valueOf(o.getSuit_Quantity());
                        }
                    }
                }
                reportAdapter = new ReportAdapter(getApplicationContext(),orders);
                monthlyReportRecyclerView.setAdapter(reportAdapter);
                linearLayout = new LinearLayoutManager(getApplicationContext());
                monthlyReportRecyclerView.setLayoutManager(linearLayout);
                totalTv.setText(""+total);
                totalQntyTv.setText(""+totalQnty);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}