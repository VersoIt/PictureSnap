package ru.verso.picturesnap.data.storage.firebase.models;

import java.util.Date;

public class Record {

    public enum Status {
        PENDING,
        ACCEPTED,
        DENIED
    }

    private String serviceId;

    private String clientId;

    private Date date;

    private Status status;

    private String comment;

    public Record(String serviceId, String clientId, Date date, Status status, String comment) {
        this.serviceId = serviceId;
        this.clientId = clientId;
        this.date = date;
        this.status = status;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Record() {
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
}
