package com.start;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SafetyCheck extends Fragment {


	private Callbacks mCallbacks = sDummyCallbacks;
	
	Button yesButton;
	Button noButton;
	
	public interface Callbacks {

		public void onButtonSelected(int id);
	}

	public SafetyCheck() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.fragment_safety_check, container, false);
		
		yesButton = (Button)myView.findViewById(R.id.yes);
		noButton = (Button)myView.findViewById(R.id.no);
		yesButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCallbacks.onButtonSelected(2);
				
			}
		});
		
		noButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				mCallbacks.onButtonSelected(3);
				
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
