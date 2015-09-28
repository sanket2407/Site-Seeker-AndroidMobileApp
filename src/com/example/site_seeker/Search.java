package com.example.site_seeker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class Search extends Activity{
	TextView addressLabel;
	EditText addressBox;
	Spinner spinner;
	String res,addressRes;
	ArrayList<String> allPlaces = new ArrayList<String>();
	double[] locationPoints = new double[4];
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		LinearLayout linear = new LinearLayout(this);
	    linear.setOrientation(LinearLayout.VERTICAL);	    
		addressLabel = new TextView(this);
		addressLabel.setText("Enter Valid Address:");
		addressLabel.setTextSize(20);		
		addressBox = new EditText(this);
        addressBox.setText("");        
        TextView distanceLabel = new TextView(this);
		distanceLabel.setText("Select Distance (Mile):");
		distanceLabel.setTextSize(20);
		
		ArrayList<String> options=new ArrayList<String>();

		options.add("01 Mile");
		options.add("02 Miles");
		options.add("05 Miles");
		options.add("10 Miles");
		options.add("20 Miles");

		// use default spinner item to show options in spinner
		   spinner = new Spinner(this);
		    ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
		        android.R.layout.simple_spinner_dropdown_item,options);
		    spinner.setAdapter(spinnerArrayAdapter);	
		    spinner.setSelection(options.indexOf("1 Mile"));
		   
        linear.addView(addressLabel);
		linear.addView(addressBox);
		linear.addView(distanceLabel);
		linear.addView(spinner, new LinearLayout.LayoutParams(
			        LinearLayout.LayoutParams.WRAP_CONTENT,
			        LinearLayout.LayoutParams.WRAP_CONTENT));
		 setContentView(linear);		
			    
			    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			            LinearLayout.LayoutParams.MATCH_PARENT,
			            LinearLayout.LayoutParams.WRAP_CONTENT);
			    Button btn = new Button(this);
			    btn.setId(1);
			    final int id_ = btn.getId();
			    btn.setText("Click To Find Attractions");
			    			    
			    //btn.setBackgroundColor(Color.rgb(70, 80, 90));
			    linear.addView(btn, params);
			    btn = ((Button) findViewById(id_));
			    btn.setOnClickListener(new View.OnClickListener() {
			        public void onClick(View view) {
			        	sendMessage(view);
			        }
			    }); 			
	}
	
	 public void sendMessage(View view) {
		
		 String address = (addressBox.getText()).toString();		 
		 TextView textView = (TextView)spinner.getSelectedView();
		 String distance = textView.getText().toString();
		 double finalrange = Double.parseDouble(distance.substring(0, 2)) * 1609.34; 
		 
		 List<Address> foundGeocode = null;
		 String address1URL = "http://maps.googleapis.com/maps/api/geocode/xml?address="+address+"&sensor=true";
		 LatlongAPICall lac = new LatlongAPICall();
		 lac.execute(address1URL);	 
				
				try {
					addressRes = (lac.get()).trim();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		if(addressRes != "null"){
			String[] latlng = addressRes.split("\\|");
			double lati = Double.parseDouble(latlng[0]);
			double longi = Double.parseDouble(latlng[1]);			 
			locationPoints[0] = lati;
			locationPoints[1] = longi;			 
			 String places = "https://maps.googleapis.com/maps/api/place/nearbysearch/xml?location="+lati+","+longi+"&radius="+finalrange+"&types=aquarium|amusement_park|art_gallery|casino|cemetery|church|museum|park|zoo&sensor=false&key=AIzaSyDM8RoFHMgloaItNhafZ1FK7k4rxrvdRZY";
				
				 PlacesAPICall pac = new PlacesAPICall();
				 pac.execute(places);
				 try {					
					res = (pac.get()).trim();
					
				 }catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 
			 Bundle bundle = new Bundle();
			 bundle.putString("param1", res);
			 bundle.putDoubleArray("locations", locationPoints);
			 Intent myIntent = new Intent(this,ViewPlaces.class); 
			 myIntent.putExtras(bundle);
			 startActivity(myIntent);			
		}
		else{
			AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);                      
		    dlgAlert.setTitle("Error"); 
		    dlgAlert.setMessage("Please enter valid address"); 
		    dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		             //finish(); 
		        }
		   });
		    dlgAlert.setCancelable(true);
		    dlgAlert.create().show();
		    addressBox.setText("");			
		}
		 
	    }
}
