package ru.verso.picturesnap.presentation.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.ActivityUnregisteredBinding;

public class UnregisteredActivity extends AppCompatActivity {

    private ActivityUnregisteredBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUnregisteredBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = binding.bottomNavigationViewMenu;
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView_controller);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}
