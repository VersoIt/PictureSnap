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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;
import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.data.repository.ClientRepositoryImpl;
import ru.verso.picturesnap.data.repository.FeedbackRepositoryImpl;
import ru.verso.picturesnap.data.repository.FirstTimeWentRepositoryImpl;
import ru.verso.picturesnap.data.repository.PhotographerRepositoryImpl;
import ru.verso.picturesnap.data.repository.RoleRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserAuthDataRepositoryImpl;
import ru.verso.picturesnap.data.repository.UserLocationRepositoryImpl;
import ru.verso.picturesnap.databinding.FragmentFeedbacksFromClientBinding;
import ru.verso.picturesnap.domain.models.Feedback;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.usecase.GetClientDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.SendFeedbackUseCase;
import ru.verso.picturesnap.presentation.adapters.client.PhotographerFeedbacksAdapter;
import ru.verso.picturesnap.presentation.factory.SendFeedbackViewModelFactory;
import ru.verso.picturesnap.presentation.viewmodel.client.SendFeedbackViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.FeedbackViewModel;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographerProfileViewModel;

public class FeedbacksFromClient extends Fragment {

    private FragmentFeedbacksFromClientBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFeedbacksFromClientBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FeedbackViewModel viewModel = getFeedbackViewModel();
        PhotographerProfileViewModel photographerProfileViewModel = getPhotographerProfileViewModel();

        PhotographerFeedbacksAdapter adapter = new PhotographerFeedbacksAdapter(new PhotographerFeedbacksAdapter.FeedbacksDiff());
        binding.recyclerViewFeedbacks.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewFeedbacks.setAdapter(adapter);

        NavController navController = getNavController();

        photographerProfileViewModel.getPhotographer().observe(getViewLifecycleOwner(), photographer -> {
            binding.textViewTotalRating.setText(String.valueOf(photographer.getRating()));
            bindWriteNewFeedbackButton(getSendFeedbackViewModel(), navController, photographer);
        });

        viewModel.getFeedbacksOfPhotographer().observe(getViewLifecycleOwner(), feedbacks -> {
            if (feedbacks.size() > 0) {
                binding.textViewNoFeedbacks.setVisibility(View.GONE);
                adapter.submitList(feedbacks);
                updateProgress(feedbacks);
            }
        });
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView_content);
        return Objects.requireNonNull(navHostFragment).getNavController();
    }

    private void bindWriteNewFeedbackButton(SendFeedbackViewModel sendFeedbackViewModel, NavController navController, Photographer photographer) {
        sendFeedbackViewModel.putPhotographerDestinationId(photographer.getId());
        binding.textViewWriteFeedback.setOnClickListener(view ->
                navController.navigate(R.id.action_feedbacksFromClient_to_sendFeedback));
    }

    private FeedbackViewModel getFeedbackViewModel() {
        return new ViewModelProvider(requireActivity())
                .get(FeedbackViewModel.class);
    }

    private SendFeedbackViewModel getSendFeedbackViewModel() {

        return new ViewModelProvider(requireActivity(), new SendFeedbackViewModelFactory(new SendFeedbackUseCase(new FeedbackRepositoryImpl()),
                new GetUserDataUseCase(new UserLocationRepositoryImpl(requireContext()),
                        new RoleRepositoryImpl(requireContext()),
                        new FirstTimeWentRepositoryImpl(requireContext()),
                        new UserAuthDataRepositoryImpl()),
                new GetClientDataUseCase(new ClientRepositoryImpl()),
                new GetPhotographerDataUseCase(new PhotographerRepositoryImpl()))).get(SendFeedbackViewModel.class);
    }

    private PhotographerProfileViewModel getPhotographerProfileViewModel() {
        return new ViewModelProvider(requireActivity())
                .get(PhotographerProfileViewModel.class);
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