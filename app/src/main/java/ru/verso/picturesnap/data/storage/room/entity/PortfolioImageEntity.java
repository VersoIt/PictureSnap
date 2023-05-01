package ru.verso.picturesnap.data.storage.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ru.verso.picturesnap.domain.models.PortfolioImage;

@Entity
public class PortfolioImageEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public int provisionId;

    @ColumnInfo
    public String imageURL;

    public PortfolioImage mapToDomain() {

        return new PortfolioImage(id, provisionId, imageURL);
    }
}
