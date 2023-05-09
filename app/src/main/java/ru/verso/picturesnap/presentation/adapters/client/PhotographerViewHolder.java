package ru.verso.picturesnap.presentation.adapters.client;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import ru.verso.picturesnap.databinding.LayoutFavoritePhotographerBinding;
import ru.verso.picturesnap.domain.models.Photographer;

public class PhotographerViewHolder extends RecyclerView.ViewHolder {

    private final LayoutFavoritePhotographerBinding binding;

    public PhotographerViewHolder(@NonNull LayoutFavoritePhotographerBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Photographer photographer) {
        binding.textViewPhotographerName.setText(String.format("%s %s", photographer.getFirstName(), photographer.getLastName()));
        binding.textViewPhotographerRating.setText(String.format(Locale.getDefault(), "%.0f/5", photographer.getRating()));
        binding.textViewPhotographerPhoneNumber.setText(photographer.getPhoneNumber());
    }
}
