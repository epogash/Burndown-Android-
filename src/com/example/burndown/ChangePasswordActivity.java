package com.example.burndown;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePasswordActivity extends Activity {
	private MainActivity object = new MainActivity();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
		return true;
		
	}

	public String read(String file) {

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

	public void changePassword(View v){
		EditText currentPass = (EditText)findViewById(R.id.currentPass);
		EditText newPass = (EditText)findViewById(R.id.newPass);
		EditText confirmPass = (EditText)findViewById(R.id.confirmPass);
		TextView message = (TextView)findViewById(R.id.welcomeMessage);

		String test = read("PASS.txt");
		message.setText(test);
		
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
