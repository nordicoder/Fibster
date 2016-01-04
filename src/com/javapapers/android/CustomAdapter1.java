package com.javapapers.android;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter1 extends BaseAdapter {

	 Context context;
	 List<Rowitem1> rowItem;

	 CustomAdapter1(Context context, List<Rowitem1> rowItem) {
	  this.context = context;
	  this.rowItem = rowItem;
	 }


	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
		  LayoutInflater mInflater = (LayoutInflater) context
				    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				  if (convertView == null) {
				   convertView = mInflater.inflate(R.layout.list_item1, null);
				  }

	        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
	        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

	        Rowitem1 row_pos = rowItem.get(position);
	        // setting the image resource and title
	        imgIcon.setImageResource(row_pos.getIcon());
	        txtTitle.setText(row_pos.getTitle());

	        return convertView;
	 }

	 @Override
	 public int getCount() {
	  return rowItem.size();
	 }

	 @Override
	 public Object getItem(int position) {
	  return rowItem.get(position);
	 }

	 @Override
	 public long getItemId(int position) {
	  return rowItem.indexOf(getItem(position));
	 }

	}