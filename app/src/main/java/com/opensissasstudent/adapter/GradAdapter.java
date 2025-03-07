package com.opensissasstudent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.SpinnerModelForInput;

import java.util.ArrayList;

public class GradAdapter extends RecyclerView.Adapter<GradAdapter.MyViewHolder>{

    Context context;
    ArrayList<SpinnerModelForInput> rangeArray;
    InputGradeStudentAdapter.setOnGradSelect mSelectedGard;
    public GradAdapter(Context context, ArrayList<SpinnerModelForInput> rangeArray) {
        this.context = context;
        this.rangeArray = rangeArray;
    }

    public void setSelectedGard(InputGradeStudentAdapter.setOnGradSelect mSelectedGard) {
        this.mSelectedGard = mSelectedGard;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.grad_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (position == 0){
            holder.tvGrad.setText(rangeArray.get(position).getItemName());
        } else {
            holder.tvGrad.setText(rangeArray.get(position).getItemName());
        }

        holder.tvGrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedGard.onClick(position,rangeArray.get(position).getItemName(),rangeArray.get(position).getBreakoff());
            }
        });
    }

    @Override
    public int getItemCount() {
        return rangeArray.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvGrad;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGrad = itemView.findViewById(R.id.tvGrad);
        }
    }
}
