package ru.verso.picturesnap.presentation.adapters.photographer;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.databinding.LayoutPhotographerServiceSelectionBinding;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;

public class ServiceSelectionAdapter extends ListAdapter<PhotographerPresentationService, PhotographerServiceSelectionViewHolder> {

    public ServiceSelectionAdapter(@NonNull DiffUtil.ItemCallback<PhotographerPresentationService> diffCallback) {
        super(diffCallback);

    }

    @NonNull
    @Override
    public PhotographerServiceSelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutPhotographerServiceSelectionBinding binding = LayoutPhotographerServiceSelectionBinding.inflate(layoutInflater, parent, false);

        return new PhotographerServiceSelectionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotographerServiceSelectionViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ServiceSelectionDiff extends DiffUtil.ItemCallback<PhotographerPresentationService> {

        @Override
        public boolean areItemsTheSame(@NonNull PhotographerPresentationService oldItem, @NonNull PhotographerPresentationService newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PhotographerPresentationService oldItem, @NonNull PhotographerPresentationService newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
