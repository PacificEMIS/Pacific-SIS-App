package com.opensissasstudent.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.MedicalNoteModel;
import com.opensissasstudent.model.MedicalPhysicalImmunizationModel;
import com.opensissasstudent.others.utility.Util;

import java.util.ArrayList;


public class PhyiscalRecordAdapter extends RecyclerView.Adapter<PhyiscalRecordAdapter.MyViewHolder> {
    ArrayList<MedicalPhysicalImmunizationModel> itemList = new ArrayList<>();



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medical_physical_record_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {


        myViewHolder.tvRecordDate.setText(Util.changeAnyDateFormat(itemList.get(i).getImmunizationDate(),"yyyy-MM-dd","MMM dd,yyyy"));
        myViewHolder.tvReordType.setText(itemList.get(i).getImmunizationType());
        myViewHolder.tvRecordDesc.setText(itemList.get(i).getComment());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvReordType,tvRecordDate,tvRecordDesc;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecordDesc=(TextView)itemView.findViewById(R.id.tvRecordDesc);
            tvRecordDate=(TextView)itemView.findViewById(R.id.tvRecordDate);
            tvReordType=(TextView)itemView.findViewById(R.id.tvReordType);

        }
    }

    public PhyiscalRecordAdapter(ArrayList<MedicalPhysicalImmunizationModel> itemList) {
        this.itemList = itemList;
    }
}
