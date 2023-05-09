package ru.verso.picturesnap.presentation.activity.states;

import android.view.Menu;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.ActivityClientBinding;

public class RegisteredActivityState implements ClientActivityState {

    private final ActivityClientBinding binding;
    private final NavController navController;

    private final Menu bottomNavigationViewMenu;

    private enum RegisteredBottomMenuItems {
        HOME,
        FAVORITES,
        RECORDS
    }

    public RegisteredActivityState(ActivityClientBinding binding, NavController navController) {
        this.binding = binding;
        this.navController = navController;

        bottomNavigationViewMenu = binding.bottomNavigationViewMenu.getMenu();
    }

    @Override
    public int getStartFragmentId() {
        return R.id.client_main;
    }

    @Override
    public void createBottomNavigationMenu() {
        binding.bottomNavigationViewMenu.inflateMenu(R.menu.client_bottom);

        binding.bottomNavigationViewMenu.setOnItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.registered_main) {
                setFragmentMenu(R.id.client_main, RegisteredBottomMenuItems.HOME);
                closeDrawer();

                return true;
            }

            if (menuItem.getItemId() == R.id.favorites) {
                setFragmentMenu(R.id.favorites_fragment, RegisteredBottomMenuItems.FAVORITES);
                closeDrawer();

                return true;
            }

            if (menuItem.getItemId() == R.id.records) {
                setFragmentMenu(R.id.clientMyRecords, RegisteredBottomMenuItems.RECORDS);
                closeDrawer();

                return true;
            }

            return false;
        });
    }

    @Override
    public void createLeftMenu() {

        binding.navigationViewMenu.inflateMenu(R.menu.client_left);
        binding.navigationViewMenu.getHeaderView(0).setOnClickListener(view -> {
            navController.navigate(R.id.unregistered_profile);
            closeDrawer();
        });

        binding.navigationViewMenu.setNavigationItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.nav_home) {
                setFragmentMenu(R.id.client_main, RegisteredBottomMenuItems.HOME);

                closeDrawer();

                return true;
            }

            if (menuItem.getItemId() == R.id.nav_favorites) {
                setFragmentMenu(R.id.favorites_fragment, RegisteredBottomMenuItems.FAVORITES);
                closeDrawer();

                return true;
            }

            if (menuItem.getItemId() == R.id.nav_records) {
                setFragmentMenu(R.id.clientMyRecords, RegisteredBottomMenuItems.RECORDS);
                closeDrawer();

                return true;

            }

            if (menuItem.getItemId() == R.id.nav_settings) {
                navController.navigate(R.id.settings);
                closeDrawer();

                return true;
            }

            return false;
        });
    }

    private void setActiveItemOnBottomMenu(RegisteredBottomMenuItems item) {
        bottomNavigationViewMenu.getItem(item.ordinal()).setChecked(true);
    }

    private void closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void setFragmentMenu(int fragmentId, RegisteredBottomMenuItems menu) {
        navController.popBackStack();
        navController.navigate(fragmentId);

        setActiveItemOnBottomMenu(menu);
    }
}
