package ru.verso.picturesnap.presentation.fragments.photograph;

import android.os.Bundle;
import android.util.Log;
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
import ru.verso.picturesnap.data.repository.PhotographRepositoryImpl;
import ru.verso.picturesnap.databinding.FragmentPhotographProfileFromUnregisteredBinding;
import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.usecase.GetFavoritesDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetFeedbackDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;
import ru.verso.picturesnap.presentation.bottomsheet.ClientBottomSheetDialogFragment;
import ru.verso.picturesnap.presentation.factory.AboutPhotographFromClientViewModelFactory;
import ru.verso.picturesnap.presentation.factory.FavoritesViewModelFactory;
import ru.verso.picturesnap.presentation.factory.FeedbackViewModelFactory;
import ru.verso.picturesnap.presentation.factory.ServicesViewModelFactory;
import ru.verso.picturesnap.presentation.utils.LocationCoordinator;
import ru.verso.picturesnap.presentation.viewmodel.AboutPhotographFromClientViewModel;
import ru.verso.picturesnap.presentation.viewmodel.FavoritesViewModel;
import ru.verso.picturesnap.presentation.viewmodel.FeedbackViewModel;
import ru.verso.picturesnap.presentation.viewmodel.PhotoSessionAddressViewModel;
import ru.verso.picturesnap.presentation.viewmodel.PhotographProfileViewModel;
import ru.verso.picturesnap.presentation.viewmodel.ServicesViewModel;

public class PhotographProfileFromUnregistered extends Fragment {

    private FragmentPhotographProfileFromUnregisteredBinding binding;

    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotographProfileFromUnregisteredBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PhotographProfileViewModel photographProfileViewModel = getPhotographProfileViewModel();
        photographProfileViewModel.getPhotograph().observe(getViewLifecycleOwner(), photograph -> {
            updateName(photograph.getFirstName(), photograph.getLastName());
            updateEmail(photograph.getEmail());
            updateLocation(new Location(photograph.getLatitude(), photograph.getLongitude()));
            updateServices(photograph);
            updateAboutPhotographButton(photograph);
            updatePortfolio(navController);
            updateFeedbacks(navController);
            sendPhotographIdToFeedbacksFragment(photograph.getId());
            updateFavoriteButton(photograph, getFavoritesViewModel());
        });

        navController = getNavController();
    }

    private PhotographProfileViewModel getPhotographProfileViewModel() {
        return new ViewModelProvider(requireActivity()).get(PhotographProfileViewModel.class);
    }

    private void updateName(String firstName, String lastName) {
        String photographName = String.format("%s %s", firstName, lastName);
        binding.textViewProfileName.setText(photographName);
    }

    private void updateEmail(String email) {
        binding.linearLayoutFieldsContainer.textViewEmail.setText(email);
    }

    private void updateFavoriteButton(Photograph photograph, FavoritesViewModel viewModel) {

        viewModel.getAllFavorites().observe(getViewLifecycleOwner(), photographs -> {

            binding.buttonFavorite.setOnClickListener(view -> {
                if (isFavorite(photograph, photographs)) {
                    binding.buttonFavorite.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_not_favorite));
                    viewModel.deleteFavorite(photograph);
                } else {
                    binding.buttonFavorite.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite));
                    viewModel.addFavorite(photograph);
                }
            });

            if (isFavorite(photograph, photographs)) {
                binding.buttonFavorite.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite));
                return;
            }

            binding.buttonFavorite.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_not_favorite));
        });
    }

    private boolean isFavorite(Photograph photograph, List<Photograph> photographs) {
        return photographs.stream().anyMatch(p -> p.getId().equals(photograph.getId()));
    }

    private void updateLocation(Location location) {

        binding.linearLayoutFieldsContainer.textViewLocation.setText(LocationCoordinator.getFullAddress(requireContext(), location.getLatitude(), location.getLongitude()));

        binding.linearLayoutFieldsContainer.textViewLocation.setOnClickListener(view -> {
            navController.navigate(R.id.action_photograph_profile_from_unregistered_to_photoSessionAddress);
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

    private void sendPhotographIdToFeedbacksFragment(String id) {
        new ViewModelProvider(requireActivity(),
                new FeedbackViewModelFactory(
                        new GetFeedbackDataUseCase(
                                new FeedbackRepositoryImpl())))
                .get(FeedbackViewModel.class).putPhotographId(id);
    }

    private void updateServices(Photograph photograph) {
        sendPhotographIdToServicesDialog(photograph.getId());

        binding.linearLayoutFieldsContainer.textViewServices.setOnClickListener(view -> {
            showBottomSheetDialog(R.id.photographServicesBottomSheet);
        });
    }

    private FavoritesViewModel getFavoritesViewModel() {

        return new ViewModelProvider(requireActivity(), new FavoritesViewModelFactory(
                new GetFavoritesDataUseCase(
                        new FavoritesRepositoryImpl(requireContext()))))
                .get(FavoritesViewModel.class);
    }

    private void updateAboutPhotographButton(Photograph photograph) {
        binding.linearLayoutFieldsContainer.textViewPhotograph.setOnClickListener(view -> {
            sendPhotographIdToAboutPhotographViewModel(photograph.getId());
            navController.navigate(R.id.action_photograph_profile_from_unregistered_to_aboutPhotographFromClient);
        });
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView_content);
        return Objects.requireNonNull(navHostFragment).getNavController();
    }

    private void sendPhotographIdToAboutPhotographViewModel(String photographId) {
        new ViewModelProvider(requireActivity(), new AboutPhotographFromClientViewModelFactory(
                new GetPhotographDataUseCase(
                        new PhotographRepositoryImpl())))
                .get(AboutPhotographFromClientViewModel.class)
                .putPhotographId(photographId);
    }

    private void sendPhotographIdToServicesDialog(String photographId) {
        new ViewModelProvider(requireActivity(), new ServicesViewModelFactory(
                new GetPhotographDataUseCase(
                        new PhotographRepositoryImpl())))
                .get(ServicesViewModel.class)
                .putPhotographId(photographId);
    }

    private void updatePortfolio(NavController navController) {
        binding.linearLayoutFieldsContainer.textViewPortfolio.setOnClickListener(view ->
                navController.navigate(R.id.action_photograph_profile_from_unregistered_to_photographPortfolioFromUnregistered));
    }

    private void updateFeedbacks(NavController navController) {

        binding.linearLayoutFieldsContainer.textViewFeedbacks.setOnClickListener(view ->
                navController.navigate(R.id.action_photograph_profile_from_unregistered_to_feedbackFromUnregistered));
    }

    public void showBottomSheetDialog(int fragmentId) {
        ClientBottomSheetDialogFragment clientBottomSheet = new ClientBottomSheetDialogFragment(fragmentId);
        clientBottomSheet.show(requireActivity().getSupportFragmentManager(), ClientBottomSheetDialogFragment.TAG);
    }
}