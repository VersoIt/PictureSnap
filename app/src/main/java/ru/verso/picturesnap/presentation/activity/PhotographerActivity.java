package ru.verso.picturesnap.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
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
import ru.verso.picturesnap.data.storage.datasources.firebase.PhotographerFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.UserAuthFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.room.RoleRoomDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.FirstTimeWentSharedPrefsDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.UserLocationSharedPrefsDataSource;
import ru.verso.picturesnap.databinding.ActivityPhotographerBinding;
import ru.verso.picturesnap.databinding.LayoutNavHeaderPhotographerBinding;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.presentation.dialogs.SignOutDialogFragment;
import ru.verso.picturesnap.presentation.factory.PhotographerActivityViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.photographer.PhotographerActivityViewModel;

public class PhotographerActivity extends AppCompatActivity {

    private ActivityPhotographerBinding binding;

    private Menu bottomNavigationViewMenu;

    private NavController navController;

    private SignOutDialogFragment signOutDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();

        setSupportActionBar(binding.toolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.overview);

        PhotographerActivityViewModel viewModel = getViewModel();

        navController = getNavController();
        setUpMenus(navController, viewModel);

        signOutDialogFragment = new SignOutDialogFragment((dialog, which) -> {
            viewModel.signOut();
            goToMainActivity();
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private PhotographerActivityViewModel getViewModel() {

        return new ViewModelProvider(this, new PhotographerActivityViewModelFactory(new GetPhotographerDataUseCase(new PhotographerRepositoryImpl(new PhotographerFirebaseDataSource())), new GetUserDataUseCase(
                new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(this)),
                new RoleRepositoryImpl(new RoleRoomDataSource(this)),
                new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(this)),
                new UserAuthDataRepositoryImpl(new UserAuthFirebaseDataSource())
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
                setFragmentMenu(R.id.photographerRecords, PhotographerMenuItems.CLIENTS_RECORDS);
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

            if (menuItem.getItemId() == R.id.nav_clients_records) {
                setFragmentMenu(R.id.photographerRecords, PhotographerMenuItems.CLIENTS_RECORDS);
                closeDrawer();

                return true;

            }

            if (menuItem.getItemId() == R.id.nav_settings) {
                navController.navigate(R.id.photographerSettings);
                closeDrawer();

                return true;
            }

            if (menuItem.getItemId() == R.id.nav_sign_out) {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(SignOutDialogFragment.TAG);

                if (fragment == null)
                    signOutDialogFragment.show(getSupportFragmentManager(), SignOutDialogFragment.TAG);

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
