package com.example.site_seeker;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Colors;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

public class PlaceDetails extends Activity {
	String temp1;
	int flag = 0;
	double[] locationPoints;
	int flag1=2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_details);

		LinearLayout linear = new LinearLayout(this);
	    linear.setOrientation(LinearLayout.VERTICAL);
		Bundle bundle = this.getIntent().getExtras();
		String placeName = bundle.getString("placeName");
		String res = bundle.getString("result");
		locationPoints = bundle.getDoubleArray("locations");
		String[] placeDetails = null;
		placeDetails = res.split("\\|");
		for (String place: placeDetails)
		{
			String[] placeAttributes = null;
			placeAttributes = place.split("\\$");
			String temp = (placeAttributes[0].replace("*", "")).trim();
			if(temp.equals(placeName))
			{
				for (int j = 0; j < placeAttributes.length; j++)
		        {
					if (placeAttributes[j].startsWith("&"))
		            {
		                	flag = 1;
		                	String ref = (placeAttributes[j].replace("&", "")).trim();
		                	String api = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=200&photoreference="+ref+"&sensor=true&key=AIzaSyDM8RoFHMgloaItNhafZ1FK7k4rxrvdRZY";
		                	
		                	ImageAPICall iac = new ImageAPICall();
		           		 	iac.execute(api);
		                	Drawable drw = null;
							try {
								drw = iac.get();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                	ImageView image = new ImageView(this);
		                	image.setImageDrawable(drw);
		                	linear.addView(image);							
		                }
		                else if(placeAttributes[j].startsWith("#")){
		                	double temp2 = Double.parseDouble((placeAttributes[j].replace("#", "")).trim());
		                	locationPoints[flag1] = temp2;
		                	flag1++;
		                }
		                else{	
		                	TextView textView = new TextView(this);
		                	if (placeAttributes[j].startsWith("*"))
		                    {
		                    	temp1 = (placeAttributes[j].replace("*", "")).trim();
		                    	textView.setTextColor(Color.BLUE);
		                    }else{
		                    	temp1 = placeAttributes[j];
		                    }
		            		
		            		textView.setText(temp1);
		            		textView.setTextSize(25);
		            		if (placeAttributes[j].equals("Currently Open")){
		            			textView.setTextColor(Color.GREEN);
		            		}if (placeAttributes[j].equals("Currently Closed")){
		            			textView.setTextColor(Color.RED);
		            		}

		            		linear.addView(textView);
		            		setContentView(linear);		                	
		                }
		                
		            }  
					if(flag == 0){
						ImageView image = new ImageView(this);
						Bitmap bmp = BitmapFactory.decodeResource(
				                getResources(), R.drawable.tourist); 
	                						
						image.setImageBitmap(bmp);
						linear.addView(image);
						setContentView(linear);						
					}
				}
			}
		    		    
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		            LinearLayout.LayoutParams.MATCH_PARENT,
		            LinearLayout.LayoutParams.WRAP_CONTENT);
		    Button btn = new Button(this);
		    btn.setId(1);
		    final int id_ = btn.getId();
		    btn.setText("Click To See Direction");
		    			    
		    //btn.setBackgroundColor(Color.rgb(70, 80, 90));
		    linear.addView(btn, params);
		    btn = ((Button) findViewById(id_));
		    btn.setOnClickListener(new View.OnClickListener() {
		        public void onClick(View view) {
		        	Intent intent = new Intent(Intent.ACTION_VIEW, 
		        		    Uri.parse("http://maps.google.com/maps?saddr="+locationPoints[0]+","+locationPoints[1]+"&daddr="+locationPoints[2]+","+locationPoints[3]));
		        		intent.setComponent(new ComponentName("com.google.android.apps.maps", 
		        		    "com.google.android.maps.MapsActivity"));
		        		startActivity(intent);
		        }
		    });
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.place_details, menu);
		return true;
	}
}
