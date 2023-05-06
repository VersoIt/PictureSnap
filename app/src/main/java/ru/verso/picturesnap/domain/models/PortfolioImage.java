package ru.verso.picturesnap.domain.models;

public class PortfolioImage {

    private final int id;
    private final int serviceProvisionId;
    private final String imageURL;

    public PortfolioImage(int id, int provisionId, String imageURL) {
        this.id = id;
        this.serviceProvisionId = provisionId;
        this.imageURL = imageURL;
    }

    public int getServiceProvisionId() {
        return serviceProvisionId;
    }

    public int getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }
}
