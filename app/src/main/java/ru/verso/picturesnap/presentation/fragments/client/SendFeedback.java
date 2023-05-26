package ru.verso.picturesnap.presentation.fragments.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentSendFeedbackBinding;
import ru.verso.picturesnap.presentation.viewmodel.client.SendFeedbackViewModel;

public class SendFeedback extends Fragment {

    private FragmentSendFeedbackBinding binding;

    private SendFeedbackViewModel sendFeedbackViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSendFeedbackBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = getNavController();

        sendFeedbackViewModel = getViewModel();
        binding.appCompatButtonSend.buttonSend.setOnClickListener(v -> {
            if (!sendFeedbackViewModel.isEmpty(Objects.requireNonNull(binding.editTextFeedbackText.getText()).toString()))
            {
                binding.appCompatButtonSend.buttonSend.setEnabled(false);
                sendFeedbackViewModel.getPhotographer().observe(getViewLifecycleOwner(), photographer -> sendFeedbackViewModel.getAllFeedbacks().observe(getViewLifecycleOwner(), feedbacks -> {
                    sendFeedbackViewModel.getFeedbackToSend(Objects.requireNonNull(binding.editTextFeedbackText.getText()).toString()).observe(getViewLifecycleOwner(), feedback -> {
                        sendFeedbackViewModel.sendFeedback(feedback, photographer.getRating(), feedbacks.size());
                        navController.navigateUp();
                    });
                }));

                return;
            }

            Toast.makeText(requireActivity(), R.string.incorrect_feedback, Toast.LENGTH_LONG).show();
        });

        bindRatingSelectionButton(sendFeedbackViewModel);
    }

    private void bindRatingSelectionButton(SendFeedbackViewModel viewModel) {
        ViewGroup root = binding.linearLayoutRatingSelection.getRoot();
        int childCount = root.getChildCount();
        for (int idxNum = 0; idxNum < childCount; ++idxNum) {
            int finalIdxNum = idxNum;
            root.getChildAt(finalIdxNum).setOnClickListener(view -> viewModel.setRating(finalIdxNum + 1));
        }

        viewModel.getRating().observe(getViewLifecycleOwner(), rating -> setRatingStars(rating, root));
    }

    private void setRatingStars(int rating, ViewGroup root) {
        int childCount = root.getChildCount();
        for (int idxNum = 0; idxNum < rating; ++idxNum) {
            (root.getChildAt(idxNum)).setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_yellow));
        }
        for (int idxNum = rating; idxNum < childCount; ++idxNum) {
            (root.getChildAt(idxNum)).setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.ic_star));
        }
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView_content);
        assert navHostFragment != null;
        return navHostFragment.getNavController();
    }

    private SendFeedbackViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(SendFeedbackViewModel.class);
    }
}