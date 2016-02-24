package com.praveen.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CalendarAdapter extends BaseAdapter {
	private Context mContext;

	private java.util.Calendar month;
	public GregorianCalendar pmonth; // calendar instance for previous month
	/**
	 * calendar instance for previous month for getting complete view
	 */
	public GregorianCalendar pmonthmaxset;
	private GregorianCalendar selectedDate;
	private int firstDay;
	private int maxWeeknumber;
	private int maxP;
	private int calMaxP;

	private int mnthlength;
	private String itemvalue, curentDateString;

	private ArrayList<String> items;
	public static List<String> dayString;
	private View previousView;
	private String userSelectedDate;
	private HashMap<String,Boolean> offDaysMap;

	public CalendarAdapter(Context c, GregorianCalendar monthCalendar) {
		CalendarAdapter.dayString = new ArrayList<String>();

		Locale.setDefault(Locale.US);
		month = monthCalendar;
		selectedDate = (GregorianCalendar) monthCalendar.clone();
		mContext = c;
		month.set(GregorianCalendar.DAY_OF_MONTH, 1);
		this.items = new ArrayList<String>();
		//df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		curentDateString = DateUtil.getFormattedDate(selectedDate.getTime());
		offDaysMap=new HashMap<String, Boolean>();
		refreshDays(null);
	}

	public void setItems(ArrayList<String> items) {
		for (int i = 0; i != items.size(); i++) {
			if (items.get(i).length() == 1) {
				items.set(i, "0" + items.get(i));
			}
		}
		this.items = items;
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
		// separates daystring into parts.
		String[] separatedTime = dayString.get(position).split("/");
		// taking first part of date. ie; 0 from 20-01-2014
		String gridvalue = separatedTime[0].replaceFirst("^0*", "");
		//Log.d("CalendarAdapter", "gridvalue "+ gridvalue +", position "+ position +", firstDay "+ firstDay );
		dayView.setTag(Integer.toString(position));

		if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
			// setting offdays to white color.
			dayView.setTextColor(Color.WHITE);
			dayView.setClickable(false);
			dayView.setFocusable(false);
			offDaysMap.put(Integer.toString(position), true);
		} else if ((Integer.parseInt(gridvalue) <= 7) && (position > 28)) {
			dayView.setTextColor(Color.WHITE);
			dayView.setClickable(false);
			dayView.setFocusable(false);
			offDaysMap.put(Integer.toString(position), true);
		} else {
			// setting curent month's days in blue color.
			dayView.setTextColor(Color.BLACK);
		}

		// checking whether the day is in current month or not.
		if (userSelectedDate!=null && dayString.get(position).equals(userSelectedDate)){
			CalendarViewActivity.selectedDayPos=position;
			setSelected(v);
		}else if (dayString.get(position).equals(curentDateString)) {
			CalendarViewActivity.selectedDayPos=position;
			setSelected(v);
		}else{
			v.setBackgroundResource(R.drawable.cal_item_background);
			v.findViewById(R.id.apmntcount).setVisibility(View.GONE);
		}

		dayView.setText(gridvalue);

		return v;
	}

	public View setSelected(View view) {
		if (previousView != null) {
			previousView.setBackgroundResource(R.drawable.cal_item_background);
			previousView.findViewById(R.id.apmntcount).setVisibility(View.GONE);
			String gridValuePos =((TextView)previousView.findViewById(R.id.date)).getTag().toString();
			//Log.d("CalendarAdapter", "gridvalue "+ gridvalue);

			if(gridValuePos!=null && offDaysMap.get(gridValuePos)!=null){//handling offdays shouldn't change to Black color
				if(!offDaysMap.get(gridValuePos)){
					((TextView)previousView.findViewById(R.id.date)).setTextColor(Color.BLACK);
				}
			}else{
				((TextView)previousView.findViewById(R.id.date)).setTextColor(Color.BLACK);
			}

		}
		previousView = view;
		view.setBackgroundResource(R.drawable.calendar_cell_blue);
		((TextView)view.findViewById(R.id.date)).setTextColor(Color.WHITE);
		//view.findViewById(R.id.apmntcount).setVisibility(View.VISIBLE);

		return view;
	}

	public void refreshDays(String selectedDate) {
		userSelectedDate=selectedDate;
		// clear items
		items.clear();
		offDaysMap.clear();
		dayString.clear();
		Locale.setDefault(Locale.US);
		pmonth = (GregorianCalendar) month.clone();

		// month start day. ie; sun, mon, etc
		firstDay = month.get(GregorianCalendar.DAY_OF_WEEK)-1;
		if(firstDay==0){
			// finding number of weeks in current month.
			maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH)+1;
			// allocating maximum row number for the gridview.
			mnthlength = maxWeeknumber * 7;
			maxP = getMaxP(); // previous month maximum day 31,30....
			firstDay=6;
			calMaxP = maxP - firstDay ;// calendar offday starting 24,25 ...
		}else{
			// finding number of weeks in current month.
			maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
			// allocating maximum row number for the gridview.
			mnthlength = maxWeeknumber * 7;
			maxP = getMaxP(); // previous month maximum day 31,30....
			calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
		}

		/**
		 * Calendar instance for getting a complete gridview including the three
		 * month's (previous,current,next) dates.
		 */
		pmonthmaxset = (GregorianCalendar) pmonth.clone();
		/**
		 * setting the start date as previous month's required date.
		 */
		pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

		/**
		 * filling calendar gridview.
		 */
		for (int n = 0; n < mnthlength; n++) {
			itemvalue = DateUtil.getFormattedDate(pmonthmaxset.getTime());
			pmonthmaxset.add(GregorianCalendar.DATE, 1);
			dayString.add(itemvalue);
		}
	}

	private int getMaxP() {
		int maxP;
		if (month.get(GregorianCalendar.MONTH) == month.getActualMinimum(GregorianCalendar.MONTH)) {
			pmonth.set((month.get(GregorianCalendar.YEAR) - 1),	month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			pmonth.set(GregorianCalendar.MONTH,	month.get(GregorianCalendar.MONTH) - 1);
		}
		maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

		return maxP;
	}

}