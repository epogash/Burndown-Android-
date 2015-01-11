package com.example.burndown;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewStyle.GridStyle;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class GraphActivity extends Activity {
	private String[] horizontalLabel = new String[] { "0", "1", "2", "3", "4", "5",	"6", "7", "8", "9", "10", "11", "12" };
	private String[] verticalLabel = new String[] { "120", "110", "100", "90", "80", "70", "60", "50", "40", "30", "20", "10", "0" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		graph();
		
	}
	private void graph(){
		// Intent intent = getIntent();
        // String hours = intent.getStringExtra("totalHours");
         int totalHours = 19;
		/*Begin*/
		
		// init example series data
		double v = 0;
		/*GraphViewData[] temp = new GraphViewData[10];
		for(int i = 0; i < totalHours; i++){
			temp[i] = new GraphViewData(totalHours-i, (i));
			//v= v + 0.3;
			//temp[i] = new GraphViewData(i, Math.sin(v));
		}*/
		GraphViewData[] temp1 = new GraphViewData[10];
		for(int i = 0; i < 10; i++){
			temp1[i] = new GraphViewData(i, -i);
		}
		//GraphViewSeries exampleSeries = new GraphViewSeries(temp);
		
		
		/*
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
		    new GraphViewData(0, 2.0d)
		    , new GraphViewData(2, 2d)
		    , new GraphViewData(3, 3d)
		    , new GraphViewData(4, 4d)
		});
		 */
		LineGraphView graphView = new LineGraphView(this, "GraphViewDemo");
		//graphView.addSeries(exampleSeries); // data
		GraphViewSeries exampleSeries1 = new GraphViewSeries(temp1);
		graphView.addSeries(exampleSeries1);
		graphView.getGraphViewStyle().setGridColor(Color.WHITE);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.YELLOW);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setTextSize(30);
		graphView.setHorizontalLabels(horizontalLabel);
		graphView.setVerticalLabels(verticalLabel);
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		layout.addView(graphView);
		
		/*End*/
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.graph, menu);
		return true;
	}
//	private void horizontalLabelMethod(int size){
//		for(int i = 0; i <= size; i++)
//			horizontalLabel[i] = (i*8) + "";
//		
//	}
//	private void verticalLabel(int size){
//		verticalLabel = new String[size];
//		for(int i = 0; i <= size; i++)
//			verticalLabel[i] = i + "";
//	}

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
