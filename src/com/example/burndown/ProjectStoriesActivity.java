package com.example.burndown;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ProjectStoriesActivity extends Activity {
	ArrayList<String> storiesList;
	private ListView lv; 
	private ArrayAdapter<String> adapter;
	String scopeName;
	String projectName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setTitle("Stories");
		setContentView(R.layout.activity_project_stories);
		storiesList = new ArrayList<String>();
		scopeName = "";
		projectName = "";
		
		Intent intent = getIntent();
		scopeName = intent.getStringExtra("scopeName");
		projectName = intent.getStringExtra("projectName");
		scopeName = modifyNames(scopeName);
	    projectName = modifyNames(projectName);
		//System.out.println("Scope ID:" + scopeName + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" + projectName);
		scopeName = "'" + scopeName + "'";
		projectName = "'" + projectName + "'";
	   //System.out.println(scopeName);
		AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {

			@Override
	        protected Void doInBackground(Void... params) {
	        	String accessToken = "";
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
	    		    try {
						HttpResponse response = httpclient.execute(httppost);
						
						BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
						String json = reader.readLine();
						JSONTokener tokener = new JSONTokener(json);
						JSONObject finalResult = new JSONObject(tokener);
						accessToken = finalResult.getString("access_token");
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		    
	    		    HttpGet storyHttpGet = new HttpGet("https://www6.v1host.com/MindtreeLTD/rest-1.oauth.v1/Data/Story?sel=Name&where=Scope.Name=" + projectName + ";Timebox.Name=" + scopeName);//("https://www6.v1host.com/MindtreeLTD/rest-1.oauth.v1/Data/Story?sel=Name");//&where=Scope=" + scopeId);
	    		    storyHttpGet.setHeader("Host",
		                    "www6.v1host.com");
	    		    //System.out.println(accessToken);
	    		    storyHttpGet.setHeader("Authorization",
		                    "Bearer " + accessToken);
	    		    //PARSE JSON RESPONSE
	    		    try {
	    		    	HttpResponse response = httpclient.execute(storyHttpGet);
						HttpEntity httpEntity = response.getEntity();
			            String xml = EntityUtils.toString(httpEntity);
			            //System.out.println(xml);
			            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			            DocumentBuilder db;
						db = dbf.newDocumentBuilder();
						InputSource inStream = new InputSource();
				        inStream.setCharacterStream(new StringReader(xml));
				        Document doc = db.parse(inStream);  
				        
			            String storyName = "";
			            NodeList nl = doc.getElementsByTagName("Attribute");
			            for(int i = 0; i < nl.getLength(); i++) {
			                 org.w3c.dom.Element nameElement = (org.w3c.dom.Element) nl.item(i);
			                 storyName = nameElement.getFirstChild().getNodeValue();
			                 storiesList.add(storyName);
			            }
			            
			            Handler mainHandler = new Handler(Looper.getMainLooper());
			            mainHandler.post(new Runnable() {

			                @Override
			                public void run() {
			                	displayStories();
			                }
			            });   
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		
	            return null;
	        }

	        @Override
	        protected void onPostExecute(Void aVoid) {
	        	//System.out.println("SUCCESS");
	            // Notifies UI when the task is done
	        }
	    }.execute();
	}
	 private String modifyNames(String scopeId){
		 String[] array = scopeId.split("\\s+");
	      scopeId = "";
	      if(array.length > 1){
	    	  for(int i = 0; i < array.length -1; i++){
	    		  scopeId = scopeId + array[i] + "%20";
	    	  }
	    	  scopeId = scopeId + array[array.length -1];
	      }
	      return scopeId;
	}
	public void displayStories() {
		setContentView(R.layout.activity_project_stories);
	    lv  = (ListView) findViewById(R.id.listView2);
	    //arraylist Append
	    adapter=new ArrayAdapter<String>(this,
	                android.R.layout.simple_list_item_1,
	                storiesList);
	    lv.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.project_stories, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
