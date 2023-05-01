package ru.verso.picturesnap.presentation.fragments.photograph;

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

    private final int photographId;

    public PortfolioImagesFragment(int photographId) {
        this.photographId = photographId;
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

        binding.text.setText("" + photographId);
    }
}