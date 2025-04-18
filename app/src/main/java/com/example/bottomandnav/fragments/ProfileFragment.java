package com.example.bottomandnav.fragments;

import android.content.Intent;
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
import com.example.bottomandnav.activities.LoginActivity;
import com.example.bottomandnav.R;
import com.example.bottomandnav.SessionManagement;

public class ProfileFragment extends Fragment {
    TextView name,userEmail,mobile,designation,department,companyId,nameF,mobileF,designationF,departmentF,companyIdF,requestHost,requestHostF,userType,userTypeF;
    ImageView signOut,userImg;
    private SessionManagement sessionManagement;
    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);


        setUpData();
        signOut.setOnClickListener(v->{
            sessionManagement.logout();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });
    }

    private void setUpData() {

        name.setText(sessionManagement.getUserDetail("name"));
        userEmail.setText(sessionManagement.getUserDetail("email"));
        mobile.setText(sessionManagement.getUserDetail("mobile"));
        companyId.setText(sessionManagement.getUserDetail("company_id"));
        nameF.setText(String.valueOf(sessionManagement.getUserDetail("name").toUpperCase().charAt(0)));
        mobileF.setText(String.valueOf(sessionManagement.getUserDetail("mobile").toUpperCase().charAt(0)));
        companyIdF.setText(String.valueOf(sessionManagement.getUserDetail("company_id").toUpperCase().charAt(0)));
        requestHost.setText(sessionManagement.getUserDetail("request_host_name"));
        requestHostF.setText(String.valueOf(sessionManagement.getUserDetail("request_host_name").toUpperCase().charAt(0)));
        userType.setText(sessionManagement.getUserDetail("user_type"));
        userTypeF.setText(String.valueOf(sessionManagement.getUserDetail("user_type").toUpperCase().charAt(0)));
        Glide.with(this)
                .load("https://dev4.pristinefulfil.com/assets/images/vendor_panel_img/mylogo.png")
                .into(userImg);
    }

    private void initViews(View view) {
        sessionManagement = new SessionManagement(getContext());
        name=view.findViewById(R.id.name);
        signOut=view.findViewById(R.id.signOut);
        userEmail=view.findViewById(R.id.userEmail);
        userImg=view.findViewById(R.id.userImg);
        mobile=view.findViewById(R.id.mobile);
        designation=view.findViewById(R.id.designation);
        department=view.findViewById(R.id.department);
        companyId=view.findViewById(R.id.companyID);
        nameF=view.findViewById(R.id.nameF);
        mobileF=view.findViewById(R.id.mobileF);
        designationF=view.findViewById(R.id.designationF);
        departmentF=view.findViewById(R.id.departmentF);
        companyIdF=view.findViewById(R.id.companyIdF);
        requestHost=view.findViewById(R.id.requestHost);
        requestHostF=view.findViewById(R.id.requestHostF);
        userType=view.findViewById(R.id.userType);
        userTypeF=view.findViewById(R.id.userTypeF);
    }
}