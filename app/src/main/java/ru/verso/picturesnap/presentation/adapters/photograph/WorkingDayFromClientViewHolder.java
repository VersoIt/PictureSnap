package ru.verso.picturesnap.presentation.adapters.photograph;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import ru.verso.picturesnap.databinding.LayoutWorkingDayFromClientBinding;
import ru.verso.picturesnap.domain.models.WorkingDay;

public class WorkingDayFromClientViewHolder extends RecyclerView.ViewHolder {

    private final LayoutWorkingDayFromClientBinding binding;

    private final Context context;

    public WorkingDayFromClientViewHolder(Context context, LayoutWorkingDayFromClientBinding binding) {
        super(binding.getRoot());

        this.context = context;
        this.binding = binding;
    }

    public void bind(WorkingDay workingDay) {
        binding.textViewDayName.setText(workingDay.getDayId() + "fu");
    }
}
