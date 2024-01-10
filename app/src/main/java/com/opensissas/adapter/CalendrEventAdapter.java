package com.opensissas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissas.R;

import java.util.ArrayList;


public class CalendrEventAdapter extends RecyclerView.Adapter<CalendrEventAdapter.MyViewHolder> {
    ArrayList<String> itemList = new ArrayList<>();


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_event_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
       myViewHolder.tvEvent.setText(itemList.get(i));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView tvEvent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEvent=(TextView)itemView.findViewById(R.id.tvEvent);


        }
    }

    public CalendrEventAdapter(ArrayList<String> itemList) {
        this.itemList = itemList;
    }
}
