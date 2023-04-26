package ru.verso.picturesnap.presentation.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.databinding.ActivityClientBinding;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.usecase.OperationPhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.OperationUserDataUseCase;
import ru.verso.picturesnap.presentation.bottomsheet.ClientBottomSheetDialogFragment;
import ru.verso.picturesnap.presentation.viewmodel.ClientActivityViewModel;
import ru.verso.picturesnap.presentation.viewmodel.factory.ClientActivityViewModelFactory;

public class ClientActivity extends AppCompatActivity implements LocationListener {

    private ActivityClientBinding binding;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private ClientActivityViewModel viewModel;

    private static final String NO_LOCATION = "";

    private NavController navController;

    private LocationManager locationManager;

    private ClientState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        viewModel = new ViewModelProvider(this, new ClientActivityViewModelFactory(
                new OperationUserDataUseCase(
                        new RoleRepositoryImpl(this),
                        new UserLocationRepositoryImpl(this),
                        new FirstTimeWentRepositoryImpl(this)
                ),
                new OperationPhotographDataUseCase(
                        new PhotographRepositoryImpl(this.getApplication()))))
                .get(ClientActivityViewModel.class);

        RoleRepository.Role role = viewModel.getCurrentRole();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        saveCity(locationManager);

        if (viewModel.isFirst()) {
            showBottomSheetDialog();
            viewModel.setVisited();
        }

        navController = getNavController();
        state = getStateByRole(role, navController);
        bindByState(state);
    }

    private ClientState getStateByRole(RoleRepository.Role role,
                                       NavController navController) {

        if (role == RoleRepository.Role.CLIENT)
            return new ClientRegisteredState(binding, navController);
        else
            return new UnregisteredState(binding, navController);
    }

    private void bindByState(ClientState state) {
        state.bindBottomNavigationView();
        state.bindNavigationViewMenu();
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }

    private void bindView() {
        binding = ActivityClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onLocationChanged(Location location) {
        viewModel.setLocation(getCityFromLocation(location.getLatitude(),
                location.getLongitude()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                navController.popBackStack();
                navController.navigate(state.getMainFragmentId());
                saveCity(locationManager);
            } else {
                Toast.makeText(this, getResources().getText(R.string.city_data_not_received), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveCity(LocationManager locationManager) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        int LOCATION_MIN_TIME_UPDATE = 360_000;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_MIN_TIME_UPDATE, 0, this);

        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null) {
            viewModel.setLocation(getCityFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));
            getCityFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        }
    }

    private String getCityFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        StringBuilder cityName = new StringBuilder();
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                for (int idxNum = 0; idxNum < address.getMaxAddressLineIndex(); ++idxNum) {
                    cityName.append(address.getAddressLine(idxNum));
                }
                cityName = new StringBuilder(address.getLocality());
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return cityName.toString();
    }

    public void showBottomSheetDialog() {
        ClientBottomSheetDialogFragment clientBottomSheet = new ClientBottomSheetDialogFragment(R.id.unregistered_welcome);
        clientBottomSheet.show(getSupportFragmentManager(), ClientBottomSheetDialogFragment.TAG);
    }

}
