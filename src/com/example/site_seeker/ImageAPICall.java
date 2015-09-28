package com.example.site_seeker;

import java.io.InputStream;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class ImageAPICall extends AsyncTask<String,Void, Drawable> {

	@Override
	protected Drawable doInBackground(String... strPhotoUrl) {
		
		 try
	        {
	        InputStream is = (InputStream) new URL(strPhotoUrl[0]).getContent();
	        Drawable d = Drawable.createFromStream(is, "src name");
	        return d;
	        }
	        catch (Exception e) {
	        System.out.println("Exc="+e);
	        return null;
	        }
	}

}
