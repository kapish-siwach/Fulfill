package com.example.bottomandnav.fragments.CreditLimit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomandnav.R;
import com.example.bottomandnav.SessionManagement;
import com.example.bottomandnav.api.RetrofitInstance;
import com.example.bottomandnav.models.CreditHeaderInsertModal;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreditLimitView extends AppCompatActivity {

    TextView creditRequestNo, seasonCode, customerCode, createdOn;
    SessionManagement session;
    List<CreditHeaderInsertModal> creditResponse;
    ImageView backBtn;
    Button completeBtn, deleteLineBtn, addLineBtn;
    LinearLayout contentContainer;
    FrameLayout addLineFrame;
    RecyclerView lineRecycler;
    CreditItemAdapter lineAdapter;
    private List<CreditHeaderInsertModal> insertHeader=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_credit_limit_view);


        String jsonData = getIntent().getStringExtra("dataPass");

        Type listType = new TypeToken<List<CreditHeaderInsertModal>>() {}.getType();
        insertHeader = new Gson().fromJson(jsonData, listType);

        //Type listType1 = new TypeToken<List<CreditHeaderInsertModal.Line>>() {}.getType();
       // insertHeader1 = new Gson().fromJson(jsonData, listType1);





        initViews();



        backBtn.setOnClickListener(v -> {
            if (addLineFrame.getVisibility() == View.VISIBLE) {
                // Restore main layout
                contentContainer.setVisibility(View.VISIBLE);
                completeBtn.setVisibility(View.VISIBLE);
                addLineFrame.setVisibility(View.GONE);
            } else {
                onBackPressed();
            }
        });

        addLineBtn.setOnClickListener(v -> {
            session.storeData("credit",insertHeader.get(0).credit_req_no);



            contentContainer.setVisibility(View.GONE);
            completeBtn.setVisibility(View.GONE);
            addLineFrame.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.addLineFrame, new AddLineFragment())
                    .addToBackStack(null)
                    .commit();
        });

        deleteLineBtn.setOnClickListener(v -> {});

        // 👉 Handle restoring view after AddLineFragment is popped
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            AddLineFragment fragment = (AddLineFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.addLineFrame);
            if (fragment == null || !fragment.isVisible()) {
                contentContainer.setVisibility(View.VISIBLE);
                completeBtn.setVisibility(View.VISIBLE);
                addLineFrame.setVisibility(View.GONE);


            }
        });
    }

    private void initViews() {
        creditRequestNo = findViewById(R.id.creditRequestNo);
        seasonCode = findViewById(R.id.seasonCode);
        customerCode = findViewById(R.id.customerCode);
        createdOn = findViewById(R.id.createdOn);
        session = new SessionManagement(this);
        completeBtn = findViewById(R.id.completeBtn);
        backBtn = findViewById(R.id.childContainerBackBtn);
        addLineBtn = findViewById(R.id.addLineBtn);
        deleteLineBtn = findViewById(R.id.deleteLineBtn);
        contentContainer = findViewById(R.id.contentContainer);
        addLineFrame = findViewById(R.id.addLineFrame);
        lineRecycler = findViewById(R.id.lineRecycler);

        creditRequestNo.setText(insertHeader.get(0).credit_req_no);
        seasonCode.setText(insertHeader.get(0).seasion_code);
        customerCode.setText(insertHeader.get(0).customer_code);
        createdOn.setText(insertHeader.get(0).created_on);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        lineRecycler.setLayoutManager(linearLayoutManager);


        CreditItemAdapter lineAdapter= new CreditItemAdapter(insertHeader.get(0).lines,getApplicationContext());

        lineRecycler.setAdapter(lineAdapter);
        lineAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String jsonData = getIntent().getStringExtra("dataPass");

//        Type listType = new TypeToken<List<CreditHeaderInsertModal>>() {}.getType();
//        insertHeader = new Gson().fromJson(jsonData, listType);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        lineRecycler.setLayoutManager(linearLayoutManager);


      //  CreditItemAdapter lineAdapter= new CreditItemAdapter(insertHeader,this);

        //lineRecycler.setAdapter(lineAdapter);
       // lineAdapter.notifyDataSetChanged();
    }
}
