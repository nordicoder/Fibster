package com.javapapers.android;

import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
//import android.support.v7.app.ActionBarActivity;

public class Send extends Activity {

	private int group1Id = 1;
	int homeId = Menu.FIRST;
	int homeId1 = Menu.FIRST+1;
	ShareExternalServer appUtil;
	SharedPreferences prefs;
 Context context;
 ImageView iv1,iv2;

	AsyncTask<Void, Void, String> shareRegidTask;
	EditText ed1,ed2,ed3;
	String t;
	String url;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send);
		ed1=(EditText)findViewById(R.id.editText1);
		ed2=(EditText)findViewById(R.id.editText2);
		ed3=(EditText)findViewById(R.id.editText4);
		ed3.setVisibility(0);
		ed3.setText("  ");
		
		iv1=(ImageView)findViewById(R.id.imageView1);
		iv2=(ImageView)findViewById(R.id.imageView2);

		
		android.app.ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
		   prefs = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);

		   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Bundle b=getIntent().getExtras();
	
		if(b!=null)
		{
		url=b.getString("url");
		Log.d("asd",url+"");
		}
	 context = this;
	 appUtil = new ShareExternalServer();
	 
	 iv1.setOnTouchListener(new OnTouchListener() {

		 @Override
		 public boolean onTouch(View v, MotionEvent event) {
		     if (event.getAction() == MotionEvent.ACTION_DOWN) {
		             // change color
		    	 GIDdaone();
		    	 iv1.setBackgroundColor(getResources().getColor(R.color.abc_search_url_text_selected));

		     }
		     else if (event.getAction() == MotionEvent.ACTION_UP) {
		             // set to normal color
		    	 iv1.setBackgroundColor(0);
		     }

		     return true;
		 }
		 });
	 
	iv2.setOnTouchListener(new OnTouchListener() {

		 @Override
		 public boolean onTouch(View v, MotionEvent event) {
		     if (event.getAction() == MotionEvent.ACTION_DOWN) {
		             // change color
		    	 MISdaone();
		  
		    	 iv2.setBackgroundColor(getResources().getColor(R.color.abc_search_url_text_selected));

		     }
		     else if (event.getAction() == MotionEvent.ACTION_UP) {
		             // set to normal color
		    	 iv2.setBackgroundColor(0);

		     }

		     return true;
		 }
		 });
	 

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send, menu);
//		menu.add(group1Id, homeId, homeId, "TE1!!");
//		menu.add(group1Id, homeId1, homeId1, "TE2!!");
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
		
		switch(id)
		{
		case 1:
			 t=ed1.getText().toString()+"<TE1";
			
			
			shareRegidTask = new AsyncTask<Void, Void, String>() {
				
				@Override
				protected String doInBackground(Void... params) {
					Log.d("dd","background stuff");

					String result = appUtil.shareRegIdWithAppServer(context, t);
					return result;
				}

				@Override
				protected void onPostExecute(String result) {
					shareRegidTask = null;
					Toast.makeText(getApplicationContext(), result,
							Toast.LENGTH_LONG).show();
					Log.d("ddd",result);
				}

			};
			shareRegidTask.execute(null, null, null);
		
			
			break;
			
		case 2:
			 t=ed1.getText().toString()+"<TE2";
				
				shareRegidTask = new AsyncTask<Void, Void, String>() {
					@Override
					protected String doInBackground(Void... params) {
						Log.d("dd","background stuff");

						String result = appUtil.shareRegIdWithAppServer(context, t);
						return result;
					}

					@Override
					protected void onPostExecute(String result) {
						shareRegidTask = null;
						Toast.makeText(getApplicationContext(), result,
								Toast.LENGTH_LONG).show();
						Log.d("ddd",result);
					}

				};
				shareRegidTask.execute(null, null, null);
			 break;
			
		
		
		
		
		
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void doit(View view)
	{
		
		ed2.setText(url);
		
		
	}
	
	public void MISdaone()
	{
		if(ed3.getText().toString().length()>0 && !ed3.getText().equals("Must attach a recipient"))
		{
		t=ed1.getText().toString()+"\n"+java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime())+"<<<"+ed3.getText().toString()+"<<<"+prefs.getString("NAME", "")+" "+prefs.getString("MIS", "")+"mmmm"+"<<<"+ed2.getText().toString()+"mmmm"+"|||a";
		
		
		shareRegidTask = new AsyncTask<Void, Void, String>() {
			
			@Override
			protected String doInBackground(Void... params) {
				Log.d("dd","background stuff");

				String result = appUtil.shareRegIdWithAppServer(context, t);
				return result;
			}

			@Override
			protected void onPostExecute(String result) {
				shareRegidTask = null;
				Toast.makeText(getApplicationContext(), result,
						Toast.LENGTH_LONG).show();
				Log.d("ddd",result);
           	 new DatabaseHandler(getApplicationContext()).addContact(new Contact(ed1.getText().toString(),ed2.getText().toString(),prefs.getString("NAME", "")+" "+prefs.getString("MIS", "")+"mmmm",ed3.getText().toString()+"mmmm"));

				Log.d("asdf","added");
			}

		};
		shareRegidTask.execute(null, null, null);
		}
		
		else 
		{
			
			ed3.setText("Must attach a recipient");
		}
	}
	
	
	public void GIDdaone()
	{
		if(ed3.getText().toString().length()>0 && !ed3.getText().equals("Must attach a recipient"))
		{
		t=ed1.getText().toString()+"\n"+java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime())+"<<<"+ed3.getText().toString()+"<<<"+prefs.getString("NAME", "")+" "+prefs.getString("MIS", "")+"mmmm"+"<<<"+ed2.getText().toString().toUpperCase()+"gggg"+"***a";
		Log.d("asdf",prefs.getString("NAME", ""));
		
		shareRegidTask = new AsyncTask<Void, Void, String>() {
			
			@Override
			protected String doInBackground(Void... params) {
				Log.d("dd","background stuff");

				String result = appUtil.shareRegIdWithAppServer(context, t);
				return result;
			}

			@Override
			protected void onPostExecute(String result) {
				shareRegidTask = null;
				Toast.makeText(getApplicationContext(), result,
						Toast.LENGTH_LONG).show();
				Log.d("ddd",result);
			}

		};
		shareRegidTask.execute(null, null, null);
		}
		
		else 
		{
			
			ed3.setText("Must attach a recipient");
		}
	}
	
	
	
	
	
	
	
	
}
