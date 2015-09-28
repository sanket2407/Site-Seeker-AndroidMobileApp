package com.example.site_seeker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;
import android.util.Log;

public class PlacesAPICall extends AsyncTask<String,Void, String>{
	
	@Override
	protected String doInBackground(String... placeUrl) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		Map<String,String> hm = new HashMap<String, String>();
		hm.put("<name", "</name>");
		hm.put("<vicinity", "</vicinity>");
		hm.put("<open_now", "</open_now>");
		hm.put("<photo_reference", "</photo_reference>");
		hm.put("<lat", "</lat>");
		hm.put("<lng", "</lng>");
		 
		try {
			
			URL url = new URL(placeUrl[0]);			
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();			
			String line;			
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			while((line=br.readLine())!=null){
				if(line.trim().equals("</result>")){
                    sb.append("|");
                }
                String[] splitArray = line.split(">");
                if (hm.containsKey(splitArray[0].trim()))
                {
                   
                	String temp = line.replaceAll(splitArray[0].trim() + ">","");
                	String temp1 = temp.replaceAll(hm.get(splitArray[0].trim()),"");
                	if(splitArray[0].trim().equals("<name")){
                		sb.append("*"+temp1+"$");
                	}
                	else if(splitArray[0].trim().equals("<vicinity")){
                		sb.append("Vicinity: " + temp1 + "$");
                	}
                	else if(splitArray[0].trim().equals("<open_now")){
                		if (temp1.trim().equals("true"))
                        {
                            sb.append("Currently Open" + "$");
                        }
                        else
                        {
                            sb.append("Currently Closed" + "$");
                        }    
                	}
                	else if(splitArray[0].trim().equals("<lat")){
                		sb.append("#"+temp1 + "$");
                	}
                	else if(splitArray[0].trim().equals("<lng")){
                		sb.append("#"+temp1 + "$");
                	}
                	else{
                		 sb.append("&"+temp1 + "$");
                	}
                }
			}
                   			
			Log.i("Response", sb.toString());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}
	
	  protected void onPostExecute(String result) {
	         //Log.i("Final result: ",result);
	         
	     }
}
