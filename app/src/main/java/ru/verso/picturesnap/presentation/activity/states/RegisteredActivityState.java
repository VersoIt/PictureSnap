package ru.verso.picturesnap.presentation.activity.states;

import android.view.Menu;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.ActivityClientBinding;
import ru.verso.picturesnap.databinding.LayoutNavHeaderClientBinding;
import ru.verso.picturesnap.presentation.dialogs.SignOutDialogFragment;
import ru.verso.picturesnap.presentation.viewmodel.client.ClientMainViewModel;

public class RegisteredActivityState implements ClientActivityState {

    private final ActivityClientBinding binding;
    private final NavController navController;

    private final Menu bottomNavigationViewMenu;

    private final ClientMainViewModel clientMainViewModel;
    private SignOutDialogFragment signOutDialogFragment;

    private FragmentManager supportFragmentManager;

    private enum RegisteredBottomMenuItems {
        HOME,
        FAVORITES,
        RECORDS
    }

    private final LifecycleOwner lifecycleOwner;

    public RegisteredActivityState(LifecycleOwner lifecycleOwner, ActivityClientBinding binding, NavController navController, ClientMainViewModel clientMainViewModel) {
        this.binding = binding;
        this.navController = navController;
        this.clientMainViewModel = clientMainViewModel;
        this.lifecycleOwner = lifecycleOwner;
        this.signOutDialogFragment = null;

        bottomNavigationViewMenu = binding.bottomNavigationViewMenu.getMenu();
    }

    public void setSignOutDialogAndParams(SignOutDialogFragment signOutDialogFragment, FragmentManager supportFragmentManager) {
        this.signOutDialogFragment = signOutDialogFragment;
        this.supportFragmentManager = supportFragmentManager;
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

    private void observeLeftMenuToClientMainViewModel(View header) {

        LayoutNavHeaderClientBinding binding = LayoutNavHeaderClientBinding.bind(header);

        clientMainViewModel.getClientAvatar().observe(lifecycleOwner, path -> Glide.with(binding.imageViewLogo.getContext())
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_person_gray)
                .into(binding.imageViewLogo));
        clientMainViewModel.getClientName().observe(lifecycleOwner, binding.textViewName::setText);
        clientMainViewModel.getClientEmail().observe(lifecycleOwner, binding.textViewEmail::setText);
    }

    @Override
    public void createLeftMenu() {

        binding.navigationViewMenu.inflateMenu(R.menu.client_left);
        binding.navigationViewMenu.inflateHeaderView(R.layout.layout_nav_header_client);
        binding.navigationViewMenu.getHeaderView(0).setOnClickListener(view -> {
            navController.navigate(R.id.clientProfile);
            closeDrawer();
        });

        observeLeftMenuToClientMainViewModel(binding.navigationViewMenu.getHeaderView(0));

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

            if (menuItem.getItemId() == R.id.nav_sign_out) {
                if (signOutDialogFragment != null && supportFragmentManager != null) {
                    signOutDialogFragment.show(supportFragmentManager, SignOutDialogFragment.TAG);
                }

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
