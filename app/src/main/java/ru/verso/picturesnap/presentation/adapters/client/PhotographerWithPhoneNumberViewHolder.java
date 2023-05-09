package ru.verso.picturesnap.presentation.adapters.client;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Locale;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutPhotographerWithPhoneNumberBinding;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.presentation.utils.StringConverter;

public class PhotographerWithPhoneNumberViewHolder extends RecyclerView.ViewHolder {

    private final LayoutPhotographerWithPhoneNumberBinding binding;

    public PhotographerWithPhoneNumberViewHolder(LayoutPhotographerWithPhoneNumberBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Photographer photographer) {
        String photographerName = String.format("%s %s", photographer.getFirstName(), photographer.getLastName());
        binding.textViewPhotographerName.setText(photographerName);

        String rating = String.format(Locale.getDefault(), "%.0f/5", photographer.getRating());
        binding.textViewPhotographerRating.setText(rating);

        String phoneNumber = StringConverter.convertPhoneNumberToConvenientFormat(photographer.getPhoneNumber());
        binding.textViewPhotographerPhoneNumber.setText(phoneNumber);

        Glide.with(binding.imageViewPhotographerLogo.getContext())
                .load(photographer.getAvatarPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_person_gray)
                .into(binding.imageViewPhotographerLogo);
    }
}