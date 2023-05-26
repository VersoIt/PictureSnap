package ru.verso.picturesnap.presentation.adapters.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

import java.util.List;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutServiceToBookBinding;
import ru.verso.picturesnap.databinding.LayoutServiceToBookDropdownBinding;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;

public class ServicesBookingSpinnerAdapter implements SpinnerAdapter {

    private final Context context;

    private final List<PhotographerPresentationService> services;

    public ServicesBookingSpinnerAdapter(Context context, List<PhotographerPresentationService> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutServiceToBookDropdownBinding binding;
        PhotographerPresentationService service = (PhotographerPresentationService) getItem(position);
        if (convertView == null)
            binding = LayoutServiceToBookDropdownBinding.inflate(LayoutInflater.from(context), parent, false);
        else
            binding = LayoutServiceToBookDropdownBinding.bind(convertView);

        binding.textViewPrice.setText(String.format("%s %s", service.getCost(), context.getString(R.string.rub_per_hour)));
        binding.textViewService.setText(getResourceIdStringByName(service.getName(), context));
        return binding.getRoot();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutServiceToBookBinding binding;
        PhotographerPresentationService service = (PhotographerPresentationService) getItem(position);
        if (convertView == null)
            binding = LayoutServiceToBookBinding.inflate(LayoutInflater.from(context), parent, false);
        else
            binding = LayoutServiceToBookBinding.bind(convertView);

        binding.textViewPrice.setText(String.format("%s %s", service.getCost(), context.getString(R.string.rub_per_hour)));
        binding.textViewService.setText(getResourceIdStringByName(service.getName(), context));
        return binding.getRoot();
    }

    @SuppressLint("DiscouragedApi")
    private int getResourceIdStringByName(String name, Context context) {
        Resources resources = context.getResources();
        String packageName = context.getPackageName();

        return resources.getIdentifier(name, "string", packageName);
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return services.isEmpty();
    }
}
