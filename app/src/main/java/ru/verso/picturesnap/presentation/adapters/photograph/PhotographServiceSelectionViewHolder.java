package ru.verso.picturesnap.presentation.adapters.photograph;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.verso.picturesnap.databinding.LayoutPhotographServiceSelectionBinding;
import ru.verso.picturesnap.domain.models.PhotographPresentationService;
import ru.verso.picturesnap.presentation.app.PictureSnapApp;

public class PhotographServiceSelectionViewHolder extends RecyclerView.ViewHolder {

    private final LayoutPhotographServiceSelectionBinding binding;

    public PhotographServiceSelectionViewHolder(@NonNull LayoutPhotographServiceSelectionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @SuppressLint("DiscouragedApi")
    public void bind(PhotographPresentationService service) {

        String packageName = binding.getRoot().getContext().getPackageName();
        Resources resources = binding.getRoot().getResources();

        final EditText price = binding.editTextPrice;
        int serviceCost = service.getCost();

        binding.textViewServiceName.setText(resources.getIdentifier(service.getName(), "string", packageName));
        price.setText(String.valueOf(serviceCost).equals("0") ? "" : serviceCost + "");

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (price.getText().toString().matches(PictureSnapApp.DIGIT_MATCH))
                    service.setCost(Integer.parseInt(price.getText().toString().isEmpty() ? "0" : price.getText().toString()));
            }
        });
    }

    public LayoutPhotographServiceSelectionBinding getBinding() {
        return binding;
    }
}
