package ru.verso.picturesnap.presentation.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.databinding.ActivityClientBinding;
import ru.verso.picturesnap.domain.repository.RoleRepository;

public class ClientActivity extends AppCompatActivity {

    private ActivityClientBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RoleRepository.Role role = getRoleFromRepository(new RoleRepositoryImpl(this));
        bindView();

        NavController navController = getNavController();
        bindByState(getStateByRole(role, navController));
    }

    private ClientState getStateByRole(RoleRepository.Role role,
                                       NavController navController) {

        if (role == RoleRepository.Role.CLIENT)
            return new ClientRegisteredState(binding, navController);
        else
            return new UnregisteredState(binding, navController);
    }

    private void bindByState(ClientState state) {
        state.bindBottomNavigationView();
        state.bindNavigationViewMenu();
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }

    private void bindView() {
        binding = ActivityClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private RoleRepository.Role getRoleFromRepository(RoleRepository repository) {
        return repository.getRole();
    }
}
