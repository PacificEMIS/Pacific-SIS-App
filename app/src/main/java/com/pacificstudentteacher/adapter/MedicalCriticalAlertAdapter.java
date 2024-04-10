package com.pacificstudentteacher.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificstudentteacher.R;
import com.pacificstudentteacher.model.MedicalAlertModel;

import java.util.ArrayList;


public class MedicalCriticalAlertAdapter extends RecyclerView.Adapter<MedicalCriticalAlertAdapter.MyViewHolder> {
    ArrayList<MedicalAlertModel> itemList = new ArrayList<>();



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medical_alert_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {


        myViewHolder.tvCriticalAlert.setText(itemList.get(i).getAlertType());
        myViewHolder.tvAlertDesc.setText(itemList.get(i).getAlertDescription());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCriticalAlert,tvAlertDesc;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCriticalAlert=(TextView)itemView.findViewById(R.id.tvCriticalAlert);
            tvAlertDesc=(TextView)itemView.findViewById(R.id.tvAlertDesc);

        }
    }

    public MedicalCriticalAlertAdapter(ArrayList<MedicalAlertModel> itemList) {
        this.itemList = itemList;
    }
}
