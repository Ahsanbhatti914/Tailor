package com.example.tailor.Utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tailor.Models.Customer;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerUtils extends Application {
    private String path;
    private DatabaseReference mRef ;
    private ChildEventListener mChildEventListener;
    private static int totalCustomer = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public CustomerUtils(){}

    public CustomerUtils(String email){
        path = "Users/"+email+"/Customers";
        FirebaseDatabase.getInstance().getReference(path).keepSynced(true);
        mRef = FirebaseDatabase.getInstance().getReference(path);
        //totalCustomer = 0;
    }

    public void addCustomer(Customer customer){
        String key = mRef.push().getKey();
        if(key != null){
            mRef.child(key).setValue(customer);
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Customer c = snapshot.getValue(Customer.class);
                    c.setCid(snapshot.getKey());
                    mRef.child(key).child("cid").setValue(c.getCid());
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            mRef.orderByKey().equalTo(key).addChildEventListener(mChildEventListener);
        }

    }

    public DatabaseReference getReference(){
       return mRef;
    }
    public int getCount(){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalCustomer = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return totalCustomer;
    }
    public void setCount(int count){
        totalCustomer = count;
    }

    public void deleteCustommer(String cid){
        mRef.child(cid).setValue(null);
    }

    public void updateCustomer(String cid,Customer customer){
        mRef.child(cid).setValue(customer);
    }
}
