package com.indyzalab.quest;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.indyzalab.quest.baseadapter.ListImageAdapter;
import com.indyzalab.quest.baseadapter.QuestItem;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestLogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestLogFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static ListView listView;
    private static SwipeRefreshLayout swipeContainer;
    private static ListImageAdapter listAdapter;
    private static ArrayList<QuestItem> listArray = new ArrayList<QuestItem>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestLogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestLogFragment newInstance(String param1, String param2) {
        QuestLogFragment fragment = new QuestLogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public QuestLogFragment() {
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
        View view = inflater.inflate(R.layout.fragment_quest_log, container, false);
        RelativeLayout upper_rl = (RelativeLayout) view.findViewById(R.id.quest_log_upper_layout);
        upper_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(MainActivity.PROFILE_FRAGMENT,false);
            }
        });
//        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
//        swipeContainer.setColorScheme(R.color.CVBlue, R.color.CVOrange, R.color.CVBlue, R.color.CVOrange);
        listArray = new ArrayList<QuestItem>();
        Bitmap icon = BitmapFactory.decodeResource(view.getContext().getResources(),
                R.drawable.icon_quest_cat_1);
        for(int i= 0;i < 6;i++) {
            listArray.add(new QuestItem("Type", "Title", "Description", icon));
        }
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        // some code #3 (that needs to be ran in UI thread)
//                        dataSource.open();
//                        listArray = dataSource.getAllNotificationItems();
//
//                        dataSource.close();

                        listAdapter = new ListImageAdapter(getActivity(), R.layout.quest_list_element, listArray);
                        listView.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                    }
                });


            }
        };

        thread.start();
        listView = (ListView) view.findViewById(R.id.listView_noti);
//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            private int visibleThreshold = 5;
//            private int currentPage = 0;
//            private int previousTotal = 0;
//            private boolean loading = true;
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int topRowVerticalPosition =
//                        (listView == null || listView.getChildCount() == 0) ?
//                                0 : listView.getChildAt(0).getTop();
//                swipeContainer.setEnabled(topRowVerticalPosition >= 0);
//
//                if (loading) {
//                    if (totalItemCount > previousTotal) {
//                        loading = false;
//                        previousTotal = totalItemCount;
//                        currentPage++;
//                    }
//                }
//                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
//                    // I load the next page of Noti using a background task,
//                    // but you can call any function here.
//
//                    DownloadNotiTask noti_task = new DownloadNotiTask(getActivity(), true,true);
//                    noti_task.execute(getOldestLink());
//                    loading = true;
//                }
//            }
//        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // Control clicking through items in list
//
//                QuestItem item = listAdapter.getItem(position);
//                View stateView = view.findViewById(R.id.state_open);
//
////                dataSource = new NotiItemDataSource(getActivity());
////                dataSource.open();
////                dataSource.triggerClickItem(item);
////                final int count = dataSource.getNoUnClicked();
////                Log.i("DownloadNoti", "Count : " + count);
////                dataSource.close();
//
//                stateView.setBackgroundColor(getActivity().getResources().getColor(android.R.color.transparent));
////                MainActivity.badgeTabUpdate(count);
//
//                String full_link = item.getItemUrl();
//                Log.i("Goto",full_link);
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(full_link));
//                getActivity().startActivity(browserIntent);
//
//            }
//        });
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                swipeContainer.setRefreshing(false);
////                if(listArray.size() != 0){
////                    DownloadNotiTask noti_task = new DownloadNotiTask(getActivity(), true);
////                    noti_task.execute(getLastestLinkNewNoti());
////                }else{
////                    //If no data then download fresh with service
////                    getActivity().startService(new Intent(getActivity(), NotificationService.class));
////                }
//            }
//        });

//        String final_link = getLastestLink();
//        String final_link = Constants.CV_URL + noti_API;
//        swipeContainer.setRefreshing(false);

//        dataSource = new NotiItemDataSource(this.getActivity());
//        try{
//            AsyncTask.Status status_first = NotificationService.taskFirstNoti.getStatus();
//            if(listArray.size() != 0){
//                if(status_first == AsyncTask.Status.FINISHED || status_first == AsyncTask.Status.PENDING){
//                    DownloadNotiTask task = new DownloadNotiTask(this.getActivity(),true);
//                    task.execute(getLastestLinkNewNoti());
//                    swipeContainer.setRefreshing(false);
//                }
//
//            }else{
//                //If no data then download fresh with service
//                getActivity().startService(new Intent(getActivity(), NotificationService.class));
//            }
//        }catch(NullPointerException e){
//            swipeContainer.setRefreshing(false);
//        }
        //Test First Fetch
        //Until reach none

        return view;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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

}
