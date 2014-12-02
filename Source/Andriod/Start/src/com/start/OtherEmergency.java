package com.start;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link OtherEmergency.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link OtherEmergency#newInstance} factory method to create an instance of
 * this fragment.
 * 
 */
public class OtherEmergency extends Fragment {

	public OtherEmergency() {
		// Required empty public constructor
	}

private String type;
EditText input;
Button send;
	


private Callbacks mCallbacks = sDummyCallbacks;
	
	public interface Callbacks {

		public void onButtonSelected(int id);
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.fragment_other_emergency, container, false);
		input = (EditText)myView.findViewById(R.id.editText1);
		send = (Button)myView.findViewById(R.id.enter);
		
		send.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				type = input.getText().toString();
				mCallbacks.onButtonSelected(11);
				
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
	
	public String getString(){
		return type;
	}
}
