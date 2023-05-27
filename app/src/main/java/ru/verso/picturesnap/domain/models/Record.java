package ru.verso.picturesnap.domain.models;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.Objects;

public class Record {

    public enum Status {
        PENDING,
        ACCEPTED,
        DENIED
    }

    private String serviceId;

    private String clientId;

    private String id;

    private Date date;

    private Status status;

    private String photographerId;

    private String comment;

    private boolean isHiddenForPhotographer;

    private boolean isHiddenForClient;

    public Record(String serviceId, String clientId, String photographerId, Date date, Status status, String comment) {
        this.serviceId = serviceId;
        this.clientId = clientId;
        this.date = date;
        this.status = status;
        this.comment = comment;
        this.photographerId = photographerId;

        isHiddenForClient = false;
        isHiddenForPhotographer = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public String getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(String photographerId) {
        this.photographerId = photographerId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Record() {
        isHiddenForClient = false;
        isHiddenForPhotographer = false;
    }

    public boolean isHiddenForPhotographer() {
        return isHiddenForPhotographer;
    }

    public void setHiddenForPhotographer(boolean hiddenForPhotographer) {
        isHiddenForPhotographer = hiddenForPhotographer;
    }

    public boolean isHiddenForClient() {
        return isHiddenForClient;
    }

    public void setHiddenForClient(boolean hiddenForClient) {
        isHiddenForClient = hiddenForClient;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, clientId, date, status, comment);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;

        if (obj instanceof Record) {
            Record record = (Record) obj;

            return serviceId.equals(record.serviceId) &&
                    clientId.equals(record.clientId) &&
                    date.equals(record.date) &&
                    status.equals(record.status) &&
                    comment.equals(record.comment);
        }

        return false;
    }
}
