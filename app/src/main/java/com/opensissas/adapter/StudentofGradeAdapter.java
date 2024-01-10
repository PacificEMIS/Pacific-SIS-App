package com.opensissas.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissas.R;
import com.opensissas.model.StudentofGradeModel;
import com.opensissas.model.StudentsModel;
import com.opensissas.ui.teacher.activity.StudentOfGradeActivity;

import java.util.ArrayList;


public class StudentofGradeAdapter extends RecyclerView.Adapter<StudentofGradeAdapter.MyViewHolder> {
    ArrayList<StudentofGradeModel> itemList = new ArrayList<>();
    Context context;
    int flag;
    View.OnFocusChangeListener listener;
    Boolean focusFlag = true;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gradeofstudent_row, viewGroup, false);
        return new MyViewHolder(view, new MyCustomEditTextListenerForNumber(), new MycustomFocusForNumber(), new MyCustomEditTextListenerForComment(), new MycustomFocusForComments());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.etNumber.setText(itemList.get(i).getMarks());
        myViewHolder.etComment.setText(itemList.get(i).getComment());
        myViewHolder.tvTotal.setText(itemList.get(i).getTotalMarks());
        myViewHolder.tvStudentName.setText(itemList.get(i).getStudentName());
        myViewHolder.tvStudentID.setText("ID :" + itemList.get(i).getStudentID());
        if (flag == 0) {
            myViewHolder.etNumber.setEnabled(false);
            myViewHolder.etComment.setEnabled(false);

        } else {
            myViewHolder.etNumber.setFocusableInTouchMode(true);
            myViewHolder.etNumber.setEnabled(true);
            myViewHolder.etComment.setEnabled(true);

        }


        myViewHolder.myCustomEditTextListenerForNumber.updatePosition(myViewHolder.getAdapterPosition());
        myViewHolder.cutomfocusForNumber.updatePosition(myViewHolder.getAdapterPosition());

        myViewHolder.myCustomEditTextListenerForComments.updatePosition(myViewHolder.getAdapterPosition());
        myViewHolder.cutomfocusForComments.updatePosition(myViewHolder.getAdapterPosition());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText etNumber, etComment;
        TextView tvTotal, tvStudentName, tvStudentID;

        MyCustomEditTextListenerForNumber myCustomEditTextListenerForNumber;
        MycustomFocusForNumber cutomfocusForNumber;

        MyCustomEditTextListenerForComment myCustomEditTextListenerForComments;
        MycustomFocusForComments cutomfocusForComments;


        public MyViewHolder(@NonNull View itemView, MyCustomEditTextListenerForNumber myCustomEditTextListenerForNumber, MycustomFocusForNumber cutomfocusForNumber, MyCustomEditTextListenerForComment myCustomEditTextListenerForComments, MycustomFocusForComments cutomfocusForComments) {
            super(itemView);
            etComment = (EditText) itemView.findViewById(R.id.etComment);
            etNumber = (EditText) itemView.findViewById(R.id.etNumber);
            tvTotal = (TextView) itemView.findViewById(R.id.tvTotal);
            tvStudentName = (TextView) itemView.findViewById(R.id.tvStudentName);
            tvStudentID = (TextView) itemView.findViewById(R.id.tvStudentID);

            this.myCustomEditTextListenerForNumber = myCustomEditTextListenerForNumber;
            this.cutomfocusForNumber = cutomfocusForNumber;
            etNumber.addTextChangedListener(myCustomEditTextListenerForNumber);
            etNumber.setOnFocusChangeListener(cutomfocusForNumber);


            this.myCustomEditTextListenerForComments = myCustomEditTextListenerForComments;
            this.cutomfocusForComments = cutomfocusForComments;
            etComment.addTextChangedListener(myCustomEditTextListenerForComments);
            etComment.setOnFocusChangeListener(cutomfocusForComments);


        }
    }

    public StudentofGradeAdapter(ArrayList<StudentofGradeModel> itemList, Context context, int flag) {
        this.itemList = itemList;
        this.context = context;
        this.flag = flag;
    }


    private class MycustomFocusForNumber implements View.OnFocusChangeListener {
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
                if (!itemList.get(position).getMarks().equals("")) {

                    ((StudentOfGradeActivity) context).updateItemStatusForNumber(position, true);
                }


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


            if (s.length() > 0) {
                itemList.get(position).setMarks(s.toString());

            } else {
                itemList.get(position).setMarks(itemList.get(position).getMarks());
            }


        }


    }


    private class MycustomFocusForComments implements View.OnFocusChangeListener {
        private int position;


        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                //((CompetitorSaleActivity) context).updateItemStatus(position);

                //   ((CompetitorSaleActivity) context).updateItemStatus(position,false);
            } else {
                ((StudentOfGradeActivity) context).updateItemStatusForCommets(position, true);


            }


        }
    }


    private class MyCustomEditTextListenerForComment implements TextWatcher {

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
            if (s.toString().length() > 0) {
                if (flag == 1) {

                    itemList.get(position).setComment(s.toString());
                }


            } else {
                //   itemList.get(position).setComment(itemList.get(position).getComment());
            }

        }


    }

    public void updateList(ArrayList<StudentofGradeModel> list) {
        itemList = list;
        notifyDataSetChanged();

    }
}
