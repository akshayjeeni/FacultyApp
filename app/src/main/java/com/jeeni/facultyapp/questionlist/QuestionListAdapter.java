package com.jeeni.facultyapp.questionlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.jeeni.facultyapp.R;
import com.jeeni.facultyapp.questiondetail.QuestionDetailActivity;

import java.util.List;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.QuestionListViewHolder> {

    private List<QuestionListPojo> questionList;
    Context context;

    public QuestionListAdapter(List<QuestionListPojo> questionList,Context context) {
        this.questionList = questionList;
        this.context = context;
    }

    @Override
    public QuestionListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_question_list, parent, false);

        return new QuestionListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionListAdapter.QuestionListViewHolder holder, int position) {
        holder.textViewQuestioText.setText(questionList.get(position).getQuestionImgSrc());
        QuestionListPojo questionListPojo = questionList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+questionList.get(position).getQuestionImgSrc(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, QuestionDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class QuestionListViewHolder extends RecyclerView.ViewHolder {
        public ImageView questionImg;
        TextView textViewQuestioText;

        public QuestionListViewHolder(View itemView) {
            super(itemView);
            questionImg = (ImageView) itemView.findViewById(R.id.imgView_question_list_item);
            textViewQuestioText = (TextView) itemView.findViewById(R.id.txtQuestText);
        }
    }
}
