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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.ClientRepositoryImpl;
import ru.verso.picturesnap.data.repository.FavoritesRepositoryImpl;
import ru.verso.picturesnap.data.repository.FeedbacksRepositoryImpl;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerPortfolioPicturesRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerRepositoryImpl;
import ru.verso.picturesnap.data.repository.RecordsRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserAuthDataRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.data.storage.datasources.firebase.ClientFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.FeedbacksFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.PhotographerFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.PhotographerPortfolioPicturesFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.RecordsFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.UserAuthFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.room.FavoritesRoomDataSource;
import ru.verso.picturesnap.data.storage.datasources.room.RoleRoomDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.FirstTimeWentSharedPrefsDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.UserLocationSharedPrefsDataSource;
import ru.verso.picturesnap.databinding.FragmentPhotographerProfileFromClientBinding;
import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.usecase.BookPhotographerUseCase;
import ru.verso.picturesnap.domain.usecase.GetClientDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetFavoritesDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetFeedbacksDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerPicturesUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.SendFeedbackUseCase;
import ru.verso.picturesnap.domain.usecase.SendPhotographerPicturesUseCase;
import ru.verso.picturesnap.presentation.bottomsheet.ClientBottomSheetDialogFragment;
import ru.verso.picturesnap.presentation.factory.AboutPhotographerFromClientViewModelFactory;
import ru.verso.picturesnap.presentation.factory.FavoritesViewModelFactory;
import ru.verso.picturesnap.presentation.factory.FeedbackViewModelFactory;
import ru.verso.picturesnap.presentation.factory.PhotographerBookViewModelFactory;
import ru.verso.picturesnap.presentation.factory.SendFeedbackViewModelFactory;
import ru.verso.picturesnap.presentation.factory.ServicesViewModelFactory;
import ru.verso.picturesnap.presentation.utils.LocationCoordinator;
import ru.verso.picturesnap.presentation.utils.StringConverter;
import ru.verso.picturesnap.presentation.viewmodel.client.PhotographerBookViewModel;
import ru.verso.picturesnap.presentation.viewmodel.client.SendFeedbackViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.AboutPhotographerFromClientViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.FavoritesViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.FeedbackViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotoSessionAddressViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographerProfileFromClientViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.ServicesViewModel;

public class PhotographerProfileFromClient extends Fragment {

    private FragmentPhotographerProfileFromClientBinding binding;

    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotographerProfileFromClientBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PhotographerProfileFromClientViewModel photographerProfileViewModel = getPhotographerProfileViewModel();
        photographerProfileViewModel.getPhotographer().observe(getViewLifecycleOwner(), photographer -> {
            updatePhoneNumber(photographer);
            updateName(photographer.getFirstName(), photographer.getLastName());
            updateEmail(photographer.getEmail());
            updateLocation(new Location(photographer.getLatitude(), photographer.getLongitude()));
            updateServices(photographer);
            updateAboutPhotographerButton(photographer);
            updatePortfolio(navController);
            updateFeedbacks(navController, getSendFeedbackViewModel(), photographer);
            sendPhotographerIdToFeedbacksFragment(photographer.getId());
            updateFavoriteButton(photographer, getFavoritesViewModel());
            updateAvatar(photographer);
        });

        navController = getNavController();
        bindBookButton();

        sendDataToPhotographerBookViewModel(photographerProfileViewModel);
    }

    private void sendDataToPhotographerBookViewModel(PhotographerProfileFromClientViewModel photographerProfileFromClientViewModel) {
        PhotographerBookViewModel viewModel = getPhotographerBookViewModel();
        viewModel.putClientId(photographerProfileFromClientViewModel.getClientId());

        photographerProfileFromClientViewModel.getPhotographer().observe(getViewLifecycleOwner(), photographer -> {
            if (photographer != null)
                viewModel.putPhotographerId(photographer.getId());
        });
    }

    private void bindBookButton() {

        binding.appCompatButtonBook.setOnClickListener(v -> navController.navigate(R.id.action_photographerProfileFromClient_to_photographerBook));
    }

    private void updateAvatar(Photographer photographer) {

        Glide.with(binding.imageViewAvatar.getContext())
                .load(photographer.getAvatarPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_person_gray)
                .into(binding.imageViewAvatar);
    }

    private PhotographerProfileFromClientViewModel getPhotographerProfileViewModel() {
        return new ViewModelProvider(requireActivity()).get(PhotographerProfileFromClientViewModel.class);
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
            navController.navigate(R.id.action_photographerProfileFromClient_to_photoSessionAddress);
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

    private void updateServices(Photographer photographer) {
        sendPhotographerIdToServicesDialog(photographer.getId());

        binding.linearLayoutFieldsContainer.textViewServices.setOnClickListener(view -> showBottomSheetDialog(R.id.photographerServicesBottomSheet));
    }

    private FavoritesViewModel getFavoritesViewModel() {

        return new ViewModelProvider(requireActivity(), new FavoritesViewModelFactory(
                new GetFavoritesDataUseCase(
                        new FavoritesRepositoryImpl(new FavoritesRoomDataSource(requireContext()))),
                new GetUserDataUseCase(new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(requireContext())), new RoleRepositoryImpl(new RoleRoomDataSource(requireContext())), new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(requireContext())), new UserAuthDataRepositoryImpl(new UserAuthFirebaseDataSource()))))
                .get(FavoritesViewModel.class);
    }

    private void updateAboutPhotographerButton(Photographer photographer) {
        binding.linearLayoutFieldsContainer.textViewAboutPhotographer.setOnClickListener(view -> {
            sendPhotographerIdToAboutPhotographerViewModel(photographer.getId());
            navController.navigate(R.id.action_photographerProfileFromClient_to_aboutPhotographerFromClient);
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

    private void updatePortfolio(NavController navController) {

        binding.linearLayoutFieldsContainer.textViewPortfolio.setOnClickListener(view ->
                navController.navigate(R.id.action_photographerProfileFromClient_to_photographerPortfolioFromClient));
    }

    private void updateFeedbacks(NavController navController, SendFeedbackViewModel sendFeedbackViewModel, Photographer photographer) {

        binding.linearLayoutFieldsContainer.textViewFeedbacks.setOnClickListener(view -> {
            sendFeedbackViewModel.putPhotographerDestinationId(photographer.getId());
            navController.navigate(R.id.action_photographerProfileFromClient_to_feedbacksFromClient);
        });
    }

    private SendFeedbackViewModel getSendFeedbackViewModel() {

        return new ViewModelProvider(requireActivity(), new SendFeedbackViewModelFactory(new SendFeedbackUseCase(new FeedbacksRepositoryImpl(new FeedbacksFirebaseDataSource())),
                new GetUserDataUseCase(new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(requireContext())),
                        new RoleRepositoryImpl(new RoleRoomDataSource(requireContext())),
                        new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(requireContext())),
                        new UserAuthDataRepositoryImpl(new UserAuthFirebaseDataSource())),
                new GetClientDataUseCase(new ClientRepositoryImpl(new ClientFirebaseDataSource())),
                new GetPhotographerDataUseCase(new PhotographerRepositoryImpl(new PhotographerFirebaseDataSource())),
                new GetFeedbacksDataUseCase(new FeedbacksRepositoryImpl(new FeedbacksFirebaseDataSource())))).get(SendFeedbackViewModel.class);
    }

    private PhotographerBookViewModel getPhotographerBookViewModel() {
        return new ViewModelProvider(requireActivity(), new PhotographerBookViewModelFactory(new BookPhotographerUseCase(new RecordsRepositoryImpl(new RecordsFirebaseDataSource())), new GetPhotographerDataUseCase(new PhotographerRepositoryImpl(new PhotographerFirebaseDataSource())))).get(PhotographerBookViewModel.class);
    }

    public void showBottomSheetDialog(int fragmentId) {
        ClientBottomSheetDialogFragment clientBottomSheet = new ClientBottomSheetDialogFragment(fragmentId);
        clientBottomSheet.show(requireActivity().getSupportFragmentManager(), ClientBottomSheetDialogFragment.TAG);
    }
}