package com.example.tailor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.tailor.Fragments.HomeFragment;
import com.example.tailor.Fragments.OtherFragment;
import com.example.tailor.Fragments.ViewCustomerFragment;
import com.example.tailor.Utils.CompletedOrderUtils;
import com.example.tailor.Utils.CustomerUtils;
import com.example.tailor.Utils.DelieveredOrderUtils;
import com.example.tailor.Utils.PendingOrderUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    Fragment preFrag;
    CustomerUtils customerUtils;
    PendingOrderUtils pendingOrderUtils;
    CompletedOrderUtils completedOrderUtils;
    DelieveredOrderUtils delieveredOrderUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //taking permissions
        ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.READ_CONTACTS},
                        PackageManager.PERMISSION_GRANTED);

        bottomNavigationView = findViewById(R.id.navBarView);
        frameLayout = findViewById(R.id.container);
        intialization();
        preFrag = new HomeFragment();
        loadFragment(preFrag,0);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_home)
                {
                    loadFragment(new HomeFragment(),1);
                }
                else if(id == R.id.nav_addCustomer)
                {
                    Intent intent = new Intent(HomeActivity.this,AddCustomer.class);
                    startActivity(intent);
                }
                else if(id == R.id.nav_viewCustomer)
                {
                    loadFragment(new ViewCustomerFragment(),1);
                }
                else if(id == R.id.nav_order)
                {
                    Intent intent = new Intent(HomeActivity.this,OrderDetailsActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.nav_other)
                {
                    loadFragment(new OtherFragment(),1);
                }
                return true;
            }
        });
    }

    private void intialization() {
        customerUtils = new CustomerUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com", ""));
        pendingOrderUtils = new PendingOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com", ""));
        completedOrderUtils = new CompletedOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com", ""));
        delieveredOrderUtils = new DelieveredOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com", ""));
    }

    public void DeletePreFrag(Fragment frag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(frag);
        fragmentTransaction.commit();
    }

    public void loadFragment(Fragment fragment,int flag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(flag == 0)
        {
            for (Fragment frag : getSupportFragmentManager().getFragments()) {
                DeletePreFrag(frag);
            }
            fragmentTransaction.add(R.id.container,fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
        else {
            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
    private void getData(){
        customerUtils.getCount();
        pendingOrderUtils.getCount();
        completedOrderUtils.getCount();
        delieveredOrderUtils.getCount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getData();
    }
}