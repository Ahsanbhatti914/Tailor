package com.example.tailor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tailor.Fragments.CompletedOrderFragment;
import com.example.tailor.Fragments.DeliveredOrderFragment;
import com.example.tailor.Fragments.PendingOrderFragment;

public class ViewPagerOrderAdapter extends FragmentStateAdapter {
    public ViewPagerOrderAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new PendingOrderFragment();
        }else if (position == 1){
            return new CompletedOrderFragment();
        }else {
            return new DeliveredOrderFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
