package ru.verso.picturesnap.presentation.adapters.client;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.databinding.LayoutServiceFromClientBinding;
import ru.verso.picturesnap.domain.models.PhotographPresentationService;

public class ServicesFromClientAdapter extends ListAdapter<PhotographPresentationService, ServiceFromClientViewHolder> {

    public ServicesFromClientAdapter(@NonNull DiffUtil.ItemCallback<PhotographPresentationService> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ServiceFromClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutServiceFromClientBinding binding = LayoutServiceFromClientBinding.inflate(layoutInflater, parent, false);

        return new ServiceFromClientViewHolder(layoutInflater.getContext(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceFromClientViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ServicesDiff extends DiffUtil.ItemCallback<PhotographPresentationService> {

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
