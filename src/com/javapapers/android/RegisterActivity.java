package com.javapapers.android;

import java.io.IOException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class RegisterActivity extends Activity {

	Button btnGCMRegister;
	Button btnAppShare;
	GoogleCloudMessaging gcm;
	ShareExternalServer appUtil;
	AsyncTask<Void, Void, String> shareRegidTask;
	Context context;
	int gg=0;
	String regId,name,mis,abcstr;
	SharedPreferences prefs;
	EditText ed1;
	EditText ed2,ed3;
	String str,grp;

	private int group1Id = 1;
	int homeId = Menu.FIRST;
	
	public static final String REG_ID = "regId";
	private static final String APP_VERSION = "appVersion";

	static final String TAG = "Register Activity";

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
//		android.app.ActionBar bar = getActionBar();
//		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2E2EE6")));
		context = getApplicationContext();
		ed1=(EditText)findViewById(R.id.editText1);
		ed2=(EditText)findViewById(R.id.editText2);
		ed3=(EditText)findViewById(R.id.editText3);
		   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		
		appUtil = new ShareExternalServer();

		
		

	  prefs = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	  /* Editor e=prefs.edit();
	   e.clear();*/
	   if (!prefs.getString(REG_ID, "").equals("")  )
	    {
			
//		   shareRegidTask = new AsyncTask<Void, Void, String>() {
//   			
//   			@Override
//   			protected String doInBackground(Void... params) {
//   				Log.d("dd","background stuff");
//
//   				String result = appUtil.shareRegIdWithAppServer(getApplicationContext(), "");
//   				return result;
//   			}
//
//   			@Override
//   			protected void onPostExecute(String result) {
//   				shareRegidTask = null;
//   				Toast.makeText(getApplicationContext(), result,
//   						Toast.LENGTH_LONG).show();
//   				Log.d("ddd",result);
//   			}
//		   };
		   
		   
		Log.d("asdfg","he bahg"+prefs.getString(REG_ID, ""));
			
			startActivity(new Intent(this, Chats.class));
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
	      gg=1;
	   finish();
	    }
	   gg=2;
	   
		regId=prefs.getString(REG_ID, "");
		name=prefs.getString("NAME","");
		mis=prefs.getString("MIS", "");

		

		btnGCMRegister = (Button) findViewById(R.id.btnGCMRegister);
		btnGCMRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (TextUtils.isEmpty(regId)) {
					regId = registerGCM();
					Log.d("RegisterActivity", "GCM RegId: " + regId);
				} else {
					Toast.makeText(getApplicationContext(),
							"Already Registered with GCM Server!",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		btnAppShare = (Button) findViewById(R.id.btnAppShare);
		btnAppShare.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View arg0) {
				
				SharedPreferences.Editor editor = prefs.edit();
				
				if (TextUtils.isEmpty(regId)) {
					Toast.makeText(getApplicationContext(), "RegId is empty!",
							Toast.LENGTH_LONG).show();
				} 
				
				
				
				
				else if(ed1.getText().toString().isEmpty())
				{
					if(ed1.getText().toString().isEmpty())
					{
						ed1.setText("Enter Group ID");
					}
					
					 else if(!ed1.getText().toString().equals("Enter Group ID"))
					 {
						 
						 grp=ed1.getText().toString();
						 
					 }
					 else if(ed1.getText().toString().equals("Enter Group ID"))
					 {
						 
						 Toast.makeText(context, "Please Enter a valid Group Id", Toast.LENGTH_SHORT);
					 }
				}
				
				
				
				else if(prefs.getString("MIS", "").isEmpty())
				{ 
				 if(ed2.getText().toString().isEmpty())
				{
					ed2.setText("Enter MIS ID");
				}
				
				 else if(!ed2.getText().toString().equals("Enter MIS ID"))
				 {
					 editor.putString("MIS", ed2.getText().toString());
						editor.commit();

					 
				 }
				 else if(ed2.getText().toString().equals("Enter MIS ID"))
				 {
					 
					 Toast.makeText(context, "Please Enter a valid MIS Id", Toast.LENGTH_SHORT);
				 }
				 
				}
				else if(prefs.getString("NAME", "").isEmpty())
				{
					
					if(ed3.getText().toString().isEmpty())
					{
						
						ed3.setText("Enter name");
					}
					
					 else if(!ed3.getText().toString().equals("Enter name"))
					 {
						 editor.putString("NAME", ed3.getText().toString());
							editor.commit();

						 
					 }
					 else if(ed1.getText().toString().equals("Enter name"))
					 {
						 
						 Toast.makeText(context, "Please Enter a valid name", Toast.LENGTH_SHORT);
					 }
					
					
				}
				
				
				
				
				
				
			else{
					
					
					
				
					
					
					
					Intent i = new Intent(getApplicationContext(),
							Chats.class);
					
				
					
					i.putExtra("regId", regId+">>>"+ed1.getText().toString().toUpperCase()+">>>"+prefs.getString("NAME","")+">>>"+prefs.getString("MIS", ""));
					Log.d("RegisterActivity",
							"onClick of Share: Before starting main activity.");
					

				      abcstr = regId+">>>"+ed1.getText().toString().toUpperCase()+"gggg"+">>>"+prefs.getString("NAME","")+">>>"+prefs.getString("MIS", "").toString().toUpperCase()+"mmmm";
				      
				      /*editor.putString("NAME", ed3.getText().toString());
						editor.commit();
						
						 editor.putString("MIS", ed2.getText().toString());
							editor.commit();*/
					      
				      
				      
				       Log.d("truth",regId+">>>"+ed1.getText().toString().toUpperCase()+"gggg"+">>>"+prefs.getString("NAME","")+">>>"+prefs.getString("MIS", "").toUpperCase()+"mmmm");
				       
				       Intent localIntent = new Intent(getApplicationContext(), Chats.class);
				      localIntent.putExtra("regId", str+">>>"+ed1.getText().toString()+">>>"+ed2.getText().toString());
				      
				      
							Log.d("dd","start sending");
						shareRegidTask = new AsyncTask<Void, Void, String>() {
							@Override
							protected String doInBackground(Void... params) {
								Log.d("dd","background stuff");
								String result = appUtil.shareRegIdWithAppServer(context, abcstr);
								
								return result;
							}

							@Override
							protected void onPostExecute(String result) {
								shareRegidTask = null;
								Toast.makeText(getApplicationContext(), result,
										Toast.LENGTH_LONG).show();
							
							}

						};
						shareRegidTask.execute(null, null, null);
					
					
					startActivity(i);
					overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
					finish();
					Log.d("RegisterActivity", "onClick of Share: After finish.");
				
			}
			}
		});
	}

	public String registerGCM() {

		gcm = GoogleCloudMessaging.getInstance(this);
		regId = getRegistrationId(context);

		if (TextUtils.isEmpty(regId)) {

			registerInBackground();

			Log.d("RegisterActivity",
					"registerGCM - successfully registered with GCM server - regId: "
							+ regId);
		} else {
			Toast.makeText(getApplicationContext(),
					"RegId already available. RegId: " + regId,
					Toast.LENGTH_LONG).show();
		}
		return regId;
	}

	private String getRegistrationId(Context context) {
	 prefs = getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		String registrationId = prefs.getString(REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			Log.d("RegisterActivity",
					"I never expected this! Going down, going down!" + e);
			throw new RuntimeException(e);
		}
	}

	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regId = gcm.register(Config.GOOGLE_PROJECT_ID);
					Log.d("RegisterActivity", "registerInBackground - regId: "
							+ regId);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString(REG_ID, regId);
					editor.commit();
					msg = "Device registered, registration ID=" + regId;

					storeRegistrationId(context, regId);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					Log.d("RegisterActivity", "Error: " + msg);
				}
				Log.d("RegisterActivity", "AsyncTask completed: " + msg);
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				Toast.makeText(getApplicationContext(),
						"Registered with GCM Server." + msg, Toast.LENGTH_LONG)
						.show();
			}
		}.execute(null, null, null);
	}

	private void storeRegistrationId(Context context, String regId) {
		 prefs = getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(REG_ID, regId);
		editor.putInt(APP_VERSION, appVersion);
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chats, menu);
		menu.add(group1Id, homeId, homeId, "Credits!!");
		return true;
		}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (item.getItemId()) {

        case R.id.action_settings:
            //openSettings();
            return true;
case 1:
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

	        builder.setTitle("**Welcome**");
	        builder.setMessage("Developed by:-Twinkle Mehta   !");

	        builder.setPositiveButton("Daone!!", new DialogInterface.OnClickListener() {

	            public void onClick(DialogInterface dialog, int which) {
	                // Do nothing but close the dialog
	          
	                dialog.dismiss();
	              
	            }

	        });

	        builder.setNegativeButton("No other option bitch", new DialogInterface.OnClickListener() {

	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	             
	                dialog.dismiss();
	               
	            }
	        });

	        AlertDialog alert = builder.create();
	        alert.show();
			
		
		

    

default:
    return super.onOptionsItemSelected(item);
		}
		//return super.onOptionsItemSelected(item);
	}
	
}
