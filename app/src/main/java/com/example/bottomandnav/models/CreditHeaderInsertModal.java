package com.example.bottomandnav.models;

import java.util.ArrayList;

public class CreditHeaderInsertModal {
    public boolean condition;
    public String message;
    public String credit_req_no;
    public String seasion_code;
    public String season_name;
    public String customer_code;
    public int credit_limit_value;
    public String customer_name;
    public String customer_address;
    public String customer_state_code;
    public String customer_state_name;
    public String customer_country_name;
    public String customer_postal_code;
    public String customer_phone_no;
    public String status;
    public String created_on;
    public String created_by;
    public String updated_on;
    public String updated_by;
    public String approved_on;
    public String approved_by;
    public ArrayList<Line> lines;
    public class Line{
        public String credit_req_no;
        public String category_code;
        public String line_no;
        public String group_code;
        public int expected_amt;
        public String expected_collection_date;
        public String created_on;
        public String created_by;
    }
}
