package ru.verso.picturesnap.presentation.fragments.photograph;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;
import java.util.Objects;

import ru.verso.picturesnap.data.repository.FeedbackRepositoryImpl;
import ru.verso.picturesnap.databinding.FragmentFeedbackFromUnregisteredBinding;
import ru.verso.picturesnap.domain.models.Feedback;
import ru.verso.picturesnap.domain.usecase.GetFeedbackDataUseCase;
import ru.verso.picturesnap.presentation.adapters.client.PhotographFeedbacksAdapter;
import ru.verso.picturesnap.presentation.factory.FeedbackViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.FeedbackViewModel;
import ru.verso.picturesnap.presentation.viewmodel.PhotographProfileViewModel;

public class FeedbacksFromUnregistered extends Fragment {

    private FragmentFeedbackFromUnregisteredBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentFeedbackFromUnregisteredBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FeedbackViewModel viewModel = getFeedbackViewModel();
        PhotographProfileViewModel photographProfileViewModel = getPhotographProfileViewModel();

        PhotographFeedbacksAdapter adapter = new PhotographFeedbacksAdapter(new PhotographFeedbacksAdapter.FeedbacksDiff());
        binding.recyclerViewFeedbacks.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewFeedbacks.setAdapter(adapter);

        photographProfileViewModel.getPhotograph().observe(getViewLifecycleOwner(), photograph ->binding.textViewTotalRating.setText(String.valueOf(photograph.getRating())));

        viewModel.getFeedbacksOfPhotograph().observe(getViewLifecycleOwner(), feedbacks -> {
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

    private PhotographProfileViewModel getPhotographProfileViewModel() {
        return new ViewModelProvider(requireActivity())
                .get(PhotographProfileViewModel.class);
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
                progressBars[idxNum].setProgress((int) (((float)currentStarCount / total) * 100));
            }
        }
    }
}