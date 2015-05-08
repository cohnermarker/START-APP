package com.start;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PoliceExample extends Fragment{


    public PoliceExample() {
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
        View myView = inflater.inflate(R.layout.police_example, container, false);


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
