package com.example.demo.service;

import java.time.Duration;
import java.time.Instant;

public class TimeService {

    public static TimeDuration compareTwoDates(long dateA, long dateB){
//        long l1 = Long.parseLong("1537803359567");
//        long l2 = Long.parseLong("1537932259145");
        Instant start = Instant.ofEpochMilli(dateA);
        Instant end = Instant.ofEpochMilli(dateB);
//        Instant start = Instant.parse("2017-10-03T22:15:30.00Z");
//        Instant end = Instant.parse("2017-10-04T10:18:40.30Z");

        Duration duration = Duration.between(start, end);
        Integer hours = ((Long) duration.toHours()).intValue();
        int minutes = (int) (duration.getSeconds() % 60);
//
//        System.out.println(new Date(l1).toString());
//        System.out.println(new Date(l2).toString());
//        System.out.println(hours + ":" + minutes);
        return new TimeDuration(hours,minutes,null);
    }
}

class TimeDuration{
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

}
