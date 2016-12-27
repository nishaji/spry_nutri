package com.spry.database;



import com.Spry.nutritionix.Constants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbAdapter
{
	private static final String DATABASE_NAME="search_saved";
	private static final String CREATE_TABLE="create table search_saved(_ID integer primary key autoincrement, position int,b_name text,pdt_name text,img_path text,qty text,unit text,calval text,proval text,sodval text,fatval text, carbsval text,satfatval text,cholestrolval text,vitA text,vitC text,ironval text, calciumval text,serving_gram text,cat0 text,cat0qty text,cat1 text,cat1qty text,cat2 text,cat2qty text);";
	private static final int DATABASE_VERSION=1;
	private static final String CREATE_TABLE_PARAMETER_SET="create table parameters(_ID integer primary key  autoincrement,calorie text,protein text,sodium text,carbs text,fat text,iron text,calcium text,vitA text,vitC text);";

	private static final String TAG = "DbAdapter";
	private static final String POSITION = "position";
	private final Context mCtx;
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private static final String TABLE_NAME="search_saved";

	public static class DatabaseHelper extends SQLiteOpenHelper
	{

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS search_saved");
			db.execSQL("DROP TABLE IF EXISTS items");
			db.execSQL("DROP TABLE IF EXISTS parameters");
			db.execSQL(CREATE_TABLE);
			db.execSQL(CREATE_TABLE_PARAMETER_SET);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS search_saved");
			db.execSQL("DROP TABLE IF EXISTS parameters");
			onCreate(db);
		}
	}
	//Initializing Database adapter
	public DbAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	//OPeninng Database Adapter

	public DbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	//closing Database Helper
	public void close() {
		mDbHelper.close();
	}

	// Creating Database Table

	public long createSave(int position ,String b_name,String pdt_name,String img_path,String qty,String unit,String
			calval,String proval,String sodval,String fatval,String carbsval,
						   String satval,String cholestrolval,String vitA,String vitC,String ironval,String calciumval,String serving_gram
	) {

		//fetching the earlier data to match whether the person is entitled /registered or not

		ContentValues initialValues = new ContentValues();
		initialValues.put(POSITION,position);
		initialValues.put("b_name",b_name);
		initialValues.put("pdt_name",pdt_name);
		initialValues.put("img_path",img_path);
		initialValues.put("qty",qty);
		initialValues.put("unit",unit);
		initialValues.put("calval",calval);
		initialValues.put("proval",proval);
		initialValues.put("sodval",sodval);
		initialValues.put("fatval",fatval);
		initialValues.put("carbsval",carbsval);
		initialValues.put("satfatval",satval);
		initialValues.put("cholestrolval",cholestrolval);
		initialValues.put("vitA",vitA);
		initialValues.put("vitC",vitC);
		initialValues.put("ironval",ironval);
		initialValues.put("calciumval",calciumval);
		initialValues.put("serving_gram",serving_gram);
		return mDb.insert(TABLE_NAME, null, initialValues);

	}

	//@Method which alter the table add the column in the table

	public void AlterAddClomunCat(String cat)
	{

		String sql="ALTER TABLE search_saved ADD "+cat+" text DEFAULT '100';";
		mDb=mDbHelper.getWritableDatabase();
		mDb.execSQL(sql);
	}
	public void AlterAddClomunCatQty(String Catqty)
	{

		String sql="ALTER TABLE search_saved ADD "+Catqty+" text DEFAULT '100';";
		mDb=mDbHelper.getWritableDatabase();
		mDb.execSQL(sql);
	}


	//@Method to Alter the table row and column with some runtime constraints

	public boolean ChangeConfiguration(int val,int size)
	{
		//Starting the Transaction
		boolean status=false;
		mDb.beginTransaction();
		try
		{
			// 1->      Alter the table
			String sql="ALTER TABLE search_saved RENAME TO temp_table;";
			mDb=mDbHelper.getWritableDatabase();

			mDb.execSQL(sql);

			// 2->   Managing the columns
			String cat[]=new String[size];
			String catqty[]=new String[size];
			String comma=",";
			String main="";

			for(int i=0;i<size-1;i++)
			{

				cat[i]="cat"+String.valueOf(i)+" text";
				catqty[i]="cat"+String.valueOf(i)+"qty" +" text";

				main=main+cat[i]+comma+catqty[i]+comma;

			}
			//column name
			int m=0;
			String column="";
			for(int i=0;i<size;i++)
			{
				if(val==i)
				{
					m++;
				}else{
					cat[i]="cat"+String.valueOf(i);
					catqty[i]="cat"+String.valueOf(i)+"qty";
					column=column+cat[i]+comma+catqty[i]+comma;
				}
			}

			// 3-> Creating the new Table

			String Createnew="create table search_saved(_ID integer primary key autoincrement, position int,b_name text,pdt_name text,img_path text,qty text,unit text,calval text,proval text,sodval text,fatval text, carbsval text,satfatval text,cholestrolval text,vitA text,vitC text,ironval text, calciumval text,serving_gram text ," + method(main) +");";
			Log.i("createnew**",Createnew);
			mDb.execSQL(Createnew);

			// 4-> Copying the temp to the new table

			String insert ="INSERT INTO search_saved " +
					"select _ID, position,b_name,pdt_name,img_path,qty,unit,calval,proval,sodval,fatval, carbsval,satfatval,cholestrolval,vitA,vitC,ironval, calciumval,serving_gram,"+method(column)+" FROM temp_table" ;
			mDb.execSQL(insert);

			// 5-> droping the Temp Table
			mDb.execSQL("DROP TABLE IF EXISTS temp_table");
			// Now Setting the transaction Successful
			mDb.setTransactionSuccessful();
			status=true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			mDb.endTransaction();
		}
		return status;
	}

	public String method(String str) {
		if (str.length() > 0 && str.charAt(str.length()-1)==',') {
			str = str.substring(0, str.length()-1);
		}
		return str;
	}
	public long createParameter(String calories,String pro,String sodium,String carbs,String iron,String fat,String calcium,String vitA,String vitC)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put("calorie",calories);
		initialValues.put("protein",pro);
		initialValues.put("sodium",sodium);
		initialValues.put("carbs",carbs);
		initialValues.put("fat",fat);
		initialValues.put("iron",iron);
		initialValues.put("calcium",calcium);
		initialValues.put("vitA",vitA);
		initialValues.put("vitC",vitC);

		return mDb.insert("parameters", null, initialValues);
	}



	public boolean updateParameters(int position,String calories,String pro,String sodium,String carbs,String iron,String fat,String calcium,String vitA,String vitC){

		ContentValues initialValues = new ContentValues();
		initialValues.put("calorie",calories);
		initialValues.put("protein",pro);
		initialValues.put("sodium",sodium);
		initialValues.put("carbs",carbs);
		initialValues.put("fat",fat);
		initialValues.put("iron",iron);
		initialValues.put("calcium",calcium);
		initialValues.put("vitA",vitA);
		initialValues.put("vitC",vitC);
		return mDb.update("parameters", initialValues, "_ID" + "=" +position, null) > 0;
	}


	public Cursor fetch_allParameters() {

		//	mDb=mDbHelper.getReadableDatabase();

		String select="select * from parameters";
		//	String select="select po.title,po.body,po.company,items.code,items.title,items.totalquantity,items.totalprice from po join items on po._id=items.po_id where po._id="+rowID+"";
		if(select.equals(null))
		{
			return null;
		}
		else
		{
			return mDb.rawQuery(select, null);
		}


	}
	//Deleting Row on the click of Remove
	public boolean deleteUnChecked(int position) {

		return mDb.delete(TABLE_NAME, POSITION + "=" + position, null) > 0;
	}
	//Deleting the Hidden Table Row
	public boolean deleteHidden(String cat[][],int keys,int cursorcount) {
		String catval=" cat";
		String catqty="100";
		String And=" and ";
		Boolean status=false;
		String query="";
		int kl=0;
		for(int i=0;i<cursorcount;i++)
		{
			for(int k=0;k<keys;k++)
			{
				if(cat[i][k].equalsIgnoreCase("100"))
				{
					kl++;
					if(k==keys-1)
					{
						String joint=catval+String.valueOf(k);
						query= query + joint +"=" +catqty;
					}
					else
					{
						String joint=catval+String.valueOf(k);
						query= query + joint + "=" + catqty + And;
					}

				}
			}
			if(kl==keys)
			{
				Log.i("deleting ", "deleting ..."+query);
				mDb=mDbHelper.getReadableDatabase();
				//String select="delete from search_saved where cat1='3' and cat2='3' and cat3='3';";
				String select="delete from search_saved where "+query+";";
				mDb.execSQL(select);
				Log.i("deleting ", "deleted");

			}
			else
			{

			}

		}
		return status;


	}

	public void DeleteRow(String query)
	{
		mDb=mDbHelper.getReadableDatabase();
		//String select="delete from search_saved where cat1='3' and cat2='3' and cat3='3';";
		String select="delete from search_saved where "+query+";";
		mDb.execSQL(select);

	}
	public boolean deleteChecked(String position,String item_name,String status) {


		ContentValues initialValues = new ContentValues();
		if(status.equalsIgnoreCase("cat1"))
		{
			initialValues.put("cat0", "3");
		}
		else if(status.equalsIgnoreCase("cat2"))
		{
			initialValues.put("cat1", "3");
		}
		else if(status.equalsIgnoreCase("cat3"))
		{
			initialValues.put("cat2", "3");
		}String b="'"+position+"'";
		String p="'"+item_name+"'";
		return mDb.update(TABLE_NAME, initialValues, "b_name" + "=" + p+  " and " + "pdt_name" + "=" + b, null) > 0;
	}

	//@Method for the Delete of the Item

	public boolean deleteCheckedItems(String product_name,String item_name,String status) {


		ContentValues initialValues = new ContentValues();

		initialValues.put(status, "100");

		String product="'"+product_name+"'";
		String bname="'"+item_name+"'";
		return mDb.update(TABLE_NAME, initialValues, "b_name" + "=" + bname+  " and " + "pdt_name" + "=" + product, null) > 0;
	}

	/*
	 * @Method For updating the catvalues
	 */
	public Boolean updatecatdragvaluesStart(String Start,String End)
	{

		ContentValues initialValues = new ContentValues();
		String catstart="cat"+End;
		String catend="cat"+Start;
		String limit="'"+"100"+"'";
		initialValues.put("cat"+Start, Start);
		if( mDb.update(TABLE_NAME, initialValues, catstart + "!=" +limit +  " and " + catend + "!=" + limit, null) > 0)
		{
			return  mDb.update(TABLE_NAME, initialValues, catend + "!=" +limit , null) > 0;
		}
		else
		{
			initialValues.clear();
			initialValues.put("cat"+Start, "100");
			return  mDb.update(TABLE_NAME, initialValues, catend + "=" +limit , null) > 0;
		}


	}


	public Boolean updatecatdragvaluesEnd(String Start,String End)
	{

		ContentValues initialValues = new ContentValues();
		String catstart="cat"+End;
		String catend="cat"+Start;
		String limit="'"+"100"+"'";
		initialValues.put("cat"+End, Start);

		if( mDb.update(TABLE_NAME, initialValues, catstart + "!=" +limit +  " and " + catend + "!=" + limit, null) > 0)
		{
			return  mDb.update(TABLE_NAME, initialValues, catstart + "!=" +limit , null) > 0;
		}
		else
		{
			initialValues.clear();
			initialValues.put("cat"+End, "100");
			return  mDb.update(TABLE_NAME, initialValues, catstart + "=" +limit , null) > 0;
		}


	}


	//@method for creating update

	public boolean updateitems(String b_name, String pdt_name,String qty,String status) {

		ContentValues initialValues = new ContentValues();
		if(status.equalsIgnoreCase("cat0"))
		{
			initialValues.put("cat0qty", qty);
		}
		else if(status.equalsIgnoreCase("cat1"))
		{
			initialValues.put("cat1qty", qty);
		}
		else if(status.equalsIgnoreCase("cat2"))
		{
			initialValues.put("cat2qty", qty);
		}

		String b="'"+b_name+"'";
		String p="'"+pdt_name+"'";
		return mDb.update(TABLE_NAME, initialValues, "b_name" + "=" + b+  " and " + "pdt_name" + "=" + p, null) > 0;
	}


	public boolean updateitemsNoAction(String b_name, String pdt_name,String qty,String status,int loop) {

		ContentValues initialValues = new ContentValues();
		for(int i=0;i<loop;i++)
		{	if(status.equalsIgnoreCase("cat"+String.valueOf(i)))
		{
			initialValues.put("cat"+String.valueOf(i)+"qty", qty);
		}
		}
		String b="'"+b_name+"'";
		String p="'"+pdt_name+"'";
		return mDb.update(TABLE_NAME, initialValues, "b_name" + "=" + b+  " and " + "pdt_name" + "=" + p, null) > 0;
	}
	//@Mehods for the updating of the values Which are created dynamically

	public boolean UpdateNewBie(String b_name,String pdt_name,String val_qty,Integer[] val,Integer size)
	{

		String cat="cat";
		String qty="qty";
		ContentValues initialValues = new ContentValues();
		for(int i=0;i<size;i++)
		{
			initialValues.put(cat+String.valueOf(i), val[i]);
			initialValues.put(cat+String.valueOf(i)+qty, val_qty);
		}
		String b="'"+b_name+"'";
		String p="'"+pdt_name+"'";
		return mDb.update(TABLE_NAME, initialValues, "b_name" + "=" + b+  " and " + "pdt_name" + "=" + p, null) > 0;
	}
	//@Method for the updatation of the flags for the changed Database


	public boolean ChangeFlag(int val,int size)
	{

		String catintiial=" cat";
		String And=" and ";

		boolean status=false;
		//@Loop
		ContentValues initial = new ContentValues();
		String condition2=catintiial + String.valueOf(val) +" ="+String.valueOf(val) + And + catintiial + String.valueOf(val) +" ='100'";
		initial.put(catintiial+String.valueOf(val), 100);
		mDb.update(TABLE_NAME, initial, condition2 , null) ;

		for(int i=0;i<size;i++)
		{
			ContentValues initialValues = new ContentValues();
			String condition=catintiial + String.valueOf(i) +" !="+String.valueOf(i) + And + catintiial + String.valueOf(i) +" !='100'";
			initialValues.put(catintiial+String.valueOf(i), i);
			status= mDb.update(TABLE_NAME, initialValues, condition , null) > 0;
		}

		return status;
	}

	/*
	 * @ Method for updating the preferences of the already saved Preferences
	 */

	public Boolean updatePreference(String pdt_name,String b_name,int keysize ,Integer[] value)
	{
		ContentValues initialValues = new ContentValues();
		String condition =null;
		String cat="cat";
		for(int i=0;i<keysize;i++){
			initialValues.put(cat+String.valueOf(i), String.valueOf(value[i]));
			Log.e(cat+String.valueOf(i), String.valueOf(value[i]));
		}
		String b="'"+b_name+"'";
		String i="'"+pdt_name+"'";
		condition="b_name"+"="+b+ " and " +"pdt_name"+"="+i;
		return mDb.update(TABLE_NAME, initialValues, condition , null) > 0;
	}

	//@Method For Saving All the Data

	public Cursor fetch_allSave() {

		mDb=mDbHelper.getReadableDatabase();
		String select="select * from search_saved";
		//	String select="select po.title,po.body,po.company,items.code,items.title,items.totalquantity,items.totalprice from po join items on po._id=items.po_id where po._id="+rowID+"";

		return mDb.rawQuery(select, null);

	}



	public Cursor fetch_allBreakfast(String flag,String category) {


		mDb=mDbHelper.getReadableDatabase();
		String select="select * from search_saved where "+category+" = "+flag;
		//	String select="select po.title,po.body,po.company,items.code,items.title,items.totalquantity,items.totalprice from po join items on po._id=items.po_id where po._id="+rowID+"";

		return mDb.rawQuery(select, null);

	}
	public Cursor fetch_allLunch(String flag) {

		mDb=mDbHelper.getReadableDatabase();
		String select="select * from search_saved where cat2="+flag;
		//	String select="select po.title,po.body,po.company,items.code,items.title,items.totalquantity,items.totalprice from po join items on po._id=items.po_id where po._id="+rowID+"";
		return mDb.rawQuery(select, null);

	}
	public Cursor fetch_allDinner(String flag) {

		mDb=mDbHelper.getReadableDatabase();
		String select="select * from search_saved where cat3="+flag;
		//	String select="select po.title,po.body,po.company,items.code,items.title,items.totalquantity,items.totalprice from po join items on po._id=items.po_id where po._id="+rowID+"";
		return mDb.rawQuery(select, null);

	}




	public Cursor fetch_data(String item,String brand)
	{
		mDb=mDbHelper.getReadableDatabase();
		String item1="'"+item+"'";
		String brand1="'"+brand+"'";
		String select="select * from search_saved where pdt_name=" + item1 + "and b_name="+brand1;
		return mDb.rawQuery(select, null);
	}
}