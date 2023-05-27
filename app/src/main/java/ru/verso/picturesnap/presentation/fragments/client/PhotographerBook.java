package ru.verso.picturesnap.presentation.fragments.client;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentPhotographerBookBinding;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.presentation.activity.MainActivity;
import ru.verso.picturesnap.presentation.adapters.client.ServicesBookingSpinnerAdapter;
import ru.verso.picturesnap.presentation.viewmodel.client.PhotographerBookViewModel;
import ru.verso.picturesnap.presentation.viewmodel.client.RecordDateSelectionViewModel;
import ru.verso.picturesnap.presentation.viewmodel.client.RecordTimeSelectionViewModel;

public class PhotographerBook extends Fragment {

    private FragmentPhotographerBookBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotographerBookBinding.inflate(inflater, container, false);
        return binding.getRoot();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = getNavController();
        bindDateSelectionTextView(navController);
        bindTimeSelectionTextView(navController);

        PhotographerBookViewModel viewModel = getPhotographerBookViewModel();
        RecordDateSelectionViewModel dateSelectionViewModel = getRecordDateSelectionViewModel();
        RecordTimeSelectionViewModel timeSelectionViewModel = getRecordTimeSelectionViewModel();

        dateSelectionViewModel.getDate().observe(getViewLifecycleOwner(), date -> {
            if (dateSelectionViewModel.isChanged() && dateSelectionViewModel.isValid()) {
                binding.textViewDateSelection.setText(new SimpleDateFormat("dd MMMM, yyyy", getCurrentLocale()).format(date));
                return;
            }

            binding.textViewDateSelection.setText(R.string.date_selection);
        });

        timeSelectionViewModel.getTime().observe(getViewLifecycleOwner(), time -> {
            if (timeSelectionViewModel.isChanged() && timeSelectionViewModel.isValid()) {
                binding.textViewTimeSelection.setText(time.toString());
            }
        });

        bindSpinnerAdapter(viewModel);
        bindSendButton(viewModel, dateSelectionViewModel, timeSelectionViewModel);
    }

    private void bindSendButton(PhotographerBookViewModel viewModel, RecordDateSelectionViewModel dateSelectionViewModel, RecordTimeSelectionViewModel timeSelectionViewModel) {
        binding.appCompatButtonSend.buttonSend.setOnClickListener(view -> {
            if (!dateSelectionViewModel.isValid() || !dateSelectionViewModel.isChanged()) {
                Toast.makeText(requireContext(), R.string.date_is_not_valid, Toast.LENGTH_LONG).show();
                return;
            }

            if (!timeSelectionViewModel.isValid() || !timeSelectionViewModel.isChanged()) {
                Toast.makeText(requireContext(), R.string.time_is_not_selected, Toast.LENGTH_LONG).show();
                return;
            }

            viewModel.setService((PhotographerPresentationService) binding.spinnerSelectedService.getSelectedItem());

            if (timeSelectionViewModel.getTime().getValue() != null) {
                binding.appCompatButtonSend.buttonSend.setEnabled(false);
                if (!viewModel.book(dateSelectionViewModel.getDate().getValue(), timeSelectionViewModel.getTime().getValue(), binding.editTextComment.getText() == null ? "" :  binding.editTextComment.getText().toString())) {
                    Toast.makeText(requireContext(), R.string.date_is_not_valid, Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(requireContext(), R.string.book_successfully, Toast.LENGTH_LONG).show();
                goToMainActivity();
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(requireContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    private void bindSpinnerAdapter(PhotographerBookViewModel viewModel) {

        binding.spinnerSelectedService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.updateSelectedServicePosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewModel.getServicesOfPhotographer().observe(getViewLifecycleOwner(), services -> {
            if (services != null && services.size() > 0) {
                ServicesBookingSpinnerAdapter spinnerAdapter = new ServicesBookingSpinnerAdapter(requireContext(), services);
                binding.spinnerSelectedService.setAdapter(spinnerAdapter);
            }
            viewModel.getSelectedServicePosition().observe(getViewLifecycleOwner(), position ->
                    binding.spinnerSelectedService.setSelection(position));
        });
    }

    private void bindDateSelectionTextView(NavController navController) {
        binding.textViewDateSelection.setOnClickListener(view -> navController.navigate(R.id.action_photographerBook_to_recordDateSelection));
    }

    private void bindTimeSelectionTextView(NavController navController) {
        binding.textViewTimeSelection.setOnClickListener(view -> navController.navigate(R.id.action_photographerBook_to_recordTimeSelection));
    }

    private PhotographerBookViewModel getPhotographerBookViewModel() {
        return new ViewModelProvider(requireActivity()).get(PhotographerBookViewModel.class);
    }

    private RecordDateSelectionViewModel getRecordDateSelectionViewModel() {
        return new ViewModelProvider(requireActivity()).get(RecordDateSelectionViewModel.class);
    }

    private RecordTimeSelectionViewModel getRecordTimeSelectionViewModel() {
        return new ViewModelProvider(requireActivity()).get(RecordTimeSelectionViewModel.class);
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }
}