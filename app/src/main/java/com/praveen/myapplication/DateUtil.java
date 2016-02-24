package com.praveen.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {
	private static SimpleDateFormat dayFormat ;
	private static SimpleDateFormat weekdayFormat;
    private static SimpleDateFormat weekdayEEEFormat;
    private static SimpleDateFormat monthMMMFormat;
    private static SimpleDateFormat yearYYYYFormat;
	private static SimpleDateFormat dateFormat ;
	private static SimpleDateFormat dateFormat_weekday;
	private static SimpleDateFormat dateMMddyyyyFormat;
	private static SimpleDateFormat dateyyyyMMddFormat ;
	private static SimpleDateFormat fullDateFormat ;
	private static SimpleDateFormat fullDateFormat_withNospaces ;
	private static SimpleDateFormat fullDateYYYY_MM_DD_HHMMSSFormat;
	private static SimpleDateFormat dateMMMMyyyyFormat;
	private static SimpleDateFormat dateEEE_dd_MM_yyyyFormat;

	private static SimpleDateFormat timeFormat_HHmm;
	private static SimpleDateFormat timeFormat ;
	private static SimpleDateFormat timeAFormat;

	public static final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};


	public static void initDateUtilLocale(Locale locale){
		dayFormat = new SimpleDateFormat("dd", locale);
		weekdayFormat = new SimpleDateFormat("EEEE", locale);
        weekdayEEEFormat = new SimpleDateFormat("EEE", locale);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy", locale);
		dateFormat_weekday = new SimpleDateFormat("EEEE dd/MM/yyyy", locale);
		dateMMddyyyyFormat = new SimpleDateFormat("MM/dd/yyyy", locale);
		dateyyyyMMddFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
		fullDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm",locale);
		fullDateFormat_withNospaces = new SimpleDateFormat("ddMMyyyyHHmm", locale);
		fullDateYYYY_MM_DD_HHMMSSFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
		dateMMMMyyyyFormat=new SimpleDateFormat("MMMM yyyy", locale);
        monthMMMFormat=new SimpleDateFormat("MMM", locale);
		dateEEE_dd_MM_yyyyFormat=new SimpleDateFormat("EEE, dd MMM yyyy", locale);
        yearYYYYFormat=new SimpleDateFormat("yyyy", locale);

		timeFormat_HHmm = new SimpleDateFormat("HH:mm", locale);
		timeFormat = new SimpleDateFormat("HH:mm:ss", locale);
		timeAFormat = new SimpleDateFormat(" hh:mm a", locale);
	}


	public static String genSignatureDate() {
		GregorianCalendar now = new GregorianCalendar();
		now.setTime(new Date());
		StringBuilder sb = new StringBuilder();
		int dayOfMonth = now.get(GregorianCalendar.DAY_OF_MONTH);
        String dayOfMonthStr = Integer.valueOf(dayOfMonth).toString();
        if (dayOfMonthStr.endsWith("1")) {
            sb.append(dayOfMonth).append("st ");
        } else if (dayOfMonthStr.endsWith("2")) {
            sb.append(dayOfMonth).append("nd ");
        } else if (dayOfMonthStr.endsWith("3")) {
            sb.append(dayOfMonth).append("rd ");
        } else {
            sb.append(dayOfMonth).append("th ");
        }

		sb.append(months[now.get(GregorianCalendar.MONTH)]).append(" ");
		sb.append(now.get(GregorianCalendar.YEAR));
		return sb.toString();
	}
	/**
	 *
	 * TODO initAppDateLocales
	 */
	public static void initAppDateLocales(){
			DateUtil.initDateUtilLocale(Locale.UK);

	}

	public static String getCurrentDate() {
		if(dateFormat==null)
			initAppDateLocales();
		return dateFormat.format(new Date());//"14/06/2013";
	}

	public static String getCurrentDay() {
		return dayFormat.format(new Date());//"14";
	}

	public static String getCurrentWeekDay() {
		return weekdayFormat.format(new Date());//"Sunday";
	}

	public static String getCurrentDateWithWeekday() {
		return dateFormat_weekday.format(new Date());//"Sunday 14/06/2013";
	}

	public static String getCurrentFullDate() {
		return fullDateFormat.format(new Date());//"14/06/2013 18:12";
	}

	public static String getCurrentFullDateYYYYMMDDHHMMSSFormat() {
		return fullDateYYYY_MM_DD_HHMMSSFormat.format(new Date());//"2014-07-30 11:45:00";
	}

	public static String getCurrentFullDateWithNoSpaces() {
		return fullDateFormat_withNospaces.format(new Date());//"140620131812";
	}

	public static String getCurrentDateMMddyyyyFormat() {
		return dateMMddyyyyFormat.format(new Date());//"06/14/2013";
	}

	public static String getCurrentDateyyyyMMddFormat() {
		return dateyyyyMMddFormat.format(new Date());//"2014/06/21";
	}

	public static String getCurrentTime() {
		return timeFormat.format(new Date());
	}

	public static String getCurrentTimeHHMM() {
		return timeFormat_HHmm.format(new Date());
	}

	public static SimpleDateFormat getSimpleteDateFormatEEEddMMyyyy(){
		return dateEEE_dd_MM_yyyyFormat;
	}

	public static String getTomorrowDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();
		return dateFormat.format(tomorrow);
	}

	public static String getDateWithWeekday(String date) {
		Date dt=getStringToDate(date, false);
		return dateFormat_weekday.format(dt);//"Sunday 14/06/2013";
	}

    public static int getWeekNumberOfYear(String date) {
        Date dt=getStringToDate(date, false);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;//"Sunday 14/06/2013";
    }

	public static String getTomorrowDateWithWeekDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();
		return dateFormat_weekday.format(tomorrow);
	}

	public static String getCurrentATime() {
		Date now = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append(dateFormat.format(now));
		sb.append(" at");
		sb.append(timeAFormat.format(now));
		return sb.toString();
	}

	public static Date getStringToFullDate(String date){
		try {
			return fullDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getStringYYYYMMDDHHMMSS_ToDateFormat(String date){
		try {
			return dateFormat.format(fullDateYYYY_MM_DD_HHMMSSFormat.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

    public static String getStringFullDate_ToDateFormat(String date){
        try {
            return dateFormat.format(fullDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

	public static String getFormattedDate(Date dt) {
		return dateFormat.format(dt);//"14/06/2013";
	}

    public static String getFormattedWeekEEE(Date dt) {
        return weekdayEEEFormat.format(dt);//"Sun";
    }

    public static String getFormattedMonthMMM(Date dt) {
        return monthMMMFormat.format(dt);//"Feb";
    }

    public static String getFormattedDaydd(Date dt) {
        return dayFormat.format(dt);//"23";
    }

    public static String getFormattedYearYYYY(Date dt) {
        return yearYYYYFormat.format(dt);//"2016";
    }


    public static String getFormattedFullDate(Date dt) {
        return fullDateFormat.format(dt);//"14/06/2013 15:13";
    }

	public static Date getStringToDate(String date,boolean isDateHasHyphen){
		try {
			if(isDateHasHyphen){
				return dateyyyyMMddFormat.parse(date);
			}else{
				return dateFormat.parse(date);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String addHours(Date datetime, int hours){
		 Calendar cal = new GregorianCalendar();
		 cal.setTime(datetime);
	    //Adding 21 Hours to your Date
		 cal.add(Calendar.HOUR_OF_DAY, hours);

		return timeFormat_HHmm.format(cal.getTime());
	}

	public static String addMins(Date datetime, int mins){
		Calendar cal = new GregorianCalendar();
		 cal.setTime(datetime);
	    //subtract 21 mins to your Date
		 cal.add(Calendar.MINUTE, mins);

		return timeFormat_HHmm.format(cal.getTime());
	}

    public static String addMinsHours(Date datetime, int mins,int hours){
        Calendar cal = new GregorianCalendar();
        cal.setTime(datetime);
        //add 21 mins to your Date
        cal.add(Calendar.MINUTE, mins);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return timeFormat_HHmm.format(cal.getTime());
    }


	 public static String genDateStringMMddyyyyFormat(int year, int monthOfYear, int dayOfMonth) {
	        GregorianCalendar date = new GregorianCalendar();
	        date.set(GregorianCalendar.YEAR, year);
	        date.set(GregorianCalendar.MONTH, monthOfYear);
	        date.set(GregorianCalendar.DAY_OF_MONTH, dayOfMonth);

	        return dateMMddyyyyFormat.format(date.getTime());
	    }


    public static String genDateString(int year, int monthOfYear, int dayOfMonth) {
        GregorianCalendar date = new GregorianCalendar();
        date.set(GregorianCalendar.YEAR, year);
        date.set(GregorianCalendar.MONTH, monthOfYear);
        date.set(GregorianCalendar.DAY_OF_MONTH, dayOfMonth);

        return dateFormat.format(date.getTime());
    }

    public static String genDateStringMMMMyyyy(Calendar calendar) {
        return dateMMMMyyyyFormat.format(calendar.getTime());
    }


    private static long getDateDifference(long date1, long date2){
    	return date2-date1;
    }

    public static int getDateDifferenceinDays(long date1,long date2){
    	int days = (int) (getDateDifference(date1,date2) / (1000*60*60*24));
    	return days;
    }

    public static int getDateDifferenceinHours(long date1,long date2){
    	int hours = (int) (getDateDifference(date1,date2) / (1000*60*60) % 24);

    	return hours;
    }


    public static int getDateDifferenceinTotalMins(long date1,long date2){
        int min = (int) (getDateDifference(date1,date2) / (1000*60));
        return min;
    }

    public static int getDateDifferenceinMins(long date1,long date2){
    	int min = (int) (getDateDifference(date1,date2) / (1000*60)%60);
    	return min;
    }

    public static int getDateDifferenceinSecs(long date1,long date2){
    	int sec = (int) (getDateDifference(date1,date2) / 1000 %60);
    	return sec;
    }

     /* public static int getDateDifferenceinHours(long date1,long date2){
    	int days = getDateDifferenceinDays(date1,date2);
    	int hours = (int) ((getDateDifference(date1,date2) - (1000*60*60*24*days)) / (1000*60*60));

    	return hours;
    }

    public static int getDateDifferenceinMins(long date1,long date2){
    	int days = getDateDifferenceinDays(date1,date2);
    	int hours = getDateDifferenceinHours(date1,date2);
    	int min = (int) (getDateDifference(date1,date2) - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
    	return min;
    }*/

    public static String genDateString() {
        GregorianCalendar date = new GregorianCalendar();
        date.setTime(new Date());

        return dateFormat.format(date.getTime());
    }
    /**
     * dateVal1 ---user selected date
     * dateVal2 ---system date to compare
    */
    public static boolean compareDateValue(Date dateVal1,Date dateVal2){
    	if(dateVal1.compareTo(dateVal2)<0 ){
    		return true;//past Date entered
    	}
    	return false;//future date entered
    }

    public static String getCurrentTimeStamp(){
        Long tsLong = System.currentTimeMillis()/1000;
        return tsLong.toString();
    }


    //With this method you will get the timestamp of today at midnight
    public static long getTodayTimestamp(){
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, c1.get(Calendar.YEAR));
        c2.set(Calendar.MONTH, c1.get(Calendar.MONTH));
        c2.set(Calendar.DAY_OF_MONTH, c1.get(Calendar.DAY_OF_MONTH));
        c2.set(Calendar.HOUR_OF_DAY, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);

        return c2.getTimeInMillis();
    }

    public static String getTodayTimestampStr(){
        Long tsLong = getTodayTimestamp();
        return tsLong.toString();
    }

    public static String convertDurationInSecondsToHHMMSS(String duration){
        int durationSecs=Integer.parseInt(duration);
        int hr = durationSecs/3600;
        int rem = durationSecs%3600;
        int mn = rem/60;
        int sec = rem%60;
        String hrStr="",mnStr="",secStr="";
        String durationStr="";
        if(hr>0){
            hrStr = (hr<10 ? "0" : "")+hr;
            durationStr=hrStr +":";
        }
        mnStr = (mn<10 ? "0" : "")+mn;
        secStr = (sec<10 ? "0" : "")+sec;

        durationStr=durationStr+mnStr+":"+secStr;
        return durationStr;
    }

    /**
     *
     * @param context
     * @param time
     * @return
     */
	public static boolean putCurrentTime(Context context, long time){
		SharedPreferences sharedPreferences = context.getSharedPreferences("currentTime", 0);
		Editor editor = sharedPreferences.edit();
		editor.putLong("time", time);
		return editor.commit();
	}

	/**
	 *
	 * @param context
	 * @return
	 */
	public static long getCurrentTime(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences("currentTime", 0);
		return sharedPreferences.getLong("time",System.currentTimeMillis());
	}


}
