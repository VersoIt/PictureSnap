package ru.verso.picturesnap.presentation.fragments.photographer;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import ru.verso.picturesnap.R;

public class ExperienceSpinnerAdapter implements SpinnerAdapter {

    private final Context context;

    private final List<Integer> experiences;

    public ExperienceSpinnerAdapter(Context context, List<Integer> experience) {
        this.context = context;
        this.experiences = experience;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_experience, parent, false);
        }

        TextView experience = view.findViewById(R.id.textView_experience);
        experience.setText(convertToStringExperience(context, experiences.get(position)));

        return view;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return experiences.size();
    }

    @Override
    public Object getItem(int position) {
        return experiences.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_experience, parent, false);
        }

        TextView experience = view.findViewById(R.id.textView_experience);
        experience.setText(convertToStringExperience(context, experiences.get(position)));

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return experiences.isEmpty();
    }

    private String convertToStringExperience(Context context, int year) {
        int[] experienceResources = {
                R.string.one_year,
                R.string.two_years,
                R.string.three_years,
                R.string.four_years,
                R.string.five_years,
                R.string.more_five_years
        };

        return context.getResources().getString(experienceResources[year >= 6 ? experienceResources.length - 1 : year - 1]);
    }
}
