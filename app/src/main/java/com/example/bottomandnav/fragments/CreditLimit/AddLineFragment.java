package com.example.bottomandnav.fragments.CreditLimit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.bottomandnav.R;
import com.example.bottomandnav.SessionManagement;
import com.example.bottomandnav.api.RetrofitInstance;
import com.example.bottomandnav.fragments.menus.CreditGroupCode;
import com.example.bottomandnav.models.CreditAllDataModal;
import com.example.bottomandnav.models.CreditHeaderInsertModal;
import com.example.bottomandnav.models.CreditItemCategory;
import com.example.bottomandnav.models.SeasonsModal;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLineFragment extends Fragment {
    private AutoCompleteTextView categorySelector,groupCodeSelector,dateSelector;
    private TextInputEditText expectedAmount;
    private Button submitBtn,resetBtn;
    List<CreditItemCategory> creditItems=new ArrayList<>();
    List<CreditGroupCode> creditGroup=new ArrayList<>();
    String CategoryCode, GroupCode,DateSelected,Amount;
    List<CreditHeaderInsertModal.Line> lineModal=new ArrayList<>();
    List<CreditHeaderInsertModal> lineModal2=new ArrayList<>();
    SessionManagement session;
    CreditItemAdapter itemAdapter;
    public AddLineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_line, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        getCategorys();


        dateSelector.setOnClickListener(v -> openDatePicker());

        submitBtn.setOnClickListener(v->{
            Amount=expectedAmount.getText().toString().trim();
            if (Amount.isBlank()&&CategoryCode.isBlank()&&GroupCode.isBlank()&&DateSelected.isBlank()){
                Toast.makeText(getContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
            }else submitCreditHeaderLine();

        });
        resetBtn.setOnClickListener(v->{
            expectedAmount.setText("");
            categorySelector.setText("");
            groupCodeSelector.setText("");
            dateSelector.setText("");
            CategoryCode="";
            GroupCode="";
            DateSelected="";
            Amount="";
        });
    }

    private void submitCreditHeaderLine() {
        try {
            JsonObject jsonBody = new JsonObject();
            jsonBody.addProperty("credit_req_no", session.getUserDetail("credit"));
            jsonBody.addProperty("category_code",CategoryCode );
            jsonBody.addProperty("expected_amt",Amount );
            jsonBody.addProperty("expected_collection_date", DateSelected);
            jsonBody.addProperty("group_code", GroupCode);
            jsonBody.addProperty("created_by", session.getUserDetail("email"));

            RetrofitInstance.getApiInterface().addHeaderLine(jsonBody).enqueue(new Callback<List<CreditHeaderInsertModal>>() {
                @Override
                public void onResponse(Call<List<CreditHeaderInsertModal>> call, Response<List<CreditHeaderInsertModal>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        //lineModal = response.body();
                        lineModal2 = response.body();
                        if (lineModal2.get(0).condition) {
//                            Toast.makeText(getContext(), "fghdfjkhg", Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("dataPass", new Gson().toJson(lineModal2));
                            Intent intent = new Intent(getActivity(), CreditLimitView.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
   //                         itemAdapter=new CreditItemAdapter(lineModal,getActivity());
   //                         requireActivity().getSupportFragmentManager().popBackStack();
//                            recyclerView.setVisibility(View.VISIBLE);
//                            // Update the adapter with the new data
//                            creditLimitAdapter = new CreditLimitAdapter(lineModal,CreditLimitEnhancement.this);
//                            recyclerView.setAdapter(creditLimitAdapter);
//                            creditLimitAdapter.notifyDataSetChanged();
//                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(getContext(), lineModal2.get(0).message, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Some error occurred..", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<CreditHeaderInsertModal>> call, Throwable t) {
                    Toast.makeText(getContext(), "Failed to load data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Toast.makeText(getContext(),"Exception "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the date (e.g. 2025-04-18)
                    String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d",
                            selectedYear, selectedMonth + 1, selectedDay);
                    dateSelector.setText(selectedDate);
                    DateSelected=selectedDate;
                }, year, month, day);

        datePickerDialog.show();
    }



    private void getGroupCode(String category) {
        try {
            JsonObject body=new JsonObject();
            body.addProperty("category_code",category);

            RetrofitInstance.getApiInterface().getCreditGroupCode(body).enqueue(new Callback<List<CreditGroupCode>>() {
                @Override
                public void onResponse(Call<List<CreditGroupCode>> call, Response<List<CreditGroupCode>> response) {
                    if (response.isSuccessful()&& response.body()!=null) {
                        creditGroup = response.body();
                        List<String> groupNames = new ArrayList<>();
                        for (CreditGroupCode groupName : creditGroup) {
                            groupNames.add(groupName.group_name + " (" + groupName.group_code + ")");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                requireContext(),
                                android.R.layout.simple_dropdown_item_1line,
                                groupNames
                        );
                        groupCodeSelector.setAdapter(adapter);
                        groupCodeSelector.setThreshold(1);

                        groupCodeSelector.setOnItemClickListener((parent, view, position, id) -> {
                            GroupCode = creditGroup.get(position).group_code;
                        });

                    }else {
                        Toast.makeText(getContext(),"An Error Occured!",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<CreditGroupCode>> call, Throwable t) {
                    Toast.makeText(getContext(),"Failure "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "Exception: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void getCategorys() {
        RetrofitInstance.getApiInterface().getCreditItemCatgory().enqueue(new Callback<List<CreditItemCategory>>() {
            @Override
            public void onResponse(Call<List<CreditItemCategory>> call, Response<List<CreditItemCategory>> response) {
                if (response.isSuccessful() && response.body() != null){
                    creditItems=response.body();
                    if (creditItems.get(0).condition){
                        List<String> items= new ArrayList<>();
                        for (CreditItemCategory itemCategory:creditItems){
                            items.add(itemCategory.category_description+" ("+itemCategory.category_code+")");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                requireContext(),
                                android.R.layout.simple_dropdown_item_1line,
                                items
                        );
                        categorySelector.setAdapter(adapter);


                        categorySelector.setOnItemClickListener((parent, view, position, id) -> {
                            getGroupCode(creditItems.get(position).category_code);
                           CategoryCode=creditItems.get(position).category_code;

                        });
                    }else {
                        Toast.makeText(getContext(), creditItems.get(0).message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CreditItemCategory>> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initViews(View view) {
        session=new SessionManagement(getActivity());
        submitBtn=view.findViewById(R.id.submitBtn);
        resetBtn=view.findViewById(R.id.resetBtn);
        expectedAmount=view.findViewById(R.id.expectedAmount);
        categorySelector=view.findViewById(R.id.categorySelector);
        groupCodeSelector=view.findViewById(R.id.groupCodeSelector);
        dateSelector=view.findViewById(R.id.expectedCollectionDate);
       // CategoryCode="";
    }
}