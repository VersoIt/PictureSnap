package ru.verso.picturesnap.data.storage.firebase.models;

public class PortfolioImage {

    private String id;
    private String serviceProvisionId;
    private String imageURL;

    public PortfolioImage(String id, String provisionId, String imageURL) {
        this.id = id;
        this.serviceProvisionId = provisionId;
        this.imageURL = imageURL;
    }

    public PortfolioImage() {
    }

    public String getServiceProvisionId() {
        return serviceProvisionId;
    }

    public String getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setServiceProvisionId(String serviceProvisionId) {
        this.serviceProvisionId = serviceProvisionId;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
