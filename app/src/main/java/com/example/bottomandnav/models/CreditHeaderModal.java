package com.example.bottomandnav.models;
import com.google.gson.annotations.SerializedName;

public class CreditHeaderModal {
    @SerializedName("condition")
    public boolean condition;

    @SerializedName("total_rows")
    public String total_rows;

    @SerializedName("season_name")
    public String season_name;

    @SerializedName("customer_name")
    public String customer_name;

    @SerializedName("credit_req_no")
    public String credit_req_no;

    @SerializedName("seasion_code")
    public String seasion_code;

    @SerializedName("customer_code")
    public String customer_code;

    @SerializedName("credit_limit_value")
    public double credit_limit_value;

    @SerializedName("status")
    public String status;

    @SerializedName("reject_reason")
    public String reject_reason;

    @SerializedName("created_on")
    public String created_on;

    @SerializedName("created_by")
    public String created_by;

    @SerializedName("updated_on")
    public String updated_on;

    @SerializedName("updated_by")
    public String updated_by;

    @SerializedName("completed_on")
    public String completed_on;

    @SerializedName("completed_by")
    public String completed_by;

    @SerializedName("approved_on")
    public String approved_on;

    @SerializedName("approved_by")
    public String approved_by;
}
