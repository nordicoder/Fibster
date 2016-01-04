package com.javapapers.android;

import java.util.Hashtable;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMNotificationIntentService extends IntentService {

	MediaPlayer player;
	AssetFileDescriptor afd;
	Vibrator  v;
	Hashtable ht;
    Editor edit;
    int flag1,flag2=0;

	String records="";
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	SharedPreferences prefs ;

	public GCMNotificationIntentService() {
		super("GcmIntentService");

	}

	public static final String TAG = "GCMNotificationIntentService";

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification("Deleted messages on server: "
						+ extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {

				for (int i = 0; i < 3; i++) {
					Log.i(TAG,
							"Working... " + (i + 1) + "/5 @ "
									+ SystemClock.elapsedRealtime());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}

				}
				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
				
	   			
				 v = (Vibrator) this.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
				 // Vibrate for 500 milliseconds
				 v.vibrate(500);
					prefs= getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
					 edit= prefs.edit();
				 Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				 Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
				 r.play();
				   
				
				String tp=extras.getString(Config.MESSAGE_KEY);
				String[] arrayOfString1 = tp.split("<<<");

				int l=arrayOfString1.length;
		        String str31 = arrayOfString1[0];
		        String str41="";
		        if(l==3)
		        {
		       str41 = arrayOfString1[1];
		        }
		        String str51=arrayOfString1[2];
		        String str61=arrayOfString1[3];
		      
				if(prefs.getString("record", "").equals(""))
				{
					if(str61.contains("gggg"))
					{
						String rec1=str61+"<>1";
						edit.putString("record", rec1);
						edit.commit();
					}
					else
					{
					String rec1=str51+"<>1";
					edit.putString("record", rec1);
					edit.commit();
					}
				}
				else
				{
					String putrecord=prefs.getString("record", "");
					String []hash=prefs.getString("record", "").split("<>");
					flag1=flag2=0;
					for(int i=0;i<hash.length;i++)
					{
						if(str61.contains("gggg"))
						{
							if(hash[i].equals(str61))
							{
								hash[i+1]=(Integer.parseInt(hash[i+1])+1)+"";
								flag1=1;
								Log.d("omkar","adding to groups");
								
								
							}
						
							
							
						}
						else 
						{
							if(hash[i].equals(str51))
							{
							hash[i+1]=(Integer.parseInt(hash[i+1])+1)+"";
							flag2=1;
							Log.d("omkar","adding to persons");

							
							
							}
						
						}
						
					}
					if(flag1==0)
					{
						
						putrecord.concat("<>"+str61+"0<>");
						edit.putString("record", putrecord);
						Log.d("hashgroup",putrecord);
						edit.commit();
					}
					else if(flag2==0 && flag1!=1)
					{
						putrecord.concat("<>"+str51+"0<>");
						edit.putString("record", putrecord);
						Log.d("hashperson",putrecord);

						edit.commit();

					}
					else
					{
						String finalrec="";
						for(int i=0;i<hash.length;i++)
						{
							finalrec=finalrec+hash[i];
						}
						edit.putString("record", finalrec);
						Log.d("hashstringbuild",finalrec);

						edit.commit();
						
					}
					
				}
				
				
				
				sendNotification( "From:-"+str51+"\n"+str31+"\n"+str41);
				//Toast.makeText(this,extras.getString(Config.MESSAGE_KEY) , Toast.LENGTH_LONG).show();
				String h=extras.get(Config.MESSAGE_KEY).toString();
				Log.i(TAG, "Received: " + extras.toString());
				//Toast.makeText(this, h,Toast.LENGTH_LONG).show();
				Log.i(TAG, "Received: " + extras.toString());
				if(h.contains("<<<"))
				{
				String[] arrayOfString = h.split("<<<");

		        String str3 = arrayOfString[0];
		        String str4 = arrayOfString[1];
		        String str5 = arrayOfString[2];
		        String str6=arrayOfString[3];
		        Log.d("ff", str3 + "jj" + str4+"kk"+str5+"ll"+str6);
		        new DatabaseHandler(this).addContact(new Contact(str3, str4,str5,str6));

		        ChatBubbleActivity cba=new ChatBubbleActivity();
		        prefs = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);

				edit.putString("label", "1");
				edit.commit();
		       // cba.gooo();
		      // getApplicationContext().
		        //cba.listView.notifyAll();
		        //cba.chatArrayAdapter.notifyDataSetChanged();
		       
		        //ht=new Hashtable<String, String>();
		      // cba.reload();
//		       prefs = getSharedPreferences(
//						MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
//		       Context ct=getApplicationContext();
//		       //cba.refresh(prefs.getString("label", ""),prefs.getString("MIS", ""),ct);
//		        if(prefs.getString("label", "")!="")
//			    {
//		        	Log.d("gba","here");
//		        Intent refresh = new Intent(this,ChatBubbleActivity.class);
//		       refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		       //refresh.setFlags();
//		    
//		     
//		       
//		       Bundle b=new Bundle();
//       		b.putString("Label", prefs.getString("label", ""));
//            Log.d("gba",prefs.getString("label", ""));
//
//       		refresh.putExtras(b); 
//       		
//       		cba.finish();
//       		
//		        startActivity(refresh);
//		       
//			    }
		        
		      
		    if(cba!=null)
		    {
		       // cba.refresh();
		     
		    }/* if(cba!=null)
		        {
		        cba.newton(str3, str4, str5, str6);
		        }*/
		       /* Chats chs=new Chats();
		    	if(cba!=null && chs!=null && cba.ct1!=null && cba.ridd!=0)
		    	{
		    		Log.d("qwer","HERE");
		    	ChatArrayAdapter caa=new ChatArrayAdapter(cba.ct1, cba.ridd);
		    	
		        
		        
		        if(cba.label!=null && str5.equals(cba.label) && str6.equals(chs.mis))
		        {
		        	cba.sendChatMessage(false,str3+"\n"+str4);
		        	caa.notifyDataSetChanged();
		        	
		    		Log.d("qwer","HERE more");

		        	
		        }
		        else if(cba.label!=null && str5.equals(cba.label) && !str6.equals(chs.mis))
		        {
		        	cba.sendChatMessage(false,str5+"\n"+str3+"\n"+str4);
		        	caa.notifyDataSetChanged();
		        	
		    		Log.d("qwer","HERE more moer");

		        	
		        }
		    	}*/
				}
				

				
			}
		}
			
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String msg) {
		Log.d(TAG, "Preparing to send notification...: " + msg);
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, Chats.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_NEW_TASK), 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.androlaugh)
				.setContentTitle("Notification")
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg);

		mBuilder.setAutoCancel(true);
		mBuilder.setSmallIcon(R.drawable.androlaugh);
		mBuilder.setContentTitle("Fibster");
		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		Log.d(TAG, "Notification sent successfully.");
	}
}
