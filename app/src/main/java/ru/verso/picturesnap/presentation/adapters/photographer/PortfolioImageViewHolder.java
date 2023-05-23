package ru.verso.picturesnap.presentation.adapters.photographer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutBottomSheetBinding;
import ru.verso.picturesnap.databinding.LayoutPortfolioImageBinding;
import ru.verso.picturesnap.databinding.LayoutPortfolioScaledImageBinding;
import ru.verso.picturesnap.domain.models.PortfolioImage;

public class PortfolioImageViewHolder extends RecyclerView.ViewHolder {

    private final LayoutPortfolioImageBinding binding;

    public PortfolioImageViewHolder(LayoutPortfolioImageBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(PortfolioImage portfolioImage, FragmentManager fragmentManager) {

        Glide.with(binding.imageViewPicture.getContext())
                .load(portfolioImage.getImageURL())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .fitCenter()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        binding.imageViewPicture.setOnClickListener(view -> new ImageDialog(binding.imageViewPicture.getContext(), resource).show(fragmentManager,  ImageDialog.TAG));
                        return false;
                    }
                })
                .into(binding.imageViewPicture);
    }

    public static class ImageDialog extends DialogFragment {

        public static final String TAG = "ImageDialog";

        private final Drawable resource;

        private final Context context;

        public ImageDialog(Context context, Drawable resource) {
            this.resource = resource;
            this.context = context;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            LayoutPortfolioScaledImageBinding binding = LayoutPortfolioScaledImageBinding.inflate(getLayoutInflater());
            AutoSizableAlertDialogBuilder dialogBuilder = (AutoSizableAlertDialogBuilder) new AutoSizableAlertDialogBuilder(context).setView(binding.getRoot());
            binding.imageViewPicture.setImageDrawable(resource);

            return dialogBuilder.create();
        }
    }

    private static class AutoSizableAlertDialogBuilder extends AlertDialog.Builder {

        public AutoSizableAlertDialogBuilder(Context context) {
            super(context);
        }

        @Override
        public AlertDialog create() {
            AlertDialog alertDialog = super.create();

            WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

            alertDialog.getWindow().setAttributes(layoutParams);
            return alertDialog;
        }
    }
}
