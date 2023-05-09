package ru.verso.picturesnap.presentation.adapters.client;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.databinding.LayoutPhotographerBinding;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerService;

public class PhotographersAdapter extends ListAdapter<Photographer, PhotographerViewHolder> {

    public PhotographersAdapter(@NonNull DiffUtil.ItemCallback<Photographer> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public PhotographerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutPhotographerBinding binding = LayoutPhotographerBinding.inflate(layoutInflater, parent, false);

        return new PhotographerViewHolder(binding);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotographerViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class PhotographerDiff extends DiffUtil.ItemCallback<Photographer> {

        @Override
        public boolean areItemsTheSame(@NonNull Photographer oldItem, @NonNull Photographer newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Photographer oldItem, @NonNull Photographer newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    }
}
