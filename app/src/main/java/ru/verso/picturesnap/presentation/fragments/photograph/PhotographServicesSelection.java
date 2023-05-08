package ru.verso.picturesnap.presentation.fragments.photograph;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.ServicesRepositoryImpl;
import ru.verso.picturesnap.databinding.FragmentPhotographServicesSelectionBinding;
import ru.verso.picturesnap.databinding.LayoutPhotographServiceSelectionBinding;
import ru.verso.picturesnap.domain.models.PhotographPresentationService;
import ru.verso.picturesnap.domain.usecase.GetServicesUseCase;
import ru.verso.picturesnap.presentation.adapters.photograph.PhotographServiceSelectionViewHolder;
import ru.verso.picturesnap.presentation.adapters.photograph.ServiceSelectionAdapter;
import ru.verso.picturesnap.presentation.app.PictureSnapApp;
import ru.verso.picturesnap.presentation.factory.PhotographServicesSelectionViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographServicesSelectionViewModel;

public class PhotographServicesSelection extends Fragment {

    private FragmentPhotographServicesSelectionBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentPhotographServicesSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PhotographServicesSelectionViewModel servicesViewModel = getPhotographServicesViewModel();

        NavController navController = getNavController();

        ListAdapter<PhotographPresentationService, PhotographServiceSelectionViewHolder> servicesAdapter =
                new ServiceSelectionAdapter(new ServiceSelectionAdapter.ServiceSelectionDiff());

        createServiceSelectionList(servicesAdapter, servicesViewModel);
        bindReadyButton(servicesAdapter, navController, servicesViewModel);
    }

    private void createServiceSelectionList(ListAdapter<PhotographPresentationService, PhotographServiceSelectionViewHolder> adapter, PhotographServicesSelectionViewModel servicesViewModel) {

        RecyclerView recycler = binding.recyclerViewServices;
        recycler.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        recycler.setAdapter(adapter);

        servicesViewModel.getAllServices().observe(getViewLifecycleOwner(), adapter::submitList);
    }

    private boolean isValid(RecyclerView recyclerView) {

        for (int idxNum = 0; idxNum < recyclerView.getChildCount(); ++idxNum) {
            View childView = recyclerView.getChildAt(idxNum);
            PhotographServiceSelectionViewHolder viewHolder = (PhotographServiceSelectionViewHolder) recyclerView.getChildViewHolder(childView);
            LayoutPhotographServiceSelectionBinding binding = viewHolder.getBinding();

            String price = binding.editTextPrice.getText().toString();
            if (!price.isEmpty() && price.matches(PictureSnapApp.DIGIT_MATCH) && Integer.parseInt(price) > 0)
                return true;
        }

        return false;
    }

    private void bindReadyButton(ListAdapter<PhotographPresentationService, PhotographServiceSelectionViewHolder> adapter, NavController navController, PhotographServicesSelectionViewModel viewModel) {
        binding.layoutReadyButton.buttonReady.setOnClickListener(v -> {

            if (isValid(binding.recyclerViewServices)) {
                viewModel.setStatus(R.string.prices_selected);
                viewModel.setServices(adapter.getCurrentList());

                navController.navigateUp();
            } else
                Toast.makeText(requireContext(), R.string.wrong_data, Toast.LENGTH_LONG).show();
        });
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }

    private PhotographServicesSelectionViewModel getPhotographServicesViewModel() {

        return new ViewModelProvider(requireActivity(),
                new PhotographServicesSelectionViewModelFactory(getViewLifecycleOwner(),
                        new GetServicesUseCase(new ServicesRepositoryImpl())))
                .get(PhotographServicesSelectionViewModel.class);
    }
}