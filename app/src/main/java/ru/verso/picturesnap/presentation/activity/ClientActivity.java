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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.databinding.ActivityClientBinding;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdatePhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.activity.states.ClientActivityState;
import ru.verso.picturesnap.presentation.activity.states.RegisteredActivityState;
import ru.verso.picturesnap.presentation.activity.states.UnregisteredActivityState;
import ru.verso.picturesnap.presentation.bottomsheet.ClientBottomSheetDialogFragment;
import ru.verso.picturesnap.presentation.viewmodel.ClientActivityViewModel;
import ru.verso.picturesnap.presentation.viewmodel.factory.ClientActivityViewModelFactory;

public class ClientActivity extends AppCompatActivity implements LocationListener {

    private ActivityClientBinding binding;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private ClientActivityViewModel viewModel;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();

        setSupportActionBar(binding.toolbar);

        viewModel = getViewModel();

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.overview);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        saveCity(locationManager);

        if (viewModel.isFirst()) {
            showBottomSheetDialog();
            viewModel.setVisited();
        }

        NavController navController = getNavController();
        setUpMenus(navController, viewModel);
    }

    private ClientActivityViewModel getViewModel() {
        return new ViewModelProvider(this, new ClientActivityViewModelFactory(
                new UpdateUserDataUseCase(new RoleRepositoryImpl(getApplicationContext()),
                        new UserLocationRepositoryImpl(getApplicationContext()),
                        new FirstTimeWentRepositoryImpl(getApplicationContext()))
                , new GetUserDataUseCase(new UserLocationRepositoryImpl(getApplicationContext()),
                new RoleRepositoryImpl(getApplicationContext()),
                new FirstTimeWentRepositoryImpl(getApplicationContext())),
                new UpdatePhotographDataUseCase(new PhotographRepositoryImpl(getApplicationContext())),
                new GetPhotographDataUseCase(new PhotographRepositoryImpl(getApplicationContext()))))
                .get(ClientActivityViewModel.class);
    }

    private void setUpMenus(NavController navController, ClientActivityViewModel viewModel) {
        RoleRepository.Role role = viewModel.getCurrentRole();
        ClientActivityState clientActivityState = getActivityStateByRole(navController, role);
        clientActivityState.createBottomNavigationMenu();
        clientActivityState.createLeftMenu();
        setupToolbar();
    }

    private ClientActivityState getActivityStateByRole(NavController navController, RoleRepository.Role role) {

        if (role == RoleRepository.Role.UNREGISTERED)
            return new UnregisteredActivityState(binding, navController);
        else
            return new RegisteredActivityState();
    }

    private void setupToolbar() {
        DrawerLayout drawerLayout = binding.drawerLayout;
        Toolbar toolbar = binding.toolbar;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.overview, R.string.overview);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
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
