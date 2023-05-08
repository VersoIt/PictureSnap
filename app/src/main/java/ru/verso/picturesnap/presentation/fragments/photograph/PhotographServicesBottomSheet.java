package ru.verso.picturesnap.presentation.fragments.photograph;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

import ru.verso.picturesnap.databinding.FragmentPhotographServicesBottomSheetBinding;
import ru.verso.picturesnap.presentation.adapters.client.ServicesFromClientAdapter;
import ru.verso.picturesnap.presentation.bottomsheet.ClientBottomSheetDialogFragment;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.ServicesViewModel;

public class PhotographServicesBottomSheet extends Fragment {

    private FragmentPhotographServicesBottomSheetBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotographServicesBottomSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.layoutReadyButton.buttonReady.setOnClickListener(v -> {
            BottomSheetDialogFragment bottomSheetDialogFragment = (BottomSheetDialogFragment) requireActivity().
                    getSupportFragmentManager().
                    findFragmentByTag(ClientBottomSheetDialogFragment.TAG);

            Objects.requireNonNull(bottomSheetDialogFragment).dismiss();
        });

        ServicesViewModel servicesViewModel = getViewModel();
        createServicesList(servicesViewModel);
    }

    private void createServicesList(ServicesViewModel viewModel) {
        RecyclerView recyclerView = binding.recyclerViewServicesContainer;

        final ServicesFromClientAdapter adapter = new ServicesFromClientAdapter(
                new ServicesFromClientAdapter.ServicesDiff()
        );

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        viewModel.getPhotographServices().observe(getViewLifecycleOwner(), adapter::submitList);
    }

    private ServicesViewModel getViewModel() {

        return new ViewModelProvider(requireActivity()).get(ServicesViewModel.class);
    }
}