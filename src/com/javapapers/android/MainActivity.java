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
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText ed1;
	TextView tv1;
	TextView tv2;
	
	ShareExternalServer appUtil;
	AsyncTask<Void, Void, String> shareRegidTask;

	protected static final int FILE_CHOOSER = 2;

	String regId;
	SharedPreferences sharedPref;
	
	HttpURLConnection connection = null;
	DataOutputStream outputStream = null;
	DataInputStream inputStream = null;
	String pathToOurFile=null ;
	String urlServer = "http://omkya.besaba.com/gcm2.php";
	String lineEnd = "\r\n";
	String twoHyphens = "--";
	String boundary =  "*****";
	 
	int bytesRead, bytesAvailable, bufferSize;
	byte[] buffer;
	int maxBufferSize = 1*2048*1024;
	
	
	
	
	int i=0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		appUtil = new ShareExternalServer();
		i=0;
		 
	

		regId = getIntent().getStringExtra("regId");
	//	Log.d("MainActivity", "regId: " + regId);
		sharedPref = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);

		 tv1=(TextView)findViewById(R.id.textView1);
		 tv2=(TextView)findViewById(R.id.textView2);
		 ed1=(EditText)findViewById(R.id.editText1);
		 tv1.setText("MIS :-"+sharedPref.getString("MIS", ""));
		 tv2.setText("NAME :-"+sharedPref.getString("NAME", ""));

		final Context context = this;
	
		/*if( i==0)
		{
			Log.d("dd","start sending");
		shareRegidTask = new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				Log.d("dd","background stuff");
				String result = appUtil.shareRegIdWithAppServer(context, regId);
				i++;
				Log.d("dd",i+"");
				return result;
			}

			@Override
			protected void onPostExecute(String result) {
				shareRegidTask = null;
				Toast.makeText(getApplicationContext(), result,
						Toast.LENGTH_LONG).show();
				i++;
				Log.d("dd",i+"");
			}

		};
		shareRegidTask.execute(null, null, null);
	}
		
	*/
	}
	

	public void send(View view)
	{
		String nofile="No file uploaded";
		Intent i=new Intent(getApplicationContext(), Send.class);
		if(pathToOurFile!=null)
		{
		String ufile[]=pathToOurFile.split("/");
		int l=ufile.length;
		Log.d("asd",ufile[l-1]);
		
		 nofile="http://omkya.besaba.com/uploads/"+ufile[l-1];
		}
		Bundle b=new Bundle();
		b.putString("url", nofile);
		i.putExtras(b);
		//i.putExtra("url", nofile);
		startActivity(i);
		
		
		
	}
	
	public void GID(View view)
	{
		if(ed1.getText().toString().isEmpty())
		{
			ed1.setText("Enter a GID");
			
		}
		
		else if(!ed1.getText().toString().equals("Enter a GID"))
		{
			
			shareRegidTask = new AsyncTask<Void, Void, String>() {
				@Override
				protected String doInBackground(Void... params) {
					Log.d("dd","background stuff");
					String result = appUtil.shareRegIdWithAppServer(getApplicationContext(), sharedPref.getString("regId", "")+">>>"+ed1.getText().toString().toUpperCase()+"gggg"+">>>"+sharedPref.getString("NAME","")+">>>"+sharedPref.getString("MIS", "").toString().toUpperCase()+"mmmm");
					i++;
					Log.d("dd",i+"");
					return result;
				}

				@Override
				protected void onPostExecute(String result) {
					shareRegidTask = null;
					Toast.makeText(getApplicationContext(), result,
							Toast.LENGTH_LONG).show();
					i++;
					Log.d("dd",i+"");
				}

			};
			shareRegidTask.execute(null, null, null);
			
			
			
			
		}
		
		
		
		
		
		
	}
public void upload(View view)
{
	Intent intent = new Intent(this, FileChooser.class);
	ArrayList<String> extensions = new ArrayList<String>();
	extensions.add(".pdf");
	extensions.add(".txt");
	extensions.add(".docx");
	extensions.add(".jpeg");
	extensions.add(".png");
	extensions.add(".jpg");
	intent.putStringArrayListExtra("filterFileExtension", extensions);
	startActivityForResult(intent, FILE_CHOOSER);

}
	public void del(View view)
	{

		
		
				
				
		
		
	    DatabaseHandler localDatabaseHandler = new DatabaseHandler(this);
	    Iterator localIterator = localDatabaseHandler.getAllContacts().iterator();
	    for (;;)
	    {
	      if (!localIterator.hasNext()) {
	        return;
	      }
	      localDatabaseHandler.deleteContact((Contact)localIterator.next());
	    }

		
	}
	public void see(View view)
	{	
		
		
		if(ed1.getText().toString().equals("99999"))
		{
			 sharedPref = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
			   Editor e=sharedPref.edit();
			   e.clear();	
			   e.commit();

		}

		String nofile="No file uploaded";
		
		if(pathToOurFile!=null)
		{
		String ufile[]=pathToOurFile.split("/");
		int l=ufile.length;
		Log.d("asd",ufile[l-1]);
		
		 nofile="http://omkya.besaba.com/uploads/"+ufile[l-1];
		}
		
		sharedPref = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		Editor e=sharedPref.edit();
		e.putString("url", nofile);
		e.commit();
		
		startActivity(new Intent(this, Chats.class));
		
		
		
		
		
	}
	
	public void backseat(View view)
	{
		finish();
		//startActivity(new Intent(this, RegisterActivity.class));

		
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
	
	

}
