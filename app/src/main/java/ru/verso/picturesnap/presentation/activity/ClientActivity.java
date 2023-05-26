package ru.verso.picturesnap.presentation.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
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

import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.ClientRepositoryImpl;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserAuthDataRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.data.storage.datasources.firebase.ClientFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.UserAuthFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.room.RoleRoomDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.FirstTimeWentSharedPrefsDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.UserLocationSharedPrefsDataSource;
import ru.verso.picturesnap.databinding.ActivityClientBinding;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.usecase.GetClientDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.activity.states.ClientActivityState;
import ru.verso.picturesnap.presentation.activity.states.RegisteredActivityState;
import ru.verso.picturesnap.presentation.activity.states.UnregisteredActivityState;
import ru.verso.picturesnap.presentation.bottomsheet.ClientBottomSheetDialogFragment;
import ru.verso.picturesnap.presentation.dialogs.SignOutDialogFragment;
import ru.verso.picturesnap.presentation.factory.ClientMainViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.client.ClientMainViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.ClientActivityViewModel;
import ru.verso.picturesnap.presentation.factory.ClientActivityViewModelFactory;

public class ClientActivity extends AppCompatActivity {

    private ActivityClientBinding binding;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private ClientActivityViewModel viewModel;

    private LocationManager locationManager;

    private ClientActivityLocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();

        setSupportActionBar(binding.toolbar);

        viewModel = getViewModel();
        locationListener = new ClientActivityLocationListener(viewModel);

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
                new UpdateUserDataUseCase(new RoleRepositoryImpl(new RoleRoomDataSource(this)),
                        new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(this)),
                        new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(this)))
                , new GetUserDataUseCase(new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(this)),
                new RoleRepositoryImpl(new RoleRoomDataSource(this)),
                new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(this)),
                new UserAuthDataRepositoryImpl(new UserAuthFirebaseDataSource()))))
                .get(ClientActivityViewModel.class);
    }

    private void setUpMenus(NavController navController, ClientActivityViewModel viewModel) {
        RoleRepository.Role role = viewModel.getCurrentRole();

        ClientActivityState clientActivityState = getActivityStateByRole(navController, role, viewModel);
        navController.navigate(clientActivityState.getStartFragmentId());
        clientActivityState.createBottomNavigationMenu();
        clientActivityState.createLeftMenu();

        setupToolbar();
    }

    private ClientActivityState getActivityStateByRole(NavController navController, RoleRepository.Role role, ClientActivityViewModel clientActivityViewModel) {

        if (role == RoleRepository.Role.UNREGISTERED)
            return new UnregisteredActivityState(binding, navController);
        else {
            RegisteredActivityState registeredActivityState = new RegisteredActivityState(this, binding, navController, getClientMainViewModel());
            registeredActivityState.setSignOutDialogAndParams(new SignOutDialogFragment((dialog, which) -> {
                clientActivityViewModel.signOut();
                goToMainActivity();
            }),
                    getSupportFragmentManager());
            return registeredActivityState;
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }

    private ClientMainViewModel getClientMainViewModel() {

        return new ViewModelProvider(this, new ClientMainViewModelFactory(new GetUserDataUseCase(
                new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(this)),
                new RoleRepositoryImpl(new RoleRoomDataSource(this)),
                new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(this)),
                new UserAuthDataRepositoryImpl(new UserAuthFirebaseDataSource())),
                new GetClientDataUseCase(new ClientRepositoryImpl(new ClientFirebaseDataSource())))).get(ClientMainViewModel.class);
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

        final int LOCATION_MIN_TIME_UPDATE = 360_000;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_MIN_TIME_UPDATE, 0, locationListener);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null) {
            viewModel.saveLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        }
    }

    public void showBottomSheetDialog() {
        ClientBottomSheetDialogFragment clientBottomSheet = new ClientBottomSheetDialogFragment(R.id.unregistered_welcome);
        clientBottomSheet.show(getSupportFragmentManager(), ClientBottomSheetDialogFragment.TAG);
    }
}
