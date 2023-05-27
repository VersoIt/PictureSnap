package ru.verso.picturesnap.presentation.fragments.photographer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.FeedbacksRepositoryImpl;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerPortfolioPicturesRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserAuthDataRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.data.storage.datasources.firebase.FeedbacksFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.PhotographerFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.PhotographerPortfolioPicturesFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.UserAuthFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.room.RoleRoomDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.FirstTimeWentSharedPrefsDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.UserLocationSharedPrefsDataSource;
import ru.verso.picturesnap.databinding.FragmentPhotographerProfileBinding;
import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.usecase.GetFeedbacksDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerPicturesUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.SendPhotographerPicturesUseCase;
import ru.verso.picturesnap.presentation.activity.MainActivity;
import ru.verso.picturesnap.presentation.bottomsheet.ClientBottomSheetDialogFragment;
import ru.verso.picturesnap.presentation.dialogs.SignOutDialogFragment;
import ru.verso.picturesnap.presentation.factory.AboutPhotographerFromClientViewModelFactory;
import ru.verso.picturesnap.presentation.factory.FeedbackViewModelFactory;
import ru.verso.picturesnap.presentation.factory.PhotographerProfileMainViewModelFactory;
import ru.verso.picturesnap.presentation.factory.ServicesViewModelFactory;
import ru.verso.picturesnap.presentation.utils.LocationCoordinator;
import ru.verso.picturesnap.presentation.utils.StringConverter;
import ru.verso.picturesnap.presentation.viewmodel.photographer.PhotographerProfileMainViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.AboutPhotographerFromClientViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.FeedbackViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotoSessionAddressViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.ServicesViewModel;

public class PhotographerProfile extends Fragment {

    private FragmentPhotographerProfileBinding binding;

    private NavController navController;

    private SignOutDialogFragment signOutDialogFragment;

    private ActivityResultLauncher<String> imagePickerLauncher;

    private final Handler handler = new Handler();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotographerProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PhotographerProfileMainViewModel photographerProfileViewModel = getPhotographerProfileViewModel();
        photographerProfileViewModel.getPhotographer().observe(getViewLifecycleOwner(), photographer -> {
            updatePhoneNumber(photographer);
            updateName(photographer.getFirstName(), photographer.getLastName());
            updateEmail(photographer.getEmail());
            updateLocation(new Location(photographer.getLatitude(), photographer.getLongitude()));
            updateServices(photographer);
            updateAboutPhotographerButton(photographer);
            updatePortfolio(navController);
            sendPhotographerIdToFeedbacksFragment(photographer.getId());
            updateAvatar(photographer);
            updateFeedbacks(navController);
        });

        signOutDialogFragment = new SignOutDialogFragment((dialog, which) -> {
            photographerProfileViewModel.signOut();
            goToMainActivity();
        });

        navController = getNavController();
        bindSignOutButton();

        bindImageViewAvatar(photographerProfileViewModel);
    }

    private void bindImageViewAvatar(PhotographerProfileMainViewModel photographerProfileViewModel) {

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        photographerProfileViewModel.loadNewImage(result);
                    }
                }
        );

        binding.imageViewAvatar.setOnClickListener(view -> imagePickerLauncher.launch("image/*"));
    }

    private void goToMainActivity() {
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    private void updateAvatar(Photographer photographer) {

        Glide.with(binding.imageViewAvatar.getContext())
                .load(photographer.getAvatarPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_person_gray)
                .into(binding.imageViewAvatar);
    }

    private PhotographerProfileMainViewModel getPhotographerProfileViewModel() {

        return new ViewModelProvider(requireActivity(), new PhotographerProfileMainViewModelFactory(
                new GetPhotographerDataUseCase(
                        new PhotographerRepositoryImpl(new PhotographerFirebaseDataSource())),
                new GetUserDataUseCase(
                        new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(requireContext())),
                        new RoleRepositoryImpl(new RoleRoomDataSource(requireContext())),
                        new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(requireContext())),
                        new UserAuthDataRepositoryImpl(new UserAuthFirebaseDataSource())))).get(PhotographerProfileMainViewModel.class);
    }

    private void updateName(String firstName, String lastName) {
        String photographerName = String.format("%s %s", firstName, lastName);
        binding.textViewProfileName.setText(photographerName);
    }

    private void updateEmail(String email) {
        binding.linearLayoutFieldsContainer.textViewEmail.setText(email);
    }

    private void updatePhoneNumber(Photographer photographer) {
        binding.linearLayoutFieldsContainer.textViewPhoneNumber.setText(StringConverter.convertPhoneNumberToConvenientFormat(photographer.getPhoneNumber()));
    }

    private void updateLocation(Location location) {

        showLocation(location);

        binding.linearLayoutFieldsContainer.textViewLocation.setOnClickListener(view -> {
            navController.navigate(R.id.action_photographerProfile_to_photoSessionAddressFromPhotographer);
            sendPhotoSessionLocationToLocationFragment(location);
        });
    }

    private void sendPhotoSessionLocationToLocationFragment(Location location) {

        new ViewModelProvider(requireActivity())
                .get(PhotoSessionAddressViewModel.class)
                .putPhotoSessionAddress(location);
    }

    private void sendPhotographerIdToFeedbacksFragment(String id) {

        new ViewModelProvider(requireActivity(),
                new FeedbackViewModelFactory(
                        new GetFeedbacksDataUseCase(
                                new FeedbacksRepositoryImpl(new FeedbacksFirebaseDataSource()))))
                .get(FeedbackViewModel.class).putPhotographerId(id);
    }

    private void updateFeedbacks(NavController navController) {
        binding.linearLayoutFieldsContainer.textViewFeedbacks.setOnClickListener(view -> navController.navigate(R.id.action_photographerProfile_to_feedbacksFromPhotographer2));
    }

    private void updateServices(Photographer photographer) {
        sendPhotographerIdToServicesDialog(photographer.getId());

        binding.linearLayoutFieldsContainer.textViewServices.setOnClickListener(view -> showBottomSheetDialog(R.id.photographerServicesBottomSheet));
    }

    private void updateAboutPhotographerButton(Photographer photographer) {
        binding.linearLayoutFieldsContainer.textViewAboutPhotographer.setOnClickListener(view -> {
            sendPhotographerIdToAboutPhotographerViewModel(photographer.getId());
            navController.navigate(R.id.action_photographerProfile_to_aboutPhotographerFromPhotographer);
        });
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView_content);
        return Objects.requireNonNull(navHostFragment).getNavController();
    }

    private void sendPhotographerIdToAboutPhotographerViewModel(String photographerId) {
        new ViewModelProvider(requireActivity(), new AboutPhotographerFromClientViewModelFactory(
                new GetPhotographerDataUseCase(
                        new PhotographerRepositoryImpl(new PhotographerFirebaseDataSource()))))
                .get(AboutPhotographerFromClientViewModel.class)
                .putPhotographerId(photographerId);
    }

    private void sendPhotographerIdToServicesDialog(String photographerId) {

        new ViewModelProvider(requireActivity(), new ServicesViewModelFactory(
                new GetPhotographerDataUseCase(
                        new PhotographerRepositoryImpl(new PhotographerFirebaseDataSource())),
                new GetPhotographerPicturesUseCase(new PhotographerPortfolioPicturesRepositoryImpl(new PhotographerPortfolioPicturesFirebaseDataSource())),
                new SendPhotographerPicturesUseCase(new PhotographerPortfolioPicturesRepositoryImpl(new PhotographerPortfolioPicturesFirebaseDataSource()))))
                .get(ServicesViewModel.class)
                .putPhotographerId(photographerId);
    }

    private void bindSignOutButton() {
        binding.appCompatButtonSignOut.appCompatButtonLeave.setOnClickListener(view -> {
            Fragment fragment = requireActivity().getSupportFragmentManager().findFragmentByTag(SignOutDialogFragment.TAG);
            if (fragment == null)
                signOutDialogFragment.show(requireActivity().getSupportFragmentManager(), SignOutDialogFragment.TAG);
        });
    }

    private void updatePortfolio(NavController navController) {

        binding.linearLayoutFieldsContainer.textViewPortfolio.setOnClickListener(view ->
                navController.navigate(R.id.action_photographerProfile_to_photographerPortfolioFromPhotographer));
    }

    public void showBottomSheetDialog(int fragmentId) {
        ClientBottomSheetDialogFragment clientBottomSheet = new ClientBottomSheetDialogFragment(fragmentId);
        clientBottomSheet.show(requireActivity().getSupportFragmentManager(), ClientBottomSheetDialogFragment.TAG);
    }

    private void showLocation(Location photographerLocation) {

        new Thread(() -> {
            String location = LocationCoordinator.getFullAddress(binding.getRoot().getContext(), photographerLocation.getLatitude(), photographerLocation.getLongitude());
            handler.post(() -> binding.linearLayoutFieldsContainer.textViewLocation.setText(location));
        }).start();
    }
}