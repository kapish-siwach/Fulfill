package com.example.bottomandnav.fragments.menus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomandnav.R;
import com.example.bottomandnav.models.ResponseModel;
import com.example.bottomandnav.SessionManagement;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends BottomSheetDialogFragment {
    RecyclerView recyclerView;
    MenuAdapter menuAdapter;
    List<ResponseModel.Menu> menuList=new ArrayList<>();
    FrameLayout frameLayout;
    private ImageView backBtn, closeBtn;
    TextView listTitle;
    SessionManagement sessionManagement;
    public MenuFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        buttonListners();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        loadMenuData();
    }

    private void buttonListners() {
        backBtn.setOnClickListener(v->{
            listTitle.setText("MY LIST");
            recyclerView.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            backBtn.setVisibility(View.INVISIBLE);
        });
        closeBtn.setOnClickListener(v->dismiss());
    }

    private void initViews(View view) {
        sessionManagement =new SessionManagement(getContext());
        backBtn=view.findViewById(R.id.backBtn);
        listTitle=view.findViewById(R.id.listTitle);
        closeBtn=view.findViewById(R.id.closeBottom);
        recyclerView=view.findViewById(R.id.recyclerView);
        frameLayout=view.findViewById(R.id.childFrame);
    }

    private void loadMenuData() {
        String menuJson= sessionManagement.getUserDetail("menu");
        Gson gson=new Gson();
        Type listType=new TypeToken<ArrayList<ResponseModel.Menu>>(){}.getType();
        menuList=gson.fromJson(menuJson,listType);
        if (menuList!=null &&! menuList.isEmpty()) {
            menuAdapter = new MenuAdapter(getContext(), menuList, new MenuAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position, ResponseModel.Menu item) {
                    showSubMenu(item);
                }
            });
            recyclerView.setAdapter(menuAdapter);
        }else {
            Toast.makeText(getActivity(), "No Menu data found", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSubMenu(ResponseModel.Menu item) {
        if (item.children != null && !item.children.isEmpty()) {
            listTitle.setText(item.title);

            ChildSheet childMenuFragment = ChildSheet.newInstance(item.children);

            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.childFrame, childMenuFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            frameLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            backBtn.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getActivity(), "No children for this menu item", Toast.LENGTH_SHORT).show();
        }

    }


}
