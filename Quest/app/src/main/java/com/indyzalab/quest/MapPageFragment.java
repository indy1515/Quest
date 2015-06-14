package com.indyzalab.quest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.indyzalab.quest.baseadapter.CustomWindowInfoAdapter;
import com.indyzalab.quest.baseadapter.EventItem;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapPageFragment extends Fragment implements LocationListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private GoogleMap googleMap; // Might be null if Google Play services APK is not available.
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context mContext;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LocationManager locationManager;

    private FragmentActivity myContext;

    private OnFragmentInteractionListener mListener;
    private LayoutInflater mInflator;

    //LatLng
    static final LatLng Sethitower = new LatLng(13.7217557,100.5246354);
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapPageFragment newInstance(String param1, String param2) {
        MapPageFragment fragment = new MapPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MapPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mInflator = inflater;
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mContext = view.getContext();
        setUpMapIfNeeded();
        View locationButton = ((View) view.findViewById(1).getParent()).findViewById(2);

        // and next place it, for exemple, on bottom right (as Google Maps app)
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
        rlp.setMargins(0, 30, 30, 0);
        return view;
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (googleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            if(MainActivity.fragmentManager == null) Log.i("Error","null");
            googleMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (googleMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #googleMap} is not null.
     */
    private void setUpMap() {
        // Move the camera instantly to hamburg with a zoom of 15.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Sethitower, 14.75f));//14.75f

        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.25f), 2000, null);


        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setMyLocationEnabled(true);
        googleMap.getMyLocation();
        //Clear map in case of some mobile strange activity
        googleMap.clear();
        // Create location manager for user gps
        locationManager =
                (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        final Location location = locationManager.getLastKnownLocation(provider);
        locationManager.requestLocationUpdates(provider, 2000, 1, this);
        addMarker();


    }
    public void addMarker(){
        // Load Data
        // Create all bitmap
        Bitmap[] type_icons = create_type_bitmap();
        Bitmap[] category_icons = create_category_bitmap();

        LatLng location1 = new LatLng(13.722079, 100.523466);
        LatLng location2 = new LatLng(13.720484, 100.523724);
        final ArrayList<EventItem> eventList = new ArrayList<EventItem>();
        final ArrayList<Marker> eventMarker = new ArrayList<Marker>();
        eventList.add(new EventItem(EventItem.TYPE_PUBLIC, type_icons,EventItem.CATEGORY_FITNESS,category_icons,Sethitower,"Run Together Marathon","Finish the marathon at marathon with marathon", 20));
        eventList.add(new EventItem(EventItem.TYPE_PUBLIC, type_icons,EventItem.CATEGORY_FOOD,category_icons,location1,"Enjoy Food Festival","Try 5 food stalls in the Festival", 5));
        eventList.add(new EventItem(EventItem.TYPE_PUBLIC, type_icons,EventItem.CATEGORY_SCHOOL,category_icons,location2,"Survive the Hackathon","The Hottest Hackathon in Thailand", 32));
        for(EventItem item: eventList){
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(item.getCategory_icon());
            eventMarker.add(googleMap.addMarker(new MarkerOptions()
                    .snippet("Detail:" + item.getDescription() + ",Number:"+item.getNumber_attend())
                    .title(item.getTitle())
                    .position(item.getPosition())
                    .icon(bitmapDescriptor)
                    .infoWindowAnchor(0.5f, 0.445f)));
        }
        googleMap.setInfoWindowAdapter(new CustomWindowInfoAdapter(mInflator,eventList,eventMarker));
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            public void onInfoWindowClick(Marker marker) {
                Log.i("Map",marker.getTitle());
                for(int i = 0;i<eventMarker.size();i++){
                    if(marker.getTitle().equals(eventMarker.get(i).getTitle())){
                        EventItem item = eventList.get(i);
                        setCurrentItem(item);
                        showFragment(MainActivity.QUEST_DETAIL_FRAGMENT,false);
                        break;

                    }
                }
            }
        });
    }

    public Bitmap[] create_type_bitmap(){
        Bitmap default_0 = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.icon_ppcompleted);
        Bitmap private_1 = default_0;
        Bitmap public_2 = default_0;
        Bitmap[] type_icons = {default_0,private_1,public_2};
        return  type_icons;
    }

    public Bitmap[] create_category_bitmap(){
        Bitmap default_0 = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.icon_quest_cat_0map);
        Bitmap fitness_1 = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.icon_quest_cat_1map);
        Bitmap food_2 = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.icon_quest_cat_2map);
        Bitmap outdoor_3 = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.icon_quest_cat_3map);;
        Bitmap school_4 = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.icon_quest_cat_4map);;
        Bitmap reading_5 = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.icon_quest_cat_5map);;
        Bitmap productivity_6 = default_0;
        Bitmap finances_7 = default_0;
        Bitmap social_8 = default_0;
        Bitmap household_9 = default_0;
        Bitmap arts_10 = default_0;
        Bitmap travel_11 = default_0;
        Bitmap[] category_icons = {default_0,fitness_1,food_2,outdoor_3,school_4,reading_5,productivity_6,finances_7,social_8,household_9,arts_10,travel_11};
        return category_icons;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (googleMap != null)
            setUpMap();

        if (googleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            googleMap = ((SupportMapFragment) MainActivity.fragmentManager
                    .findFragmentById(R.id.map)).getMap(); // getMap is deprecated
            // Check if we were successful in obtaining the map.
            if (googleMap != null)
                setUpMap();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myContext=(FragmentActivity) activity;
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    /**** The mapfragment's id must be removed from the FragmentManager
     **** or else if the same it is passed on the next time then
     **** app will crash ****/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (googleMap != null) {
            MainActivity.fragmentManager.beginTransaction()
                    .remove(getChildFragmentManager().findFragmentById(R.id.map)).commit();
            googleMap = null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void showFragment(int fragmentIndex, boolean addToBackStack);
        public void setCurrentItem(EventItem item);
        public EventItem getCurrentItem();
    }


    /**
     * Using selectItem from MainActivity
     * @param fragmentIndex
     */
    public void showFragment(int fragmentIndex, boolean addToBackStack) {
        if (mListener != null) {
            mListener.showFragment(fragmentIndex, addToBackStack);
        }
    }

    public void setCurrentItem(EventItem item) {
        if (mListener != null) {
            mListener.setCurrentItem(item);
        }
    }

    public EventItem getCurrentItem() {
        if (mListener != null) {
            return mListener.getCurrentItem();
        }
        return null;
    }


}
