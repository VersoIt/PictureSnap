package ru.verso.picturesnap.presentation.adapters.photographer;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import ru.verso.picturesnap.databinding.LayoutPortfolioImageBinding;
import ru.verso.picturesnap.domain.models.PortfolioImage;

public class PortfolioImageViewHolder extends RecyclerView.ViewHolder {

    private final LayoutPortfolioImageBinding binding;

    public PortfolioImageViewHolder(LayoutPortfolioImageBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(PortfolioImage portfolioImage) {

        Glide.with(binding.imageViewPicture.getContext())
                .load(portfolioImage.getImageURL())
                .into(binding.imageViewPicture);
    }
}
