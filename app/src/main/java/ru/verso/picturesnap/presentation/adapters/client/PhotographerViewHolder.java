package ru.verso.picturesnap.presentation.adapters.client;

import android.os.Handler;
import android.os.Looper;

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

    private final Handler handler;

    public PhotographerViewHolder(LayoutPhotographerBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        handler = new Handler(Looper.getMainLooper());
    }

    public void bind(Photographer photographer) {
        String photographerName = String.format("%s %s", photographer.getFirstName(), photographer.getLastName());
        binding.textViewPhotographerName.setText(photographerName);

        String rating = String.format(Locale.getDefault(), "%.1f/5", photographer.getRating());
        binding.textViewPhotographerRating.setText(rating);

        showLocation(photographer);

        Glide.with(binding.imageViewPhotographerLogo.getContext())
                .load(photographer.getAvatarPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_person_gray)
                .into(binding.imageViewPhotographerLogo);
    }

    private void showLocation(Photographer photographer) {

        new Thread(() -> {
            String location = LocationCoordinator.getFullAddress(binding.getRoot().getContext(), photographer.getLatitude(), photographer.getLongitude());
            handler.post(() -> binding.textViewPhotographerAddress.setText(location));
        }).start();
    }
}