package ru.verso.picturesnap.presentation.fragments.photograph;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentPhotographServicesSelectionBinding;
import ru.verso.picturesnap.databinding.LayoutPhotographServiceBinding;

public class PhotographServicesSelection extends Fragment {

    private FragmentPhotographServicesSelectionBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhotographServicesSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = getNavController();
        binding.layoutReadyButton.buttonReady.setOnClickListener(v -> {
            if (isValid())
                navController.navigateUp();
            else
                Toast.makeText(requireContext(), R.string.fill_input, Toast.LENGTH_LONG).show();
        });
    }

    private boolean isValid() {

        return !binding.editTextAdvertisementPrice.getText().toString().isEmpty() &&
                !binding.editTextChildPrice.getText().toString().isEmpty() &&
                !binding.editTextFamilyPrice.getText().toString().isEmpty() &&
                !binding.editTextObjectivePrice.getText().toString().isEmpty() &&
                !binding.editTextPortraitPrice.getText().toString().isEmpty() &&
                !binding.editTextStudioPrice.getText().toString().isEmpty() &&
                !binding.editTextPregnantPrice.getText().toString().isEmpty();
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }
}