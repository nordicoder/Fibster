package com.javapapers.android;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class ChatBubbleActivity extends Activity implements OnEditorActionListener {
    private static final String TAG = "ChatActivity";
    WindowManager wm;

    
    String[] menutitles;
    TypedArray menuIcons;

    // nav drawer title
   
    private DrawerLayout mDrawerLayout;
     ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private List<Rowitem1> rowItems;
    private CustomAdapter1 adapter;
    CountDownTimer cdt;
    
    int width1;
  ChatArrayAdapter chatArrayAdapter;
 ListView listView;
    private EditText chatText;
    private ImageView buttonSend;
    AsyncTask<Void, Void, String> shareRegidTask;
	ShareExternalServer appUtil;
    SharedPreferences prefs;
    Chats ch;
    String miss,t,recipent,url123;
    String downloadCompleteIntentName;
    String url="";
    DatabaseHandler db6;
//    private int group1Id = 1;
//    int homeId = 1;
//    int homeId2 = 2;
//    int homeId3 = 3;
	 private MenuItem myActionMenuItem;

    String label="";
    
    HttpURLConnection connection = null;
	DataOutputStream outputStream = null;
	DataInputStream inputStream = null;
	
	protected static final int FILE_CHOOSER = 2;

	
	String pathToOurFile=null ;
	String urlServer = "http://omkya.besaba.com/gcm2.php";
	String lineEnd = "\r\n";
	String twoHyphens = "--";
	String boundary =  "*****";
	 
	int bytesRead, bytesAvailable, bufferSize;
	byte[] buffer;
	int maxBufferSize = 1*2048*1024;
	 EditText member;
	 ImageView iv;
	
	
	
	int i=0;
    
    
    
	 List<Contact> cts;
	 
	 Context ct1,ct2;
	 int ridd,len;
	 
	 
	 private IntentFilter downloadCompleteIntentFilter;
     private BroadcastReceiver downloadCompleteReceiver;
     
     DownloadManager downloadmanager;
     
     DownloadManager.Request request;
     String arr[];
	 


    Intent intent;
    private boolean side,b = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        label=i.getExtras().getString("Label");
        Log.d("cba",label);
        	//arr=label.split(" ");
        //recipent=arr[arr.length-1];
        //len=arr.length;
        
        setContentView(R.layout.activity_chat);
		   //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        
		android.app.ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
		
        
        if(label.contains("mmmm"))
        {
        	bar.hide();
        	 String []title1=label.split("mmmm");
             setTitle(title1[0]);
        }
        else
        {
        	 String []title1=label.split("gggg");
             setTitle(title1[0]);
        }
    
        menutitles = getResources().getStringArray(R.array.titles);
        menuIcons = getResources().obtainTypedArray(R.array.icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.slider_list);

        rowItems = new ArrayList<Rowitem1>();

        for (int j = 0; j < menutitles.length; j++) {
         Rowitem1 items = new Rowitem1(menutitles[j], menuIcons.getResourceId(j, -1));
         rowItems.add(items);
        }

        menuIcons.recycle();

        adapter = new CustomAdapter1(getApplicationContext(), rowItems);

        mDrawerList.setAdapter(adapter);
        
       
        int width = getResources().getDisplayMetrics().widthPixels/2;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mDrawerList.getLayoutParams();
        params.width = width;
        mDrawerList.setLayoutParams(params);
        
        class SlideitemListener implements ListView.OnItemClickListener {
            @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                 {
            	   switch (position) {
                   case 0:Toast.makeText(getApplicationContext(), "Upload", Toast.LENGTH_LONG).show();
                   Intent intent = new Intent(getApplicationContext(), FileChooser.class);
       			ArrayList<String> extensions = new ArrayList<String>();
       			extensions.add(".pdf");
       			extensions.add(".txt");
       			extensions.add(".docx");
       			extensions.add(".jpeg");
       			extensions.add(".png");
       			extensions.add(".jpg");
       			extensions.add(".zip");
       			intent.putStringArrayListExtra("filterFileExtension", extensions);
       			startActivityForResult(intent, FILE_CHOOSER);
       			
       			attach();
                                break;
                   case 1:
                	   Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_LONG).show();

           			finish();
           		
           			
           			startActivity(getIntent());
           			break;
                                                
                   case 2:
                	   Toast.makeText(getApplicationContext(), "View Downloads", Toast.LENGTH_LONG).show();

         		      Intent localIntent = new Intent();
         		      localIntent.setAction("android.intent.action.VIEW_DOWNLOADS");
         		      startActivity(localIntent);
                               break;
                  default:
                             break;
        }
                 }

           }
        
        
        mDrawerList.setOnItemClickListener(new SlideitemListener());

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
         width1= wm.getDefaultDisplay().getWidth();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
          R.drawable.ic_drawer, R.string.app_name,R.string.app_name) {
             public void onDrawerClosed(View view) {
            	  ViewGroup.LayoutParams params = listView.getLayoutParams();
            	    params.width = width1;
            	    listView.setLayoutParams(params);
            	    listView.requestLayout();
               invalidateOptionsMenu();
               //mDrawerLayout.setDrawerLockMode(BIND_NOT_FOREGROUND);
              
             }

              public void onDrawerOpened(View drawerView) {
            	  ViewGroup.LayoutParams params = listView.getLayoutParams();
            	    params.width = 0;
            	    listView.setLayoutParams(params);
            	    listView.requestLayout();
                    invalidateOptionsMenu();
                  
               }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        
        
       
        
        
        
        

        
   	 appUtil = new ShareExternalServer();
ct2=getApplicationContext();
        
       prefs = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		   miss=prefs.getString("MIS", "");
		   ch=new Chats();
        
		   Editor edit = prefs.edit();
			edit.putString("label", label);
			edit.commit();
		   
        db6=new DatabaseHandler(this);
        cts=db6.getAllContacts();
        buttonSend = (ImageView) findViewById(R.id.send);
        
        
        downloadmanager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
      

        listView = (ListView) findViewById(R.id.listView1);
        ct1=getApplicationContext();
        ridd= R.layout.activity_chat_singlemessage;
        
      
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.activity_chat_singlemessage);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EditText) findViewById(R.id.chatText);
    	chatText.setText(prefs.getString("edittext", ""));

       /* chatText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });*/
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	
		        

            	
                sendChatMessage(false,chatText.getText().toString());
                
                if(label.contains("gggg"))
                {
                	
               	 t=chatText.getText().toString()+"\n"+java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime())+"<<<"+url+"<<<"+prefs.getString("NAME", "")+" "+prefs.getString("MIS", "")+"mmmm"+"<<<"+label+"***a";

                
                }
                else if(label.contains("mmmm"))
                {
                	t=chatText.getText().toString()+"\n"+java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime())+"<<<"+url+"<<<"+prefs.getString("NAME", "")+" "+prefs.getString("MIS", "")+"mmmm"+"<<<"+label+"|||a";
                	 new DatabaseHandler(ct2).addContact(new Contact(chatText.getText().toString(),url,prefs.getString("NAME", "")+" "+prefs.getString("MIS", "")+"mmmm",label));
                	
                }
        		
        		shareRegidTask = new AsyncTask<Void, Void, String>() {
        			
        			@Override
        			protected String doInBackground(Void... params) {
        				Log.d("dd","background stuff");

        				String result = appUtil.shareRegIdWithAppServer(getApplicationContext(), t);
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
                //sendChatMessage(false,chatText.getText().toString()+"\n"+url);

        		/*if(len>1)
        		{
        		new DatabaseHandler(ct2).addContact(new Contact(chatText.getText().toString(),url,prefs.getString("NAME", "")+" "+prefs.getString("MIS", ""),label.toUpperCase()));
        		}
        		*/
        		/*else
        		{
        			new DatabaseHandler(ct2).addContact(new Contact(chatText.getText().toString(),url,prefs.getString("NAME", "")+" "+prefs.getString("MIS", ""),arr[0].toUpperCase()));
        			
        		}
                */
                
                
            }
        });
       

       
        
        
        
        
        
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
        
        
        
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, final int paramAnonymousInt, long paramAnonymousLong)
            {
              Toast.makeText(ChatBubbleActivity.this.getApplicationContext(), "good", Toast.LENGTH_SHORT).show();
             
                  String str = chatArrayAdapter.getItem(paramAnonymousInt).message.toString();
                  Log.d("hmm", str + "ff");
                  
                  
                 // String url123 = 
                  String []siplt=str.split("\n");
                  url123=siplt[siplt.length-1];
                  Toast.makeText(ChatBubbleActivity.this.getApplicationContext(), url123, Toast.LENGTH_SHORT).show();

                  if(url123.contains("http://"))
                		  {
                  
                		   request = new DownloadManager.Request(Uri.parse(url123));
                   downloadmanager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                   
                   
                   String fileName = url123.substring( url123.lastIndexOf('/')+1, url123.length() );
                File ff=new File("/storage/sdcard0/Fibster data/Files/" +fileName );
                if(ff.exists())
                {
                	if(fileName.contains(".txt"))
                	{
                		
                		Intent i = new Intent();
                		i.setAction(android.content.Intent.ACTION_VIEW);
                		i.setDataAndType(Uri.fromFile(ff), "text/plain");
                		startActivity(i);
                		
                	}
                	
                }
                else
                {
                   
                		request.setDestinationUri(Uri.fromFile(new File("/storage/sdcard0/Fibster data/Files/" +fileName )));
                   
                 

                  long downloadID = downloadmanager.enqueue(request);
                }
                		  }
                  else
                  {
                	  
                	  
                  }
                  
             
            }
          });
        
        
        
        
        initialize();
        
        
        gooo();
        
      /*  Thread thread = new Thread()
        {
            @Override
            public void run() {
                try {
                    while(true) {
                        sleep(10000);
                    	 
                  	  //finish();
                  	  //startActivity(getIntent());
                  	gooo();
        
                    }
                    
                }
                
            
            catch(Exception e)
            {}}};
                    thread.start();*/
        MyTimerTask yourTask = new MyTimerTask();
        Timer t = new Timer();

        t.scheduleAtFixedRate(yourTask, 0, 2500);  // 10 sec interval      
    

    }
    class MyTimerTask extends TimerTask {
        public void run() {
            // do whatever you want in here
        	//listView.notify();
        	
        	
        	runOnUiThread(new Runnable() {
        	    public void run() {
        	    	String refresh=prefs.getString("label", "");
        	    	if(refresh.contains("1"))
        	    	{
        	    	gooo();
        	    	
        	    	chatArrayAdapter.notifyDataSetChanged();
        	    	   Editor edit = prefs.edit();
       				edit.putString("label", "0");
       				edit.commit();
        	    	}
        	    	
        	    	}
        	});
        }
  }
    public boolean onCreateOptionsMenu(Menu menu)
    {
  	  //getMenuInflater().inflate(R.menu.messages, menu);
  	//	menu.add(group1Id, homeId, homeId, "View downlaods!!");
  		//menu.add(group1Id, homeId2, homeId2, "Upload!!");
  		//menu.add(group1Id, homeId3, homeId3, "Refresh!!");
     
    	
    	
    	getMenuInflater().inflate(R.menu.bubble, menu);
		
		 myActionMenuItem = menu.findItem(R.id.member);
		    View actionView = myActionMenuItem.getActionView();
		    
		    if(actionView != null) {
		        member = (EditText) actionView.findViewById(R.id.myActionEditText);
		        iv=(ImageView)actionView.findViewById(R.id.imageView1);
		        iv.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), member.getText().toString(), Toast.LENGTH_SHORT).show();
						
						{
						      final String abcstr = member.getText().toString()+"mmmm!!!!"+label;
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
					    				
					    				Log.d("ddd",result);
					    			}

					    		};
					    		shareRegidTask.execute(null, null, null);
						      
						      
							//String respond=cmms.commun(abcstr);
							//Toast.makeText(getApplicationContext(), respond, Toast.LENGTH_SHORT).show();
						}
						
					}
				});
		   
		        if(member != null) {
		          // We set a listener that will be called when the return/enter key is pressed
		          member.setOnEditorActionListener(this);
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
			        if(member != null) {
			          member.setText("");
			        }
			    
		   
		        
		      
		 
		        return true;  // Return true to expand action view
		      }
		    });
    	
    	
    	
    	
      return true;
    }
    
    
    
    public void gooo()
    {
    	
   	 List<Contact> cts;
 cts=db6.getAllContacts();
        for(Contact ct : cts)
        {
        	Log.d("contents",ct.getINF());
        	
        	if(ct.getTOO().contains("gggg") && ct.getTOO().contains(label))
        	{
            	Log.d("qwe SENT TOO",ct.getTOO()+" LABEL "+label+" FROM "+ct.getFROM()+" MIS "+miss+" "+ch.mis);

        		if(ct.getFROM().contains(miss))
        		{
        			sendChatMessage(false,ct.getINF()+"\n"+ct.getURL());
        			Log.d("treue","treue");
        			
        			
        			
        		}
        		else if(!ct.getFROM().contains(miss))//sent by other person to a group or to me in a chat with that person
        		{
        			sendChatMessage(true,ct.getFROM()+"\n"+ct.getINF()+"\n"+ct.getURL());
        		}
        		
        		
        		
        		
        		
        		
        		
        	}
        	else if((ct.getTOO().contains(label)&& (ct.getFROM().contains(miss)) || (ct.getFROM().contains(label)&& ct.getTOO().contains(miss))))
        	{
        		if(ct.getFROM().contains(miss))
        		{
        			sendChatMessage(false,ct.getINF()+"\n"+ct.getURL());
        			Log.d("treue","treue");
        			
        			
        			
        		}
        		else if(ct.getFROM().contains(label))//sent by other person to a group or to me in a chat with that person
        		{
        			sendChatMessage(true,ct.getFROM()+"\n"+ct.getINF()+"\n"+ct.getURL());
        		}
        		
        		
        		
        	}
        	
        	/*else if(ct.getTOO().equals(label))
        	{
        		
        			sendChatMessage(true,ct.getINF()+"\n"+ct.getURL());
        		
        		
        		
        		
        		
        	}*/
        	
        	
        	
        	
        }
    	
    	
    	
    	
    	
    }
    

    public boolean sendChatMessage(Boolean b,String msg){
        chatArrayAdapter.add(new ChatMessage(b, msg));
        chatArrayAdapter.notifyDataSetChanged();
       // chatText.setText("");
        side = b;
        return true;
    }
public void attach()
{
	
	url=prefs.getString("url", "No File Uploaded");
	Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG);
	if(!url.contains("No File Uploaded"))
	{
		url ="!@#"+"\n"+url;
		if(label.contains("gggg"))
				{
          	 t=chatText.getText().toString()+"\n"+java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime())+"<<<"+url+"<<<"+prefs.getString("NAME", "")+" "+prefs.getString("MIS", "")+"mmmm"+"<<<"+label+"***a";

			
			shareRegidTask = new AsyncTask<Void, Void, String>() {
    			
    			@Override
    			protected String doInBackground(Void... params) {
    				Log.d("dd","background stuff");

    				String result = appUtil.shareRegIdWithAppServer(getApplicationContext(), t);
    				return result;
    			}

    			@Override
    			protected void onPostExecute(String result) {
    				shareRegidTask = null;
    				
    			}

    		};
    		shareRegidTask.execute(null, null, null);
				}
		
		else
		{
			
			
			
			
			
			t=chatText.getText().toString()+"\n"+java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime())+"<<<"+url+"<<<"+prefs.getString("NAME", "")+" "+prefs.getString("MIS", "")+"mmmm"+"<<<"+label+"|||a";
       	 new DatabaseHandler(ct2).addContact(new Contact(chatText.getText().toString(),url,prefs.getString("NAME", "")+" "+prefs.getString("MIS", "")+"mmmm",label));
       	
			
			
			
shareRegidTask = new AsyncTask<Void, Void, String>() {
	
    			
    			@Override
    			protected String doInBackground(Void... params) {
    				Log.d("dd","background stuff");

    				String result = appUtil.shareRegIdWithAppServer(getApplicationContext(), t);
    				return result;
    			}

    			@Override
    			protected void onPostExecute(String result) {
    				shareRegidTask = null;
    			
    			}

    		};
    		shareRegidTask.execute(null, null, null);
    		
    		
    		
    		
    		
    		sendChatMessage(b, url);
			
			
			
			
			
			
			
			
		}
		
	}
	

}

public void initialize()
{
	 downloadCompleteIntentName = DownloadManager.ACTION_DOWNLOAD_COMPLETE;
     IntentFilter downloadCompleteIntentFilter = new IntentFilter(downloadCompleteIntentName);
      downloadCompleteReceiver = new BroadcastReceiver() {
         @Override
         public void onReceive(Context context, Intent intent) {
             // TO BE FILLED
        	 Toast.makeText(getApplicationContext(), "Download finished", Toast.LENGTH_SHORT).show();
         }
     };


     // when initialize
     getApplicationContext().registerReceiver(downloadCompleteReceiver, downloadCompleteIntentFilter);
     
}

public void ct()
{
	
	cdt=new CountDownTimer(500, 100) {       
		   public void onTick(long millisUntilFinished) {          
		   //Toast.makeText(this,"seconds remaining: " + millisUntilFinished / 1000,Toast.LENGTH_LONG).show();      
			   
			   }
			   
		   
		public void onFinish() {         
		    //ed1.setText("done");
			//player.start();
			//Log.d("score",t+" wrong!");
			  //Toast.makeText(this, "game over:(", Toast.LENGTH_LONG).show();
			  listView.notify();
			  chatArrayAdapter.notifyDataSetChanged();
			 
				  //player.stop();
				 // player1.stop();
				  cdt.cancel();
				  cdt.start();
				  
			
			  
			 
			
			
		}   
		}.start(); 


}






public void onPause()
{
  unregisterReceiver(this.downloadCompleteReceiver);
  Editor e=prefs.edit();
	e.putString("edittext", chatText.getText().toString());
	e.commit();
  super.onPause();
}
public void onResume()
{
	chatText.setText(prefs.getString("edittext", ""));
    registerReceiver(this.downloadCompleteReceiver, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
  
    super.onResume();
	  
}
public void onStop()
{
	  Editor e=prefs.edit();
			e.putString("edittext", chatText.getText().toString());
			e.commit();
		  super.onDestroy();
}
public void onDestory()
{
	  unregisterReceiver(this.downloadCompleteReceiver);
	  
	  Editor e=prefs.edit();
		e.putString("edittext", chatText.getText().toString());
		e.commit();
	  super.onDestroy();
	  
	  
	 
	  
}
public void reload()
{
	finish();
	
	
	startActivity(getIntent());
	
}
/*public void newton(String a, String b, String c, String d)
{

    	//Log.d("qwe SENT TOO",ct.getTOO()+" LABEL "+label+" FROM "+ct.getFROM()+" MIS "+miss+" "+ch.mis);
    	
    	if(d.contains("gggg") && d.contains(label))
    	{
    		if(c.contains(miss))
    		{
    			sendChatMessage(false,a+"\n"+b);
    			Log.d("treue","treue");
    			
    			
    			
    		}
    		else if(!c.contains(miss))//sent by other person to a group or to me in a chat with that person
    		{
    			sendChatMessage(true,c+"\n"+a+"\n"+b);
    		}
    		
    		
    		
    		
    		
    		
    		
    	}
    	if((d.contains(label)&& (c.contains(miss)) || (c.contains(label)&& d.contains(miss))))
    	{
    		if(c.contains(miss))
    		{
    			sendChatMessage(false,a+"\n"+b);
    			Log.d("treue","treue");
    			
    			
    			
    		}
    		else if(c.contains(label))//sent by other person to a group or to me in a chat with that person
    		{
    			sendChatMessage(true,c+"\n"+a+"\n"+b);
    		}
    		
    		chatArrayAdapter.notifyDataSetChanged();
    		
    	}
    	
    	/*else if(ct.getTOO().equals(label))
    	{
    		
    			sendChatMessage(true,ct.getINF()+"\n"+ct.getURL());
    		
    		
    		
    		
    		
    	}*/
    	
    	
    	
    	
    


public boolean onOptionsItemSelected(MenuItem item)
{
	
	  if (mDrawerToggle.onOptionsItemSelected(item)) {
	      return true;
	    }
	
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

		      break;
		case 2:
			Intent intent = new Intent(this, FileChooser.class);
			ArrayList<String> extensions = new ArrayList<String>();
			extensions.add(".pdf");
			extensions.add(".txt");
			extensions.add(".docx");
			extensions.add(".jpeg");
			extensions.add(".png");
			extensions.add(".jpg");
			extensions.add(".zip");
			intent.putStringArrayListExtra("filterFileExtension", extensions);
			startActivityForResult(intent, FILE_CHOOSER);
			
			attach();
			break;
		case 3:

			
			//Intent i=new Intent(getApplicationContext(), ChatBubbleActivity.class);
		
			finish();
		
			
			startActivity(getIntent());
			break;
			
		
		
		}
		return super.onOptionsItemSelected(item);
  }
	
	public void refresh(String label,String miss,Context con)
	{
	      //SharedPreferences prefs = getApplicationContext().getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);

		 List<Contact> cts;
		 DatabaseHandler db6=new DatabaseHandler(con);
		   
	        cts=db6.getAllContacts();
			  //String miss=prefs.getString("MIS", "");

		 ChatArrayAdapter chatArrayAdapter;
		 
		 ListView listView;

	        listView = (ListView) findViewById(R.id.listView1);
	        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.activity_chat_singlemessage);
	        listView.setAdapter(chatArrayAdapter);
		 
	     
	        ChatBubbleActivity cbb=new ChatBubbleActivity();
		
		
		
		
		 for(Contact ct : cts)
	        {
	        	//Log.d("qwe SENT TOO",ct.getTOO()+" LABEL "+label+" FROM "+ct.getFROM()+" MIS "+miss+" "+ch.mis);
	        	
	        	if(ct.getTOO().contains("gggg") && ct.getTOO().contains(label))
	        	{
	        		if(ct.getFROM().contains(miss))
	        		{
	        			cbb.sendChatMessage(false,ct.getINF()+"\n"+ct.getURL());
	        			Log.d("treue","treue");
	        			
	        			
	        			
	        		}
	        		else if(!ct.getFROM().contains(miss))//sent by other person to a group or to me in a chat with that person
	        		{
	        			cbb.sendChatMessage(true,ct.getFROM()+"\n"+ct.getINF()+"\n"+ct.getURL());
	        		}
	        		
	        		
	        		
	        		
	        		
	        		
	        		
	        	}
	        	else if((ct.getTOO().contains(label)&& (ct.getFROM().contains(miss)) || (ct.getFROM().contains(label)&& ct.getTOO().contains(miss))))
	        	{
	        		if(ct.getFROM().contains(miss))
	        		{
	        			cbb.sendChatMessage(false,ct.getINF()+"\n"+ct.getURL());
	        			Log.d("treue","treue");
	        			
	        			
	        			
	        		}
	        		else if(ct.getFROM().contains(label))//sent by other person to a group or to me in a chat with that person
	        		{
	        			cbb.sendChatMessage(true,ct.getFROM()+"\n"+ct.getINF()+"\n"+ct.getURL());
	        		}
	        		
	        		
	        		
	        	}
	        	
	        	/*else if(ct.getTOO().equals(label))
	        	{
	        		
	        			sendChatMessage(true,ct.getINF()+"\n"+ct.getURL());
	        		
	        		
	        		
	        		
	        		
	        	}*/
	        	
	        	
	        	
	        	
	        }
		listView.notify();
		chatArrayAdapter.notifyDataSetChanged();
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		   if ((requestCode == FILE_CHOOSER) && (resultCode == -1)) {
			   Bundle bundle = data.getExtras();
			   
			   String fileSelected= bundle.getString("fileSelected");
		        //String fileSelected = data.getStringExtra("fileSelected");
		        Toast.makeText(this, fileSelected, Toast.LENGTH_SHORT).show();
		        pathToOurFile=fileSelected;
		        int flag=0;
		        
		        
		        String nofile="No file uploaded";
				
				if(pathToOurFile!=null)
				{
				String ufile[]=pathToOurFile.split("/");
				int l=ufile.length;
				Log.d("asd",ufile[l-1]);
				
				 nofile="http://omkya.besaba.com/"+miss+"mmmm/"+ufile[l-1];
				}
				
				prefs = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
				Editor e=prefs.edit();
				e.putString("url", nofile);
				e.commit();
				
		        
		        
		        byte[] buf = new byte[4096];
		        ByteArrayOutputStream os = new ByteArrayOutputStream();
		        Thread thread = new Thread()
		        {
		            @Override
		            public void run() {
		                try {
		                    while(true) {
		                        //sleep(1000);
		                       
		                    	
		                    	
		        		        try
		        		        {
		        		        	InputStream is;
		        		        	//System.setProperty("http.keepAlive", "false");
		        		            FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );
		        		         urlServer=urlServer+"?username="+miss+"mmmm";

		        		            URL url = new URL(urlServer);
		        		            connection = (HttpURLConnection) url.openConnection();
		        		            Log.d("ff","http url fine");
		        		         
		        		            // Allow Inputs &amp; Outputs.
		        		            connection.setDoInput(true);
		        		            connection.setDoOutput(true);
		        		            connection.setUseCaches(false);
		        		            
		        		            //connection.setConnectTimeout (30000) ; 
		        		         
		        		            // Set HTTP method to POST.
		        		            connection.setRequestMethod("POST");
		        		         
		        		            connection.setRequestProperty("Connection", "Keep-Alive");
		        		            
		        		            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
		        		            
		        		            //is=connection.getInputStream();
		        		            int ret = 0;
		        		            Log.d("ff","connections fine");

		        		            /*if(is.read(buf)==0)
		        		            {
		        		            	flag=1;
		        		            }*/
		        		            
		        		          /*  while ((ret = is.read(buf)) > 0) 
		        		            {
		        		            	os.write(buf, 0, ret);  
		        		            	Log.d("as",buf.toString());
		        		            }*/
		        		            //is.close();
		        		            
		        		         
		        		            outputStream = new DataOutputStream( connection.getOutputStream() );
		        		            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
		        		            outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + pathToOurFile +"\"" + lineEnd);
		        		            outputStream.writeBytes(lineEnd);
		        		            
		        		            Log.d("ff","outputstream fine");
		        		         
		        		            bytesAvailable = fileInputStream.available();
		        		            bufferSize = Math.min(bytesAvailable, maxBufferSize);
		        		            buffer = new byte[bufferSize];
		        		         
		        		            // Read file
		        		            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
		        		            Log.d("ff","bytes read fine");
		        		         
		        		            while (bytesRead > 0)
		        		            {
		        		                outputStream.write(buffer, 0, bufferSize);
		        		                bytesAvailable = fileInputStream.available();
		        		                bufferSize = Math.min(bytesAvailable, maxBufferSize);
		        		                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
		        		            }
		        		         
		        		            outputStream.writeBytes(lineEnd);
		        		            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
		        		            
		        		         
		        		            // Responses from the server (code and message)
		        		            Log.d("asd","uhoh");
		        		            int serverResponseCode = connection.getResponseCode();
		        		            String serverResponseMessage = connection.getResponseMessage();
		        		            Log.d("asd","hushsh");
		        		            Toast.makeText(getApplicationContext(), "hushhh", Toast.LENGTH_LONG).show();
		        		            
		        		           // Log.d("dd",/*serverResponseCode*/"--"+serverResponseMessage);
		        		         
		        		            fileInputStream.close();
		        		            outputStream.flush();
		        		            outputStream.close();
		        		            connection.disconnect();
		        		            
		        		            //flag=0;
		        		           
		        		            Log.d("ff","closed and done");
		        		            Toast.makeText(getApplicationContext(), "Upload done", Toast.LENGTH_LONG).show();
		        		        }
		        		        catch (Exception ex)
		        		        {
		        		            //Exception handling
		        		        	ex.printStackTrace();
		        		       
		        		        }
		        		        
		                    	
		                    	
		                    	
		                    	
		                    	
		                    
		                    }}catch (Exception e) {
		                    e.printStackTrace();
		                }
		            }
		        };

		        thread.start();
		        
		        
		        
		        
		   }
	



	}


	@Override
	public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
		// TODO Auto-generated method stub
		return false;
	}
}



