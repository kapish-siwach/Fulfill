package com.example.bottomandnav.fragments.CreditLimit;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomandnav.R;
import com.example.bottomandnav.SessionManagement;
import com.example.bottomandnav.StaticMethods;
import com.example.bottomandnav.api.RetrofitInstance;
import com.example.bottomandnav.models.CreditAllDataModal;
import com.example.bottomandnav.models.CreditHeaderInsertModal;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreditLimitAdapter extends RecyclerView.Adapter<CreditLimitAdapter.MyViewHolder> {
private List<CreditAllDataModal> creditAllDataModals;
private List<CreditHeaderInsertModal> list;
private Context context;
SessionManagement sessionManagement;

    public CreditLimitAdapter(List<CreditAllDataModal> creditAllDataModals, Context context) {
        this.creditAllDataModals = creditAllDataModals;
        this.context = context;
        this.sessionManagement = new SessionManagement(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CreditAllDataModal creditItem= creditAllDataModals.get(position);

        holder.creditRequestNo.setText(creditItem.credit_req_no);
        holder.seasonCode.setText(creditItem.seasion_code);
        holder.customerCode.setText(creditItem.customer_code);
        holder.createdOn.setText(creditItem.created_on);

        holder.itemView.setOnClickListener(v -> {
            getApiResponse(creditItem.credit_req_no,creditItem.seasion_code,creditItem.customer_code,creditItem.credit_limit_value);
            // Do something like open details screen
//            sessionManagement.storeData("credit_req_no",creditItem.credit_req_no);
//            sessionManagement.storeData("seasion_code",creditItem.seasion_code);
//            sessionManagement.storeData("created_on",creditItem.created_on);
//            sessionManagement.storeData("status",creditItem.status);
//            sessionManagement.storeData("customer_code",creditItem.customer_code);
////            StaticMethods.loadFragmentsWithBackStack((FragmentActivity) context,new CreditLimitView());
//            context.startActivity(new Intent(context,CreditLimitView.class));
        });



    }

    private void getApiResponse(String credit_req_no,String season_name,String customer_code,double limit_value) {

        try {
            JsonObject jsonBody = new JsonObject();
            jsonBody.addProperty("credit_req_no", credit_req_no);
            jsonBody.addProperty("seasion_code", season_name);
            jsonBody.addProperty("customer_code", customer_code);
            jsonBody.addProperty("credit_limit_value", String.valueOf(limit_value));
            jsonBody.addProperty("created_by", sessionManagement.getUserDetail("email"));


            RetrofitInstance.getApiInterface().addCreditHeader(jsonBody).enqueue(new Callback<List<CreditHeaderInsertModal>>() {
                @Override
                public void onResponse(Call<List<CreditHeaderInsertModal>> call, Response<List<CreditHeaderInsertModal>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("API_RESPONSE", new Gson().toJson(response.body()));
                        list = response.body();
                        Bundle bundle = new Bundle();
                        bundle.putString("dataPass", new Gson().toJson(list));
                       // bundle.putString("dataPass", new Gson().toJson(list.get(0).lines));
                        Intent intent = new Intent(context, CreditLimitView.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);






                    } else {
                        Toast.makeText(context, "Some error occurred..", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<List<CreditHeaderInsertModal>> call, Throwable t) {

                    Toast.makeText(context, "Failed to load data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {

            Toast.makeText(context, "Exception " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public int getItemCount() {
        return creditAllDataModals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView creditRequestNo,seasonCode,customerCode,createdOn;

        public MyViewHolder(@NonNull View view) {
            super(view);
            creditRequestNo=view.findViewById(R.id.creditRequestNo);
            seasonCode=view.findViewById(R.id.seasonCode);
            customerCode=view.findViewById(R.id.customerCode);
            createdOn=view.findViewById(R.id.createdOn);
        }



    }
}
