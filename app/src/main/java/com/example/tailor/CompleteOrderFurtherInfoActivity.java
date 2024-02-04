package com.example.tailor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CompleteOrderFurtherInfoActivity extends AppCompatActivity implements View.OnClickListener {

    EditText customMsg,defaultMsg;
    TextView custID,custname,custNumber;
    Button sendSMS;
    String defaulMessage,customMessage;
    LinearLayout linearLayout;
    private String ID, Name, Number,order_Status, start_date,completed_date,
            total_Payment,paid_Payment ,suit_Quantity,selected_Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order_further_info);

        Initializtion();
        getIntentData();
        setCustomerDatails();
        setDefaultMessage();

        sendSMS.setOnClickListener(this);
    }

    public void Initializtion(){
        custID = findViewById(R.id.completeOrderInfoCustID);
        custname = findViewById(R.id.completeOrderInfoCustName);
        custNumber = findViewById(R.id.completeOrderInfoCustNumber);
        customMsg = findViewById(R.id.completeOrderInfoCustomMsgEdt);
        defaultMsg = findViewById(R.id.completeOrderInfoDefaultMsgEdt);
        sendSMS = findViewById(R.id.completeOrderInfoSendSMS);
        linearLayout = findViewById(R.id.mainLayoutCompleteOrderFurther);
        linearLayout.getBackground().setAlpha(25);
    }
    public void getIntentData(){
        Intent intent = getIntent();
        ID = intent.getStringExtra("CustID");
        Name = intent.getStringExtra("CustName");
        Number = intent.getStringExtra("CustNumber");
        order_Status = intent.getStringExtra("OrderStatus");
        start_date = intent.getStringExtra("OrderStartedDate");
        selected_Date = intent.getStringExtra("OrderDueDate");
        completed_date = intent.getStringExtra("OrderCompletedDate");
        total_Payment = intent.getStringExtra("OrderTotalPayment");
        paid_Payment = intent.getStringExtra("OrderPaidPayment");
        suit_Quantity = intent.getStringExtra("OrderSuitQuantity");
    }
    public  void setCustomerDatails(){
        custID.setText(ID);
        custname.setText(Name);
        custNumber.setText(Number);
    }
    public void setDefaultMessage(){
        defaulMessage = "Dear " + Name + ", your order with ID #" + ID + " has been completed. Please come and take your order within 24 hours. Best regards AL ATTARI FABRICS & STICHERS.";
        defaultMsg.setText(defaulMessage);
    }

    @Override
    public void onClick(View v) {
        if(v == sendSMS){
            Toast.makeText(this, "SMS sending...", Toast.LENGTH_SHORT).show();
            if(customMsg.getText().toString().equals("")){
                sendSMS(defaultMsg.getText().toString());
            }else {
                sendSMS(customMsg.getText().toString());
            }
        }
    }

    private void sendSMS(String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();

            ArrayList<String> parts = smsManager.divideMessage(msg);
            ArrayList<PendingIntent> sentIntents = new ArrayList<>();
            ArrayList<PendingIntent> deliveryIntents = new ArrayList<>();

            for (int i = 0; i < parts.size(); i++) {
                sentIntents.add(null);
                deliveryIntents.add(null);
            }

            smsManager.sendMultipartTextMessage(Number.trim(), null, parts, sentIntents, deliveryIntents);

        }catch (Exception e){
            Toast.makeText(this, "Exception " + e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        defaulMessage = defaultMsg.getText().toString();
        customMessage = customMsg.getText().toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMessages();
    }

    private void getMessages() {
        defaultMsg.setText(defaulMessage);
        customMsg.setText(customMessage);
    }
}