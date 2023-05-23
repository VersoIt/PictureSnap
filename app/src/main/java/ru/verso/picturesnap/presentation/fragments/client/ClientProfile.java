package ru.verso.picturesnap.presentation.fragments.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.ClientRepositoryImpl;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserAuthDataRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.databinding.FragmentClientProfileBinding;
import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.usecase.GetClientDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.presentation.activity.MainActivity;
import ru.verso.picturesnap.presentation.dialogs.SignOutDialogFragment;
import ru.verso.picturesnap.presentation.factory.ClientMainProfileViewModelFactory;
import ru.verso.picturesnap.presentation.utils.LocationCoordinator;
import ru.verso.picturesnap.presentation.viewmodel.client.ClientMainProfileViewModel;

public class ClientProfile extends Fragment {

    private FragmentClientProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentClientProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ClientMainProfileViewModel clientMainProfileViewModel = getViewModel();

        SignOutDialogFragment signOutDialogFragment = new SignOutDialogFragment((dialog, which) -> {
            clientMainProfileViewModel.signOut();
            goToMainActivity();
            requireActivity().finish();
        });

        bindViews(clientMainProfileViewModel, signOutDialogFragment);
    }

    private void bindViews(ClientMainProfileViewModel clientMainProfileViewModel, DialogFragment dialogFragment) {

        clientMainProfileViewModel.getClientEmail().observe(getViewLifecycleOwner(), binding.linearLayoutFieldsContainer.textViewEmail::setText);
        clientMainProfileViewModel.getClientName().observe(getViewLifecycleOwner(), binding.textViewProfileName::setText);

        clientMainProfileViewModel.getAvatarPath().observe(getViewLifecycleOwner(), path -> Glide.with(binding.imageViewAvatar.getContext())
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_person_gray)
                .into(binding.imageViewAvatar));


        Location userLocation = clientMainProfileViewModel.getCityLocation();
        binding.linearLayoutFieldsContainer.textViewLocation.setText(LocationCoordinator.getCityNameByLocation(requireActivity(), userLocation.getLatitude(), userLocation.getLongitude()));

        binding.appCompatButtonSignOut.appCompatButtonLeave.setOnClickListener(view -> {
            Fragment fragment = requireActivity().getSupportFragmentManager().findFragmentByTag(SignOutDialogFragment.TAG);
            if (fragment == null) {
                dialogFragment.show(requireActivity().getSupportFragmentManager(), SignOutDialogFragment.TAG);
            }
        });

        ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        clientMainProfileViewModel.updateAvatar(result);
                    }
                }
        );
        binding.imageViewAvatar.setOnClickListener(view -> imagePickerLauncher.launch("image/*"));
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this.getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private ClientMainProfileViewModel getViewModel() {

        return new ViewModelProvider(requireActivity(), new ClientMainProfileViewModelFactory(new GetUserDataUseCase(
                new UserLocationRepositoryImpl(requireContext()),
                new RoleRepositoryImpl(requireContext()),
                new FirstTimeWentRepositoryImpl(requireContext()),
                new UserAuthDataRepositoryImpl()
        ), new GetClientDataUseCase(new ClientRepositoryImpl()))).get(ClientMainProfileViewModel.class);
    }
}