package com.cfi.cfi_sita;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (getIntent().hasExtra("dialog")) {
			displayDialog();
			getIntent().removeExtra("dialog");
		}

		Button rate = (Button) findViewById(R.id.button1);
		Button photo = (Button) findViewById(R.id.button2);
		Button language = (Button) findViewById(R.id.button3);

		rate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(MainActivity.this, SchoolActivity.class);
				MainActivity.this.startActivity(myIntent);
			}
		});

		photo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showPrompt();
			}
		});

		language.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String languageToLoad;
				if (SurveyData.language == 0) {
					languageToLoad  = "ta";
					SurveyData.language = 1;
				} else {
					languageToLoad = "en";
					SurveyData.language = 0;
				}
				Locale locale = new Locale(languageToLoad); 
				Locale.setDefault(locale);
				Configuration config = new Configuration();
				config.locale = locale;
				getBaseContext().getResources().updateConfiguration(config, 
						getBaseContext().getResources().getDisplayMetrics());
				finish();
				startActivity(getIntent());			}
		});
	}

	private void showPrompt() {
		LayoutInflater li = LayoutInflater.from(this);
		View promptView = li.inflate(R.layout.prompt, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setView(promptView);

		final EditText schoolName = (EditText) promptView
				.findViewById(R.id.editText1);
		final EditText comment = (EditText) promptView
				.findViewById(R.id.editText2);

		// set dialog message
		alertDialogBuilder
		.setCancelable(false)
		.setPositiveButton("Take Photo",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				if (schoolName.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "Please type school name", Toast.LENGTH_SHORT).show();
				} else {
					SurveyData.photoName = schoolName.getText().toString();
					SurveyData.comment = comment.getText().toString();
					Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(intent, 0);
				}
			}
		})
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	private void displayDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle(getText(R.string.dialog_title));

		// set dialog message
		alertDialogBuilder
		.setMessage(getText(R.string.dialog_msg))
		.setCancelable(false)
		.setNegativeButton("Close",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}


	@SuppressLint("NewApi") @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			Bitmap bm = (Bitmap) data.getExtras().get("data"); 
			int bytes = bm.getByteCount();
			ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
			bm.copyPixelsToBuffer(buffer); //Move the byte data to the buffer
			byte[] array = buffer.array(); 
			postData(array);
		}
	}

	public void postData(byte[] array) {
		// Create a new HttpClient and Post Header
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://utssrc.appspot.com/media");

			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			String encodedImage = Base64.encodeToString(array, Base64.DEFAULT);
			nameValuePairs.add(new BasicNameValuePair("photo", encodedImage));
			nameValuePairs.add(new BasicNameValuePair("school", SurveyData.photoName));
			nameValuePairs.add(new BasicNameValuePair("comment", SurveyData.comment));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			String responseBody = EntityUtils.toString(response.getEntity());  
			Toast.makeText(getApplicationContext(), "HTTP Request sent", Toast.LENGTH_SHORT).show();
			Toast.makeText(getApplicationContext(), responseBody, Toast.LENGTH_LONG).show();
		} catch (ClientProtocolException e) {
			Log.e("HTTP", "Failed to post to server");
		} catch (IOException e) {
			Log.e("HTTP", "Failed to get response from server");
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
