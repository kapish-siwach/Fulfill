package com.example.bottomandnav.fragments.menus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomandnav.R;
import com.example.bottomandnav.ResponseModel;
import com.example.bottomandnav.RetrofitInstance;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private ProgressBar progressIndicator;
    private List<String> menuHeaders;
    private HashMap<String, List<String>> menuChildren;
    private List<ResponseModel> responseData = new ArrayList<>();
    private ImageView backBtn, closeBtn;
    TextView listTitle;
    String selectedItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        closeBtn.setOnClickListener(v -> dismiss());
        backBtn.setOnClickListener(v -> getApiResponse());
        getApiResponse();
    }

    private void loadMenuData() {
        backBtn.setVisibility(View.INVISIBLE);
        menuHeaders = new ArrayList<>();
        menuChildren = new HashMap<>();
        for (ResponseModel.Menu menu : responseData.get(0).menu) {
            menuHeaders.add(menu.title);

            List<String> childItems = new ArrayList<>();
            if (menu.children != null) {
                for (ResponseModel.Child child : menu.children) {
                    childItems.add(child.title);
                }
            }
            menuChildren.put(menu.title, childItems);
        }

        menuAdapter = new MenuAdapter(getActivity(), menuHeaders, menuChildren);
        recyclerView.setAdapter(menuAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && gestureDetector.onTouchEvent(e)) {
                    int position = recyclerView.getChildAdapterPosition(childView);
                    selectedItem = menuAdapter.currentMenuItems.get(position);
                    backBtn.setVisibility(View.VISIBLE);

                    if (menuChildren.containsKey(selectedItem)) {
                        menuAdapter.showChildItems(selectedItem);
                        listTitle.setText(selectedItem);
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    private void getApiResponse() {
        progressIndicator.setVisibility(View.VISIBLE);
        SharedPreferences sp=getContext().getSharedPreferences("Response", Context.MODE_PRIVATE);

        try {
            JsonObject jsonBody = new JsonObject();
            jsonBody.addProperty("email", "ritik.jaiswal@pristinebs.co.in");
            jsonBody.addProperty("password", "12345");

            RetrofitInstance.getApiInterface().loginUser(jsonBody).enqueue(new Callback<List<ResponseModel>>() {
                @Override
                public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                    if (response.isSuccessful()) {
                        List<ResponseModel> responseModel = response.body();
                        if (responseModel.size() > 0 && responseModel.get(0).condition) {
                            responseData = responseModel;
                            loadMenuData();
                            listTitle.setText("MY LIST");
                            progressIndicator.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(getActivity(), responseModel.get(0).message, Toast.LENGTH_SHORT).show();
                            progressIndicator.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Error!!", Toast.LENGTH_SHORT).show();
                        progressIndicator.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                    Toast.makeText(getActivity(), "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressIndicator.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.listView);
        progressIndicator = view.findViewById(R.id.progressBar);
        backBtn = view.findViewById(R.id.backBtn);
        closeBtn = view.findViewById(R.id.closeBottom);
        listTitle = view.findViewById(R.id.listTitle);
    }
}