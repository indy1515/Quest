package com.indyzalab.quest;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SplashFragment extends Fragment {

	private Button skipLoginButton;
    private SkipLoginCallback skipLoginCallback;

    public interface SkipLoginCallback {
        void onSkipLoginPressed();
    }
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_splash_fragment, container, false);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/JS_Noklae.ttf");
        
        TextView engineer_tx = (TextView) view.findViewById(R.id.splash_engineer_text);
        engineer_tx.setTypeface(tf);

//        LoginButton authButton = (LoginButton) view.findViewById(R.id.login_button);
//        authButton.setFragment(this);
//        skipLoginButton = (Button) view.findViewById(R.id.skip_login_button);
//        skipLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (skipLoginCallback != null) {
//                    skipLoginCallback.onSkipLoginPressed();
//                }
//            }
//        });
        
        
        return view;
    }

    public void setSkipLoginCallback(SkipLoginCallback callback) {
        skipLoginCallback = callback;
    }

}
