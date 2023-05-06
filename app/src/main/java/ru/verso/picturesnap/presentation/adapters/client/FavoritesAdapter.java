package ru.verso.picturesnap.presentation.adapters.client;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutFavoritePhotographBinding;
import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.presentation.viewmodel.PhotographProfileViewModel;

public class FavoritesAdapter extends ListAdapter<Photograph, PhotographViewHolder> {

    private final NavController navController;

    private final PhotographProfileViewModel photographProfileViewModel;

    public FavoritesAdapter(@NonNull ItemCallback<Photograph> diffCallback, NavController navController, PhotographProfileViewModel photographProfileViewModel) {
        super(diffCallback);

        this.navController = navController;
        this.photographProfileViewModel = photographProfileViewModel;
    }

    @NonNull
    @Override
    public PhotographViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutFavoritePhotographBinding binding = LayoutFavoritePhotographBinding.inflate(layoutInflater, parent, false);

        return new PhotographViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotographViewHolder holder, int position) {
        holder.bind(getItem(position));
        holder.itemView.setOnClickListener(view -> {
            photographProfileViewModel.putId(getItem(position).getId());
            navController.navigate(R.id.action_favorites_fragment_to_photograph_profile_from_unregistered);
        });
    }

    public static class DiffUtil extends ItemCallback<Photograph> {

        @Override
        public boolean areItemsTheSame(@NonNull Photograph oldItem, @NonNull Photograph newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Photograph oldItem, @NonNull Photograph newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    }
}
