package ru.verso.picturesnap.presentation.fragments.photographer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentAboutPhotographerFromClientBinding;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.AboutPhotographerFromClientViewModel;

public class AboutPhotographerFromClient extends Fragment {

    private FragmentAboutPhotographerFromClientBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutPhotographerFromClientBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AboutPhotographerFromClientViewModel viewModel = getViewModel();
        initViews(viewModel, getNavController());
    }

    private AboutPhotographerFromClientViewModel getViewModel() {
        return new ViewModelProvider(requireActivity())
                .get(AboutPhotographerFromClientViewModel.class);
    }

    private void initViews(AboutPhotographerFromClientViewModel viewModel, NavController navController) {

        viewModel.getPhotographer().observe(getViewLifecycleOwner(), photographer ->
                binding.textViewDescription.setText(photographer.getDescription()));

        viewModel.getPhotographer().observe(getViewLifecycleOwner(), photographer ->
                binding.textViewExperience.setText(getStringExperience(photographer.getExperience())));

        binding.layoutReadyButton.buttonReady.setOnClickListener(view -> navController.navigateUp());
    }

    private String getStringExperience(int experience) {
        int[] resourcesId = {
                R.string.one_year,
                R.string.two_years,
                R.string.three_years,
                R.string.four_years,
                R.string.five_years,
                R.string.more_five_years };

        return binding.getRoot().getResources().getString(experience < resourcesId.length ?
                resourcesId[experience - 1] :
                resourcesId[resourcesId.length - 1]);
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }
}