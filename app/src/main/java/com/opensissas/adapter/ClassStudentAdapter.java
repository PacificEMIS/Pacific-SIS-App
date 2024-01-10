package com.opensissas.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissas.R;
import com.opensissas.model.ClassStudentModel;

import java.util.ArrayList;


public class ClassStudentAdapter extends RecyclerView.Adapter<ClassStudentAdapter.MyViewHolder> {
    ArrayList<ClassStudentModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.class_student_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvStudentName.setText(itemList.get(i).getStudentName());
        myViewHolder.tvID.setText("ID :"+itemList.get(i).getStudentID());
        myViewHolder.tvAltID.setText("Alt ID :"+itemList.get(i).getStudentAltID());
        myViewHolder.tvGrade.setText(itemList.get(i).getGrade());
        myViewHolder.tvSection.setText(itemList.get(i).getSection());

        if (!itemList.get(i).getStudentPhoto().equals("")) {
            byte[] decodedString = Base64.decode(itemList.get(i).getStudentPhoto(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            myViewHolder.imgUser.setImageBitmap(decodedByte);
        }else {
            myViewHolder.imgUser.setImageDrawable(context.getResources().getDrawable(R.drawable.student_user));
        }

       myViewHolder.imgMail.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_SENDTO);
               intent.putExtra(Intent.EXTRA_EMAIL, new String[]{itemList.get(i).getEmail()});
               intent.setData(Uri.parse("mailto:"));
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(intent);

           }
       });

       myViewHolder.imgPhn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_DIAL);
               intent.setData(Uri.parse("tel:"+itemList.get(i).getMob()));
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
        TextView tvStudentName,tvID,tvAltID,tvGrade,tvSection;
        ImageView imgUser,imgMail,imgPhn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSection=(TextView)itemView.findViewById(R.id.tvSection);
            tvGrade=(TextView)itemView.findViewById(R.id.tvGrade);
            tvAltID=(TextView)itemView.findViewById(R.id.tvAltID);
            tvID=(TextView)itemView.findViewById(R.id.tvID);
            tvStudentName=(TextView)itemView.findViewById(R.id.tvStudentName);
            imgUser=(ImageView)itemView.findViewById(R.id.imgUser);
            imgMail=(ImageView)itemView.findViewById(R.id.imgMail);
            imgPhn=(ImageView)itemView.findViewById(R.id.imgPhn);

        }
    }

    public ClassStudentAdapter(ArrayList<ClassStudentModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
