package com.example.burndown;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePasswordActivity extends Activity {
	private String passwordFile = "PASS.txt";
	private String usernameFile = "staticUser.txt";
	HashPassword hashPass = new HashPassword();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setTitle("Change Password");
		setContentView(R.layout.activity_change_password);
		TextView message = (TextView) findViewById(R.id.welcomeMessage);

		String user = read(usernameFile);
		message.setText("Hello, " + user);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
		return true;

	}

	private void write(String file, String value) {
		FileOutputStream write;
		try {
			write = openFileOutput(file, Context.MODE_PRIVATE);
			write.write(value.getBytes());
			write.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String read(String file) {

		String value = "";
		try {
			FileInputStream input = openFileInput(file);
			BufferedReader inputRead = new BufferedReader(
					new InputStreamReader(input));
			value = inputRead.readLine();
			return value;

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
		return value;
	}
	@SuppressWarnings("unused")
	private void refresh() {
		
		finish();
		startActivity(getIntent());
		//Intent projectList = new Intent(this, ChangePasswordActivity.class);
		//startActivity(projectList);

	}
	public void changePassword(View v) {
		EditText currentPass = (EditText) findViewById(R.id.currentPass);
		EditText newPass = (EditText) findViewById(R.id.newPass);
		EditText confirmPass = (EditText) findViewById(R.id.confirmPass);
		TextView message = (TextView) findViewById(R.id.welcomeMessage);
		String user = read(usernameFile);
		
		//super.onBackPressed();
		String password = read(passwordFile);
		//write(passwordFile, newPass.getText().toString());
		if(password.equals(hashPass.hash(currentPass.getText().toString()))){
			if(newPass.getText().toString().equals(confirmPass.getText().toString()) && !confirmPass.getText().toString().equals("") ){
				if(confirmPass.getText().toString().length() > 5){
					write(passwordFile, hashPass.hash(newPass.getText().toString()));
					super.onBackPressed();
				}
				else{
					System.out.println("\n\n\nNew Password must be \nat least 6 characters long");
					message.setText("Hello, " + user + "\n\n\nNew Password must be \nat least 6 characters long");
				}
			}
			
			else{
				System.out.println("\n\n\nNew Password and Confirm Password\nMust be the same");
				message.setText("Hello, " + user + "\n\n\nNew Password and Confirm Password\nMust be the same");
				
			}
		}
		else{
			message.setText("Hello, " + user + "\n\n\nPlease make sure you are using all fields");
		}

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
