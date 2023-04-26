package ru.verso.picturesnap.presentation.fragments.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentUnregisteredWelcomeBinding;
import ru.verso.picturesnap.presentation.bottomsheet.ClientBottomSheetDialogFragment;

public class UnregisteredWelcome extends Fragment {

    private FragmentUnregisteredWelcomeBinding binding;

    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        navController = getNavController();

        binding = FragmentUnregisteredWelcomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        Fragment fragment = requireActivity().getSupportFragmentManager().findFragmentByTag(ClientBottomSheetDialogFragment.TAG);

        binding.linearLayoutAuthButtons.includeLoginButton.buttonLogin.setOnClickListener(v -> {
            if (fragment instanceof BottomSheetDialogFragment) {
                BottomSheetDialogFragment bottomSheetDialogFragment = (BottomSheetDialogFragment) fragment;
                bottomSheetDialogFragment.dismiss();
            }
            navController.navigate(R.id.login);
        });

        binding.linearLayoutAuthButtons.includeSignupButton.buttonSignup.setOnClickListener(v -> {
            if (fragment instanceof BottomSheetDialogFragment) {
                BottomSheetDialogFragment bottomSheetDialogFragment = (BottomSheetDialogFragment) fragment;
                bottomSheetDialogFragment.dismiss();
            }
            navController.navigate(R.id.user_selection);
        });
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }
}