package com.indyzalab.quest.baseadapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.indyzalab.quest.AndroidUtils;
import com.indyzalab.quest.R;

import java.util.ArrayList;

public class CustomWindowInfoAdapter implements InfoWindowAdapter{
	
	private final String EVENT = "Detail"; //Indicate that is an event Form="Detail:detail"
	private final String NUMBER = "Number";	//Form "Number:xxx" xxx = number of attendance(int)
	LayoutInflater inflater = null;
	private TextView textViewTitle;
	private TextView lineText;
	private TextView numText;
	ArrayList<EventItem> events;
	ArrayList<Marker> allEvents;
	public CustomWindowInfoAdapter(LayoutInflater inflater) {
		this.inflater = inflater;
	}
	
	public CustomWindowInfoAdapter(LayoutInflater inflater,ArrayList<EventItem> events) {
		this.inflater = inflater;
		this.events = events;
	}
	
	public CustomWindowInfoAdapter(LayoutInflater inflater,ArrayList<EventItem> events,ArrayList<Marker> allEvents) {
		this.inflater = inflater;
		this.events = events;
		this.allEvents = allEvents;
	}


	@SuppressLint("ResourceAsColor")
	@Override
	public View getInfoWindow(Marker marker) {
		View v = inflater.inflate(R.layout.custom_windowinfo, null);
		
		if (marker != null) {
			textViewTitle = (TextView) v.findViewById(R.id.textViewTitle);
			textViewTitle.setText(marker.getTitle());
			lineText = (TextView) v.findViewById(R.id.WindowLine);
			numText= (TextView) v.findViewById(R.id.numAttend);
			Log.i("Snippet Custom",marker.getSnippet());
			String snippet = marker.getSnippet();
			if(snippet.startsWith(EVENT)){
				for(EventItem item: events){
					if(item.getTitle().equals(marker.getTitle())){
						String[] all = snippet.split(",");
						String detail = all[0].substring(EVENT.length()+1); //Detail:deatil
						String number = all[1].substring(NUMBER.length()+1);
						lineText.setText(detail);
						numText.setText(number);
//						lineText.setTextColor(v.getResources().getColor(R.color.white));
						lineText.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
					}
				}
			}
			
			AndroidUtils.setNoklaeFont(inflater.getContext(), v);
			
		}
		return (v);
	}

	@Override
	public View getInfoContents(Marker marker) {
		return (null);
	}
	
}
