package com.opensis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensis.R;
import com.opensis.model.FYModel;
import com.opensis.model.FinalGradeSemModel;

import java.util.ArrayList;


public class FYSemAdapter extends RecyclerView.Adapter<FYSemAdapter.MyViewHolder> {
    ArrayList<FYModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.final_grade_fy_row, viewGroup, false);
        return new FYSemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

       myViewHolder.tvGradeName.setText(itemList.get(i).getSemName());
       myViewHolder.etGrade.setText(itemList.get(i).getSemMarks());







    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText etGrade;
        TextView tvGradeName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvGradeName=(TextView)itemView.findViewById(R.id.tvGradeName);
            etGrade=(EditText)itemView.findViewById(R.id.etGrade);

        }
    }

    public FYSemAdapter(ArrayList<FYModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;

    }






}
