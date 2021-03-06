package com.example.jeremy.wakemeup;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Jeremy on 7/23/2016.
 */
public class AlarmObjectData implements Serializable
{

    private String name;
    private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    private int challengeLevel;
    private int frequency;
    private boolean activeStatus;
    private String alarmTone;



    public AlarmObjectData(String name, GregorianCalendar startTime, GregorianCalendar endTime, int frequency)
    {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.frequency = frequency;
    }

     AlarmObjectData(String name, GregorianCalendar startTime, GregorianCalendar endTime, int frequency, boolean activeStatus, int challengeLevel, String alarmTone) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.frequency = frequency;
        this.activeStatus = activeStatus;
        this.challengeLevel = challengeLevel;
        this.alarmTone = alarmTone;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GregorianCalendar getStartTime() {
        return startTime;
    }

    public int getStartHour() {
        int startHour = startTime.get(Calendar.HOUR_OF_DAY);
        return startHour;
    }
    public void setStartHour(int startHour) {
        startTime.set(Calendar.HOUR_OF_DAY, startHour);
    }

    public int getStartMinute() {
        int startMinute = startTime.get(Calendar.MINUTE);
        return startMinute;
    }
    public void setStartMinute(int startMinute) {
        startTime.set(Calendar.MINUTE, startMinute);
    }

    public int getEndHour() {
        int endHour = endTime.get(Calendar.HOUR_OF_DAY);
        return endHour;
    }
    public void setEndHour(int endHour) {
        endTime.set(Calendar.HOUR_OF_DAY, endHour);
    }

    public int getEndMinute() {
        int endMinute = endTime.get(Calendar.MINUTE);
        return endMinute;
    }
    public void setEndMinute(int endMinute) {
        endTime.set(Calendar.MINUTE, endMinute);
    }


    public void setStartTime(GregorianCalendar startTime) {
        this.startTime = startTime;
    }

    public GregorianCalendar getEndTime() {
        return endTime;
    }

    public void setEndTime(GregorianCalendar endTime) {
        this.endTime = endTime;
    }

    public int getChallengeLevel() {
        return challengeLevel;
    }

    public void setChallengeLevel(int challengeLevel) {
        this.challengeLevel = challengeLevel;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getAlarmTone() {
        return alarmTone;
    }

    public void setAlarmTone(String alarmTone) {
        this.alarmTone = alarmTone;
    }
}
