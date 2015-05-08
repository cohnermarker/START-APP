//This fragment was built with the assistance of Jim Ward

package com.start;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;
import android.content.Context;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class HelpOnWay extends Fragment {
    TextView output;
	public HelpOnWay() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.fragment_help_on_way, container, false);

        output = (TextView) myView.findViewById(R.id.gps);
        //output.append("\nNOTE, if you haven't told the Sim a location, there will be errors!\n");
        LocationManager myL = (LocationManager) getActivity().getBaseContext().getSystemService(Context.LOCATION_SERVICE);
        myL.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        //if we have location information, update the screen here.  just lat and lot, others
                        //are shown if you may need them.
                        if (location != null) {
                            output.append("\n onLocationChanged called");
					/*	    location.getAltitude();
	    			        location.getLatitude();
	    			        location.getLongitude();
	    			        location.getTime();
	    			        location.getAccuracy();
	    			        location.getSpeed();
	    			        location.getProvider();
					 */
                            output.append("\n"+location.getLatitude() + " "+ location.getLongitude());

                        }
                    }
                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                    @Override
                    public void onProviderEnabled(String provider) {


                    }
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {


                    }
                } );

        //Get a list of providers
        //could also use  String = getBestProvider(Criteria  criteria, boolean enabledOnly)
        List<String> mylist = myL.getProviders(true);
        Location loc = null;  String networkstr = "";
        for (int i=0; i<mylist.size() && loc ==null ; i++) {
            networkstr = mylist.get(i).toString();
            output.append("\n Attempting: "+ networkstr);
            loc = myL.getLastKnownLocation(networkstr);
        }
        if (loc != null ) {
            double sLatitude = loc.getLatitude();
            double sLongitude = loc.getLongitude();
            String location = " "+sLatitude+","+sLongitude;
            output.append(location);
        } else {
            output.append("\nNo location can be found.\n");
        }

		return myView;
	}

}
