package ru.verso.picturesnap.presentation.fragments.photographer;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.verso.picturesnap.databinding.FragmentImagesBinding;

public class PortfolioImagesFragment extends Fragment {

    private FragmentImagesBinding binding;

    private final String serviceId;

    public PortfolioImagesFragment(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentImagesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.text.setText("" + serviceId);
    }
}