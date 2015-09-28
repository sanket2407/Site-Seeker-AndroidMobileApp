package com.example.site_seeker;

import android.os.Bundle;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
	private TabHost mTabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;	
		
		//AroundMe tab
		intent = new Intent(this, AroundMe.class);
		spec = mTabHost.newTabSpec("aroundme")
				.setIndicator("Around Me")
				.setContent(intent);
		mTabHost.addTab(spec);
		
		//Search tab
		intent = new Intent(this, Search.class);
		spec = mTabHost.newTabSpec("search")
				.setIndicator("Search")
				.setContent(intent);
		mTabHost.addTab(spec);
		
		mTabHost.setCurrentTab(0);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
