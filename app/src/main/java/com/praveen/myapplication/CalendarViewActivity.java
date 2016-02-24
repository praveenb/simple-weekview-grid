package com.praveen.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarViewActivity extends AppCompatActivity {

    /**
     *
     */
    private GregorianCalendar month;// calendar instances.
    /**
     *
     */
    private CalendarAdapter2 adapter;// adapter instance
    /**
     *
     */
    public static int selectedDayPos;
    /**
     *
     */
    private GridView gridview;
    /**
     *
     */
    private boolean isLongClicked = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);
        Locale.setDefault(Locale.US);
        DateUtil.initAppDateLocales();
        month = (GregorianCalendar) GregorianCalendar.getInstance();

        adapter = new CalendarAdapter2(this, month);

        gridview = (GridView) findViewById(R.id.gridview_days);
        gridview.setAdapter(adapter);

       /* TextView title = (TextView) findViewById(R.id.title);
        //android.text.format.DateFormat.format("MMMM yyyy", month)
        title.setText(DateUtil.genDateStringMMMMyyyy(month));*/

        RelativeLayout previous = (RelativeLayout) findViewById(R.id.previous);

        previous.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                month=adapter.getVisibleFirstDayOfCalendar();
                month.add(Calendar.DAY_OF_YEAR, -7);
                String val= DateUtil.getFormattedDate(month.getTime());
                Log.d("CalendarViewActivity", "getVisibleFirstDayOfCalendar " + val );
                adapter = new CalendarAdapter2(CalendarViewActivity.this, month);
                gridview.setAdapter(adapter);
            }
        });

        RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                month=adapter.getVisibleLastDayOfCalendar();
                month.add(Calendar.DAY_OF_YEAR, 1);
                String val= DateUtil.getFormattedDate(month.getTime());
                Log.d("CalendarViewActivity", "getVisibleLastDayOfCalendar " + val );
                adapter = new CalendarAdapter2(CalendarViewActivity.this, month);
                gridview.setAdapter(adapter);

            }
        });


        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

               /* String selectedGridDate = CalendarAdapter.dayString.get(position);
                Log.d("CalendarViewActivity", "Selected Grid Date " + selectedGridDate);
                String[] separatedTime = selectedGridDate.split("/");
                String gridvalueString = separatedTime[0].replaceFirst("^0*", "");// taking first part of date. ie; 2 from 22-01-2014.
                int gridvalue = Integer.parseInt(gridvalueString);
                // navigate to next or previous month on clicking offdays.
                selectedDayPos = position;
                Log.d("CalendarViewActivity", "Clicked gridvalue " + gridvalue + ", position " + position);
                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar(selectedGridDate);
                } else if ((gridvalue <= 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar(selectedGridDate);
                }

                ((CalendarAdapter) parent.getAdapter()).setSelected(v);*/
            }

        });

        gridview.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v,
                                           int position, long id) {
                Log.d("CalendarViewActivity", "LongClicked position " + position);
                isLongClicked = true;
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
