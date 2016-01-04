package com.javapapers.android;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

	private TextView chatText;
	ImageView imv;
	private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
	private LinearLayout singleMessageContainer;

	@Override
	public void add(ChatMessage object) {
		chatMessageList.add(object);
		super.add(object);
	}

	public ChatArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public int getCount() {
		return this.chatMessageList.size();
	}

	public ChatMessage getItem(int index) {
		return this.chatMessageList.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.activity_chat_singlemessage, parent, false);
		}
		singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
		ChatMessage chatMessageObj = getItem(position);
		chatText = (TextView) row.findViewById(R.id.singleMessage);
		

		//imv=(ImageView)row.findViewById(R.id.imageView1);
	
		
		String findd=chatMessageObj.message;
		if(findd.contains("!@#"))
		{
			
			String utl[]=findd.split("!@#");
			String finis[]=utl[1].split("/",5);
			Log.d("fins",finis[0]+finis[1]+finis[2]+finis[3]+finis[4]);
			//imv.setVisibility(1);
			chatText.setText(finis[4]);
			//chatText.setVisibility(0);
			//imv.setImageResource(R.drawable.download);
			if(findd.contains(".txt"))
			{chatText.setBackgroundResource(R.drawable.txt123);
			}
			else if(findd.contains(".pdf"))
			{chatText.setBackgroundResource(R.drawable.pdf123);}
			else if(findd.contains(".jpg"))
			{chatText.setBackgroundResource(R.drawable.jpeg123);}
			else if(findd.contains(".jpeg"))
			{chatText.setBackgroundResource(R.drawable.jpeg123);}
			else if(findd.contains(".zip"))
			{chatText.setBackgroundResource(R.drawable.zip123);}
			singleMessageContainer.setGravity(chatMessageObj.left ? Gravity.LEFT : Gravity.RIGHT);
			
		}
		else
		{
			
			//chatText.setVisibility(1);
			//imv.setVisibility(0);
		chatText.setText(chatMessageObj.message);
		chatText.setBackgroundResource(chatMessageObj.left ? R.drawable.bubble_a : R.drawable.bubble_b);
		singleMessageContainer.setGravity(chatMessageObj.left ? Gravity.LEFT : Gravity.RIGHT);
		}
		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
	
	 
	
	

}