package ru.verso.picturesnap.data.repository;

import ru.verso.picturesnap.data.storage.datasources.FirstTimeWentDataSource;
import ru.verso.picturesnap.domain.repository.FirstTimeWentRepository;

public class FirstTimeWentRepositoryImpl implements FirstTimeWentRepository {

    private final FirstTimeWentDataSource firstTimeWentDataSource;

    public FirstTimeWentRepositoryImpl(FirstTimeWentDataSource firstTimeWentDataSource) {
        this.firstTimeWentDataSource = firstTimeWentDataSource;
    }

    @Override
    public boolean isFirst() {
        return firstTimeWentDataSource.isFirst();
    }

    @Override
    public void setVisited() {
        firstTimeWentDataSource.setVisited();
    }
}
