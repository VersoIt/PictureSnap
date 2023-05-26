package ru.verso.picturesnap.presentation.fragments.client;

import android.content.Context;
import android.content.res.Resources;
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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentRecordDateSelectionBinding;
import ru.verso.picturesnap.presentation.viewmodel.client.RecordDateSelectionViewModel;

public class RecordDateSelection extends Fragment {

    private FragmentRecordDateSelectionBinding binding;

    private RecordDateSelectionViewModel serviceDateSelectionViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRecordDateSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceDateSelectionViewModel = getViewModel();

        bindDatePicker();

        NavController navController = getNavController();
        bindSaveButton(serviceDateSelectionViewModel, navController);
        setOnBackPressEvent(serviceDateSelectionViewModel, navController);
    }

    private void bindSaveButton(RecordDateSelectionViewModel viewModel, NavController navController) {
        binding.appCompatButtonReady.buttonReady.setOnClickListener(view -> {
            if (verify(viewModel))
                navController.navigateUp();
        });
    }

    private boolean verify(RecordDateSelectionViewModel viewModel) {
        if (!viewModel.isValid()) {
            Toast.makeText(requireContext(), R.string.date_is_not_valid, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void setOnBackPressEvent(RecordDateSelectionViewModel viewModel, NavController navController) {

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                verify(viewModel);
                navController.navigateUp();
            }
        });
    }

    private void bindDatePicker() {

        serviceDateSelectionViewModel.getDate().observe(getViewLifecycleOwner(), date -> {
            Calendar calendar = Calendar.getInstance(getCurrentLocale());
            calendar.setTime(date);

            binding.datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), (v, year, monthOfYear, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                serviceDateSelectionViewModel.setDate(calendar.getTime());
            });
        });
    }

    private Locale getCurrentLocale() {
        Context context = getContext();
        Resources resources = null;

        if (context != null)
            resources = context.getResources();

        if (resources != null)
            return resources.getConfiguration().getLocales().get(0);

        return Locale.getDefault();
    }

    private RecordDateSelectionViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(RecordDateSelectionViewModel.class);
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }
}