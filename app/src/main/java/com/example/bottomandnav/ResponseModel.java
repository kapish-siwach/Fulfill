package com.example.bottomandnav;

import java.util.ArrayList;

public class ResponseModel {
    public String name;
    public String message;
    public String email;
    public String device_id;
    public String password;
    public String roleId;
    public String role_name;
    public boolean condition;
    public String is_ho;
    public String cluster_id;
    public String company_id;
    public String employee_id_auto_genrated;
    public String phoneNo;
    public String email_ids;
    public String jwt_token;
    public String request_host_name;
    public String profile;
    public String user_type;
    public String customer_code;
    public ArrayList<Menu> menu;

    public class Menu{
        public String id;
        public String title;
        public String tooltip;
        public String translate;
        public String type;
        public String icon;
        public ArrayList<Child> children;
    }
    public class Child{
        public String title;
        public String type;
        public String icon;
        public String id;
        public String link;
    }

}
