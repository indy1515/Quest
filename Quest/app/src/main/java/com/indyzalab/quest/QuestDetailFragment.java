package com.indyzalab.quest;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indyzalab.quest.baseadapter.EventItem;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static boolean contain;
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
     * @return A new instance of fragment QuestDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestDetailFragment newInstance(String param1, String param2) {
        QuestDetailFragment fragment = new QuestDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public QuestDetailFragment() {
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
        View view = inflater.inflate(R.layout.fragment_quest_detail, container, false);
        AndroidUtils.setNoklaeFont(view.getContext(),view);
        ImageView backBtn = (ImageView) view.findViewById(R.id.quest_detail_back_button);
        ImageView acceptBtn = (ImageView) view.findViewById(R.id.quest_detail_accept_button);
        TextView numAttend = (TextView) view.findViewById(R.id.quest_detail_num_attend);
        TextView type = (TextView) view.findViewById(R.id.quest_detail_type);
        TextView title = (TextView) view.findViewById(R.id.quest_detail_title);
        TextView desc = (TextView) view.findViewById(R.id.quest_detail_desc);
        contain = false;
        final EventItem current_item = getCurrentItem();
        ArrayList<EventItem> events = getCurrentLog();
        if(events.contains(current_item)){
            contain =true;
            acceptBtn.setImageResource(R.drawable.btn_done_idle);
        }

        numAttend.setText(current_item.getNumber_attend()+"");

        if(current_item.isPrivate()){
            type.setText("Private");
        }else if(current_item.isPublic()){
            type.setText("Public");
        }

        title.setText(current_item.getTitle());

        desc.setText(current_item.getDescription());
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(MainActivity.MAP_FRAGMENT, false);
            }
        });
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save Data and addup to list
                if(contain){
                    ArrayList<EventItem> current_log = getCurrentLog();
                    current_log.remove(current_item);
                    MainActivity.quest_count++;
                    showFragment(MainActivity.QUEST_LOG_FRAGMENT, false);
                }else {
                    ArrayList<EventItem> current_log = getCurrentLog();
                    current_log.add(0, getCurrentItem());
                    setCurrentLog(current_log);
                    showFragment(MainActivity.QUEST_LOG_FRAGMENT, false);
                }
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
        }
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
        public void setCurrentLog(ArrayList<EventItem> eventsList);
        public ArrayList<EventItem> getCurrentLog();
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

    public void setCurrentLog(ArrayList<EventItem> eventsList) {
        if (mListener != null) {
            mListener.setCurrentLog(eventsList);
        }
    }


    public ArrayList<EventItem> getCurrentLog() {
        if (mListener != null) {
            return mListener.getCurrentLog();
        }
        return new ArrayList<EventItem>();
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
