package ru.verso.picturesnap.presentation.fragments.photographer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentFeedbacksFromUnregisteredBinding;
import ru.verso.picturesnap.domain.models.Feedback;
import ru.verso.picturesnap.presentation.adapters.client.PhotographerFeedbacksAdapter;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.FeedbackViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographerProfileFromClientViewModel;

public class FeedbacksFromUnregistered extends Fragment {

    private FragmentFeedbacksFromUnregisteredBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentFeedbacksFromUnregisteredBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FeedbackViewModel viewModel = getFeedbackViewModel();
        PhotographerProfileFromClientViewModel photographerProfileViewModel = getPhotographerProfileViewModel();

        PhotographerFeedbacksAdapter adapter = new PhotographerFeedbacksAdapter(new PhotographerFeedbacksAdapter.FeedbacksDiff());
        binding.recyclerViewFeedbacks.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewFeedbacks.setAdapter(adapter);

        photographerProfileViewModel.getPhotographer().observe(getViewLifecycleOwner(), photographer -> binding.textViewTotalRating.setText(String.valueOf(photographer.getRating())));

        viewModel.getFeedbacksOfPhotographer().observe(getViewLifecycleOwner(), feedbacks -> {
            if (feedbacks.size() > 0) {
                binding.textViewNoFeedbacks.setVisibility(View.GONE);
                adapter.submitList(feedbacks);
                updateProgress(feedbacks);
            }
        });

        viewModel.getFeedbacksOfPhotographer().observe(getViewLifecycleOwner(), feedbacks -> {
            binding.textViewRatingsCount.setText(String.format("%s %s", feedbacks.size(), getResources().getString(R.string.ratings_count)));
            if (feedbacks.size() > 0) {
                binding.textViewNoFeedbacks.setVisibility(View.GONE);
                adapter.submitList(feedbacks);
                updateProgress(feedbacks);
            }
        });
    }

    private FeedbackViewModel getFeedbackViewModel() {
        return new ViewModelProvider(requireActivity())
                .get(FeedbackViewModel.class);
    }

    private PhotographerProfileFromClientViewModel getPhotographerProfileViewModel() {
        return new ViewModelProvider(requireActivity())
                .get(PhotographerProfileFromClientViewModel.class);
    }

    private void updateProgress(List<Feedback> feedbacks) {
        int total = feedbacks.size();
        if (total > 0) {

            ProgressBar[] progressBars = {
                    binding.progressBarOneStar,
                    binding.progressBarTwoStar,
                    binding.progressBarThreeStar,
                    binding.progressBarFourStar,
                    binding.progressBarFiveStar
            };

            for (int idxNum = 0; idxNum < progressBars.length; ++idxNum) {
                final int currentStar = idxNum + 1;
                int currentStarCount = (int) feedbacks.stream().filter(feedback -> feedback.getRating() == currentStar).count();
                progressBars[idxNum].setProgress((int) (((float) currentStarCount / total) * 100));
            }
        }
    }
}