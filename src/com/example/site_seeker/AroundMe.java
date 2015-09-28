package com.example.site_seeker;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import com.example.site_seeker.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AroundMe extends Activity implements LocationListener{
	public volatile Location deviceLocation = null;
	BufferedReader r = null;
	String response = null;
	ArrayList<String> allPlaces = new ArrayList<String>();
	String res, addressRes;
	double[] locationPoints = new double[4];
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000L,500.0f, this);
        Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double lat=37.335423;
        double lng=-121.887080;
        if(location!=null){
	        lat = location.getLatitude();	       
	        lng = location.getLongitude();	       
        }
        
        String addressURL = "http://maps.googleapis.com/maps/api/geocode/xml?latlng="+lat+","+lng+"&sensor=true";
		AddressAPICall aac = new AddressAPICall();
		aac.execute(addressURL);
		try {
			addressRes = (aac.get()).trim();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
       	LinearLayout linear = new LinearLayout(this);
	    linear.setOrientation(LinearLayout.VERTICAL);
	    
		TextView textView = new TextView(this);
		textView.setText("You are at:");
		textView.setTextSize(20);
				
		TextView addressView = new TextView(this);
		String finalAddress = addressRes;
		addressView.setText(finalAddress);
		addressView.setTextSize(20);		
		
		linear.addView(textView);
		linear.addView(addressView);
		setContentView(linear);
		locationPoints[0] = lat;
		locationPoints[1] = lng;
		
		String places = "https://maps.googleapis.com/maps/api/place/nearbysearch/xml?location="+lat+","+lng+"&radius=500&types=aquarium|amusement_park|art_gallery|casino|cemetery|church|museum|park|zoo&sensor=false&key=AIzaSyDM8RoFHMgloaItNhafZ1FK7k4rxrvdRZY";
		PlacesAPICall pac = new PlacesAPICall();
		pac.execute(places);
		 try {
			
			res = (pac.get()).trim();
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		            LinearLayout.LayoutParams.MATCH_PARENT,
		            LinearLayout.LayoutParams.WRAP_CONTENT);
			
		    Button btn = new Button(this);
		    btn.setId(1);		    
		    final int id_ = btn.getId();
		    btn.setText("Click To Find Attractions");
		    linear.addView(btn, params);
		    btn = ((Button) findViewById(id_));
		    btn.setOnClickListener(new View.OnClickListener() {
		        public void onClick(View view) {
		        	sendMessage(view);
		        }
		    });		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	 public void sendMessage(View view) {
		 Bundle bundle = new Bundle();
			bundle.putString("param1", res);
			bundle.putDoubleArray("locations", locationPoints);
		 Intent myIntent = new Intent(this,ViewPlaces.class); 
			myIntent.putExtras(bundle);
			startActivity(myIntent);
	    }
	
	@Override
	public void onLocationChanged(Location curLocation) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if(deviceLocation == null)
		{
			
			if(curLocation != null)
				deviceLocation = curLocation;
			else
				Log.i("Location inintialzed", "null location");
		}
		else
		{
			if(deviceLocation.getLatitude() == curLocation.getLatitude() && 
			   deviceLocation.getLongitude() == curLocation.getLongitude())
			{
				return;	
			}
			else
				deviceLocation = curLocation;
		}
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
}
