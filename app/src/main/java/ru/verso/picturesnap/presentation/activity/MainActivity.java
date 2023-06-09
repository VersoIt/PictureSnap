package ru.verso.picturesnap.presentation.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserAuthDataRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.data.storage.datasources.firebase.UserAuthFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.room.RoleRoomDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.FirstTimeWentSharedPrefsDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.UserLocationSharedPrefsDataSource;
import ru.verso.picturesnap.databinding.ActivityMainBinding;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.presentation.factory.MainActivityViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();
        navigateToSelectedActivityRole();

        finish();
    }

    private void bindView() {
        ru.verso.picturesnap.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void navigateToSelectedActivityRole() {

        MainActivityViewModel viewModel = new ViewModelProvider(this, new MainActivityViewModelFactory(new GetUserDataUseCase(
                new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(this)),
                new RoleRepositoryImpl(new RoleRoomDataSource(this)),
                new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(this)),
                new UserAuthDataRepositoryImpl(new UserAuthFirebaseDataSource()))))
                .get(MainActivityViewModel.class);

        Class<? extends AppCompatActivity> activityToNavigate = viewModel.getClassToNavigate();

        Intent intent = new Intent(this, activityToNavigate);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
