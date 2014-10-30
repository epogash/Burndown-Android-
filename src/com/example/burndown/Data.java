package com.example.burndown;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.AsyncTask;
import android.view.View;

public class Data extends AsyncTask<Void, Void, Void>
{
	//------------------AsyncTask block-------------------------------
	public void printResponse(View view)
	{
		AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {

	        @Override
	        
	        public Void doInBackground(Void... params) {
	              HttpClient httpclient = new DefaultHttpClient();
	              HttpPost httppost = new HttpPost("https://www6.v1host.com/MindtreeLTD/oauth.v1/token");
	              httppost.setHeader("Host",
	                    "www6.v1host.com");
	              httppost.setHeader("Content-Type",
	                    "application/x-www-form-urlencoded");
	              
	                  // Add your data
	                  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
	                  nameValuePairs.add(new BasicNameValuePair("refresh_token", "tf9i!IAAAAD2RDmLDGwmAzulxkVPWdiqvV8W5eIuPqeY-5hW_nUzmsQAAAAEksbeRdjdratVefTpYU_D2IzvfcFQycn0JJZ1Y8rBZV_xud3D_5x3R5LutJou62uB6T4PFyep0DtzslnOVx688z9NP5Ciz8fq08xvTewo4bfmgIzL7q1dbOXtMfho1OqHigc_-I3bJK80udZ2ui3HDpFqSO4OyJMCGv2ikTVs76Gwn01TMWdm-sKBWQBmMmoJGbiQvimQpSQnJ3KJ2Mke8A712k8n05kPDwL09ahjqjA"));
	                  nameValuePairs.add(new BasicNameValuePair("grant_type", "refresh_token"));
	                  nameValuePairs.add(new BasicNameValuePair("client_id", "client_2xofp4kh"));
	                  nameValuePairs.add(new BasicNameValuePair("client_secret", "s9cw5xputt86mgggj8d8"));
	                 try {
	                                  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	                           } catch (UnsupportedEncodingException e) {
	                                  // TODO Auto-generated catch block
	                                  e.printStackTrace();
	                           }

	                   // Execute HTTP Post Request
	                  /*try {
	                                  HttpResponse response = httpclient.execute(httppost);
	                                  BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
	                                  String json = reader.readLine();
	                                  JSONTokener tokener = new JSONTokener(json);
	                                  JSONObject finalResult = new JSONObject(tokener);
	                                  System.out.println(finalResult);
	                           } catch (ClientProtocolException e) {
	                                  // TODO Auto-generated catch block
	                                  e.printStackTrace();
	                           } catch (IOException e) {
	                                  // TODO Auto-generated catch block
	                                  e.printStackTrace();
	                           } catch (JSONException e) {
	                                  // TODO Auto-generated catch block
	                                  e.printStackTrace();
	                           }*/
	            return null;
	        }

	        @Override
	        protected void onPostExecute(Void aVoid) {
	              System.out.println("SUCCESS");
	            // Notifies UI when the task is done
	        }
	    };
	    
	    asyncTask.execute();
	}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}
	
	//------------------------------------------------------------------------*/

}
