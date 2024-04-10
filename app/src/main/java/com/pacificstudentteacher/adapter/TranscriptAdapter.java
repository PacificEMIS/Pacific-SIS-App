package com.pacificstudentteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificstudentteacher.R;
import com.pacificstudentteacher.model.TranscriptModel;

import java.util.ArrayList;


public class TranscriptAdapter extends RecyclerView.Adapter<TranscriptAdapter.MyViewHolder> {
    ArrayList<TranscriptModel> itemList = new ArrayList<>();
    Context context;

    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transcript_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvGarde.setText(itemList.get(i).getGradeLevelTitle());
        myViewHolder.tvSchool.setText("( "+itemList.get(i).getSchoolName()+" )");
        myViewHolder.tvSchoolYear.setText("School Year: "+itemList.get(i).getSchoolYear());



        myViewHolder.setIsRecyclable(false);


            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(
                    myViewHolder
                            .rvmarkingPeriod
                            .getContext(),
                    LinearLayoutManager.VERTICAL,
                    false);

            layoutManager
                    .setInitialPrefetchItemCount(
                            itemList.get(i)
                                    .getPeriodList()
                                    .size());

            TranscriptPeriodAdapter childItemAdapter
                    = new TranscriptPeriodAdapter(
                    itemList.get(i)
                            .getPeriodList(),context);
            myViewHolder
                    .rvmarkingPeriod
                    .setLayoutManager(layoutManager);
            myViewHolder
                    .rvmarkingPeriod
                    .setAdapter(childItemAdapter);
            myViewHolder
                    .rvmarkingPeriod
                    .setRecycledViewPool(viewPool);



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


       TextView tvGarde,tvSchool,tvSchoolYear;
       RecyclerView rvmarkingPeriod;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGarde=(TextView)itemView.findViewById(R.id.tvGarde);
            tvSchool=(TextView)itemView.findViewById(R.id.tvSchool);
            tvSchoolYear=(TextView)itemView.findViewById(R.id.tvSchoolYear);


            rvmarkingPeriod=(RecyclerView) itemView.findViewById(R.id.rvmarkingPeriod);



        }
    }

    public TranscriptAdapter(ArrayList<TranscriptModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
