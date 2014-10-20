package com.example.burndown;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
		
		setContentView(R.layout.projects);
		// Find the ListView resource.   
	    mainListView = (ListView) findViewById( R.id.mainListView );
	    
	    // Create and populate a List of planet names.  
	    String[] project_array = new String[] { "TestProject", "Project2", "Project3", "Project4"};    
	    ArrayList<String> projects = new ArrayList<String>();  
	    projects.addAll( Arrays.asList(project_array) );  
	    
	    
	    
	    // Create ArrayAdapter using the planet list.  
	    listAdapter = new ArrayAdapter<String>(this, R.layout.row, projects);  
	    
	    listAdapter.add( "Extra" );  
	 // Set the ArrayAdapter as the ListView's adapter. 
	 //THIS LINE OF CODE IS CAUSING THE APP TO CRASH---------------
	    mainListView.setAdapter( listAdapter );
		
	   
		
		
		//Calling HTTP from data class
		Data data = new Data();
		data.printResponse(view);
		
	}
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
