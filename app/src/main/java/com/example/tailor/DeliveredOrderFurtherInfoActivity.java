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

public class DeliveredOrderFurtherInfoActivity extends AppCompatActivity implements View.OnClickListener {

    EditText customMsg,defaultMsg;
    TextView custID,custname,custNumber;
    Button sendSMS,Invoice;
    String defaulMessage,customMessage;
    private String ID, Name, Number,order_Status, start_date,completed_date,delivered_date,
            total_Payment,paid_Payment ,suit_Quantity,selected_Date;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered_order_further_info);

        Initializtion();
        getIntentData();
        setCustomerDatails();
        setDefaultMessage();

        sendSMS.setOnClickListener(this);
        Invoice.setOnClickListener(this);
    }


    public void Initializtion(){
        custID = findViewById(R.id.deliveredOrderInfoCustID);
        custname = findViewById(R.id.deliveredOrderInfoCustName);
        custNumber = findViewById(R.id.deliveredOrderInfoCustNumber);
        customMsg = findViewById(R.id.deliveredOrderInfoCustomMsgEdt);
        defaultMsg = findViewById(R.id.deliveredOrderInfoDefaultMsgEdt);
        sendSMS = findViewById(R.id.deliveredOrderInfoSendSMS);
        Invoice = findViewById(R.id.deliveredOrderInfoInvoice);
        linearLayout = findViewById(R.id.mainLayoutDeliveredOrderFurther);
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
        delivered_date = intent.getStringExtra("OrderDeliveredDate");
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
        //Double total = Double.parseDouble(total_Payment) - Double.parseDouble(paid_Payment);
        defaulMessage = "Dear " + Name + " your order with ID #" + ID + " has been delivered, Warmly thank you for buying our services. Best regards AL ATTARI FABRICS & STICHERS.";
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
        else if(v == Invoice){
            Intent intent = new Intent(this, GenerateInvoiceActivity.class);
            intent.putExtra("CustID",ID);
            intent.putExtra("CustName",Name);
            intent.putExtra("CustNumber",Number);
            intent.putExtra("OrderStatus" , order_Status);
            intent.putExtra("OrderStartedDate",start_date);
            intent.putExtra("OrderDueDate",selected_Date);
            intent.putExtra("OrderCompletedDate",completed_date);
            intent.putExtra("OrderDeliveredDate",delivered_date);
            intent.putExtra("OrderTotalPayment",total_Payment);
            intent.putExtra("OrderPaidPayment",paid_Payment);
            intent.putExtra("OrderSuitQuantity",suit_Quantity);
            startActivity(intent);
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