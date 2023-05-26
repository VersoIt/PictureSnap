package ru.verso.picturesnap.presentation.fragments.photographer;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.ServicesRepositoryImpl;
import ru.verso.picturesnap.data.storage.datasources.firebase.ServicesFirebaseDataSource;
import ru.verso.picturesnap.databinding.FragmentPhotographerServicesSelectionBinding;
import ru.verso.picturesnap.databinding.LayoutPhotographerServiceSelectionBinding;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.usecase.GetServicesUseCase;
import ru.verso.picturesnap.presentation.adapters.photographer.PhotographerServiceSelectionViewHolder;
import ru.verso.picturesnap.presentation.adapters.photographer.ServiceSelectionAdapter;
import ru.verso.picturesnap.presentation.app.PictureSnapApp;
import ru.verso.picturesnap.presentation.factory.PhotographerServicesSelectionViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographerServicesSelectionViewModel;

public class PhotographerServiceSelection extends Fragment {

    private FragmentPhotographerServicesSelectionBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentPhotographerServicesSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PhotographerServicesSelectionViewModel servicesViewModel = getPhotographerServicesViewModel();

        NavController navController = getNavController();

        ListAdapter<PhotographerPresentationService, PhotographerServiceSelectionViewHolder> servicesAdapter =
                new ServiceSelectionAdapter(new ServiceSelectionAdapter.ServiceSelectionDiff());

        createServiceSelectionList(servicesAdapter, servicesViewModel);
        bindReadyButton(servicesAdapter, navController, servicesViewModel);
        setOnBackPressEvent(servicesAdapter, navController, servicesViewModel);
    }

    private void createServiceSelectionList(ListAdapter<PhotographerPresentationService, PhotographerServiceSelectionViewHolder> adapter, PhotographerServicesSelectionViewModel servicesViewModel) {

        RecyclerView recycler = binding.recyclerViewServices;
        recycler.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        recycler.setAdapter(adapter);

        servicesViewModel.getAllServices().observe(getViewLifecycleOwner(), adapter::submitList);
    }

    private boolean isValid(RecyclerView recyclerView) {

        for (int idxNum = 0; idxNum < recyclerView.getChildCount(); ++idxNum) {
            View childView = recyclerView.getChildAt(idxNum);
            PhotographerServiceSelectionViewHolder viewHolder = (PhotographerServiceSelectionViewHolder) recyclerView.getChildViewHolder(childView);
            LayoutPhotographerServiceSelectionBinding binding = viewHolder.getBinding();

            String price = binding.editTextPrice.getText().toString();
            if (!price.isEmpty() && price.matches(PictureSnapApp.DIGIT_MATCH) && Integer.parseInt(price) > 0)
                return true;
        }

        return false;
    }

    private void setOnBackPressEvent(ListAdapter<PhotographerPresentationService, PhotographerServiceSelectionViewHolder> adapter, NavController navController, PhotographerServicesSelectionViewModel viewModel) {

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {
                if (isValid(binding.recyclerViewServices)) {
                    viewModel.setStatus(R.string.prices_selected);
                    viewModel.setServices(adapter.getCurrentList());

                }

                navController.navigateUp();
            }
        });
    }

    private void save(ListAdapter<PhotographerPresentationService, PhotographerServiceSelectionViewHolder> adapter, NavController navController, PhotographerServicesSelectionViewModel viewModel) {

        if (isValid(binding.recyclerViewServices)) {
            viewModel.setStatus(R.string.prices_selected);
            viewModel.setServices(adapter.getCurrentList());

            navController.navigateUp();
        } else
            Toast.makeText(requireContext(), R.string.wrong_data, Toast.LENGTH_LONG).show();
    }

    private void bindReadyButton(ListAdapter<PhotographerPresentationService, PhotographerServiceSelectionViewHolder> adapter, NavController navController, PhotographerServicesSelectionViewModel viewModel) {
        binding.layoutReadyButton.buttonReady.setOnClickListener(v -> save(adapter, navController, viewModel));
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }

    private PhotographerServicesSelectionViewModel getPhotographerServicesViewModel() {

        return new ViewModelProvider(requireActivity(),
                new PhotographerServicesSelectionViewModelFactory(getViewLifecycleOwner(),
                        new GetServicesUseCase(new ServicesRepositoryImpl(new ServicesFirebaseDataSource()))))
                .get(PhotographerServicesSelectionViewModel.class);
    }
}