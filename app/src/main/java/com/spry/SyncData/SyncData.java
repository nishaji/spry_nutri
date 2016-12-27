package com.spry.SyncData;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.Spry.nutritionix.Constants;
import com.spry.model.Sync;
import com.myapplication.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sprydev5 on 9/6/15.
 */
public class SyncData extends Service {
    private static final String TAG = "Sync Service";
    public static String name, age, click_count, activity_name, Gender, time_elapsed, AndroidID, DeviceID, SERIALID;
    private final int INTERVAL = 60 * 1000 * 60 * 24;
    Sync person;
    private Timer timer = new Timer();
 int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE;
    SyncData sync;
    /*
          @Method For the POst of the values to the server
           */
    public static String POST(String url) {
        InputStream inputStream = null;
        String result = "xxx";
        String json = null;
        try {
            HttpParams httpParameters = new BasicHttpParams();
            // Set the timeout in milliseconds until a connection is established.
            // The default value is zero, that means the timeout is not used.
            int timeoutConnection = 4000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT)
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 5000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient(httpParameters);

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            JSONObject jsonObject = new JSONObject();
            JSONObject jsonMain = new JSONObject();
            jsonObject.accumulate("name", name);
            jsonObject.accumulate("Gender", Gender.equals("0") ? "Male" : "Female");
            jsonObject.accumulate("Age", age);
            //jsonObject.accumulate("DOB", person.getDOB());

            // 4. convert JSONObject to JSON to String

            jsonMain.accumulate("Profile", jsonObject);

            JSONObject jsondevice = new JSONObject();
            jsondevice.accumulate("AndroidID", AndroidID);
            jsondevice.accumulate("DeviceID", DeviceID);
            jsondevice.accumulate("SerialNumber", SERIALID);

            jsonMain.accumulate("Device", jsondevice);
            JSONObject jsonApp = new JSONObject();

            jsonApp.accumulate(Constants.Click_Count, click_count);
            jsonApp.accumulate(Constants.Time_Elaspsed, time_elapsed);
            jsonApp.accumulate(Constants.ACTIVITY_NAME, activity_name);
            //jsonObject.accumulate("DOB", person.getDOB());

            // 4. convert JSONObject to JSON to String

            jsonMain.accumulate("AppHistory", jsonApp);


            //jArray.put(1,jsondevice.toString());
            json = jsonMain.toString();
            Log.d("JSON VAl", json);
            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
                Log.d("Result", result);
            } else {
                result = "Did not work!";
                Log.d("Result", result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 11. return result
        return result;
    }

    /*
    @Method for the Convertion of the inputsteram to the String
     */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Service#onCreate() Called by the system when the service
     * is first created. Do not call this method directly.
     */
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        // person=new Sync();


    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        // Display the Toast Message
        Log.e("UserDetails", "Userdetails Called" + getApplicationContext());
        UserDetails();
        AppHistoryActivityDetails();
        Log.e("UserDetails", "Telephonic Message");
        TelphonicMessage();

        // Execute an action after period time
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                // Print a log
                Log.d(TAG, Constants.SYNCURL);

                new HttpAsyncTask().execute(Constants.SYNCURL);
                Log.d(TAG, "Start to do an action");
            }
        }, 0, INTERVAL);
        return super.onStartCommand(intent, flags, startId);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Service#onDestroy() Service is destroyed when user stop
     * the service
     */
    @Override
    public void onDestroy() {
        // Display the Toast Message

        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }

    /*
       Message Toast if There is time out from the server or Internet Connectivity
    */
    public void Message() {
        if (isConnected()) {
            // Toast.makeText(getApplicationContext(), getResources().getString(R.string.servererror), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.checki), Toast.LENGTH_LONG).show();
        }

    }

    /*
            Checking for the Connectivity of the Android Device
     */
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        Log.d("Connection result1", String.valueOf(activeNetwork.isConnectedOrConnecting()));
        Log.d("Connection result2", String.valueOf(activeNetwork));
        Log.d("Connection result3", String.valueOf(isConnected));
        return isConnected;
    }

    /*
    @Method Getting the User Detasils
     */
    private void UserDetails() {
        SharedPreferences sharedPref = this.getSharedPreferences("GDetails", Context.MODE_PRIVATE);

        name = (sharedPref.getString("Name", "x"));

        age = (sharedPref.getString("Age", "x"));

        Gender = (sharedPref.getString("Gender", "x"));
        Log.d("Gender", name + age + Gender);

    }

    /*
    * @Method for the Values of the Code
    * */

    /*
    @Method for the getting the app History
     */
    private void AppHistoryActivityDetails() {

        SharedPreferences sharedPref = this.getSharedPreferences(getApplicationContext().getClass().getSimpleName(), Context.MODE_PRIVATE);

        time_elapsed = (sharedPref.getString(Constants.Time_Elaspsed, "x"));
        activity_name = (sharedPref.getString(Constants.ACTIVITY_NAME, "x"));
        click_count = (sharedPref.getString(Constants.Click_Count, "x"));

        Log.d("Time Elapsed", time_elapsed + activity_name + click_count);

    }

    private void TelphonicMessage() {

        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        AndroidID = (Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));
        DeviceID = (tm.getDeviceId());
        SERIALID = (tm.getSimSerialNumber());
        Log.d("Time Elapsed", AndroidID + DeviceID + SERIALID);

    }

    /*
@AsyncClass for the sending of the values from the client to the server
 */
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            return POST(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();

            // mSignInButton.setEnabled(false);
            if (result.equals("xxx")) {
                Message();
                Log.d("Message point", "11");

            } else {
                // Retrieve some profile information to personalize our app for the user.

            }
        }

    }
}


