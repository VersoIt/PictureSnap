package ru.verso.picturesnap.presentation.adapters.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

import androidx.recyclerview.widget.RecyclerView;

import ru.verso.picturesnap.databinding.LayoutPhotographServiceBinding;
import ru.verso.picturesnap.domain.models.PhotographService;

public class PhotographServiceViewHolder extends RecyclerView.ViewHolder {

    private final LayoutPhotographServiceBinding binding;
    private final Context context;

    public PhotographServiceViewHolder(Context context, LayoutPhotographServiceBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
    }

    @SuppressLint("DiscouragedApi")
    public void bind(PhotographService photographService) {
        Resources resources = binding.getRoot().getResources();
        String packageName = context.getPackageName();

        binding.imageViewBackground.setImageResource(resources.getIdentifier(photographService.getIconPath(), "drawable", packageName));
        binding.textViewHeader.setText(resources.getIdentifier(photographService.getName(), "string", packageName));
    }
}