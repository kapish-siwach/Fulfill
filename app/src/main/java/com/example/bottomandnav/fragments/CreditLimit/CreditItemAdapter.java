package com.example.bottomandnav.fragments.CreditLimit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomandnav.R;
import com.example.bottomandnav.models.CreditHeaderInsertModal;

import java.util.ArrayList;
import java.util.List;

public class CreditItemAdapter extends RecyclerView.Adapter<CreditItemAdapter.viewHolder> {
private List<CreditHeaderInsertModal.Line> lineList=new ArrayList<>();
private Context context;

    public CreditItemAdapter(List<CreditHeaderInsertModal.Line> lineList, Context context) {
        this.lineList = (lineList != null) ? lineList : new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.line_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
       // CreditHeaderInsertModal item=lineList.get(position);
        holder.expectedAmount.setText(String.valueOf(lineList.get(position).expected_amt));
        holder.expectedDate.setText(lineList.get(position).expected_collection_date);
        holder.groupCode.setText(lineList.get(position).group_code);
        holder.categoryCode.setText(lineList.get(position).category_code);

    }

    @Override
    public int getItemCount() {
        return lineList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView categoryCode,groupCode,expectedAmount,expectedDate;
        ImageView dltBtn;
        public viewHolder(@NonNull View view) {
            super(view);
            categoryCode=view.findViewById(R.id.categoryCode);
            groupCode=view.findViewById(R.id.groupCode);
            expectedAmount=view.findViewById(R.id.expectedAmountView);
            expectedDate=view.findViewById(R.id.expectedDate);
            dltBtn=view.findViewById(R.id.dltLineBtn);
        }
    }
}
