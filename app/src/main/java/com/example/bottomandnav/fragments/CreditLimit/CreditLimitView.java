package com.example.bottomandnav.fragments.CreditLimit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomandnav.R;
import com.example.bottomandnav.SessionManagement;
import com.example.bottomandnav.api.RetrofitInstance;
import com.example.bottomandnav.models.CreditHeaderResponse;
import com.example.bottomandnav.models.CreditAllDataModal;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreditLimitView extends Fragment {
    TextView creditRequestNo,seasonCode,customerCode,createdOn;
    SessionManagement session;
    List<CreditHeaderResponse> creditResponse=new ArrayList<>();

    public CreditLimitView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credit_limit_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        
        getHeaderResponse();
    }

    private void getHeaderResponse() {
        try {
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("credit_req_no",session.getUserDetail("credit_req_no"));
            jsonObject.addProperty("seasion_code",session.getUserDetail("seasion_code"));
            jsonObject.addProperty("customer_code",session.getUserDetail("customer_code"));
            jsonObject.addProperty("status",session.getUserDetail("status"));
            jsonObject.addProperty("email_id",session.getUserDetail("email"));
            jsonObject.addProperty("rowsPerPage",10);
            jsonObject.addProperty("pageNumber",0);

            RetrofitInstance.getApiInterface().showCreditHeader(jsonObject).enqueue(new Callback<List<CreditHeaderResponse>>() {
                @Override
                public void onResponse(Call<List<CreditHeaderResponse>> call, Response<List<CreditHeaderResponse>> response) {
                    if (response.isSuccessful()&&response.body()!=null){
                        creditResponse=response.body();
                        creditRequestNo.setText(creditResponse.get(0).credit_req_no);
                        seasonCode.setText(creditResponse.get(0).seasion_code);
                        customerCode.setText(creditResponse.get(0).customer_code);
                        createdOn.setText(creditResponse.get(0).created_on);
                    }
                }

                @Override
                public void onFailure(Call<List<CreditHeaderResponse>> call, Throwable t) {
                    Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "Exception "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews(View view) {
        creditRequestNo=view.findViewById(R.id.creditRequestNo);
        seasonCode=view.findViewById(R.id.seasonCode);
        customerCode=view.findViewById(R.id.customerCode);
        createdOn=view.findViewById(R.id.createdOn);
        session=new SessionManagement(getContext());
    }
}