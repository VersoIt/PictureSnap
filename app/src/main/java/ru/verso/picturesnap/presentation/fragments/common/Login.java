package ru.verso.picturesnap.presentation.fragments.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentLoginBinding;

public class Login extends Fragment {

    private FragmentLoginBinding binding;

    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase database;

    private DatabaseReference reference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("Users");

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController contentNavController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView_content);

        firebaseAuth = FirebaseAuth.getInstance();

        binding.textViewForgetPassword.setOnClickListener(v ->
                contentNavController.navigate(R.id.action_login_to_passwordRecover)
        );

        binding.buttonLogin.buttonLogin.setOnClickListener(v ->
                verify());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void verify() {
        if (!isAllFilled()) {
            Toast.makeText(requireContext(), R.string.fill_input, Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(binding.editTextEmail.getText().toString(),
                binding.editTextPassword.getText().toString());
    }

    private boolean isAllFilled() {
        return (!binding.editTextEmail.getText().toString().isEmpty() &&
                !binding.editTextPassword.getText().toString().isEmpty());
    }
}