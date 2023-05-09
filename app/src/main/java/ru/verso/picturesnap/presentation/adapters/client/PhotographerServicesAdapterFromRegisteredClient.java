package ru.verso.picturesnap.presentation.adapters.client;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutPhotographerServiceBinding;
import ru.verso.picturesnap.domain.models.PhotographerService;
import ru.verso.picturesnap.presentation.viewmodel.client.ClientPhotographersOfSelectedServiceViewModel;

public class PhotographerServicesAdapterFromRegisteredClient extends ListAdapter<PhotographerService, PhotographerServiceViewHolder> {

    private final NavController navController;

    private final ClientPhotographersOfSelectedServiceViewModel clientRecordsViewModel;

    public PhotographerServicesAdapterFromRegisteredClient(@NonNull DiffUtil.ItemCallback<PhotographerService> diffCallback, NavController navController, ClientPhotographersOfSelectedServiceViewModel clientRecordsViewModel) {
        super(diffCallback);
        this.navController = navController;
        this.clientRecordsViewModel = clientRecordsViewModel;
    }

    @NonNull
    @Override
    public PhotographerServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutPhotographerServiceBinding binding = LayoutPhotographerServiceBinding.inflate(layoutInflater, parent, false);

        return new PhotographerServiceViewHolder(layoutInflater.getContext(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotographerServiceViewHolder holder, int position) {
        PhotographerService current = getItem(position);
        holder.bind(current);

        holder.itemView.setOnClickListener(v -> {
            clientRecordsViewModel.putServiceId(getItem(position).getId());
            navController.navigate(R.id.action_client_main_to_photographersOfSelectedService2);
        });
    }

    public static class PhotographerServiceDiff extends DiffUtil.ItemCallback<PhotographerService> {

        @Override
        public boolean areItemsTheSame(@NonNull PhotographerService oldItem, @NonNull PhotographerService newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PhotographerService oldItem, @NonNull PhotographerService newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}