package ru.verso.picturesnap.presentation.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserAuthDataRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.databinding.ActivityPhotographerBinding;
import ru.verso.picturesnap.databinding.LayoutNavHeaderPhotographerBinding;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.presentation.factory.PhotographerActivityViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.PhotographerActivityViewModel;

public class PhotographerActivity extends AppCompatActivity {

    private ActivityPhotographerBinding binding;

    private Menu bottomNavigationViewMenu;

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();

        setSupportActionBar(binding.toolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.overview);

        PhotographerActivityViewModel viewModel = getViewModel();

        navController = getNavController();
        setUpMenus(navController, viewModel);
    }

    private PhotographerActivityViewModel getViewModel() {

        return new ViewModelProvider(this, new PhotographerActivityViewModelFactory(new GetPhotographerDataUseCase(new PhotographerRepositoryImpl()), new GetUserDataUseCase(
                new UserLocationRepositoryImpl(this),
                new RoleRepositoryImpl(this),
                new FirstTimeWentRepositoryImpl(this),
                new UserAuthDataRepositoryImpl()
        ))).get(PhotographerActivityViewModel.class);
    }

    private void setUpMenus(NavController navController, PhotographerActivityViewModel viewModel) {

        createBottomNavigationMenu();
        createLeftMenu(navController, viewModel);

        setupToolbar();
    }

    private void createBottomNavigationMenu() {
        binding.bottomNavigationViewMenu.inflateMenu(R.menu.photograph_bottom);

        binding.bottomNavigationViewMenu.setOnItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.photographer_main) {
                setFragmentMenu(R.id.photographerMain, PhotographerMenuItems.HOME);
                closeDrawer();

                return true;
            }

            if (menuItem.getItemId() == R.id.profile) {
                setFragmentMenu(R.id.photographerProfile, PhotographerMenuItems.PROFILE);
                closeDrawer();

                return true;
            }

            if (menuItem.getItemId() == R.id.records) {
                setFragmentMenu(R.id.clientsRecords, PhotographerMenuItems.CLIENTS_RECORDS);
                closeDrawer();

                return true;
            }

            return false;
        });
    }

    private void observeLeftMenu(View header, PhotographerActivityViewModel viewModel) {

        LayoutNavHeaderPhotographerBinding binding = LayoutNavHeaderPhotographerBinding.bind(header);

        viewModel.getPhotographerAvatar().observe(this, path -> Glide.with(binding.imageViewLogo.getContext())
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_person_gray)
                .into(binding.imageViewLogo));
        viewModel.getPhotographerEmail().observe(this, binding.textViewEmail::setText);
        viewModel.getPhotographerName().observe(this, binding.textViewPhotographer::setText);
    }

    private void createLeftMenu(NavController navController, PhotographerActivityViewModel viewModel) {

        binding.navigationViewMenu.inflateMenu(R.menu.photographer_left);
        binding.navigationViewMenu.getHeaderView(0).setOnClickListener(view -> {
            navController.navigate(R.id.photographerProfile);
            closeDrawer();
        });

        observeLeftMenu(binding.navigationViewMenu.getHeaderView(0), viewModel);

        binding.navigationViewMenu.setNavigationItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.nav_home) {
                setFragmentMenu(R.id.photographerMain, PhotographerMenuItems.HOME);

                closeDrawer();

                return true;
            }

            if (menuItem.getItemId() == R.id.nav_portfolio) {
                closeDrawer();

                return true;
            }

            if (menuItem.getItemId() == R.id.nav_clients_records) {
                setFragmentMenu(R.id.clientsRecords, PhotographerMenuItems.CLIENTS_RECORDS);
                closeDrawer();

                return true;

            }

            if (menuItem.getItemId() == R.id.nav_settings) {
                navController.navigate(R.id.photographerSettings);
                closeDrawer();

                return true;
            }

            return false;
        });
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
        binding = ActivityPhotographerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationViewMenu = binding.bottomNavigationViewMenu.getMenu();
    }

    public void setFragmentMenu(int destination, PhotographerMenuItems menu) {
        navController.popBackStack();
        navController.navigate(destination);

        setActiveItemOnBottomMenu(menu);
    }

    private void closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void setActiveItemOnBottomMenu(PhotographerMenuItems item) {
        bottomNavigationViewMenu.getItem(item.ordinal()).setChecked(true);
    }

    private enum PhotographerMenuItems {
        HOME,
        PROFILE,
        CLIENTS_RECORDS
    }
}
