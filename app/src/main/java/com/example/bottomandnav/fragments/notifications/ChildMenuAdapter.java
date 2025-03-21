package com.example.bottomandnav.fragments.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomandnav.R;
import com.example.bottomandnav.ResponseModel;

import java.util.List;

public class ChildMenuAdapter extends RecyclerView.Adapter<ChildMenuAdapter.ChildMenuViewHolder> {

    private Context context;
    private List<ResponseModel.Child> childList;

    public ChildMenuAdapter(Context context, List<ResponseModel.Child> childList) {
        this.context = context;
        this.childList = childList;
    }

    @NonNull
    @Override
    public ChildMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_child, parent, false);
        return new ChildMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildMenuViewHolder holder, int position) {
        ResponseModel.Child child = childList.get(position);
        holder.childTitle.setText(child.title);
        setupChildImg(holder.childIcon,child.title);
    }

    private void setupChildImg(ImageView childIcon, String title) {
        switch (title){
            case "Check In/Out":
                childIcon.setImageResource(R.drawable.checkinout);
                break;
            case "User Route":
                childIcon.setImageResource(R.drawable.route);
                break;
            case "Shift":
                childIcon.setImageResource(R.drawable.shift);
                break;
            case "Grade":
                childIcon.setImageResource(R.drawable.grade);
                break;
            case "Item Category":
                childIcon.setImageResource(R.drawable.categories);
                break;
            case "Item Group":
                childIcon.setImageResource(R.drawable.group);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

    public class ChildMenuViewHolder extends RecyclerView.ViewHolder {
        TextView childTitle;
        ImageView childIcon;
        public ChildMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            childTitle = itemView.findViewById(R.id.subMenuTitle);
            childIcon=itemView.findViewById(R.id.childImg);
        }
    }
}
