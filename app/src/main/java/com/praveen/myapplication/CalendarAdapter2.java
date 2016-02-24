package com.praveen.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
public class CalendarAdapter2 extends BaseAdapter {
	private Context mContext;


	public GregorianCalendar pmonth; // calendar instance for previous month
    public GregorianCalendar visibleFirstDayOfCalendar;
    public GregorianCalendar visibleLastDayOfCalendar;
	/**
	 * calendar instance for previous month for getting complete view
	 */

	private GregorianCalendar selectedDate;
	private String datevalue, curentDateString,dayVal,weekDayVal,monthVal;

	private ArrayList<String> items;
	public List<WeekData> dayString;


	public CalendarAdapter2(Context c, GregorianCalendar monthCalendar) {
		dayString = new ArrayList<WeekData>();
		Locale.setDefault(Locale.US);
		selectedDate = (GregorianCalendar) monthCalendar.clone();
		mContext = c;
		this.items = new ArrayList<String>();
		curentDateString = DateUtil.getCurrentDate();
        pmonth = (GregorianCalendar) monthCalendar.clone();
		refreshDays();
	}

	public int getCount() {
		return dayString.size();
	}

	public Object getItem(int position) {
		return dayString.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new view for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		TextView dayView;
		if (convertView == null) { // if it's not recycled, initialize some attributes
			LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.item_calendar, null);
		}
		dayView = (TextView) v.findViewById(R.id.date);

        WeekData weekData = dayString.get(position);
        String gridvalue=weekData.getGridVal();
        String dateValue=weekData.getDateOfCalendar();
        dayView.setTag(Integer.toString(position));

        dayView.setTextColor(Color.BLACK);
        // Set the layout parameters for TextView widget
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        dayView.setLayoutParams(lp);
        // Get the TextView LayoutParams
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) dayView.getLayoutParams();

        if (gridvalue.equalsIgnoreCase("Sun")||gridvalue.equalsIgnoreCase("Mon")||gridvalue.equalsIgnoreCase("Tue")
                ||gridvalue.equalsIgnoreCase("Wed")||gridvalue.equalsIgnoreCase("Thu")||gridvalue.equalsIgnoreCase("Fri")
                ||gridvalue.equalsIgnoreCase("Sat")){
            dayView.setTextColor(Color.WHITE);
            dayView.setClickable(false);
            dayView.setFocusable(false);
            v.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));

            params.width = getPixelsFromDPs(mContext, 100);
            params.height = getPixelsFromDPs(mContext,40);
        }else{
            params.width = getPixelsFromDPs(mContext, 100);
            params.height = getPixelsFromDPs(mContext,60);

            if(dateValue.equalsIgnoreCase(curentDateString)){
                dayView.setTextColor(Color.WHITE);
                v.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            }else{
                dayView.setTextColor(Color.BLACK);
                v.setBackgroundColor(mContext.getResources().getColor(R.color.thin_blue));
            }

        }
        // Set the TextView layout parameters
        dayView.setLayoutParams(params);
        dayView.setSingleLine(false);

		dayView.setText(gridvalue);

		return v;
	}

    public GregorianCalendar getVisibleFirstDayOfCalendar(){
        return visibleFirstDayOfCalendar;
    }

    public GregorianCalendar getVisibleLastDayOfCalendar(){
        return visibleLastDayOfCalendar;
    }

	public void refreshDays() {
		// clear items
		items.clear();
		dayString.clear();
		Locale.setDefault(Locale.US);

        visibleFirstDayOfCalendar=(GregorianCalendar) pmonth.clone();
        visibleLastDayOfCalendar=(GregorianCalendar) pmonth.clone();

        LinkedHashMap<String,String> tempdayStringHM = new LinkedHashMap<String,String>();
        LinkedHashMap<String,String> tempweekDayStringHM = new LinkedHashMap<String,String>();
        int daylength=7;
    	/**
		 * filling calendar gridview.
		 */
        for (int n = 0; n < daylength; n++) {
            pmonth.setTime(selectedDate.getTime());
            pmonth.add(Calendar.DAY_OF_YEAR, n);
            datevalue=DateUtil.getFormattedDate(pmonth.getTime());
            dayVal = DateUtil.getFormattedDaydd(pmonth.getTime());
            weekDayVal= DateUtil.getFormattedWeekEEE(pmonth.getTime());
            monthVal=DateUtil.getFormattedMonthMMM(pmonth.getTime());
            tempdayStringHM.put(datevalue,dayVal + "\n" +monthVal);
            tempweekDayStringHM.put(datevalue, weekDayVal);
            if(n==0){
                visibleFirstDayOfCalendar.setTime(pmonth.getTime());//setting visible first cell date calendar
            }else if(n==6){
                visibleLastDayOfCalendar.setTime(pmonth.getTime());//setting visible last cell date calendar
            }
        }

        for(String key:tempweekDayStringHM.keySet()){
            WeekData weekData=new WeekData(tempweekDayStringHM.get(key),key) ;
            dayString.add(weekData);
        }

        for(String key:tempdayStringHM.keySet()){
            WeekData weekData=new WeekData(tempdayStringHM.get(key),key) ;
            dayString.add(weekData);
        }

    }

    // Method for converting DP value to pixels
    public static int getPixelsFromDPs(Context ctx, int dps){
        Resources r = ctx.getResources();
        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }
}