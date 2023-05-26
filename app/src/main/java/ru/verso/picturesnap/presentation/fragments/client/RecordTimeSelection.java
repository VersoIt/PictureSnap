package ru.verso.picturesnap.presentation.fragments.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.time.LocalTime;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentRecordTimeSelectionBinding;
import ru.verso.picturesnap.presentation.viewmodel.client.RecordTimeSelectionViewModel;

public class RecordTimeSelection extends Fragment {

    private FragmentRecordTimeSelectionBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRecordTimeSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.timePicker.setIs24HourView(true);
        NavController navController = getNavController();
        RecordTimeSelectionViewModel viewModel = getViewModel();

        bindTimePicker(viewModel);
        setOnBackPressedEvent(navController, viewModel);
        bindReadyButton(viewModel, navController);
    }

    private void setOnBackPressedEvent(NavController navController, RecordTimeSelectionViewModel recordTimeSelectionViewModel) {

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                verify(recordTimeSelectionViewModel);
                navController.navigateUp();
            }
        });
    }

    private void bindReadyButton(RecordTimeSelectionViewModel viewModel, NavController navController) {

        binding.appCompatButtonReady.buttonReady.setOnClickListener(view -> {
            if (verify(viewModel)) {
                navController.navigateUp();
            }
        });
    }

    private boolean verify(RecordTimeSelectionViewModel recordTimeSelectionViewModel) {
        if (!recordTimeSelectionViewModel.isValid()) {
            Toast.makeText(requireContext(), R.string.time_is_not_selected, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void bindTimePicker(RecordTimeSelectionViewModel recordTimeSelectionViewModel) {
        binding.timePicker.setIs24HourView(true);

        recordTimeSelectionViewModel.getTime().observe(getViewLifecycleOwner(),  time -> {
            binding.timePicker.setHour(time.getHour());
            binding.timePicker.setMinute(time.getMinute());
        });

        binding.timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            LocalTime time = LocalTime.of(hourOfDay, minute, 0);
            recordTimeSelectionViewModel.setTime(time);
        });
    }

    private RecordTimeSelectionViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(RecordTimeSelectionViewModel.class);
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }
}