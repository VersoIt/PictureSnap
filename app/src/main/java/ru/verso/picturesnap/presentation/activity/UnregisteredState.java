package ru.verso.picturesnap.presentation.activity;

import android.view.Menu;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.ActivityClientBinding;

public class UnregisteredState implements ClientState {

    private enum MenuTypes {
        HOME,
        FAVORITES,
        RIGHT_MENU
    }

    private final ActivityClientBinding binding;
    private final NavController navController;

    private final Menu bottomNavigationViewMenu;

    public UnregisteredState(ActivityClientBinding binding, NavController navController) {
        this.binding = binding;
        this.navController = navController;

        bottomNavigationViewMenu = binding.bottomNavigationViewMenu.getMenu();
    }

    @Override
    public void bindBottomNavigationView() {
        binding.bottomNavigationViewMenu.setOnItemSelectedListener(item -> {
            if (R.id.home == item.getItemId()) {
                setFragmentMenu(R.id.unregistered_main, MenuTypes.HOME);
                return true;
            }

            if (R.id.favorites == item.getItemId()) {
                setFragmentMenu(R.id.favorites, MenuTypes.FAVORITES);
                return true;
            }

            if (R.id.menu == item.getItemId()) {
                openRightMenu();

                return true;
            }

            return false;
        });
    }

    private void setFragmentMenu(int fragmentId, MenuTypes menu) {
        navController.popBackStack();
        navController.navigate(fragmentId);

        setActiveButtonMenu(menu);
    }

    private void openRightMenu() {
        navController.popBackStack();
        binding.getRoot().openDrawer(GravityCompat.END);

        setActiveButtonMenu(MenuTypes.RIGHT_MENU);
    }

    @Override
    public void bindNavigationViewMenu() {
        binding.navigationViewMenu.inflateMenu(R.menu.menu_unregistered_client_nav);
        binding.navigationViewMenu.getHeaderView(0).setOnClickListener(view -> {
            navController.navigate(R.id.unregistered_profile);
            closeDrawer();
        });

        binding.navigationViewMenu.setNavigationItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.nav_home) {
                navController.popBackStack();
                navController.navigate(R.id.unregistered_main);
                setActiveButtonMenu(MenuTypes.HOME);

                closeDrawer();

                return true;
            }

            if (menuItem.getItemId() == R.id.nav_favorites) {
                navController.popBackStack();
                navController.navigate(R.id.favorites);

                setActiveButtonMenu(MenuTypes.FAVORITES);
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

    @Override
    public int getMainFragmentId() {
        return R.id.unregistered_main;
    }

    private void setActiveButtonMenu(MenuTypes menu) {
        bottomNavigationViewMenu.getItem(menu.ordinal()).setChecked(true);
    }

    private void closeDrawer() {
        binding.getRoot().closeDrawer(GravityCompat.END);
    }
}
