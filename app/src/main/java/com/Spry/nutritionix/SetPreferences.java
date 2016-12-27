package com.Spry.nutritionix;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Spry.dev5magic.NoBoringActionBarActivity;
import com.myapplication.R;
public class SetPreferences extends Activity {

	EditText age,weight,height;
	Spinner activity,height_conversion;
	Button btnset;
	RadioGroup gender;
	String[] _activity,_heigt_conversion;
	String _agevalue,_weightvalue,_heightvalue,_activityvalue,_gendervalue,_typeweight;
	TextView tv;
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_preferences);
		ActionBar actionBar=getActionBar(); 
	
		
		    actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar));
			
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			if(Integer.parseInt(android.os.Build.VERSION.SDK)<=18)
		       {
				actionBar.setHomeButtonEnabled(true);
			
		       }
			else
			{
			//actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.back_arrow));
			}
			
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        Typeface roboto_light1=Typeface.createFromAsset(getAssets(), "fonts/Roboto_Light.ttf");
	        actionBar.setCustomView(R.layout.custom_action_bar);
	    	tv=(TextView)findViewById(R.id.actiontitle);
	    	tv.setTypeface(roboto_light1);
	    	tv.setText("My Preferences");
		
		
		btnset=(Button)findViewById(R.id.btnsetview);
		gender=(RadioGroup)findViewById(R.id.radioGender);
		
		age=(EditText)findViewById(R.id.edtage);
		weight=(EditText)findViewById(R.id.edtWeight);
		height=(EditText)findViewById(R.id.edtheight);
	
	activity=(Spinner)findViewById(R.id.Activityset);
	height_conversion=(Spinner)findViewById(R.id.SPI_Test);
	
	
	_activity=new String[]{"Lightly active","Active", "Very Active"};
	_heigt_conversion=new String[]{"Kg","Pound"};
	
	
	 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _activity);
	// Specify the layout to use when the list of choices appears
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// Apply the adapter to the spinner
	
	activity.setAdapter(adapter);
	

	 ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _heigt_conversion);	// Specify the layout to use when the list of choices appears
	adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// Apply the adapter to the spinner
	height_conversion.setAdapter(adapter2);
	
	
	//Now Responding to the user Selection
	
	btnset.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
			
			_agevalue=age.getText().toString();
			_heightvalue=height.getText().toString();
			_weightvalue=weight.getText().toString();
			int x=activity.getSelectedItemPosition();
			switch (x) {
			case 0:
				_activityvalue="Lightly active";
				break;
			case 1:
				_activityvalue="Active";
				break;
			case 2:
				_activityvalue="Very Active";
				break;

			default:
				_activityvalue="Lightly active";
				break;
			}
			
		int 	y=height_conversion.getSelectedItemPosition();

			switch (y) {
			case 0:
				_typeweight="Kg";
				break;
			case 1:
				_typeweight="Pound";
				break;
			
			default:
				_typeweight="Kg";
				break;
			}
			
			Toast.makeText(getApplicationContext(), _agevalue+"\n"+_heightvalue+"\n"+_weightvalue+_typeweight+"\n"+_gendervalue+"\n"
			+_activityvalue,Toast.LENGTH_LONG).show(); 
			
			if(_agevalue.equalsIgnoreCase(null)||_agevalue.equalsIgnoreCase("")||_agevalue.equalsIgnoreCase("\"\"")
				||	_heightvalue.equalsIgnoreCase(null)||_heightvalue.equalsIgnoreCase("")||_heightvalue.equalsIgnoreCase("\"\"")
			||	_weightvalue.equalsIgnoreCase(null)||_weightvalue.equalsIgnoreCase("")||_weightvalue.equalsIgnoreCase("\"\"")
			||	_gendervalue.equalsIgnoreCase(null)||_gendervalue.equalsIgnoreCase("")||_gendervalue.equalsIgnoreCase("\"\"")
			
					)
			{
				Toast.makeText(SetPreferences.this, "Please fill All the entries", Toast.LENGTH_LONG).show();
			}
			else
			{
			Intent intent=new Intent(SetPreferences.this,Thanku.class);
			intent.putExtra("Class","set");
			intent.putExtra("age", _agevalue);
			intent.putExtra("height", _heightvalue);
			intent.putExtra("weight", _weightvalue);
			intent.putExtra("typeofweight", _typeweight);
			intent.putExtra("gender", _gendervalue);
			intent.putExtra("activity", _activityvalue);
			
			startActivity(intent);
			}
		}
	});
	
	}

	
public void findGender(View view)
{
	boolean checked = ((RadioButton) view).isChecked();

// Check which radio button was clicked
	switch(view.getId()) {
    case R.id.rdbtnmal:
        if (checked)
        	_gendervalue="Male";
            // Pirates are the best
        break;
    case R.id.rdbtnfemale:
        if (checked)
            // Ninjas rule
        	_gendervalue="Female";
        break;
    case R.id.rdbtnother:
        if (checked)
        	_gendervalue="Other";
            // Ninjas rule
        break;
    default:
    	_gendervalue="Male";
		break;
}}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_preferences, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
	/*	if (id == R.id.Preferances) {
			Intent intent=new Intent(SetPreferences.this,NoBoringActionBarActivity.class);
        	startActivity(intent);
		}*/
		
		if (id == R.id.main_search) {
			Intent intent=new Intent(SetPreferences.this,NutritionSearch.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			finish();
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}