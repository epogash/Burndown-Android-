/*package com.example.burndown;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.os.AsyncTask;
import android.view.View;

public class Data extends AsyncTask<Void, Void, Void>
{
	ArrayList<String> projectList;
    private ListView lv; 
    private ArrayAdapter<String> adapter;
    
    
    protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_project_list_view);
           projectList = new ArrayList<String>();
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
                      
                      HttpGet httpget = new HttpGet("https://www6.v1host.com/MindtreeLTD/rest-1.oauth.v1/Data/Scope?sel=Name");
                         httpget.setHeader("Host",
                               "www6.v1host.com");
                         httpget.setHeader("Authorization",
                               "Bearer " + accessToken);
                         try {
                                      HttpResponse response = httpclient.execute(httpget);
                                      HttpEntity httpEntity = response.getEntity();
                              String xml = EntityUtils.toString(httpEntity);
                              System.out.println(xml);
                              DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                              DocumentBuilder db;
                                      db = dbf.newDocumentBuilder();
                                      InputSource inStream = new InputSource();
                                inStream.setCharacterStream(new StringReader(xml));
                                Document doc = db.parse(inStream);  
                                
                              String projectName = "";
                              projectList = new ArrayList<String>();
                              NodeList nl = doc.getElementsByTagName("Attribute");
                              for(int i = 0; i < nl.getLength(); i++) {
                                   org.w3c.dom.Element nameElement = (org.w3c.dom.Element) nl.item(i);
                                   projectName = nameElement.getFirstChild().getNodeValue();
                                   projectList.add(projectName);
                              }
                              for(int i = 0; i <projectList.size(); i++){
                                System.out.println(projectList.get(i));
                              }
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
                  System.out.println("SUCCESS");
                // Notifies UI when the task is done
            }
        }.execute();
    }
    
    public void displayProjects() {
           setContentView(R.layout.activity_project_list_view);
        lv  = (ListView) findViewById(R.id.list);
        //arraylist Append
        adapter=new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    projectList);
        lv.setAdapter(adapter);
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

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}
            
 }*/
