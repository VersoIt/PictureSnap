package ru.verso.picturesnap.presentation.fragments.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.SettingsRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserAuthDataRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.data.storage.datasources.firebase.UserAuthFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.room.RoleRoomDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.FirstTimeWentSharedPrefsDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.SettingsSharedPrefsDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.UserLocationSharedPrefsDataSource;
import ru.verso.picturesnap.databinding.FragmentSettingsBinding;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.usecase.GetApplicationSettingsDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateApplicationSettingsUseCase;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.SettingsViewModel;
import ru.verso.picturesnap.presentation.factory.SettingsViewModelFactory;

public class Settings extends Fragment {

    private FragmentSettingsBinding binding;

    private SettingsViewModel settingsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settingsViewModel = new ViewModelProvider(this, new SettingsViewModelFactory(
                new GetApplicationSettingsDataUseCase(new SettingsRepositoryImpl(new SettingsSharedPrefsDataSource(requireContext()))),
                new GetUserDataUseCase(new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(requireContext())),
                        new RoleRepositoryImpl(new RoleRoomDataSource(requireContext())),
                        new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(requireContext())),
                        new UserAuthDataRepositoryImpl(new UserAuthFirebaseDataSource())),
                new UpdateApplicationSettingsUseCase(new SettingsRepositoryImpl(new SettingsSharedPrefsDataSource(requireContext())))
                ))
                .get(SettingsViewModel.class);

        binding.settingsFields.switchCompatNotifications.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked)
                settingsViewModel.enableNotifications();
            else
                settingsViewModel.disableNotifications();
        });

        settingsViewModel.getNotificationsState().observe(getViewLifecycleOwner(), (isEnabled) -> binding.settingsFields.switchCompatNotifications.setChecked(isEnabled));

        NavController toolbarNavController =
                Navigation.findNavController(requireActivity(), R.id.fragmentContainerView_content);

        binding.layoutReadyButton.buttonReady.setOnClickListener(v -> toolbarNavController.navigateUp());
    }
}