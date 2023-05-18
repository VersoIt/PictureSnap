package ru.verso.picturesnap.presentation.fragments.client;

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

import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.ClientSignUpRepositoryImpl;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.SignInRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.databinding.FragmentClientRegistrationBinding;
import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.repository.SignUpFailureCallback;
import ru.verso.picturesnap.domain.usecase.SignInUserUseCase;
import ru.verso.picturesnap.domain.usecase.SignUpNewClientUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.activity.MainActivity;
import ru.verso.picturesnap.presentation.factory.ClientRegistrationViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.ClientRegistrationViewModel;

public class ClientRegistration extends Fragment {

    private FragmentClientRegistrationBinding binding;

    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClientRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ClientRegistrationViewModel viewModel = getViewModel();
        bindSignUpButton(viewModel);

        observeClientRegistrationViewModel(viewModel);
        navController = getNavController();

        setOnBackPressEvent(viewModel);
    }

    private void setOnBackPressEvent(ClientRegistrationViewModel viewModel) {

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                save(viewModel);
                navController.navigateUp();
            }
        });
    }

    private void verify(ClientRegistrationViewModel viewModel) {

        if (!viewModel.isHaveAllData()) {
            Toast.makeText(requireContext(), R.string.fill_input, Toast.LENGTH_LONG).show();
            return;
        }

        if (!viewModel.isPasswordsMatch()) {
            Toast.makeText(requireContext(), R.string.passwords_not_equals, Toast.LENGTH_LONG).show();
            return;
        }

        if (!viewModel.isEmailMatch()) {
            Toast.makeText(requireContext(), R.string.wrong_enter_email, Toast.LENGTH_LONG).show();
            return;
        }

        if (!viewModel.isValidPassword()) {
            Toast.makeText(requireContext(), R.string.short_password, Toast.LENGTH_LONG).show();
            return;
        }

        if (!viewModel.isValidFirstName()) {
            Toast.makeText(requireContext(), R.string.invalid_first_name, Toast.LENGTH_LONG).show();
            return;
        }

        if (!viewModel.isValidLastName()) {
            Toast.makeText(requireContext(), R.string.invalid_last_name, Toast.LENGTH_LONG).show();
            return;
        }

        binding.buttonSignup.buttonSignup.setActivated(false);
        binding.progressBarLoading.setVisibility(View.VISIBLE);

        viewModel.signUpClient(new SignUpFailureCallback<Client>() {

            @Override
            public void onUserCollision() {
                Toast.makeText(requireActivity(), R.string.user_already_exists, Toast.LENGTH_LONG).show();
                binding.progressBarLoading.setVisibility(View.GONE);
                binding.buttonSignup.buttonSignup.setActivated(true);
            }

            @Override
            public void onNetworkError() {
                Toast.makeText(requireActivity(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
                binding.progressBarLoading.setVisibility(View.GONE);
                binding.buttonSignup.buttonSignup.setActivated(true);
            }
        }).observe(getViewLifecycleOwner(), client -> viewModel.signInClient().observe(getViewLifecycleOwner(), user -> {
            if (user.getRole() != null && client.getId() != null) {
                sendToMainActivity();
                requireActivity().finish();
            }
        }));
    }

    private void sendToMainActivity() {
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        startActivity(intent);
    }

    private NavController getNavController() {

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        return Objects.requireNonNull(navHostFragment).getNavController();
    }

    private void bindSignUpButton(ClientRegistrationViewModel viewModel) {
        binding.buttonSignup.buttonSignup.setOnClickListener(v -> {
            save(viewModel);
            verify(viewModel);
        });
    }

    private void observeClientRegistrationViewModel(ClientRegistrationViewModel clientRegistrationViewModel) {

        clientRegistrationViewModel.getFirstName().observe(getViewLifecycleOwner(), name ->
                binding.editTextFirstName.setText(name));

        clientRegistrationViewModel.getLastName().observe(getViewLifecycleOwner(), name ->
                binding.editTextLastName.setText(name));

        clientRegistrationViewModel.getEmail().observe(getViewLifecycleOwner(), email ->
                binding.editTextEmail.setText(email));

        clientRegistrationViewModel.getPassword().observe(getViewLifecycleOwner(), password ->
                binding.editTextPassword.setText(password));

        clientRegistrationViewModel.getPasswordConfirmation().observe(getViewLifecycleOwner(), confirmation ->
                binding.editTextPasswordConfirmation.setText(confirmation));
    }

    private void save(ClientRegistrationViewModel viewModel) {

        viewModel.setFirstName(binding.editTextFirstName.getText().toString());
        viewModel.setLastName(binding.editTextLastName.getText().toString());
        viewModel.setEmail(binding.editTextEmail.getText().toString());
        viewModel.setPassword(binding.editTextPassword.getText().toString());
        viewModel.setPasswordConfirmation(binding.editTextPasswordConfirmation.getText().toString());
    }

    private ClientRegistrationViewModel getViewModel() {

        return new ViewModelProvider(requireActivity(), new ClientRegistrationViewModelFactory(
                new SignUpNewClientUseCase(new ClientSignUpRepositoryImpl()),
                new SignInUserUseCase(new SignInRepositoryImpl()),
                new UpdateUserDataUseCase(new RoleRepositoryImpl(requireContext()),
                        new UserLocationRepositoryImpl(requireContext()),
                        new FirstTimeWentRepositoryImpl(requireContext()))
        )).get(ClientRegistrationViewModel.class);
    }
}