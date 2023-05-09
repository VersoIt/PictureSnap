package ru.verso.picturesnap.data.repository;

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
import java.util.Objects;

import ru.verso.picturesnap.data.storage.firebase.models.FeedbackEntity;
import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.models.Feedback;
import ru.verso.picturesnap.domain.repository.FeedbackRepository;

public class FeedbackRepositoryImpl implements FeedbackRepository {

    private final DatabaseReference feedbackReference;

    private final DatabaseReference clientReference;

    private static final String FEEDBACK_REF = "feedbacks";

    private static final String CLIENT_REF = "clients";

    public FeedbackRepositoryImpl() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        this.feedbackReference = firebaseDatabase.getReference(FEEDBACK_REF);
        this.clientReference = firebaseDatabase.getReference(CLIENT_REF);
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

                    FeedbackEntity feedbackEntity = feedbackSnapshot.getValue(FeedbackEntity.class);

                    assert feedbackEntity != null;
                    Query clientQuery = clientReference.orderByChild("id").equalTo(feedbackEntity.getOwnerId());
                    String a = feedbackEntity.getOwnerId();
                    clientQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot clientDataSnapshot) {
                            Client client = null;
                            for (DataSnapshot clientSnapshot : clientDataSnapshot.getChildren()) {
                                client = clientSnapshot.getValue(Client.class);
                                break;
                            }

                            Feedback feedback = feedbackEntity.mapToDomain();
                            feedback.setImagePath(Objects.requireNonNull(client).getImagePath());
                            feedback.setOwnerName(String.format("%s %s", client.getFirstName(), client.getLastName()));

                            feedbacks.add(feedback);
                            feedbacksMutableLiveData.setValue(feedbacks);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return feedbacksMutableLiveData;
    }
}
