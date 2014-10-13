package com.example.burndown;

//package com.abelalvarez.designproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
	private TextView error;
	private boolean check = false;
	private String passwordFile = "PASS.txt";
	private String usernameFile = "USRname.txt";
	
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
		readPassword();
	}

	private void toggle(View v) {
		RadioButton remember = (RadioButton) v;
		remember.setChecked(check);
		check = !check;
	}
	private void readPassword() {
		EditText user = (EditText) findViewById(R.id.password);
		user.setText((CharSequence) read(passwordFile));
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

	public void buttonClicked(View v) {
		//Button button = (Button) v;
		EditText user = (EditText) findViewById(R.id.username);
		EditText pass = (EditText) findViewById(R.id.password);
		error = (TextView) findViewById(R.id.error);
		String message = "Incorrect \nusername/password";

		if (!check){
			write(usernameFile, user.getText() + "");
			write(passwordFile, pass.getText() + "");
		}

		if (!("" + user.getText()).equals("")
				&& !("" + pass.getText()).equals("")) {
			message = "";
			goToPageActivity();
		}
		error.setText(message);
	}

	private void goToPageActivity() {
		Intent projectList = new Intent(this, ChangePasswordActivity.class);
		startActivity(projectList);

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
