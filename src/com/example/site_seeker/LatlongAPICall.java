package com.example.site_seeker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class LatlongAPICall extends AsyncTask<String,Void, String> {
	
	@Override	
	protected String doInBackground(String... addressUrl1) {
		// TODO Auto-generated method stub
		String temp="", temp1="", temp2="", temp3="", temp4="", temp5="";
		StringBuffer sb = new StringBuffer();		
		BufferedReader br = null;		 
		try {
			
			String dummy = addressUrl1[0].replaceAll(" ", "%20");
			URL url = new URL(dummy);
			
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			
			String line;
			
			br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			while((line=br.readLine())!=null){				
				if (line.trim().startsWith("<lat>"))
                {    
					temp = line;                    
        			temp1 = temp.replace("<lat>", "");                   
                    temp2 = temp1.replace("</lat>", "");                    
                    sb.append(temp2+"|");                    
                }
				if (line.trim().startsWith("<lng>"))
				{					
					temp5 = line;
					temp3 = temp5.replace("<lng>", "");					
                    temp4 = temp3.replace("</lng>", "");                   
                    sb.append(temp4);                   
                    break;                    
				}
			}                   			
			if(sb == null || sb.toString()==""){
				return "null";
			}
			
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
	
	protected void onPostExecute(Void unused) {
        //this.cancel(true);
    }
}
