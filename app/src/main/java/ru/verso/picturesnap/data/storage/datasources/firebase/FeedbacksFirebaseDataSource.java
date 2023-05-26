package ru.verso.picturesnap.data.storage.datasources.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ru.verso.picturesnap.data.storage.datasources.FeedbacksDataSource;
import ru.verso.picturesnap.data.storage.firebase.Constants;
import ru.verso.picturesnap.domain.models.Feedback;

public class FeedbacksFirebaseDataSource implements FeedbacksDataSource {

    private final DatabaseReference feedbackReference;

    public FeedbacksFirebaseDataSource() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        this.feedbackReference = firebaseDatabase.getReference(Constants.FIREBASE_FEEDBACKS_PATH);
    }

    @Override
    public LiveData<List<Feedback>> getFeedbacksOf(String photographerId) {
        final String PHOTOGRAPH_ID_PATH = "photographerId";

        MutableLiveData<List<Feedback>> feedbacksMutableLiveData = new MutableLiveData<>();
        Query feedbackQuery = feedbackReference.orderByChild(PHOTOGRAPH_ID_PATH).equalTo(photographerId);
        feedbackQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Feedback> feedbacks = new ArrayList<>();
                for (DataSnapshot feedbackSnapshot : snapshot.getChildren()) {

                    Feedback feedbackEntity = feedbackSnapshot.getValue(Feedback.class);
                    if (feedbackEntity != null) {
                        feedbacks.add(feedbackEntity);
                    }

                }
                feedbacks = feedbacks.stream().sorted((a, b) -> -a.getDate().compareTo(b.getDate())).collect(Collectors.toList());
                feedbacksMutableLiveData.setValue(feedbacks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return feedbacksMutableLiveData;
    }

    @Override
    public void sendFeedback(Feedback feedback) {
        String id = feedbackReference.push().getKey();

        if (id != null) {
            feedback.setId(id);
            feedbackReference.child(id).setValue(feedback);
        }
    }
}
