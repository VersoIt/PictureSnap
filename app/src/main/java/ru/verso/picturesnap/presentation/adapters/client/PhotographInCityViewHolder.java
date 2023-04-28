package ru.verso.picturesnap.presentation.adapters.client;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import ru.verso.picturesnap.databinding.LayoutPhotographInCityBinding;
import ru.verso.picturesnap.domain.models.Photograph;

public class PhotographInCityViewHolder extends RecyclerView.ViewHolder {

    private final LayoutPhotographInCityBinding binding;
    private final Context context;

    public PhotographInCityViewHolder(Context context, LayoutPhotographInCityBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
    }

    public void bind(Photograph photograph) {
        String photographName = String.format("%s %s", photograph.getFirstName(), photograph.getLastName());
        binding.textViewPhotographName.setText(photographName);

        String rating = String.format(Locale.getDefault(), "%.0f/5", photograph.getRating());
        binding.textViewPhotographRating.setText(rating);
    }
}