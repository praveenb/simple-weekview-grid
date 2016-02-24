package com.praveen.myapplication;

import java.util.Date;

/**
 * Created by employee2 on 23-02-2016.
 */
public class WeekData {


    private String gridVal;
    private String dateOfCalendar;//23/Feb/2016

    public WeekData(String gridVal, String dateOfCalendar) {
        this.gridVal = gridVal;
        this.dateOfCalendar = dateOfCalendar;
    }

    public String getDateOfCalendar() {
        return dateOfCalendar;
    }

    public String getGridVal() {
        return gridVal;
    }
}
