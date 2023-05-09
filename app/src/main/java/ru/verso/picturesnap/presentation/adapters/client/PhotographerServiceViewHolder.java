package ru.verso.picturesnap.presentation.adapters.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

import androidx.recyclerview.widget.RecyclerView;

import ru.verso.picturesnap.databinding.LayoutPhotographerServiceBinding;
import ru.verso.picturesnap.domain.models.PhotographerService;

public class PhotographerServiceViewHolder extends RecyclerView.ViewHolder {

    private final LayoutPhotographerServiceBinding binding;
    private final Context context;

    public PhotographerServiceViewHolder(Context context, LayoutPhotographerServiceBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
    }

    @SuppressLint("DiscouragedApi")
    public void bind(PhotographerService photographerService) {
        Resources resources = binding.getRoot().getResources();
        String packageName = context.getPackageName();

        binding.imageViewBackground.setImageResource(resources.getIdentifier(photographerService.getIconPath(), "drawable", packageName));
        binding.textViewHeader.setText(resources.getIdentifier(photographerService.getName(), "string", packageName));
    }
}