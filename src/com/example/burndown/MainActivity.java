package com.example.burndown;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.DocumentsContract.Document;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;


public class MainActivity extends Activity {
	private TextView error;
	private boolean check = false;
	private String passwordFile = "PASS.txt";
	private String usernameFile = "USRname.txt";
	private String staticUser = "staticUser.txt";
	private ListView mainListView ;  
	private ArrayAdapter<String> listAdapter ; 
	ArrayList<String> project_list;
	
	
	
	public String getUsernameFile() {
		return usernameFile;
	}

	public String getPasswordFile() {
		return passwordFile;
	}

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		readUser();
         
         
	}

	@SuppressWarnings("unused")
	private void refresh() {

		finish();
		startActivity(getIntent());
		// Intent projectList = new Intent(this, ChangePasswordActivity.class);
		// startActivity(projectList);

	}
	public void toggle(View v) {
		RadioButton remember = (RadioButton) v;
		remember.setChecked(check);
		check = !check;
	}
	
	protected void write(String file, String value) {
		FileOutputStream write;
		try {
			write = openFileOutput(file, Context.MODE_PRIVATE);
			write.write(value.getBytes());
			write.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void readUser() {
		EditText username = (EditText) findViewById(R.id.username);
		username.setText((CharSequence) read(usernameFile));
	}

	public String read(String file) {
		
		String value = "";
		try {
			FileInputStream input = openFileInput(file);
			BufferedReader inputRead = new BufferedReader(new InputStreamReader(input));
			value = inputRead.readLine();
			return value;

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
		return value;
	}
	
	//Method used to call a new screen (projects.xml) whenever the "login" button is pressed 
	//on the login screen -- this is temporary, main for testing.
	public void back_to_login(View view)
	{
		setContentView(R.layout.activity_main);
	}

	public void buttonClicked(View view) {
		
		//Button button = (Button) v;
		EditText user = (EditText) findViewById(R.id.username);
		EditText pass = (EditText) findViewById(R.id.password);
		error = (TextView) findViewById(R.id.error);
		String message = "Incorrect \nusername/password";
		if (!check){
			write(usernameFile, user.getText() + "");
			//write(passwordFile, pass.getText() + "");
		}
		if(user.getText().toString().equals("admin") && pass.getText().toString().equals("MindtreeInterns")){// this will clear log in data in phone
			write(staticUser, "");
			write(usernameFile, "");
			write(passwordFile, "password");
			message = "Reset Completed";
		}
		else if( user.getText().toString().equals(read(staticUser)) && pass.getText().toString().equals(read(passwordFile))&& !pass.getText().toString().equals("") && !(user.getText().toString().equals("admin"))){
			message = "Next Page";
		}
		else if (!("" + user.getText()).equals("") && !("" + pass.getText()).equals("")) { // checks to see if there is a username and password
			message = "";
			
			if(pass.getText().toString().equals("password") && read(passwordFile).equals("password")){// first time users goes through here and creates an account
				write(staticUser, user.getText() + "");
				message = "Account Created";
			}
			else{
				message = "Incorrect \nusername/password\nPlease login as\n" + read(staticUser);
			}
		}	
		
		error.setText(message);
		
		
		
		//-----------------------------------------------------------------------------
		 AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
			 
			
             
             @Override
             protected Void doInBackground(Void... params) 
             {
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
                                 org.w3c.dom.Document doc = db.parse(inStream);  
                                 
                               String projectName = "";
                               ArrayList<String> project_list = new ArrayList<String>();  
                               NodeList nl = doc.getElementsByTagName("Attribute");
                               for(int i = 0; i < nl.getLength(); i++) 
                               {
                                   org.w3c.dom.Element nameElement = (org.w3c.dom.Element) nl.item(i);
                                   projectName = nameElement.getFirstChild().getNodeValue();
                                   project_list.add(projectName);
                               }
                               
                               for(int i = 0; i <project_list.size(); i++){
                                 System.out.println(project_list.get(i));
                               }
                               Handler mainHandler = new Handler(Looper.getMainLooper());
                               mainHandler.post(new Runnable() {

                                   @Override
                                   public void run() {
                                      displayProjects();
                          }
                         });
                               
                              } 
                          		
                          		catch (ClientProtocolException e) {
                                      
                                    e.printStackTrace();
                                } 
                          		catch (IOException e) {
                                       
                                  e.printStackTrace();
                                } 
                          		catch (SAXException e) {
                                       
                                    e.printStackTrace();
                                } 
                          		catch (ParserConfigurationException e) {
                                      
                                       e.printStackTrace();
                                }
                          
                return null;
             }

             @Override
             protected void onPostExecute(Void aVoid) {
                   System.out.println("SUCCESS");
                 // Notifies UI when the task is done
             }
             
             public void displayProjects() {
            	 setContentView(R.layout.projects);
            	 mainListView = (ListView) findViewById( R.id.mainListView );
                 //arraylist Append
            	 listAdapter = new ArrayAdapter<String>(this, R.layout.row, project_list);  
                 mainListView.setAdapter(listAdapter);
             }
            
             
             public void printResponse(View view) {
                        
             }
         }.execute();
     
         
         
     
    
	}

		//------------------------------------------------------------------------------
		// Find the ListView resource.   
	    /*mainListView = (ListView) findViewById( R.id.mainListView );
	    
	    // Create and populate a List of planet names.  
	    String[] project_array = new String[] { "TestProject", "Project2", "Project3", "Project4"};    
	    ArrayList<String> projects = new ArrayList<String>();  
	    projects.addAll( Arrays.asList(project_array) );  */
	    
	    
	    
	    // Create ArrayAdapter using the planet list.  
	    //listAdapter = new ArrayAdapter<String>(this, R.layout.row, projects);  
	    
	    //listAdapter.add( "Extra" );  
	 // Set the ArrayAdapter as the ListView's adapter. 
	 
	    //mainListView.setAdapter( listAdapter );
		
	   
		
		//Calling HTTP from data class
		//Data data = new Data();
		//data.printResponse(view);
		

	
	
	public void changePassword(View v){
		CheckBox change = (CheckBox)v;
		change.setChecked(false);
		Intent changePassword = new Intent(this, ChangePasswordActivity.class);
		startActivity(changePassword);
	}
	@SuppressWarnings("unused")
	private void goToPageActivity() {
		Intent changePassword = new Intent(this, ChangePasswordActivity.class);
		startActivity(changePassword);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

