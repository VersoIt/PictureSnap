package ru.verso.picturesnap.presentation.fragments.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentUnregisteredProfileBinding;

public class UnregisteredProfile extends Fragment {

    private FragmentUnregisteredProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUnregisteredProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView_content);

        binding.linearLayoutAuthButtons.includeLoginButton.buttonLogin.setOnClickListener(v ->
                navController.navigate(R.id.action_unregistered_profile_to_login));

        binding.linearLayoutAuthButtons.includeSignupButton.buttonSignup.setOnClickListener(v ->
                navController.navigate(R.id.action_profile_to_userSelection));
    }
}