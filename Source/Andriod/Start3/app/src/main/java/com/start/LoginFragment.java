package com.start;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.*;


public class LoginFragment extends Fragment {


    private Callbacks mCallbacks = sDummyCallbacks;

    Button loginButton;
    Button RegButton;
    EditText userField;
    EditText passField;



    public interface Callbacks {

        public void onButtonSelected(int id);
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_login, container, false);

        final MainActivity act = ((MainActivity)getActivity());

        loginButton = (Button)myView.findViewById(R.id.loginButton);
        RegButton = (Button)myView.findViewById(R.id.RegButton);
        final CheckBox remMe = (CheckBox) myView.findViewById(R.id.remMe);
        final EditText userField=(EditText)myView.findViewById(R.id.userText);
        final EditText passField=(EditText)myView.findViewById(R.id.passField);


        String filename = "shadow";

        File file = new File(Environment.getExternalStorageDirectory()+ File.separator + "start/" + filename + ".txt");

        if (remMe.isChecked()) {

            try {

                InputStream in = new FileInputStream(file);
                //InputStreamReader inReader = new InputStreamReader(in);

                FileReader inReader = new FileReader(file);


                BufferedReader reader = new BufferedReader(inReader);


                act.setUserName(reader.readLine());
                act.setPassName(reader.readLine());

                mCallbacks.onButtonSelected(15);

                if(act.getUserName() == null || act.getPassName() == null)
                {
                    in.close();
                    inReader.close();
                    reader.close();
                    mCallbacks.onButtonSelected(12);
                    //mCallbacks.onButtonSelected(0);

                }
                else
                {

                    Thread connect = new Thread() {
                        public void run() {

                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("http://www.cs.uwyo.edu/~cmarker1/login.php");

                            try {
                                // Add your data
                                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                                nameValuePairs.add(new BasicNameValuePair("username", act.getUserName().toString()));
                                nameValuePairs.add(new BasicNameValuePair("password", act.getPassName().toString()));
                                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                                // Execute HTTP Post Request
                                HttpResponse response = httpclient.execute(httppost);
                                HttpEntity entity = response.getEntity();

                            /*InputStream is = response.getEntity().getContent();
                            //convert response to string
                            BufferedReader myReader = new BufferedReader(new InputStreamReader(is,"UTF-16"));
                            StringBuilder sb = new StringBuilder();
                            sb.append(myReader.readLine() + "\n");
                            String line="";
                            while ((line = myReader.readLine()) != null) {
                                sb.append(line + "\n");
                            }*/
                                act.setServerResponse(EntityUtils.toString(entity));

                                //act.serverResponse = EntityUtils.toString(entity);

                            } catch (ClientProtocolException e) {
                                Log.e("log_tag", "ClientProtocol " + e.toString());
                            } catch (IOException e) {
                                Log.e("log_tag", "IO "+e.toString());
                            }

                        }
                    };
                    connect.start();

                    while(act.getServerResponse().equals("")){};

                    in.close();
                    inReader.close();
                    reader.close();





                    if(act.serverResponse.equals("Success")) {
                        mCallbacks.onButtonSelected(0);
                    }
                    else
                    {
                        mCallbacks.onButtonSelected(17);
                    }
                }

            }
            catch(IOException e)
            {
                mCallbacks.onButtonSelected(12);
                e.printStackTrace();
            }


        }
        else
        {
            mCallbacks.onButtonSelected(14);
            //remMe.setChecked(false);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String filename = "shadow";



                if(userField.getText().toString().equals("") || passField.getText().toString().equals(""))
                    mCallbacks.onButtonSelected(12);
                else if(remMe.isChecked()) {
                    try {
                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        digest.update(passField.getText().toString().getBytes());

                        byte[] bytes= digest.digest();
                        StringBuffer hexString = new StringBuffer();
                        for(byte byt : bytes) hexString.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));

                        act.setUserName(userField.getText().toString());
                        act.setPassName(hexString.toString());

                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    //File file = new File(filename);

                    try {
                        //file.mkdirs();
                        //FileOutputStream out = act.openFileOutput(filename, act.MODE_PRIVATE);
                        FileOutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory()+ File.separator + "start/" + filename + ".txt" );
                        BufferedOutputStream writing = new BufferedOutputStream(out);

                        writing.write(act.getUserName().getBytes());
                        writing.write("\n".getBytes());
                        writing.write(act.getPassName().getBytes());

                        writing.flush();
                        out.flush();
                        writing.close();
                        out.close();

                        mCallbacks.onButtonSelected(13);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Thread connect = new Thread() {
                        public void run() {

                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("http://www.cs.uwyo.edu/~cmarker1/login.php");

                            try {
                                // Add your data
                                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                                nameValuePairs.add(new BasicNameValuePair("username", act.getUserName().toString()));
                                nameValuePairs.add(new BasicNameValuePair("password", act.getPassName().toString()));
                                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                                // Execute HTTP Post Request
                                HttpResponse response = httpclient.execute(httppost);
                                HttpEntity entity = response.getEntity();

                            /*InputStream is = response.getEntity().getContent();
                            //convert response to string
                            BufferedReader myReader = new BufferedReader(new InputStreamReader(is,"UTF-16"));
                            StringBuilder sb = new StringBuilder();
                            sb.append(myReader.readLine() + "\n");
                            String line="";
                            while ((line = myReader.readLine()) != null) {
                                sb.append(line + "\n");
                            }*/
                                act.setServerResponse(EntityUtils.toString(entity));

                                //act.serverResponse = EntityUtils.toString(entity);

                            } catch (ClientProtocolException e) {
                                Log.e("log_tag", "ClientProtocol " + e.toString());
                            } catch (IOException e) {
                                Log.e("log_tag", "IO "+e.toString());
                            }

                        }
                    };
                    connect.start();

                    while(act.getServerResponse().equals("")){};


                    //mCallbacks.onButtonSelected(18);


                    if(act.serverResponse.equals("Success")) {
                        mCallbacks.onButtonSelected(0);
                    }
                    else
                    {
                        mCallbacks.onButtonSelected(17);
                    }

                }

                else
                {
                    try {
                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        digest.update(passField.getText().toString().getBytes());

                        byte[] bytes= digest.digest();
                        StringBuffer hexString = new StringBuffer();
                        for(byte byt : bytes) hexString.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));

                        act.setUserName(userField.getText().toString());
                        act.setPassName(hexString.toString());

                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    Thread connect = new Thread() {
                        public void run() {

                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("http://www.cs.uwyo.edu/~cmarker1/login.php");

                            try {
                                // Add your data
                                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                                nameValuePairs.add(new BasicNameValuePair("username", act.getUserName().toString()));
                                nameValuePairs.add(new BasicNameValuePair("password", act.getPassName().toString()));
                                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                                // Execute HTTP Post Request
                                HttpResponse response = httpclient.execute(httppost);
                                HttpEntity entity = response.getEntity();

                            /*InputStream is = response.getEntity().getContent();
                            //convert response to string
                            BufferedReader myReader = new BufferedReader(new InputStreamReader(is,"UTF-16"));
                            StringBuilder sb = new StringBuilder();
                            sb.append(myReader.readLine() + "\n");
                            String line="";
                            while ((line = myReader.readLine()) != null) {
                                sb.append(line + "\n");
                            }*/
                                act.setServerResponse(EntityUtils.toString(entity));

                                //act.serverResponse = EntityUtils.toString(entity);

                            } catch (ClientProtocolException e) {
                                Log.e("log_tag", "ClientProtocol " + e.toString());
                            } catch (IOException e) {
                                Log.e("log_tag", "IO "+e.toString());
                            }

                        }
                    };
                    connect.start();

                    while(act.getServerResponse().equals("")){};


                    mCallbacks.onButtonSelected(18);


                    if(act.serverResponse.equals("Success")) {
                        mCallbacks.onButtonSelected(0);
                    }
                    else
                    {
                        mCallbacks.onButtonSelected(17);
                    }
                }



                //mCallbacks.onButtonSelected(0);

            }
        });

        RegButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCallbacks.onButtonSelected(16);

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
