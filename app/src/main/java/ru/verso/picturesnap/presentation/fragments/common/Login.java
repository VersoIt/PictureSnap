package ru.verso.picturesnap.presentation.fragments.common;

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
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.SignInRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.data.storage.datasources.firebase.SignInFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.room.RoleRoomDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.FirstTimeWentSharedPrefsDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.UserLocationSharedPrefsDataSource;
import ru.verso.picturesnap.databinding.FragmentLoginBinding;
import ru.verso.picturesnap.domain.models.User;
import ru.verso.picturesnap.domain.repository.SignInCallback;
import ru.verso.picturesnap.domain.usecase.SignInUserUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.activity.MainActivity;
import ru.verso.picturesnap.presentation.factory.LoginViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.LoginViewModel;

public class Login extends Fragment {

    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController contentNavController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView_content);
        LoginViewModel loginViewModel = getViewModel();

        binding.textViewForgetPassword.setOnClickListener(v ->
                contentNavController.navigate(R.id.action_login_to_passwordRecover)
        );

        bindSignInButton(loginViewModel);

        observeLoginViewModel(loginViewModel);
        NavController navController = getNavController();

        setOnBackPressEvent(loginViewModel, navController);
    }

    private void bindSignInButton(LoginViewModel loginViewModel) {
        binding.buttonLogin.buttonLogin.setOnClickListener(v -> {
            save(loginViewModel);
            verify(loginViewModel);
        });
    }

    private void observeLoginViewModel(LoginViewModel loginViewModel) {

        loginViewModel.getEmail().observe(getViewLifecycleOwner(), email ->
                binding.editTextEmail.setText(email));

        loginViewModel.getPassword().observe(getViewLifecycleOwner(), password ->
                binding.editTextPassword.setText(password));
    }

    private void verify(LoginViewModel viewModel) {

        if (!viewModel.isHaveAllData()) {
            Toast.makeText(requireContext(), R.string.fill_input, Toast.LENGTH_LONG).show();
            return;
        }

        if (!viewModel.isEmailMatch()) {
            Toast.makeText(requireContext(), R.string.wrong_enter_email, Toast.LENGTH_LONG).show();
            return;
        }

        showLoading();

        viewModel.signIn(new SignInCallback<User>() {
            @Override
            public void onNotFoundUser() {
                Toast.makeText(requireContext(), R.string.user_not_found, Toast.LENGTH_LONG).show();
                hideLoading();
            }

            @Override
            public void onWrongPassword() {
                Toast.makeText(requireContext(), R.string.wrong_password, Toast.LENGTH_LONG).show();
                hideLoading();
            }

            @Override
            public void onNetworkError() {
                Toast.makeText(requireContext(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
                hideLoading();
            }
        }).observe(getViewLifecycleOwner(), user -> {
            if (user.getRole() != null) {
                viewModel.saveRoleInLocal(user);
                sendToMainActivity();
                requireActivity().finish();
            }
        });
    }

    private void sendToMainActivity() {
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void save(LoginViewModel viewModel) {
        viewModel.setEmail(binding.editTextEmail.getText().toString());
        viewModel.setPassword(binding.editTextPassword.getText().toString());
    }


    private LoginViewModel getViewModel() {

        return new ViewModelProvider(requireActivity(), new LoginViewModelFactory(new SignInUserUseCase(new SignInRepositoryImpl(new SignInFirebaseDataSource())),
                new UpdateUserDataUseCase(new RoleRepositoryImpl(new RoleRoomDataSource(requireContext())),
                        new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(requireContext())),
                        new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(requireContext())))))
                .get(LoginViewModel.class);
    }

    private NavController getNavController() {

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        return Objects.requireNonNull(navHostFragment).getNavController();
    }

    private void setOnBackPressEvent(LoginViewModel viewModel, NavController navController) {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                save(viewModel);
                navController.navigateUp();
            }
        });
    }

    private void showLoading() {
        updateLoadingView(false, View.VISIBLE);
    }

    private void hideLoading() {
        updateLoadingView(true, View.GONE);
    }

    private void updateLoadingView(boolean isActiveButton, int state) {
        binding.buttonLogin.buttonLogin.setEnabled(isActiveButton);
        binding.progressBarLoading.setVisibility(state);
    }
}