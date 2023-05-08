package ru.verso.picturesnap.presentation.fragments.photograph;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.PhotographRegistrationRepositoryImpl;
import ru.verso.picturesnap.data.repository.ServicesRepositoryImpl;
import ru.verso.picturesnap.databinding.FragmentPhotographRegistrationBinding;
import ru.verso.picturesnap.domain.usecase.GetServicesUseCase;
import ru.verso.picturesnap.domain.usecase.SignUpNewPhotographUseCase;
import ru.verso.picturesnap.presentation.factory.PhotographRegistrationViewModelFactory;
import ru.verso.picturesnap.presentation.factory.PhotographServicesSelectionViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographPhotoSessionAddressSelectionViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographRegistrationViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographServicesSelectionViewModel;

public class PhotographRegistration extends Fragment {

    private FragmentPhotographRegistrationBinding binding;

    private PhotographRegistrationViewModel photographRegistrationViewModel;

    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotographRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.buttonRegistration.buttonSignup.setOnClickListener(v -> {
            save();
            verify();
        });

        PhotographPhotoSessionAddressSelectionViewModel photographPhotoSessionAddressSelectionViewModel = getPhotoSessionSelectionViewModel();
        PhotographServicesSelectionViewModel photographServicesSelectionViewModel = getPhotographServicesSelectionViewModel();
        photographRegistrationViewModel = getPhotographRegistrationViewModel();

        observePhotographRegistrationViewModel(photographRegistrationViewModel);
        navController = getNavController();

        photographServicesSelectionViewModel.getStatusId().observe(getViewLifecycleOwner(), status -> binding.textViewServices.setHint(status));

        photographPhotoSessionAddressSelectionViewModel.getLocationString().observe(getViewLifecycleOwner(), location ->
                binding.textViewAddress.setText(location));

        photographPhotoSessionAddressSelectionViewModel.getLocation().observe(getViewLifecycleOwner(), location ->
                photographRegistrationViewModel.setPhotoSessionLocation(location));

        photographServicesSelectionViewModel.getAllServices().observe(getViewLifecycleOwner(), services ->
                photographRegistrationViewModel.setServices(services));

        buildSpinner();

        initAddressView(navController);
        initServicesSelection(navController);
        setOnBackPressEvent();
    }

    private void save() {

        photographRegistrationViewModel.setFirstName(binding.editTextFirstName.getText().toString());
        photographRegistrationViewModel.setLastName(binding.editTextLastName.getText().toString());
        photographRegistrationViewModel.setExperience((Integer)binding.spinnerExperience.getSelectedItem());
        photographRegistrationViewModel.setEmail(binding.editTextEmail.getText().toString());
        photographRegistrationViewModel.setPhoneNumber(binding.editTextPhoneNumber.getText().toString());
        photographRegistrationViewModel.setAboutText(binding.editTextAbout.getText().toString());
        photographRegistrationViewModel.setPassword(binding.editTextPassword.getText().toString());
        photographRegistrationViewModel.setPasswordConfirmation(binding.editTextConfirmPassword.getText().toString());
    }

    private void setOnBackPressEvent() {

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                save();
                navController.navigateUp();
            }
        });
    }

    private void observePhotographRegistrationViewModel(PhotographRegistrationViewModel photographRegistrationViewModel) {

        photographRegistrationViewModel.getFirstName().observe(getViewLifecycleOwner(), name ->
                binding.editTextFirstName.setText(name));

        photographRegistrationViewModel.getLastName().observe(getViewLifecycleOwner(), name ->
                binding.editTextLastName.setText(name));

        photographRegistrationViewModel.getEmail().observe(getViewLifecycleOwner(), email ->
                binding.editTextEmail.setText(email));

        photographRegistrationViewModel.getPhoneNumber().observe(getViewLifecycleOwner(), phoneNumber ->
                binding.editTextPhoneNumber.setText(phoneNumber));

        photographRegistrationViewModel.getAboutText().observe(getViewLifecycleOwner(), services ->
                binding.editTextAbout.setText(services));

        photographRegistrationViewModel.getPassword().observe(getViewLifecycleOwner(), password ->
                binding.editTextPassword.setText(password));

        photographRegistrationViewModel.getPasswordConfirmation().observe(getViewLifecycleOwner(), confirmation ->
                binding.editTextConfirmPassword.setText(confirmation));

        photographRegistrationViewModel.getExperience().observe(getViewLifecycleOwner(), experience ->
                binding.spinnerExperience.setSelection(experience - 1));
    }

    private NavController getNavController() {

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        return Objects.requireNonNull(navHostFragment).getNavController();
    }

    private void buildSpinner() {

        ExperienceSpinnerAdapter experienceSpinnerAdapter = new ExperienceSpinnerAdapter(requireContext(), new ArrayList<Integer>() {
            {
                add(1);
                add(2);
                add(3);
                add(4);
                add(5);
                add(6);
            }
        });
        binding.spinnerExperience.setAdapter(experienceSpinnerAdapter);
    }

    private void verify() {

        if (!photographRegistrationViewModel.isHaveAllData()) {
            Toast.makeText(requireContext(), R.string.fill_input, Toast.LENGTH_LONG).show();
            return;
        }

        if (!photographRegistrationViewModel.isPasswordsMatch()) {
            Toast.makeText(requireContext(), R.string.passwords_not_equals, Toast.LENGTH_LONG).show();
            return;
        }

        if (!photographRegistrationViewModel.isPhoneNumberMatch()) {
            Toast.makeText(requireContext(), R.string.wrong_enter_phone_number, Toast.LENGTH_LONG).show();
            return;
        }

        if (!photographRegistrationViewModel.isEmailMatch()) {
            Toast.makeText(requireContext(), R.string.wrong_enter_email, Toast.LENGTH_LONG).show();
            return;
        }

        if (!photographRegistrationViewModel.isValidPassword()) {
            Toast.makeText(requireContext(), R.string.short_password, Toast.LENGTH_LONG).show();
            return;
        }

        if (!photographRegistrationViewModel.isValidFirstName()) {
            Toast.makeText(requireContext(), R.string.invalid_first_name, Toast.LENGTH_LONG).show();
            return;
        }

        if (!photographRegistrationViewModel.isValidLastName()) {
            Toast.makeText(requireContext(), R.string.invalid_last_name, Toast.LENGTH_LONG).show();
            return;
        }

        photographRegistrationViewModel.save();
    }

    private void initServicesSelection(NavController navController) {
        binding.textViewServices.setOnClickListener(view -> {
            save();
            navController.navigate(R.id.action_photographRegistration_to_photographServicesSelection);
        });
    }

    private void initAddressView(NavController navController) {
        binding.textViewAddress.setOnClickListener(view -> {
            save();
            navController.navigate(R.id.action_photographRegistration_to_photographPhotoSessionAddressSelection);
        });
    }

    private PhotographPhotoSessionAddressSelectionViewModel getPhotoSessionSelectionViewModel() {

        return new ViewModelProvider(requireActivity()).get(PhotographPhotoSessionAddressSelectionViewModel.class);
    }

    private PhotographServicesSelectionViewModel getPhotographServicesSelectionViewModel() {

        return new ViewModelProvider(requireActivity(),
                new PhotographServicesSelectionViewModelFactory(getViewLifecycleOwner(),
                        new GetServicesUseCase(new ServicesRepositoryImpl())))
                .get(PhotographServicesSelectionViewModel.class);
    }

    private PhotographRegistrationViewModel getPhotographRegistrationViewModel() {

        return new ViewModelProvider(requireActivity(),
                new PhotographRegistrationViewModelFactory(new SignUpNewPhotographUseCase(
                new PhotographRegistrationRepositoryImpl())))
                .get(PhotographRegistrationViewModel.class);
    }
}