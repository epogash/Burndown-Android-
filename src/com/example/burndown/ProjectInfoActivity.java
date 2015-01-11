package com.example.burndown;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ProjectInfoActivity extends Activity {
	private ListView lv; 
	private ArrayAdapter<String> adapter;
	String scopeName;
	String projectName;
	String timeBoxId;
	ArrayList<String> projectInfoList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setTitle("Project Info");
		setContentView(R.layout.activity_project_info);
		scopeName = "";
		projectName = "";
		timeBoxId = "";
		projectInfoList = new ArrayList<String>();
		projectInfoList.add("Burndown");
		projectInfoList.add("Stories");
		projectInfoList.add("Time");
		projectInfoList.add("Velocity");
		
		
	    lv  = (ListView) findViewById(R.id.listView1);
	    //arraylist Append
	    adapter=new ArrayAdapter<String>(this,
	                android.R.layout.simple_list_item_1,
	                projectInfoList);
	    lv.setAdapter(adapter);
	    Intent intent = getIntent();
	    scopeName = intent.getStringExtra("scopeName");
	    projectName = intent.getStringExtra("projectName");
	    timeBoxId = intent.getStringExtra("timeBoxId");
	    
	    //System.out.println("INTENT: " + intent.getStringExtra("scopeName"));
	    lv.setOnItemClickListener(new OnItemClickListener() {
			   @Override
			   public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
				  String informationTypeName = projectInfoList.get(position);
				  if(informationTypeName.equals("Stories")){
					  Intent projectStoryIntent = new Intent(ProjectInfoActivity.this, ProjectStoriesActivity.class);
				  	  projectStoryIntent.putExtra("scopeName", scopeName);
				  	  projectStoryIntent.putExtra("projectName", projectName);
				  	  startActivity(projectStoryIntent);
				  }
				  else if(informationTypeName.equals("Burndown")){
					  Intent projectBurndownIntent = new Intent(ProjectInfoActivity.this, SprintBurndownActivity.class);
					  projectBurndownIntent.putExtra("sprintName", scopeName);
					  projectBurndownIntent.putExtra("projectName", projectName);
					  projectBurndownIntent.putExtra("timeBoxId", timeBoxId);
				  	  startActivity(projectBurndownIntent);
				  }
			   } 
			});
	    
	    
	}

	public void displayStories() {
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.project_info, menu);
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
