package ru.verso.picturesnap.presentation.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.verso.picturesnap.R;

public class SignOutDialogFragment extends DialogFragment {

    public static final String TAG = "SignOutDialogFragment";

    private final DialogInterface.OnClickListener positiveListener;

    public SignOutDialogFragment(DialogInterface.OnClickListener positiveListener) {
        this.positiveListener = positiveListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.leave)
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(R.string.sign_out_confirmation)
                .setPositiveButton(R.string.leave, positiveListener)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dismiss());

        return builder.create();
    }
}
