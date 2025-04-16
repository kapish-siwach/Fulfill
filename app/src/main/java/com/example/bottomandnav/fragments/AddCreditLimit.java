package com.example.bottomandnav.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.bottomandnav.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddCreditLimit extends Fragment {
    private AutoCompleteTextView seasonSelector;
    public AddCreditLimit() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_credit_limit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        String[] seasons = {"Summer", "Winter", "Spring", "Autumn"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                seasons
        );

        seasonSelector.setAdapter(adapter);

        seasonSelector.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedSeason = seasons[position];

        });


    }

    private void initViews(View view) {
        seasonSelector=view.findViewById(R.id.seasonSelector);
    }
}