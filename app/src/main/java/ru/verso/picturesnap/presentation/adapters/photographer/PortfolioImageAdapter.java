package ru.verso.picturesnap.presentation.adapters.photographer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import ru.verso.picturesnap.databinding.LayoutPortfolioImageBinding;
import ru.verso.picturesnap.domain.models.PortfolioImage;

public class PortfolioImageAdapter extends ListAdapter<PortfolioImage, PortfolioImageViewHolder> {

    private final FragmentManager fragmentManager;

    public PortfolioImageAdapter(@NonNull DiffUtil.ItemCallback<PortfolioImage> diffCallback, FragmentManager fragmentManager) {
        super(diffCallback);
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public PortfolioImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LayoutPortfolioImageBinding binding = LayoutPortfolioImageBinding.inflate(inflater);

        return new PortfolioImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PortfolioImageViewHolder holder, int position) {
        holder.bind(getItem(position), fragmentManager);
    }

    public static class PortfolioImageDiff extends DiffUtil.ItemCallback<PortfolioImage> {

        @Override
        public boolean areItemsTheSame(@NonNull PortfolioImage oldItem, @NonNull PortfolioImage newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PortfolioImage oldItem, @NonNull PortfolioImage newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    }
}
