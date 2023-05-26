package ru.verso.picturesnap.data.storage.datasources.firebase;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.verso.picturesnap.data.storage.datasources.PhotographerPortfolioPicturesDataSource;
import ru.verso.picturesnap.data.storage.firebase.Constants;
import ru.verso.picturesnap.domain.models.PortfolioImage;

public class PhotographerPortfolioPicturesFirebaseDataSource implements PhotographerPortfolioPicturesDataSource {

    private final DatabaseReference picturesReference;

    public PhotographerPortfolioPicturesFirebaseDataSource() {
        picturesReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_PORTFOLIO_IMAGES_CHILD_PATH);
    }

    private void addNewPictureRecordToFirebase(String pictureUri, String photographerServiceProvisionId) {
        String key = picturesReference.push().getKey();

        if (key != null)
            picturesReference.child(key)
                    .setValue(new PortfolioImage(key, photographerServiceProvisionId, pictureUri));
    }

    @Override
    public void pushNewImage(Uri picture, String photographerServiceId) {
        String pictureName = UUID.randomUUID().toString() + ".jpg";
        StorageReference pictureReference = FirebaseStorage.getInstance().getReference().child(Constants.FIREBASE_PORTFOLIO_PATH + pictureName);

        UploadTask uploadTask = pictureReference.putFile(picture);
        uploadTask.addOnSuccessListener(taskSnapshot -> pictureReference.getDownloadUrl().addOnSuccessListener(uri -> {
            String imageUri = uri.toString();
            addNewPictureRecordToFirebase(imageUri, photographerServiceId);
        }));
    }

    @Override
    public LiveData<List<PortfolioImage>> getAllImagesOf(String photographerServiceId) {
        MutableLiveData<List<PortfolioImage>> allImages = new MutableLiveData<>();

        Query query = picturesReference.orderByChild(Constants.FIREBASE_SERVICE_PROVISION_CHILD_PATH).equalTo(photographerServiceId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<PortfolioImage> images = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PortfolioImage image = dataSnapshot.getValue(PortfolioImage.class);
                    if (image != null)
                        images.add(image);

                    allImages.setValue(images);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return allImages;
    }
}
