package com.example.site_seeker;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPlaces extends ListActivity {
	ArrayList<String> allPlaces = new ArrayList<String>();
	String res;
	double[] locationPoints;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_view_places_new);
		
	    Bundle bundle = this.getIntent().getExtras();
	    
	    res = bundle.getString("param1");
	    locationPoints = bundle.getDoubleArray("locations");
	    	    
	    String[] placeDetails = null;
		placeDetails = res.split("\\|");
		for (String place: placeDetails){
			String[] placeAttributes = null;
			placeAttributes = place.split("\\$");
			for (int j = 0; j < placeAttributes.length; j++)
            {
                if (placeAttributes[j].startsWith("*"))
                {
                	String temp = (placeAttributes[j].replace("*", "")).trim();
                	allPlaces.add(temp);
                }
            }
		}
	   
	    setListAdapter((ListAdapter) new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_1, allPlaces));
	    getListView().setTextFilterEnabled(true);
	    
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Object o = this.getListAdapter().getItem(position);
		String placeName = o.toString();
		
		 Bundle bundle = new Bundle();
		 bundle.putString("placeName", placeName);
		 bundle.putString("result", res);
		 bundle.putDoubleArray("locations", locationPoints);
		 Intent myIntent = new Intent(this,PlaceDetails.class); 
			myIntent.putExtras(bundle);			
			startActivity(myIntent);		
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_places_new, menu);
		return true;
	}

}
