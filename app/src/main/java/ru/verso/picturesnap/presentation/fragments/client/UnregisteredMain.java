package ru.verso.picturesnap.presentation.fragments.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentUnregisteredMainBinding;
import ru.verso.picturesnap.domain.models.PhotographService;
import ru.verso.picturesnap.presentation.adapters.client.PhotographServicesAdapter;
import ru.verso.picturesnap.presentation.viewmodel.PhotographServicesViewModel;

public class UnregisteredMain extends Fragment {

    private FragmentUnregisteredMainBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUnregisteredMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navigateToolbar();

        PhotographServicesViewModel photographServicesViewModel = new ViewModelProvider(this).get(PhotographServicesViewModel.class);
        RecyclerView recyclerView = binding.recyclerViewServices;

        final PhotographServicesAdapter adapter = new PhotographServicesAdapter(new PhotographServicesAdapter.PhotographServiceDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        photographServicesViewModel.getServices().observe(getViewLifecycleOwner(), adapter::submitList);
    }

    private void navigateToolbar() {
        NavController toolbarNavController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView_tool_bar);
        toolbarNavController.navigate(R.id.unregisteredToolbar);
    }
}