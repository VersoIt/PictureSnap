package ru.verso.picturesnap.presentation.fragments.photograph;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentAboutPhotographFromClientBinding;
import ru.verso.picturesnap.presentation.viewmodel.AboutPhotographFromClientViewModel;

public class AboutPhotographFromClient extends Fragment {

    private FragmentAboutPhotographFromClientBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutPhotographFromClientBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AboutPhotographFromClientViewModel viewModel = getViewModel();
        initViews(viewModel, getNavController());
    }

    private AboutPhotographFromClientViewModel getViewModel() {
        return new ViewModelProvider(requireActivity())
                .get(AboutPhotographFromClientViewModel.class);
    }

    private void initViews(AboutPhotographFromClientViewModel viewModel, NavController navController) {

        viewModel.getPhotograph().observe(getViewLifecycleOwner(), photograph ->
                binding.textViewDescription.setText(photograph.getDescription()));

        viewModel.getPhotograph().observe(getViewLifecycleOwner(), photograph ->
                binding.textViewExperience.setText(getStringExperience(photograph.getExperience())));

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