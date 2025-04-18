package com.example.bottomandnav.api;

import com.example.bottomandnav.fragments.menus.CreditGroupCode;
import com.example.bottomandnav.models.CreditAllDataModal;
import com.example.bottomandnav.models.CreditHeaderInsertModal;
import com.example.bottomandnav.models.CreditItemCategory;
import com.example.bottomandnav.models.CustomersModel;
import com.example.bottomandnav.models.ResponseModel;
import com.example.bottomandnav.models.SeasonsModal;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("/api/UserLogin/Login")
    @Headers("Content-Type: application/json")
    Call<List<ResponseModel>> loginUser(@Body JsonObject request);


    @POST("/api/CreditLimitRequest/CreditLimitHeaderGet")
    @Headers({"Content-Type: application/json","company_id: Green Gold Seeds Pvt. Ltd."})
    Call<List<CreditAllDataModal>> getCreditHeaders(@Body JsonObject object);

    @POST("/api/Planting/SeasonMstGet")
    @Headers({"company_id: Green Gold Seeds Pvt. Ltd.","Content-Type: application/json-patch+json"})
    Call<List<SeasonsModal>> getSeasons(@Body JsonObject object);

    @POST("/api/Customer/CustomerGetGeoSetupWise")
    @Headers({"company_id: Green Gold Seeds Pvt. Ltd.","Content-Type: application/json-patch+json","accept: */*"})
    Call<List<CustomersModel>> searchCustomer(@Body JsonObject object);

    @POST("/api/CreditLimitRequest/CreditLimitRequestHeaderInsert")
    @Headers({"company_id: Green Gold Seeds Pvt. Ltd.","Content-Type: application/json-patch+json","accept: */*"})
    Call<List<CreditHeaderInsertModal>> addCreditHeader(@Body JsonObject object);

    @POST("/api/CreditLimitRequest/CreditLimitHeaderGet")
    @Headers({"company_id: Green Gold Seeds Pvt. Ltd.","Content-Type: application/json-patch+json","accept: */*"})
    Call<List</*CreditHeaderResponse*/ CreditHeaderInsertModal>> showCreditHeader(@Body JsonObject object);

    @GET("/api/ItemMaster/ItemCategoryMstGet")
    @Headers({"company_id: Green Gold Seeds Pvt. Ltd.","accept: */*"})
    Call<List<CreditItemCategory>> getCreditItemCatgory();

    @POST("/api/ItemMaster/ItemGroupMstGet")
    @Headers({"company_id: Green Gold Seeds Pvt. Ltd.","Content-Type: application/json-patch+json","accept: */*"})
    Call<List<CreditGroupCode>> getCreditGroupCode(@Body JsonObject object);

    @POST("/api/CreditLimitRequest/CreditLimitRequestLineInsert")
    @Headers({"company_id: Green Gold Seeds Pvt. Ltd.","Content-Type: application/json-patch+json","accept: */*"})
    Call<List<CreditHeaderInsertModal>> addHeaderLine(@Body JsonObject object);
}
