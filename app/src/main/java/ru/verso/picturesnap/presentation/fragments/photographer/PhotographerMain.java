package ru.verso.picturesnap.presentation.fragments.photographer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.RecordsRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserAuthDataRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.data.storage.datasources.firebase.RecordsFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.UserAuthFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.room.RoleRoomDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.FirstTimeWentSharedPrefsDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.UserLocationSharedPrefsDataSource;
import ru.verso.picturesnap.databinding.FragmentPhotographerMainBinding;
import ru.verso.picturesnap.domain.usecase.GetPhotographerRecordsUseCase.GetPhotographerRecordsUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.presentation.factory.PhotographerMainViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.photographer.PhotographerMainViewModel;

public class PhotographerMain extends Fragment {

    private FragmentPhotographerMainBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotographerMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        NavController navController = getNavController();
        initAmountOfRecords(getViewModel());
        initRecordsButton(navController);
    }

    private void initAmountOfRecords(PhotographerMainViewModel photographerMainViewModel) {

        photographerMainViewModel.getPhotographerRecords(records -> {
            if (records.size() > 0) {
                binding.textViewRecordCount.setVisibility(View.VISIBLE);
                binding.textViewRecordCount.setText(String.valueOf(records.size()));
            }
        });
    }

    private PhotographerMainViewModel getViewModel() {

        return new ViewModelProvider(requireActivity(), new PhotographerMainViewModelFactory(new GetPhotographerRecordsUseCase(new RecordsRepositoryImpl(new RecordsFirebaseDataSource())),
                new GetUserDataUseCase(new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(requireContext())),
                        new RoleRepositoryImpl(new RoleRoomDataSource(requireContext())),
                        new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(requireContext())),
                        new UserAuthDataRepositoryImpl(new UserAuthFirebaseDataSource())))).get(PhotographerMainViewModel.class);
    }

    private void initRecordsButton(NavController navController) {
        binding.textViewMyRecords.setOnClickListener(view -> navController.navigate(R.id.action_photographerMain_to_photographerRecords));
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }
}