package com.example.bottomandnav;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomandnav.fragments.AddCreditLimit;
import com.example.bottomandnav.models.CreditHeaderModal;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreditLimitEnhancement extends AppCompatActivity {
    SessionManagement sessionManagement;
    private RecyclerView recyclerView;
    private TextView childTitle,pendingBtn,underApprovalBtn,approvedBtn,rejectedBtn;
    private ImageView backBtn;
    private List<CreditHeaderModal> creditHeaderModal=new ArrayList<>();
    CreditLimitAdapter creditLimitAdapter;
    private ProgressBar progressBar;
    private Button addBtn;
    private FrameLayout addFrame;
    private HorizontalScrollView horizontalScroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_credit_limit_enhancement);
        initViews();

        String title = sessionManagement.getUserDetail("child_title");
        childTitle.setText(title);
        backBtn.setOnClickListener(v -> onBackPressed());

        pendingBtn.setOnClickListener(v->getApiResponse("Pending"));
        underApprovalBtn.setOnClickListener(v->getApiResponse("Under Approval"));
        approvedBtn.setOnClickListener(v->getApiResponse("Approved"));
        rejectedBtn.setOnClickListener(v->getApiResponse("Rejected"));
        getApiResponse("Pending");

        addBtn.setOnClickListener(v->AddBtnClicked());
    }

    private void AddBtnClicked() {
        horizontalScroll.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        addFrame.setVisibility(View.VISIBLE);
        childTitle.setText(R.string.add_credit_limit);
        addBtn.setVisibility(View.INVISIBLE);

        backBtn.setOnClickListener(v->{
            horizontalScroll.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            addFrame.setVisibility(View.GONE);
            addBtn.setVisibility(View.VISIBLE);
            backBtn.setOnClickListener(vs -> onBackPressed());
            childTitle.setText(sessionManagement.getUserDetail("child_title"));
        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.addFrame,new AddCreditLimit() )
                .commit();
    }

    private void getApiResponse(String status) {
        progressBar.setVisibility(View.VISIBLE);
        try {
            JsonObject jsonBody = new JsonObject();
            jsonBody.addProperty("status", status);
            jsonBody.addProperty("email_id", sessionManagement.getUserDetail("email"));
            jsonBody.addProperty("rowsPerPage", 10);
            jsonBody.addProperty("pageNumber", 0);
            jsonBody.addProperty("credit_req_no", "");
            jsonBody.addProperty("seasion_code", "");
            jsonBody.addProperty("customer_code", "");

            RetrofitInstance.getApiInterface().getCreditHeaders(jsonBody).enqueue(new Callback<List<CreditHeaderModal>>() {
                @Override
                public void onResponse(Call<List<CreditHeaderModal>> call, Response<List<CreditHeaderModal>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("API_RESPONSE", new Gson().toJson(response.body()));
                        creditHeaderModal = response.body();
                        Log.d("API_RESPONSE", "onResponse: " + creditHeaderModal.get(0).credit_req_no);


                        if (creditHeaderModal.get(0).condition) {
                            recyclerView.setVisibility(View.VISIBLE);
                            // Update the adapter with the new data
                            creditLimitAdapter = new CreditLimitAdapter(creditHeaderModal);
                            recyclerView.setAdapter(creditLimitAdapter);
                            creditLimitAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred..", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<CreditHeaderModal>> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Failed to load data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Exception " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void initViews() {
        sessionManagement = new SessionManagement(this);
        backBtn = findViewById(R.id.childContainerBackBtn);
        childTitle = findViewById(R.id.childTitle);
        pendingBtn=findViewById(R.id.pendingBtn);
        underApprovalBtn=findViewById(R.id.underApprvalBtn);
        approvedBtn=findViewById(R.id.approvedBtn);
        rejectedBtn=findViewById(R.id.rejectedBtn);
        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);
        addBtn=findViewById(R.id.addBtn);
        addFrame=findViewById(R.id.addFrame);
        horizontalScroll=findViewById(R.id.horizontalScroll);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}