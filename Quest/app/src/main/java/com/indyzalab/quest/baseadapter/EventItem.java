package com.indyzalab.quest.baseadapter;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by apple on 6/14/15 AD.
 */
public class EventItem {

    private String type;
    private String category;
    private LatLng position;
    private String title;
    private String description;
    private int number_attend;


    private Bitmap[] category_bitmap;
    private Bitmap[] type_bitmap;


    private Bitmap category_icon;
    private Bitmap type_icon;

    public static final String TYPE_PUBLIC = "public";
    public static final String TYPE_PRIVATE = "private";
    public static final String CATEGORY_FITNESS = "fitness";
    public static final String CATEGORY_FOOD = "food";
    public static final String CATEGORY_OUTDOOR = "outdoor";
    public static final String CATEGORY_SCHOOL = "school";
    public static final String CATEGORY_READING = "reading";
    public static final String CATEGORY_PRODUCTIVITY = "productivity";
    public static final String CATEGORY_FINANCES = "finances";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_HOUSEHOLD = "household";
    public static final String CATEGORY_ARTS = "arts";
    public static final String CATEGORY_TRAVEL = "travels";


    public EventItem(String type,Bitmap[] type_bitmap, String category,Bitmap[] category_bitmap, LatLng position, String title, String description, int number_attend) {
        setType_bitmap(type_bitmap);
        setType(type);
        setCategory_bitmap(category_bitmap);
        setCategory(category);
        this.position = position;
        this.title = title;
        this.description = description;
        this.number_attend = number_attend;
    }

    public boolean isPublic(){
        return type.equals(TYPE_PUBLIC);
    }

    public boolean isPrivate(){
        return type.equals(TYPE_PRIVATE);
    }

    public int getNumber_attend() {
        return number_attend;
    }

    public void setNumber_attend(int number_attend) {
        this.number_attend = number_attend;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        Bitmap icon;
        switch (type){
            case TYPE_PRIVATE: icon = type_bitmap[1]; break;
            case TYPE_PUBLIC: icon = type_bitmap[2]; break;
            default: icon = type_bitmap[0]; break;
        }
        setType_icon(icon);
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        Bitmap icon;
        switch (category){
            case CATEGORY_FITNESS: icon = category_bitmap[1]; break;
            case CATEGORY_FOOD: icon = category_bitmap[2]; break;
            case CATEGORY_OUTDOOR: icon = category_bitmap[3]; break;
            case CATEGORY_SCHOOL: icon = category_bitmap[4]; break;
            case CATEGORY_READING: icon = category_bitmap[5]; break;
            case CATEGORY_PRODUCTIVITY: icon = category_bitmap[6]; break;
            case CATEGORY_FINANCES: icon = category_bitmap[7]; break;
            case CATEGORY_SOCIAL: icon = category_bitmap[8]; break;
            case CATEGORY_HOUSEHOLD: icon = category_bitmap[9]; break;
            case CATEGORY_ARTS: icon = category_bitmap[10]; break;
            case CATEGORY_TRAVEL: icon = category_bitmap[11]; break;
            default: icon = category_bitmap[0];
        }
        setCategory_icon(icon);
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Bitmap getCategory_icon() {
        return category_icon;
    }

    public void setCategory_icon(Bitmap category_icon) {
        this.category_icon = category_icon;
    }

    public Bitmap getType_icon() {
        return type_icon;
    }

    public void setType_icon(Bitmap type_icon) {
        this.type_icon = type_icon;
    }


    public Bitmap[] getCategory_bitmap() {
        return category_bitmap;
    }

    public void setCategory_bitmap(Bitmap[] category_bitmap) {
        this.category_bitmap = category_bitmap;
    }

    public Bitmap[] getType_bitmap() {
        return type_bitmap;
    }

    public void setType_bitmap(Bitmap[] type_bitmap) {
        this.type_bitmap = type_bitmap;
    }

}
