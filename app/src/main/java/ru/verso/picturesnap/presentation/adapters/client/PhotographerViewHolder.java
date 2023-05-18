package ru.verso.picturesnap.presentation.adapters.client;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Locale;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutPhotographerBinding;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.presentation.utils.LocationCoordinator;

public class PhotographerViewHolder extends RecyclerView.ViewHolder {

    private final LayoutPhotographerBinding binding;

    public PhotographerViewHolder(LayoutPhotographerBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Photographer photographer) {
        String photographerName = String.format("%s %s", photographer.getFirstName(), photographer.getLastName());
        binding.textViewPhotographerName.setText(photographerName);

        String rating = String.format(Locale.getDefault(), "%.0f/5", photographer.getRating());
        binding.textViewPhotographerRating.setText(rating);

        binding.textViewPhotographerAddress.setText(LocationCoordinator.getFullAddress(binding.getRoot().getContext(), photographer.getLatitude(), photographer.getLongitude()));

        Glide.with(binding.imageViewPhotographerLogo.getContext())
                .load(photographer.getAvatarPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_person_gray)
                .into(binding.imageViewPhotographerLogo);
    }
}