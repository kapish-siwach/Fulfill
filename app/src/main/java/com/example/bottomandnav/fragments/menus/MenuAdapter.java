package com.example.bottomandnav.fragments.menus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomandnav.R;
import com.example.bottomandnav.models.ResponseModel;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ParentViewHolder> {
    Context context;
    List<ResponseModel.Menu> menuList;
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(int position, ResponseModel.Menu item);
    }

    public MenuAdapter(Context context, List<ResponseModel.Menu> menuList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.menuList = menuList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parent_item_layout, parent, false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder holder, int position) {
        ResponseModel.Menu item = menuList.get(position);
        holder.parentTitle.setText(item.title);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position, item);
            }
        });
        setupImages(item.title,holder.menuIcon);
    }

    private void setupImages(String title, ImageView menuIcon) {
        switch (title){
            case "Order":
                menuIcon.setImageResource(R.drawable.order);
                break;
            case "Master":
                menuIcon.setImageResource(R.drawable.master);
                break;
            case "TA/DA":
                menuIcon.setImageResource(R.drawable.tada);
                break;
            case "Item Master":
                menuIcon.setImageResource(R.drawable.itemmaster);
                break;
            case "User":
                menuIcon.setImageResource(R.drawable.user);
                break;
            case "Reports":
                menuIcon.setImageResource(R.drawable.reports);
                break;
            case "M & S":
                menuIcon.setImageResource(R.drawable.mands);
                break;
            case "Planting":
                menuIcon.setImageResource(R.drawable.planting);
                break;
            default:
                menuIcon.setImageResource(R.drawable.baseline_broken_image_24);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    class ParentViewHolder extends RecyclerView.ViewHolder {
        TextView parentTitle;
        ImageView menuIcon;

        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);
            parentTitle = itemView.findViewById(R.id.menuTitle);
            menuIcon = itemView.findViewById(R.id.parentImg);
        }
    }
}
