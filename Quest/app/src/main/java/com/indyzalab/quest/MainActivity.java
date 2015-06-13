package com.indyzalab.quest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.indyzalab.quest.baseadapter.Constants;

public class MainActivity extends FragmentActivity implements MapPageFragment.OnFragmentInteractionListener, QuestLogFragment.OnFragmentInteractionListener , ProfileFragment.OnFragmentInteractionListener{

    public static String PACKAGE_NAME;
    private static final String LOG_TAG = "MainActivity";
    private static final String USER_SKIPPED_LOGIN_KEY = "user_skipped_login";

    public static final int SPLASH_FRAGMENT = 0;
    public static final int QUEST_LOG_FRAGMENT = 1;
    public static final int MAP_FRAGMENT = 2;
    public static final int PROFILE_FRAGMENT = 3;
    public static final int CONFIRM_FRAGMENT = 4;
    private final int FRAGMENT_SIZE = PROFILE_FRAGMENT + 1;
    public static int current_fragment = 1;
    private Fragment[] fragments;
    private static final String FRAGMENT_TAG = "Fragment";
    public static FragmentManager fragmentManager;
    public Activity mContext;
    private boolean fbThreadRunning = false;

    private boolean isResumed = false;
    private boolean userSkippedLogin = false;
    private UiLifecycleHelper uiHelper;

    private static final String TAG = "Login";

    private Session.StatusCallback callback = new Session.StatusCallback() {

        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialising the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentManager = getSupportFragmentManager();
        initializingFragment();
//        showFragment(QUEST_LOG_FRAGMENT, false);
        mContext = this;
        if (savedInstanceState != null) {

            userSkippedLogin = savedInstanceState.getBoolean(USER_SKIPPED_LOGIN_KEY);

        }
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
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
        fragments [SPLASH_FRAGMENT] = new SplashFragment();
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
        uiHelper.onResume();
        isResumed = true;
    }

    @Override
    public void onPause() {

        super.onPause();
        uiHelper.onPause();
        isResumed = false;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
//        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        uiHelper.onDestroy();

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);

        outState.putBoolean(USER_SKIPPED_LOGIN_KEY, userSkippedLogin);

    }

    @Override
    protected void onResumeFragments() {

        super.onResumeFragments();
        Session session = Session.getActiveSession();

        Log.i("onResumeFragment", "Resume Fragment");
        if (session != null && session.isOpened()) {
            // if the session is already open, try to show the selection fragment
            Log.i("MainActivity","Resume "+current_fragment);
            if(current_fragment == SPLASH_FRAGMENT)
                current_fragment = QUEST_LOG_FRAGMENT;

            showFragment(current_fragment, false);

            userSkippedLogin = false;
            showTab();
        } else if (userSkippedLogin) {
            Log.i("MainActivity","Skip Login");

            showFragment(QUEST_LOG_FRAGMENT, false);
            showTab();
        } else {
            // otherwise present the splash screen and ask the user to login, unless the user explicitly skipped.
            Log.i("User Skip","Show Login");
//        	showFragment(COURSES, false);
            showFragment(SPLASH_FRAGMENT, false);
            hideTab();
        }

    }

    public void showTab(){
        Log.i("MainActivity", "Show Tab");
        LinearLayout ll = (LinearLayout) findViewById(R.id.menu_ll);
        if(ll.getVisibility() != View.VISIBLE){
            ll.setVisibility(View.VISIBLE);

//            getActionBar().show();
        }

    }
    public void hideTab(){
        Log.i("MainActivity","Hide Tab");
        LinearLayout ll = (LinearLayout) findViewById(R.id.menu_ll);

        ll.setVisibility(View.GONE);
//        getActionBar().hide();
    }




    private void onSessionStateChange(final Session session, SessionState state, Exception exception) {

        if (isResumed) {

            Log.d(TAG, "Session state changed: " + state);
            FragmentManager manager = getSupportFragmentManager();

            int backStackSize = manager.getBackStackEntryCount();

            for (int i = 0; i < backStackSize; i++) {
                manager.popBackStack();
            }
            //For Testing User Not found alert
//            showUserNotFoundAlert(context);
            Log.d(TAG, "Session state changed: " + state);

//            session = Session.getActiveSession();
            // check for the OPENED state instead of session.isOpened() since for the
            // OPENED_TOKEN_UPDATED state, the selection fragment should already be showing.

            Log.i("Check State", "" + state.equals(SessionState.OPENED));
            Log.i("Check Session", "" + session.isOpened());
            Log.i("Check Session", "" + session.isClosed());

            if (state.equals(SessionState.OPENED)) {

                //Request for FB_ID
                final Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        // If the response is successful
                        if (session == Session.getActiveSession()) {
                            if (user != null) {
                                final String accessToken = session.getAccessToken();
                                final String user_ID = user.getId();//user id
                                String profileName = user.getName();//user's profile name
//                                userNameView.setText(user.getName());

                                //Copy to clipboard for testing only
//                                ClipboardManager clipboard = (ClipboardManager)
//                                        getSystemService(Context.CLIPBOARD_SERVICE);
//                                ClipData clip = ClipData.newPlainText("FB Access Data","Access token: "+accessToken+"\n FB ID: "+user_ID);
//                                clipboard.setPrimaryClip(clip);

                                //Save to Preference
                                SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
//                              S = settings.getString(Constants.ACCESS_TOKEN_NAME, "");
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString(Constants.ACCESS_TOKEN_NAME, accessToken);
                                editor.putString(Constants.FB_ID_NAME, user_ID);
                                editor.commit();

//                                Thread thread = new Thread(new Runnable(){
//                                    @Override
//                                    public void run() {
//                                        fbThreadRunning = true;
//                                        try {
//                                            JSONParser jParser = new JSONParser();
//                                            String url = Constants.CV_URL+"?q=cvapi/get/course&fbid="+user_ID+"&access_token="+accessToken;
//                                            JSONObject jObject = jParser.getJSONObjFromUrl(url);
//                                            String status = jObject.getString("status");
//                                            Log.i("Check Status ","Status: "+status);
//                                            if(status.equals("0")){
//                                                //Try using request v.1
//                                                Request request2 = Request.newMeRequest(session, new Request.GraphUserCallback() {
//                                                    @Override
//                                                    public void onCompleted(GraphUser user, Response response) {
//                                                        // If the response is successful
//                                                        if (session == Session.getActiveSession()) {
//                                                            if (user != null) {
//                                                                final String accessToken = session.getAccessToken();
//                                                                final String user_ID = user.getId();//user id
//                                                                String profileName = user.getName();//user's profile name
////                                userNameView.setText(user.getName());
//
//                                                                //Copy to clipboard for testing only
////                                ClipboardManager clipboard = (ClipboardManager)
////                                        getSystemService(Context.CLIPBOARD_SERVICE);
////                                ClipData clip = ClipData.newPlainText("FB Access Data","Access token: "+accessToken+"\n FB ID: "+user_ID);
////                                clipboard.setPrimaryClip(clip);
//
//                                                                //Save to Preference
//                                                                SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
////                              S = settings.getString(Constants.ACCESS_TOKEN_NAME, "");
//                                                                SharedPreferences.Editor editor = settings.edit();
//                                                                editor.putString(Constants.ACCESS_TOKEN_NAME, accessToken);
//                                                                editor.putString(Constants.FB_ID_NAME, user_ID);
//                                                                editor.commit();
//
//                                                                Thread thread = new Thread(new Runnable(){
//                                                                    @Override
//                                                                    public void run() {
//                                                                        try {
//                                                                            JSONParser jParser = new JSONParser();
//                                                                            String url = Constants.CV_URL+"?q=cvapi/get/course&fbid="+user_ID+"&access_token="+accessToken;
//                                                                            JSONObject jObject = jParser.getJSONObjFromUrl(url);
//                                                                            String status = jObject.getString("status");
//                                                                            Log.i("Check Status ","Status: "+status);
//                                                                            if(status.equals("0")){
//                                                                                //Nothing found
//                                                                                showUserNotFoundAlert(mContext);
//                                                                                return;
//                                                                            }else if(status.equals("1")){
//                                                                                // Found!
//                                                                                Log.i("Check Status","Graph V1 Found!");
//                                                                            }else{
//                                                                                Log.i("Check Status","Error");
//                                                                                showUserNotFoundAlert(mContext);
//                                                                                return;
//                                                                            }
//                                                                        } catch (Exception e) {
//                                                                            e.printStackTrace();
//                                                                        }
//                                                                    }
//                                                                });
//
//                                                                thread.start();
//
//
//                                                                if(getIntent().getAction().equals("Notification")) {
//                                                                    // Open Tab
//                                                                    showFragment(NOTI, false);
//                                                                }else{
//                                                                    //Show Fragment
//                                                                    showFragment(COURSES, false);
//
//                                                                }
//
//                                                                // Start service to fetch notification
////                                                                startService(new Intent(MainActivity.this, NotificationService.class));
//                                                                Log.i(TAG, "Logged in...");
//                                                                Log.i("FB_id", user.getId());
//                                                                Log.i("Access Token",session.getAccessToken());
//                                                                fbThreadRunning = false;
//                                                            }
//                                                        }
//                                                    }
//                                                });
//                                                request2.setVersion("v1.0");
//                                                Request.executeBatchAsync(request2);
//                                                fbThreadRunning = false;
//                                            }else if(status.equals("1")){
//                                                fbThreadRunning = false;
//                                                showUserNotFoundAlert(mContext);
//                                                return;
//                                            }else{
//                                                Log.i("Check Status","Error");
//                                                fbThreadRunning = false;
//                                                showUserNotFoundAlert(mContext);
//                                                return;
//                                            }
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                });
//
//                                thread.start();
//                                while(!fbThreadRunning){
//
//                                }


                                //Show Fragment
                                showFragment(QUEST_LOG_FRAGMENT, false);


                                // Start service to fetch notification
//                                startService(new Intent(MainActivity.this, NotificationService.class));
                                Log.i(TAG, "Logged in...");
                                Log.i("FB_id", user.getId());
                                Log.i("Access Token", session.getAccessToken());
                            }
                        }
                    }
                });
                Request.executeBatchAsync(request);
//                Toast.makeText(this, "Data Copy to clipboard!", Toast.LENGTH_LONG).show();
//                Toast.makeText(this, session.getAccessToken(), Toast.LENGTH_LONG).show();


            } else if (state.isClosed()) {

                showFragment(SPLASH_FRAGMENT, false);
                hideTab();
                Log.i(TAG, "Logged out...");

            }
        }
    }

    private void showUserNotFoundAlert(final Context context){
        new AlertDialog.Builder(this)
                .setTitle("User Not Found")
                .setMessage("Please make sure you register at myCourseVille")
                .setNegativeButton("Register", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
//                        showFragment(SPLASH,false);
                        Session.getActiveSession().closeAndClearTokenInformation();
                        String full_link = "https://www.mycourseville.com";
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(full_link));
                        context.startActivity(browserIntent);
                        finish();
                    }
                })
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        showFragment(SPLASH_FRAGMENT,false);
                        Session.getActiveSession().closeAndClearTokenInformation();
                        finish();
                    }
                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }



}
