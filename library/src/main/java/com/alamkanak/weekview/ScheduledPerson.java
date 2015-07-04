package com.alamkanak.weekview;

public class ScheduledPerson {
    private int[] scheduledDay;
    private String name;

    public ScheduledPerson() {}

    public ScheduledPerson(int[] scheduledDay, String name) {
        this.scheduledDay = scheduledDay;
        this.name = name;
    }

    public int[] getScheduledDay() {
        return scheduledDay;
    }

    public void setScheduledDay(int[] scheduledDay) {
        this.scheduledDay = scheduledDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
