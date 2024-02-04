package com.example.tailor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class OrderDetailsActivity extends AppCompatActivity {
    TabLayout myTabLayout;
    ViewPager2 viewPager;
    ViewPagerOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        myTabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.viewPager);

        myTabLayout.addTab(myTabLayout.newTab().setText("Pending"));
        myTabLayout.addTab(myTabLayout.newTab().setText("Completed"));
        myTabLayout.addTab(myTabLayout.newTab().setText("Delivered"));

        FragmentManager fm = getSupportFragmentManager();
        adapter = new ViewPagerOrderAdapter(fm,getLifecycle());
        viewPager.setAdapter(adapter);

        myTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                myTabLayout.selectTab(myTabLayout.getTabAt(position));
            }
        });
    }
}