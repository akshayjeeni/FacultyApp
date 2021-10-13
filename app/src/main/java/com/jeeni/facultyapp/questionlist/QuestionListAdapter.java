package com.jeeni.facultyapp.questionlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jeeni.facultyapp.R;

import java.util.List;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.QuestionListViewHolder> {

    private List<QuestionListPojo> questionList;

    public QuestionListAdapter(List<QuestionListPojo> questionList) {
        this.questionList = questionList;
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
