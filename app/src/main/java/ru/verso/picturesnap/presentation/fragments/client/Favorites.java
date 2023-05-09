package ru.verso.picturesnap.presentation.fragments.client;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.FavoritesRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerRepositoryImpl;
import ru.verso.picturesnap.databinding.FragmentFavoritesBinding;
import ru.verso.picturesnap.domain.usecase.GetFavoritesDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.presentation.adapters.client.FavoritesAdapter;
import ru.verso.picturesnap.presentation.factory.FavoritesViewModelFactory;
import ru.verso.picturesnap.presentation.factory.PhotographerProfileViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.FavoritesViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographerProfileViewModel;

public class Favorites extends Fragment {

    private FragmentFavoritesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FavoritesViewModel favoritesViewModel = getFavoritesViewModel();
        createFavoritesRecycler(favoritesViewModel);
    }

    private void createFavoritesRecycler(FavoritesViewModel favoritesViewModel) {
        RecyclerView recycler = binding.recyclerViewFavorites;
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        FavoritesAdapter favoritesAdapter = new FavoritesAdapter(new FavoritesAdapter.DiffUtil(),
                getNavController(),
                getPhotographerProfileViewModel());
        recycler.setAdapter(favoritesAdapter);
        favoritesViewModel.getAllFavorites().observe(getViewLifecycleOwner(), favoritesAdapter::submitList);
    }

    private FavoritesViewModel getFavoritesViewModel() {

        return new ViewModelProvider(requireActivity(), new FavoritesViewModelFactory(
                new GetFavoritesDataUseCase(
                new FavoritesRepositoryImpl(requireContext()))))
                .get(FavoritesViewModel.class);
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView_content);

        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }

    private PhotographerProfileViewModel getPhotographerProfileViewModel() {

        return new ViewModelProvider(requireActivity(), new PhotographerProfileViewModelFactory(new GetPhotographerDataUseCase(
                new PhotographerRepositoryImpl())))
                .get(PhotographerProfileViewModel.class);
    }
}