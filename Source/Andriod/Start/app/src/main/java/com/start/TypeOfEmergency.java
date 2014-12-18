package com.start;

import com.start.SafetyCheck.Callbacks;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class TypeOfEmergency extends Fragment {

	public TypeOfEmergency() {
		// Required empty public constructor
	}

	private Callbacks mCallbacks = sDummyCallbacks;
	
	Button armedRob;
	Button shooter;
	Button bombThreat;
	Button fire;
	Button medicalEmer;
	Button animal;
	Button other;
	
	public interface Callbacks {

		public void onButtonSelected(int id);
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.fragment_type_of_emergency, container, false);
		
		armedRob = (Button)myView.findViewById(R.id.armedRob);
		shooter = (Button)myView.findViewById(R.id.shooter);
		bombThreat = (Button)myView.findViewById(R.id.bombThreat);
		fire = (Button)myView.findViewById(R.id.fire);
		medicalEmer = (Button)myView.findViewById(R.id.medicalEmer);
		animal = (Button)myView.findViewById(R.id.animal);
		other = (Button)myView.findViewById(R.id.other);
		
		armedRob.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCallbacks.onButtonSelected(4);
				
			}
		});
		
		shooter.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				mCallbacks.onButtonSelected(5);
				
			}
		});
		
		bombThreat.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				mCallbacks.onButtonSelected(6);
				
			}
		});
		
		fire.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				mCallbacks.onButtonSelected(7);
				
			}
		});
		
		medicalEmer.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				mCallbacks.onButtonSelected(8);
				
			}
		});
		
		animal.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				mCallbacks.onButtonSelected(9);
				
			}
		});
		
		other.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				mCallbacks.onButtonSelected(10);
				
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
