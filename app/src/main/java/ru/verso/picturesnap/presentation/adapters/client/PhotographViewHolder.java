package ru.verso.picturesnap.presentation.adapters.client;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import ru.verso.picturesnap.databinding.LayoutFavoritePhotographBinding;
import ru.verso.picturesnap.domain.models.Photograph;

public class PhotographViewHolder extends RecyclerView.ViewHolder {

    private final LayoutFavoritePhotographBinding binding;

    public PhotographViewHolder(@NonNull LayoutFavoritePhotographBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Photograph photograph) {
        binding.textViewPhotographName.setText(String.format("%s %s", photograph.getFirstName(), photograph.getLastName()));
        binding.textViewPhotographRating.setText(String.format(Locale.getDefault(), "%.0f/5", photograph.getRating()));
        binding.textViewPhotographPhoneNumber.setText(photograph.getPhoneNumber());
    }
}
