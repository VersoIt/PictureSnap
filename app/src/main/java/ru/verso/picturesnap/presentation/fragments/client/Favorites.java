package ru.verso.picturesnap.presentation.fragments.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentFavoritesBinding;

public class Favorites extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ru.verso.picturesnap.databinding.FragmentFavoritesBinding binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}