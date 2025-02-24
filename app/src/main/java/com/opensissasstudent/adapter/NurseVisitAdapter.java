package com.opensissasstudent.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.MedicalNoteModel;
import com.opensissasstudent.model.MedicalNourseVisitModel;
import com.opensissasstudent.others.utility.Util;

import java.util.ArrayList;


public class NurseVisitAdapter extends RecyclerView.Adapter<NurseVisitAdapter.MyViewHolder> {
    ArrayList<MedicalNourseVisitModel> itemList = new ArrayList<>();



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medical_nurse_visit_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        if (itemList.get(i).getTimeOut().equals("")){
            myViewHolder.tvVisitDate.setText(Util.changeAnyDateFormat(itemList.get(i).getNurseVisitDate(),"yyyy-MM-dd","MMM dd, yyyy") +" In "+Util.changeAnyDateFormat(itemList.get(i).getTimeIn(),"yyyy-MM-ddhh:mm:ss","hh:mm"));

        }else {
            myViewHolder.tvVisitDate.setText(Util.changeAnyDateFormat(itemList.get(i).getNurseVisitDate(),"yyyy-MM-dd","MMM dd, yyyy") +" In "+Util.changeAnyDateFormat(itemList.get(i).getTimeIn(),"yyyy-MM-ddhh:mm:ss","hh:mm")+" Out "+Util.changeAnyDateFormat(itemList.get(i).getTimeOut(),"yyyy-MM-ddhh:mm:ss","hh:mm"));

        }


        myViewHolder.tvVisitReason.setText(itemList.get(i).getReason());
        myViewHolder.tvResult.setText(itemList.get(i).getResult());
        myViewHolder.tvComment.setText(itemList.get(i).getComment());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvVisitReason,tvVisitDate,tvResult,tvComment;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVisitReason=(TextView)itemView.findViewById(R.id.tvVisitReason);
            tvVisitDate=(TextView)itemView.findViewById(R.id.tvVisitDate);
            tvResult=(TextView)itemView.findViewById(R.id.tvResult);
            tvComment=(TextView)itemView.findViewById(R.id.tvComment);

        }
    }

    public NurseVisitAdapter(ArrayList<MedicalNourseVisitModel> itemList) {
        this.itemList = itemList;
    }
}
