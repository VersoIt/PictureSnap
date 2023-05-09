package ru.verso.picturesnap.presentation.fragments.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.verso.picturesnap.databinding.FragmentClientMyRecordsBinding;
import ru.verso.picturesnap.presentation.viewmodel.client.ClientPhotographersOfSelectedServiceViewModel;

public class ClientRecords extends Fragment {

    private ClientPhotographersOfSelectedServiceViewModel clientRecordsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ru.verso.picturesnap.databinding.FragmentClientMyRecordsBinding binding = FragmentClientMyRecordsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ClientPhotographersOfSelectedServiceViewModel viewModel = getViewModel();
    }

    private void createListOfPhotographers() {

    }

    private ClientPhotographersOfSelectedServiceViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(ClientPhotographersOfSelectedServiceViewModel.class);
    }
}