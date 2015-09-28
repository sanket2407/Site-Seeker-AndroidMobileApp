package com.example.site_seeker.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;


public class MyLocationListener implements LocationListener {
private double lati , longi;
	
	public MyLocationListener(){
		this.lati = 0;
		this.longi = 0;
	}
	@Override  
	public void onLocationChanged(Location loc) {  
		this.longi = loc.getLongitude();  
	    this.lati = loc.getLatitude();  
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}  
	
	public double[] getLatLng(){
		return new double[]{lati, longi};
	}

}
