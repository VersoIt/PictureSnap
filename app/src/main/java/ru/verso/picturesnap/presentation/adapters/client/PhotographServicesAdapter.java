package ru.verso.picturesnap.presentation.adapters.client;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutPhotographServiceBinding;
import ru.verso.picturesnap.domain.models.PhotographService;

public class PhotographServicesAdapter extends ListAdapter<PhotographService, PhotographServiceViewHolder> {

    private final NavController navController;

    public PhotographServicesAdapter(@NonNull DiffUtil.ItemCallback<PhotographService> diffCallback, NavController navController) {
        super(diffCallback);

        this.navController = navController;
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

        holder.itemView.setOnClickListener(view -> navController.navigate(R.id.action_unregistered_home_to_unregistered_profile));
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