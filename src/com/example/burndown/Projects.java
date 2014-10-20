package com.example.burndown;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Projects extends Activity
{
	private String passwordFile = "PASS.txt";
	private String usernameFile = "staticUser.txt";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		TextView message = (TextView) findViewById(R.id.welcome_message);

		String user = read(usernameFile);
		message.setText("Hello, " + user);
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
}
