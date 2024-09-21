package com.opensissasstudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.CurseDetailsModel;
import com.opensissasstudent.model.Transcriptmarkingperiodmodel;
import com.opensissasstudent.ui.student.activity.TranscriptReportActivity;

import java.util.ArrayList;


public class TranscriptPeriodAdapter extends RecyclerView.Adapter<TranscriptPeriodAdapter.MyViewHolder> {
    ArrayList<Transcriptmarkingperiodmodel> itemList = new ArrayList<>();
    Context context;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transcript_period_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvMarkingPeriod.setText(itemList.get(i).getMarkingPeriodTitle());
        myViewHolder.tvCreditEarned.setText("Credit Earned: "+itemList.get(i).getCreditEarned());
        myViewHolder.tvCreditAttemted.setText("Credit Attemted: "+itemList.get(i).getCreditAttemted());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, TranscriptReportActivity.class);
                intent.putExtra("toolbar",itemList.get(i).getMarkingPeriodTitle());
                intent.putExtra("reportlist",itemList.get(i).getReprtCardArray());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });







    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


       TextView tvMarkingPeriod,tvCreditEarned,tvCreditAttemted;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMarkingPeriod=(TextView)itemView.findViewById(R.id.tvMarkingPeriod);
            tvCreditEarned=(TextView)itemView.findViewById(R.id.tvCreditEarned);
            tvCreditAttemted=(TextView)itemView.findViewById(R.id.tvCreditAttemted);






        }
    }

    public TranscriptPeriodAdapter(ArrayList<Transcriptmarkingperiodmodel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
