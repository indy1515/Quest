package com.indyzalab.quest.baseadapter;


import android.graphics.Bitmap;

import java.io.Serializable;


public class Item implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String courseNo;
	private String year;
	private String semester;
	private String imgUrl;
	private transient Bitmap image;
	
	
	public Item() {
		this("000000", "0000", "0", "");
	}
	public Item(String courseNo, String year, String semester, String imgUrl){
		setCourseNo(courseNo);
		setYear(year);
		setSemester(semester);
		setImgUrl(imgUrl);
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}


	public String getCourseNo() {
		return courseNo;
	}


	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}


	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	


}
