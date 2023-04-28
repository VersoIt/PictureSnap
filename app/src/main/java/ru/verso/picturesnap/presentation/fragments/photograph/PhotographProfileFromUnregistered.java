package ru.verso.picturesnap.presentation.fragments.photograph;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.data.repository.WorkingDaysRepositoryImpl;
import ru.verso.picturesnap.databinding.FragmentPhotographProfileFromUnregisteredBinding;
import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.usecase.GetPhotographWorkingDaysUseCase;
import ru.verso.picturesnap.presentation.bottomsheet.ClientBottomSheetDialogFragment;
import ru.verso.picturesnap.presentation.viewmodel.PhotographProfileViewModel;
import ru.verso.picturesnap.presentation.viewmodel.WorkingDaysViewModel;
import ru.verso.picturesnap.presentation.viewmodel.factory.WorkingDaysViewModelFactory;

public class PhotographProfileFromUnregistered extends Fragment {

    private FragmentPhotographProfileFromUnregisteredBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotographProfileFromUnregisteredBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PhotographProfileViewModel photographProfileViewModel = getPhotographProfileViewModel();
        photographProfileViewModel.getPhotograph().observe(getViewLifecycleOwner(), photograph -> {
            updateName(photograph.getFirstName(), photograph.getLastName());
            updateEmail(photograph.getEmail());
            updateLocation(new Location(photograph.getLatitude(), photograph.getLongitude()));
            updateWorkingDays(photograph);
        });
    }

    private PhotographProfileViewModel getPhotographProfileViewModel() {
        return new ViewModelProvider(requireActivity()).get(PhotographProfileViewModel.class);
    }

    private void updateName(String firstName, String lastName) {
        String photographName = String.format("%s %s", firstName, lastName);
        binding.textViewProfileName.setText(photographName);
    }

    private void updateEmail(String email) {
        binding.linearLayoutFieldsContainer.textViewEmail.setText(email);
    }

    private void updateLocation(Location location) {
        binding.linearLayoutFieldsContainer.textViewLocation.setText(
                convertGeoCoordinatesToString(
                        location.getLatitude(),
                        location.getLongitude()));
    }

    private String convertGeoCoordinatesToString(double latitude, double longitude) {
        return "fucker";
    }

    private void updateServices() {

    }

    private void updateAboutPhotographButton() {

    }

    private void updateWorkingDays(Photograph photograph) {
        binding.linearLayoutFieldsContainer.textViewWorkDays.setOnClickListener(view -> {
            sendPhotographIdToWorkingDaysDialog(photograph.getId());
            showBottomSheetDialog(R.id.photograph_working_days_from_client);
        });
    }

    private void sendPhotographIdToWorkingDaysDialog(int photographId) {
        new ViewModelProvider(requireActivity(), new WorkingDaysViewModelFactory(
                new GetPhotographWorkingDaysUseCase(
                        new WorkingDaysRepositoryImpl(getContext()))))
                .get(WorkingDaysViewModel.class)
                .putPhotographId(photographId);
    }

    public void showBottomSheetDialog(int fragmentId) {
        ClientBottomSheetDialogFragment clientBottomSheet = new ClientBottomSheetDialogFragment(fragmentId);
        clientBottomSheet.show(requireActivity().getSupportFragmentManager(), ClientBottomSheetDialogFragment.TAG);
    }
}