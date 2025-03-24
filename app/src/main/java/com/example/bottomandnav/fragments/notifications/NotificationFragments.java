package com.example.bottomandnav.fragments.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bottomandnav.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class NotificationFragments extends Fragment {
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2=view.findViewById(R.id.viewPager);
        tabLayout=view.findViewById(R.id.tabLayout);
        viewPagerAdapter=new ViewPagerAdapter(getActivity());
        viewPagerAdapter.addFragment(new Alert(),"Alert");
        viewPagerAdapter.addFragment(new Notification(),"Notification");
        viewPager2.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) ->
                tab.setText(viewPagerAdapter.getPageTitle(position)))).attach();
    }

}