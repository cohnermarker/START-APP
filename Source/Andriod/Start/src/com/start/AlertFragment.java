package com.start;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class AlertFragment extends Fragment {


	private Callbacks mCallbacks = sDummyCallbacks;
	
	Button helpButton;
	
	public interface Callbacks {
		public void onButtonSelected(int id);
	}
	
	public static AlertFragment newInstance() {
		AlertFragment fragment = new AlertFragment();
		return fragment;
	}

	public AlertFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.fragment_alert, container, false);
		
		int isPressed = 0;
		
		helpButton = (Button)myView.findViewById(R.id.helpButton);
		helpButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
						
				mCallbacks.onButtonSelected(1);
				
			}
		});
		
		return myView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}
		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = sDummyCallbacks;
	}

	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onButtonSelected(int id) {
			; //nothing, this is dummy method.
		}
	};

}
