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
import java.io.InputStreamReader;


public class MainActivity extends Activity {
    TextView error;
    boolean check = false;
    String usernameFile = "USRname.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readUsername();
    }
    public void toggle(View v){
        RadioButton remember = (RadioButton)v;
        remember.setChecked(check);
        check = !check;
    }
    public void writeUsername(){
        EditText user = (EditText)findViewById(R.id.username);
        String username = user.getText().toString();
        FileOutputStream write;
        try{
            write = openFileOutput(usernameFile, Context.MODE_PRIVATE);
            write.write(username.getBytes());
            write.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void readUsername(){
       try{
           FileInputStream input = openFileInput(usernameFile);
           BufferedReader inputRead = new BufferedReader(new InputStreamReader(input));
           String username = inputRead.readLine();
           EditText user = (EditText)findViewById(R.id.username);
           user.setText((CharSequence) username);
       }catch (FileNotFoundException e) {
           e.printStackTrace();

       }catch (IOException e) {
           e.printStackTrace();

       }

    }
    public void buttonClicked(View v){
        Button button = (Button)v;
        EditText user = (EditText)findViewById(R.id.username);
        EditText pass = (EditText)findViewById(R.id.password);
        error = (TextView)findViewById(R.id.error);
        String message = "Incorrect \nusername/password";

        if(!check)
            writeUsername();

        if(!("" + user.getText()).equals("") && !("" + pass.getText()).equals("")){
            message = "";

           goToPageActivity();
        }
        error.setText(message);
    }
    public void goToPageActivity(){
    	Intent projectList = new Intent(this, ProjectListViewActivity.class);
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
