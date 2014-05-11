package com.cfi.cfi_sita;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class LibraryActivity extends ActionBarActivity {
	private int value;
	private int choice = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.library_activity);
		Button finish = (Button) findViewById(R.id.button1);
		final RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton1);
		final RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton2);
		final RadioButton rb3 = (RadioButton) findViewById(R.id.radioButton3);

		finish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!(rb1.isChecked() ||
						rb2.isChecked() ||
						rb3.isChecked())) {
					Toast.makeText(getApplicationContext(), "Please select an option", Toast.LENGTH_LONG).show();
					return;
				}
				postData();
				Intent myIntent = new Intent(LibraryActivity.this, MainActivity.class);
				myIntent.putExtra("dialog", true);
				LibraryActivity.this.startActivity(myIntent);
			}
		});

		rb1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rb1.isChecked()) {
					rb2.setChecked(false);
					rb3.setChecked(false);
					SurveyData.library = 1;
				}
			}
		});

		rb2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rb2.isChecked()) {
					rb1.setChecked(false);
					rb3.setChecked(false);
					SurveyData.library = 2;
				}
			}
		});

		rb3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rb3.isChecked()) {
					rb1.setChecked(false);
					rb2.setChecked(false);
					SurveyData.library = 3;
				}
			}
		});
	}

	public void postData() {
		// Create a new HttpClient and Post Header
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://utssrc.appspot.com/test");

			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("schoolname", SurveyData.school));
			nameValuePairs.add(new BasicNameValuePair("lat", Double.toString(SurveyData.latitude)));
			nameValuePairs.add(new BasicNameValuePair("lng", Double.toString(SurveyData.longitude)));
			nameValuePairs.add(new BasicNameValuePair("library", Integer.toString(SurveyData.ramp)));
			nameValuePairs.add(new BasicNameValuePair("ramp", Integer.toString(SurveyData.library)));
			nameValuePairs.add(new BasicNameValuePair("teacher", Integer.toString(SurveyData.teacher)));
			nameValuePairs.add(new BasicNameValuePair("classroom", Integer.toString(SurveyData.classroom)));
			nameValuePairs.add(new BasicNameValuePair("playground", Integer.toString(SurveyData.playground)));
			nameValuePairs.add(new BasicNameValuePair("toilet", Integer.toString(SurveyData.toilet)));
			nameValuePairs.add(new BasicNameValuePair("meal", Integer.toString(SurveyData.meal)));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			String responseBody = EntityUtils.toString(response.getEntity());  
			Toast.makeText(getApplicationContext(), "HTTP Request sent", Toast.LENGTH_SHORT).show();
			Toast.makeText(getApplicationContext(), responseBody, Toast.LENGTH_LONG).show();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
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
