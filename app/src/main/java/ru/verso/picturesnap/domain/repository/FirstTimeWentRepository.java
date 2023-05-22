package ru.verso.picturesnap.domain.repository;

public interface FirstTimeWentRepository {

    boolean isFirst();

    void setVisited();
}