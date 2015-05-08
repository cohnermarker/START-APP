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
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Spencer on 2/22/2015.
 */
public class NewUserFrag extends Fragment{


    private Callbacks mCallbacks = sDummyCallbacks;


    public interface Callbacks {

        public void onButtonSelected(int id);
    }

    public NewUserFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_newuser, container, false);



        Button regButton = (Button) myView.findViewById(R.id.regButton);

        TextView userText = (TextView) myView.findViewById(R.id.userText);
        TextView passText = (TextView) myView.findViewById(R.id.passText);
        TextView confirmText = (TextView) myView.findViewById(R.id.confirmText);

        final EditText userField = (EditText) myView.findViewById(R.id.userField);
        final EditText passField = (EditText) myView.findViewById(R.id.passField);
        final EditText confirmField = (EditText) myView.findViewById(R.id.confirmField);
        final MainActivity act = ((MainActivity)getActivity());




        regButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                act.setUserName(userField.getText().toString());
                act.setPassName(passField.getText().toString());
                String confName = confirmField.getText().toString();

                if (act.getUserName().equals("") || act.getPassName().equals("") || confName.equals(""))
                {
                    mCallbacks.onButtonSelected(12);
                }
                else if(!act.getPassName().equals(confName))
                {
                    mCallbacks.onButtonSelected(17);
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
                            HttpPost httppost = new HttpPost("http://www.cs.uwyo.edu/~cmarker1/register.php");

                            try {
                                // Add your data
                                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                                nameValuePairs.add(new BasicNameValuePair("userName", act.getUserName().toString()));
                                nameValuePairs.add(new BasicNameValuePair("passName", act.getPassName().toString()));
                                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                                // Execute HTTP Post Request
                                HttpResponse response = httpclient.execute(httppost);
                                HttpEntity entity = response.getEntity();
                                //is = entity.getContent();

                            } catch (ClientProtocolException e) {
                                Log.e("log_tag", "ClientProtocol " + e.toString());
                            } catch (IOException e) {
                                Log.e("log_tag", "IO "+e.toString());
                            }

                        }
                    };
                    connect.start();

                    String filename = "shadow";


                    try {
                        FileOutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "start/" + filename + ".txt");
                        BufferedOutputStream writing = new BufferedOutputStream(out);

                        writing.write(act.getUserName().getBytes());
                        writing.write("\n".getBytes());
                        writing.write(act.getPassName().getBytes());

                        writing.flush();
                        out.flush();
                        writing.close();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    mCallbacks.onButtonSelected(0);
                }
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
