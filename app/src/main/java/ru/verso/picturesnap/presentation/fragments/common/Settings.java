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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.SettingsRepositoryImpl;
import ru.verso.picturesnap.databinding.FragmentSettingsBinding;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.presentation.viewmodel.SettingsViewModel;
import ru.verso.picturesnap.presentation.viewmodel.factory.SettingsViewModelFactory;

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
                new SettingsRepositoryImpl(requireActivity().getApplicationContext()),
                new RoleRepositoryImpl(requireActivity().getApplicationContext())))
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

        binding.layoutReadyButton.buttonReady.setOnClickListener(v -> {
            if (settingsViewModel.getUserRole() == RoleRepository.Role.CLIENT)
                toolbarNavController.navigate(R.id.action_settings_to_client_main);
            else if (settingsViewModel.getUserRole() == RoleRepository.Role.UNREGISTERED)
                toolbarNavController.navigate(R.id.action_settings_to_unregistered_home);
        });
    }
}