package com.opensissasstudent.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.DashboardAssignmentModel;

import java.util.ArrayList;


public class DashboardAssignmentAdapter extends RecyclerView.Adapter<DashboardAssignmentAdapter.MyViewHolder> {
    ArrayList<DashboardAssignmentModel> itemList = new ArrayList<>();




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.assignment_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {




        myViewHolder.setIsRecyclable(false);
        myViewHolder.tvDueDate.setText(itemList.get(i).getDueDate());
        myViewHolder.tvAssigmentTitle.setText(itemList.get(i).getAssignmentTitle());
        myViewHolder.tvAssismentDetails.setText(itemList.get(i).getAssignmentSectionTitle()+" - "+itemList.get(i).getAssignmentTypeTitle());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


       TextView tvDueDate,tvAssigmentTitle,tvAssismentDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDueDate=(TextView)itemView.findViewById(R.id.tvDueDate);
            tvAssigmentTitle=(TextView)itemView.findViewById(R.id.tvAssigmentTitle);
            tvAssismentDetails=(TextView)itemView.findViewById(R.id.tvAssismentDetails);


        }
    }

    public DashboardAssignmentAdapter(ArrayList<DashboardAssignmentModel> itemList) {
        this.itemList = itemList;
    }
}
