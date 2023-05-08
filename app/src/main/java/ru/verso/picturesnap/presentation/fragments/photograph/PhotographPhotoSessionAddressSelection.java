package ru.verso.picturesnap.presentation.fragments.photograph;

import android.content.res.Resources;
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

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.runtime.image.ImageProvider;

import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentPhotographPhotoSessionAddressSelectionBinding;
import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.presentation.utils.LocationCoordinator;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographPhotoSessionAddressSelectionViewModel;

public class PhotographPhotoSessionAddressSelection extends Fragment {

    private FragmentPhotographPhotoSessionAddressSelectionBinding binding;

    private static final float MAP_ZOOM = 18f;

    private static boolean isFirstOpen = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotographPhotoSessionAddressSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private PhotographPhotoSessionAddressSelectionViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(PhotographPhotoSessionAddressSelectionViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapKitFactory.initialize(requireActivity().getApplicationContext());
        PhotographPhotoSessionAddressSelectionViewModel viewModel = getViewModel();
        initMap(viewModel);

        NavController navController = getNavController();
        binding.layoutReadyButton.buttonReady.setOnClickListener(v -> {
            saveLocationInViewModel(viewModel);
            navController.navigateUp();
        });
    }

    private void saveLocationInViewModel(PhotographPhotoSessionAddressSelectionViewModel viewModel) {

        String address = binding.editTextAddressSearch.getText().toString();
        if (!address.isEmpty() && address.trim().length() > 0) {
            LocationCoordinator.getLocationByString(requireContext(),
                    address).observe(requireActivity(), location -> {

                if (location.isInvalid()) {
                    Resources resources = binding.getRoot().getResources();
                    viewModel.setLocationString(resources.getString(R.string.invalid_address));
                    isFirstOpen = true;
                    return;
                }

                viewModel.setLocation(location);
                viewModel.setLocationString(address);
            });
        }
    }

    private void clearMap() {
        MapObjectCollection mapObjects = binding.mapView.getMap().getMapObjects();
        mapObjects.clear();
    }

    private void initMap(PhotographPhotoSessionAddressSelectionViewModel viewModel) {
        viewModel.getLocationString().observe(getViewLifecycleOwner(), location -> binding.editTextAddressSearch.setText(location));

        viewModel.getLocation().observe(getViewLifecycleOwner(), location -> {
            if (location != null && !location.isInvalid()) {
                viewModel.setLocationString(binding.editTextAddressSearch.getText().toString());
                if (!isFirstOpen) {
                    clearMap();
                    showLocationInMap(location, viewModel);
                }
                isFirstOpen = false;

                moveCameraMapTo(location);
            }
        });

        binding.buttonSearch.setOnClickListener(view -> {

            if (!binding.editTextAddressSearch.getText().toString().isEmpty()) {
                clearMap();
                showLocationInMap(binding.editTextAddressSearch.getText().toString(), viewModel);
            }
        });
    }

    private void moveCameraMapTo(Location location) {
        binding.mapView.getMap().move(
                new CameraPosition(new Point(location.getLatitude(), location.getLongitude()), MAP_ZOOM, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
    }

    private void showLocationInMap(Location location, PhotographPhotoSessionAddressSelectionViewModel viewModel) {

        binding.mapView.getMap().move(
                new CameraPosition(new Point(location.getLatitude(), location.getLongitude()), MAP_ZOOM, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null
        );

        MapObjectCollection mapObjectCollection = binding.mapView.getMap().getMapObjects();
        PlacemarkMapObject placeMark = mapObjectCollection.addPlacemark(new Point(location.getLatitude(), location.getLongitude()));
        placeMark.setIcon(ImageProvider.fromResource(requireContext(), R.mipmap.ic_photosession_location));
    }

    private void showLocationInMap(String locationString, PhotographPhotoSessionAddressSelectionViewModel viewModel) {
        LocationCoordinator.getLocationByString(requireContext(),
                locationString).observe(getViewLifecycleOwner(), location -> {

            if (location.isInvalid()) {
                Toast.makeText(requireContext(), R.string.invalid_address, Toast.LENGTH_LONG).show();
                return;
            }

            viewModel.setLocation(location);
            viewModel.setLocationString(binding.editTextAddressSearch.getText().toString());

            showLocationInMap(location, viewModel);
        });
    }

    private NavController getNavController() {

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        return Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        binding.mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        MapKitFactory.getInstance().onStop();
        binding.mapView.onStop();
    }
}