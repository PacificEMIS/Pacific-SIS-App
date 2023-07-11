package com.opensis.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.opensis.R;
import com.opensis.model.ClassStudentModel;
import com.opensis.model.InputGradeStudentModel;
import com.opensis.model.SpinnerModel;
import com.opensis.ui.teacher.activity.StudentOfGradeActivity;
import com.opensis.ui.teacher.fragment.ClassAttendanceFragment;
import com.opensis.ui.teacher.fragment.InputGradeFragment;

import java.util.ArrayList;


public class InputGradeStudentAdapter extends RecyclerView.Adapter<InputGradeStudentAdapter.MyViewHolder> {
    ArrayList<InputGradeStudentModel> itemList = new ArrayList<>();
    Context context;
    ArrayList<String>gradeList=new ArrayList<>();
    ArrayList<SpinnerModel>gradeModelList=new ArrayList<>();
    Fragment fContext;
    int letterFlag;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.input_grade_student_row, viewGroup, false);
        return new MyViewHolder(view,new InputGradeStudentAdapter.MyCustomEditTextListenerForNumber(), new InputGradeStudentAdapter.MycustomFocusForNumber());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvStudentName.setText(itemList.get(i).getStudentName());
        myViewHolder.tvGrade.setText(itemList.get(i).getGrade());

        //myViewHolder.etTeacherComment.setText(itemList.get(i).getTeacherComment());
        myViewHolder.lnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InputGradeFragment)fContext).commentPopUp(i,itemList.get(i).getStudentName(),itemList.get(i).getTeacherComment());
            }
        });

        if (itemList.get(i).getTeacherComment().equals("")){
            myViewHolder.imgComment.setImageTintList(ContextCompat.getColorStateList(context, R.color.grey));;
        }else {
            myViewHolder.imgComment.setImageTintList(ContextCompat.getColorStateList(context, R.color.greencolor));;
        }
        if (!itemList.get(i).getStudentPhoto().equals("")) {
            byte[] decodedString = Base64.decode(itemList.get(i).getStudentPhoto(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            myViewHolder.imgUser.setImageBitmap(decodedByte);
        }else {
            myViewHolder.imgUser.setImageDrawable(context.getResources().getDrawable(R.drawable.student_user));
        }

        if (letterFlag==1) {
            myViewHolder.lnPercent.setEnabled(false);
            myViewHolder.spLetter.setVisibility(View.VISIBLE);
            myViewHolder.tvObtainedGrade.setVisibility(View.GONE);
            myViewHolder.lnLetterGrade.setVisibility(View.VISIBLE);
            myViewHolder.etPercent.setText(itemList.get(i).getPercent()+"%");
        }else {
            myViewHolder.spLetter.setVisibility(View.GONE);
            myViewHolder.tvObtainedGrade.setVisibility(View.VISIBLE);
            myViewHolder.tvObtainedGrade.setEnabled(false);
            myViewHolder.lnLetterGrade.setVisibility(View.VISIBLE);
            myViewHolder.etPercent.setText(itemList.get(i).getPercent()+"");
            myViewHolder.lnPercent.setEnabled(true);
        }

        ArrayAdapter ad
                = new ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                gradeList);

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);


        myViewHolder.spLetter.setAdapter(ad);
       // myViewHolder.spLetter.setSelection(itemList.get(i).getPos1());



        if (itemList.get(i).getObtainedGrade().equals("0")){
            if (letterFlag==1){
                myViewHolder.spLetter.setVisibility(View.VISIBLE);
                myViewHolder.tvObtainedGrade.setVisibility(View.GONE);
            }else {
                myViewHolder.spLetter.setVisibility(View.GONE);
                myViewHolder.tvObtainedGrade.setVisibility(View.VISIBLE);
            }

        }else {
            if (letterFlag==1){
                myViewHolder.spLetter.setVisibility(View.GONE);
                myViewHolder.tvObtainedGrade.setVisibility(View.VISIBLE);
                myViewHolder.tvObtainedGrade.setText(itemList.get(i).getObtainedGrade());

            }else {
                myViewHolder.spLetter.setVisibility(View.GONE);
                myViewHolder.tvObtainedGrade.setVisibility(View.VISIBLE);
                myViewHolder.tvObtainedGrade.setText(itemList.get(i).getObtainedGrade());

            }


        }


        myViewHolder.spLetter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    ((InputGradeFragment)fContext).setPercentage(i, Integer.parseInt(gradeModelList.get(position).getItemID()),gradeList.get(position));
                    // itemList.get(i).setPos1(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        myViewHolder.tvObtainedGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewHolder.spLetter.setVisibility(View.VISIBLE);
                myViewHolder.tvObtainedGrade.setVisibility(View.GONE);
            }
        });

        myViewHolder.myCustomEditTextListenerForNumber.updatePosition(myViewHolder.getAdapterPosition());
        myViewHolder.cutomfocusForNumber.updatePosition(myViewHolder.getAdapterPosition());

       myViewHolder.lnPercent.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ((InputGradeFragment)fContext).addMarkpopup(i,itemList.get(i).getStudentName(),itemList.get(i).getPercent()+"%");

           }
       });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName,tvGrade,tvObtainedGrade;
        ImageView imgUser;
        TextView etPercent,etTeacherComment;
        Spinner spLetter;
        LinearLayout lnLetterGrade;
        ImageView imgComment;
        LinearLayout lnComment,lnPercent;

        InputGradeStudentAdapter.MyCustomEditTextListenerForNumber myCustomEditTextListenerForNumber;
        InputGradeStudentAdapter.MycustomFocusForNumber cutomfocusForNumber;


        public MyViewHolder(@NonNull View itemView, InputGradeStudentAdapter.MyCustomEditTextListenerForNumber myCustomEditTextListenerForNumber, InputGradeStudentAdapter.MycustomFocusForNumber cutomfocusForNumber) {
            super(itemView);
            tvGrade=(TextView)itemView.findViewById(R.id.tvGrade);
            tvStudentName=(TextView)itemView.findViewById(R.id.tvStudentName);
            tvObtainedGrade=(TextView)itemView.findViewById(R.id.tvObtainedGrade);
            imgUser=(ImageView)itemView.findViewById(R.id.imgUser);
            spLetter=(Spinner) itemView.findViewById(R.id.spLetter);
            etPercent=(TextView) itemView.findViewById(R.id.etPercent);
           // etTeacherComment=(EditText) itemView.findViewById(R.id.etTeacherComment);

            this.myCustomEditTextListenerForNumber = myCustomEditTextListenerForNumber;
            this.cutomfocusForNumber = cutomfocusForNumber;
            lnLetterGrade=(LinearLayout) itemView.findViewById(R.id.lnLetterGrade);
            imgComment=(ImageView)itemView.findViewById(R.id.imgComment);
            lnComment=(LinearLayout) itemView.findViewById(R.id.lnComment);
            lnPercent=(LinearLayout) itemView.findViewById(R.id.lnPercent);
           /* etPercent.addTextChangedListener(myCustomEditTextListenerForNumber);
            etPercent.setOnFocusChangeListener(cutomfocusForNumber);*/
        }
    }

    public InputGradeStudentAdapter(ArrayList<InputGradeStudentModel> itemList, Context context, ArrayList<String>gradeList,ArrayList<SpinnerModel>gradeModelList,Fragment fContext,int letterFlag) {
        this.itemList = itemList;
        this.context = context;
        this.gradeList=gradeList;
        this.gradeModelList=gradeModelList;
        this.fContext=fContext;
        this.letterFlag=letterFlag;

    }


    private class MycustomFocusForNumber implements View.OnFocusChangeListener   {
        private int position;


        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                //((CompetitorSaleActivity) context).updateItemStatus(position);

                // ((StudentOfGradeActivity) context).updateItemStatusForNumber(position,true);


            } else {





            }



        }
    }


    private class MyCustomEditTextListenerForNumber implements TextWatcher {

        private int position;


        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {


        }

        @Override
        public void afterTextChanged(Editable s) {





        }




    }
}
