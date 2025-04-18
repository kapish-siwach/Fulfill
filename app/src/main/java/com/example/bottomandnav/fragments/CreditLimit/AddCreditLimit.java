package com.example.bottomandnav.fragments.CreditLimit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.bottomandnav.R;
import com.example.bottomandnav.StaticMethods;
import com.example.bottomandnav.api.RetrofitInstance;
import com.example.bottomandnav.SessionManagement;
import com.example.bottomandnav.models.CreditHeaderInsertModal;
import com.example.bottomandnav.models.CustomersModel;
import com.example.bottomandnav.models.SeasonsModal;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCreditLimit extends Fragment /*implements AdapterView.OnItemSelectedListener*/ {
    private AutoCompleteTextView seasonSelector,customerSelecter;
    private List<SeasonsModal> seasonsModals= new ArrayList<>();
    String season_Code, customer_no,credit_limit_value,credit_req_no,customer_code,status;
    SessionManagement sessionManagement;
    private List<CustomersModel> customers=new ArrayList<>();
    private List<CreditHeaderInsertModal> insertHeader=new ArrayList<>();
    private Button resetBtn,submitBtn;

    public AddCreditLimit() {

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
        setupCustomerSearch();
        submitBtn.setOnClickListener(v-> {
            if (season_Code == null || customer_no == null || season_Code.isEmpty() || customer_no.isEmpty()) {
                Toast.makeText(getContext(), "Please select both Season and Customer", Toast.LENGTH_SHORT).show();
            } else {
                addCreditHeaderr();
            }
        });

        resetBtn.setOnClickListener(v->{
            seasonSelector.setText("");
            customerSelecter.setText("");
            season_Code = null;
            customer_no = null;
            credit_limit_value = null;
        });

    }


    private void addCreditHeaderr() {
        Log.d("FindingMError", "addCreditHeaderr ");

        try {
            Log.d("FindingMError", "addCreditHeaderr try");

            JsonObject body = new JsonObject();
            body.addProperty("credit_req_no", "");
            body.addProperty("seasion_code", season_Code);
            body.addProperty("customer_code", customer_no);
            body.addProperty("credit_limit_value", credit_limit_value);
            body.addProperty("created_by", sessionManagement.getUserDetail("email"));

            RetrofitInstance.getApiInterface().addCreditHeader(body).enqueue(new Callback<List<CreditHeaderInsertModal>>() {
                @Override
                public void onResponse(Call<List<CreditHeaderInsertModal>> call, Response<List<CreditHeaderInsertModal>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                            insertHeader = response.body();
                        if (insertHeader.get(0).condition){
//                            StaticMethods.loadFragments(requireActivity(),new CreditLimitView());

                            Bundle bundle = new Bundle();
                            bundle.putString("dataPass", new Gson().toJson(insertHeader));
                            Intent intent = new Intent(getActivity(), CreditLimitView.class);
                              intent.putExtras(bundle);
                             startActivity(intent);




                            storeResponseIntoSession();
                        }else {
                            Toast.makeText(getContext(), insertHeader.get(0).message, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Something gone wrong!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<CreditHeaderInsertModal>> call, Throwable t) {
                    Log.d("FindingMError", "addCreditHeaderr failure");

                    Toast.makeText(getContext(), "Failure: "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Toast.makeText(getContext(), "Exeception: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setupCustomerSearch() {
        customerSelecter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 0) {
                    fetchCustomers(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void fetchCustomers(String name) {
       try {
            JsonObject body = new JsonObject();
            body.addProperty("customer_name", name);
            body.addProperty("credit_req_no", "");
            body.addProperty("customer_type", "");
            body.addProperty("state_code", "");
            body.addProperty("latitude", "");
            body.addProperty("longitude", "");
            body.addProperty("email_id", sessionManagement.getUserDetail("email"));
            body.addProperty("row_per_page", 10);
            body.addProperty("page_number", 0);

            RetrofitInstance.getApiInterface().searchCustomer(body).enqueue(new Callback<List<CustomersModel>>() {
                @Override
                public void onResponse(Call<List<CustomersModel>> call, Response<List<CustomersModel>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        customers = response.body();

                        List<String> customerNames = new ArrayList<>();

                        for (CustomersModel customer : customers) {
                            if (customer.condition) {
                                customerNames.add(customer.name + " (" + customer.customer_no + ")");
                            } else {
                                customerSelecter.dismissDropDown();
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                requireContext(),
                                android.R.layout.simple_dropdown_item_1line,
                                customerNames
                        );
                        customerSelecter.setAdapter(adapter);
                        customerSelecter.showDropDown();

                        customerSelecter.setOnItemClickListener((parent, view, position, id) -> {
                            customer_no = customers.get(position).customer_no;
                            credit_limit_value=String.valueOf(customers.get(position).credit_limit);

//                            Toast.makeText(getContext(), "Selected Customer No: " + customer_no, Toast.LENGTH_SHORT).show();
                        });

                    }
                }

                @Override
                public void onFailure(Call<List<CustomersModel>> call, Throwable t) {
                    Toast.makeText(requireContext(), "Failed to load customers", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
           Toast.makeText(getContext(), "Exeception: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
       }
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
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                requireContext(),
                                android.R.layout.simple_dropdown_item_1line,
                                seasonNames
                        );
                        seasonSelector.setAdapter(adapter);

                        seasonSelector.setOnItemClickListener((parent, view, position, id) -> {
                            season_Code = seasonsModals.get(position).season_code;

//                            Toast.makeText(getContext(), "Selected Season: " + season_Code, Toast.LENGTH_SHORT).show();
                        });


                    }else {
                        Toast.makeText(getContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<SeasonsModal>> call, Throwable t) {
                    Toast.makeText(getContext(),"Failure "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "Exception: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void storeResponseIntoSession() {
//        sessionManagement.storeData("credit_req_no",insertHeader.get(0).credit_req_no);
//        sessionManagement.storeData("seasion_code",season_Code);
//        sessionManagement.storeData("customer_code",insertHeader.get(0).customer_code);
//        sessionManagement.storeData("status",insertHeader.get(0).status);
    }


    private void initViews(View view) {
        sessionManagement=new SessionManagement(getContext());
        seasonSelector=view.findViewById(R.id.seasonSelector);
        customerSelecter=view.findViewById(R.id.customerSelecter);
        resetBtn=view.findViewById(R.id.resetBtn);
        submitBtn=view.findViewById(R.id.submitBtn);

    }


//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            season_Code = seasonsModals.get(position).season_code;
//            customer_no = customers.get(position).customer_no;
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}