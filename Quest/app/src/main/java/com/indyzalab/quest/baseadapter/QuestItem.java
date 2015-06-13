package com.indyzalab.quest.baseadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Log;

import com.indyzalab.quest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;


public class QuestItem extends Item {

	/** Tue, 19 Jan 2038 03:14:07 GMT
	 * Will break Timestamp
	 */
	private final String SITE_URL = Constants.CV_URL;
	private String courseTitle;
	private String type;
	private String title;



	private String description;
	private long	timestamp;
	private String itemUrl;
	private boolean	isClicked;
	private Context context;
	public QuestItem() {
		this("Undefined", "No Title", 0, "");
	}

	public QuestItem(String type, String title, String description, Bitmap image){
		this(type,title,0,"");
		setImage(image);
		setDescription(description);
		setIsClicked(true);
	}
	public QuestItem(String type, String title, long timestamp, String itemUrl) {

		super();
		setType(type);
		setTitle(title);
		setTimestamp(timestamp);
		setItemUrl(itemUrl);
		setIsClicked(false);

	}

	public QuestItem(String courseNo, String year, String semester, String imgUrl,
					 String type, String title, long timestamp, String itemUrl) {

		super(courseNo, year, semester, imgUrl);
		setType(type);
		setTitle(title);
		setTimestamp(timestamp);
		setItemUrl(itemUrl);
		setIsClicked(false);

	}

	public QuestItem(String courseNo, String year, String semester, String imgUrl,
					 String type, String title, long timestamp, String itemUrl, boolean isClicked) {

		super(courseNo, year, semester, imgUrl);
		setType(type);
		setTitle(title);
		setTimestamp(timestamp);
		setItemUrl(itemUrl);
		setIsClicked(isClicked);

	}


	/**/
	public QuestItem(JSONObject jObject){
		this(jObject, false, null);

	}

	public QuestItem(JSONObject jObject, boolean isClicked, Context context){

		this();
		this.context = context;
		try {
            //Convert from formatted html to proper String Ex. &amp; => & \/ => /
            //Default yearSemester 2013\/2 -> 2013/2
            jObject = new JSONObject(Html.fromHtml(jObject.toString()).toString());

            Log.i("NotificationItem", "YearSem: " + jObject.getString("field_semester_year_value"));
			String yearSemester = jObject.getString("field_semester_year_value");
			Scanner sc = new Scanner(yearSemester);
			sc.useDelimiter("/");

			setYear(sc.next());
			setSemester(sc.next());

			setType(jObject.getString("type"));
			setTitle(jObject.getString("title"));
			setCourseNo(jObject.getString("field_course_number_value"));
			String timestamp = jObject.getString("changed");
			if(!timestamp.equals("null")){
				setTimestamp(Integer.parseInt(timestamp));
			}else{
				setTimestamp(-1);
			}

			setImgUrl(SITE_URL+jObject.getString("icon_fullpath"));
			setItemUrl(SITE_URL+jObject.getString("item_url"));
			setIsClicked(isClicked);
			Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_btn);
			setImage(image);


		} catch (JSONException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	public static QuestItem[] CreateNotificationArray(JSONArray jsonArray){
		
		QuestItem[] itemArray = new QuestItem[jsonArray.length()];
		
		try {
			
			for (int i=0; i<jsonArray.length(); i++){	 
				itemArray[i] = new QuestItem(jsonArray.getJSONObject(i));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemArray;	
	}
	
	public static QuestItem[] CreateNotificationArray(JSONArray jsonArray,boolean isClicked,Context context){
		
		QuestItem[] itemArray = new QuestItem[jsonArray.length()];
		
		try {
			
			for (int i=0; i<jsonArray.length(); i++){	 
				itemArray[i] = new QuestItem(jsonArray.getJSONObject(i),isClicked,context);
                Log.i("Time: ", "" + itemArray[i].getTimestamp());
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemArray;	
	}
	
	
	public void setInfo(JSONObject jObject) {
		
		try {
			
			setCourseNo(jObject.getString("course_no"));
			setYear(jObject.getString("year"));
			setSemester(jObject.getString("semester"));
			setCourseTitle(jObject.getString("title"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Set final Link

		
	}
	
	
	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public boolean isClicked() {
		return isClicked;
	}
	
	public void setIsClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}
	
	public String getItemUrl() {
		return itemUrl;
	}
	
	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public String toString(){
		String s = "Course Title: "+getCourseTitle()+" Course No: "+getCourseNo();
		return s;
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}