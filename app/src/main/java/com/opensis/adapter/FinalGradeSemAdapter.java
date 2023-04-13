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
import com.opensis.model.FinalGradeQtrModel;
import com.opensis.model.FinalGradeSemModel;

import java.util.ArrayList;


public class FinalGradeSemAdapter extends RecyclerView.Adapter<FinalGradeSemAdapter.MyViewHolder> {
    ArrayList<FinalGradeSemModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.final_grade_sem_row, viewGroup, false);
        return new FinalGradeSemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

      myViewHolder.tvGradeName.setText(itemList.get(i).getSemName());
        myViewHolder.tvGradeTotal.setText(itemList.get(i).getSemName()+" Exam");
        myViewHolder.tvGradeQ1.setText(itemList.get(i).getQtrName());


        myViewHolder.etGradeTotal.setText(itemList.get(i).getSemMarks());
        myViewHolder.etGradeQ1.setText(itemList.get(i).getQtrMarks());









    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText etGradeQ1,etGradeQ2,etGradeTotal;
        TextView tvGradeName,tvGradeQ1,tvGradeQ2,tvGradeTotal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            etGradeQ2=(EditText)itemView.findViewById(R.id.etGradeQ2);
            etGradeQ1=(EditText)itemView.findViewById(R.id.etGradeQ1);
            etGradeTotal=(EditText)itemView.findViewById(R.id.etGradeTotal);

            tvGradeQ2=(TextView)itemView.findViewById(R.id.tvGradeQ2);
            tvGradeName=(TextView)itemView.findViewById(R.id.tvGradeName);
            tvGradeQ1=(TextView)itemView.findViewById(R.id.tvGradeQ1);
            tvGradeTotal=(TextView)itemView.findViewById(R.id.tvGradeTotal);



        }
    }

    public FinalGradeSemAdapter(ArrayList<FinalGradeSemModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;

    }






}
