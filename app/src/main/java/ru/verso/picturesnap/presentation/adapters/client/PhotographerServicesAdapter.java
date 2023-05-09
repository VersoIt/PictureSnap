package ru.verso.picturesnap.presentation.adapters.client;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutPhotographerServiceBinding;
import ru.verso.picturesnap.domain.models.PhotographerService;

public class PhotographerServicesAdapter extends ListAdapter<PhotographerService, PhotographerServiceViewHolder> {

    private final NavController navController;

    public PhotographerServicesAdapter(@NonNull DiffUtil.ItemCallback<PhotographerService> diffCallback, NavController navController) {
        super(diffCallback);
        this.navController = navController;
    }

    @NonNull
    @Override
    public PhotographerServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutPhotographerServiceBinding binding = LayoutPhotographerServiceBinding.inflate(layoutInflater, parent, false);

        return new PhotographerServiceViewHolder(layoutInflater.getContext(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotographerServiceViewHolder holder, int position) {
        PhotographerService current = getItem(position);
        holder.bind(current);

        holder.itemView.setOnClickListener(view -> navController.navigate(R.id.action_unregistered_home_to_unregistered_profile));
    }

    public static class PhotographerServiceDiff extends DiffUtil.ItemCallback<PhotographerService> {

        @Override
        public boolean areItemsTheSame(@NonNull PhotographerService oldItem, @NonNull PhotographerService newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PhotographerService oldItem, @NonNull PhotographerService newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}