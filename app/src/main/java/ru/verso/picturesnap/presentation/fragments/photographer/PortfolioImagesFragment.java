package ru.verso.picturesnap.presentation.fragments.photographer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import ru.verso.picturesnap.databinding.FragmentImagesBinding;
import ru.verso.picturesnap.presentation.adapters.photographer.PortfolioImageAdapter;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.ServicesViewModel;

public class PortfolioImagesFragment extends Fragment {

    private FragmentImagesBinding binding;

    private final String serviceId;

    private final ServicesViewModel servicesViewModel;

    public PortfolioImagesFragment(String serviceId, ServicesViewModel servicesViewModel) {
        this.serviceId = serviceId;
        this.servicesViewModel = servicesViewModel;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentImagesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createImagesList();
    }

    private void createImagesList() {
        RecyclerView recyclerView = binding.recyclerViewImages;

        PortfolioImageAdapter adapter = new PortfolioImageAdapter(new PortfolioImageAdapter.PortfolioImageDiff(), requireActivity().getSupportFragmentManager());
        recyclerView.setAdapter(adapter);

        Log.e("FUCKER", serviceId);
        servicesViewModel.getPicturesOf(serviceId).observe(getViewLifecycleOwner(), adapter::submitList);
    }
}