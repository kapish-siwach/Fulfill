package com.example.bottomandnav;

import com.example.bottomandnav.models.CreditHeaderModal;
import com.example.bottomandnav.models.ResponseModel;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("/api/UserLogin/Login")
    @Headers("Content-Type: application/json")
    Call<List<ResponseModel>> loginUser(@Body JsonObject request);


    @POST("/api/CreditLimitRequest/CreditLimitHeaderGet")
    @Headers({"Content-Type: application/json","company_id: Green Gold Seeds Pvt. Ltd."})
    Call<List<CreditHeaderModal>> getCreditHeaders(@Body JsonObject object);
}
