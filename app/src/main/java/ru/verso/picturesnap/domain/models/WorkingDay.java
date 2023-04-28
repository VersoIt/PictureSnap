package ru.verso.picturesnap.domain.models;

public class WorkingDay {

    private int photographId;
    private int dayId;
    private int hourStart;
    private int hourEnd;
    private int minuteStart;
    private int minuteEnd;

    public WorkingDay() {
    }

    public void setPhotographId(int photographId) {
        this.photographId = photographId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public void setHourStart(int hourStart) {
        this.hourStart = hourStart;
    }

    public void setHourEnd(int hourEnd) {
        this.hourEnd = hourEnd;
    }

    public void setMinuteStart(int minuteStart) {
        this.minuteStart = minuteStart;
    }

    public void setMinuteEnd(int minuteEnd) {
        this.minuteEnd = minuteEnd;
    }

    public int getPhotographId() {
        return photographId;
    }

    public int getDayId() {
        return dayId;
    }

    public int getHourStart() {
        return hourStart;
    }

    public int getHourEnd() {
        return hourEnd;
    }

    public int getMinuteStart() {
        return minuteStart;
    }

    public int getMinuteEnd() {
        return minuteEnd;
    }

    public static class Builder {

        private final WorkingDay workingDay;

        public Builder() {
            this.workingDay = new WorkingDay();
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

        public WorkingDay create() {
            return workingDay;
        }
    }
}
