package com.start;

import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class MainActivity extends FragmentActivity implements  LoginFragment.Callbacks, AlertFragment.Callbacks, SafetyCheck.Callbacks,
																TypeOfEmergency.Callbacks, OtherEmergency.Callbacks, PasscodeFragment.Callbacks,
                                                                NewUserFrag.Callbacks, PoliceExample.Callbacks {

	FragmentManager fragmentManager;
    String userName=null;
    String passName=null;
    String serverResponse="";

    public String getUserName()
    {
        return userName;
    }

    public String getPassName()
    {
        return passName;
    }

    public void setUserName(String name)
    {
        userName = name;
    }

    public void setPassName(String name)
    {
        passName = name;
    }

    public String getServerResponse(){ return serverResponse;}

    public void setServerResponse(String name){ serverResponse = name;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, new LoginFragment()).commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem logoutItem = (MenuItem) menu.findItem(R.id.action_logout);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Toast toast = Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT);
            toast.show();

            String filename = "shadow";
            File file = new File(filename);

            try {
                //file.mkdirs();
                //FileOutputStream out = act.openFileOutput(filename, act.MODE_PRIVATE);
                FileOutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory()+ File.separator + "start/" + filename + ".txt" );
                BufferedOutputStream writing = new BufferedOutputStream(out);

                setUserName(null);
                setPassName(null);
                writing.write(getUserName().getBytes());
                writing.write("\n".getBytes());
                writing.write(getPassName().getBytes());

                writing.flush();
                out.flush();
                writing.close();
                out.close();



            } catch (Exception e) {
                e.printStackTrace();
            }


            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, new LoginFragment()).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onButtonSelected(int id) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		
		/*id is determined in each java file for each fragment. Depending on which button is pressed, it gives different information
		and it gives a different fragment.
		
		transaction.replace(R.id.container, new <fragment_name>); determines which fragment to send it to
		replace means it will remove the other fragment to display the current fragment
		container is where the fragment is displayed, which for us will always be the full screen
		Spencer Johnson 10/29/14
		*/
		
		
		//First fragment for the login.
		//This is called when the large help button has been pressed
        if(id == 0)
        {

            Thread connect = new Thread() {
                public void run() {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://www.cs.uwyo.edu/~cmarker1/login.php");

                    try {
                        // Add your data
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("userName", getUserName().toString()));
                        nameValuePairs.add(new BasicNameValuePair("passName", getPassName().toString()));
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        // Execute HTTP Post Request
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        //is = entity.getContent();

                    } catch (ClientProtocolException e) {
                        Log.e("log_tag", "ClientProtocol "+e.toString());
                    } catch (IOException e) {
                        Log.e("log_tag", "IO "+e.toString());
                    }

                }
            };
            connect.start();



            transaction.replace(R.id.container, new AlertFragment());
            //Toast toast = Toast.makeText(getApplicationContext(), userName + " " + passName, Toast.LENGTH_SHORT);
            //toast.show();




        }
		else if(id == 1)
		{
            transaction.replace(R.id.container, new SafetyCheck());

            //This Grabs the GPS location to send in an HTTP post request to the server
            //The Location Manager updates the location
            // Works as expected and is very accurate
            //Cohner Marker

            LocationManager myL = (LocationManager) this.getBaseContext().getSystemService(Context.LOCATION_SERVICE);
            myL.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            //if we have location information, update the screen here.  just lat and lot, others
                            //are shown if you may need them.
                            if (location != null) {
                                location.getLatitude();
                                location.getLongitude();
					/*	    location.getAltitude();

	    			        location.getTime();
	    			        location.getAccuracy();
	    			        location.getSpeed();
	    			        location.getProvider();
					 */

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

            //Cant set a final variable
            //So this one is used to set GPS
            String tempGPS = "";

            //Request the location from the
            //Location Manager
            //If not null set the strings
            //if null set to location cannot be found
            Location loc = null;  String networkstr = "";
            List<String> mylist = myL.getProviders(true);
            for (int i=0; i<mylist.size() && loc ==null ; i++) {
                networkstr = mylist.get(i).toString();
                loc = myL.getLastKnownLocation(networkstr);
            }
            if (loc != null ) {
                double sLatitude = loc.getLatitude();
                double sLongitude = loc.getLongitude();
                tempGPS = sLatitude+","+sLongitude;

            } else {
                tempGPS ="No location can be found";
            }

            //Assign to final variable to be
            //able to send to the server!
            final String finGPS = tempGPS;

			 /*This is where the networking takes place. Essentially it will create a http client
             * and start it on a separate thread. It has to be on a separate thread or else it
             * will crash the entire app. All networking threads should be instant
             * and will create a post request to the php it needs.
             * Cohner Marker
             */
            Thread connect = new Thread() {
                public void run() {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://www.cs.uwyo.edu/~cmarker1/helpNeeded.php");

                    try {
                        // Add your data
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("status", "UNRESOLVED"));
                        nameValuePairs.add(new BasicNameValuePair("user", "cmarker1"));
                        nameValuePairs.add(new BasicNameValuePair("cellphone", "307-359-2632"));
                        nameValuePairs.add(new BasicNameValuePair("gps", finGPS));
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        // Execute HTTP Post Request
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();

                    } catch (ClientProtocolException e) {
                        Log.e("log_tag", "ClientProtocol "+e.toString());
                    } catch (IOException e) {
                        Log.e("log_tag", "IO "+e.toString());
                    }
                }
            };
            connect.start();

        }

		//Second fragment for the safety question
		//This is called when the yes button is selected
		else if(id == 2)
		{
            transaction.replace(R.id.container, new TypeOfEmergency());

			/*This is where the networking takes place. Essentially it will create a http client
             *and start it on a separate thread. It has to be on a separate thread or else it
             * will crash the entire app. All networking threads should be instant
             * and will create a post request to the php it needs.
             * Cohner Marker
             */
            Thread connect = new Thread() {
                public void run() {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://www.cs.uwyo.edu/~cmarker1/areyousafe.php");

                    try {
                        // Add your data
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("safe", "YES"));
                        nameValuePairs.add(new BasicNameValuePair("user", "cmarker1"));
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        // Execute HTTP Post Request
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        //is = entity.getContent();

                    } catch (ClientProtocolException e) {
                        Log.e("log_tag", "ClientProtocol "+e.toString());
                    } catch (IOException e) {
                        Log.e("log_tag", "IO "+e.toString());
                    }

                }
            };
            connect.start();
		}
		
		//Second fragment for the safety question
		//this is called when the no button is selected
		else if(id == 3)
		{
            transaction.replace(R.id.container, new TypeOfEmergency());
			/*This is where the networking takes place. Essentially it will create a http client
             *and start it on a separate thread. It has to be on a separate thread or else it
             * will crash the entire app. All networking threads should be instant
             * and will create a post request to the php it needs.
             * Cohner Marker
             */
            Thread connect = new Thread() {
                public void run() {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://www.cs.uwyo.edu/~cmarker1/areyousafe.php");

                    try {
                        // Add your data
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("safe", "NO"));
                        nameValuePairs.add(new BasicNameValuePair("user", "cmarker1"));
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        // Execute HTTP Post Request
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        //is = entity.getContent();

                    } catch (ClientProtocolException e) {
                        Log.e("log_tag", "ClientProtocol "+e.toString());
                    } catch (IOException e) {
                        Log.e("log_tag", "IO "+e.toString());
                    }

                }
            };
            connect.start();

        }
		
		
		//Third fragment for the safety question
		//this is called when the robber button is selected
		else if(id == 4)
		{
            transaction.replace(R.id.container, new HelpOnWay());

            /*This is where the networking takes place. Essentially it will create a http client
             *and start it on a separate thread. It has to be on a separate thread or else it
             * will crash the entire app. All networking threads should be instant
             * and will create a post request to the php it needs.
             * Cohner Marker
             */
            Thread connect = new Thread() {
                public void run() {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://www.cs.uwyo.edu/~cmarker1/ThreatResponse.php");

                    try {
                        // Add your data
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("nature", "Armed Robbery"));
                        nameValuePairs.add(new BasicNameValuePair("user", "cmarker1"));
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        // Execute HTTP Post Request
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        //is = entity.getContent();

                    } catch (ClientProtocolException e) {
                        Log.e("log_tag", "ClientProtocol "+e.toString());
                    } catch (IOException e) {
                        Log.e("log_tag", "IO "+e.toString());
                    }

                }
            };
            connect.start();
		}
		
		//Third fragment for the safety question
				//this is called when the shooter button is selected
		else if(id == 5)
		{
            transaction.replace(R.id.container, new HelpOnWay());


            /*This is where the networking takes place. Essentially it will create a http client
             *and start it on a separate thread. It has to be on a separate thread or else it
             * will crash the entire app. All networking threads should be instant
             * and will create a post request to the php it needs.
             * Cohner Marker
             */
            Thread connect = new Thread() {
                public void run() {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://www.cs.uwyo.edu/~cmarker1/ThreatResponse.php");

                    try {
                        // Add your data
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("nature", "Live Shooter"));
                        nameValuePairs.add(new BasicNameValuePair("user", "cmarker1"));
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        // Execute HTTP Post Request
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        //is = entity.getContent();

                    } catch (ClientProtocolException e) {
                        Log.e("log_tag", "ClientProtocol "+e.toString());
                    } catch (IOException e) {
                        Log.e("log_tag", "IO "+e.toString());
                    }

                }
            };
            connect.start();
		}
		
		//Third fragment for the safety question
				//this is called when the bomb threat button is selected
		else if(id == 6)
		{
            transaction.replace(R.id.container, new HelpOnWay());


            /*This is where the networking takes place. Essentially it will create a http client
             *and start it on a separate thread. It has to be on a separate thread or else it
             * will crash the entire app. All networking threads should be instant
             * and will create a post request to the php it needs.
             * Cohner Marker
             */
            Thread connect = new Thread() {
                public void run() {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://www.cs.uwyo.edu/~cmarker1/ThreatResponse.php");

                    try {
                        // Add your data
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("nature", "Bomb Threat"));
                        nameValuePairs.add(new BasicNameValuePair("user", "cmarker1"));
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        // Execute HTTP Post Request
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        //is = entity.getContent();

                    } catch (ClientProtocolException e) {
                        Log.e("log_tag", "ClientProtocol "+e.toString());
                    } catch (IOException e) {
                        Log.e("log_tag", "IO "+e.toString());
                    }

                }
            };
            connect.start();
		}
		
		//Third fragment for the safety question
				//this is called when the fire button is selected
		else if(id == 7)
		{
            transaction.replace(R.id.container, new HelpOnWay());


            /*This is where the networking takes place. Essentially it will create a http client
             *and start it on a separate thread. It has to be on a separate thread or else it
             * will crash the entire app. All networking threads should be instant
             * and will create a post request to the php it needs.
             * Cohner Marker
             */
            Thread connect = new Thread() {
                public void run() {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://www.cs.uwyo.edu/~cmarker1/ThreatResponse.php");

                    try {
                        // Add your data
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("nature", "FIRE"));
                        nameValuePairs.add(new BasicNameValuePair("user", "cmarker1"));
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        // Execute HTTP Post Request
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        //is = entity.getContent();

                    } catch (ClientProtocolException e) {
                        Log.e("log_tag", "ClientProtocol "+e.toString());
                    } catch (IOException e) {
                        Log.e("log_tag", "IO "+e.toString());
                    }

                }
            };
            connect.start();

        }
		
		//Third fragment for the safety question
				//this is called when the medical emergency button is selected
		else if(id == 8)
		{
            transaction.replace(R.id.container, new HelpOnWay());


            /*This is where the networking takes place. Essentially it will create a http client
             *and start it on a separate thread. It has to be on a separate thread or else it
             * will crash the entire app. All networking threads should be instant
             * and will create a post request to the php it needs.
             * Cohner Marker
             */
            Thread connect = new Thread() {
                public void run() {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://www.cs.uwyo.edu/~cmarker1/ThreatResponse.php");

                    try {
                        // Add your data
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("nature", "Medical Emergency"));
                        nameValuePairs.add(new BasicNameValuePair("user", "cmarker1"));
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        // Execute HTTP Post Request
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        //is = entity.getContent();

                    } catch (ClientProtocolException e) {
                        Log.e("log_tag", "ClientProtocol "+e.toString());
                    } catch (IOException e) {
                        Log.e("log_tag", "IO "+e.toString());
                    }

                }
            };
            connect.start();
		}
		
		//Third fragment for the safety question
				//this is called when the animal button is selected
		else if(id == 9)
		{
			transaction.replace(R.id.container, new PoliceExample());
            Thread connect = new Thread() {
                public void run() {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://www.cs.uwyo.edu/~cmarker1/ThreatResponse.php");

                    try {
                        // Add your data
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("nature", "Wild Animal"));
                        nameValuePairs.add(new BasicNameValuePair("user", "cmarker1"));
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        // Execute HTTP Post Request
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        //is = entity.getContent();

                    } catch (ClientProtocolException e) {
                        Log.e("log_tag", "ClientProtocol "+e.toString());
                    } catch (IOException e) {
                        Log.e("log_tag", "IO "+e.toString());
                    }

                }
            };
            connect.start();
		}
		
		//Third fragment for the safety question
				//this is called when the other button is selected
		else if(id == 10)
		{
			transaction.replace(R.id.container, new OtherEmergency());
			Toast toast = Toast.makeText(getApplicationContext(), "ENTER OTHER EMERGENCY, 10", Toast.LENGTH_SHORT);
			toast.show();
		}
		else if(id == 11)
		{
			//Currently unsure how to grab string that is input between fragments, but everything works
			transaction.replace(R.id.container, new HelpOnWay());
			//Toast toast = Toast.makeText(getApplicationContext(), "EMERGENCY IS SENT 11", Toast.LENGTH_SHORT);
			//toast.show();
		}

        else if(id == 12)
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter username and password 12", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(id == 13)
        {
            //Toast toast = Toast.makeText(getApplicationContext(), "File Created 13", Toast.LENGTH_SHORT);
            //toast.show();
        }
        else if(id == 14)
        {
            //Toast toast = Toast.makeText(getApplicationContext(), "File Doesn't Exist 14", Toast.LENGTH_SHORT);
            //toast.show();
        }
        else if(id == 15)
        {
            //Toast toast = Toast.makeText(getApplicationContext(), "Attempted to read from file 15", Toast.LENGTH_SHORT);
            //Toast toasty = Toast.makeText(getApplicationContext(), userName + " " + passName, Toast.LENGTH_SHORT);
           // toast.show();
            //toasty.show();
        }
        else if(id == 16)
        {
            transaction.replace(R.id.container, new NewUserFrag());
            //Toast toast = Toast.makeText(getApplicationContext(), "Register New User, 16", Toast.LENGTH_SHORT);
            //toast.show();
        }
        else if(id == 17) {
            Toast toast = Toast.makeText(getApplicationContext(), "Password does not match confirmation 17", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(id == 18)
        {
            transaction.replace(R.id.container, new LoginFragment());
            Toast toast = Toast.makeText(getApplicationContext(), "New User Created, 18", Toast.LENGTH_SHORT);
            toast.show();
        }


        transaction.commit();
	}

}
