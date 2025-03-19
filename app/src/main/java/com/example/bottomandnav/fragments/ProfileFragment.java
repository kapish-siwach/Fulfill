package com.example.bottomandnav.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bottomandnav.LoginActivity;
import com.example.bottomandnav.R;

public class ProfileFragment extends Fragment {
    TextView name;
    ImageView signOut;
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
        name=view.findViewById(R.id.name);
        signOut=view.findViewById(R.id.signOut);
        SharedPreferences sp= getContext().getSharedPreferences("Response", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt=sp.edit();
        name.setText(sp.getString("name","N/A"));

        signOut.setOnClickListener(v->{
            edt.clear();
            edt.commit();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });
    }
}