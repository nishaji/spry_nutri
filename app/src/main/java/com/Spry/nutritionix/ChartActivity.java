package com.Spry.nutritionix;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Spry.dev5magic.NoBoringActionBarActivity;
import com.myapplication.R;

public class ChartActivity extends AppCompatActivity {
	String  item_name,brand_name,serving_size_qty,serving_size_unit,serving_size_gram,nf_calories,nf_calories_from_fat,
	total_fat,saturated_fat,poly_sat_fat,nf_cholesterol,nf_sodium,total_carbohydrates,nf_proteins,nf_vitamin_A,
	nf_vitamin_C,nf_calcium,nf_iron,dietary_fiber,sugar;
	TextView tvitem_name,tvbrand_name,tvserving_size_unit,tvserving_size_gram,tvnf_calories,tvnf_calories_from_fat,
	tvtotal_fat,tvsaturated_fat,tvpoly_sat_fat,tvnf_cholesterol,tvnf_sodium,tvtotal_carbohydrates,tvnf_proteins,tvnf_vitamin_A,
	tvnf_vitamin_C,tvnf_calcium,tvfrom,tvnf_iron,tvdietary_fiber,tvsugar,tvamountserving,tvnote;
	TextView edtserving_size_qty;
	TextView tvTitle,tvpervitA,tvpercal,tvpervitc,tvperiron;
	TextView tvcalorieGraph,tvCaloriePer,tvfatGraph,tvfatPer,tvCholestrolGraph,tvCholestrolPer,tvcarbsGraph,tvcarbsPer
			,tvsodGraph,tvsodPer,tvproGraph,tvproPer,tvsatfatGraph,tvsatfatPer;
	static int counter=0;
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_detail_view);

	Typeface roboto_regular=Typeface.createFromAsset(getAssets(), "fonts/Roboto_Regular.ttf");
    Typeface roboto_light=Typeface.createFromAsset(getAssets(), "fonts/Roboto_Light.ttf");
    Typeface roboto_medium=Typeface.createFromAsset(getAssets(), "fonts/Roboto_Medium.ttf");
    	
								
	    Intent intent=getIntent();
	    item_name=intent.getStringExtra(Constants.ITEM_NAME);
	    brand_name=intent.getStringExtra(Constants.BRAND_NAME);
		serving_size_qty=intent.getStringExtra(Constants.NF_SERVING_SIZE_QTY);
		serving_size_unit=intent.getStringExtra(Constants.NF_SERVING_SIZE_UNIT);
		serving_size_gram=intent.getStringExtra(Constants.NF_SERVING_WEIGHT_GRAMS);
		nf_calories=intent.getStringExtra(Constants.NF_CALORIES);
		nf_calories_from_fat=intent.getStringExtra(Constants.NF_CALORIES_FROM_FAT);
		total_fat=intent.getStringExtra(Constants.NF_TOTAL_FAT);
		saturated_fat=intent.getStringExtra(Constants.NF_SATURATED_FAT);
		poly_sat_fat=intent.getStringExtra(Constants.NF_POLY_SATURATED_FAT);
		nf_cholesterol=intent.getStringExtra(Constants.NF_CHOLESTEROL);
		nf_sodium=intent.getStringExtra(Constants.NF_SODIUM);
		total_carbohydrates=intent.getStringExtra(Constants.NF_TOTAL_CARBOHYDRATES);
		nf_proteins=intent.getStringExtra(Constants.NF_PROTEINS);
		nf_vitamin_A=intent.getStringExtra(Constants.NF_VITAMIN_A);
		nf_vitamin_C=intent.getStringExtra(Constants.NF_VITAMIN_C);
		nf_calcium=intent.getStringExtra(Constants.NF_CALCIUM);
		nf_iron=intent.getStringExtra(Constants.NF_IRON);
		dietary_fiber=intent.getStringExtra(Constants.NF_DIETARY_FIBER);
		sugar=intent.getStringExtra(Constants.NF_SUGAR);
		Toolbar toolbar = (Toolbar) findViewById(R.id.chart_toolbar);
		setSupportActionBar(toolbar);
		if(getActionBar() != null){
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
		toolbar.setTitle(brand_name);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				 onBackPressed();
			}
		});
		tvfrom=(TextView)findViewById(R.id.tvfromshow);
		tvnote=(TextView)findViewById(R.id.tvNoteShow);
		tvnote.setTypeface(roboto_medium);
		tvamountserving=(TextView)findViewById(R.id.tvSatfatShow);
		tvamountserving.setTypeface(roboto_light);
		tvitem_name=(TextView)findViewById(R.id.tvitemnameshow);
		tvTitle=(TextView)findViewById(R.id.tvtitlenameshow);
		tvitem_name.setTypeface(roboto_regular);
		tvbrand_name=(TextView)findViewById(R.id.tvbrandnamedetailshow);
		edtserving_size_qty=(TextView)findViewById(R.id.edtsearvingsizeqtyshow);
		edtserving_size_qty.setTypeface(roboto_light);
		tvserving_size_unit=(TextView)findViewById(R.id.tvitemdetailsShow);
		tvnf_calories=(TextView)findViewById(R.id.tvcaloriesValueShow);
		tvnf_calories.setTypeface(roboto_regular);
		tvtotal_fat=(TextView)findViewById(R.id.tvFatValueShow);
		tvtotal_fat.setTypeface(roboto_regular);
		tvsaturated_fat=(TextView)findViewById(R.id.tvSatFatValueShow);
		tvsaturated_fat.setTypeface(roboto_regular);
		tvnf_cholesterol=(TextView)findViewById(R.id.tvCholestrolValueShow);
		tvnf_cholesterol.setTypeface(roboto_regular);
		tvnf_sodium=(TextView)findViewById(R.id.tvSodiumValueShow);
		tvnf_sodium.setTypeface(roboto_regular);
		tvtotal_carbohydrates=(TextView)findViewById(R.id.tvCarbsValueShow);
		tvtotal_carbohydrates.setTypeface(roboto_regular);
		tvnf_proteins=(TextView)findViewById(R.id.tvProteinValueShow);
		tvnf_proteins.setTypeface(roboto_regular);
		tvnf_vitamin_A=(TextView)findViewById(R.id.tvVitminAValueShow);
		tvnf_vitamin_A.setTypeface(roboto_regular);
		tvnf_vitamin_C=(TextView)findViewById(R.id.tvVitminBValueShow);
		tvnf_vitamin_C.setTypeface(roboto_regular);
		tvnf_calcium=(TextView)findViewById(R.id.tvCalciumValueShow);
		tvnf_calcium.setTypeface(roboto_regular);
		tvnf_iron=(TextView)findViewById(R.id.tvIronValueShow);;
		tvnf_iron.setTypeface(roboto_regular);
		tvcalorieGraph=(TextView)findViewById(R.id.tvCaloriePerGraphValue);
		tvCaloriePer=(TextView)findViewById(R.id.tvCaloriesPerValueShow);
		tvfatGraph=(TextView)findViewById(R.id.tvFatPerGraphValue);
		tvfatPer=(TextView)findViewById(R.id.tvFatPerValueShow);
		tvCholestrolGraph=(TextView)findViewById(R.id.tvCholestrolPerGraphValue);
		tvCholestrolPer=(TextView)findViewById(R.id.tvCholestrolPerValueShow);
		tvcarbsGraph=(TextView)findViewById(R.id.tvCarbsPerGraphValue);
		tvcarbsPer=(TextView)findViewById(R.id.tvCarbsPerValueShow);
		tvsodGraph=(TextView)findViewById(R.id.tvSodiumPerGraphValue);
		tvsodPer=(TextView)findViewById(R.id.tvSodiumPerValueShow);
		tvproGraph=(TextView)findViewById(R.id.tvProteinPerGraphValue);
		tvproPer=(TextView)findViewById(R.id.tvProteinPerValueShow);
		tvsatfatGraph=(TextView)findViewById(R.id.tvSatFatPerGraphValue);
		tvsatfatPer=(TextView)findViewById(R.id.tvSatFatPerValueShow);
		tvpervitA=(TextView)findViewById(R.id.tvVitminAPerValue);
		tvpervitc=(TextView)findViewById(R.id.tvVitminBPerValue);
		tvpercal=(TextView)findViewById(R.id.tvCalciumPerValue);
		tvperiron=(TextView)findViewById(R.id.tvIronPerValueShow);
		tvTitle.setTypeface(roboto_regular);
		edtserving_size_qty.setTypeface(roboto_regular);
		edtserving_size_qty.addTextChangedListener(new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable arg0) {
			System.out.println("hii");
			// TODO Auto-generated method stub
			String Val=edtserving_size_qty.getText().toString();
			System.out.println(Val+"aftertextchangecheckingvalue");
			Float i;
			if(Val.equals("")||Val.equals(null))
			{
				i=0.0f;
			}else
			{
				i=Float.valueOf(Val);
			}
			
			if(i>0)
			{
				Float j=Float.valueOf(serving_size_qty);
					

					Log.d("Values","In grams");
				Log.d("servingsize",j.toString());
					
					if(!nf_calories.equals("null")&&!nf_calories.equals(null))
					{
					Float L=Float.valueOf(nf_calories);
					Float K=((L/j))*i;
					Float per=(K/2000)*100;
					tvCaloriePer.setText("("+ String.valueOf(Math.round(per))+"%)");
					tvcalorieGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per)))*2);
					tvnf_calories.setText(String.format("%.2f", K)+"    K"  );
					}
					if(!total_fat.equals("null")&&!total_fat.equals(null))
					{
						Float L=Float.valueOf(total_fat);
						Float K=((L/j))*i;
						Float per=(K/65)*100;
						tvfatPer.setText("("+ String.valueOf(Math.round(per))+"%)");
						tvfatGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per)))*2);
						tvtotal_fat.setText(String.format("%.2f", K)+"    g");
					}
					if(!saturated_fat.equals("null")&&!saturated_fat.equals(null))
					{	Float L=Float.valueOf(saturated_fat);
					    Float K=((L/j))*i;
					    Float per=(K/24)*100;
					    tvsatfatPer.setText("("+ String.valueOf(Math.round(per))+"%)");
					    tvsatfatGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per)))*2);
						tvsaturated_fat.setText(String.format("%.2f", K)+"    g");
					}
					if(!nf_cholesterol.equals("null")&&!nf_cholesterol.equals(null))
					{
						Float L=Float.valueOf(nf_cholesterol);
						Float K=((L/j))*i;
						Float per=(K/300)*100;
						tvCholestrolPer.setText("("+ String.valueOf(Math.round(per))+"%)");
						tvCholestrolGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per)))*2);
						tvnf_cholesterol.setText(String.format("%.2f", K)+"    mg");
					
					}
					if(!nf_sodium.equals("null")&& !nf_sodium.equals(null))
					{	Float L=Float.valueOf(nf_sodium);
						Float K=((L/j))*i;
						Float per=(K/2400)*100;
						tvsodPer.setText("("+ String.valueOf(Math.round(per))+"%)");
						tvsodGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per)))*2);
						tvnf_sodium.setText(String.format("%.2f", K)+"    mg"+"  ("+ String.format("%.1f", per)+"%)");
					
					}
					if(!total_carbohydrates.equals("null") && !total_carbohydrates.equals(null))
					{	Float L=Float.valueOf(total_carbohydrates);
						Float K=((L/j))*i;
						Float per=(K/300)*100;
						tvcarbsPer.setText("("+ String.valueOf(Math.round(per))+"%)");
						tvcarbsGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per)))*2);
						tvtotal_carbohydrates.setText(String.format("%.2f", K)+"    g"+"  ("+ String.format("%.1f", per)+"%)");
					
					}
					else
					{
						
					}
					if(!nf_proteins.equals("null")&&!nf_proteins.equals(null))
					{
						Float L=Float.valueOf(nf_proteins);
						Float K=((L/j))*i;
						Float per=(K/50)*100;
						tvproPer.setText("("+ String.valueOf(Math.round(per))+"%)");
						tvproGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per)))*2);
						tvnf_proteins.setText(String.format("%.2f", K)+"    g"+"  ("+ String.format("%.1f", per)+"%)");
					
					}
					else{
						
					}
				}
		}
	});

		/*
	 * Now Setting the Values to the TextViews
	 */
	if(!item_name.equals("null") && !item_name.equals(null))
		tvitem_name.setText(item_name);
	if(!brand_name.equals("null"))
		tvbrand_name.setText(brand_name);
	if(!serving_size_qty.equals("null"))
		{
			System.out.println("firstif");
		int i= Math.round(Float.valueOf(serving_size_qty));
		if (i>0)
			{
				System.out.println("secondif");
			Log.i("value of order", String.valueOf(i));
			edtserving_size_qty.setText(String.valueOf(i));
			}
		else
		{
			System.out.println("firstelse");
			edtserving_size_qty.setText("1");
		}
		}
	else {
		System.out.println("secondelse");
		edtserving_size_qty.setText("1");
	}
	if(!serving_size_unit.equals("null"))
	tvserving_size_unit.setText(serving_size_qty+" "+serving_size_unit);
	if(!nf_calories.equals("null"))
	{
	Float L=Float.valueOf(nf_calories);
	Float per=(L/2000)*100;
	tvCaloriePer.setText("("+ String.valueOf(Math.round(per))+"%)");
	tvcalorieGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per)))*2);
	tvnf_calories.setText(String.valueOf(L)+"    K");
	}
	if(!total_fat.equals("null"))
	{
		Float L=Float.valueOf(total_fat);
		Float per=(L/65)*100;
		tvfatPer.setText("("+ String.valueOf(Math.round(per))+"%)");
		tvfatGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per)))*2);
	    tvtotal_fat.setText(String.valueOf(L)+"    g");
	}
	if(!saturated_fat.equals("null"))
	{	Float L=Float.valueOf(saturated_fat);
	
		Float per=(L/24)*100;
		tvfatPer.setText("("+ String.valueOf(Math.round(per))+"%)");
		tvfatGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per)))*2);
		tvsaturated_fat.setText(saturated_fat+"    g");
		
	}
	if(!nf_cholesterol.equals("null"))
	{

		Float L=Float.valueOf(nf_cholesterol);
		Float per=(L/300)*100;
		tvCholestrolPer.setText("("+ String.valueOf(Math.round(per))+"%)");
		tvCholestrolGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per)))*2);
		tvnf_cholesterol.setText(String.valueOf(L)+"    mg");
	}
	if(!nf_sodium.equals("null"))
	{
		Float L=Float.valueOf(nf_sodium);
		
		Float per=(L/2400)*100;
		tvsodPer.setText("("+ String.valueOf(Math.round(per))+"%)");
		tvsodGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per)))*2);
		tvnf_sodium.setText(String.valueOf(L)+"    mg");
	}
	if(!total_carbohydrates.equals("null"))
	{
		Float L=Float.valueOf(total_carbohydrates);
		Float per=(L/300)*100;
		tvcarbsPer.setText("("+ String.valueOf(Math.round(per))+"%)");
		tvcarbsGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per)))*2);
		tvtotal_carbohydrates.setText(String.valueOf(L)+"    g");
	}
	if(!nf_proteins.equals("null"))
	{
		Float L=Float.valueOf(nf_proteins);
		Float per=(L/50)*100;
		tvproPer.setText("("+ String.valueOf(Math.round(per))+"%)");
		tvproGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per)))*2);
		tvnf_proteins.setText(String.valueOf(L)+"    g");
	}
	if(!nf_vitamin_A.equals("null"))
	tvnf_vitamin_A.setText(nf_vitamin_A+"%");
	else
		tvnf_vitamin_A.setText("0"+"%");
	if(!nf_vitamin_C.equals("null"))
	tvnf_vitamin_C.setText(nf_vitamin_C+"%");
	else
		tvnf_vitamin_C.setText("0"+"%");
	if(!nf_calcium.equals("null"))
	tvnf_calcium.setText(nf_calcium+"%");
	else
		tvnf_calcium.setText("0"+"%");
	if(!nf_iron.equals("null"))
	tvnf_iron.setText(nf_iron+"%");
	else
		tvnf_iron.setText("0"+"%");
	if(!nf_calcium.equals("null"))
	{
		tvpercal.setWidth((int) (Float.parseFloat(nf_calcium) *2));
	}
	if(!nf_vitamin_A.equals("null"))
	{
		tvpervitA.setWidth((int) (Float.parseFloat(nf_vitamin_A) *2));
	}
	if(!nf_vitamin_C.equals("null"))
	{
		tvpervitc.setWidth((int) (Float.parseFloat(nf_vitamin_C) *2));
	}
	
	if(!nf_iron.equals("null"))
	{
		tvperiron.setWidth((int) (Float.parseFloat(nf_iron) *2));
	}
  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chart, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.chart_home) {
			Intent intent = new Intent(ChartActivity.this, LauncherActivity.class);
			startActivity(intent);
			return true;
		}
			return super.onOptionsItemSelected(item);
		}

	
	public void clickPlus(View v)
	{
     counter=Integer.parseInt(edtserving_size_qty.getText().toString());
     if(counter>0)
     {
    	 counter++;
    	 edtserving_size_qty.setText(String.valueOf(counter));
		 System.out.println(counter+"counter");
    	 if(!(nf_calcium.equalsIgnoreCase("null")||nf_calcium.equalsIgnoreCase("0")))
    	 {
    	 }
     }
     else
    	 Toast.makeText(ChartActivity.this, "Quantity can't less than 1",Toast.LENGTH_LONG).show();
      
	}

	public void clickMinus(View v)
	{
		counter=Integer.parseInt(edtserving_size_qty.getText().toString());
	     if(counter>1)
	     {
	    	 counter--;
	    	 edtserving_size_qty.setText(String.valueOf(counter));
	    	 if(!(nf_calcium.equalsIgnoreCase("null")||nf_calcium.equalsIgnoreCase("0")))
	    	 {
	    	 }
	     }
	     else
	    	 
	    	 Toast.makeText(ChartActivity.this, "Quantity can't less than 1",Toast.LENGTH_LONG).show();
	      
		}
	

}

