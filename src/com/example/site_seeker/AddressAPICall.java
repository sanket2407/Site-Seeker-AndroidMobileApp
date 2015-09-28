package com.example.site_seeker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;
import android.util.Log;

public class AddressAPICall extends AsyncTask<String,Void, String>{
	String temp, temp1, temp2;
	@Override
	
	protected String doInBackground(String... addressUrl) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();		 
		try {
			URL url = new URL(addressUrl[0]);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			String line;
			
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			while((line=br.readLine())!=null){
				if (line.trim().startsWith("<formatted_address>"))
                {
                    sb.append(line);
                    break;
                }

			}
			temp = sb.toString();
			temp1 = temp.replace("<formatted_address>", "");
            temp2 = temp1.replace("</formatted_address>", "");
            
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp2;
	}
	
	  protected void onPostExecute(String result) {
	         //Log.i("Final result: ",result);
	         
	  }
}
