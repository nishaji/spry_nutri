package com.Spry.nutritionix;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.Spry.dev5magic.NoBoringActionBarActivity;
import com.myapplication.R;
import com.spry.SyncData.SyncData;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;


public class NutritionSearch extends Fragment {
    static int ClickCount = 0, Timeelapsed = 0;
    EditText search;
    Button btngo;
    TextView tvdesclogo, tvactionbartitle;
    ImageView imageView;
    String url = "";
    JSONArray contacts = null;
    String responseString = "";
    Button mealplans, btnmymeals, btndailygoal;
    String View_name = "null";
    long tStart;
    ProgressDialog pDialog;
    String name,age,Gender,time_elapsed,click_count,view_name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_nutrition_search, container, false);
        setHasOptionsMenu(true);


        tStart = System.currentTimeMillis();
        UserDetails();
        Log.d("Values of Profile", name + age + Gender);

        imageView = (ImageView)v. findViewById(R.id.imagecutlaunch);

        tvdesclogo = (TextView)v. findViewById(R.id.serving_size_grams);

        Typeface robotoThin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto_Thin.ttf");
        Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto_Light.ttf");

        tvdesclogo.setTypeface(robotoLight);

        search = (EditText)v. findViewById(R.id.edtSearch);
        search.setTypeface(robotoThin);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        search.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub
                if ((event != null && ((actionId == EditorInfo.IME_ACTION_GO) || (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)))) {
                    String k = search.getText().toString().replace(' ', '+');
                    InputMethodManager mgr = (InputMethodManager)getActivity(). getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(search.getWindowToken(), 0);
                    Log.i("", "**********************************");
                    //  url = "https://api.nutritionix.com/v1_1/search/"+ k +"?results=0:20&fields=item_name,brand_name,item_id,nf_calories,nf_sodium,nf_serving_weight_grams,nf_total_fat,nf_calories,item_type,nf_serving_size_unit,nf_serving_size_qty,nf_calories_from_fat,nf_saturated_fat,nf_polyunsaturated_fat,nf_cholesterol,nf_total_carbohydrate,nf_protein,nf_vitamin_a_dv,nf_vitamin_c_dv,nf_calcium_dv,nf_iron_dv,nf_dietary_fiber,nf_sugars&appId=2031ec0d&appKey=a3af5e3ea7aaecf88251adac3da83b02";
                   // url = "http://107.155.92.24/api/" + k + "?results=0:20&id=54ae3b63a8ec52f218272689&key=xxxx";
                    int limit=20;
                    url="http://192.168.1.107:3003/api/searchitems/search?limit="+limit+"&searchitem="+k;

                    Log.d("URL", url);
                    if (search.getText().toString().equals(null) || search.getText().toString().equals("")) {
                        Toast.makeText(NutritionSearch.this.getActivity(), "Please Enter some Values to search", Toast.LENGTH_LONG).show();
                    } else {
                        if (isConnectingToInternet() == true) {
                            try {
                                new Search().execute(url);
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "Please enter a proper Item ", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Message();
                            Log.d("Message point", "2");
                        }
                    }
                }


                return false;
            }
        });

        btngo = (Button)v. findViewById(R.id.btnsearch);

        btngo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ClickCount++;

                View_name = btngo.getText().toString();
                // TODO Auto-generated method stub
                String k = search.getText().toString().replace(' ', '+');
                System.out.println("kkkkkkkkkk" + k);
                InputMethodManager mgr = (InputMethodManager)getActivity(). getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(search.getWindowToken(), 0);

                //  url = "https://api.nutritionix.com/v1_1/search/"+ k +"?results=0:20&fields=item_name,brand_name,item_id,nf_calories,nf_sodium,nf_serving_weight_grams,nf_total_fat,nf_calories,item_type,nf_serving_size_unit,nf_serving_size_qty,nf_calories_from_fat,nf_saturated_fat,nf_polyunsaturated_fat,nf_cholesterol,nf_total_carbohydrate,nf_protein,nf_vitamin_a_dv,nf_vitamin_c_dv,nf_calcium_dv,nf_iron_dv,nf_dietary_fiber,nf_sugars&appId=2031ec0d&appKey=a3af5e3ea7aaecf88251adac3da83b02";
               // url = "http://107.155.92.24/api/" + k + "?results=0:20&id=54ae3b63a8ec52f218272689&key=xxxx";
                int limit=20;
                url="http://192.168.1.107:3003/api/searchitems/search?limit="+limit+"&searchitem="+k;

                Log.d("URL", url);

                if (search.getText().toString().equals(null) || search.getText().toString().equals("")) {
                    Toast.makeText(NutritionSearch.this.getActivity(), "Please Enter some Values to search", Toast.LENGTH_LONG).show();
                } else {
                    if (isConnectingToInternet() == true) {
                        try {
                            new Search().execute(url);
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Please enter a proper Item ", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Message();
                        Log.d("Message point", "3");
                    }
                }
            }
        });
        return v;
    }

	/*
	 * @method to set the button my plans
	 */


    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        CreatePreferences();
    }

    //@Method for  the creation of the Shared Preferences
    public void CreatePreferences() {

        File f = new File("/data/data/" + getActivity() + "/shared_prefs/Hello.xml");

        if (f.exists()) {
            Log.d("TAG", "SharedPreferences Name_of_your_preference : exist");
        } else {
            SharedPreferences sharedPref = this.getActivity().getSharedPreferences("Hello", Context.MODE_PRIVATE);
            Map<String, ?> keys;
            keys = sharedPref.getAll();
            if (keys.entrySet().size() > 0) {
                Log.d("TAG", "Shared Pref Not Done");
            } else {

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("cat0", "BreakFast");
                editor.putString("cat1", "Lunch");
                editor.putString("cat2", "Dinner");
                editor.apply();
                Log.d("TAG", "SharedPreferences Recreated");
            }
        }
        File position = new File("/data/data/" + getActivity() + "/shared_prefs/Position.xml");

        if (position.exists()) {
            Log.d("TAG", "SharedPreferences Name_of_your_preference : exist");
        } else {
            SharedPreferences sharedPref = this.getActivity().getSharedPreferences("Position", Context.MODE_PRIVATE);
            Map<String, ?> keys;
            keys = sharedPref.getAll();
            if (keys.entrySet().size() > 0) {
                Log.d("TAG", "Shared Pref Not Done");
            } else {

                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString("cat0", "0");
                editor.putString("cat1", "1");
                editor.putString("cat2", "2");
                editor.apply();

                Log.d("TAG", "SharedPreferences Position Created");
            }
        }


    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        try {
            if ((pDialog != null) && pDialog.isShowing()) {
                pDialog.dismiss();
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {

            pDialog = null;
        }
        if (setContextData()) {
            setContextData();
            UserDetails();
            Log.d("Values of Profile",name+age+Gender);
            Intent intent = new Intent(this.getActivity(), SyncData.class);
            // add infos for the service which file to download and where to store

            //URL Sent To Write the Datas
            intent.putExtra(Constants.SYNCURLS,
                    Constants.SYNCURL);
            //About Aoo History Send
            Log.d("Values are", this.getActivity().getLocalClassName() + String.valueOf(Timeelapsed) + String.valueOf(ClickCount) + name +age+Gender);
            getActivity().startService(intent);

        }
        super.onPause();
    }
    //Getting the User Details
    private void UserDetails() {
        Log.e("Values are", "/data/data/" + getActivity() + "/shared_prefs/GDetails.xml");
        File f = new File("/data/data/" + getActivity() + "/shared_prefs/GDetails.xml");
        if (f.exists()) {

            SharedPreferences sharedPref = getActivity().getSharedPreferences("GDetails", Context.MODE_PRIVATE);

            name = sharedPref.getString("Name", "x");

            age = sharedPref.getString("Age", "x");

            Gender = sharedPref.getString("Gender", "x");
            Log.d("Gender", name+age+Gender);
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        //Now Creating the History of the Data

    }

    //Setting New Preferences to store the Data's related to the userAppUse
    public Boolean setContextData() {
        Boolean status = false;

        File f = new File("/data/data/" + getActivity() + "/shared_prefs/" + Constants.APPLICATION + ".xml" );

        if (f.exists()) {
            Log.d("TAG", "SharedPreferences Name_of_your_preference : exist");
            SharedPreferences sharedPref = this.getActivity().getSharedPreferences(Constants.APPLICATION, Context.MODE_PRIVATE);
            String strelapsedtime = sharedPref.getString(Constants.Time_Elaspsed, "null");
            long tEnd = System.currentTimeMillis();
            long tDelta = tEnd - tStart;
            double elapsedSeconds = (tDelta / 1000.0) + Double.parseDouble(strelapsedtime);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Constants.Time_Elaspsed, String.valueOf(elapsedSeconds));
            editor.apply();
            status = true;

        } else {
            long tEnd = System.currentTimeMillis();
            long tDelta = tEnd - tStart;
            double elapsedSeconds = tDelta / 1000.0;

            SharedPreferences sharedPref = this.getActivity().getSharedPreferences(getActivity().getClass().getSimpleName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Constants.ACTIVITY_NAME, getActivity().getClass().getSimpleName());
            editor.putString(Constants.View_Name, View_name);
            editor.putString(Constants.Click_Count, String.valueOf(ClickCount));
            editor.putString(Constants.Time_Elaspsed, String.valueOf(elapsedSeconds));
            editor.apply();
            status = true;
        }
        return status;
    }


    private void Message() {
        // TODO Auto-generated method stub
        Toast.makeText(this.getActivity(), "Check Your Internet Connection", Toast.LENGTH_LONG).show();
    }
/*
 * @method for checking of the Devise Internet Connections
 */

    public boolean isConnectingToInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        Log.d("Connection result1", String.valueOf(activeNetwork.isConnectedOrConnecting()));
        Log.d("Connection result2", String.valueOf(activeNetwork));
        Log.d("Connection result3", String.valueOf(isConnected));
        return isConnected;
    }
    /*
     * @Method for the clearing of the Search Box
     */
    public void clearText(View v) {
        search.setText("");
        imageView.setVisibility(View.GONE);
    }

    /*
     * @Async  class to get the Json from the Api
     */
    public class Search extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(NutritionSearch.this.getActivity(), AlertDialog.THEME_HOLO_DARK);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(true);
            pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.styeprogress));

            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub


            HttpParams httpParameters = new BasicHttpParams();
            // Set the timeout in milliseconds until a connection is established.
            // The default value is zero, that means the timeout is not used.
            int timeoutConnection = 4000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT)
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 5000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            // Creating service handler class instance
            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpResponse response;
            try {
                response = httpclient.execute(new HttpGet(params[0]));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();
                    Log.d("Values are here", responseString);
                } else {
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                if ((pDialog != null) && pDialog.isShowing()) {
                    pDialog.dismiss();
                }
            } catch (final IllegalArgumentException e) {
                // Handle or log or ignore
            } catch (final Exception e) {
                // Handle or log or ignore
            } finally {

                pDialog = null;
            }
            if (responseString.length() == 0) {
                Message();
                Log.d("Message point", "1");
            } else {
                Intent intent = new Intent(NutritionSearch.this.getActivity(), Go.class);
                intent.putExtra("search", search.getText().toString());
                intent.putExtra("Json", responseString);
                startActivity(intent);
            }


        }
    }
}