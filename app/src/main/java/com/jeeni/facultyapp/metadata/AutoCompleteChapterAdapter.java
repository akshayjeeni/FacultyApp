package com.jeeni.facultyapp.metadata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jeeni.facultyapp.R;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteChapterAdapter extends ArrayAdapter<ChapterTopicListPojo> {
    private List<ChapterTopicListPojo> allPlacesList;
    private List<ChapterTopicListPojo> filteredPlacesList;

    public AutoCompleteChapterAdapter(@NonNull Context context,  List<ChapterTopicListPojo> placesList) {
        super(context,0,placesList);
        allPlacesList = new ArrayList<>(placesList);
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return placeFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.autocomplete_item_chapter, parent, false
            );
        }

        TextView placeLabel = convertView.findViewById(R.id.txt_chapter_label);

        ChapterTopicListPojo place = getItem(position);
        if (place != null) {
            placeLabel.setText(place.getName());
        }

        return convertView;
    }

    private Filter placeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            filteredPlacesList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredPlacesList.addAll(allPlacesList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ChapterTopicListPojo place: allPlacesList) {
                    if (place.getName().toLowerCase().contains(filterPattern)) {
                        filteredPlacesList.add(place);
                    }
                }
            }

            results.values = filteredPlacesList;
            results.count = filteredPlacesList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ChapterTopicListPojo) resultValue).getName();
        }
    };
}
