package ru.verso.picturesnap.presentation.fragments.common;

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

import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentLoginBinding;

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

        NavController toolbarNavController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView_tool_bar);
        toolbarNavController.navigate(R.id.unregisteredToolbarBackable);

        NavController contentNavController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView_content);

        binding.textViewForgetPassword.setOnClickListener(v ->
                contentNavController.navigate(R.id.action_login_to_passwordRecover)
        );
    }
}