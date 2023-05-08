
package ru.verso.picturesnap.presentation.adapters.client;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.PhotographRepositoryImpl;
import ru.verso.picturesnap.databinding.LayoutPhotographInCityBinding;
import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographProfileViewModel;
import ru.verso.picturesnap.presentation.factory.PhotographProfileViewModelFactory;

public class PhotographsInCityAdapter extends ListAdapter<Photograph, PhotographInCityViewHolder> {

    private final NavController navController;

    private final FragmentActivity viewModelOwner;

    public PhotographsInCityAdapter(@NonNull DiffUtil.ItemCallback<Photograph> diffCallback, NavController navController, FragmentActivity viewModelOwner) {
        super(diffCallback);

        this.navController = navController;
        this.viewModelOwner = viewModelOwner;
    }

    @NonNull
    @Override
    public PhotographInCityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutPhotographInCityBinding binding = LayoutPhotographInCityBinding.inflate(layoutInflater, parent, false);

        return new PhotographInCityViewHolder(layoutInflater.getContext(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotographInCityViewHolder holder, int position) {
        Photograph current = getItem(position);
        PhotographProfileViewModel photographProfileViewModel = getPhotographViewModel();
        holder.bind(current);

        holder.itemView.setOnClickListener(view -> {
            photographProfileViewModel.putId(current.getId());
            navController.navigate(R.id.action_unregistered_main_to_photograph_profile_from_unregistered);
        });
    }

    private PhotographProfileViewModel getPhotographViewModel() {
        return new ViewModelProvider(viewModelOwner, new PhotographProfileViewModelFactory(new GetPhotographDataUseCase(
                new PhotographRepositoryImpl())))
                .get(PhotographProfileViewModel.class);
    }

    public static class PhotographInCityDiff extends DiffUtil.ItemCallback<Photograph> {

        @Override
        public boolean areItemsTheSame(@NonNull Photograph oldItem, @NonNull Photograph newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Photograph oldItem, @NonNull Photograph newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    }
}