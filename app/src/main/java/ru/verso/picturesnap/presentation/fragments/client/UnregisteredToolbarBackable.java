package ru.verso.picturesnap.presentation.fragments.client;

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
import ru.verso.picturesnap.databinding.FragmentUnregisteredToolbarBackableBinding;

public class UnregisteredToolbarBackable extends Fragment {

    private FragmentUnregisteredToolbarBackableBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUnregisteredToolbarBackableBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NavController contentNavController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView_content);

        binding.imageViewPrevious.setOnClickListener(v ->
                contentNavController.navigateUp());
    }
}