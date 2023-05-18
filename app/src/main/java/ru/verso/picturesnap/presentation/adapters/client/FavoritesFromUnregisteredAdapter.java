package ru.verso.picturesnap.presentation.adapters.client;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutPhotographerBinding;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographerProfileViewModel;

public class FavoritesFromUnregisteredAdapter extends ListAdapter<Photographer, PhotographerViewHolder> {

    private final NavController navController;

    private final PhotographerProfileViewModel photographerProfileViewModel;

    public FavoritesFromUnregisteredAdapter(@NonNull ItemCallback<Photographer> diffCallback, NavController navController, PhotographerProfileViewModel photographerProfileViewModel) {
        super(diffCallback);

        this.navController = navController;
        this.photographerProfileViewModel = photographerProfileViewModel;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public PhotographerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutPhotographerBinding binding = LayoutPhotographerBinding.inflate(layoutInflater, parent, false);

        return new PhotographerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotographerViewHolder holder, int position) {
        holder.bind(getItem(position));
        holder.itemView.setOnClickListener(view -> {
            photographerProfileViewModel.putId(getItem(position).getId());
            navController.navigate(R.id.action_favorites_fragment_to_photographer_profile_from_unregistered);
        });
    }

    public static class DiffUtil extends ItemCallback<Photographer> {

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
