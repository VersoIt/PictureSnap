package ru.verso.picturesnap.domain.usecase;

import ru.verso.picturesnap.domain.models.Feedback;
import ru.verso.picturesnap.domain.repository.FeedbackRepository;

public class SendFeedbackUseCase {

    private final FeedbackRepository feedbackRepository;

    public SendFeedbackUseCase(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public void send(Feedback feedback) {
        feedbackRepository.sendFeedback(feedback);
    }
}