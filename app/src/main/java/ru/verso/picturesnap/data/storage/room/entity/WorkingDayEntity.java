package ru.verso.picturesnap.data.storage.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ru.verso.picturesnap.domain.models.WorkingDay;

@Entity
public class WorkingDayEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public int photographId;

    @ColumnInfo
    public int dayId;

    @ColumnInfo
    public int hourStart;

    @ColumnInfo
    public int hourEnd;

    @ColumnInfo
    public int minuteStart;

    @ColumnInfo
    public int minuteEnd;

    public WorkingDayEntity() {
    }

    public WorkingDay mapToDomain() {

        return new WorkingDay.Builder()
                .setPhotographId(photographId)
                .setHourStart(hourStart)
                .setMinuteStart(minuteStart)
                .setHourEnd(hourEnd)
                .setMinuteEnd(minuteEnd)
                .setDayId(dayId)
                .create();
    }

    public static class Builder {

        private final WorkingDayEntity workingDay;

        public Builder() {
            this.workingDay = new WorkingDayEntity();
        }

        public Builder setPhotographId(int id) {
            workingDay.photographId = id;
            return this;
        }

        public Builder setDayId(int id) {
            workingDay.dayId = id;
            return this;
        }

        public Builder setHourStart(int hourStart) {
            workingDay.hourStart = hourStart;
            return this;
        }

        public Builder setHourEnd(int hourEnd) {
            workingDay.hourEnd = hourEnd;
            return this;
        }

        public Builder setMinuteStart(int minuteStart) {
            workingDay.minuteStart = minuteStart;
            return this;
        }

        public Builder setMinuteEnd(int minuteEnd) {
            workingDay.minuteEnd = minuteEnd;
            return this;
        }

        public Builder setId(int id) {
            workingDay.id = id;
            return this;
        }

        public WorkingDayEntity create() {
            return workingDay;
        }
    }
}
