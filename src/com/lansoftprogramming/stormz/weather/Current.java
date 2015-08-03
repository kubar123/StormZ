package com.lansoftprogramming.stormz.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.lansoftprogramming.stormz.R;
import com.lansoftprogramming.stormz.R.drawable;

import android.location.Geocoder;



public class Current{
	private String mIcon;
	private long mTime;
	private double mTemperature;
	private double mHumidity;
	private double mPrecipChance;
	private String mSummary;
	private String mTimeZone;
	private String mLocation;
	
	public String getLocation(){
		return mLocation;
	}
	public void setLocation(String loc){
		this.mLocation=loc;
	}
	public String getIcon() {
		return mIcon;
	}
	public int getIconId(){
		int iconId=R.drawable.clear_day;
		if (mIcon.equals("clear-day")) {
		    iconId = R.drawable.clear_day;
		}
		else if (mIcon.equals("clear-night")) {
		    iconId = R.drawable.clear_night;
		}
		else if (mIcon.equals("rain")) {
		    iconId = R.drawable.rain;
		}
		else if (mIcon.equals("snow")) {
		    iconId = R.drawable.snow;
		}
		else if (mIcon.equals("sleet")) {
		    iconId = R.drawable.sleet;
		}
		else if (mIcon.equals("wind")) {
		    iconId = R.drawable.wind;
		}
		else if (mIcon.equals("fog")) {
		    iconId = R.drawable.fog;
		}
		else if (mIcon.equals("cloudy")) {
		    iconId = R.drawable.cloudy;
		}
		else if (mIcon.equals("partly-cloudy-day")) {
		    iconId = R.drawable.partly_cloudy;
		}
		else if (mIcon.equals("partly-cloudy-night")) {
		    iconId = R.drawable.cloudy_night;
		}
		return iconId;
	}
	public String getTimeZone() {
		return mTimeZone;
	}
	public void setTimeZone(String timeZone) {
		mTimeZone = timeZone;
	}
	public void setIcon(String icon) {
		mIcon = icon;
	}
	public long getTime() {
		return mTime;
	}
	public String getFormattedTime(){
		SimpleDateFormat formatter=new SimpleDateFormat("H:mm a");
		formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
		Date dateTime=new Date(getTime()*1000);
		return formatter.format(dateTime);
	}
	public void setTime(long time) {
		mTime = time;
	}
	public int getTemperature() {
		return (int) Math.round(mTemperature);
	}
	public void setTemperature(double temperature) {
		mTemperature = temperature;
	}
	public double getHumidity() {
		return mHumidity;
	}
	public void setHumidity(double humidity) {
		mHumidity = humidity;
	}
	public int getPrecipChance() {
		return (int) (100*mPrecipChance);
	}
	public void setPrecipChance(double precipChance) {
		mPrecipChance = precipChance;
	}
	public String getSummary() {
		return mSummary;
	}
	public void setSummary(String summary) {
		mSummary = summary;
	}
	
}