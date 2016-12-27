package com.Spry.nutritionix;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;


import com.spry.database.DbAdapter;
import com.myapplication.R;


public class StableArrayAdapter extends ArrayAdapter<String>  {

    final int INVALID_ID = -1;
    DbAdapter dbAdapter;
	Activity activity;
	ProgressDialog pDialog;
	Viewholder holder;
	 SharedPreferences sharedPref;
 static Map<String,?> keys;
    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    Context context;
    public StableArrayAdapter(Context context, int textViewResourceId,ArrayList<String> list) {
        super(context, textViewResourceId, list);
        for (int i = 0; i < list.size(); ++i) {
            mIdMap.put(list.get(i), i);
        }
        sharedPref = context.getSharedPreferences("Hello",Context.MODE_PRIVATE);
        keys = sharedPref.getAll();
		dbAdapter=new DbAdapter(context);
		dbAdapter.open();
        this.context=context;
        this.activity=(Activity) context;
		this.list=list;
    }

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position)  {
		// TODO Auto-generated method stub
		if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
		else
			{String item = getItem(position);
	       //mIdMap.get(item);
		  try{
		return mIdMap.get(item);
		  }
		  catch(Exception e)
		  {
			  return 0;
		  }
			}
	}
	
	
    @Override
    public boolean hasStableIds() {
        return true;
    }


	   ArrayList<String> list=new ArrayList<String>();
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

//@constructor for getting the refrance of the class which is initiating this

	
	@Override
	public View getView(int position, View convertview, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int kposition=position;
		LayoutInflater layoutInflater=activity.getLayoutInflater();
		if(convertview==null)
		{
		convertview=layoutInflater.inflate(R.layout.list_row_my_daily_meal_plan,parent,false);
		holder = new Viewholder();
		//holder.imgdele=(ImageView)convertview.findViewById(R.id.imgdele_my_daily);
		//holder.imgedit=(ImageView)convertview.findViewById(R.id.imgedit_my_daily);
		holder.tvcategories=(TextView)convertview.findViewById(R.id.tvname_categories);
		convertview.setTag(holder);
		}	
		 else
		 {
			  holder=(Viewholder)convertview.getTag();
		 }
			
		Log.i(":::::", "inside getview"+"\t"+String.valueOf(list.size()));
		SharedPreferences sharedPrefText;
		sharedPrefText = activity.getSharedPreferences("Hello",Context.MODE_PRIVATE);
	
		holder.tvcategories.setText(sharedPrefText.getString("cat"+String.valueOf(kposition), "Deafult"));
		//holder.tvcategories.setText(list.get(kposition));
		
		//@Method Handling the delete Method of the categories
		holder.imgdele.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				SharedPreferences sharedPref;
				sharedPref = activity.getSharedPreferences("Hello",Context.MODE_PRIVATE);
				Map<String,?> keys;
				keys=sharedPref.getAll();
				
				if(keys.entrySet().size()<2)
				{
					Toast.makeText(activity, "Atleast one Categories required", Toast.LENGTH_LONG).show();
				}
				else
				 {
					if(sharedPref.contains("cat"+String.valueOf(kposition))) {
		                //list.remove(keys.entrySet().size()-kposition-1);
		               
		               new UpdateData(kposition,keys.entrySet().size()).execute();
		               list.remove(kposition);
		               
		   			notifyDataSetChanged();
		   	       
		   			Toast.makeText(activity, "deleted"+String.valueOf(kposition), Toast.LENGTH_LONG).show();	                
		            }
				 }
	
	
			}
		});
		
		//@Method Handling the delete Method of the categories
		holder.imgedit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				final Dialog dialog=new Dialog(activity);
				dialog.setTitle("Edit Category");
				dialog.setCanceledOnTouchOutside(true);
				dialog.setContentView(R.layout.dialog_my_daily_meals);
				final EditText editText=(EditText)dialog.findViewById(R.id.dialog_edt_my_daily_meal);
				SharedPreferences sharedPrefs;
				
				sharedPrefs = activity.getSharedPreferences("Hello",Context.MODE_PRIVATE);
				Map<String,?> keys;
				final Map<String,?> ss;
				keys=sharedPrefs.getAll();
				ss=sharedPrefs.getAll();
				//String 	vals=(String) keys.get("cat"+String.valueOf(keys.entrySet().size()-kposition-1));
				String 	vals=sharedPrefs.getString("cat"+String.valueOf(kposition), "Deafult");	
				Log.d("Value",vals);
				editText.setText(vals);
				final Button btnok=(Button)dialog.findViewById(R.id.dialog_btn_ok_my_daily_meal);
				final Button btncancel=(Button)dialog.findViewById(R.id.dilaog_btn_cancel_my_dail_meal);
				
				//@Listener For the btn OK Click
				
				btnok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						SharedPreferences sharedPrefs;
						
						sharedPrefs = activity.getSharedPreferences("Hello",Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPrefs.edit();
						editor.putString("cat"+String.valueOf(kposition), editText.getText().toString()); 
						//This is just an example, you could also put boolean, long, int or floats
						editor.commit();
						
					list.set(kposition, editText.getText().toString());
					
					notifyDataSetChanged();
					dialog.dismiss();
					}
				});
				
				btncancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				
				dialog.show();
				
			
				Toast.makeText(activity, "edited"+String.valueOf(kposition), Toast.LENGTH_LONG).show();
			}
		});
		
		return convertview;
	}
	private class Viewholder
	{
		TextView tvcategories;
		ImageView imgedit,imgdele;
		
	}
	
	public class UpdateData extends AsyncTask<String,String, String>
	{	int val=0;
		int size=0;
		public UpdateData(int val,int size) {
			// TODO Auto-generated constructor stub
			this.val=val;
			this.size=size;
			Log.d("Value of the row to be deleted ",String.valueOf(val));
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pDialog = new ProgressDialog(activity,AlertDialog.THEME_HOLO_DARK);
			pDialog.setMessage("Managing Preferences...");
			pDialog.setIndeterminate(true);
			pDialog.setIndeterminateDrawable(activity.getResources().getDrawable(R.drawable.styeprogress));
			pDialog.setCancelable(false);
			pDialog.show();
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			if (dbAdapter.ChangeConfiguration(val,size))
			{		
				SharedPreferences.Editor editor = sharedPref.edit();
	            editor.remove("cat"+String.valueOf(val));
	            editor.commit();
	        	String values[]=new String[size];
			
	        
				int m=0;
				for(int i=0;i<size;i++)
				{	if(i==val)
	            {	m++;
				
	            }	
				else{
					values[i]=sharedPref.getString("cat"+String.valueOf(i), "Default");
				}
					
				}
				SharedPreferences.Editor editor2 = sharedPref.edit();
	            editor2.clear();
	            editor2.commit();
	            SharedPreferences.Editor editor3 = sharedPref.edit();
	            m=0;
	            for(int i=0;i<size;i++)
				{
				if(i==val)
	            {	m++;
				
	            }
				else{
					editor3.putString("cat"+String.valueOf(i-m), values[i]);
				}
				}
	            editor3.commit();
			}
			Cursor cursor=dbAdapter.fetch_allSave();
        	if(cursor.getCount()>0)
        	{	if(dbAdapter.ChangeFlag(val,keys.entrySet().size()-1))
        			{
        				//Toast.makeText(getApplicationContext(), "Flags updated", Toast.LENGTH_LONG).show();
        				Log.d("Flags Changed","Ok");
        			}
        	}

			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(pDialog.isShowing())
				{
				pDialog.dismiss();
				activity.finish();
				activity.startActivity(activity.getIntent());
				}
		}
	}
	
}
