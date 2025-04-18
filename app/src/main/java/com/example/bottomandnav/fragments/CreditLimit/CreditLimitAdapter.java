package com.example.bottomandnav.fragments.CreditLimit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomandnav.R;
import com.example.bottomandnav.models.CreditAllDataModal;

import java.util.List;

public class CreditLimitAdapter extends RecyclerView.Adapter<CreditLimitAdapter.MyViewHolder> {
private List<CreditAllDataModal> creditAllDataModals;

    public CreditLimitAdapter(List<CreditAllDataModal> creditAllDataModals) {
        this.creditAllDataModals = creditAllDataModals;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CreditAllDataModal creditItem= creditAllDataModals.get(position);

        holder.creditRequestNo.setText(creditItem.credit_req_no);
        holder.seasonCode.setText(creditItem.seasion_code);
        holder.customerCode.setText(creditItem.customer_code);
        holder.createdOn.setText(creditItem.created_on);

        holder.itemView.setOnClickListener(v -> {
            // Do something like open details screen
            Toast.makeText(v.getContext(), "Clicked: " + creditItem.credit_req_no, Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return creditAllDataModals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView creditRequestNo,seasonCode,customerCode,createdOn;

        public MyViewHolder(@NonNull View view) {
            super(view);
            creditRequestNo=view.findViewById(R.id.creditRequestNo);
            seasonCode=view.findViewById(R.id.seasonCode);
            customerCode=view.findViewById(R.id.customerCode);
            createdOn=view.findViewById(R.id.createdOn);
        }
    }
}
