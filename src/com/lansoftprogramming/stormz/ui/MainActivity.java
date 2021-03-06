package com.lansoftprogramming.stormz.ui;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import butterknife.Bind;
import butterknife.ButterKnife;

import com.lansoftprogramming.stormz.R;
import com.lansoftprogramming.stormz.R.id;
import com.lansoftprogramming.stormz.R.layout;
import com.lansoftprogramming.stormz.R.string;
import com.lansoftprogramming.stormz.weather.Current;
import com.lansoftprogramming.stormz.weather.Day;
import com.lansoftprogramming.stormz.weather.Forecast;
import com.lansoftprogramming.stormz.weather.Hour;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	public static final String TAG = MainActivity.class.getSimpleName();
	private Forecast mForecast;
	
	private double latitude;
    private double longitude;
	//private TextView mTemperatureLabel;
	
	@Bind(R.id.timeLabel) TextView mTimeLabel;
	@Bind(R.id.temperatureLabel) TextView mTemperatureLabel;
	@Bind(R.id.humidityValue) TextView mHumidityValue;
	@Bind(R.id.precipValue) TextView mPercipValue;
	@Bind(R.id.textView1) TextView mSummaryValue;
	@Bind(R.id.iconImageView) ImageView mIconImageView; 
	@Bind(R.id.refreshImageView) ImageView mRefreshImageView;
	@Bind(R.id.progressBar1) ProgressBar mProgressBar1;
	@Bind(R.id.locationLabel) TextView mLocationLabel;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        
        mProgressBar1.setVisibility(View.INVISIBLE);
        
        //default initialized long/lat
        latitude=37.8267;
        longitude=-122.423;
        
        //setting location...
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        LocationListener locList=new LocationListener(){
        	@Override
        	public void onLocationChanged(Location location) {
        		latitude=location.getLatitude();
        		longitude=location.getLongitude();
        		getForecast(latitude,longitude);
        		Log.i(TAG,"inLocChanged: LAT: "+latitude);
        	}
        	 @Override
             public void onStatusChanged(String s, int i, Bundle bundle) {

             }

             @Override
             public void onProviderEnabled(String s) {

             }

             @Override
             public void onProviderDisabled(String s) {

             }
        };
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1,100,locList);
        
        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i(TAG,"inOnclick: LAT: "+latitude);
				getForecast(latitude,longitude);
			}
		});
        
        //ButterKnife.setDebug(true);
        Log.i(TAG,"gettingForecast: LAT: "+latitude);
    }
	private void getForecast(double latitude, double longitude) {
		Log.i(TAG,"inGetForecast: LAT: "+latitude);
		Log.i(TAG,"inGetForeCastThisLat: LAT: "+this.latitude);

		
		String apiKey="5de75ea6e418ac21f9804619e044e268";
        String forecastUrl="https://api.forecast.io/forecast/"+apiKey+"/"+latitude+","+longitude;
        
        if(isNetworkAvailible()){
        	toggleRefresh();
        	
	        OkHttpClient client=new OkHttpClient();
	        Request request=new Request.Builder().url(forecastUrl).build();
	        Log.i(TAG,"bef call.enque: LAT: "+latitude);
	        Call call=client.newCall(request);
	        call.enqueue(new Callback(){
	
				@Override
				public void onFailure(Request arg0, IOException arg1) {
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							toggleRefresh();
						}
					});
					alertUserAboutError();
				}
	
				@Override
				public void onResponse(Response response) throws IOException {
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							toggleRefresh();
						}
					});
					try {

						String jsonData=response.body().string();
						Log.v(TAG,jsonData);
						if(response.isSuccessful()){
							mForecast=parseForecastDetails(jsonData);
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									//mTemperatureLabel.setText("--");
									updateDisplay();
								}
							});
						}else{
							alertUserAboutError();
						}
					} catch (IOException e) {
						Log.e(TAG,"Exception caught: ",e);
					}catch(JSONException e){
						Log.e(TAG,"Exception caught: ",e);
					}
					
				}
	        	
        });
        }else{
        	Toast.makeText(this,R.string.network_unavailiable_message,Toast.LENGTH_LONG).show();
        }
	}
	
	
	private void toggleRefresh() {
		if(mProgressBar1.getVisibility()==View.INVISIBLE){
			mProgressBar1.setVisibility(View.VISIBLE);
			mRefreshImageView.setVisibility(View.INVISIBLE);
		}else{
			mProgressBar1.setVisibility(View.INVISIBLE);
			mRefreshImageView.setVisibility(View.VISIBLE);
		}
	}
	protected void updateDisplay() {
		Current current=mForecast.getCurrent();
		
		mTemperatureLabel.setText(current.getTemperature()+"");
		mTimeLabel.setText("At "+current.getFormattedTime()+" it will be");
		mHumidityValue.setText(current.getHumidity()+"");
		mPercipValue.setText(current.getPrecipChance()+"%");
		mSummaryValue.setText(current.getSummary());
		Drawable drawable=getResources().getDrawable(current.getIconId());
		
		mIconImageView.setImageDrawable(drawable);
		mLocationLabel.setText(current.getLocation());
	}
	
	private Forecast parseForecastDetails(String jsonData) throws JSONException{
		Forecast forecast=new Forecast();
		
		forecast.setCurrent(getCurrentDetails(jsonData));
		forecast.setHourlyForecast(getHourlyForecase(jsonData));
		forecast.setDailyForecast(getDailyForecast(jsonData));
		return forecast;
	}
	
	
	private Day[] getDailyForecast(String jsonData) throws JSONException {
		JSONObject forecast=new JSONObject(jsonData);
		String timezone=forecast.getString("timezone");
		JSONObject daily=forecast.getJSONObject("daily");
		JSONArray data=daily.getJSONArray("data");
		
		Day[] days=new Day[data.length()];
		
		for(int i=0;i<data.length();i++){
			JSONObject jsonDay=data.getJSONObject(i);
			Day day=new Day();
			
			day.setSummary(jsonDay.getString("summary"));
			day.setIcon(jsonDay.getString("icon"));
			day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
			day.setTime(jsonDay.getLong("time"));
			day.setTimezone(timezone);
			
			days[i]=day;
		}
		Log.i(TAG,"weather: returned days");
		return days;
	}
	
	private Hour[] getHourlyForecase(String jsonData) throws JSONException {
		JSONObject forecast=new JSONObject(jsonData);
		String timezone=forecast.getString("timezone");
		JSONObject hourly=forecast.getJSONObject("hourly");
		JSONArray data=hourly.getJSONArray("data");
		
		Hour[] hours=new Hour[data.length()];
		
		for(int i=0;i<data.length();i++){
			JSONObject jsonHour=data.getJSONObject(i);
			Hour hour=new Hour();
			
			hour.setSummary(jsonHour.getString("summary"));
			hour.setTemperature(jsonHour.getDouble("temperature"));
			hour.setIcon(jsonHour.getString("icon"));
			hour.setTime(jsonHour.getLong("time"));
			hour.setTimezone(timezone);
			
			hours[i]=hour;
			
		}
		Log.i(TAG,"weather: returned hours");
		return hours;
	}
	
	protected Current getCurrentDetails(String jsonData) throws JSONException {
		JSONObject forecast=new JSONObject(jsonData);
		String timezone=forecast.getString("timezone");
		Log.i(TAG,"From JSON: "+timezone);
		
		JSONObject currently = forecast.getJSONObject("currently");
		
		Current currentWeather=new Current();
		currentWeather.setHumidity(currently.getDouble("humidity"));
		currentWeather.setTime(currently.getLong("time"));
		currentWeather.setIcon(currently.getString("icon"));
		currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
		currentWeather.setSummary(currently.getString("summary"));
		currentWeather.setTemperature(currently.getDouble("temperature"));
		currentWeather.setTimeZone(timezone);
		//set location
		Geocoder geocoder=new Geocoder(this, Locale.getDefault());
		String location="";
		try {
			List<Address> addresses  = geocoder.getFromLocation(latitude,longitude, 1);
			Log.i(TAG,"In address: LAT: "+latitude);
			location = addresses.get(0).getFeatureName();
			Log.i(TAG,"LOC: feature: "+location);
			if(location==null || location.length()<3){//may be unknown - show Post Code instead
				location=addresses.get(0).getPostalCode();
				Log.i(TAG,"LOC: POST: "+location);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentWeather.setLocation(location);
		
		
		Log.d(TAG,currentWeather.getFormattedTime());
		return currentWeather;
	}
	
	private boolean isNetworkAvailible() {
		ConnectivityManager manager=(ConnectivityManager) 
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo=manager.getActiveNetworkInfo();
		
		boolean isAvailible=false;
		if(networkInfo!=null && networkInfo.isConnected()){
			isAvailible=true;
		}
		return isAvailible;
	}
	protected void alertUserAboutError() {
		AlertDialogFragment dialog=new AlertDialogFragment();
		dialog.show(getFragmentManager(),"error_dialog");
		
	}
	
	
}
