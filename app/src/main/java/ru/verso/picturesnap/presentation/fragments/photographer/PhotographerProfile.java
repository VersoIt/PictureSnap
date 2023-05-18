package ru.verso.picturesnap.presentation.fragments.photographer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentPhotographerProfileBinding;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographerProfileViewModel;

public class PhotographerProfile extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ru.verso.picturesnap.databinding.FragmentPhotographerProfileBinding binding = FragmentPhotographerProfileBinding.inflate(inflater, container, false);
        return inflater.inflate(R.layout.fragment_photographer_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}