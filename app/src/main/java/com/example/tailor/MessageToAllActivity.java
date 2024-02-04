package com.example.tailor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tailor.Utils.CustomerUtils;
import com.example.tailor.Models.Customer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageToAllActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    EditText edtMessage;
    Button sendMessage;
    CustomerUtils customerUtils;

    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_to_all);
        customerUtils = new CustomerUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        linearLayout = findViewById(R.id.mainLayoutSendMessageToAll);
        edtMessage = findViewById(R.id.customMsgEdtMessageToAll);
        sendMessage = findViewById(R.id.sendBtnMessageToAll);
        linearLayout.getBackground().setAlpha(25);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtMessage.getText().toString().equals("")){
                    Toast.makeText(MessageToAllActivity.this, "Messages sending...", Toast.LENGTH_SHORT).show();
                    sendMessageToAll(edtMessage.getText().toString());
                }
            }
        });
    }

    private void sendMessageToAll(String message) {

        customerUtils.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Customer customer = dataSnapshot.getValue(Customer.class);
                        String phone = customer.getNumber().trim();

                        SmsManager smsManager = SmsManager.getDefault();

                        ArrayList<String> parts = smsManager.divideMessage(message);
                        ArrayList<PendingIntent> sentIntents = new ArrayList<>();
                        ArrayList<PendingIntent> deliveryIntents = new ArrayList<>();

                        for (int i = 0; i < parts.size(); i++) {
                            sentIntents.add(null);
                            deliveryIntents.add(null);
                        }

                        smsManager.sendMultipartTextMessage(phone, null, parts, sentIntents, deliveryIntents);
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Exception " + e, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        message = edtMessage.getText().toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMessages();
    }

    private void getMessages() {
        edtMessage.setText(message);
    }
}