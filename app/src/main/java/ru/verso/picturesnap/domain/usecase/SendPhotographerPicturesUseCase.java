package ru.verso.picturesnap.domain.usecase;

import android.net.Uri;

import ru.verso.picturesnap.domain.repository.PhotographerPortfolioPicturesRepository;

public class SendPhotographerPicturesUseCase {

    private final PhotographerPortfolioPicturesRepository photographerPortfolioPicturesRepository;

    public SendPhotographerPicturesUseCase(PhotographerPortfolioPicturesRepository photographerPortfolioPicturesRepository) {
        this.photographerPortfolioPicturesRepository = photographerPortfolioPicturesRepository;
    }

    public void send(Uri picture, String servicesPresentationId) {
        photographerPortfolioPicturesRepository.pushNewImage(picture, servicesPresentationId);
    }
}
