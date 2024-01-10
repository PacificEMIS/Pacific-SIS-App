package com.opensissas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissas.R;
import com.opensissas.model.FinalGradeQtrModel;

import java.util.ArrayList;


public class FinalQtrGradeAdapter extends RecyclerView.Adapter<FinalQtrGradeAdapter.MyViewHolder> {
    ArrayList<FinalGradeQtrModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.final_grade_qtr_row, viewGroup, false);
        return new FinalQtrGradeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

      myViewHolder.tvGradeName.setText(itemList.get(i).getGradeName());
        myViewHolder.tvGradeTotal.setText(itemList.get(i).getGradeName());
        myViewHolder.tvGradeExam.setText(itemList.get(i).getGradeName()+" Exam");


        myViewHolder.etGradeTotal.setText(itemList.get(i).getGradeTotalMarks());
        myViewHolder.etGradeExam.setText(itemList.get(i).getGradeMarks());

        if (itemList.get(i).isDoesExam()){
            myViewHolder.lnQtrExam.setVisibility(View.VISIBLE);
        }else {
            myViewHolder.lnQtrExam.setVisibility(View.GONE);
        }


        if (itemList.get(i).isDoesGrades()){
            myViewHolder.lnQtrGrade.setVisibility(View.VISIBLE);
        }else {
            myViewHolder.lnQtrGrade.setVisibility(View.GONE);
        }





    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText etGradeTotal,etGradeExam;
        TextView tvGradeName,tvGradeTotal,tvGradeExam,tvQtrHundred;
        LinearLayout lnQtrGrade,lnQtrExam;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            etGradeExam=(EditText)itemView.findViewById(R.id.etGradeExam);
            etGradeTotal=(EditText)itemView.findViewById(R.id.etGradeTotal);

            tvGradeExam=(TextView)itemView.findViewById(R.id.tvGradeExam);
            tvGradeName=(TextView)itemView.findViewById(R.id.tvGradeName);
            tvGradeTotal=(TextView)itemView.findViewById(R.id.tvGradeTotal);
            tvQtrHundred=(TextView) itemView.findViewById(R.id.tvQtrHundred);

            lnQtrExam=(LinearLayout) itemView.findViewById(R.id.lnQtrExam);
            lnQtrGrade=(LinearLayout) itemView.findViewById(R.id.lnQtrGrade);


        }
    }

    public FinalQtrGradeAdapter(ArrayList<FinalGradeQtrModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;

    }

    private void removeText(){

    }






}
