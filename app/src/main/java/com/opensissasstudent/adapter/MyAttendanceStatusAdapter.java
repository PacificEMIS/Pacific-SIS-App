package com.opensissasstudent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.CourseVeriableScheduleModel;
import com.opensissasstudent.model.MyAttendanceStatusModel;

import java.util.ArrayList;


public class MyAttendanceStatusAdapter extends RecyclerView.Adapter<MyAttendanceStatusAdapter.MyViewHolder> {
    ArrayList<MyAttendanceStatusModel> itemList = new ArrayList<>();
    Context context;

    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_attendance_status_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvDate.setText(itemList.get(i).getDate());


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                myViewHolder
                        .rvStatus
                        .getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        layoutManager
                .setInitialPrefetchItemCount(
                        itemList.get(i)
                                .getStatusDetailsModelArrayList()
                                .size());

        MyAttendanceStatusDetailsAdapter childItemAdapter
                = new MyAttendanceStatusDetailsAdapter(
                itemList.get(i)
                        .getStatusDetailsModelArrayList());
        myViewHolder
                .rvStatus
                .setLayoutManager(layoutManager);
        myViewHolder
                .rvStatus
                .setAdapter(childItemAdapter);
        myViewHolder
                .rvStatus
                .setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


       TextView tvDate;
       RecyclerView rvStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            rvStatus=(RecyclerView) itemView.findViewById(R.id.rvStatus);






        }
    }

    public MyAttendanceStatusAdapter(ArrayList<MyAttendanceStatusModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
