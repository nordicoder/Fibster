package com.javapapers.android;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Iterator;
import java.util.List;



public class Messages
  extends Activity
{

  Context con;
  DownloadManager dm;
  long enqueue;
  String ext;
  private int group1Id = 1;
  int homeId = 1;
 
  int i;
  MainActivity ma;
  BroadcastReceiver receiver;
  String[] url;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_messages);
    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
    this.con = this;
    this.url = new String[50];
    this.ext = "txt";
    this.i = 0;

	ArrayAdapter<String> localArrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);    
		        ListView localListView = (ListView)findViewById(R.id.listView1);
    Iterator localIterator = new DatabaseHandler(this).getAllContacts().iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        localListView.setAdapter(localArrayAdapter);
        this.receiver = new BroadcastReceiver()
        {
          public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
          {
            if ("android.intent.action.DOWNLOAD_COMPLETE".equals(paramAnonymousIntent.getAction()))
            {
              paramAnonymousIntent.getLongExtra("extra_download_id", 0L);
              DownloadManager.Query localQuery = new DownloadManager.Query();
              long[] arrayOfLong = new long[1];
              arrayOfLong[0] = Messages.this.enqueue;
              localQuery.setFilterById(arrayOfLong);
              
              if(Messages.this.dm.query(localQuery)!=null)
              {
              Cursor localCursor = Messages.this.dm.query(localQuery);
              
              if ((localCursor.moveToFirst()) && (8 == localCursor.getInt(localCursor.getColumnIndex("status")))) {
                localCursor.getString(localCursor.getColumnIndex("local_uri"));
              }
              }
            }
          }
        };
        registerReceiver(this.receiver, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
        localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
          public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, final int paramAnonymousInt, long paramAnonymousLong)
          {
            Toast.makeText(Messages.this.con, "good", 1).show();
            new Thread(new Runnable()
            {
              public void run()
              {
                String str = Messages.this.url[paramAnonymousInt].toString();
                Log.d("hmm", str + "ff");
                if(!str.contains("http://")||!str.isEmpty())
                {
                Messages.this.dm = ((DownloadManager)Messages.this.getSystemService("download"));
                DownloadManager.Request localRequest = new DownloadManager.Request(Uri.parse(str));
                Messages.this.enqueue = Messages.this.dm.enqueue(localRequest);
                }
                
                else
                {
                	Toast.makeText(Messages.this.con,"Invalid URL",Toast.LENGTH_LONG).show();
                }
                }
            }).start();
          }
        });
        return;
      }
      Contact localContact = (Contact)localIterator.next();
      if ((localContact.getINF() != null) && (localContact.getURL() != null))
      {
        this.url[this.i] = localContact.getURL();
        localArrayAdapter.add( "From:-  "+localContact.getFROM()+"\n"+localContact.getINF() + "\n" /*+ "URL:-      " + localContact.getURL()*/);
        localArrayAdapter.notifyDataSetChanged();
        this.i = (1 + this.i);
      }
    }
    }
  
  public boolean onCreateOptionsMenu(Menu menu)
  {
	  getMenuInflater().inflate(R.menu.messages, menu);
		menu.add(group1Id, homeId, homeId, "View downlaods!!");
   
    return true;
  }
  
  public void onDestroy()
  {
    if (this.receiver != null) {
      super.onDestroy();
    }
  }
  
  public boolean onOptionsItemSelected(MenuItem item)
  {
	  int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		switch(id)
		{
		case 1:
			
			

		      Intent localIntent = new Intent();
		      localIntent.setAction("android.intent.action.VIEW_DOWNLOADS");
		      startActivity(localIntent);

		
		
		
		}
		return super.onOptionsItemSelected(item);
    }
  
  
  public void onPause()
  {
    unregisterReceiver(this.receiver);
    super.onPause();
  }
  public void onResume()
  {
      registerReceiver(this.receiver, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
      super.onResume();
	  
  }
  public void onDestory()
  {
	  unregisterReceiver(this.receiver);
	  super.onDestroy();
	  
	  
  }
}

/* Location:           C:\Users\GHANSHYAM\Downloads\dex2jar-0.0.9.15\GCM-dex2jar.jar * Qualified Name:     com.javapapers.android.Messages * JD-Core Version:    0.7.0.1 */