package com.alamkanak.weekview;

import android.content.Context;

import com.adtech.webservice.daomain.Doctor;
import com.adtech.webservice.daomain.WorkSchedule;

import java.lang.annotation.Documented;
import java.math.BigDecimal;
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

    public static List<WeekViewEvent> createEventByDate(Context context, int index, List<WorkSchedule> schedules, int color) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (WorkSchedule schedule : schedules) {
            String[] dateArray = schedule.getWeekDay_Date().split("-");
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.YEAR, Integer.valueOf(dateArray[0]));
            startTime.set(Calendar.MONTH, Integer.valueOf(dateArray[1])-1);
            startTime.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dateArray[2]));
            startTime.set(Calendar.HOUR_OF_DAY, index);
            if (schedule.getPeriod_Id().equals(BigDecimal.valueOf(2))) {
//                startTime.set(Calendar.HOUR_OF_DAY, index);
                startTime.add(Calendar.MINUTE, 30);
            }
            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.MINUTE, 30);
            WeekViewEvent event = new WeekViewEvent(index, schedule.getReg_Num_Remain() + "/" + schedule.getReg_Number(), startTime, endTime);
            event.setColor(context.getResources().getColor(color));
            events.add(event);
        }
        return events;
    }
    public static List<WeekViewEvent> createEvents(Context context, List<Doctor> doctors, int color) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (int i=0;i<doctors.size();i++) {
           List<WorkSchedule> workSchedules = doctors.get(i).getDateList();
            events.addAll(createEventByDate(context, i, workSchedules, color));
        }
        return events;
    }

    public static String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }
}
