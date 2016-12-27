package com.Spry.nutritionix;

import java.io.File;
import java.util.Map;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.myapplication.R;
import com.spry.database.DbAdapter;


public class Daily_Goal extends Fragment{
	String _age,_height="",_weight="",_typeofweight="",_gender="",_activity_type="",_class="";
	DbAdapter adapter;
	String textgoal="Set goal";
	String dialogtext="Default goal";
	TextView calval,tvpro,sodval,tvcalcium,tvcarbs,fatval,tviron,tvviA,tvvitC,tv,tvdesclaimer;
	Button set;
	View setgoal,viewgoal;
	EditText age,weight,height;
	Spinner activity,height_conversion;
	Button btnset;
	RadioGroup gender;
	String[] _activity,_heigt_conversion;
	String _agevalue="",_weightvalue="",_heightvalue="",_activityvalue="",_gendervalue="",_typeweight="";
	static Map<String,?> keys;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v= inflater.inflate(R.layout.activity_daily__goal, container, false);
		setHasOptionsMenu(true);
		return v;

	}
	@Override
	public void onViewCreated (View view, Bundle savedInstanceState) {
		setgoal=getView().findViewById(R.id.setpref);
		viewgoal=getView().findViewById(R.id.viewpref);
		SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("Daily_Goal", Context.MODE_PRIVATE);
		textgoal=sharedPreferences.getString("view", textgoal);
		dialogtext=sharedPreferences.getString("dialog", dialogtext);
		ThankIds();
		SetPrefIds();
	}

	private void SetPrefIds() {
		// TODO Auto-generated method stub
		btnset=(Button)getView().findViewById(R.id.btnsetview);
		gender=(RadioGroup)getView().findViewById(R.id.radioGender);

		gender.setOnClickListener(new OnClickListener() {

									  @Override
									  public void onClick(View v) {
										  boolean checked = ((RadioButton) v).isChecked();

										  // Check which radio button was clicked
										  switch (v.getId()) {
											  case R.id.rdbtnmal:
												  if (checked)
													  _gendervalue = "Male";
												  // Pirates are the best
												  break;
											  case R.id.rdbtnfemale:
												  if (checked)
													  // Ninjas rule
													  _gendervalue = "Female";
												  break;
											  case R.id.rdbtnother:
												  if (checked)
													  _gendervalue = "Other";
												  // Ninjas rule
												  break;
											  default:
												  _gendervalue = "Male";
												  break;
										  }
									  }
								  });


		age=(EditText)getView().findViewById(R.id.edtage);
		weight=(EditText)getView().findViewById(R.id.edtWeight);
		height=(EditText)getView().findViewById(R.id.edtheight);

		age.setText("");
		height.setText("");
		weight.setText("");

		activity=(Spinner)getView().findViewById(R.id.Activityset);
		height_conversion=(Spinner)getView().findViewById(R.id.SPI_Test);
		_activity=new String[]{"Lightly active","Active", "Very Active"};
		_heigt_conversion=new String[]
				{"Kg","Pound"
				};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, _activity);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner

		activity.setAdapter(adapter);


		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, _heigt_conversion);	// Specify the layout to use when the list of choices appears
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
				_gendervalue = ((RadioButton)getView().findViewById(gender.getCheckedRadioButtonId())).getText().toString();
                  System.out.println("gendervalueeee11"+_gendervalue);
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

				Toast.makeText(getContext(), _agevalue+"\n"+_heightvalue+"\n"+_weightvalue+_typeweight+"\n"+_gendervalue+"\n"
						+_activityvalue,Toast.LENGTH_LONG).show();

				if(_agevalue.equalsIgnoreCase(null)||_agevalue.equalsIgnoreCase("")||_agevalue.equalsIgnoreCase("\"\"")
						||	_heightvalue.equalsIgnoreCase(null)||_heightvalue.equalsIgnoreCase("")||_heightvalue.equalsIgnoreCase("\"\"")
						||	_weightvalue.equalsIgnoreCase(null)||_weightvalue.equalsIgnoreCase("")||_weightvalue.equalsIgnoreCase("\"\"")
						||	_gendervalue.equalsIgnoreCase(null)||_gendervalue.equalsIgnoreCase("")||_gendervalue.equalsIgnoreCase("\"\"")

						)
				{
					Toast.makeText(Daily_Goal.this.getActivity(), "Please fill All the entries", Toast.LENGTH_LONG).show();
				}
				else
				{
					_age=_agevalue;
					_class="set";
					_height=_heightvalue;
					_weight=_weightvalue;
					_typeofweight=_typeweight;
					_gender=_gendervalue;
					System.out.println(_gender+"gggggggggeeeeeennnnnnddddeeeeeer");
					_activity_type=_activityvalue;

					ThankIds();
					setgoal.setVisibility(View.GONE);
					viewgoal.setVisibility(View.VISIBLE);
				}
			}
		});

	}


	private void ThankIds() {
		// TODO Auto-generated method stub
		adapter=new DbAdapter(this.getContext());
		adapter.open();
		calval=(TextView)getView().findViewById(R.id.calorierequiredval);
		tvpro=(TextView)getView().findViewById(R.id.proteinsreqval);
		sodval=(TextView)getView().findViewById(R.id.sodreqval);
		tvcalcium=(TextView)getView().findViewById(R.id.calreqval);
		tvcarbs=(TextView)getView().findViewById(R.id.carbsrequval);
		fatval=(TextView)getView().findViewById(R.id.fatrequval);
		tviron=(TextView)getView().findViewById(R.id.ironrequval);
		tvviA=(TextView)getView().findViewById(R.id.vitareqval);
		tvvitC=(TextView)getView().findViewById(R.id.vitcreqval);

		tvdesclaimer=(TextView)getView().findViewById(R.id.tvdesclaimer);

		set=(Button)getView().findViewById(R.id.btnfinalsetpreferences);


		if(_class.equals("set"))
		{
			set.setVisibility(View.VISIBLE);
			set.setText("set new goal");

			//@Methods to calculate all the related categories
			SetCalories(_age,_gender,_activity_type);

			SetSodium(_age);
			doAll(_age,_gender);

			// TODO Auto-generated method stub

			Toast.makeText(getContext(), "Congratulation All Set", Toast.LENGTH_LONG).show();

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
				System.out.println(cursor+"currrssoorrr");
				if(cursor.getCount()>=1)
				{
					adapter.updateParameters(position, calories, pro, sodium, carbs, iron, fat, calcium, vitA, vitC);

				}
				else
				{
					adapter.createParameter(calories, pro, sodium, carbs, iron, fat, calcium, vitA, vitC);
					dialogtext="My Goal";
					textgoal="set new goal";
					File f = new File("/data/data/"+getContext()+"/shared_prefs/Daily_Goal.xml");

					if (f.exists())
					{
						Log.d("TAG", "SharedPreferences Name_of_your_preference : exist");
					}
					else
					{
						SharedPreferences sharedPref = Daily_Goal.this.getActivity().getSharedPreferences("Daily_Goal",Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPref.edit();
						editor.putString("dialog", dialogtext);
						editor.putString("view", textgoal);
						editor.commit();
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}



			Log.i("Hello how are u bro ",

					_age+_height+_weight+_typeofweight+_gender+_activity_type


			);

			set.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					setgoal.setVisibility(View.VISIBLE);
					viewgoal.setVisibility(View.GONE);
				}

			});


		}
		else
		{
			set.setVisibility(View.VISIBLE);
			set.setText(textgoal);

			//tvdesclaimer.setVisibility(View.GONE);

			set.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(Daily_Goal.this.getActivity(), "Set  other", Toast.LENGTH_LONG).show();
					setgoal.setVisibility(View.VISIBLE);
					viewgoal.setVisibility(View.GONE);
				}
			});
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
				Toast.makeText(Daily_Goal.this.getActivity(), "Set Your Own Daily Goal", Toast.LENGTH_LONG).show();
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

	/*@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.daily__goal, menu);
		super.onCreateOptionsMenu(menu, inflater);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id==R.id.action_settings)
		{
			Intent intent=new Intent(Daily_Goal.this.getActivity(),LauncherActivity.class);
			startActivity(intent);

		}
		return super.onOptionsItemSelected(item);
	}*/
}
