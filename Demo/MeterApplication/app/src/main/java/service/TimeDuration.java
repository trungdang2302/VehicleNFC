package service;

import java.time.Duration;
import java.time.Instant;

public class TimeDuration {
    Integer hour;
    Integer minute;
    Integer second;

    public TimeDuration(Integer hour, Integer minute, Integer second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public long toMilisecond() {
        return this.hour * 3600000 + this.minute * 60000;
    }

    public static TimeDuration compareTwoDates(long dateA, long dateB) {
        long seconds = (dateB - dateA) / 1000;
        int minutes = (int) (seconds / 60);
        int hours = (int) (minutes / 60);
        minutes = minutes - 60 * hours;

        return new TimeDuration(hours, minutes, null);
    }
}
