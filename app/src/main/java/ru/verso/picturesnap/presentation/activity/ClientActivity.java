package ru.verso.picturesnap.presentation.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.ActivityClientBinding;

public class ClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityClientBinding binding = ActivityClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        binding.bottomNavigationViewMenu.setOnItemSelectedListener(item -> {
            if (R.id.home == item.getItemId())
                navController.navigate(R.id.unregistered_home);

            if (R.id.profile == item.getItemId())
                navController.navigate(R.id.unregistered_profile);

            if (R.id.menu == item.getItemId())
                binding.getRoot().openDrawer(GravityCompat.END);

            return true;
        });
    }

}
