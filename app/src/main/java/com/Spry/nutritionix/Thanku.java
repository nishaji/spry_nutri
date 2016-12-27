package com.Spry.nutritionix;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.spry.database.DbAdapter;
import com.myapplication.R;
public class Thanku extends Activity {

	String _age,_height,_weight,_typeofweight,_gender,_activity_type;
	DbAdapter adapter;
	TextView calval,tvpro,sodval,tvcalcium,tvcarbs,fatval,tviron,tvviA,tvvitC,tv;
	Button set;
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thanku);
		
		
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
    	tv.setText("My Diet");

		
		
		
		
		
		
		adapter=new DbAdapter(this);
		adapter.open();
		calval=(TextView)findViewById(R.id.calorierequiredval);
		tvpro=(TextView)findViewById(R.id.proteinsreqval);
		sodval=(TextView)findViewById(R.id.sodreqval);
		tvcalcium=(TextView)findViewById(R.id.calreqval);
		tvcarbs=(TextView)findViewById(R.id.carbsrequval);
		fatval=(TextView)findViewById(R.id.fatrequval);
		tviron=(TextView)findViewById(R.id.ironrequval);
		tvviA=(TextView)findViewById(R.id.vitareqval);
		tvvitC=(TextView)findViewById(R.id.vitcreqval);
		set=(Button)findViewById(R.id.btnfinalsetpreferences);
		
		
		Intent intent=getIntent();
		
		if(intent.getStringExtra("Class").equals("set"))
		{    set.setVisibility(View.VISIBLE);
			_age=intent.getStringExtra("age");
			_height=intent.getStringExtra("height");
			_weight=intent.getStringExtra("weight");
			_typeofweight=intent.getStringExtra("typeofweight");
			_gender=intent.getStringExtra("gender");
			_activity_type=intent.getStringExtra("activity");
		
			Log.i("Hello how are u bro ", 
				
				_age+_height+_weight+_typeofweight+_gender+_activity_type
				
				
				);
			SetCalories(_age,_gender,_activity_type);
	
	
			SetSodium(_age);
			doAll(_age,_gender);
		
			set.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Congratulation All Set", Toast.LENGTH_LONG).show();
			
				int position=1;
				String calories,pro,sodium,carbs,iron,fat,calcium,vitA,vitC;
				calories=calval.getText().toString();
				pro=tvpro.getText().toString();
				sodium=sodval.getText().toString();
				carbs=tvcarbs.getText().toString();
				iron=tviron.getText().toString();
				fat=fatval.getText().toString();
				calcium=tvcalcium.getText().toString();
				vitA=tvviA.getText().toString();
				vitC=tvvitC.getText().toString();
				try 
				{
			Cursor cursor;
			cursor=adapter.fetch_allParameters();
			if(cursor.getCount()>=1)
			{
				adapter.updateParameters(position, calories, pro, sodium, carbs, iron, fat, calcium, vitA, vitC);
			}
			else
			{
				adapter.createParameter(calories, pro, sodium, carbs, iron, fat, calcium, vitA, vitC);
			}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				
				Intent intent =new Intent(Thanku.this,NutritionSearch.class);
				startActivity(intent);
			}
			});
			}
		else
		{
			set.setVisibility(View.GONE);
			
			Cursor cursor=adapter.fetch_allParameters();
			  if(cursor.getCount()>0)
		        {
				  if(cursor.moveToFirst())

					{ do

					{
						calval.setText(cursor.getString(cursor.getColumnIndex("calorie")));
						tvpro.setText(cursor.getString(cursor.getColumnIndex("protein")));
						sodval.setText(cursor.getString(cursor.getColumnIndex("sodium")));
						tvcarbs.setText((cursor.getString(cursor.getColumnIndex("carbs"))));
						
						fatval.setText(cursor.getString(cursor.getColumnIndex("fat")));
						tviron.setText(cursor.getString(cursor.getColumnIndex("iron")));
						tvcalcium.setText(cursor.getString(cursor.getColumnIndex("calcium")));
						tvviA.setText(cursor.getString(cursor.getColumnIndex("vitA")));
						tvvitC.setText(cursor.getString(cursor.getColumnIndex("vitC")));
					
					  
				  }while(cursor.moveToNext());
				  cursor.close();
		        }
			 
		        } else
				  {

				  }
		}
	}

	private void doAll(String _age2, String _gender2) {
		
		// TODO Auto-generated method stub
        int iage=Integer.parseInt(_age2);


        String xgender=_gender2;
       
         if(iage<3)
         {
                 tviron.setText("7");
                 tvcalcium.setText("500");
                 tvviA.setText("300");
                 tvvitC.setText("400");
                 tvpro.setText("13");

                 tvcarbs.setText("14");

         }

         if(iage>=3 && iage<9)
         {
                 tvpro.setText("19");
                 tvcalcium.setText("800");
                 tvviA.setText("400");
                 tvvitC.setText("650");
         if(xgender.equalsIgnoreCase("male")||xgender.equalsIgnoreCase("other"))
         {      tviron.setText("10");
                tvcarbs.setText("20");
         }
         else
         {
                 tvcarbs.setText("17");
                 tviron.setText("10");
         }

         }
         if(iage>=9 && iage<14)
         {
                 tvpro.setText("34");
                 tvvitC.setText("1200");
                 tvviA.setText("600");
                 tvcalcium.setText("1300");
                 if(xgender.equalsIgnoreCase("male")||xgender.equalsIgnoreCase("other"))
         {      tvcarbs.setText("25");
                 tviron.setText("8");
         }
                else
                {
                        tvcarbs.setText("22");
                         tviron.setText("8");
                }

         }

         if(iage>=14 && iage<19)
         {
                 tvpro.setText("46");
                 tvvitC.setText("1800");
                 tvcalcium.setText("1300");
                 if(xgender.equalsIgnoreCase("male")||xgender.equalsIgnoreCase("other"))
         {
                         tvcarbs.setText("31");
                         tvviA.setText("900");
                         tviron.setText("11");
         }
         else
         {       tvcarbs.setText("25");
                 tvviA.setText("700");
                 tviron.setText("15");
         }
         }
         if(iage>=19 && iage<31)

         {
                 tvpro.setText("50");
                 tvvitC.setText("2000");
                 tvcalcium.setText("1000");
                 if(xgender.equalsIgnoreCase("male")||xgender.equalsIgnoreCase("other"))
                 {
                         tvcarbs.setText("34");
                         tvviA.setText("900");
                         tviron.setText("8");
                 }
         else
         {
                 tvcarbs.setText("28");
                 tvviA.setText("700");
                 tviron.setText("18");
         }

         }
         if(iage>=31 && iage<51)
         {
                 tvpro.setText("50");
                 tvvitC.setText("2000");
                 tvcalcium.setText("1000");
                 if(xgender.equalsIgnoreCase("male")||xgender.equalsIgnoreCase("other"))
                 {
                         tvcarbs.setText("31");
                         tvviA.setText("900");
                         tviron.setText("8");
                 }
         else

                 {
                        tvpro.setText("46");
                        tviron.setText("18");
                        tvcarbs.setText("25");
                        tvviA.setText("700");
                 }
         }
         if(iage>=51)
         {
                 tvpro.setText("50");
                 tvvitC.setText("2000");
                 tviron.setText("8");
                 tvcalcium.setText("1200");
                 if(xgender.equalsIgnoreCase("male")||xgender.equalsIgnoreCase("other"))
                 {
                         tvviA.setText("900");
                         tvcarbs.setText("28");
                 }
         else

                 {
                        tvcarbs.setText("28");
                        tvviA.setText("700");
                 }

         }

	}

	private void SetSodium(String _age2) {
		// TODO Auto-generated method stub
		int iage=Integer.valueOf((Math.round(Float.valueOf(_age2))));
		if(iage<12)
			sodval.setText("2200");
		else
			sodval.setText("2400");
	}

	

	private void SetCalories(String _age1, String _gender1,
			String _activity_type1) {
		// TODO Auto-generated method stub
		
		
		int iage=Integer.valueOf((Math.round(Float.valueOf(_age1))));
		Log.i("We are in calories",_age1); 
		if(iage<3)
		{		
			if(_gender1.equalsIgnoreCase("Male")||_gender1.equalsIgnoreCase("Female")||_gender1.equalsIgnoreCase("Other"))
			{
				if( _activity_type1.equalsIgnoreCase("lightly active")){
					fatval.setText("50");
					calval.setText("1000");
					
				}
				
				else if( _activity_type1.equalsIgnoreCase("active"))
					{  
					fatval.setText("50");
				
				    	calval.setText("1200");
					}
				else 
					{
					fatval.setText("50");
				
				    	calval.setText("1400");
					}
			}
		}
		
		
		if(iage>=3 )
			
		{
			if(iage<9)
			{
			if(_gender1.equalsIgnoreCase("Male")||_gender1.equalsIgnoreCase("Other"))
			{
				if( _activity_type1.equalsIgnoreCase("lightly active"))
				{		fatval.setText("50");
					
					calval.setText("1400");
				}
				else if( _activity_type1.equalsIgnoreCase("active"))
				{
					fatval.setText("50");
					
					calval.setText("1600");
				}
				else 
					{
					fatval.setText("65");
					
					calval.setText("2000");
					}
			}
			if(_gender1.equalsIgnoreCase("Female"))
			{
				if( _activity_type1.equalsIgnoreCase("lightly active"))
				{	
					fatval.setText("50");
					
					calval.setText("1200");
				}
				else if( _activity_type1.equalsIgnoreCase("active"))
					{
					fatval.setText("50");
					
					calval.setText("1600");
					}
				else 
					{
					fatval.setText("65");
					
					calval.setText("1800");
					}
			}
			}	
		}
		
		if(iage>=9)
		{
			if(iage<14)
			{
				
			
			if(_gender1.equalsIgnoreCase("Male")||_gender1.equalsIgnoreCase("Other"))
			{
				if( _activity_type1.equalsIgnoreCase("lightly active"))
				{
					fatval.setText("65");
					
					calval.setText("1800");
				}
				else if( _activity_type1.equalsIgnoreCase("active"))
					{
					fatval.setText("70");
					
					calval.setText("2200");
					}
				else 
				{
					fatval.setText("75");
					
					calval.setText("2600");
				}
				
			}
			if(_gender1.equalsIgnoreCase("Female"))
			{
				if( _activity_type1.equalsIgnoreCase("lightly active"))
				{
					fatval.setText("50");
					
					calval.setText("1600");
				}
				else if( _activity_type1.equalsIgnoreCase("active"))
					{
					fatval.setText("65");
					
					calval.setText("2000");
					}
				else 
				{
					fatval.setText("75");
					
					calval.setText("2200");
				}
			}
			}
		}
			if(iage>=14 )
			{
				if(iage<19)
				{
				if(_gender1.equalsIgnoreCase("Male")||_gender1.equalsIgnoreCase("Other"))
				{
					if( _activity_type1.equalsIgnoreCase("lightly active"))
					{
						fatval.setText("75");
					
						calval.setText("2200");
					}
					else if( _activity_type1.equalsIgnoreCase("active"))
						{
						fatval.setText("80");
					
						calval.setText("2800");
						}
					else 
						{
						fatval.setText("95");
					
						calval.setText("3200");
						}
				}
				if(_gender1.equalsIgnoreCase("Female"))
				{
					if( _activity_type1.equalsIgnoreCase("lightly active"))
					{
						fatval.setText("55");
					
						calval.setText("1800");
					}
					else if( _activity_type1.equalsIgnoreCase("active"))
					{
						fatval.setText("65");
					
						calval.setText("2000");
					}
					else 
					{
						fatval.setText("80");
					
						calval.setText("2400");
					}
				}
			}	
			}	
			if(iage>=19 )
			{
				if(iage<31)
				{
				if(_gender1.equalsIgnoreCase("Male")||_gender1.equalsIgnoreCase("Other"))
				{
					if( _activity_type1.equalsIgnoreCase("lightly active"))
					{
						fatval.setText("75");
					
						calval.setText("2400");
					}
					else if( _activity_type1.equalsIgnoreCase("active"))
						{
						fatval.setText("95");
					
						calval.setText("2800");
						}
					else 
						{
						fatval.setText("95");
					
						calval.setText("3000");
						}
				}
				if(_gender1.equalsIgnoreCase("Female"))
				{
					if( _activity_type1.equalsIgnoreCase("lightly active"))
					{
						fatval.setText("75");
					
						calval.setText("2000");
					}
					else if( _activity_type1.equalsIgnoreCase("active"))
						{
						fatval.setText("75");
					
						calval.setText("2200");
						}
					else 
					{
						fatval.setText("80");
					
						calval.setText("2400");
					}
				}
			}	
			}
			if(iage>=31)
			{
				if( iage<51)
				{
				if(_gender1.equalsIgnoreCase("Male")||_gender1.equalsIgnoreCase("Other"))
				{
					if( _activity_type1.equalsIgnoreCase("lightly active"))
					{	
						fatval.setText("75");
					
						calval.setText("2200");
					}
					else if( _activity_type1.equalsIgnoreCase("active"))
						{
						fatval.setText("80");
					
						calval.setText("2600");
						}
					else 
						{
						fatval.setText("95");
					
						calval.setText("3000");
						}
				}
				if(_gender1.equalsIgnoreCase("Female"))
				{
					if( _activity_type1.equalsIgnoreCase("lightly active"))
					{
						fatval.setText("60");
					
						calval.setText("1800");
					}
					else if( _activity_type1.equalsIgnoreCase("active"))
						{
						fatval.setText("65");
					
						calval.setText("2000");
						}
					else 
						{
						fatval.setText("75");
					
						calval.setText("2200");
						}
				}
				}
			}
				if(iage>=51)
				{
					if(_gender1.equalsIgnoreCase("Male")||_gender1.equalsIgnoreCase("Other"))
					{
						if( _activity_type1.equalsIgnoreCase("lightly active"))
						{
							fatval.setText("75");
					
							calval.setText("2000");
						}
						else if( _activity_type1.equalsIgnoreCase("active"))
						{
							fatval.setText("80");
					
							calval.setText("2400");
						}
						else 
							{
							fatval.setText("95");
					
							calval.setText("2800");
							}
					}
					if(_gender1.equalsIgnoreCase("Female"))
					{
						if( _activity_type1.equalsIgnoreCase("lightly active"))
						{
							fatval.setText("50");
					
							calval.setText("1600");
						}
						else if( _activity_type1.equalsIgnoreCase("active"))
							{
							fatval.setText("60");
					
							calval.setText("1800");
							}
						else 
							{
							fatval.setText("75");
					
							calval.setText("2200");
							}
					}
				}	
				
			}
		
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thanku, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
	
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent=new Intent(Thanku.this,NutritionSearch.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        	startActivity(intent);
		}
		if (id == R.id.saved_pref) {
			Intent intent=new Intent(Thanku.this,SetPreferences.class);
        	startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
}