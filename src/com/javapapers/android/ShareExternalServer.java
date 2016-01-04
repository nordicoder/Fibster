package com.javapapers.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ShareExternalServer {

	String result;
	HttpURLConnection httpCon;
	byte[] bytes;
    String response;

	URL serverUrl;
	public String shareRegIdWithAppServer(final Context context,
			final String regId) {

	 result = "";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("regId", regId);
		Log.d("ddd","share external server");
		
		
		try {
			serverUrl = null;
			try {
				serverUrl = new URL(Config.APP_SERVER_URL);
			} catch (MalformedURLException e) {
				Log.d("AppUtil", "URL Connection Error: "
						+ Config.APP_SERVER_URL, e);
				result = "Invalid URL: " + Config.APP_SERVER_URL;
			}

			StringBuilder postBody = new StringBuilder();
			Iterator<Entry<String, String>> iterator = paramsMap.entrySet()
					.iterator();

			while (iterator.hasNext()) {
				Entry<String, String> param = iterator.next();
				postBody.append(param.getKey()).append('=')
						.append(param.getValue());
				if (iterator.hasNext()) {
					postBody.append('&');
				}
			}
			Log.d("ddd","http before");

			String body = postBody.toString();
			 bytes = body.getBytes();
			InputStream is;
	        byte[] buf = new byte[4096];
	        byte[] bytes1 = new byte[4096];

	        int flag=0;
	        
		httpCon = null;
			
			 
			
			try {
				httpCon = (HttpURLConnection) serverUrl.openConnection();
	            httpCon.setDoInput(true);

				httpCon.setDoOutput(true);
				httpCon.setUseCaches(false);
				httpCon.setFixedLengthStreamingMode(bytes.length);
				httpCon.setRequestMethod("POST");
				httpCon.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF-8");
	            /**/httpCon.setRequestProperty("Connection", "Keep-Alive");
	            /**/httpCon.setConnectTimeout (30000) ; 
	            
				
				Log.d("ddd","OutputStream Before");

				OutputStream out = httpCon.getOutputStream();
				out.write(bytes);
				out.close();
				
				InputStream in=httpCon.getInputStream();
	            in.read(bytes1);
	             response =new String(bytes1);
	            in.close();
				
				Log.d("ddd","Getting response uhoh");
				
				Log.d("ddd","phew");
				

				int status = httpCon.getResponseCode();
				if (status == 200) {
					result = "RegId shared with Application Server. RegId: "
							+ regId;
					Log.d("dd","thers hope:)");
				} else {
					result = "Post Failure." + " Status: " + status;
					Log.d("dd","sorry not shared");
					httpCon.disconnect();
				}
			} 
			finally {
				if (httpCon != null) {
					Log.d("dd","http con is not null:)");

					httpCon.disconnect();
				}
			}
	        

	        
		
		
		
		
	
		                }
		            catch (Exception e) {
		    			result = "Post Failure. Error in sharing with App Server.";
		    			Log.d("dd","sorry not shared again");
		    			Log.e("AppUtil", "Error in sharing with App Server: " + e);
		    			
		            }
		            
		Log.d("result",result);
		return response+"\n"+result;
	}
	
	
}
