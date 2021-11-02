package com.jeeni.facultyapp.questionlist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.card.MaterialCardView;
import com.jeeni.facultyapp.R;
import com.jeeni.facultyapp.questiondetail.QuestionDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.QuestionListViewHolder> {

    private List<QuestionListPojo> questionList;
    Context context;

    public QuestionListAdapter(List<QuestionListPojo> questionList, Context context) {
        this.questionList = questionList;
        this.context = context;
    }

    @Override
    public QuestionListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.recycler_question_list, parent, false);

        return new QuestionListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionListAdapter.QuestionListViewHolder holder, int position) {
        //   holder.textViewQuestioText.setText(questionList.get(position).getQuestionGenImgUrl());

        QuestionListPojo questionListPojo = questionList.get(position);

        Picasso.get()
                .load(questionListPojo.getQuestionGenImgUrl())
                .placeholder(R.drawable.loading)
                .into(holder.questionImg);
        holder.cardViewQuestionItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "" + questionListPojo.getQuestionId(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, QuestionDetailActivity.class);
                intent.putExtra("questionId",questionListPojo.getQuestionId());
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
        MaterialCardView cardViewQuestionItem;

        public QuestionListViewHolder(View itemView) {
            super(itemView);
            questionImg = (ImageView) itemView.findViewById(R.id.imgView_question_list_item);
            cardViewQuestionItem = (MaterialCardView) itemView.findViewById(R.id.card_question_item);
        }
    }

    public void addAllData(List<QuestionListPojo> newList) {
        if (newList != null) {
            questionList.addAll(newList);
            notifyDataSetChanged();
        }

    }
}
