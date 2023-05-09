package ru.verso.picturesnap.presentation.fragments.photographer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;
import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.FavoritesRepositoryImpl;
import ru.verso.picturesnap.data.repository.FeedbackRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerRepositoryImpl;
import ru.verso.picturesnap.databinding.FragmentPhotographerProfileFromUnregisteredBinding;
import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.usecase.GetFavoritesDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetFeedbackDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.presentation.bottomsheet.ClientBottomSheetDialogFragment;
import ru.verso.picturesnap.presentation.factory.AboutPhotographerFromClientViewModelFactory;
import ru.verso.picturesnap.presentation.factory.FavoritesViewModelFactory;
import ru.verso.picturesnap.presentation.factory.FeedbackViewModelFactory;
import ru.verso.picturesnap.presentation.factory.ServicesViewModelFactory;
import ru.verso.picturesnap.presentation.utils.LocationCoordinator;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.AboutPhotographerFromClientViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.FavoritesViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.FeedbackViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotoSessionAddressViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographerProfileViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.ServicesViewModel;

public class PhotographerProfileFromUnregistered extends Fragment {

    private FragmentPhotographerProfileFromUnregisteredBinding binding;

    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotographerProfileFromUnregisteredBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PhotographerProfileViewModel photographerProfileViewModel = getPhotographerProfileViewModel();
        photographerProfileViewModel.getPhotographer().observe(getViewLifecycleOwner(), photographer -> {
            updateName(photographer.getFirstName(), photographer.getLastName());
            updateEmail(photographer.getEmail());
            updateLocation(new Location(photographer.getLatitude(), photographer.getLongitude()));
            updateServices(photographer);
            updateAboutPhotographerButton(photographer);
            updatePortfolio(navController);
            updateFeedbacks(navController);
            sendPhotographerIdToFeedbacksFragment(photographer.getId());
            updateFavoriteButton(photographer, getFavoritesViewModel());
        });

        navController = getNavController();
    }

    private PhotographerProfileViewModel getPhotographerProfileViewModel() {
        return new ViewModelProvider(requireActivity()).get(PhotographerProfileViewModel.class);
    }

    private void updateName(String firstName, String lastName) {
        String photographerName = String.format("%s %s", firstName, lastName);
        binding.textViewProfileName.setText(photographerName);
    }

    private void updateEmail(String email) {
        binding.linearLayoutFieldsContainer.textViewEmail.setText(email);
    }

    private void updateFavoriteButton(Photographer photographer, FavoritesViewModel viewModel) {

        viewModel.getAllFavorites().observe(getViewLifecycleOwner(), photographers -> {

            binding.buttonFavorite.setOnClickListener(view -> {
                if (isFavorite(photographer, photographers)) {
                    binding.buttonFavorite.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_not_favorite));
                    viewModel.deleteFavorite(photographer);
                } else {
                    binding.buttonFavorite.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite));
                    viewModel.addFavorite(photographer);
                }
            });

            if (isFavorite(photographer, photographers)) {
                binding.buttonFavorite.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite));
                return;
            }

            binding.buttonFavorite.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_not_favorite));
        });
    }

    private boolean isFavorite(Photographer photographer, List<Photographer> photographers) {
        return photographers.stream().anyMatch(p -> p.getId().equals(photographer.getId()));
    }

    private void updateLocation(Location location) {

        binding.linearLayoutFieldsContainer.textViewLocation.setText(LocationCoordinator.getFullAddress(requireContext(), location.getLatitude(), location.getLongitude()));

        binding.linearLayoutFieldsContainer.textViewLocation.setOnClickListener(view -> {
            navController.navigate(R.id.action_photographer_profile_from_unregistered_to_photoSessionAddress);
            sendPhotoSessionLocationToLocationFragment(location);
        });
    }

    private String convertGeoCoordinatesToString(double latitude, double longitude) {
        return LocationCoordinator.getCityNameByLocation(getContext(), latitude, longitude);
    }

    private void sendPhotoSessionLocationToLocationFragment(Location location) {
        new ViewModelProvider(requireActivity())
                .get(PhotoSessionAddressViewModel.class)
                .putPhotoSessionAddress(location);
    }

    private void sendPhotographerIdToFeedbacksFragment(String id) {
        new ViewModelProvider(requireActivity(),
                new FeedbackViewModelFactory(
                        new GetFeedbackDataUseCase(
                                new FeedbackRepositoryImpl())))
                .get(FeedbackViewModel.class).putPhotographerId(id);
    }

    private void updateServices(Photographer photographer) {
        sendPhotographerIdToServicesDialog(photographer.getId());

        binding.linearLayoutFieldsContainer.textViewServices.setOnClickListener(view -> showBottomSheetDialog(R.id.photographerServicesBottomSheet));
    }

    private FavoritesViewModel getFavoritesViewModel() {

        return new ViewModelProvider(requireActivity(), new FavoritesViewModelFactory(
                new GetFavoritesDataUseCase(
                        new FavoritesRepositoryImpl(requireContext()))))
                .get(FavoritesViewModel.class);
    }

    private void updateAboutPhotographerButton(Photographer photographer) {
        binding.linearLayoutFieldsContainer.textViewPhotographer.setOnClickListener(view -> {
            sendPhotographerIdToAboutPhotographerViewModel(photographer.getId());
            navController.navigate(R.id.action_photographer_profile_from_unregistered_to_aboutPhotographerFromClient);
        });
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView_content);
        return Objects.requireNonNull(navHostFragment).getNavController();
    }

    private void sendPhotographerIdToAboutPhotographerViewModel(String photographerId) {
        new ViewModelProvider(requireActivity(), new AboutPhotographerFromClientViewModelFactory(
                new GetPhotographerDataUseCase(
                        new PhotographerRepositoryImpl())))
                .get(AboutPhotographerFromClientViewModel.class)
                .putPhotographerId(photographerId);
    }

    private void sendPhotographerIdToServicesDialog(String photographerId) {
        new ViewModelProvider(requireActivity(), new ServicesViewModelFactory(
                new GetPhotographerDataUseCase(
                        new PhotographerRepositoryImpl())))
                .get(ServicesViewModel.class)
                .putPhotographerId(photographerId);
    }

    private void updatePortfolio(NavController navController) {
        binding.linearLayoutFieldsContainer.textViewPortfolio.setOnClickListener(view ->
                navController.navigate(R.id.action_photographer_profile_from_unregistered_to_photographerPortfolioFromUnregistered));
    }

    private void updateFeedbacks(NavController navController) {

        binding.linearLayoutFieldsContainer.textViewFeedbacks.setOnClickListener(view ->
                navController.navigate(R.id.action_photographer_profile_from_unregistered_to_feedbackFromUnregistered));
    }

    public void showBottomSheetDialog(int fragmentId) {
        ClientBottomSheetDialogFragment clientBottomSheet = new ClientBottomSheetDialogFragment(fragmentId);
        clientBottomSheet.show(requireActivity().getSupportFragmentManager(), ClientBottomSheetDialogFragment.TAG);
    }
}