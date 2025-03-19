package com.example.bottomandnav.fragments.menus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomandnav.R;

import java.util.HashMap;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_PARENT = 0;
    private static final int TYPE_CHILD = 1;

    private Context context;
    private List<String> menuHeaders;
    private HashMap<String, List<String>> menuChildren;
    List<String> currentMenuItems;

    public MenuAdapter(Context context, List<String> menuHeaders, HashMap<String, List<String>> menuChildren) {
        this.context = context;
        this.menuHeaders = menuHeaders;
        this.menuChildren = menuChildren;
        this.currentMenuItems = menuHeaders;
    }

    @Override
    public int getItemCount() {
        return currentMenuItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return currentMenuItems == menuHeaders ? TYPE_PARENT : TYPE_CHILD;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PARENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.parent_item_layout, parent, false);
            return new ParentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_menu_child, parent, false);
            return new ChildViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String item = currentMenuItems.get(position);

        if (holder instanceof ParentViewHolder) {
            ((ParentViewHolder) holder).bind(item);
        } else if (holder instanceof ChildViewHolder) {
            ((ChildViewHolder) holder).bind(item);
        }
    }

    public void showChildItems(String parent) {
        List<String> children = menuChildren.get(parent);
        if (children != null) {
            currentMenuItems = children;
            notifyDataSetChanged();
        }
    }

    public void showParentItems() {
        currentMenuItems = menuHeaders;
        notifyDataSetChanged();
    }


    class ParentViewHolder extends RecyclerView.ViewHolder {
        TextView parentTitle;
        ImageView menuIcon;

        public ParentViewHolder(View itemView) {
            super(itemView);
            parentTitle = itemView.findViewById(R.id.menuTitle);
            menuIcon = itemView.findViewById(R.id.parentImg);
        }

        public void bind(String title) {
            parentTitle.setText(title);
            setParentIcon(title,menuIcon);

            itemView.setOnClickListener(v -> {

                if (menuChildren.containsKey(title)) {
                    showChildItems(title);
                }
            });
        }
    }

    private void setParentIcon(String title, ImageView menuIcon) {
        switch (title) {
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


    class ChildViewHolder extends RecyclerView.ViewHolder {
        TextView childTitle;
        ImageView childIcon;

        public ChildViewHolder(View itemView) {
            super(itemView);
            childTitle = itemView.findViewById(R.id.subMenuTitle);
            childIcon = itemView.findViewById(R.id.childImg);
        }

        public void bind(String title) {
            childTitle.setText(title);
           setChildIcons(title,childIcon);

            itemView.setOnClickListener(v -> {
                Toast.makeText(context, "Clicked on: " + title, Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void setChildIcons(String title, ImageView childIcon) {
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
}
