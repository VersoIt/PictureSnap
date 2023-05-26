
package ru.verso.picturesnap.presentation.adapters.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserAuthDataRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.data.storage.datasources.firebase.PhotographerFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.firebase.UserAuthFirebaseDataSource;
import ru.verso.picturesnap.data.storage.datasources.room.RoleRoomDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.FirstTimeWentSharedPrefsDataSource;
import ru.verso.picturesnap.data.storage.datasources.sharedprefs.UserLocationSharedPrefsDataSource;
import ru.verso.picturesnap.databinding.LayoutPhotographerBinding;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.presentation.factory.PhotographerProfileFromClientViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographerProfileFromClientViewModel;

public class PhotographersInCityAdapter extends ListAdapter<Photographer, PhotographerViewHolder> {

    private final NavController navController;

    private final FragmentActivity viewModelOwner;

    private final Context context;

    public PhotographersInCityAdapter(Context context, @NonNull DiffUtil.ItemCallback<Photographer> diffCallback, NavController navController, FragmentActivity viewModelOwner) {
        super(diffCallback);

        this.navController = navController;
        this.viewModelOwner = viewModelOwner;

        this.context = context;
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
        PhotographerProfileFromClientViewModel photographerProfileViewModel = getPhotographerViewModel();
        holder.bind(current);

        holder.itemView.setOnClickListener(view -> {
            photographerProfileViewModel.putId(current.getId());
            navController.navigate(R.id.action_unregistered_main_to_photographer_profile_from_unregistered);
        });
    }

    private PhotographerProfileFromClientViewModel getPhotographerViewModel() {
        return new ViewModelProvider(viewModelOwner, new PhotographerProfileFromClientViewModelFactory(new GetPhotographerDataUseCase(
                new PhotographerRepositoryImpl(new PhotographerFirebaseDataSource())),
                new GetUserDataUseCase(new UserLocationRepositoryImpl(new UserLocationSharedPrefsDataSource(context)),
                        new RoleRepositoryImpl(new RoleRoomDataSource(context)),
                        new FirstTimeWentRepositoryImpl(new FirstTimeWentSharedPrefsDataSource(context)),
                        new UserAuthDataRepositoryImpl(new UserAuthFirebaseDataSource()))))
                .get(PhotographerProfileFromClientViewModel.class);
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