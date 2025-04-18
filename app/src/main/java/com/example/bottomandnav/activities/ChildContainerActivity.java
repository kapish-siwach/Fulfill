package com.example.bottomandnav.activities;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.bottomandnav.R;
import com.example.bottomandnav.SessionManagement;
import com.example.bottomandnav.fragments.CreditLimit.CreditLimitView;

public class ChildContainerActivity extends AppCompatActivity {
    SessionManagement sessionManagement;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_container);

        sessionManagement = new SessionManagement(this);
        frameLayout = findViewById(R.id.fragment_container);

    }

}
