package ru.verso.picturesnap.presentation.adapters.client;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.databinding.LayoutPhotographServiceBinding;
import ru.verso.picturesnap.domain.models.PhotographService;

public class PhotographServicesAdapter extends ListAdapter<PhotographService, PhotographServiceViewHolder> {

    public PhotographServicesAdapter(@NonNull DiffUtil.ItemCallback<PhotographService> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public PhotographServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutPhotographServiceBinding binding = LayoutPhotographServiceBinding.inflate(layoutInflater, parent, false);

        return new PhotographServiceViewHolder(layoutInflater.getContext(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotographServiceViewHolder holder, int position) {
        PhotographService current = getItem(position);
        holder.bind(current);

        holder.itemView.setOnClickListener(view -> {

        });
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
}