package com.indyzalab.quest.baseadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.indyzalab.quest.R;

import java.util.ArrayList;



public class ListImageAdapter extends ArrayAdapter<EventItem> {

    Context context;
    int layoutResourceId;
    RecordHolder holder;
    ArrayList<EventItem> data = new ArrayList<EventItem>();
    private static LayoutInflater inflater = null;


    public ListImageAdapter(Context context, int layoutResourceId, ArrayList<EventItem> data) {
    	
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
			holder.txtDescription3 = (TextView) row.findViewById(R.id.textDescList3);
    		holder.imageItem = (ImageView) row.findViewById(R.id.imgList1);
    		holder.stateView = row.findViewById(R.id.state_open);
    		
    		row.setTag(holder);
    		
    	} else {
    		
    		holder = (RecordHolder) row.getTag();
    		
    	}
    	
    	final EventItem item = data.get(position);
    	holder.txtTitle.setText(item.getTitle());
//    	Date date = DateHelper.timestampToDate("" + item.getTimestamp());
//    	String fDate = new SimpleDateFormat("dd MMM yyyy, kk:mm").format(date);
//    	String description = fDate+" " + item.getCourseTitle().toUpperCase()+"("+item.getCourseNo()+")";
    	holder.txtDescription.setText(item.getDescription());
		if(item.isPrivate()){
			holder.txtDescription2.setText("");
			holder.txtDescription3.setText("Private");
		}else if(item.isPublic()){
			holder.txtDescription2.setText(item.getNumber_attend()+"");
			holder.txtDescription3.setText("Public");
		}
    	holder.imageItem.setImageBitmap(item.getCategory_icon());

    		holder.stateView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));


    	//set Type
    	Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/JS_Noklae.ttf");
		holder.txtTitle.setTypeface(tf);
		holder.txtTitle.setTextColor(this.getContext().getResources().getColor(R.color.white));
		holder.txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		holder.txtDescription.setTypeface(tf);
		holder.txtDescription.setTextColor(this.getContext().getResources().getColor(R.color.white));
		holder.txtDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		holder.txtDescription2.setTypeface(tf);
		holder.txtDescription2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		holder.txtDescription3.setTypeface(tf);
		holder.txtDescription3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
    	return row;
    	
    }
    
    
    static class RecordHolder {
    	
    	TextView txtTitle;
    	TextView txtDescription;
		TextView txtDescription2;
		TextView txtDescription3;
    	ImageView imageItem;

    	
    	View stateView;
    }
    
}