package com.example.bottomandnav;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ChildContainerActivity extends AppCompatActivity {
    SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_container);

        sessionManagement = new SessionManagement(this);

        ImageView backBtn = findViewById(R.id.childContainerBackBtn);
        TextView childTitle = findViewById(R.id.childTitle);
        String title = sessionManagement.getUserDetail("child_title");
        childTitle.setText(title);
        backBtn.setOnClickListener(v->onBackPressed());

//
//        if (title != null) {
//            switch (title) {
//                case "Credit Limit Enhancement":
////                    fragment = new CreditLimitEnhancement();
//                    switchFragment(new CreditLimitEnhancement());
//                    break;
//
//                default:
//                    Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
    }
    void switchFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}