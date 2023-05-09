package ru.verso.picturesnap.presentation.adapters.client;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import ru.verso.picturesnap.databinding.LayoutPhotographerInCityBinding;
import ru.verso.picturesnap.domain.models.Photographer;

public class PhotographerInCityViewHolder extends RecyclerView.ViewHolder {

    private final LayoutPhotographerInCityBinding binding;

    public PhotographerInCityViewHolder(LayoutPhotographerInCityBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Photographer photographer) {
        String photographerName = String.format("%s %s", photographer.getFirstName(), photographer.getLastName());
        binding.textViewPhotographerName.setText(photographerName);

        String rating = String.format(Locale.getDefault(), "%.0f/5", photographer.getRating());
        binding.textViewPhotographerRating.setText(rating);
    }
}