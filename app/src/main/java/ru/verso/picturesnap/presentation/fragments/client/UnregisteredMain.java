package ru.verso.picturesnap.presentation.fragments.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.ClientRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.databinding.FragmentUnregisteredMainBinding;
import ru.verso.picturesnap.presentation.adapters.client.PhotographServicesAdapter;
import ru.verso.picturesnap.presentation.viewmodel.UnregisteredMainViewModel;
import ru.verso.picturesnap.presentation.viewmodel.factory.UnregisteredMainViewModelFactory;

public class UnregisteredMain extends Fragment {

    private FragmentUnregisteredMainBinding binding;

    private UnregisteredMainViewModel photographServicesViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUnregisteredMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        photographServicesViewModel = new ViewModelProvider(this,
                new UnregisteredMainViewModelFactory(new ClientRepositoryImpl(requireActivity().getApplication()),
                        new UserLocationRepositoryImpl(requireActivity().getApplicationContext())))
                .get(UnregisteredMainViewModel.class);

        NavController navController = getNavController();

        navigateToolbar();
        bindButtons(navController);
        createPhotographServicesList(navController);

        photographServicesViewModel.getLocation().observe(getViewLifecycleOwner(), location -> {
            String result = String.format("%s %s", getResources().getString(R.string.photographs_in_city), location);
            binding.textViewPhotographsInCity.setText(result);
        });
    }

    private void bindButtons(NavController navController) {
        binding.linearLayoutAuthButtons.includeSignupButton.buttonSignup.setOnClickListener(view ->
                navController.navigate(R.id.action_unregistered_home_to_userSelection));

        binding.linearLayoutAuthButtons.includeLoginButton.buttonLogin.setOnClickListener(view ->
                navController.navigate(R.id.action_unregistered_home_to_login));
    }

    private void createPhotographServicesList(NavController navController) {
        RecyclerView recyclerView = binding.recyclerViewServices;

        final PhotographServicesAdapter adapter =
                new PhotographServicesAdapter(new PhotographServicesAdapter.PhotographServiceDiff(), navController);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        photographServicesViewModel.getServices().observe(getViewLifecycleOwner(), adapter::submitList);
    }

    private void navigateToolbar() {
        NavController toolbarNavController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView_tool_bar);
        toolbarNavController.navigate(R.id.unregisteredToolbar);
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }
}