package com.example.tailor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.tailor.Utils.CompletedOrderUtils;
import com.example.tailor.Utils.CustomerUtils;
import com.example.tailor.Utils.DelieveredOrderUtils;
import com.example.tailor.Utils.PendingOrderUtils;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    CustomerUtils customerUtils;
    PendingOrderUtils pendingOrderUtils;
    CompletedOrderUtils completedOrderUtils;
    DelieveredOrderUtils delieveredOrderUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        IntializeSomeData();

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {

                }
                Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        };
        thread.start();
    }

    private void IntializeSomeData() {
        try {
            customerUtils = new CustomerUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com", ""));
            pendingOrderUtils = new PendingOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com", ""));
            completedOrderUtils = new CompletedOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com", ""));
            delieveredOrderUtils = new DelieveredOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com", ""));
            getData();
        } catch (Exception e) {

        }
    }
    private void getData(){
        customerUtils.getCount();
        pendingOrderUtils.getCount();
        completedOrderUtils.getCount();
        delieveredOrderUtils.getCount();
    }
}
