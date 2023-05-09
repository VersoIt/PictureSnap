package ru.verso.picturesnap.presentation.fragments.client;

import android.annotation.SuppressLint;
import android.content.res.Resources;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentPhotographersOfSelectedServiceBinding;
import ru.verso.picturesnap.presentation.adapters.client.PhotographersInCityAdapter;
import ru.verso.picturesnap.presentation.adapters.client.PhotographersInCityFromRegisteredClientAdapter;
import ru.verso.picturesnap.presentation.viewmodel.client.ClientPhotographersOfSelectedServiceViewModel;

public class PhotographersOfSelectedService extends Fragment {

    private FragmentPhotographersOfSelectedServiceBinding binding;

    private boolean skipFirstPhotographsDeliver = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotographersOfSelectedServiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ClientPhotographersOfSelectedServiceViewModel viewModel = getViewModel();
        createPhotographersList(viewModel, getNavController());
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }

    @SuppressLint("DiscouragedApi")
    private void createPhotographersList(ClientPhotographersOfSelectedServiceViewModel viewModel, NavController navController) {

        PhotographersInCityFromRegisteredClientAdapter photographersAdapter = new PhotographersInCityFromRegisteredClientAdapter(new PhotographersInCityAdapter.PhotographerInCityDiff(),
                navController,
                requireActivity(),
                R.id.action_photographersOfSelectedService_to_photographerProfileFromClient);
        binding.recyclerViewPhotographers.setAdapter(photographersAdapter);
        binding.recyclerViewPhotographers.setLayoutManager(new LinearLayoutManager(requireContext()));
        viewModel.getPhotographersByServiceId().observe(getViewLifecycleOwner(), photographers -> {
            if (skipFirstPhotographsDeliver) {
                skipFirstPhotographsDeliver = false;
                return;
            }

            if (photographers != null) {
                if (photographers.size() > 0)
                    photographersAdapter.submitList(photographers);
            }

            hideLoading();
        });

        viewModel.getService().observe(getViewLifecycleOwner(), service -> {
            String name = service.getName();
            if (name != null) {
                String packageName = binding.getRoot().getContext().getPackageName();
                Resources resources = binding.getRoot().getResources();
                binding.textViewHeader.setText(resources.getIdentifier(service.getName(), "string", packageName));
            }
        });
    }

    private void showAbsentText() {
        binding.textViewAbsent.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        binding.progressBarLoading.setVisibility(View.GONE);
        binding.recyclerViewPhotographers.setVisibility(View.VISIBLE);
    }

    private ClientPhotographersOfSelectedServiceViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(ClientPhotographersOfSelectedServiceViewModel.class);
    }

}