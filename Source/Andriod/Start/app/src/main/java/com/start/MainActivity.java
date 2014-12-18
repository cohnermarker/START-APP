package com.start;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends FragmentActivity implements AlertFragment.Callbacks, SafetyCheck.Callbacks, 
																TypeOfEmergency.Callbacks, OtherEmergency.Callbacks {
	
	FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, new AlertFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
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

		//First fragment for the alert.
		//This is called when the large help button has been pressed
		if(id == 1)
		{ 
			transaction.replace(R.id.container, new SafetyCheck());
			final String response = null;


            /*This is where the networking takes place. Essentially it will create a http client
             *and start it on a separate thread. It has to be on a separate thread or else it
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
				        nameValuePairs.add(new BasicNameValuePair("message", "Help is Needed"));
				        nameValuePairs.add(new BasicNameValuePair("GPS", "41.3130972,-105.57967059999999"));
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
                        nameValuePairs.add(new BasicNameValuePair("message", "YES I AM SAFE"));
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



            //Toast toast = Toast.makeText(getApplicationContext(), "YES I AM SAFE, 2", Toast.LENGTH_SHORT);
			//toast.show();
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
                        nameValuePairs.add(new BasicNameValuePair("message", "NO, I AM UNSAFE"));
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

            //Toast toast = Toast.makeText(getApplicationContext(), "NO, I AM UNSAFE, 3", Toast.LENGTH_SHORT);
			//toast.show();
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
                        nameValuePairs.add(new BasicNameValuePair("message", "Armed Robbery"));
                        nameValuePairs.add(new BasicNameValuePair("notify", "Police"));
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

            //Toast toast = Toast.makeText(getApplicationContext(), "POLICE NOTIFIED, 4", Toast.LENGTH_SHORT);
			//toast.show();
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
                        nameValuePairs.add(new BasicNameValuePair("message", "Live Shooter"));
                        nameValuePairs.add(new BasicNameValuePair("notify", "Swat"));
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

			//Toast toast = Toast.makeText(getApplicationContext(), "SWAT NOTIFIED, 5", Toast.LENGTH_SHORT);
			//toast.show();
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
                        nameValuePairs.add(new BasicNameValuePair("message", "Bomb Threat"));
                        nameValuePairs.add(new BasicNameValuePair("notify", "Bomb Disposal"));
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




			//Toast toast = Toast.makeText(getApplicationContext(), "BOMB DISPOSAL NOTIFIED, 6", Toast.LENGTH_SHORT);
			//toast.show();
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
                        nameValuePairs.add(new BasicNameValuePair("message", "FIRE"));
                        nameValuePairs.add(new BasicNameValuePair("notify", "Fire Dept"));
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



			//Toast toast = Toast.makeText(getApplicationContext(), "FIRE DEPARTMENT NOTIFIED, 7", Toast.LENGTH_SHORT);
			//toast.show();
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
                        nameValuePairs.add(new BasicNameValuePair("message", "Medical Emergency"));
                        nameValuePairs.add(new BasicNameValuePair("notify", "Ambulance"));
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

			//Toast toast = Toast.makeText(getApplicationContext(), "AMBULANCE NOTIFIED, 8", Toast.LENGTH_SHORT);
			//toast.show();
		}
		
		//Third fragment for the safety question
				//this is called when the animal button is selected
		else if(id == 9)
		{
			transaction.replace(R.id.container, new HelpOnWay());
			Toast toast = Toast.makeText(getApplicationContext(), "ANIMAL CONTROL NOTIFIED, 9", Toast.LENGTH_SHORT);
			toast.show();
		}
		
		//Third fragment for the safety question
				//this is called when the other button is selected
		else if(id == 10)
		{
			transaction.replace(R.id.container, new OtherEmergency());


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
                        nameValuePairs.add(new BasicNameValuePair("message", "Loose Animal"));
                        nameValuePairs.add(new BasicNameValuePair("notify", "Animal Control"));
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

			//Toast toast = Toast.makeText(getApplicationContext(), "ENTER OTHER EMERGENCY, 10", Toast.LENGTH_SHORT);
			//toast.show();
		}
		else if(id == 11)
		{
			//Currently unsure how to grab string that is input between fragments, but everything works
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
                        nameValuePairs.add(new BasicNameValuePair("message", "Other"));
                        nameValuePairs.add(new BasicNameValuePair("notify", "<StatedEmergency>"));
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


			//Toast toast = Toast.makeText(getApplicationContext(), "EMERGENCY IS SENT 11", Toast.LENGTH_SHORT);
			//toast.show();
		}
		
		transaction.commit();
	}

}
