package Q2;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Events {
    private HashMap<Date,String> events;
    private Calendar calendar;

    public Events(){
        calendar = Calendar.getInstance();
        events = new HashMap<>();
    }

    //when the user adds an event to a date that already busy - it overrides it, he can just write all the events he has once in the string
    //there was no demand in the mamman to hold a list of events for every day so I am letting to user to hold just one.
    public void addEvent(Date date, String desc){
        events.put(date,desc);
    }

    public String showEvent(Date date){
        return events.get(date);
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void nextMonth(){
        this.calendar.add(Calendar.MONTH,1);
    }

    public void prevMonth(){
        this.calendar.add(Calendar.MONTH,-1);
    }
}
