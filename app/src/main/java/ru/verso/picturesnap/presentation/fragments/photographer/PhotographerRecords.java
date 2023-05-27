package ru.verso.picturesnap.presentation.fragments.photographer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ru.verso.picturesnap.data.repository.ClientRepositoryImpl;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerRepositoryImpl;
import ru.verso.picturesnap.data.repository.RecordsRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserAuthDataRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.data.storage.datasources.firebase.ClientFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.PhotographerFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.RecordsFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.UserAuthFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.room.RoleRoomDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.FirstTimeWentSharedPrefsDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.UserLocationSharedPrefsDataSource;
import ru.verso.picturesnap.databinding.FragmentPhotographerClientsRecordsBinding;
import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.models.Record;
import ru.verso.picturesnap.domain.usecase.GetClientDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerRecordsUseCase.GetPhotographerRecordsUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateRecordsDataUseCase;
import ru.verso.picturesnap.presentation.adapters.photographer.RecordsFromClientAdapter;
import ru.verso.picturesnap.presentation.factory.PhotographerRecordsViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.photographer.PhotographerRecordsViewModel;

public class PhotographerRecords extends Fragment {

    FragmentPhotographerClientsRecordsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotographerClientsRecordsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecordsRecyclerView(getViewModel());
    }

    private void initRecordsRecyclerView(PhotographerRecordsViewModel photographerRecordsViewModel) {
        RecordsFromClientAdapter adapter = new RecordsFromClientAdapter(new RecordsFromClientAdapter.RecordsDiff(), photographerRecordsViewModel, requireActivity());
        binding.recyclerViewRecords.setAdapter(adapter);
        binding.recyclerViewRecords.setLayoutManager(new LinearLayoutManager(requireContext()));

        photographerRecordsViewModel.getServicesOfPhotographer(records -> {
            List<RecordsFromClientAdapter.RecordBundle> bundles = new ArrayList<>();
            View owner = getView();
            if (owner != null) {
                MediatorLiveData<RecordsFromClientAdapter.RecordBundle> mediatorLiveData = new MediatorLiveData<>();
                for (Record record : records) {
                    LiveData<Client> clientLiveData = photographerRecordsViewModel.getClientById(record.getClientId());
                    LiveData<PhotographerPresentationService> serviceLiveData = photographerRecordsViewModel.getServiceById(record.getServiceId());

                    mediatorLiveData.addSource(clientLiveData, photographer -> {
                        mediatorLiveData.removeSource(clientLiveData);
                        mediatorLiveData.setValue(new RecordsFromClientAdapter.RecordBundle(record, photographer, serviceLiveData.getValue()));
                    });

                    mediatorLiveData.addSource(serviceLiveData, service -> {
                        mediatorLiveData.removeSource(serviceLiveData);
                        mediatorLiveData.setValue(new RecordsFromClientAdapter.RecordBundle(record, clientLiveData.getValue(), service));
                    });
                }

                mediatorLiveData.observe(getViewLifecycleOwner(), bundle -> {
                    if (bundle != null && bundle.getRecord() != null && bundle.getClient() != null && bundle.getPhotographerPresentationService() != null) {
                        bundles.add(bundle);
                        adapter.submitList(new ArrayList<>(bundles));
                    }
                });

            }
        });
    }

    private PhotographerRecordsViewModel getViewModel() {

        return new ViewModelProvider(this, new PhotographerRecordsViewModelFactory(new GetPhotographerDataUseCase(new PhotographerRepositoryImpl(new PhotographerFirebaseDataSource())),
                new GetPhotographerRecordsUseCase(new RecordsRepositoryImpl(new RecordsFirebaseDataSource())),
                new GetUserDataUseCase(new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(requireContext())),
                        new RoleRepositoryImpl(new RoleRoomDataSource(requireContext())),
                        new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(requireContext())),
                        new UserAuthDataRepositoryImpl(new UserAuthFirebaseDataSource())),
                new GetClientDataUseCase(new ClientRepositoryImpl(new ClientFirebaseDataSource())),
                new UpdateRecordsDataUseCase(new RecordsRepositoryImpl(new RecordsFirebaseDataSource())))).get(PhotographerRecordsViewModel.class);
    }
}