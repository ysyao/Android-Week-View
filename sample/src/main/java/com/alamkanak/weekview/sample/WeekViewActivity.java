package com.alamkanak.weekview.sample;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.adtech.webservice.daomain.Doctor;
import com.adtech.webservice.daomain.WorkSchedule;
import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.ScheduledPerson;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.alamkanak.weekview.WeekViewEventUtils;
import com.google.gson.Gson;

import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * Created by Raquib-ul-Alam Kanak on 7/21/2014.
 * Website: http://alamkanak.github.io/
 */
public class WeekViewActivity extends AppCompatActivity implements WeekView.MonthChangeListener,
        WeekView.EventClickListener, WeekView.EventLongPressListener {

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;
    private List<Doctor> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);
        persons = initPerson();
        mWeekView.setDoctors(persons);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id){
            case R.id.action_today:
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_switch_data:

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    private static final String DOCTORS_JSON = "{\"tList\":[{\"orgNature\":\"1\",\"orgName\":\"四川省肿瘤医院\",\"orgCode\":\"scszlyy\",\"orgShortName\":\"四川省肿瘤医院\",\"staffAvgTime\":\"10\",\"recoupWay\":\"2\",\"feeList\":[{\"priceAmount\":5.0,\"priceTypeName\":\"挂号费\",\"priceId\":\"8999\",\"isSystem\":\"N\"},{\"priceAmount\":0.0,\"priceTypeName\":\"诊疗费\",\"priceId\":\"9000\",\"isSystem\":\"N\"}],\"dateList\":[{\"dutyId\":2573438,\"weekDay_Date\":\"2015-07-15\",\"weekDay_Name\":\"星期三\",\"period_Name\":\"上午\",\"period_Id\":1,\"reg_Number\":50,\"reg_Num_Remain\":50,\"reg_Num_Used\":0,\"typeId\":2002204,\"uuid\":\"ab8ea0ae4cfd45b0bab40e3cbe549020\",\"dutyLimit\":\"Y\"}],\"levelName\":\"主治\",\"staffId\":2022205,\"depId\":4383,\"staffName\":\"华西-外科主任-李静\",\"officeName\":\"血液透析室\",\"officeCategoryName\":\"血液透析室\",\"officeCategoryId\":4382,\"levelId\":2002204,\"orgId\":582,\"hasRegConfirm\":\"Y\",\"sex\":0}],\"messageStatus\":\"1\"}";

    private List<Doctor> initPerson() {
        Gson gson = new Gson();
//        ScheduledPerson person = gson.fromJson(DOCTORS_JSON, ScheduledPerson.class);
        ScheduledPerson person = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("doctors.json"), "UTF-8"));

            // do reading, usually loop until end of file reading
            StringBuilder json = new StringBuilder();
            String mLine = reader.readLine();
            while (mLine != null) {
                //process line
                json.append(mLine);
                mLine = reader.readLine();
            }

            String jsonStr = json.toString();
            person = gson.fromJson(jsonStr, ScheduledPerson.class);
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        if (person != null) {
            return person.getDoctors();
        } else {
            return null;
        }
    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        return WeekViewEventUtils.createEvents(this, persons, android.R.color.holo_green_light);
    }

    private String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        WorkSchedule schedule = (WorkSchedule)event.getObject();
        Toast.makeText(WeekViewActivity.this, "Clicked " + event.getName() + ",dutyId:" + schedule.getDutyId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
//        Toast.makeText(WeekViewActivity.this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

}
