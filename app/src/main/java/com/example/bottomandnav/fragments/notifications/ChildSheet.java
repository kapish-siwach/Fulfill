package com.example.bottomandnav.fragments.notifications;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bottomandnav.R;
import com.example.bottomandnav.ResponseModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class ChildSheet extends Fragment {

    private List<ResponseModel.Child> childList;
    private RecyclerView recyclerView;
    private ChildMenuAdapter childMenuAdapter;

    public ChildSheet(List<ResponseModel.Child> childList) {
        this.childList = childList;
    }
    public static ChildSheet newInstance(List<ResponseModel.Child> childList) {
        ChildSheet fragment = new ChildSheet(childList);
        Bundle args = new Bundle();
        args.putSerializable("child_sheet", (java.io.Serializable) childList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.childRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        childMenuAdapter = new ChildMenuAdapter(getContext(), childList);
        recyclerView.setAdapter(childMenuAdapter);
    }
}
