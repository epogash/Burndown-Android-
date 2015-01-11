package com.example.burndown;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SprintBurndownActivity extends Activity {
	ArrayList<String> storiesList;
	private ListView lv;
	private ArrayList<String> datesAndHoursList = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private String test = "";
	String projectName;
	String sprintName;
	String timeBoxId;
	private int totalHours = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);

		// TextView message = (TextView) findViewById(R.id.textView1);

		Intent intent = getIntent();
		projectName = intent.getStringExtra("projectName");
		sprintName = intent.getStringExtra("sprintName");
		timeBoxId = intent.getStringExtra("timeBoxId");

		System.out.println(projectName);
		System.out.println(sprintName);
		System.out.println(timeBoxId);

		AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				String accessToken = "";
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						"https://www6.v1host.com/MindtreeLTD/oauth.v1/token");
				httppost.setHeader("Host", "www6.v1host.com");
				httppost.setHeader("Content-Type",
						"application/x-www-form-urlencoded");

				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						4);
				nameValuePairs
						.add(new BasicNameValuePair(
								"refresh_token",
								"74PV!IAAAAODQrMS--Q3pnj7ONldMS3B9jJnMtWV1O-tFFbLOIXoowQAAAAHv8BUG3sFF3X_RxRvJGXWmYA2xpcqdGezL4wmiDoyK1bvepk5DQr4qQPY7pLXbqfW0GeJ-ZCB_fYhK5HvsO2bx0W7OJktaSQEVg_nQR36bX3a6edNajSj-A-KsDGvi1Di2YiG_4ujCw1NItHCSJGhedfQUZbfe7T_Bm__6ufeIsogMnUMzUUgi5eJPSuN5gafb-TYfheUu-cnGKafxXDpZDAXvA2n5nuTnQcwkhiQeQYhmswTpswePv94HD8XeT64"));
				nameValuePairs.add(new BasicNameValuePair("grant_type",
						"refresh_token"));
				nameValuePairs.add(new BasicNameValuePair("client_id",
						"client_m4xy6r47"));
				nameValuePairs.add(new BasicNameValuePair("client_secret",
						"j9xrqqp5vthbu56okx27"));
				try {
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("FIRST ERROR");
				}

				// Execute HTTP Post Request
				try {
					HttpResponse response = httpclient.execute(httppost);

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent(), "UTF-8"));
					String json = reader.readLine();
					JSONTokener tokener = new JSONTokener(json);
					JSONObject finalResult = new JSONObject(tokener);
					accessToken = finalResult.getString("access_token");
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("SECOND ERROR");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("SECOND ERROR");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("SECOND ERROR");
				}
				// https://www6.v1host.com/MindtreeLTD/rest-1.v1/Data/Timebox?sel=Name&where=Schedule.ScheduledScopes.Name="
				// + projectName
				HttpPost storyHttpPost = new HttpPost(
						"https://www6.v1host.com/MindtreeLTD/query.v1");
				storyHttpPost.setHeader("Host", "www6.v1host.com");
				storyHttpPost.setHeader("Content-type", "application/json");
				storyHttpPost.setHeader("Authorization", "Bearer "
						+ accessToken);
				// PARSE JSON RESPONSE

				// "from: Timebox\nselect:\n  - Name\n  - Workitems.ToDo.@Sum\n  - BeginDate\n  - EndDate\nwhere:\n  ID: '"
				// + timeBoxId + "'\nasof: 2014-05-28\n\n---\n\n" +
				// "from: Timebox\nselect:\n  - Name\n  - Workitems.ToDo.@Sum\n  - BeginDate\n  - EndDate\nwhere:\n  ID: '"
				// + timeBoxId + "'\nasof: 2014-05-28\n\n---\n\n" +;

				try {
					String str = "from: Timebox\nselect:\n  - Name\n  - Workitems.ToDo.@Sum\n  - BeginDate\n  - EndDate\nwhere:\n  ID: '"
							+ timeBoxId + "'";

					System.out.println(str);
					StringEntity parameters = new StringEntity(str);
					storyHttpPost.setEntity(parameters);
					HttpClient httpclient2 = new DefaultHttpClient();
					HttpResponse response = httpclient2.execute(storyHttpPost);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent(), "UTF-8"));
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						System.out.println("&&&&&&&&&&&" + line);
						sb.append(line);

					}
					System.out.println(sb.toString());
					JSONTokener tokener = new JSONTokener(sb.toString());
					JSONArray finalResult = new JSONArray(tokener);
					JSONObject firstObject = finalResult.getJSONArray(0)
							.getJSONObject(0);
					final String beginDate = firstObject.getString("BeginDate").substring(0, 10);
					final String endDate = firstObject.getString("EndDate").substring(0, 10);
					System.out.println(beginDate);
					System.out.println(endDate);
					String parsedBeginDate = parseDate(beginDate);
					String parsedEndDate = parseDate(endDate);
					System.out.println(parsedBeginDate);
					System.out.println(parsedEndDate);

					Calendar beginCalendar = Calendar.getInstance();
					beginCalendar.set(Calendar.YEAR,
							Integer.parseInt(parsedBeginDate.substring(0, 4)));
					beginCalendar.set(Calendar.MONTH,
							Integer.parseInt(parsedBeginDate.substring(5, 7)));
					beginCalendar.set(Calendar.DAY_OF_MONTH,
							Integer.parseInt(parsedBeginDate.substring(8)));

					Calendar endCalendar = Calendar.getInstance();
					endCalendar.set(Calendar.YEAR,
							Integer.parseInt(parsedEndDate.substring(0, 4)));
					endCalendar.set(Calendar.MONTH,
							Integer.parseInt(parsedEndDate.substring(5, 7)));
					endCalendar.set(Calendar.DAY_OF_MONTH,
							Integer.parseInt(parsedEndDate.substring(8)));

					final int numberOfDays = (int) daysBetween(beginCalendar,
							endCalendar);
					System.out.println("Number of Days:" + numberOfDays);
					StringBuilder query = new StringBuilder();
					String date = "";
					String day = "";
					String month = "";

					for (int i = 0; i < numberOfDays; i++) {
						if (beginCalendar.get(Calendar.MONTH) < 10) {
							month = "0" + beginCalendar.get(Calendar.MONTH);
						} else {
							month = "" + beginCalendar.get(Calendar.MONTH);
						}

						if (beginCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
							day = "0"
									+ beginCalendar.get(Calendar.DAY_OF_MONTH);
						} else {
							day = "" + beginCalendar.get(Calendar.DAY_OF_MONTH);
						}

						date = beginCalendar.get(Calendar.YEAR) + "-" + month
								+ "-" + day;
						if (i != numberOfDays - 1) {
							query.append("from: Timebox\nselect:\n  - Name\n  - Workitems.ToDo.@Sum\n  - BeginDate\n  - EndDate\nwhere:\n  ID: '"
									+ timeBoxId
									+ "'\nasof: "
									+ date
									+ "\n\n---\n\n");
						} else {
							query.append("from: Timebox\nselect:\n  - Name\n  - Workitems.ToDo.@Sum\n  - BeginDate\n  - EndDate\nwhere:\n  ID: '"
									+ timeBoxId + "'\nasof: " + date);
						}
						beginCalendar.add(Calendar.DATE, 1);
					}
					System.out.println(query);
					HttpPost burndownPost = new HttpPost(
							"https://www6.v1host.com/MindtreeLTD/query.v1");
					burndownPost.setHeader("Host", "www6.v1host.com");
					burndownPost.setHeader("Content-type", "application/json");
					burndownPost.setHeader("Authorization", "Bearer "
							+ accessToken);

					StringEntity parameters2 = new StringEntity(
							query.toString());
					burndownPost.setEntity(parameters2);
					HttpClient httpclient3 = new DefaultHttpClient();
					HttpResponse response2 = httpclient3.execute(burndownPost);
					BufferedReader reader2 = new BufferedReader(
							new InputStreamReader(response2.getEntity()
									.getContent(), "UTF-8"));
					final StringBuilder sb2 = new StringBuilder();
					String line2 = null;

					while ((line2 = reader2.readLine()) != null) {
						sb2.append(line2);
						System.out.println("Printing line2 " + line2);
					}
					System.out.println(sb2.toString());					
					
					
					Handler mainHandler = new Handler(Looper.getMainLooper());
					mainHandler.post(new Runnable() {

						@Override
						public void run() {
							try {
								getInfoForBurndown(sb2.toString(), beginDate, endDate, numberOfDays);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
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

				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				System.out.println("SUCCESS");
				// Notifies UI when the task is done
			}
		}.execute();
	}
	public void getInfoForBurndown(String sb2, String beginDate, String endDate, int numberOfDays) throws JSONException {
		JSONTokener tokener2 = new JSONTokener(sb2.toString());
		JSONArray hoursArray = new JSONArray(tokener2);
		double firstHours = 0;
		int i;
		int count = 0;
		for (i = 0; hoursArray.getJSONArray(i).length() == 0; i++) {
			count++;
		}

		final ArrayList<String> datesAndHoursList = new ArrayList<String>();
		for (int j = 0; j < count; j++) {
			datesAndHoursList.add("0");
		}

		JSONObject sprintInfo = hoursArray.getJSONArray(i)
				.getJSONObject(0);
		double currentHours = 0;
		if(sprintInfo.getString("Workitems.ToDo.@Sum") != "null") {
			currentHours = Double.parseDouble(sprintInfo
					.getString("Workitems.ToDo.@Sum"));
		}
	
		if (firstHours < currentHours) {
			firstHours = currentHours;
		}
		System.out.println("Max Hours:" + firstHours);

		String hours = "";
		for (; i < hoursArray.length(); i++) {
			JSONObject sprints = hoursArray.getJSONArray(i)
					.getJSONObject(0);
			hours = sprints.getString("Workitems.ToDo.@Sum");
			datesAndHoursList.add(hours);
		}
		for (int j = 0; j < datesAndHoursList.size(); j++) {
			System.out.println(datesAndHoursList.get(j));
		}
		graph(beginDate, endDate, numberOfDays, datesAndHoursList, firstHours);
	}
	private void graph(String beginDate, String endDate, int numberOfDays, ArrayList<String> datesAndHoursList, double maxHours) {
		String[] horizontalLabel = new String[numberOfDays];
		if(horizontalLabel.length == 0) {
			return;
		}
		for(int i = 0; i < horizontalLabel.length; i++) {
			horizontalLabel[i] = " ";
		}
		horizontalLabel[0] = beginDate;
		horizontalLabel[horizontalLabel.length - 1] = endDate;
		double max = 0;
		for(int i = 0; i < datesAndHoursList.size(); i++) {
			double current = 0;
			if(datesAndHoursList.get(i) != "null") {
				current =  Double.parseDouble(datesAndHoursList.get(i));
			} 
			if(max < current) {
				max = current;
			}
		}
 		System.out.println("MAX: " + max);
//		for(int i = 0; i < datesAndHoursList.size(); i++) {
//			verticalLabel[i] = Integer.toString(max * (i+1) / 5);
//		}
		
		System.out.println("Entering Graph " + horizontalLabel.length);

		GraphViewData[] temp1 = new GraphViewData[datesAndHoursList.size()];
		ArrayList<Integer> dayList = new ArrayList<Integer>();
		for(int i = 0; i < numberOfDays; i++) {
			dayList.add(i);
		}
		
		for (int i = 0; i < datesAndHoursList.size(); i++) {
			String data = datesAndHoursList.get(i);
			if(data != "null") {
				temp1[i] = new GraphViewData(dayList.get(i), Double.parseDouble(data));
			} else {
				temp1[i] = new GraphViewData(dayList.get(i), 0);
			}
			
		}
		// GraphViewSeries exampleSeries = new GraphViewSeries(temp);

		/*
		 * GraphViewSeries exampleSeries = new GraphViewSeries(new
		 * GraphViewData[] { new GraphViewData(0, 2.0d) , new GraphViewData(2,
		 * 2d) , new GraphViewData(3, 3d) , new GraphViewData(4, 4d) });
		 */
		GraphViewData[] ideal = new GraphViewData[2];
		//only 2 points needed - it's the ideal line
		ideal[0] = new GraphViewData(0, max);
		ideal[1] = new GraphViewData(dayList.size(), 0);
		
		LineGraphView graphView = new LineGraphView(this, "Burndown");
		// graphView.addSeries(exampleSeries); // data
		 
		GraphViewSeries exampleSeries1 = new GraphViewSeries("Actual", new GraphViewSeriesStyle(Color.rgb(200, 50, 00), 3),temp1);
		GraphViewSeries idealLineSeries = new GraphViewSeries("Ideal",new GraphViewSeriesStyle(Color.rgb(135, 206, 235), 3), ideal);
		graphView.addSeries(exampleSeries1);
		graphView.addSeries(idealLineSeries);
		graphView.getGraphViewStyle().setNumVerticalLabels(5);
        graphView.setManualYAxisBounds(max, 0);
		graphView.getGraphViewStyle().setGridColor(Color.WHITE);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.WHITE);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.WHITE);
		graphView.getGraphViewStyle().setTextSize(30);
		graphView.setHorizontalLabels(horizontalLabel);
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		layout.addView(graphView);

		/* End */
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.project_stories, menu);
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

	String parseDate(String date) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			sb.append(date.charAt(i));
		}
		return sb.toString();
	}

	public static long daysBetween(Calendar startDate, Calendar endDate) {
		Calendar date = (Calendar) startDate.clone();
		long daysBetween = 0;
		while (date.before(endDate)) {
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}
}
