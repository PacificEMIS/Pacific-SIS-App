package com.opensissas.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.opensissas.R;
import com.opensissas.model.InputGradeStudentModel;
import com.opensissas.model.SpinnerModel;
import com.opensissas.ui.teacher.fragment.InputGradeFragment;

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



            myViewHolder.lnLetterGrade.setVisibility(View.VISIBLE);
            myViewHolder.etPercent.setText(itemList.get(i).getPercent()+"");
        myViewHolder.etCredit.setText(itemList.get(i).getCredit()+"");




       // myViewHolder.spLetter.setSelection(itemList.get(i).getPos1());











        myViewHolder.myCustomEditTextListenerForNumber.updatePosition(myViewHolder.getAdapterPosition());
        myViewHolder.cutomfocusForNumber.updatePosition(myViewHolder.getAdapterPosition());

       myViewHolder.lnPercent.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ((InputGradeFragment)fContext).addMarkpopup(i,itemList.get(i).getStudentName(),itemList.get(i).getPercent()+"%");

           }
       });

        myViewHolder.lnLetterGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InputGradeFragment)fContext).addGradepopup(i,itemList.get(i).getStudentName(),itemList.get(i).getCredit()+"");

            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName,tvGrade;
        ImageView imgUser;
        TextView etPercent,etCredit;
        LinearLayout lnLetterGrade;
        ImageView imgComment;
        LinearLayout lnComment,lnPercent;

        InputGradeStudentAdapter.MyCustomEditTextListenerForNumber myCustomEditTextListenerForNumber;
        InputGradeStudentAdapter.MycustomFocusForNumber cutomfocusForNumber;


        public MyViewHolder(@NonNull View itemView, InputGradeStudentAdapter.MyCustomEditTextListenerForNumber myCustomEditTextListenerForNumber, InputGradeStudentAdapter.MycustomFocusForNumber cutomfocusForNumber) {
            super(itemView);
            tvGrade=(TextView)itemView.findViewById(R.id.tvGrade);
            tvStudentName=(TextView)itemView.findViewById(R.id.tvStudentName);

            imgUser=(ImageView)itemView.findViewById(R.id.imgUser);

            etPercent=(TextView) itemView.findViewById(R.id.etPercent);
           // etTeacherComment=(EditText) itemView.findViewById(R.id.etTeacherComment);
            etCredit=(EditText)itemView.findViewById(R.id.etCredit);
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
