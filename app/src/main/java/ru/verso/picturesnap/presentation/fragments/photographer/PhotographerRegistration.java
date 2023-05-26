package ru.verso.picturesnap.presentation.fragments.photographer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerSignUpRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.ServicesRepositoryImpl;
import ru.verso.picturesnap.data.repository.SignInRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.data.storage.datasources.firebase.PhotographerSignUpFirebaseDataBase;
import ru.verso.picturesnap.data.storage.datasources.firebase.ServicesFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.SignInFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.room.RoleRoomDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.FirstTimeWentSharedPrefsDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.UserLocationSharedPrefsDataSource;
import ru.verso.picturesnap.databinding.FragmentPhotographerRegistrationBinding;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.repository.SignUpFailureCallback;
import ru.verso.picturesnap.domain.usecase.GetServicesUseCase;
import ru.verso.picturesnap.domain.usecase.SignInUserUseCase;
import ru.verso.picturesnap.domain.usecase.SignUpNewPhotographerUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.activity.MainActivity;
import ru.verso.picturesnap.presentation.factory.PhotographerRegistrationViewModelFactory;
import ru.verso.picturesnap.presentation.factory.PhotographerServicesSelectionViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographerPhotoSessionAddressSelectionViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographerRegistrationViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographerServicesSelectionViewModel;

public class PhotographerRegistration extends Fragment {

    private FragmentPhotographerRegistrationBinding binding;

    private PhotographerRegistrationViewModel photographerRegistrationViewModel;

    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotographerRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.buttonRegistration.buttonSignup.setOnClickListener(v -> {
            save();
            verify();
        });

        PhotographerPhotoSessionAddressSelectionViewModel photographerPhotoSessionAddressSelectionViewModel = getPhotoSessionSelectionViewModel();
        PhotographerServicesSelectionViewModel photographerServicesSelectionViewModel = getPhotographerServicesSelectionViewModel();
        photographerRegistrationViewModel = getPhotographerRegistrationViewModel();

        observePhotographerRegistrationViewModel(photographerRegistrationViewModel);
        navController = getNavController();

        photographerServicesSelectionViewModel.getStatusId().observe(getViewLifecycleOwner(), status -> binding.textViewServices.setHint(status));

        photographerPhotoSessionAddressSelectionViewModel.getLocationString().observe(getViewLifecycleOwner(), location ->
                binding.textViewAddress.setText(location));

        photographerPhotoSessionAddressSelectionViewModel.getLocation().observe(getViewLifecycleOwner(), location ->
                photographerRegistrationViewModel.setPhotoSessionLocation(location));

        photographerServicesSelectionViewModel.getAllServices().observe(getViewLifecycleOwner(), services ->
                photographerRegistrationViewModel.setServices(services));

        buildSpinner();

        initAddressView(navController);
        initServicesSelection(navController);
        setOnBackPressEvent();
    }

    private void save() {

        photographerRegistrationViewModel.setFirstName(binding.editTextFirstName.getText().toString());
        photographerRegistrationViewModel.setLastName(binding.editTextLastName.getText().toString());
        photographerRegistrationViewModel.setExperience((Integer) binding.spinnerExperience.getSelectedItem());
        photographerRegistrationViewModel.setEmail(binding.editTextEmail.getText().toString());
        photographerRegistrationViewModel.setPhoneNumber(binding.editTextPhoneNumber.getText().toString());
        photographerRegistrationViewModel.setAboutText(binding.editTextAbout.getText().toString());
        photographerRegistrationViewModel.setPassword(binding.editTextPassword.getText().toString());
        photographerRegistrationViewModel.setPasswordConfirmation(binding.editTextConfirmPassword.getText().toString());
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

    private void observePhotographerRegistrationViewModel(PhotographerRegistrationViewModel photographerRegistrationViewModel) {

        photographerRegistrationViewModel.getFirstName().observe(getViewLifecycleOwner(), name ->
                binding.editTextFirstName.setText(name));

        photographerRegistrationViewModel.getLastName().observe(getViewLifecycleOwner(), name ->
                binding.editTextLastName.setText(name));

        photographerRegistrationViewModel.getEmail().observe(getViewLifecycleOwner(), email ->
                binding.editTextEmail.setText(email));

        photographerRegistrationViewModel.getPhoneNumber().observe(getViewLifecycleOwner(), phoneNumber ->
                binding.editTextPhoneNumber.setText(phoneNumber));

        photographerRegistrationViewModel.getAboutText().observe(getViewLifecycleOwner(), services ->
                binding.editTextAbout.setText(services));

        photographerRegistrationViewModel.getPassword().observe(getViewLifecycleOwner(), password ->
                binding.editTextPassword.setText(password));

        photographerRegistrationViewModel.getPasswordConfirmation().observe(getViewLifecycleOwner(), confirmation ->
                binding.editTextConfirmPassword.setText(confirmation));

        photographerRegistrationViewModel.getExperience().observe(getViewLifecycleOwner(), experience ->
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

        if (!photographerRegistrationViewModel.isHaveAllData()) {
            Toast.makeText(requireContext(), R.string.fill_input, Toast.LENGTH_LONG).show();
            return;
        }

        if (!photographerRegistrationViewModel.isPasswordsMatch()) {
            Toast.makeText(requireContext(), R.string.passwords_not_equals, Toast.LENGTH_LONG).show();
            return;
        }

        if (!photographerRegistrationViewModel.isPhoneNumberMatch()) {
            Toast.makeText(requireContext(), R.string.wrong_enter_phone_number, Toast.LENGTH_LONG).show();
            return;
        }

        if (!photographerRegistrationViewModel.isEmailMatch()) {
            Toast.makeText(requireContext(), R.string.wrong_enter_email, Toast.LENGTH_LONG).show();
            return;
        }

        if (!photographerRegistrationViewModel.isValidPassword()) {
            Toast.makeText(requireContext(), R.string.short_password, Toast.LENGTH_LONG).show();
            return;
        }

        if (!photographerRegistrationViewModel.isValidFirstName()) {
            Toast.makeText(requireContext(), R.string.invalid_first_name, Toast.LENGTH_LONG).show();
            return;
        }

        if (!photographerRegistrationViewModel.isValidLastName()) {
            Toast.makeText(requireContext(), R.string.invalid_last_name, Toast.LENGTH_LONG).show();
            return;
        }

        binding.buttonRegistration.buttonSignup.setEnabled(false);

        photographerRegistrationViewModel.signUpPhotographer(new SignUpFailureCallback<Photographer>() {

            @Override
            public void onUserCollision() {
                Toast.makeText(requireActivity(), R.string.user_already_exists, Toast.LENGTH_LONG).show();
                binding.buttonRegistration.buttonSignup.setEnabled(true);
            }

            @Override
            public void onNetworkError() {
                Toast.makeText(requireActivity(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
                binding.buttonRegistration.buttonSignup.setEnabled(true);
            }
        }).observe(getViewLifecycleOwner(), photographer ->
                photographerRegistrationViewModel.signInPhotograph().observe(getViewLifecycleOwner(), user -> {
            if (user.getRole() != null && photographer.getId() != null) {
                sendToMainActivity();
                requireActivity().finish();
            }
        }));
    }

    private void sendToMainActivity() {
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void initServicesSelection(NavController navController) {
        binding.textViewServices.setOnClickListener(view -> {
            save();
            navController.navigate(R.id.action_photographerRegistration_to_photographerServicesSelection);
        });
    }

    private void initAddressView(NavController navController) {
        binding.textViewAddress.setOnClickListener(view -> {
            save();
            navController.navigate(R.id.action_photographerRegistration_to_photographerPhotoSessionAddressSelection);
        });
    }

    private PhotographerPhotoSessionAddressSelectionViewModel getPhotoSessionSelectionViewModel() {

        return new ViewModelProvider(requireActivity()).get(PhotographerPhotoSessionAddressSelectionViewModel.class);
    }

    private PhotographerServicesSelectionViewModel getPhotographerServicesSelectionViewModel() {

        return new ViewModelProvider(requireActivity(),
                new PhotographerServicesSelectionViewModelFactory(getViewLifecycleOwner(),
                        new GetServicesUseCase(new ServicesRepositoryImpl(new ServicesFirebaseDataSource()))))
                .get(PhotographerServicesSelectionViewModel.class);
    }

    private PhotographerRegistrationViewModel getPhotographerRegistrationViewModel() {

        return new ViewModelProvider(requireActivity(),
                new PhotographerRegistrationViewModelFactory(new SignUpNewPhotographerUseCase(
                        new PhotographerSignUpRepositoryImpl(new PhotographerSignUpFirebaseDataBase())),
                        new SignInUserUseCase(new SignInRepositoryImpl(new SignInFirebaseDataSource())),
                        new UpdateUserDataUseCase(new RoleRepositoryImpl(new RoleRoomDataSource(requireContext())),
                                new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(requireContext())),
                                new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(requireContext())))))
                .get(PhotographerRegistrationViewModel.class);
    }
}