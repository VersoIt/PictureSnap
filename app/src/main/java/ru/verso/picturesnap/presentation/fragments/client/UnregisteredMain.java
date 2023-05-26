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
import ru.verso.picturesnap.data.repository.PhotographerRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserAuthDataRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.data.storage.datasources.firebase.PhotographerFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.UserAuthFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.room.RoleRoomDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.FirstTimeWentSharedPrefsDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.UserLocationSharedPrefsDataSource;
import ru.verso.picturesnap.databinding.FragmentUnregisteredMainBinding;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.adapters.client.PhotographerServicesAdapter;
import ru.verso.picturesnap.presentation.adapters.client.PhotographersInCityAdapter;
import ru.verso.picturesnap.presentation.adapters.client.PhotographersInCityFromRegisteredClientAdapter;
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
        createPhotographerServicesList(navController);
        createPhotographersInCityList(navController);

        viewModel.getAllPhotographers().observe(requireActivity(), photographers -> viewModel.updatePhotographersInCity(photographers));
    }

    private UnregisteredMainViewModel getViewModel() {
        return new ViewModelProvider(this,
                new UnregisteredMainViewModelFactory(
                        requireActivity().getApplication(),
                        new GetPhotographerDataUseCase(new PhotographerRepositoryImpl(new PhotographerFirebaseDataSource())),
                        new GetUserDataUseCase(new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(requireContext())),
                                new RoleRepositoryImpl(new RoleRoomDataSource(requireContext())),
                                new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(requireContext())),
                                new UserAuthDataRepositoryImpl(new UserAuthFirebaseDataSource())),
                        new UpdateUserDataUseCase(new RoleRepositoryImpl(new RoleRoomDataSource(requireContext())),
                                new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(requireContext())),
                                new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(requireContext())))))

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

    public void createPhotographersInCityList(NavController navController) {
        RecyclerView recyclerView = binding.recyclerViewPhotographersInCity;

        final PhotographersInCityAdapter photographersInCityAdapter = new PhotographersInCityAdapter(getContext(), new PhotographersInCityFromRegisteredClientAdapter.PhotographerInCityDiff(),
                navController,
                this.requireActivity());

        recyclerView.setAdapter(photographersInCityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        viewModel.getPhotographersInCity().observe(getViewLifecycleOwner(), photographers -> {
            if (photographers != null && photographers.size() > 0) {
                binding.textViewPhotographersInCity.setVisibility(View.VISIBLE);
                binding.recyclerViewPhotographersInCity.setVisibility(View.VISIBLE);
                photographersInCityAdapter.submitList(photographers);
            }
        });
    }

    private void createPhotographerServicesList(NavController navController) {
        RecyclerView recyclerView = binding.recyclerViewServices;

        final PhotographerServicesAdapter adapter =
                new PhotographerServicesAdapter(new PhotographerServicesAdapter.PhotographerServiceDiff(), navController);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        viewModel.getServices().observe(getViewLifecycleOwner(), services -> {
            if (services != null && services.size() > 0) {
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewServices.setVisibility(View.VISIBLE);
                adapter.submitList(services);
            }
        });
    }
}