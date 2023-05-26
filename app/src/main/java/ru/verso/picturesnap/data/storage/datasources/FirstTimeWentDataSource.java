package ru.verso.picturesnap.data.storage.datasources;

public interface FirstTimeWentDataSource {

    boolean isFirst();

    void setVisited();
}
