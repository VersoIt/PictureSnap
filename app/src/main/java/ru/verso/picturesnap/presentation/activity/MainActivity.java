package ru.verso.picturesnap.presentation.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.databinding.ActivityMainBinding;
import ru.verso.picturesnap.presentation.viewmodel.MainActivityViewModel;
import ru.verso.picturesnap.presentation.viewmodel.factory.MainActivityViewModelFactory;

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
        MainActivityViewModel viewModel = new ViewModelProvider(this, new MainActivityViewModelFactory(new RoleRepositoryImpl(this)))
                .get(MainActivityViewModel.class);

        Class<? extends AppCompatActivity> activityToNavigate = viewModel.getClassToNavigate();

        Intent intent = new Intent(this, activityToNavigate);
        startActivity(intent);
    }
}
