package com.javapapers.android;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.v7.app.ActionBarActivity;

public class Chats extends Activity implements TextView.OnEditorActionListener {

	ArrayAdapter<String>  lla;
	 ListView llv;
	 RelativeLayout rl;
	 List<Contact> cts;
	 DatabaseHandler db5;
	 List<String> tome;
	 List<String> fromme;
	 List<String> groups;
	 File fold,fold1;
	 SharedPreferences prefs;
	 String mis;
	 String gid;
		private int group1Id = 1;
		int homeId = Menu.FIRST;
	 CustomAdapter adapter;
	 EditText group;
	 ImageView iv;
	 AsyncTask<Void, Void, String> shareRegidTask;
		ShareExternalServer appUtil;
		//SharedPreferences sharedPref;
		
		Comms cmms;
		



	 String pic;
	
	 
	 List<RowItem> rowItems;
	 int width1;
	 private MenuItem myActionMenuItem;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chats);
		
		cmms=new Comms();
	   	 appUtil = new ShareExternalServer();
	  
		 fold=new File("/storage/sdcard0/Fibster data/Files");
		 fold.mkdir();

		 if(fold.exists())
		 {
			 Log.d("hush","hush");
		 }
		 else
		 {
			 Log.d("not","hush");
		 fold.setWritable(true);
		fold.setReadable(true);
		fold.setExecutable(true);
		 }
////	fold1=new File(Environment.getExternalStorageDirectory().getPath()+"/Fibsters");
//	 fold1.mkdir();
//
//	if(fold1.exists())
//	{
//		 Log.d("hush1","hush1");
//
//	}
//	else
//	{
//		 Log.d("not1","hush1");
//
//	Log.d("dfdf",Environment.getExternalStorageDirectory().toString());
//	 fold1.setWritable(true);
//	fold1.setReadable(true);
//	fold1.setExecutable(true);
//	}
	android.app.ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
		   //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	
		
		rl = (RelativeLayout) findViewById(R.id.rl);
		   prefs = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		   mis=prefs.getString("MIS", "");
		   rowItems = new ArrayList<RowItem>();
		   db5=new DatabaseHandler(this);
		   
		   if(db5.getContactsCount() == 0)
		   {
			   rl.setBackgroundResource(R.drawable.trial);
		   }
		   else
		   {
			   rl.setBackgroundResource(R.drawable.backg);
		   }
		tome=new ArrayList <String>();
		fromme=new ArrayList <String>();
		groups=new ArrayList <String>();

		//lla= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);  
		 adapter = new CustomAdapter(this, rowItems);
        llv = (ListView)findViewById(R.id.listView1);
         cts = db5.getAllContacts(); 
         llv.setAdapter(adapter);
    	
        for (Contact cn : cts)
 	   {
        	
        	Log.d("dfd",cn.getFROM()+" "+cn.getTOO());
        
        	
        	
        	
        	if(cn.getTOO().contains("gggg"))
        	{
        		int j=1;
        		
        		for(int i=0;i<groups.size();i++)
        		{
        			j=1;
        			if(cn.getTOO().contains(groups.get(i)))
        			{
        				j=0;
        				break;
        				
        				
        			}
        			
        			
        		}
        		
        		if(j==1)
        		{
        			
        			Log.d("aa","1");
        			groups.add(cn.getTOO());
        			RowItem ri=new RowItem(cn.getTOO(),R.drawable.group , "Group");
        			
        		rowItems.add(ri);
        			adapter.notifyDataSetChanged();
        			j=0;
        		}
        	}
        	
        	
        	if(cn.getFROM().contains(mis) && cn.getTOO().contains(mis))
        	{}
        	else
        	{
        	
        	if(cn.getTOO().contains(mis))
        	{
        		int j=1;
        		
        		for( int i=0;i<tome.size();i++)
        		{
        			j=1;
        			if((cn.getFROM().contains(tome.get(i))))
        			{
        				j=0;
        				
        				//Log.d(cn.getFROM(),fromme.get(i));
        				break;
        		
        				
        				
        			}
        			
        		
        			
        			
        		}
        		
        		for( int i=0;i<fromme.size();i++)
        		{
        			j=1;
        			if((cn.getFROM().contains(fromme.get(i))))
        			{
        				j=0;
        				
        				//Log.d(cn.getFROM(),tome.get(i));
        				break;
        		
        				
        				
        			}
        			if((cn.getFROM().contains(mis+"mmmm"))&&cn.getTOO().contains(mis+"mmmm"))
        			{
        				j=0;
        				break;
        				
        			}
        		}
        		
        		if(j==1)
        		{
        			Log.d("aa","2");
        			
        			tome.add(cn.getFROM());
        			//lla.add(cn.getFROM());
        			Log.d("asd","adeded bjbhbhh"+" "+cn.getFROM());
        			RowItem ri=new RowItem(cn.getFROM(),R.drawable.person , "Friend");
            		rowItems.add(ri);
            			adapter.notifyDataSetChanged();
        			//lla.notifyDataSetChanged();
        			//tome.notify();
        			j=0;
        		}
        		
        		
        		
        		}
        	
        	
        	
        	if(cn.getFROM().contains(mis) && cn.getTOO().contains("mmmm"))
        	{
        		int j=1;
        		
        		for(int i=0;i<tome.size();i++)
        		{
        			j=1;
        			if(cn.getTOO().contains(tome.get(i)))
        			{
        				j=0;
        				break;
        				
        				
        			}
        			
        			
        		}
        		
        		for(int i=0;i<fromme.size();i++)
        		{
        			j=1;
        			if(cn.getTOO().contains(fromme.get(i)))
        			{
        				j=0;
        				break;
        				
        				
        			}
        			
        			
        		}
        		if((cn.getFROM().contains(mis+"mmmm"))&&cn.getTOO().contains(mis+"mmmm"))
    			{
    				j=0;
    				break;
    				
    			}
        		
        		if(j==1)
        		{
        			Log.d("aa","3");
        			fromme.add(cn.getTOO());
        			Log.d("asd","adeded bjbhbhh"+" "+cn.getTOO());
        			RowItem ri=new RowItem(cn.getTOO(),R.drawable.person , "Friend");
            		rowItems.add(ri);
            			adapter.notifyDataSetChanged();
        			
        			
        			//lla.add(cn.getTOO());
        			//lla.notifyDataSetChanged();
        			j=0;
        		}
        		
        		
        		
        		
        		
        		
        		
        	}
        	
        	
        	}
        	
        	

        	
    
 		
 	  }
        
        
        
        llv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
          public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, final int paramAnonymousInt, long paramAnonymousLong)
          {
            Toast.makeText(Chats.this.getApplicationContext(), "good", 1).show();
           
                RowItem ri = rowItems.get(paramAnonymousInt);
                String str=ri.getMember_name();
                Log.d("hmm", str + "ff");
            	Intent i=new Intent(getApplicationContext(), ChatBubbleActivity.class);
            	Bundle b=new Bundle();
        		b.putString("Label", str);
        		i.putExtras(b);
        		startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                
           
          }
        });

      }
        
        
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chats, menu);
		menu.add(group1Id, homeId, homeId, "Credits!!");

		 myActionMenuItem = menu.findItem(R.id.group);
		    View actionView = myActionMenuItem.getActionView();
		    
		    if(actionView != null) {
		        group = (EditText) actionView.findViewById(R.id.myActionEditText);
		        iv=(ImageView)actionView.findViewById(R.id.imageView1);
		        iv.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), group.getText().toString(), Toast.LENGTH_SHORT).show();
						if(group.getText().toString().contains("99999"))
						{
							 //pr = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
							   Editor e=prefs.edit();
							   e.clear();	
							   e.commit();
						}
						else
						{
						      final String abcstr = prefs.getString("regId", "")+"^^^"+group.getText().toString().toUpperCase()+"gggg"+"^^^"+prefs.getString("NAME","")+"^^^"+prefs.getString("MIS", "").toString().toUpperCase()+"mmmm";

						      shareRegidTask = new AsyncTask<Void, Void, String>() {
					    			
					    			@Override
					    			protected String doInBackground(Void... params) {
					    				Log.d("dd","background stuff");

					    				String result = appUtil.shareRegIdWithAppServer(getApplicationContext(), abcstr);
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
						      
						      
							//String respond=cmms.commun(abcstr);
							//Toast.makeText(getApplicationContext(), respond, Toast.LENGTH_SHORT).show();
						}
						
					}
				});
		   
		        if(group != null) {
		          // We set a listener that will be called when the return/enter key is pressed
		          group.setOnEditorActionListener(this);
		        }   
		      }
		 // For support of API level 14 and below, we use MenuItemCompat
		   // MenuItemCompat.setOnActionExpandListener(myActionMenuItem, new OnActionExpandListener() {
		    MenuItemCompat.setOnActionExpandListener(myActionMenuItem, new MenuItemCompat.OnActionExpandListener() {
				
		    	 @Override
			      public boolean onMenuItemActionCollapse(MenuItem item) {
			        // Do something when collapsed
			    	  Log.d("asdasd","asdasd");
			        return true;  // Return true to collapse action view
			      }
			 
			      @Override
			      public boolean onMenuItemActionExpand(MenuItem item) {
			        // Do something when expanded
			        if(group != null) {
			          group.setText("");
			        }
			    
		   
		        
		      
		 
		        return true;  // Return true to expand action view
		      }
		    });
		    
		
	
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (item.getItemId()) {
        case R.id.add:
            Intent intent100 = new Intent(Chats.this,Send.class);
            startActivity(intent100);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            return true;
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
	
	@Override
	  public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
	    if(keyEvent != null) {
	      // When the return key is pressed, we get the text the user entered, display it and collapse the view
	        Log.d("out","here");

	      if(keyEvent.getAction() == KeyEvent.ACTION_DOWN || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
	        CharSequence textInput = textView.getText();
	        Log.d("in","here");
	        // Do something useful with the text
	        Toast.makeText(this, textInput, Toast.LENGTH_SHORT).show();
	        MenuItemCompat.collapseActionView(myActionMenuItem);
	      }
	    }
	    return false;
	  }
	
	
	
	
	
}
