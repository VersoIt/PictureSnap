package ru.verso.picturesnap.presentation.fragments.photograph;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentPhotographRegistrationBinding;
import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.presentation.viewmodel.PhotographPhotoSessionAddressSelectionViewModel;

public class PhotographRegistration extends Fragment {

    private FragmentPhotographRegistrationBinding binding;

    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");

        binding = FragmentPhotographRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.buttonRegistration.buttonSignup.setOnClickListener(v -> {
            verify();
        });

        PhotographPhotoSessionAddressSelectionViewModel photographPhotoSessionAddressSelectionViewModel = getPhotoSessionSelectionViewModel();
        NavController navController = getNavController();

        photographPhotoSessionAddressSelectionViewModel.getLocationString().observe(getViewLifecycleOwner(), location ->
                binding.textViewAddress.setText(location));

        buildSpinner();

        initAddressView(navController);
        initServicesSelection(navController);
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

    private boolean isFilledInputs() {
        return (!binding.editTextFirstName.getText().toString().isEmpty() &&
                !binding.editTextLastName.getText().toString().isEmpty() &&
                !binding.editTextEmail.getText().toString().isEmpty() &&
                !binding.editTextPassword.getText().toString().isEmpty() &&
                !binding.editTextConfirmPassword.getText().toString().isEmpty() &&
                !binding.editTextAbout.getText().toString().isEmpty() &&
                !binding.editTextPhoneNumber.getText().toString().isEmpty() &&
                !binding.textViewAddress.getText().toString().isEmpty()
        );
    }

    private void verify() {
        if (!isFilledInputs()) {
            Toast.makeText(requireContext(), R.string.fill_input, Toast.LENGTH_LONG).show();
            return;
        }

        if (!binding.editTextPassword.getText().toString().equals(binding.editTextConfirmPassword.getText().toString())) {
            Toast.makeText(requireContext(), R.string.passwords_not_equals, Toast.LENGTH_LONG).show();
            return;
        }

        Photograph.Builder photographBuilder = new Photograph.Builder();
        photographBuilder.setPhoneNumber(binding.editTextPhoneNumber.getText().toString());

        firebaseAuth.createUserWithEmailAndPassword(binding.editTextEmail.getText().toString(), binding.editTextPassword.getText().toString()).addOnCompleteListener(
                task -> {
                    if (task.isComplete())
                        databaseReference.child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).setValue(photographBuilder.create());
                    Toast.makeText(requireContext(), "df", Toast.LENGTH_LONG).show();
                }
        );
    }

    private void initServicesSelection(NavController navController) {
        binding.textViewServices.setOnClickListener(view -> {
            navController.navigate(R.id.action_photographRegistration_to_photographServicesSelection);
        });
    }

    private void initAddressView(NavController navController) {
        binding.textViewAddress.setOnClickListener(view -> {
            navController.navigate(R.id.action_photographRegistration_to_photographPhotoSessionAddressSelection);
        });
    }

    private PhotographPhotoSessionAddressSelectionViewModel getPhotoSessionSelectionViewModel() {
        return new ViewModelProvider(requireActivity()).get(PhotographPhotoSessionAddressSelectionViewModel.class);
    }
}