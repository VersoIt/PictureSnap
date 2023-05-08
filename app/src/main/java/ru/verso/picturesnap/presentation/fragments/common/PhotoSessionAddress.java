package ru.verso.picturesnap.presentation.fragments.common;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.TextStyle;
import com.yandex.runtime.image.ImageProvider;

import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentPhotoSessionAddressBinding;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotoSessionAddressViewModel;
import ru.verso.picturesnap.presentation.utils.LocationCoordinator;

public class PhotoSessionAddress extends Fragment {

    private FragmentPhotoSessionAddressBinding binding;

    private static final float LABEL_SIZE = 10;

    private static final int PURPLE_PRIMARY = Color.rgb(124, 21, 235);

    private static final float MAP_ZOOM = 18.5f;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotoSessionAddressBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PhotoSessionAddressViewModel viewModel = getViewModel();

        MapKitFactory.initialize(requireActivity().getApplicationContext());
        initMap(viewModel);

        NavController navController = getNavController();
        binding.layoutReadyButton.buttonReady.setOnClickListener(v ->
                navController.navigateUp());
    }

    private void initMap(PhotoSessionAddressViewModel viewModel) {

        viewModel.getPhotoSessionLocation().observe(getViewLifecycleOwner(), location -> {

            binding.mapView.getMap().move(
                    new CameraPosition(new Point(location.getLatitude(), location.getLongitude()), MAP_ZOOM, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 0),
                    null
            );

            String fullAddress = LocationCoordinator.getFullAddress(requireContext(), location.getLatitude(), location.getLongitude());

            MapObjectCollection mapObjectCollection = binding.mapView.getMap().getMapObjects();
            PlacemarkMapObject placeMark = mapObjectCollection.addPlacemark(new Point(location.getLatitude(), location.getLongitude()));
            placeMark.setIcon(ImageProvider.fromResource(requireContext(), R.mipmap.ic_photosession_location));

            placeMark.setTextStyle(new TextStyle()
                    .setOffsetFromIcon(true)
                    .setOutlineColor(Color.WHITE)
                    .setColor(PURPLE_PRIMARY)
                    .setSize(LABEL_SIZE));
            placeMark.setText(fullAddress);
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

    private PhotoSessionAddressViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(PhotoSessionAddressViewModel.class);
    }
}