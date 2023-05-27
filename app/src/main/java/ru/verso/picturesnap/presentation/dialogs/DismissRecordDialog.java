package ru.verso.picturesnap.presentation.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.domain.models.Record;
import ru.verso.picturesnap.presentation.adapters.photographer.RecordViewHolder;

public class DismissRecordDialog extends DialogFragment {

    public static final String TAG = "DismissRecordDialog";

    private final RecordViewHolder.RecordStatusChanger recordStatusChanger;

    private String recordId;

    public DismissRecordDialog(RecordViewHolder.RecordStatusChanger recordStatusChanger) {
        this.recordStatusChanger = recordStatusChanger;
    }

    public void setRecordId(String record) {
        this.recordId = record;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.confirm_action)
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(R.string.cancel_photo_session)
                .setPositiveButton(R.string.Ok, (dialog, which) -> recordStatusChanger.changeRecordStatusTo(recordId, Record.Status.DENIED))
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());

        return builder.create();
    }
}
