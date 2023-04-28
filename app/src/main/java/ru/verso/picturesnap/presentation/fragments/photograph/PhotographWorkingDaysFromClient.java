package ru.verso.picturesnap.presentation.fragments.photograph;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

import ru.verso.picturesnap.databinding.FragmentPhotographWorkingDaysFromClientBottomsheetBinding;
import ru.verso.picturesnap.presentation.adapters.client.PhotographsInCityAdapter;
import ru.verso.picturesnap.presentation.adapters.photograph.WorkingDaysFromUnregisteredAdapter;
import ru.verso.picturesnap.presentation.bottomsheet.ClientBottomSheetDialogFragment;
import ru.verso.picturesnap.presentation.viewmodel.WorkingDaysViewModel;

public class PhotographWorkingDaysFromClient extends Fragment {

    private FragmentPhotographWorkingDaysFromClientBottomsheetBinding binding;

    private WorkingDaysViewModel workingDaysViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhotographWorkingDaysFromClientBottomsheetBinding.inflate(inflater, container, false);
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

        workingDaysViewModel = getWorkingDaysViewModel();
        createWorkingDaysList(workingDaysViewModel);
    }

    private void createWorkingDaysList(WorkingDaysViewModel workingDaysViewModel) {
        RecyclerView recyclerView = binding.recyclerViewWorkingDaysContainer;

        final WorkingDaysFromUnregisteredAdapter adapter = new WorkingDaysFromUnregisteredAdapter(
                new WorkingDaysFromUnregisteredAdapter.WorkingDayDiff()
        );

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        workingDaysViewModel.getWorkingDays().observe(getViewLifecycleOwner(), adapter::submitList);
    }

    private WorkingDaysViewModel getWorkingDaysViewModel() {
        return new ViewModelProvider(requireActivity()).get(WorkingDaysViewModel.class);
    }
}