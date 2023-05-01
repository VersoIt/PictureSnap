package ru.verso.picturesnap.domain.models;

public class PortfolioImage {

    private final int provisionId;
    private final int id;
    private final String imageURL;

    public PortfolioImage(int id, int provisionId, String imageURL) {
        this.id = id;
        this.provisionId = provisionId;
        this.imageURL = imageURL;
    }

    public int getProvisionId() {
        return provisionId;
    }

    public int getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }
}
