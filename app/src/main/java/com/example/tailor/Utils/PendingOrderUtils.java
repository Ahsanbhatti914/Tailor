package com.example.tailor.Utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tailor.Models.OrderDetails;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PendingOrderUtils extends CustomerUtils {
    private String path;
    private DatabaseReference mRef;
    private ChildEventListener mChildEventListener;
    private static int totalPendingOrder = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public PendingOrderUtils(){}

    public PendingOrderUtils(String email){
        path = "Users/"+email+"/PendingOrders";
        FirebaseDatabase.getInstance().getReference(path).keepSynced(true);
        mRef = FirebaseDatabase.getInstance().getReference(path);
        //totalPendingOrder = 0;
    }

    public void addOrder(OrderDetails orderDetails){
        try{
            String key = mRef.push().getKey();
            if(key != null){
                mRef.child(key).setValue(orderDetails);
                mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        OrderDetails o = snapshot.getValue(OrderDetails.class);
                        o.setOid(snapshot.getKey());
                        mRef.child(key).child("oid").setValue(o.getOid());
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
        }catch (Exception e){
            Log.d("mTag", "addOrder: " + e);
        }
    }

    public DatabaseReference getReference(){
        return mRef;
    }

    public int getCount(){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalPendingOrder = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return totalPendingOrder;
    }
    public void setCount(int count){
        totalPendingOrder = count;
    }

    public void deleteOrder(String oid){
        mRef.child(oid).setValue(null);
    }

    public void updateOrder(String oid,OrderDetails order){
        mRef.child(oid).setValue(order);
    }
}
