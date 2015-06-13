package com.indyzalab.quest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends FragmentActivity implements MapPageFragment.OnFragmentInteractionListener, QuestLogFragment.OnFragmentInteractionListener , ProfileFragment.OnFragmentInteractionListener{


    public static final int QUEST_LOG_FRAGMENT = 0;
    public static final int MAP_FRAGMENT = 1;
    public static final int PROFILE_FRAGMENT = 2;
    public static final int CONFIRM_FRAGMENT = 3;
    private final int FRAGMENT_SIZE = PROFILE_FRAGMENT + 1;
    public static int current_fragment = 1;
    private Fragment[] fragments;
    private static final String FRAGMENT_TAG = "Fragment";
    public static FragmentManager fragmentManager;
    public Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialising the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentManager = getSupportFragmentManager();
        initializingFragment();
        showFragment(QUEST_LOG_FRAGMENT, false);
        mContext = this;

        //Menu
        ImageView menu_logs = (ImageView) findViewById(R.id.menu_logs);
        menu_logs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(QUEST_LOG_FRAGMENT,false);
            }
        });

        ImageView menu_create = (ImageView) findViewById(R.id.menu_create);
        menu_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Coming Soon",Toast.LENGTH_SHORT);
            }
        });

        ImageView menu_map = (ImageView) findViewById(R.id.menu_map);
        menu_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(MAP_FRAGMENT, false);
            }
        });

        ImageView menu_friends = (ImageView) findViewById(R.id.menu_friends);
        menu_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Coming Soon", Toast.LENGTH_SHORT);
            }
        });

        ImageView menu_profile = (ImageView) findViewById(R.id.menu_profile);
        menu_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(PROFILE_FRAGMENT, false);
            }
        });

    }


    public void initializingFragment(){
        fragments = new Fragment[FRAGMENT_SIZE];
        fragments [QUEST_LOG_FRAGMENT] = new QuestLogFragment();
        fragments [MAP_FRAGMENT] = new MapPageFragment();
        fragments[PROFILE_FRAGMENT] = new ProfileFragment();
//        fragments[CONFIRM_FRAGMENT] = new ConfirmFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for(int i = 0; i < fragments.length; i++) {

            transaction.hide(fragments[i]);

        } transaction.commit();
    }



    public void showFragment(int fragmentIndex, boolean addToBackStack) {

        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        current_fragment = fragmentIndex;
        for (int i = 0; i < fragments.length; i++) {

            Log.i("MainActivity", "fragment index: " + fragmentIndex + " i: " + i);
            if (i == fragmentIndex) {
                transaction.replace(R.id.container, fragments[i],FRAGMENT_TAG+i);
                transaction.show(fragments[i]);

//                BadgeView badge = new BadgeView(this, actionBar.getTabAt(1));
//                badge.setText("1");
//                badge.show();

            } else {

                transaction.hide(fragments[i]);

            }

        }

        if (addToBackStack) {

            transaction.addToBackStack(null);

        }
        try{
            transaction.commit();
        }catch(Exception e){

        }

        //Setup Action Bar
//        Log.i("Create actionbar Tab", "Tab Created "+"Index: "+MAIN);

//        if(fragmentIndex == MAIN) {
//
////        	createTab();
//
//        }

//        updateTabIcon(fragmentIndex);

    }




    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed(){

        switch(current_fragment){
            case QUEST_LOG_FRAGMENT:{
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//			Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
//		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		    intent.putExtra("EXIT", true);
//		    startActivity(intent);
                overridePendingTransition(R.anim.righttoleft,R.anim.lefttoright);
            } break;
            case MAP_FRAGMENT: showFragment(QUEST_LOG_FRAGMENT, false); break;
            case PROFILE_FRAGMENT: showFragment(QUEST_LOG_FRAGMENT, false); break;
            default: showFragment(QUEST_LOG_FRAGMENT, false);
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */

}
