package ru.verso.picturesnap.presentation.adapters.client;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.databinding.LayoutFeedbackFromClientBinding;
import ru.verso.picturesnap.domain.models.Feedback;

public class PhotographerFeedbacksAdapter extends ListAdapter<Feedback, FeedbackViewHolder> {

    public PhotographerFeedbacksAdapter(@NonNull DiffUtil.ItemCallback<Feedback> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutFeedbackFromClientBinding binding =  LayoutFeedbackFromClientBinding.inflate(layoutInflater, parent, false);

        return new FeedbackViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class FeedbacksDiff extends DiffUtil.ItemCallback<Feedback> {

        @Override
        public boolean areItemsTheSame(@NonNull Feedback oldItem, @NonNull Feedback newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Feedback oldItem, @NonNull Feedback newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    }
}
