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
import ru.verso.picturesnap.data.repository.ClientRepositoryImpl;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerPresentationServiceRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerServiceRepositoryImpl;
import ru.verso.picturesnap.data.repository.RecordsRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserAuthDataRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.data.storage.datasources.firebase.ClientFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.PhotographerFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.PhotographerPresentationServiceFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.PhotographerServiceFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.RecordsFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.UserAuthFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.room.RoleRoomDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.FirstTimeWentSharedPrefsDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.UserLocationSharedPrefsDataSource;
import ru.verso.picturesnap.databinding.FragmentClientMainBinding;
import ru.verso.picturesnap.domain.usecase.GetClientDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetClientRecordsUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerServiceByIdUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographersByServiceIdUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.adapters.client.PhotographerServicesAdapterFromRegisteredClient;
import ru.verso.picturesnap.presentation.adapters.client.PhotographersInCityFromRegisteredClientAdapter;
import ru.verso.picturesnap.presentation.factory.ClientMainViewModelFactory;
import ru.verso.picturesnap.presentation.factory.ClientRecordsViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.client.ClientMainViewModel;
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

        NavController navController = getNavController();

        binding.textViewMyRecords.setEnabled(true);
        bindRecordsButton(navController);

        viewModel = getUnregisteredMainViewModel();

        createPhotographerServicesList(navController);
        createPhotographersInCityList(navController);

        viewModel.getAllPhotographers().observe(requireActivity(), photographers -> viewModel.updatePhotographersInCity(photographers));
        initAmountOfRecords(getClientMainViewModel());
    }

    private void initAmountOfRecords(ClientMainViewModel clientMainViewModel) {
        clientMainViewModel.getClientId().observe(getViewLifecycleOwner(), id -> clientMainViewModel.getClientRecords(id, records -> {
            if (records.size() > 0) {
                binding.textViewRecordCount.setVisibility(View.VISIBLE);
                binding.textViewRecordCount.setText(String.valueOf(records.size()));
            }
        }));
    }

    private UnregisteredMainViewModel getUnregisteredMainViewModel() {

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

    private void bindRecordsButton(NavController navController) {
        binding.textViewMyRecords.setOnClickListener(view -> navController.navigate(R.id.action_client_main_to_clientMyRecords));
    }

    private ClientPhotographersOfSelectedServiceViewModel getClientRecordsViewModel() {

        return new ViewModelProvider(requireActivity(), new ClientRecordsViewModelFactory(new GetPhotographersByServiceIdUseCase(new PhotographerRepositoryImpl(new PhotographerFirebaseDataSource()),
                new PhotographerPresentationServiceRepositoryImpl(new PhotographerPresentationServiceFirebaseDataSource())),
                new GetPhotographerServiceByIdUseCase(new PhotographerServiceRepositoryImpl(new PhotographerServiceFirebaseDataSource())))).get(ClientPhotographersOfSelectedServiceViewModel.class);
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }

    public void createPhotographersInCityList(NavController navController) {
        RecyclerView recyclerView = binding.recyclerViewPhotographersInCity;

        final PhotographersInCityFromRegisteredClientAdapter photographersInCityAdapter = new PhotographersInCityFromRegisteredClientAdapter(getContext(), new PhotographersInCityFromRegisteredClientAdapter.PhotographerInCityDiff(),
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
            binding.textViewMyRecords.setEnabled(true);
            if (services != null && services.size() > 0) {
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewServices.setVisibility(View.VISIBLE);
                adapter.submitList(services);
            }
        });
    }

    private ClientMainViewModel getClientMainViewModel() {

        return new ViewModelProvider(this, new ClientMainViewModelFactory(new GetUserDataUseCase(
                new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(requireContext())),
                new RoleRepositoryImpl(new RoleRoomDataSource(requireContext())),
                new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(requireContext())),
                new UserAuthDataRepositoryImpl(new UserAuthFirebaseDataSource())),
                new GetClientDataUseCase(new ClientRepositoryImpl(new ClientFirebaseDataSource())),
                new GetClientRecordsUseCase(new RecordsRepositoryImpl(new RecordsFirebaseDataSource())))).get(ClientMainViewModel.class);
    }
}