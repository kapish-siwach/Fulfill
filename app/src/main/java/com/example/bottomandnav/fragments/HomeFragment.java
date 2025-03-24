package com.example.bottomandnav.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bottomandnav.R;
import com.example.bottomandnav.SessionManagement;

public class HomeFragment extends Fragment {
    TextView userTitle;
    ImageView titleImage,gifArea;
    SessionManagement session;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        userTitle.setText(session.getUserDetail("name"));
        Glide.with(getContext()).load("https://dev4.pristinefulfil.com/assets/images/vendor_panel_img/mylogo.png")
                .into(titleImage);

        Glide.with(getContext()).load(R.drawable.zumba).into(gifArea);
    }

    private void initViews(View view) {
        userTitle=view.findViewById(R.id.userTitle);
        titleImage=view.findViewById(R.id.titleImage);
        gifArea=view.findViewById(R.id.gifArea);
        session=new SessionManagement(getContext());
    }
}