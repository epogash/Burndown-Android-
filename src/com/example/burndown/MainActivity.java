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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
	HashPassword hashPass = new HashPassword();
	ArrayList<String> project_list;
	AsyncTask<Void, Void, Void> asyncTask;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setTitle("Burn Down");
		setContentView(R.layout.activity_main);
		project_list = new ArrayList<String>();
		
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
		if((read(staticUser)+"").equals("")){
			if(!("" + user.getText()).equals("")){
				write(staticUser, user.getText() + "");
				write(passwordFile, hashPass.hash("password"));
				message = "Account Created";
			}
			else{
				message = "Please create an account by entering a valid Username and password will be 'password'";
			}
		}
		else if(user.getText().toString().equalsIgnoreCase("admin") && pass.getText().toString().equals("MindtreeInterns")){// this will clear log in data in phone
			write(staticUser, "");
			write(usernameFile, "");
			write(passwordFile, hashPass.hash("password"));
			message = "Reset Completed";
		}
		else if( user.getText().toString().equalsIgnoreCase(read(staticUser)) && hashPass.hash(pass.getText().toString()).equals(read(passwordFile))&& !pass.getText().toString().equals("") && !(user.getText().toString().equals("admin"))){
			displayProjects();
			message = "";
		}
		
		error.setText(message);
		
	}
	 public void displayProjects() {
		Intent changePassword = new Intent(this, Projects.class);
		startActivity(changePassword);
       }
   
       public void printResponse(View view) {
           
       }
       
	public void changePassword(View v){
		CheckBox change = (CheckBox)v;
		change.setChecked(false);
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

