package com.indyzalab.quest.baseadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.indyzalab.quest.DateHelper;
import com.indyzalab.quest.R;

import java.util.ArrayList;
import java.util.Date;



public class ListImageAdapter extends ArrayAdapter<QuestItem> {

    Context context;
    int layoutResourceId;
    RecordHolder holder;
    ArrayList<QuestItem> data = new ArrayList<QuestItem>();
    private static LayoutInflater inflater = null;


    public ListImageAdapter(Context context, int layoutResourceId, ArrayList<QuestItem> data) {
    	
        // TODO Auto-generated constructor stub
    	super(context, layoutResourceId, data);
    	this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
//        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
    }
    
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	View row = convertView;
    	holder = null;
    	
    	if (row == null) {
    		
    		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
    		row = inflater.inflate(layoutResourceId, parent, false);
    		
    		holder = new RecordHolder();
    		holder.txtTitle = (TextView) row.findViewById(R.id.textTitleList1);
    		holder.txtDescription = (TextView) row.findViewById(R.id.textDescList1);
			holder.txtDescription2 = (TextView) row.findViewById(R.id.textDescList2);
    		holder.imageItem = (ImageView) row.findViewById(R.id.imgList1);
    		holder.stateView = row.findViewById(R.id.state_open);
    		
    		row.setTag(holder);
    		
    	} else {
    		
    		holder = (RecordHolder) row.getTag();
    		
    	}
    	
    	final QuestItem item = data.get(position);
    	holder.txtTitle.setText(item.getTitle());
    	Date date = DateHelper.timestampToDate("" + item.getTimestamp());
//    	String fDate = new SimpleDateFormat("dd MMM yyyy, kk:mm").format(date);
//    	String description = fDate+" " + item.getCourseTitle().toUpperCase()+"("+item.getCourseNo()+")";
    	holder.txtDescription.setText(item.getDescription());
		holder.txtDescription2.setText(item.getDescription());
    	holder.imageItem.setImageBitmap(item.getImage());
    	
    	if(!item.isClicked()){
    		holder.stateView.setBackgroundColor(context.getResources().getColor(R.color.CVBlue));
    	}else{
    		holder.stateView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
    	}

    	//set Type
    	Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto_Regular.ttf");
    	Typeface tf2 = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto_Condensed.ttf");
		holder.txtTitle.setTypeface(tf);
		holder.txtTitle.setTextColor(this.getContext().getResources().getColor(R.color.white));
		holder.txtDescription.setTypeface(tf2);
		holder.txtDescription.setTextColor(this.getContext().getResources().getColor(R.color.white));
    	

    	return row;
    	
    }
    
    
    static class RecordHolder {
    	
    	TextView txtTitle;
    	TextView txtDescription;
		TextView txtDescription2;
    	ImageView imageItem;

    	
    	View stateView;
    }
    
}