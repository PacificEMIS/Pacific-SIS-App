package com.opensissasstudent.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.MedicalAlertModel;
import com.opensissasstudent.model.MedicalNoteModel;
import com.opensissasstudent.others.utility.Util;

import java.util.ArrayList;


public class MedicalNoteAdapter extends RecyclerView.Adapter<MedicalNoteAdapter.MyViewHolder> {
    ArrayList<MedicalNoteModel> itemList = new ArrayList<>();



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medical_note_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {


        myViewHolder.tvMedicalNoteDate.setText(Util.changeAnyDateFormat(itemList.get(i).getNoteDate(),"yyyy-MM-dd","MMM dd,yyyy"));
        myViewHolder.tvMedicalNoteDesc.setText(itemList.get(i).getMedicalNote());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvMedicalNoteDate,tvMedicalNoteDesc;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMedicalNoteDesc=(TextView)itemView.findViewById(R.id.tvMedicalNoteDesc);
            tvMedicalNoteDate=(TextView)itemView.findViewById(R.id.tvMedicalNoteDate);

        }
    }

    public MedicalNoteAdapter(ArrayList<MedicalNoteModel> itemList) {
        this.itemList = itemList;
    }
}
