package ru.verso.picturesnap.presentation.adapters.photograph;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.databinding.LayoutWorkingDayFromClientBinding;
import ru.verso.picturesnap.domain.models.PhotographService;
import ru.verso.picturesnap.domain.models.WorkingDay;

public class WorkingDaysFromUnregisteredAdapter extends ListAdapter<WorkingDay, WorkingDayFromClientViewHolder> {

    public WorkingDaysFromUnregisteredAdapter(@NonNull DiffUtil.ItemCallback<WorkingDay> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public WorkingDayFromClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutWorkingDayFromClientBinding binding = LayoutWorkingDayFromClientBinding.inflate(layoutInflater, parent, false);

        return new WorkingDayFromClientViewHolder(layoutInflater.getContext(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkingDayFromClientViewHolder holder, int position) {
        WorkingDay current = getItem(position);
        holder.bind(current);
    }

    public static class PhotographServiceDiff extends DiffUtil.ItemCallback<PhotographService> {

        @Override
        public boolean areItemsTheSame(@NonNull PhotographService oldItem, @NonNull PhotographService newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PhotographService oldItem, @NonNull PhotographService newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

    public static class WorkingDayDiff extends DiffUtil.ItemCallback<WorkingDay> {

        @Override
        public boolean areItemsTheSame(@NonNull WorkingDay oldItem, @NonNull WorkingDay newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull WorkingDay oldItem, @NonNull WorkingDay newItem) {
            return oldItem.getDayId() == newItem.getDayId();
        }
    }
}
