package com.spry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;

import com.Spry.nutritionix.FirstLaunchingTutorial;
import com.Spry.nutritionix.LauncherActivity;
import com.Spry.nutritionix.NutritionSearch;
import com.spry.nutrition.sign.Gsign;
import com.myapplication.R;

public class MainActivity extends Activity {
    public static final String MyPrefs = "MyPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences settings = getSharedPreferences(Gsign.PREFS_NAME, 0);
        SharedPreferences sp = getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
        if (!sp.getBoolean("first", false)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("first", true);
            editor.commit();
            Intent intent = new Intent(this, FirstLaunchingTutorial.class);
            startActivity(intent);
        } else if (hasLoggedIn) {
            Intent intent = new Intent(MainActivity.this, LauncherActivity.class);
            startActivity(intent);
        } else {
            Thread timerThread = new Thread() {
                public void run() {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent intent = new Intent(MainActivity.this, Gsign.class);
                        startActivity(intent);
                    }
                }
            };
            timerThread.start();
        }
    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}


