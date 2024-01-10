package com.opensissas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissas.R;
import com.opensissas.model.AssignmentDetailsModel;
import com.opensissas.others.utility.Util;
import com.opensissas.ui.teacher.activity.AssignmentDetailsActivity;

import java.util.ArrayList;


public class ClassAssignmentDetailsAdapter extends RecyclerView.Adapter<ClassAssignmentDetailsAdapter.MyViewHolder> {
    ArrayList<AssignmentDetailsModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.assignment_details_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvAssignmentdetailsName.setText(itemList.get(i).getAssignmentdetailsName());
        myViewHolder.tvPoints.setText("Points :"+itemList.get(i).getPoints());
        myViewHolder.tvAssignedDate.setText(Util.changeAnyDateFormat(itemList.get(i).getAssignedDate(),"yyyy-MM-dd","MMM dd,yyyy"));
        myViewHolder.tvDueDate.setText(Util.changeAnyDateFormat(itemList.get(i).getDueDate(),"yyyy-MM-dd","MMM dd,yyyy"));
        myViewHolder.lnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AssignmentDetailsActivity)context).editWorkoutPopup(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAssignmentdetailsName,tvPoints,tvAssignedDate,tvDueDate;
        LinearLayout lnMore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDueDate=(TextView)itemView.findViewById(R.id.tvDueDate);
            tvAssignedDate=(TextView)itemView.findViewById(R.id.tvAssignedDate);
            tvPoints=(TextView)itemView.findViewById(R.id.tvPoints);
            tvAssignmentdetailsName=(TextView)itemView.findViewById(R.id.tvAssignmentdetailsName);
            lnMore=(LinearLayout)itemView.findViewById(R.id.lnMore);



        }
    }

    public ClassAssignmentDetailsAdapter(ArrayList<AssignmentDetailsModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
