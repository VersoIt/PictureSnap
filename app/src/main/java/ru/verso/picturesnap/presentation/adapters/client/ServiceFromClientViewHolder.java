package ru.verso.picturesnap.presentation.adapters.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutServiceFromClientBinding;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;

public class ServiceFromClientViewHolder extends RecyclerView.ViewHolder {

    private final LayoutServiceFromClientBinding binding;

    private final Context context;

    public ServiceFromClientViewHolder(Context context, LayoutServiceFromClientBinding binding) {
        super(binding.getRoot());

        this.context = context;
        this.binding = binding;
    }

    @SuppressLint("DiscouragedApi")
    public void bind(PhotographerPresentationService photographerService) {
        Resources resources = binding.getRoot().getResources();
        String packageName = context.getPackageName();

        binding.textViewServiceName.setText(resources.getIdentifier(photographerService.getName(), "string", packageName));

        String cost = String.format(Locale.getDefault(), "%d %s", photographerService.getCost(), resources.getString(R.string.rub_per_hour));
        binding.textViewCost.setText(cost);
    }
}
