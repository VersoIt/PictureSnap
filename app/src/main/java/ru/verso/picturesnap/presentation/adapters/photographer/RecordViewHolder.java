package ru.verso.picturesnap.presentation.adapters.photographer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutClientRecordBinding;
import ru.verso.picturesnap.domain.models.Record;
import ru.verso.picturesnap.presentation.app.PictureSnapApp;
import ru.verso.picturesnap.presentation.dialogs.AcceptRecordDialog;
import ru.verso.picturesnap.presentation.dialogs.DismissRecordDialog;

public class RecordViewHolder extends RecyclerView.ViewHolder {

    private final LayoutClientRecordBinding binding;

    private final AcceptRecordDialog acceptRecordDialog;

    private final DismissRecordDialog dismissRecordDialog;

    private final FragmentActivity parent;

    public RecordViewHolder(View view, RecordStatusChanger statusChanger, FragmentActivity parent) {
        super(view);
        binding = LayoutClientRecordBinding.bind(view);

        acceptRecordDialog = new AcceptRecordDialog(statusChanger);
        dismissRecordDialog = new DismissRecordDialog(statusChanger);

        this.parent = parent;
    }

    public void bind(RecordsFromClientAdapter.RecordBundle recordBundle) {
        loadImageAsync(recordBundle);
        initStaticInfo(recordBundle);
        initStatusInfo(recordBundle);
        initStatusButtons(recordBundle.getRecord().getId());
    }

    private void loadImageAsync(RecordsFromClientAdapter.RecordBundle recordBundle) {

        Glide.with(binding.imageViewClientLogo.getContext())
                .load(recordBundle.getClient().getImagePath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_person_gray)
                .into(binding.imageViewClientLogo);
    }

    private void initStatusButtons(String recordId) {

        binding.appCompatButtonDismiss.setOnClickListener(view -> {
            Fragment fragment = parent.getSupportFragmentManager().findFragmentByTag(AcceptRecordDialog.TAG);
            if (fragment == null) {
                dismissRecordDialog.setRecordId(recordId);
                dismissRecordDialog.show(parent.getSupportFragmentManager(), AcceptRecordDialog.TAG);
            }
            
            binding.linearLayoutStatusButtons.setEnabled(false);
        });

        binding.appCompatButtonAccept.setOnClickListener(view -> {
            Fragment fragment = parent.getSupportFragmentManager().findFragmentByTag(AcceptRecordDialog.TAG);
            if (fragment == null) {
                acceptRecordDialog.setRecordId(recordId);
                acceptRecordDialog.show(parent.getSupportFragmentManager(), AcceptRecordDialog.TAG);
            }
            
            binding.linearLayoutStatusButtons.setEnabled(false);
        });
    }

    private void initStaticInfo(RecordsFromClientAdapter.RecordBundle recordBundle) {

        binding.textViewClientName.setText(String.format("%s %s", recordBundle.getClient().getFirstName(), recordBundle.getClient().getLastName()));
        binding.textViewDate.setText(new SimpleDateFormat(PictureSnapApp.LONG_DATE_FORMAT, Locale.getDefault()).format(recordBundle.getRecord().getDate()));
        binding.textViewPhotoSessionName.setText(convertResourceNameToResourceId(binding.getRoot().getContext(), recordBundle.getPhotographerPresentationService().getName()));
        binding.textViewPhotoSessionCost.setText(String.format("%s %s", recordBundle.getPhotographerPresentationService().getCost(), itemView.getResources().getString(R.string.rub_per_hour)));

        if (!recordBundle.getRecord().getComment().isEmpty()) {
            binding.textViewComment.setText(recordBundle.getRecord().getComment());
            binding.textViewComment.setVisibility(View.VISIBLE);
        }
    }

    private void initStatusInfo(RecordsFromClientAdapter.RecordBundle recordBundle) {

        Record.Status status = recordBundle.getRecord().getStatus();

        if (status == Record.Status.ACCEPTED || status == Record.Status.DENIED)
            showStatusText(status);
    }

    private void showStatusText(Record.Status status) {
        binding.linearLayoutStatusButtons.setVisibility(View.GONE);
        binding.textViewStatus.setVisibility(View.VISIBLE);
        binding.textViewStatus.setText(getNameIdOfStatus(status));
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

    public interface RecordStatusChanger {

        void changeRecordStatusTo(String recordId, Record.Status status);
    }
}