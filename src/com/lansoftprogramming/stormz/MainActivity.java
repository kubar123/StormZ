package com.lansoftprogramming.stormz;

import java.io.IOException;
import butterknife.ButterKnife;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	public static final String TAG = MainActivity.class.getSimpleName();
	private CurrentWeather mCurrentWeather;
	
	//private TextView mTemperatureLabel;
	
	@Bind(R.id.timeLabel) TextView mTimeLabel;
	@Bind(R.id.temperatureLabel) TextView mTemperatureLabel;
	@Bind(R.id.humidityLabel) TextView mHumidityLabel;
	@Bind(R.id.precipLabel) TextView mPercipLabel;
	@Bind(R.id.textView1) TextView mSummaryLabel;
	@Bind(R.id.iconImageView) ImageView mIconImageView; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //ButterKnife.setDebug(true);
        String apiKey="5de75ea6e418ac21f9804619e044e268";
        double latitude=37.8267;
        double longitude=-122.423;
        String forecastUrl="https://api.forecast.io/forecast/"+apiKey+"/"+latitude+","+longitude;
        
        if(isNetworkAvailible()){
	        OkHttpClient client=new OkHttpClient();
	        Request request=new Request.Builder().url(forecastUrl).build();
	        
	        Call call=client.newCall(request);
	        call.enqueue(new Callback(){
	
				@Override
				public void onFailure(Request arg0, IOException arg1) {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void onResponse(Response response) throws IOException {
					try {
						String jsonData=response.body().string();
						Log.v(TAG,jsonData);
						if(response.isSuccessful()){
							mCurrentWeather=getCurrentDetails(jsonData);
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									mTemperatureLabel.setText("19");
									//updateDisplay();
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
	protected void updateDisplay() {
		mTemperatureLabel.setText("12.9");
		
	}
	protected CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
		JSONObject forecast=new JSONObject(jsonData);
		String timezone=forecast.getString("timezone");
		Log.i(TAG,"From JSON: "+timezone);
		
		JSONObject currently = forecast.getJSONObject("currently");
		
		CurrentWeather currentWeather=new CurrentWeather();
		currentWeather.setHumidity(currently.getDouble("humidity"));
		currentWeather.setTime(currently.getLong("time"));
		currentWeather.setIcon(currently.getString("icon"));
		currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
		currentWeather.setSummary(currently.getString("summary"));
		currentWeather.setTemperature(currently.getDouble("temperature"));
		currentWeather.setTimeZone(timezone);
		
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
