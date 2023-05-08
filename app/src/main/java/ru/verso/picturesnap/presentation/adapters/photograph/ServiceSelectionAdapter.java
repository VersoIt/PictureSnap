package ru.verso.picturesnap.presentation.adapters.photograph;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.databinding.LayoutPhotographServiceSelectionBinding;
import ru.verso.picturesnap.domain.models.PhotographPresentationService;

public class ServiceSelectionAdapter extends ListAdapter<PhotographPresentationService, PhotographServiceSelectionViewHolder> {

    public ServiceSelectionAdapter(@NonNull DiffUtil.ItemCallback<PhotographPresentationService> diffCallback) {
        super(diffCallback);

    }

    @NonNull
    @Override
    public PhotographServiceSelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutPhotographServiceSelectionBinding binding = LayoutPhotographServiceSelectionBinding.inflate(layoutInflater, parent, false);

        return new PhotographServiceSelectionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotographServiceSelectionViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ServiceSelectionDiff extends DiffUtil.ItemCallback<PhotographPresentationService> {

        @Override
        public boolean areItemsTheSame(@NonNull PhotographPresentationService oldItem, @NonNull PhotographPresentationService newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PhotographPresentationService oldItem, @NonNull PhotographPresentationService newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
