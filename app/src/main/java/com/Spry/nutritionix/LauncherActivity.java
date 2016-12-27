package com.Spry.nutritionix;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.Spry.dev5magic.NoBoringActionBarActivity;
import com.myapplication.R;
import com.squareup.picasso.Picasso;
public class LauncherActivity extends AppCompatActivity {

    public static final String AVATAR_URL = "http://lorempixel.com/200/200/people/1/";

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    String id;
    private View content;

    String PersonName, email, picurl;
    public static final String MY_PREFS_NAME = "MyPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        SharedPreferences prefs = getSharedPreferences("googleprofile", MODE_PRIVATE);
        PersonName = prefs.getString("personname","personName");
        email=prefs.getString("email","email");
        picurl=prefs.getString("picurl","personPhotoUrl");
        Log.e(PersonName + email + picurl, "hellooooooooooooooooooooooooooooooooooooooooooooo");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        final ImageView avatar = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.avatar);
        System.out.print(avatar);
        Picasso.with(this).load(picurl).transform(new CircleTransform()).into(avatar);
        final TextView personname = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.username);
        personname.setText(PersonName);
        final TextView emaill = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.email);
        emaill.setText(email);
        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.drawer_home) {

                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

                }
                if (menuItem.getItemId() == R.id.drawer_nutrition_search) {
                    /*Intent intent = new Intent(LauncherActivity.this, NutritionSearch.class);
                    startActivity(intent);*/
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new NutritionSearch()).commit();

                }
                if (menuItem.getItemId() == R.id.my_meal) {
                    /*Intent intent = new Intent(LauncherActivity.this, My_Daily_Plans.class);
                    startActivity(intent);*/
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new My_Daily_Plans()).commit();

                }
                if (menuItem.getItemId() == R.id.meal_plans) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new NoBoringActionBarActivity()).commit();

                }
                if (menuItem.getItemId() == R.id.daily_goal) {
                    /*Intent intent = new Intent(LauncherActivity.this, Daily_Goal.class);
                    startActivity(intent);*/
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new Daily_Goal()).commit();

                }
                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.spry_toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

       if (id == R.id.main_home) {
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }*/
}