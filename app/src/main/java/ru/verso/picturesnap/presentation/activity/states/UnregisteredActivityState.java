package ru.verso.picturesnap.presentation.activity.states;

import android.view.Menu;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.ActivityClientBinding;

public class UnregisteredActivityState implements ClientActivityState {

    private final ActivityClientBinding binding;
    private final NavController navController;

    private final Menu bottomNavigationViewMenu;

    private enum UnregisteredBottomMenuItems {
        HOME,
        FAVORITES
    }

    public UnregisteredActivityState(ActivityClientBinding binding, NavController navController) {
        this.binding = binding;
        this.navController = navController;

        bottomNavigationViewMenu = binding.bottomNavigationViewMenu.getMenu();
    }

    @Override
    public int getStartFragmentId() {
        return R.id.unregistered_main;
    }

    @Override
    public void createBottomNavigationMenu() {
        binding.bottomNavigationViewMenu.inflateMenu(R.menu.unregistered_bottom);

        binding.bottomNavigationViewMenu.setOnItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.unregistered_main) {
                setFragmentMenu(R.id.unregistered_main, UnregisteredBottomMenuItems.HOME);
                closeDrawer();

                return true;
            }

            if (menuItem.getItemId() == R.id.favorites_fragment) {
                setFragmentMenu(R.id.favorites_fragment, UnregisteredBottomMenuItems.FAVORITES);
                closeDrawer();

                return true;
            }

            return false;
        });
    }

    @Override
    public void createLeftMenu() {
        binding.navigationViewMenu.inflateMenu(R.menu.unregistered_left);
        binding.navigationViewMenu.getHeaderView(0).setOnClickListener(view -> {
            navController.navigate(R.id.unregistered_profile);
            closeDrawer();
        });

        binding.navigationViewMenu.setNavigationItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.nav_home) {
                setFragmentMenu(R.id.unregistered_main, UnregisteredBottomMenuItems.HOME);

                closeDrawer();

                return true;
            }

            if (menuItem.getItemId() == R.id.nav_favorites) {
                setFragmentMenu(R.id.favorites_fragment, UnregisteredBottomMenuItems.FAVORITES);
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

    private void setActiveItemOnBottomMenu(UnregisteredBottomMenuItems item) {
        bottomNavigationViewMenu.getItem(item.ordinal()).setChecked(true);
    }

    private void closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void setFragmentMenu(int fragmentId, UnregisteredBottomMenuItems menu) {
        navController.popBackStack();
        navController.navigate(fragmentId);

        setActiveItemOnBottomMenu(menu);
    }
}
