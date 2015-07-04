package com.alamkanak.weekview;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WeekViewEventUtils {
    public static List<WeekViewEvent> createEventByScheduledPerson(Context context, int index, int[] daysOfWeek, int newYear, int newMonth, int color) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (int dayOfWeek : daysOfWeek) {
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, index);
            startTime.set(Calendar.DAY_OF_WEEK, dayOfWeek);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR, 1);
//            endTime.add(Calendar.DAY_OF_WEEK, dayOfWeek);
            endTime.set(Calendar.MONTH, newMonth-1);
            WeekViewEvent event = new WeekViewEvent(index, getEventTitle(startTime), startTime, endTime);
            event.setColor(context.getResources().getColor(color));
            events.add(event);
        }

        return events;
    }

    public static List<WeekViewEvent> createEvents(Context context, ScheduledPerson[] persons, int newYear, int newMonth, int color) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (int i=0;i<persons.length;i++) {
            events.addAll(createEventByScheduledPerson(context, i, persons[i].getScheduledDay(), newYear, newMonth, color));
        }
        return events;
    }

    public static String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }
}
