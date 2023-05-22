package ru.verso.picturesnap.presentation.adapters.photographer;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.databinding.LayoutPortfolioImageBinding;
import ru.verso.picturesnap.domain.models.PortfolioImage;

public class PortfolioImageAdapter extends ListAdapter<PortfolioImage, PortfolioImageViewHolder> {

    public PortfolioImageAdapter(@NonNull DiffUtil.ItemCallback<PortfolioImage> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public PortfolioImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LayoutPortfolioImageBinding binding = LayoutPortfolioImageBinding.inflate(inflater);

        return new PortfolioImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PortfolioImageViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class PortfolioImageDiff extends DiffUtil.ItemCallback<PortfolioImage> {

        @Override
        public boolean areItemsTheSame(@NonNull PortfolioImage oldItem, @NonNull PortfolioImage newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PortfolioImage oldItem, @NonNull PortfolioImage newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    }
}
