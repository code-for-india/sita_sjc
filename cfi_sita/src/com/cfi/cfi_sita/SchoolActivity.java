package com.cfi.cfi_sita;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SchoolActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.school_activity);

		Button next = (Button) findViewById(R.id.button1);
		final EditText school = (EditText) findViewById(R.id.editText1);
		final EditText reviewer = (EditText) findViewById(R.id.editText2);

		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String schoolName = school.getText().toString();
				String reviewerName = reviewer.getText().toString();
				if (schoolName.matches("") || reviewerName.matches("")) {
					Toast.makeText(getApplicationContext(), "Please fill the two fields", Toast.LENGTH_LONG).show();
					return;
				}
				SurveyData.school = school.getText().toString();
				SurveyData.reviewer = reviewer.getText().toString();
				Location location = getCurrentLocation();
				SurveyData.latitude = location == null ? -1 : location.getLatitude();
				SurveyData.longitude = location == null ? -1 : location.getLongitude();
				Toast.makeText(getApplicationContext(), SurveyData.latitude + ", " + SurveyData.longitude, Toast.LENGTH_SHORT).show();
				Intent myIntent = new Intent(SchoolActivity.this, RampActivity.class);
				SchoolActivity.this.startActivity(myIntent);
			}
		});
	}

	Location getCurrentLocation() {
		Location location = null;
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (locationManager != null) {
			Criteria crta = new Criteria();
			crta.setAccuracy(Criteria.ACCURACY_FINE);
			crta.setCostAllowed(false);
			crta.setPowerRequirement(Criteria.POWER_LOW); 
			String provider = locationManager.getBestProvider(crta, true);
			location = locationManager.getLastKnownLocation(provider);
		}
		return location;
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
