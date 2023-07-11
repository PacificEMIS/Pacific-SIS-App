package com.opensis.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.opensis.R;
import com.opensis.model.AttendanceCodeModel;
import com.opensis.model.AttendanceStudentModel;
import com.opensis.ui.teacher.activity.MissingClassAttendanceActivity;
import com.opensis.ui.teacher.fragment.ClassAttendanceFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MissingAttendanceStudentAdapter extends RecyclerView.Adapter<MissingAttendanceStudentAdapter.MyViewHolder> {
    ArrayList<AttendanceStudentModel> itemList = new ArrayList<>();
    Context context;
    ArrayList<AttendanceCodeModel> codeList = new ArrayList<>();
    AlertDialog alerDialog1;
    int use;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attendance_student_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvStudentName.setText(itemList.get(i).getStudentFrstName()+" "+itemList.get(i).getStudentLstName());
        myViewHolder.tvID.setText("ID : "+itemList.get(i).getStudentID());
        myViewHolder.tvAltID.setText("Alt ID : "+itemList.get(i).getAltId());
        myViewHolder.tvSection.setText("Section : "+itemList.get(i).getSection());

        myViewHolder.tvGrade.setText( itemList.get(i).getGrade());

        myViewHolder.tvAttenDance.setText(itemList.get(i).getAttendanceName());
        if (itemList.get(i).getAttendanceName().equalsIgnoreCase("P")){
            myViewHolder.lnAttendanceType.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.green));

        }else if (itemList.get(i).getAttendanceName().equalsIgnoreCase("A")){
            myViewHolder.lnAttendanceType.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.missing_attendance));

        }else if (itemList.get(i).getAttendanceName().equalsIgnoreCase("AB")){
            myViewHolder.lnAttendanceType.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.missing_attendance));

        }else if (itemList.get(i).getAttendanceName().equalsIgnoreCase("T")){
            myViewHolder.lnAttendanceType.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.color3));

        }else if (itemList.get(i).getAttendanceName().equalsIgnoreCase("EA")){
            myViewHolder.lnAttendanceType.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.color1));

        }else if (itemList.get(i).getAttendanceName().equalsIgnoreCase("L")){
            myViewHolder.lnAttendanceType.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.orangecolor));

        }else if (itemList.get(i).getAttendanceName().equalsIgnoreCase("HF")){
            myViewHolder.lnAttendanceType.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.orangecolor));

        }else {
            myViewHolder.lnAttendanceType.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.green));

        }

        myViewHolder.lnAttendanceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MissingClassAttendanceActivity)context).codePopUp(i);
            }
        });

        myViewHolder.lnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MissingClassAttendanceActivity)context).commentPopUp(i,itemList.get(i).getStudentFrstName()+" "+itemList.get(i).getStudentLstName(),itemList.get(i).getComments());
            }
        });

        if (itemList.get(i).getComments().equals("")){
            myViewHolder.imgComment.setImageTintList(ContextCompat.getColorStateList(context, R.color.grey));;
        }else {
            myViewHolder.imgComment.setImageTintList(ContextCompat.getColorStateList(context, R.color.greencolor));;
        }
        if (!itemList.get(i).getStudentPhoto().equals("")) {
            byte[] decodedString = Base64.decode(itemList.get(i).getStudentPhoto(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            myViewHolder.profileImage.setImageBitmap(decodedByte);
        }

        myViewHolder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       LinearLayout lnAttendanceType,lnComment;
       ImageView imgComment;
       TextView tvStudentName,tvID,tvAltID,tvGrade,tvSection,tvAttenDance;
       CircleImageView profileImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSection=(TextView)itemView.findViewById(R.id.tvSection);
            tvGrade=(TextView)itemView.findViewById(R.id.tvGrade);
            tvID=(TextView)itemView.findViewById(R.id.tvID);
            tvStudentName=(TextView)itemView.findViewById(R.id.tvStudentName);
            tvAltID=(TextView)itemView.findViewById(R.id.tvAltID);
            tvAttenDance=(TextView)itemView.findViewById(R.id.tvAttenDance);
            lnAttendanceType=(LinearLayout)itemView.findViewById(R.id.lnAttendanceType);
            lnComment=(LinearLayout)itemView.findViewById(R.id.lnComment);
            profileImage=(CircleImageView)itemView.findViewById(R.id.profileImage);

            imgComment=(ImageView)itemView.findViewById(R.id.imgComment);


        }
    }

    public MissingAttendanceStudentAdapter(ArrayList<AttendanceStudentModel> itemList, Context context,  int use) {
        this.itemList = itemList;
        this.context = context;
        this.use=use;

    }


}
