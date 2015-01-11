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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Projects extends Activity {
	ArrayList<String> projectList;
	ArrayList<String> scopeIdList;
	private ListView lv; 
	private ArrayAdapter<String> adapter;
	private ArrayList<String> display;
	//private TextView inputSearch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setTitle("Projects");
		setContentView(R.layout.activity_projects_test);
		projectList = new ArrayList<String>();
		scopeIdList = new ArrayList<String>();
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
	    		    
	    		    HttpGet httpget = new HttpGet("https://www6.v1host.com/MindtreeLTD/rest-1.oauth.v1/Data/Scope?sel=Name,Schedule.Name");
		    		httpget.setHeader("Host",
		                    "www6.v1host.com");
		    		httpget.setHeader("Authorization",
		                    "Bearer " + accessToken);
		    		try {
						HttpResponse response = httpclient.execute(httpget);
						HttpEntity httpEntity = response.getEntity();
			            String xml = EntityUtils.toString(httpEntity);
			            //System.out.println(xml);
			            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			            DocumentBuilder db;
						db = dbf.newDocumentBuilder();
						InputSource inStream = new InputSource();
				        inStream.setCharacterStream(new StringReader(xml));
				        Document doc = db.parse(inStream);  
				        
			            String projectName = "";
			            NodeList nl = doc.getElementsByTagName("Attribute");
			            for(int i = 0; i < nl.getLength(); i++) {
			            	try{
			                 org.w3c.dom.Element nameElement = (org.w3c.dom.Element) nl.item(i);
			                 projectName = nameElement.getFirstChild().getNodeValue();
			            	}
			            	catch(Exception e){
			            	}
			                	 projectList.add(projectName);
			            }
			            String scopeId = "";
			            NodeList scopeIdNodeList = doc.getElementsByTagName("Asset");
			            for(int i = 0; i < scopeIdNodeList.getLength(); i++) {
			                 org.w3c.dom.Element nameElement = (org.w3c.dom.Element) scopeIdNodeList.item(i);
			                 scopeId = nameElement.getAttribute("id");
			                 //System.out.println(nameElement.getAttribute("id"));
			                	 scopeIdList.add(scopeId);
			            }
			            
//			            for(int i = 0; i <projectList.size(); i++){
//			            	System.out.println(projectList.get(i));
//			            }
			            
			            Handler mainHandler = new Handler(Looper.getMainLooper());
			            mainHandler.post(new Runnable() {

			                @Override
			                public void run() {
			                   displayProjects();
			                }
			            });   
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParserConfigurationException e) {
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
	
	public void displayProjects() {
		setContentView(R.layout.activity_projects_test);
	    lv  = (ListView) findViewById(R.id.mainListView);
	    display = new ArrayList<String>();
	    for(int i = 0; i < projectList.size(); i++){
	    	if(i % 2 == 0)
	    		display.add(projectList.get(i));
	    }
	   // inputSearch = (EditText) findViewById(R.id.inputSearchBar);
	    //arraylist Append
	    adapter=new ArrayAdapter<String>(this,
	                android.R.layout.simple_list_item_1,
	                display);
	    lv.setAdapter(adapter);
	    
	    
	 
	    lv.setOnItemClickListener(new OnItemClickListener() {
			   @Override
			   public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
			      Intent projectSprint = new Intent(Projects.this, SprintsActivity.class);
			      String scopeId = "";
			      if(position == 0){
			    	  scopeId = projectList.get(position + 1);
			      }
			      else{
			    	 scopeId = projectList.get((position * 2) + 1);
			      }
			      scopeId = modifyNames(scopeId);
			       
//			      String[] array = scopeId.split("\\s+");
//			      scopeId = "";
//			      if(array.length > 1){
//			    	  for(int i = 0; i < array.length -1; i++){
//			    		  scopeId = scopeId + array[i] + "%20";
//			    	  }
//			    	  scopeId = scopeId + array[array.length -1];
//			      }
			      
			      projectSprint.putExtra("scopeId", scopeId);
			      projectSprint.putExtra("projectName", display.get(position));
			     // System.out.println("Scope ID:" + scopeId);
			      startActivity(projectSprint);
			   } 
			});
	    
	    /**
         * Enabling Search Filter
         * */
        /*inputSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// When user changed the Text
				ProjectsTest.this.adapter.getFilter().filter(cs);	
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub							
			}
		});*/
	}
	//this method will make sure the parameters have the same format
	   private String modifyNames(String scopeId){
			 String[] array = scopeId.split("\\s+");
		      if(array.length > 1){
		    	  scopeId = "";
		    	  for(int i = 0; i < array.length -1; i++){
		    		  scopeId = scopeId + array[i] + "%20";
		    	  }
		    	  scopeId = scopeId + array[array.length -1];
		      }
		      return scopeId;
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.project_list_view, menu);
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
	
	public void printResponse(View view) {
		    
	}
}
