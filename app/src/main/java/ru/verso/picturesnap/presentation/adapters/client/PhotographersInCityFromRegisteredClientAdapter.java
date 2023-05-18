
package ru.verso.picturesnap.presentation.adapters.client;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.data.repository.PhotographerRepositoryImpl;
import ru.verso.picturesnap.databinding.LayoutPhotographerBinding;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.presentation.factory.PhotographerProfileViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographerProfileViewModel;

public class PhotographersInCityFromRegisteredClientAdapter extends ListAdapter<Photographer, PhotographerViewHolder> {

    private final NavController navController;

    private final FragmentActivity viewModelOwner;

    private final int fragmentAction;

    public PhotographersInCityFromRegisteredClientAdapter(@NonNull DiffUtil.ItemCallback<Photographer> diffCallback, NavController navController, FragmentActivity viewModelOwner, int fragmentAction) {
        super(diffCallback);

        this.navController = navController;
        this.viewModelOwner = viewModelOwner;
        this.fragmentAction = fragmentAction;
    }

    @NonNull
    @Override
    public PhotographerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutPhotographerBinding binding = LayoutPhotographerBinding.inflate(layoutInflater, parent, false);

        return new PhotographerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotographerViewHolder holder, int position) {
        Photographer current = getItem(position);
        PhotographerProfileViewModel photographerProfileViewModel = getPhotographerViewModel();
        holder.bind(current);

        holder.itemView.setOnClickListener(view -> {
            photographerProfileViewModel.putId(current.getId());
            navController.navigate(fragmentAction);
        });
    }

    private PhotographerProfileViewModel getPhotographerViewModel() {
        return new ViewModelProvider(viewModelOwner, new PhotographerProfileViewModelFactory(new GetPhotographerDataUseCase(
                new PhotographerRepositoryImpl())))
                .get(PhotographerProfileViewModel.class);
    }

    public static class PhotographerInCityDiff extends DiffUtil.ItemCallback<Photographer> {

        @Override
        public boolean areItemsTheSame(@NonNull Photographer oldItem, @NonNull Photographer newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Photographer oldItem, @NonNull Photographer newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    }
}