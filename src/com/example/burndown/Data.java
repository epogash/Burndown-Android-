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
	                  nameValuePairs.add(new BasicNameValuePair("refresh_token", "tf9i!IAAAAHYVmQaz0WdiucVhbtyL8J3deIzvco9vMlSBmNZzzhjhsQAAAAGTil9Rphm6-6yVgJwX90SoZFK23Vy5u3nZ6KntnQbtEUNdw3Qbv2eLfswT2MvMEfyJd5wgqOKjlclYasv4qC9byywuOjL6cFJ1Xr_DCsvnqXREAtds-KzQAYTLRyL-EYOXX-5aUCepBU9sXQBqDovCq2tEQgqCbhIa7zAwSOnud32FggtcgejUL9QD6GFocNR0MkHkBCv0V6jJOl7aUo7RQb4BUquocKzixsOo03gJxQ"));
	                  nameValuePairs.add(new BasicNameValuePair("grant_type", "refresh_token"));
	                  nameValuePairs.add(new BasicNameValuePair("client_id", "client_m4xy6r47"));
	                  nameValuePairs.add(new BasicNameValuePair("client_secret", "j9xrqqp5vthbu56okx27"));
	                 try {
	                                  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	                           } catch (UnsupportedEncodingException e) {
	                                  // TODO Auto-generated catch block
	                                  e.printStackTrace();
	                           }

	                   // Execute HTTP Post Request
	                 /* try {
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
