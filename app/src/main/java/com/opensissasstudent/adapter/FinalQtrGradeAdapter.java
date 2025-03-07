package com.opensissasstudent.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.FinalGradeQtrModel;

import java.util.ArrayList;


public class FinalQtrGradeAdapter extends RecyclerView.Adapter<FinalQtrGradeAdapter.MyViewHolder> {
    ArrayList<FinalGradeQtrModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.final_grade_qtr_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

      myViewHolder.tvGradeName.setText(itemList.get(i).getGradeName());
        myViewHolder.tvGradeTotal.setText(itemList.get(i).getGradeName());
        myViewHolder.tvGradeExam.setText(itemList.get(i).getGradeName()+" Exam");


        myViewHolder.etGradeTotal.setText(itemList.get(i).getGradeTotalMarks());
        myViewHolder.etGradeExam.setText(itemList.get(i).getGradeMarks());




        myViewHolder.etGradeTotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (!myViewHolder.etGradeTotal.getText().toString().isEmpty()){
                        itemList.get(i).setGradeTotalMarks(myViewHolder.etGradeTotal.getText().toString().trim());
                        itemList.get(i).setGradeMarks(myViewHolder.etGradeTotal.getText().toString().trim());
                    }


                } catch (IndexOutOfBoundsException e){

                }

            }
        });


        myViewHolder.etGradeTotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (!myViewHolder.etGradeTotal.getText().toString().isEmpty()){
                        itemList.get(i).setGradeTotalMarks(myViewHolder.etGradeTotal.getText().toString().trim());
                    }


                } catch (IndexOutOfBoundsException e){

                }

            }
        });


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
