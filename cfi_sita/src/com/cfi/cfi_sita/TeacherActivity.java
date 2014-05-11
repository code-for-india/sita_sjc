package com.cfi.cfi_sita;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TeacherActivity extends ActionBarActivity {
	private int choice = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacher_activity);

		Button next = (Button) findViewById(R.id.button1);
		final RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton1);
		final RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton2);
		final RadioButton rb3 = (RadioButton) findViewById(R.id.radioButton3);

		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!(rb1.isChecked() ||
						rb2.isChecked() ||
						rb3.isChecked())) {
					Toast.makeText(getApplicationContext(), "Please select an option", Toast.LENGTH_LONG).show();
					return;
				}
				Intent myIntent = new Intent(TeacherActivity.this, PlaygroundActivity.class);
				TeacherActivity.this.startActivity(myIntent);
			}
		});
		
		rb1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rb1.isChecked()) {
					rb2.setChecked(false);
					rb3.setChecked(false);
					SurveyData.teacher = 1;
				}
			}
		});
		
		rb2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rb2.isChecked()) {
					rb1.setChecked(false);
					rb3.setChecked(false);
					SurveyData.teacher = 2;
				}
			}
		});

		rb3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rb3.isChecked()) {
					rb1.setChecked(false);
					rb2.setChecked(false);
					SurveyData.teacher = 3;
				}
			}
		});


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
