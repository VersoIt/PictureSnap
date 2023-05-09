package ru.verso.picturesnap.presentation.fragments.common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentUserSelectionBinding;

public class UserSelection extends Fragment {

    private FragmentUserSelectionBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        NavController contentNavController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView_content);

        binding.buttonClient.setOnClickListener(v ->
                contentNavController.navigate(R.id.action_userSelection_to_clientRegistration));

        binding.buttonPhotographer.setOnClickListener(v ->
                contentNavController.navigate(R.id.action_userSelection_to_photographerRegistration));
    }
}