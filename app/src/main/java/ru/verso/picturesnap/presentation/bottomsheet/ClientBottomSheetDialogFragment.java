package ru.verso.picturesnap.presentation.bottomsheet;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutBottomSheetBinding;

public class ClientBottomSheetDialogFragment extends BottomSheetDialogFragment {

    public static final String TAG = "ClientBottomSheet";

    private NavController navController;

    private int fragmentId;

    public ClientBottomSheetDialogFragment(int fragmentId) {
        this.fragmentId = fragmentId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        ru.verso.picturesnap.databinding.LayoutBottomSheetBinding binding = LayoutBottomSheetBinding.inflate(getLayoutInflater());

        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.fragmentContainerView_bottom_sheet_content);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        navController.navigate(fragmentId);

        dialog.setContentView(binding.getRoot());
        return dialog;
    }

    public void changeFragment(int fragmentId) {
        this.fragmentId = fragmentId;
        navController.navigate(fragmentId);
    }
}
