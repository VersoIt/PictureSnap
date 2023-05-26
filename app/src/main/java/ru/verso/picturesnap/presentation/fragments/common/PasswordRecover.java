package ru.verso.picturesnap.presentation.fragments.common;

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

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.PasswordRecoverRepositoryImpl;
import ru.verso.picturesnap.data.storage.datasources.firebase.PasswordRecoverFirebaseDataSource;
import ru.verso.picturesnap.databinding.FragmentPasswordRecoverBinding;
import ru.verso.picturesnap.domain.repository.PasswordResetCallback;
import ru.verso.picturesnap.domain.usecase.SendPasswordRecoverUseCase;
import ru.verso.picturesnap.presentation.factory.PasswordRecoverViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PasswordRecoverViewModel;

public class PasswordRecover extends Fragment {

    private FragmentPasswordRecoverBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPasswordRecoverBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PasswordRecoverViewModel passwordRecoverViewModel = getViewModel();
        NavController navController = getNavController();

        setOnBackPressEvent(passwordRecoverViewModel, navController);
        bindReadyButton(passwordRecoverViewModel, navController);
    }

    private void bindReadyButton(PasswordRecoverViewModel passwordRecoverViewModel, NavController navController) {

        binding.buttonReady.buttonReady.setOnClickListener(v -> {

            enableLoading();
            save(passwordRecoverViewModel);

            if (!passwordRecoverViewModel.sendPasswordReset(new PasswordResetCallback() {

                @Override
                public void onSuccess() {
                    Toast.makeText(requireActivity(), R.string.password_recover_sent, Toast.LENGTH_LONG).show();
                    passwordRecoverViewModel.clearEmail();
                    navController.navigateUp();
                }

                @Override
                public void onNotFoundEmail() {
                    Toast.makeText(requireActivity(), R.string.user_with_this_email_not_exists, Toast.LENGTH_LONG).show();
                    disableLoading();
                }

                @Override
                public void onNetworkError() {
                    Toast.makeText(requireActivity(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
                    disableLoading();
                }
            })) {
                Toast.makeText(requireActivity(), R.string.wrong_enter_email, Toast.LENGTH_LONG).show();
                disableLoading();
            }
        });
    }

    private PasswordRecoverViewModel getViewModel() {

        return new ViewModelProvider(requireActivity(),
                new PasswordRecoverViewModelFactory(
                        new SendPasswordRecoverUseCase(
                                new PasswordRecoverRepositoryImpl(new PasswordRecoverFirebaseDataSource()))))
                .get(PasswordRecoverViewModel.class);
    }

    private void setOnBackPressEvent(PasswordRecoverViewModel viewModel, NavController navController) {

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                save(viewModel);
                navController.navigateUp();
            }
        });
    }

    private void save(PasswordRecoverViewModel viewModel) {
        viewModel.setEmail(binding.editTextEmail.getText().toString());
    }

    private NavController getNavController() {

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }

    private void enableLoading() {
        setLoading(false, View.VISIBLE);
    }

    private void setLoading(boolean isButtonActive, int visibleCode) {
        binding.buttonReady.buttonReady.setEnabled(isButtonActive);
        binding.progressBarLoading.setVisibility(visibleCode);
    }

    private void disableLoading() {
        setLoading(true, View.GONE);
    }
}