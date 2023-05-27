package ru.verso.picturesnap.presentation.adapters.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutRecordToPhotographerBinding;
import ru.verso.picturesnap.domain.models.Record;
import ru.verso.picturesnap.presentation.app.PictureSnapApp;

public class RecordViewHolder extends RecyclerView.ViewHolder {

    private final LayoutRecordToPhotographerBinding binding;

    public RecordViewHolder(View view) {
        super(view);
        binding = LayoutRecordToPhotographerBinding.bind(view);
    }

    public void bind(RecordsFromClientAdapter.RecordBundle recordBundle) {

        Glide.with(binding.imageViewPhotographerLogo.getContext())
                .load(recordBundle.getPhotographer().getAvatarPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_person_gray)
                .into(binding.imageViewPhotographerLogo);

        binding.textViewPhotographerName.setText(String.format("%s %s", recordBundle.getPhotographer().getFirstName(), recordBundle.getPhotographer().getLastName()));
        binding.textViewDate.setText(new SimpleDateFormat(PictureSnapApp.LONG_DATE_FORMAT, Locale.getDefault()).format(recordBundle.getRecord().getDate()));
        binding.textViewPhotoSessionName.setText(convertResourceNameToResourceId(binding.getRoot().getContext(), recordBundle.getPhotographerPresentationService().getName()));
        binding.textViewStatus.setText(getNameIdOfStatus(recordBundle.getRecord().getStatus()));
        binding.textViewPhotoSessionCost.setText(String.format("%s %s", recordBundle.getPhotographerPresentationService().getCost(), itemView.getResources().getString(R.string.rub_per_hour)));
    }

    private int getNameIdOfStatus(Record.Status status) {
        int[] ids = {
                R.string.pending,
                R.string.accepted,
                R.string.denied
        };

        return ids[status.ordinal()];
    }

    @SuppressLint("DiscouragedApi")
    private int convertResourceNameToResourceId(Context context, String name) {
        String packageName = context.getPackageName();
        Resources resources = context.getResources();

        return resources.getIdentifier(name, "string", packageName);
    }
}
