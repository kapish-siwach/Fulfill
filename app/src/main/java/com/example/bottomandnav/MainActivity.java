package com.example.bottomandnav;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.bottomandnav.fragments.HomeFragment;
import com.example.bottomandnav.fragments.menus.MenuFragment;
import com.example.bottomandnav.fragments.notifications.NotificationFragment;
import com.example.bottomandnav.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        changeFragemnt(new HomeFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if (id==R.id.homeBtn){
                    changeFragemnt(new HomeFragment());
                    return true;
                }
                if (id==R.id.menuBtn){
                    MenuFragment menuFragment = new MenuFragment();
                    menuFragment.show(getSupportFragmentManager(), menuFragment.getTag());
                    return true;
                }
                if (id==R.id.notificationBtn){
//                    changeFragemnt(new NotificationFragment());
                    NotificationFragment notificationFragment=new NotificationFragment();
                    notificationFragment.show(getSupportFragmentManager(),notificationFragment.getTag());
                    return true;
                }
                if (id==R.id.profileBtn){
                    changeFragemnt(new ProfileFragment());
                    return true;
                }
                return false;
            }
        });
    }

    private void changeFragemnt(Fragment clickedFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,clickedFragment)
                .commit();
    }
}