package ru.verso.picturesnap.presentation.adapters.client;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Locale;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutPhotographerBinding;
import ru.verso.picturesnap.domain.models.Photographer;

public class PhotographerViewHolder extends RecyclerView.ViewHolder {

    private final LayoutPhotographerBinding binding;

    public PhotographerViewHolder(@NonNull LayoutPhotographerBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Photographer photographer) {
        binding.textViewPhotographerName.setText(String.format("%s %s", photographer.getFirstName(), photographer.getLastName()));
        binding.textViewPhotographerRating.setText(String.format(Locale.getDefault(), "%.0f/5", photographer.getRating()));

        Glide.with(binding.imageViewPhotographerLogo.getContext())
                .load(photographer.getAvatarPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_person_gray)
                .into(binding.imageViewPhotographerLogo);
    }
}
