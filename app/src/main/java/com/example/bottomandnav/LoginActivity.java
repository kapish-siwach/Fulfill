package com.example.bottomandnav;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ImageView pristineImg;
    TextInputEditText emailInput, passwordInput;
    TextInputLayout emailLayout, passwordLayout;
    ProgressBar progressIndicator;
    Button signInBtn;
    TextView errorText;
    private List<ResponseModel> responseData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        SharedPreferences sp = getSharedPreferences("Response", MODE_PRIVATE);
        if (sp.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        signInBtn.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        if (emailInput.getText().toString().trim().equalsIgnoreCase("") || passwordInput.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, "Both fields are required", Toast.LENGTH_SHORT).show();
            return;
        }
        progressIndicator.setVisibility(View.VISIBLE);
        try {
            JsonObject jsonBody = new JsonObject();
            jsonBody.addProperty("email", emailInput.getText().toString().trim());
            jsonBody.addProperty("password", passwordInput.getText().toString().trim());


            RetrofitInstance.getApiInterface().loginUser(jsonBody).enqueue(new Callback<List<ResponseModel>>() {
                @Override
                public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                    if (response.isSuccessful()) {
                        List<ResponseModel> responseModel = response.body();
                        if (responseModel.size() > 0 && responseModel.get(0).condition) {
                            responseData = responseModel;
                            storeData();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                            clearFields();
                            progressIndicator.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(LoginActivity.this, responseModel.get(0).message, Toast.LENGTH_SHORT).show();
                            progressIndicator.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Error!!", Toast.LENGTH_SHORT).show();
                        progressIndicator.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressIndicator.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            Log.d("Post Api Error", "Exception: " + e.getMessage());
        }
    }

    private void clearFields() {
        emailInput.setText("");
        passwordInput.setText("");
        errorText.setVisibility(View.GONE);
        progressIndicator.setVisibility(View.GONE);
        finish();
    }
    private void storeData() {
        SharedPreferences sp=getSharedPreferences("Response",MODE_PRIVATE);
        SharedPreferences.Editor edt=sp.edit();
        edt.putString("email",responseData.get(0).email);
        edt.putString("role_name",responseData.get(0).role_name);
        edt.putString("company_i",responseData.get(0).company_id);
        edt.putString("menu",new Gson().toJson(responseData.get(0).menu));
        edt.putString("name",responseData.get(0).name);
        edt.putBoolean("isLoggedIn",true);
        edt.putString("profile",responseData.get(0).profile);
        edt.apply();

    }

    private void initViews() {
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        errorText = findViewById(R.id.errorText);
        pristineImg = findViewById(R.id.pristineImg);
        signInBtn = findViewById(R.id.signInBtn);
        progressIndicator = findViewById(R.id.progrssBarLogin);
        Glide.with(this)
                .load("https://dev4.pristinefulfil.com/assets/images/vendor_panel_img/mylogo.png")
                .into(pristineImg);
    }

}