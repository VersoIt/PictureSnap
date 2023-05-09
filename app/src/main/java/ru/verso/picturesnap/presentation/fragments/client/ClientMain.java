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
import ru.verso.picturesnap.data.repository.PhotographerPresentationServiceRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerServiceRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.databinding.FragmentClientMainBinding;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerServiceByIdUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographersByServiceIdUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.adapters.client.PhotographerServicesAdapterFromRegisteredClient;
import ru.verso.picturesnap.presentation.adapters.client.PhotographersInCityFromRegisteredClientAdapter;
import ru.verso.picturesnap.presentation.factory.ClientRecordsViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.client.ClientPhotographersOfSelectedServiceViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.UnregisteredMainViewModel;
import ru.verso.picturesnap.presentation.factory.UnregisteredMainViewModelFactory;

public class ClientMain extends Fragment {

    private FragmentClientMainBinding binding;

    private UnregisteredMainViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentClientMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = getViewModel();

        NavController navController = getNavController();

        bindRecordsButton(navController);
        createPhotographerServicesList(navController);
        createPhotographersInCityList(navController);

        viewModel.getAllPhotographers().observe(requireActivity(), photographers -> viewModel.updatePhotographersInCity(photographers));
    }

    private UnregisteredMainViewModel getViewModel() {

        return new ViewModelProvider(this,
                new UnregisteredMainViewModelFactory(
                        requireActivity().getApplication(),
                        new GetPhotographerDataUseCase(new PhotographerRepositoryImpl()),
                        new GetUserDataUseCase(new UserLocationRepositoryImpl(this.requireActivity().getApplicationContext()),
                                new RoleRepositoryImpl(requireActivity().getApplicationContext()),
                                new FirstTimeWentRepositoryImpl(requireActivity().getApplicationContext())),
                        new UpdateUserDataUseCase(new RoleRepositoryImpl(requireActivity().getApplicationContext()),
                                new UserLocationRepositoryImpl(requireContext()),
                                new FirstTimeWentRepositoryImpl(requireContext()))))

                .get(UnregisteredMainViewModel.class);
    }

    private void bindRecordsButton(NavController navController) {
        binding.textViewMyRecords.setOnClickListener(view -> navController.navigate(R.id.action_client_main_to_clientMyRecords));
    }

    private ClientPhotographersOfSelectedServiceViewModel getClientRecordsViewModel() {

        return new ViewModelProvider(requireActivity(), new ClientRecordsViewModelFactory(new GetPhotographersByServiceIdUseCase(new PhotographerRepositoryImpl(),
                new PhotographerPresentationServiceRepositoryImpl()),
                new GetPhotographerServiceByIdUseCase(new PhotographerServiceRepositoryImpl()))).get(ClientPhotographersOfSelectedServiceViewModel.class);
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }

    public void createPhotographersInCityList(NavController navController) {
        RecyclerView recyclerView = binding.recyclerViewPhotographersInCity;

        final PhotographersInCityFromRegisteredClientAdapter photographersInCityAdapter = new PhotographersInCityFromRegisteredClientAdapter(new PhotographersInCityFromRegisteredClientAdapter.PhotographerInCityDiff(),
                navController,
                this.requireActivity(),
                R.id.action_client_main_to_photographerProfileFromClient);

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

        final PhotographerServicesAdapterFromRegisteredClient adapter =
                new PhotographerServicesAdapterFromRegisteredClient(new PhotographerServicesAdapterFromRegisteredClient.PhotographerServiceDiff(), navController, getClientRecordsViewModel());
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