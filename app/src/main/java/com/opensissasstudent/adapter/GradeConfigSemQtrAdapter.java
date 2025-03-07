package com.opensissasstudent.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.GradeBokConfigSemQtrModel;

import java.util.ArrayList;


public class GradeConfigSemQtrAdapter extends RecyclerView.Adapter<GradeConfigSemQtrAdapter.MyViewHolder> {
    ArrayList<GradeBokConfigSemQtrModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gradebook_cong_sem_qtr_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvGradeQ1.setText(itemList.get(i).getTitle());
        myViewHolder.etGradeQ1.setText(itemList.get(i).getQtrMarks());

        myViewHolder.etGradeQ1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (!myViewHolder.etGradeQ1.getText().toString().isEmpty()){
                        itemList.get(i).setQtrMarks(myViewHolder.etGradeQ1.getText().toString().trim());
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
        TextView tvGradeQ1;
        EditText etGradeQ1;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGradeQ1=(TextView)itemView.findViewById(R.id.tvGradeQ1);
            etGradeQ1=(EditText) itemView.findViewById(R.id.etGradeQ1);


        }
    }

    public GradeConfigSemQtrAdapter(ArrayList<GradeBokConfigSemQtrModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }


}
