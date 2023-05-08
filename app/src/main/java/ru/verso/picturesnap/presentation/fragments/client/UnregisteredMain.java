package ru.verso.picturesnap.presentation.fragments.client;

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
import androidx.recyclerview.widget.RecyclerView;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.databinding.FragmentUnregisteredMainBinding;
import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.adapters.client.PhotographServicesAdapter;
import ru.verso.picturesnap.presentation.adapters.client.PhotographsInCityAdapter;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.UnregisteredMainViewModel;
import ru.verso.picturesnap.presentation.factory.UnregisteredMainViewModelFactory;

public class UnregisteredMain extends Fragment {

    private FragmentUnregisteredMainBinding binding;

    private UnregisteredMainViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUnregisteredMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = getViewModel();

        NavController navController = getNavController();

        bindButtons(navController);
        createPhotographServicesList(navController);
        createPhotographsInCityList(navController);

        viewModel.getAllPhotographs().observe(requireActivity(), photographs -> viewModel.updatePhotographsInCity(photographs));
    }

    private UnregisteredMainViewModel getViewModel() {
        return new ViewModelProvider(this,
                new UnregisteredMainViewModelFactory(
                        requireActivity().getApplication(),
                        new GetPhotographDataUseCase(new PhotographRepositoryImpl()),
                        new GetUserDataUseCase(new UserLocationRepositoryImpl(this.requireActivity().getApplicationContext()),
                                new RoleRepositoryImpl(requireActivity().getApplicationContext()),
                                new FirstTimeWentRepositoryImpl(requireActivity().getApplicationContext())),
                        new UpdateUserDataUseCase(new RoleRepositoryImpl(requireActivity().getApplicationContext()),
                                new UserLocationRepositoryImpl(requireContext()),
                                new FirstTimeWentRepositoryImpl(requireContext()))))

                .get(UnregisteredMainViewModel.class);
    }

    private void bindButtons(NavController navController) {
        binding.linearLayoutAuthButtons.includeSignupButton.buttonSignup.setOnClickListener(view ->
                navController.navigate(R.id.action_unregistered_home_to_userSelection));

        binding.linearLayoutAuthButtons.includeLoginButton.buttonLogin.setOnClickListener(view ->
                navController.navigate(R.id.action_unregistered_home_to_login));
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }

    public void createPhotographsInCityList(NavController navController) {
        RecyclerView recyclerView = binding.recyclerViewPhotographsInCity;

        final PhotographsInCityAdapter photographsInCityAdapter = new PhotographsInCityAdapter(new PhotographsInCityAdapter.PhotographInCityDiff(),
                navController,
                this.requireActivity());

        recyclerView.setAdapter(photographsInCityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        viewModel.getPhotographsInCity().observe(getViewLifecycleOwner(), photographs -> {
            if (photographs.size() > 0) {
                binding.textViewPhotographsInCity.setVisibility(View.VISIBLE);
                binding.recyclerViewPhotographsInCity.setVisibility(View.VISIBLE);
                photographsInCityAdapter.submitList(photographs);
            }
        });
    }

    private void createPhotographServicesList(NavController navController) {
        RecyclerView recyclerView = binding.recyclerViewServices;

        final PhotographServicesAdapter adapter =
                new PhotographServicesAdapter(new PhotographServicesAdapter.PhotographServiceDiff(), navController);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        viewModel.getServices().observe(getViewLifecycleOwner(), services -> {
            if (services.size() > 0) {
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewServices.setVisibility(View.VISIBLE);
                adapter.submitList(services);
            }
        });
    }
}