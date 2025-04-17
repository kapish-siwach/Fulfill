package com.example.bottomandnav.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.bottomandnav.R;
import com.example.bottomandnav.RetrofitInstance;
import com.example.bottomandnav.models.SeasonsModal;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCreditLimit extends Fragment implements AdapterView.OnItemSelectedListener {
    private AutoCompleteTextView seasonSelector;
    private List<SeasonsModal> seasonsModals= new ArrayList<>();
    String season_Code;
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
        getSeasons();
    }

    private void getSeasons() {
        try {
            JsonObject body=new JsonObject();
            body.addProperty("season_code","");
            body.addProperty("season_name","");
            body.addProperty("is_visible",1);

            RetrofitInstance.getApiInterface().getSeasons(body).enqueue(new Callback<List<SeasonsModal>>() {
                @Override
                public void onResponse(Call<List<SeasonsModal>> call, Response<List<SeasonsModal>> response) {
                    if (response.isSuccessful()&& response.body()!=null){
                        seasonsModals=response.body();
                        List<String> seasonNames = new ArrayList<>();
                        for (SeasonsModal season : seasonsModals) {
                            seasonNames.add(season.season_name+" ("+season.season_code+")");

                            // Make sure getSeasonName() exists
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                requireContext(),
                                android.R.layout.simple_dropdown_item_1line,
                                seasonNames
                        );
                        seasonSelector.setAdapter(adapter);




                    }else {
                        Toast.makeText(getContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<SeasonsModal>> call, Throwable t) {

                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "Exception: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }




    private void initViews(View view) {
        seasonSelector=view.findViewById(R.id.seasonSelector);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            season_Code = seasonsModals.get(position).season_code;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}