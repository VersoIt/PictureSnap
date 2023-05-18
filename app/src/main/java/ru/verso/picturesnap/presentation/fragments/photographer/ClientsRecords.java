package ru.verso.picturesnap.presentation.fragments.photographer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.verso.picturesnap.databinding.FragmentClientsRecordsBinding;

public class ClientsRecords extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ru.verso.picturesnap.databinding.FragmentClientsRecordsBinding binding = FragmentClientsRecordsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}