package ru.verso.picturesnap.data.storage.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ru.verso.picturesnap.domain.models.ServiceProvision;
import ru.verso.picturesnap.domain.models.WorkingDay;

@Entity
public class ServiceProvisionEntity {

    @PrimaryKey(autoGenerate = true)
    public int serviceProvisionId;

    @ColumnInfo
    public int photographId;

    @ColumnInfo
    public int serviceId;

    @ColumnInfo
    public int cost;

    public ServiceProvisionEntity() {
    }

    public ServiceProvisionEntity(int id, int photographId, int serviceId, int cost) {
        this.serviceProvisionId = id;
        this.photographId = photographId;
        this.serviceId = serviceId;
        this.cost = cost;
    }

    public ServiceProvision mapToDomain() {

        return new ServiceProvision(serviceProvisionId, photographId, serviceId, cost);
    }
}
