package com.pacific.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacific.R;
import com.pacific.model.TeacheSchoolModel;
import com.pacific.ui.student.activity.SchoolDetailsActivity;

import java.util.ArrayList;


public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.MyViewHolder> {
    ArrayList<TeacheSchoolModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_school_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvSchoolAddress.setText(itemList.get(i).getSchoolAddress());
        myViewHolder.tvSchoolName.setText(itemList.get(i).getSchoolName());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SchoolDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("schoolID",itemList.get(i).getSchoolID());
                context.startActivity(intent);
            }
        });

        if (!itemList.get(i).getSchoolLog().equals("")){
            byte[] decodedString = Base64.decode(itemList.get(i).getSchoolLog(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            myViewHolder.imgLogo.setImageBitmap(decodedByte);
        }


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvSchoolName,tvSchoolAddress;
        ImageView imgLogo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSchoolAddress=(TextView)itemView.findViewById(R.id.tvSchoolAddress);
            tvSchoolName=(TextView)itemView.findViewById(R.id.tvSchoolName);
            imgLogo=(ImageView) itemView.findViewById(R.id.imgLogo);

        }
    }

    public SchoolAdapter(ArrayList<TeacheSchoolModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
