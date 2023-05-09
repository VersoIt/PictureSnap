package ru.verso.picturesnap.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Feedback;

public interface FeedbackRepository {

    LiveData<List<Feedback>> getFeedbacksOf(String photographerId);
}
