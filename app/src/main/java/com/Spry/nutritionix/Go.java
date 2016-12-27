package com.Spry.nutritionix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.Spry.Animation.FlipAnimation;
import com.Spry.Design.CircleButton;
import com.Spry.Swipe.BackgroundContainer;
import com.Spry.dev5magic.NoBoringActionBarActivity;
import com.myapplication.R;
import com.ramotion.foldingcell.FoldingCell;
import com.spry.database.DbAdapter;



public class Go extends AppCompatActivity {
    EditText edtsearch;
    ListViewAdapter adapter;
    String url="";
    static int icountinex=0;
    Button addmore;
    ImageView imgloadmore;
    TextView tvresult,tvdesc2,tvpowerdesc;
    TextView tvl1,tvl2,tvl3,tvl4,tvl5;

    String responseString="";
    boolean left=true,right=true;
    //BackgroundContainer mBackgroundContainer;
    TextView tvstatus;
    ImageView imageView;
    DbAdapter dataadapter;
    RelativeLayout rel_search;
    boolean status=true;
    ProgressDialog pDialog;
    String [] item_name,brand_name,serving_size_qty,serving_size_unit,serving_size_gram,nf_calories,nf_calories_from_fat,
            total_fat,saturated_fat,poly_sat_fat,nf_cholesterol,nf_sodium,total_carbohydrates,nf_proteins,nf_vitamin_A,
            nf_vitamin_C,nf_calcium,nf_iron,nf_daietry_fiber,nf_sugar;
    LinearLayout pieContainer;
    int factor = 1;
    MenuItem search_item;
    Menu menu;
    TextView tvcarbs,tvactionbartitle;
    String s="";
    ListView lsview;
    ArrayList<HashMap<?, ?>> contactList;
    ArrayList<HashMap<?, ?>> MarkerList;
    ArrayList<HashMap<?, ?>> con;
    Float firon,fcalcium,fvitA,fvitC;
    Float fcal=2000f,
            fpro=50f,
            ffat=65f,

    fsod=2400f, fcarbs=300f;

    String [] FINAL_ITEM_NAME;
    static int counter=0;
    BackgroundContainer mBackgroundContainer;
    static  Integer[] indexvalue;
    int multiplyfactor_cal=6,multiplyfactor_pro=6,multiplyfactor_sod=6,multiplyfactor_fat=6,multiplyfactor_carbs=6;
    int detailmultiplyfactor_cal=4,detailmultiplyfactor_carb=4,detailmultiplyfactor_pro=4,detailmultiplyfactor_fiber=4,detailmultiplyfactor_sod=4;

    Button btngo,btncalories,btncarbs,btnfats,btnsodium,btnprotien;
    static int count=0;
    SharedPreferences sharedPref;
    static Map<String,?> keys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);
        Toolbar toolbar = (Toolbar) findViewById(R.id.go_toolbar);
        setSupportActionBar(toolbar);
        if(getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolbar.setTitle("Searh");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onBackPressed();
                Intent intent=new Intent(Go.this,LauncherActivity.class);
                startActivity(intent);
            }
        });
        sharedPref = this.getSharedPreferences("Hello",Context.MODE_PRIVATE);
        keys = sharedPref.getAll();
        // getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("map values",entry.getKey() + ": " +
                    entry.getValue().toString());

        }

        tvstatus=(TextView)findViewById(R.id.tvstatusfalse);
        mBackgroundContainer = (BackgroundContainer) findViewById(R.id.listViewBackground);
        lsview = (ListView)findViewById(android.R.id.list);

        lsview.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                v.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        lsview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                System.out.println(pos+"possiittiioonn");
                // register in adapter that state for selected cell is toggled

            }
        });

        if(Integer.parseInt(String.format("%.0f", fcal))<=1000)
        {
            multiplyfactor_cal=8;
            multiplyfactor_pro=8;
            multiplyfactor_sod=8;
            multiplyfactor_fat=8;
            multiplyfactor_carbs=8;
        }
        else if(Integer.parseInt(String.format("%.0f", fcal))>=1001)
        {

            if(Integer.parseInt(String.format("%.0f", fcal))<=1600)
            {
                multiplyfactor_cal=6;
                multiplyfactor_pro=6;
                multiplyfactor_sod=6;
                multiplyfactor_fat=6;
                multiplyfactor_carbs=6;
            }
        }

        else if(Integer.parseInt(String.format("%.0f", fcal))>=1601)
        {

            if(Integer.parseInt(String.format("%.0f", fcal))<=2000)
            {
                multiplyfactor_cal=4;
                multiplyfactor_pro=4;
                multiplyfactor_sod=4;
                multiplyfactor_fat=4;
                multiplyfactor_carbs=4;
            }
        }
        else
        {

            multiplyfactor_cal=3;
            multiplyfactor_pro=3;
            multiplyfactor_sod=3;
            multiplyfactor_fat=3;
            multiplyfactor_carbs=3;

        }
        if(Integer.parseInt(String.format("%.0f", fcal))<=1000)
        {
            detailmultiplyfactor_cal=8;
            detailmultiplyfactor_pro=8;
            detailmultiplyfactor_sod=8;
            detailmultiplyfactor_fiber=8;
            detailmultiplyfactor_carb=8;
        }
        else if(Integer.parseInt(String.format("%.0f", fcal))>=1001)
        {

            if(Integer.parseInt(String.format("%.0f", fcal))<=1600)
            {
                detailmultiplyfactor_cal=6;
                detailmultiplyfactor_pro=6;
                detailmultiplyfactor_sod=6;
                detailmultiplyfactor_fiber=6;
                detailmultiplyfactor_carb=6;
            }
        }

        else if(Integer.parseInt(String.format("%.0f", fcal))>=1601)
        {

            if(Integer.parseInt(String.format("%.0f", fcal))<=2000)
            {
                detailmultiplyfactor_cal=4;
                detailmultiplyfactor_pro=4;
                detailmultiplyfactor_sod=4;
                detailmultiplyfactor_fiber=4;
                detailmultiplyfactor_carb=4;
            }
        }
        else
        {

            detailmultiplyfactor_cal=3;
            detailmultiplyfactor_pro=3;
            detailmultiplyfactor_sod=3;
            detailmultiplyfactor_fiber=3;
            detailmultiplyfactor_carb=3;

        }
        rel_search=(RelativeLayout)findViewById(R.id.main_lay_search);
        imageView=(ImageView)findViewById(R.id.imagecut);
        imageView.setVisibility(View.VISIBLE);
        addmore=(Button)findViewById(R.id.LoadMore);
        imgloadmore=(ImageView)findViewById(R.id.imgloadmore);
        addmore.setVisibility(View.GONE);
        imgloadmore.setVisibility(View.GONE);
        tvactionbartitle=(TextView)findViewById(R.id.actiontitle);
        tvl1=(TextView)findViewById(R.id.tvprotiendesclabel);
        tvl2=(TextView)findViewById(R.id.tvcaloriesdesclabel);
        tvl3=(TextView)findViewById(R.id.tvcarbsdesclabel);
        tvl4=(TextView)findViewById(R.id.tvsodiumdesclabel);
        tvl5=(TextView)findViewById(R.id.tvfiberdesclabel);



        Typeface roboto_light=Typeface.createFromAsset(getAssets(), "fonts/Roboto_Light.ttf");
        Typeface roboto_regular=Typeface.createFromAsset(getAssets(), "fonts/Roboto_Regular.ttf");

        tvl1.setTypeface(roboto_regular);
        tvl2.setTypeface(roboto_regular);
        tvl3.setTypeface(roboto_regular);
        tvl4.setTypeface(roboto_regular);
        tvl5.setTypeface(roboto_regular);
        tvcarbs=(TextView)findViewById(R.id.carbsview);


        Intent intent=getIntent();
        String response=intent.getStringExtra("Json");
        String edt="";
        edt=intent.getStringExtra("search");
        rel_search.setVisibility(View.GONE);

        contactList = new ArrayList<HashMap<?, ?>>();
        MarkerList = new ArrayList<HashMap<?, ?>>();
        con = new ArrayList<HashMap<?, ?>>();

        edtsearch=(EditText) findViewById(R.id.edtserach2);
        edtsearch.setTypeface(roboto_light);
        btngo=(Button) findViewById(R.id.btngosecond);
        edtsearch.setText(edt);
        if(!(edt.equals("")||edt.equals(null)))
        {
            try {
                if(response.equals(""))
                {
                    Toast.makeText(Go.this, "Check You Connection", Toast.LENGTH_LONG).show();
                }
                else

                    parserJSON(response);


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {

        }
        if((contactList.isEmpty())||MarkerList.isEmpty())
        {

        }else
        {
            adapter=new ListViewAdapter(Go.this, contactList);
            lsview.setAdapter(adapter);
            addmore.setVisibility(View.VISIBLE);
            imgloadmore.setVisibility(View.VISIBLE);

        }

        btngo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // TODO Auto-generated method stub
                String k = edtsearch.getText().toString().replace(' ', '+');
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(edtsearch.getWindowToken(), 0);
                int limit=20;
                url="http://192.168.1.107:3003/api/searchitems/search?limit="+limit+"&searchitem="+k;
                Log.d("URL",url);
                if(edtsearch.getText().toString().equals(null)||edtsearch.getText().toString().equals(""))
                {
                    Log.d("msg","Please Enter some Values to search");
                }else
                {
                    if(isConnectingToInternet()==true)
                    {s="main";
                        //tvdesc3.setText("Type");
                        contactList.clear();
                        MarkerList.clear();
                        try
                        {
                            new Search().execute(url);
                            rel_search.setVisibility(View.GONE);
                            search_item.setVisible(true);
                            addmore.setVisibility(View.VISIBLE);
                            imgloadmore.setVisibility(View.VISIBLE);
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(Go.this, "Please Enter some Values to search",Toast.LENGTH_LONG).show();
                        }

                    }
                    else
                    {
                        Message();
                    }
                }
            }
        });


		/*
		 * HandlingKeyEvents
		 */


        edtsearch.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub
                if ((event != null && ((actionId == EditorInfo.IME_ACTION_GO)||(event.getKeyCode()==KeyEvent.KEYCODE_ENTER)))) {
                    String k =edtsearch.getText().toString().replace(' ', '+');
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(edtsearch.getWindowToken(), 0);
                    int limit=20;
                    url="http://192.168.1.107:3003/api/searchitems/search?limit="+limit+"&searchitem="+k;
                    Log.d("URL",url);

                    if(edtsearch.getText().toString().equals(null)||edtsearch.getText().toString().equals(""))
                    {
                        Log.d("msg","Please Enter some Values to search");
                    }else
                    {
                        if(isConnectingToInternet()==true){
                            contactList.clear();
                            MarkerList.clear();
                            new Search().execute(url);
                            rel_search.setVisibility(View.GONE);
                            search_item.setVisible(true);
                            addmore.setVisibility(View.VISIBLE);
                            imgloadmore.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            Message();
                        }
                    }
                }


                return false;
            }
        });


        edtsearch.addTextChangedListener(new TextWatcher() {

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
    }
    @Override
    protected void onPause() {
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
        super.onPause();
    }



    //Intent For the Most Details Intent To ChartActivity


	/*
	 * Handling List View Click
	 */

    public void intentMe(int position)
    {
        Intent in = new Intent(Go.this,
                ChartActivity.class);

        in.putExtra(Constants.ITEM_NAME,item_name[position] );
        in.putExtra(Constants.BRAND_NAME, brand_name[position]);
        in.putExtra(Constants.NF_SERVING_SIZE_QTY,serving_size_qty[position]);
        in.putExtra(Constants.NF_SERVING_SIZE_UNIT,serving_size_unit[position]);
        in.putExtra(Constants.NF_SERVING_WEIGHT_GRAMS,serving_size_gram[position]);
        in.putExtra(Constants.NF_CALORIES, nf_calories[position]);
        in.putExtra(Constants.NF_CALORIES_FROM_FAT,nf_calories_from_fat[position]);
        in.putExtra(Constants.NF_TOTAL_FAT,total_fat[position]);
        in.putExtra(Constants.NF_SATURATED_FAT, saturated_fat[position]);
        in.putExtra(Constants.NF_POLY_SATURATED_FAT, poly_sat_fat[position]);
        in.putExtra(Constants.NF_CHOLESTEROL, nf_cholesterol[position]);
        in.putExtra(Constants.NF_SODIUM, nf_sodium[position]);
        in.putExtra(Constants.NF_TOTAL_CARBOHYDRATES, total_carbohydrates[position]);
        in.putExtra(Constants.NF_PROTEINS, nf_proteins[position]);
        in.putExtra(Constants.NF_VITAMIN_A, nf_vitamin_A[position]);
        in.putExtra(Constants.NF_VITAMIN_C, nf_vitamin_C[position]);
        in.putExtra(Constants.NF_IRON, nf_iron[position]);
        in.putExtra(Constants.NF_CALCIUM, nf_calcium[position]);
        in.putExtra(Constants.NF_DIETARY_FIBER, nf_daietry_fiber[position]);
        in.putExtra(Constants.NF_SUGAR, nf_sugar[position]);
        startActivity(in);

    }

    /*
     *  Add More Buttons To Show More Items in the List View
     */
    public void addMore(View v)
    {
        if(isConnectingToInternet()==true)
        {s="main";
            //tvdesc3.setText("Type");
            contactList.clear();

            adapter=new ListViewAdapter(Go.this, MarkerList);
            lsview.setAdapter(adapter);


            addmore.setVisibility(View.GONE);
            imgloadmore.setVisibility(View.GONE);
        }
        else
        {
            Message();
        }

    }

	/*
	 * @ JSON PARSER
	 */

    private void parserJSON(String response) throws JSONException {
        // TODO Auto-generated method stub
        JSONObject json1=new JSONObject(response);

//        Log.d("hits",json1.getString(Constants.TOTAL_HITS));

        //    Log.d("MaxScore",json1.getString(Constants.MAX_SCORE));

        JSONArray jArray=json1.getJSONArray("response");

        if(jArray.length()==0)
        {
            status=false;
            tvstatus.setVisibility(View.VISIBLE);
        }

        else
        {
            status=true;
            item_name=new String[jArray.length()];
            brand_name=new String[jArray.length()];
            serving_size_qty=new String[jArray.length()];
            serving_size_unit=new String[jArray.length()];
            serving_size_gram=new String[jArray.length()];
            nf_calories=new String[jArray.length()];
            nf_calories_from_fat=new String[jArray.length()];
            total_fat=new String[jArray.length()];
            saturated_fat=new String[jArray.length()];
            poly_sat_fat=new String[jArray.length()];
            nf_cholesterol=new String[jArray.length()];
            nf_sodium=new String[jArray.length()];
            total_carbohydrates=new String[jArray.length()];
            nf_proteins=new String[jArray.length()];
            nf_vitamin_A=new String[jArray.length()];
            nf_vitamin_C=new String[jArray.length()];
            nf_calcium=new String[jArray.length()];
            nf_iron=new String[jArray.length()];
            nf_daietry_fiber=new String[jArray.length()];
            nf_sugar=new String[jArray.length()];
            for(int i=0;i<jArray.length();i++)
            {
                JSONObject subjsonhits=jArray.getJSONObject(i);
                HashMap<String, String> contact = new HashMap<String, String>();
            /*    Log.d("Index",subjsonhits.getString(Constants._INDEX));
                Log.d("Type",subjsonhits.getString(Constants._TYPE));
                Log.d("IDs",subjsonhits.getString(Constants._ID));*/
                JSONObject subhitsFields=subjsonhits.getJSONObject(Constants.FIELDS);
             /*   Log.d("item_id",subhitsFields.getString(Constants.ITEM_ID));
                Log.d("item_name",subhitsFields.getString(Constants.ITEM_NAME));
                Log.d("brand_name",subhitsFields.getString(Constants.BRAND_NAME));
                Log.d("nf_calories",subhitsFields.getString(Constants.NF_CALORIES));
                Log.d("nf_serving_size_qty",subhitsFields.getString(Constants.NF_SERVING_SIZE_QTY));
                Log.d("nf_unit",subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT));
                Log.d("","*****************************************************************");
                Log.d(Constants._TYPE,subjsonhits.getString(Constants._TYPE));
                Log.d(Constants.NF_CALORIES,subhitsFields.getString(Constants.NF_CALORIES));
                Log.d(Constants._TYPE,subhitsFields.getString(Constants.NF_SODIUM));
                Log.d(Constants._TYPE,subhitsFields.getString(Constants.NF_TOTAL_FAT));
*/
                item_name[i]=subhitsFields.getString(Constants.ITEM_NAME);
                brand_name[i]=subhitsFields.getString(Constants.BRAND_NAME);
                serving_size_qty[i]=subhitsFields.getString(Constants.NF_SERVING_SIZE_QTY);
                serving_size_unit[i]=subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT);
                serving_size_gram[i]=subhitsFields.getString(Constants.NF_SERVING_WEIGHT_GRAMS);
                nf_calories[i]=subhitsFields.getString(Constants.NF_CALORIES);
                nf_calories_from_fat[i]=subhitsFields.getString(Constants.NF_CALORIES_FROM_FAT);
                total_fat[i]=subhitsFields.getString(Constants.NF_TOTAL_FAT);
                saturated_fat[i]=subhitsFields.getString(Constants.NF_SATURATED_FAT);
                poly_sat_fat[i]=subhitsFields.getString(Constants.NF_POLY_SATURATED_FAT);
                nf_cholesterol[i]=subhitsFields.getString(Constants.NF_CHOLESTEROL);
                nf_sodium[i]=subhitsFields.getString(Constants.NF_SODIUM);
                total_carbohydrates[i]=subhitsFields.getString(Constants.NF_TOTAL_CARBOHYDRATES);
                nf_proteins[i]=subhitsFields.getString(Constants.NF_PROTEINS);
                nf_vitamin_A[i]=subhitsFields.getString(Constants.NF_VITAMIN_A);
                nf_vitamin_C[i]=subhitsFields.getString(Constants.NF_VITAMIN_C);
                nf_calcium[i]=subhitsFields.getString(Constants.NF_CALCIUM);
                nf_iron[i]=subhitsFields.getString(Constants.NF_IRON);
                nf_daietry_fiber[i]=subhitsFields.getString(Constants.NF_DIETARY_FIBER);
                nf_sugar[i]=subhitsFields.getString(Constants.NF_SUGAR);


                //  contact.put(Constants._TYPE,subjsonhits.getString(Constants._TYPE));Slicesbottle





                if(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("slice,large")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("Slices")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("Slice")
                        ||(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("slice thin/small"))
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("NLEA serving")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("bread")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("each")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("pizza")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("whole pizza")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("slice (1 oz)")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("slice,thin")
                        ||(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("slice thin, crust not eaten"))
                        ||(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("slice, thick/large (1/2\" thick)"))
                        ||(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("Cherry Tomato, Raw - 1 slice, medium (1/4\" thick)"))
                        ||(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("slice, very thin"))
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("Loaf")
                        )
                    contact.put("path",String.valueOf(R.drawable.flat_hand));
                else if(
                        subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("salad")
                                || 		subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("fruit without refuse")
                                || 		subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("bunch")
                                || 		subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("Order")
                                || 		subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("package (10 oz)")
                        )
                {
                    contact.put("path",String.valueOf(R.drawable.two_fist));
                }

                else if(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("can")

                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("bottle"))
                {
                    contact.put("path",String.valueOf(R.drawable.bottle));
                }



                else if(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("Oz")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("fl oz")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("oz."))
                {
                    contact.put("path",String.valueOf(R.drawable.glass));
                }
                else if(
                        subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("tbsp")
                                ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("tsp")
                                ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("grape")
                                ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("scoop"))
                {
                    contact.put("path",String.valueOf(R.drawable.spoon));
                }
                else if(
                        (subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("pat"))

                        )
                {
                    contact.put("path",String.valueOf(R.drawable.pointer_finger));
                }

                else if(
                        subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("tea bag")
                                ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("bag")
                                ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("packets")
                                ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("package")
                        )
                {
                    contact.put("path",String.valueOf(R.drawable.bag));
                }




                else if(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("serving")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("pie")

                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("Whole Calzone")
                        )

                    contact.put("path",String.valueOf(R.drawable.one_fist));

                else if(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("cup")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("cups")
                        ||(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("cup, shredded"))
                        ||(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("cup, melted"))
                        ||(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("cup, chopped or sliced"))
                        ||(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("cup, diced"))
                        ||(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("cup, crumbs"))
                        ||(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("cup, cubes"))
                        ||(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("cup,slices"))
                        ||(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("cup pieces"))
                        ||(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase(" cup, quartered or chopped"))
                        ||(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("cup cherry tomatoes")))
                    contact.put("path",String.valueOf(R.drawable.cup));

                else if(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("plum tomato")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("handful serving")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("apple")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("jar")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("bar")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("box")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("leaf")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("pieces")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("cookies")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("Italian tomato")

                        )
                {
                    contact.put("path",String.valueOf(R.drawable.handful));
                }
                else if(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("cherry")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("Cherry Tomato, Raw - 1 large whole (3\" dia)")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("Apple, raw - 1 medium (3\" dia)")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("large (3-1/4\" dia)")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("extra small (2-1/2\" dia)")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("small (2-3/4\" dia)")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("medium (3\" dia)")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("cup slices (1\" dia)")

                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("small whole (2-2/5\" dia)")



                        )
                {
                    contact.put("path",String.valueOf(R.drawable.dice));
                }
                else if(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("grams")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("g")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("gram"))
                {
                    int i1=(Math.round(Float.valueOf(subhitsFields.getString(Constants.NF_SERVING_SIZE_QTY))));//String.format("%.0f", Float.valueOf(subhitsFields.getString(Constants.NF_SERVING_SIZE_QTY))));

                    if(i1<50)
                        contact.put("path",String.valueOf(R.drawable.handful));
                    if(i1>50 && i1<100)
                        contact.put("path",String.valueOf(R.drawable.ball));
                    if(i1>140 && i1<200)
                        contact.put("path",String.valueOf(R.drawable.flat_hand));
                    if(i1>200 && i1<275)
                        contact.put("path",String.valueOf(R.drawable.cup));
                    if(i1>275 )
                        contact.put("path",String.valueOf(R.drawable.two_fist));
                }

                else if(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("sandwich")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("burger"))
                {
                    contact.put("path",String.valueOf(R.drawable.ball));
                }

                else if(subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("ounce")
                        ||subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT).equalsIgnoreCase("ounces")
                        )
                {

                    String val=subhitsFields.getString(Constants.NF_SERVING_SIZE_QTY).toString();
                    String str=String.valueOf(Math.ceil(Double.valueOf(val)));
                    try
                    {

                        int i2=1;


                        if(str.equals("")||str.equals(null))
                        {
                            i2=1;
                        }
                        else
                        {
                            i2=Integer.parseInt(str);
                        }

                        if(i2<2)
                            contact.put("path",String.valueOf(R.drawable.handful));
                        if(i2>2 && i2<4)
                            contact.put("path",String.valueOf(R.drawable.ball));
                        if(i2>4 && i2<7)
                            contact.put("path",String.valueOf(R.drawable.flat_hand));
                        if(i2>7 && i2<10)
                            contact.put("path",String.valueOf(R.drawable.cup));
                        if(i2>10 )
                            contact.put("path",String.valueOf(R.drawable.two_fist));

                    }
                    catch(Exception e)
                    {

                    }
                }
                else
                {
                    contact.put("path",String.valueOf(R.drawable.n_a));
                }

                //Managing Graph view

                if(!(subhitsFields.getString(Constants.NF_CALORIES).equalsIgnoreCase("null")||subhitsFields.getString(Constants.NF_CALORIES).equals("")))
                {
                    Float L=Float.valueOf(subhitsFields.getString(Constants.NF_CALORIES));
                    Float per=(L/fcal)*100;
//				    	Float marker=25.0f;
//				    	int x=Float.compare(marker, per);
//				    	if(x<0)
                    contact.put(Constants.NF_CALORIES,String.valueOf(per));
//				    	else
//				    	contact.put(Constants.NF_CALORIES,"500");
                }else
                {
                    contact.put(Constants.NF_CALORIES,subhitsFields.getString(Constants.NF_CALORIES));
                }

                if(!(subhitsFields.getString(Constants.NF_PROTEINS).equalsIgnoreCase("null")||subhitsFields.getString(Constants.NF_PROTEINS).equals("")))
                {
                    Float L=Float.valueOf(subhitsFields.getString(Constants.NF_PROTEINS));
                    Float per=(L/fpro)*100;
//				    	Float marker=25.0f;
//				    	int x=Float.compare(marker, per);
//				    	if(x<0)
                    contact.put(Constants.NF_PROTEINS,String.valueOf(per));
//				    	else
//				    		contact.put(Constants.NF_PROTEINS,"12");
                }else
                {
                    contact.put(Constants.NF_PROTEINS,subhitsFields.getString(Constants.NF_PROTEINS));
                }

                if(!(subhitsFields.getString(Constants.NF_SODIUM).equalsIgnoreCase("null")||subhitsFields.getString(Constants.NF_SODIUM).equals("")))
                {
                    Float L=Float.valueOf(subhitsFields.getString(Constants.NF_SODIUM));
                    Float per=(L/fsod)*100;
//				    	Float marker=25.0f;
//				    	int x=Float.compare(marker, per);
//				    	if(x<0)
//
                    contact.put(Constants.NF_SODIUM,String.valueOf(per));
//				    	else
//				    		contact.put(Constants.NF_SODIUM,"600");
                }else
                {
                    contact.put(Constants.NF_SODIUM,subhitsFields.getString(Constants.NF_SODIUM));
                }

                if(!(subhitsFields.getString(Constants.NF_TOTAL_FAT).equalsIgnoreCase("null")||subhitsFields.getString(Constants.NF_TOTAL_FAT).equals("")))
                {
                    Float L=Float.valueOf(subhitsFields.getString(Constants.NF_TOTAL_FAT));
                    Float per=(L/ffat)*100;
//				    	Float marker=25.0f;
//				    	int x=Float.compare(marker, per);
//				    	if(x<0)
                    contact.put(Constants.NF_TOTAL_FAT,String.valueOf(per));
//				    	else
//				    		contact.put(Constants.NF_TOTAL_FAT,"16");
                }else
                {
                    contact.put(Constants.NF_TOTAL_FAT, subhitsFields.getString(Constants.NF_TOTAL_FAT));
                }


                if(!(subhitsFields.getString(Constants.NF_TOTAL_CARBOHYDRATES).equalsIgnoreCase("null")||subhitsFields.getString(Constants.NF_TOTAL_CARBOHYDRATES).equals("")))
                {
                    Float L=Float.valueOf(subhitsFields.getString(Constants.NF_TOTAL_CARBOHYDRATES));
                    Float per=(L/fcarbs)*100;
//				    	Float marker=25.0f;
//				    	int x=Float.compare(marker, per);
//				    	if(x<0)
                    contact.put(Constants.NF_TOTAL_CARBOHYDRATES,String.valueOf(per));
//				    	else
//				    	contact.put(Constants.NF_TOTAL_CARBOHYDRATES,"75");
                }
                else
                {
                    contact.put(Constants.NF_TOTAL_CARBOHYDRATES,subhitsFields.getString(Constants.NF_TOTAL_CARBOHYDRATES));
                }
                contact.put("carbsval",subhitsFields.getString(Constants.NF_TOTAL_CARBOHYDRATES));
                contact.put("fatval", subhitsFields.getString(Constants.NF_TOTAL_FAT));
                contact.put("sodval",subhitsFields.getString(Constants.NF_SODIUM));
                contact.put("proval",subhitsFields.getString(Constants.NF_PROTEINS));
                contact.put("caloval",subhitsFields.getString(Constants.NF_CALORIES));

                contact.put(Constants.NF_DIETARY_FIBER,subhitsFields.getString(Constants.NF_DIETARY_FIBER));


                if(countExclaimItemName(subhitsFields.getString(Constants.ITEM_NAME))>0){

                    contact.put(Constants.ITEM_NAME,subhitsFields.getString(Constants.ITEM_NAME).substring(0, countExclaimItemName(subhitsFields.getString(Constants.ITEM_NAME))));
                }
                else
                {
                    contact.put(Constants.ITEM_NAME, subhitsFields.getString(Constants.ITEM_NAME));
                }
                contact.put(Constants.BRAND_NAME,subhitsFields.getString(Constants.BRAND_NAME));
                contact.put(Constants.NF_SERVING_SIZE_UNIT,subhitsFields.getString(Constants.NF_SERVING_SIZE_UNIT));
                contact.put(Constants.NF_SATURATED_FAT,subhitsFields.getString(Constants.NF_SATURATED_FAT));
                contact.put(Constants.NF_CHOLESTEROL,subhitsFields.getString(Constants.NF_CHOLESTEROL));
                contact.put(Constants.NF_VITAMIN_A,subhitsFields.getString(Constants.NF_VITAMIN_A));
                contact.put(Constants.NF_VITAMIN_C,subhitsFields.getString(Constants.NF_VITAMIN_C));
                contact.put(Constants.NF_IRON,subhitsFields.getString(Constants.NF_IRON));
                contact.put(Constants.NF_CALCIUM,subhitsFields.getString(Constants.NF_CALCIUM));
                String qty=null;
                if(((Integer.valueOf(((String.format("%.0f", Float.valueOf(subhitsFields.getString(Constants.NF_SERVING_SIZE_QTY)))))))) <1)
                {
                    qty=String.valueOf((String.format("%.0f", Float.valueOf(subhitsFields.getString(Constants.NF_SERVING_SIZE_QTY))*10)));
                }
                else
                {
                    qty=String.valueOf((Float.valueOf(subhitsFields.getString(Constants.NF_SERVING_SIZE_QTY))));
                }
                contact.put(Constants.NF_SERVING_SIZE_QTY,qty);
                contact.put(Constants.NF_SERVING_WEIGHT_GRAMS,subhitsFields.getString(Constants.NF_SERVING_WEIGHT_GRAMS));
                if(i<10)
                    contactList.add(i,contact);

                MarkerList.add(i,contact);
            }

        }
    }
    //@method to get the length of the shared prefrances


    public int countExclaimItemName(String string) {
        int count = 0;
        if(string.indexOf("-")==-1)
        {
            count=0;
        }
        else
        {
            count=string.indexOf("-");
        }
        return count;
    }
    public class Search extends AsyncTask<String, String, String>

    {



        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Go.this,AlertDialog.THEME_HOLO_DARK);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(true);
            pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.styeprogress));

            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            // Creating service handler class instance
            HttpParams httpParameters = new BasicHttpParams();
            // Set the timeout in milliseconds until a connection is established.
            // The default value is zero, that means the timeout is not used.
            int timeoutConnection = 3000;
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
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();
                    // Log.d("Values are here", responseString);
                } else{
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

            try {
                if(responseString.equals("")||responseString.equals("null")||responseString.equals(null))
                {
                    Message();
                }
                else  if(!responseString.equals("")||!responseString.equals("null")||!responseString.equals(null))
                {
                    parserJSON(responseString);
                    if(status==false)
                    {
                        Message();
                    }
                }
                else
                {
                    Message();
                }
            }
            catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(!(responseString.equals(null)))
            {


                if(status==false)
                {
                    tvstatus.setVisibility(View.VISIBLE);
                }
                else
                {
                    tvstatus.setVisibility(View.GONE);
                    ListViewAdapter adapter=new ListViewAdapter(Go.this, contactList);

                    lsview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
}
            else
            {
                tvstatus.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(result);

        }
    }
    public void clearText(View v)
    {
        edtsearch.setText("");
        imageView.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.go, menu);
        this.menu=menu;
        search_item=menu.findItem(R.id.Search);
        search_item.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent=new Intent(Go.this,Daily_Goal.class);
            startActivity(intent);

            return true;
        }

        if(id==android.R.id.home)
        {
            onBackPressed();

            return true;
        }


        if(id==R.id.Search)
        {
            rel_search.setVisibility(View.VISIBLE);
            search_item.setVisible(false);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
    public boolean isConnectingToInternet(){
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private void Message() {
        // TODO Auto-generated method stub

        rel_search.setVisibility(View.VISIBLE);
        addmore.setVisibility(View.GONE);
        imgloadmore.setVisibility(View.GONE);
        Toast.makeText(this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
    }

	    /*
	     * Now ListViewAdapter Class
	     */

    int index = 0;
    public class ListViewAdapter extends BaseAdapter {

        int iTOTAL_WIDTH = 105;
        Viewholder holder;
        public ArrayList<HashMap<?, ?>> list;
        public ArrayList<Integer> add;
        Activity activity;
        boolean[] checkedItems;
        boolean[] viewText;
        boolean[] imagemore;
        boolean[] ImageShow;
        boolean[] checkboxshow1, checkboxshow2, checkboxshow3, checkboxshow4, checkboxshow5, checkboxshow6, checkboxshow7, checkboxshow8;
        Boolean[] Exactviewckbox;

        boolean[] Hide, Hidemain, Hidden, HideAnimation, HideAddview;
        ArrayList<HashMap<?, ?>> duplicates;
        String[] ckboxTextAdd;
        DbAdapter db;
        int icount = 0;

        /*
         * Constructor Calling
         */
        public ArrayList<HashMap<?, ?>> removeDuplicates(ArrayList<HashMap<?, ?>> list2) {
            ArrayList<HashMap<?, ?>> DumiOriginalList = new ArrayList<HashMap<?, ?>>();
            DumiOriginalList = list2;
            System.out.println("dumillist" + DumiOriginalList);
            icount = list2.size();
            return DumiOriginalList;
        }


        public ListViewAdapter(Activity activity, ArrayList<HashMap<?, ?>> list) {
            // TODO Auto-generated constructor stub
            super();
            this.list = removeDuplicates(list);
            this.activity = activity;
            add = new ArrayList<Integer>(list.size());
            db = new DbAdapter(activity);
            db.open();

            checkedItems = new boolean[list.size()];
            Hidden = new boolean[list.size()];
            Hide = new boolean[list.size()];
            Hidemain = new boolean[list.size()];
            viewText = new boolean[list.size()];
            imagemore = new boolean[list.size()];
            HideAnimation = new boolean[list.size()];
            HideAddview = new boolean[list.size()];
            ckboxTextAdd = new String[list.size()];
            ImageShow = new boolean[list.size()];
            checkboxshow1 = new boolean[list.size()];
            checkboxshow2 = new boolean[list.size()];
            checkboxshow3 = new boolean[list.size()];
            checkboxshow4 = new boolean[list.size()];
            checkboxshow5 = new boolean[list.size()];
            checkboxshow6 = new boolean[list.size()];
            checkboxshow7 = new boolean[list.size()];
            checkboxshow8 = new boolean[list.size()];
            for (int i = 0; i < list.size(); i++) {
                checkedItems[i] = false;
                ckboxTextAdd[i] = " Add";
                Hidden[i] = false;
                Hidemain[i] = false;
                Hide[i] = false;
                HideAnimation[i] = false;
                HideAddview[i] = false;
                viewText[i] = true;
                imagemore[i] = false;
                ImageShow[i] = false;
                checkboxshow1[i] = false;
                checkboxshow2[i] = false;
                checkboxshow3[i] = false;
                checkboxshow4[i] = false;
                checkboxshow5[i] = false;
                checkboxshow6[i] = false;
                checkboxshow7[i] = false;
                checkboxshow8[i] = false;
            }

        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        private void UpdateMe(String b_name, String pdt_name, String qty) {
            // TODO Auto-generated method stub
            db.UpdateNewBie(b_name, pdt_name, qty, indexvalue, keys.entrySet().size());
            for (int i = 0; i < keys.entrySet().size(); i++) {
                Log.e("msg  " + String.valueOf(i), String.valueOf(indexvalue[i]));
            }

        }

        @SuppressLint("NewApi")
        @Override
        public View getView(final int position2, View convertview, ViewGroup parent) {

            Typeface roboto_regular = Typeface.createFromAsset(getAssets(), "fonts/Roboto_Regular.ttf");
            Typeface roboto_light = Typeface.createFromAsset(getAssets(), "fonts/Roboto_Light.ttf");

            // TODO Auto-generated method stub
            final int position = position2;
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            final HashMap<?, ?> map = list.get(position);

            icountinex = 8;
            indexvalue = new Integer[8];
            for (int i = 0; i < icountinex; i++) {
                indexvalue[i] = 100;
            }

            if (convertview == null) {
                convertview = layoutInflater.inflate(R.layout.l_view, parent, false);
                holder = new Viewholder();
                holder.title = (TextView) convertview.findViewById(R.id.item_brand_name);
                holder.carbs = (TextView) convertview.findViewById(R.id.carbsview);
                holder.proteins = (TextView) convertview.findViewById(R.id.Protienview);
                holder.calories = (TextView) convertview.findViewById(R.id.caloriesview);
                holder.calories_val = (TextView) convertview.findViewById(R.id.cal_desc_val);
                holder.serving = (TextView) convertview.findViewById(R.id.serving_size);
                holder.proteins_val = (TextView) convertview.findViewById(R.id.prot_desc_val);
                holder.carbs_val = (TextView) convertview.findViewById(R.id.carbs_desc_val);
                holder.tvbrand = (TextView) convertview.findViewById(R.id.serving_size_grams);
                holder.holeaddview = (LinearLayout) convertview.findViewById(R.id.add_view_hole);
                holder.detailpageitem = (TextView) convertview.findViewById(R.id.tvitemnameshow);
                holder.detailbrand = (TextView) convertview.findViewById(R.id.detailbrand);
                //holder.detailserving=(TextView)convertview.findViewById(R.id.detailserving);
                holder.tvamountserving = (TextView) convertview.findViewById(R.id.tvSatfatShow);
                holder.tvamountserving.setTypeface(roboto_light);
                holder.tvitem_name = (TextView) convertview.findViewById(R.id.tvitemnameshow);
                holder.tvTitle = (TextView) convertview.findViewById(R.id.tvtitlenameshow);
                holder.tvitem_name.setTypeface(roboto_regular);
                holder.tvbrand_name = (TextView) convertview.findViewById(R.id.tvbrandnamedetailshow);
                holder.tvserving_size_unit = (TextView) convertview.findViewById(R.id.tvitemdetailsShow);
                holder.tvnf_calories = (TextView) convertview.findViewById(R.id.tvcaloriesValueShow);
                holder.tvnf_calories.setTypeface(roboto_regular);
                holder.tvtotal_fat = (TextView) convertview.findViewById(R.id.tvFatValueShow);
                holder.tvtotal_fat.setTypeface(roboto_regular);
                holder.tvsaturated_fat = (TextView) convertview.findViewById(R.id.tvSatFatValueShow);
                holder.tvsaturated_fat.setTypeface(roboto_regular);
                holder.tvnf_cholesterol = (TextView) convertview.findViewById(R.id.tvCholestrolValueShow);
                holder.tvnf_cholesterol.setTypeface(roboto_regular);
                holder.tvnf_sodium = (TextView) convertview.findViewById(R.id.tvSodiumValueShow);
                holder.tvnf_sodium.setTypeface(roboto_regular);
                holder.tvtotal_carbohydrates = (TextView) convertview.findViewById(R.id.tvCarbsValueShow);
                holder.tvtotal_carbohydrates.setTypeface(roboto_regular);
                holder.tvnf_proteins = (TextView) convertview.findViewById(R.id.tvProteinValueShow);
                holder.tvnf_proteins.setTypeface(roboto_regular);
                holder.tvnf_vitamin_A = (TextView) convertview.findViewById(R.id.tvVitminAValueShow);
                holder.tvnf_vitamin_A.setTypeface(roboto_regular);
                holder.tvnf_vitamin_C = (TextView) convertview.findViewById(R.id.tvVitminBValueShow);
                holder.tvnf_vitamin_C.setTypeface(roboto_regular);
                holder.tvcalorieGraph = (TextView) convertview.findViewById(R.id.tvCaloriePerGraphValue);
                holder.tvCaloriePer = (TextView) convertview.findViewById(R.id.tvCaloriesPerValueShow);
                holder.tvfatGraph = (TextView) convertview.findViewById(R.id.tvFatPerGraphValue);
                holder.tvfatPer = (TextView) convertview.findViewById(R.id.tvFatPerValueShow);
                holder.tvCholestrolGraph = (TextView) convertview.findViewById(R.id.tvCholestrolPerGraphValue);
                holder.tvCholestrolPer = (TextView) convertview.findViewById(R.id.tvCholestrolPerValueShow);
                holder.tvcarbsGraph = (TextView) convertview.findViewById(R.id.tvCarbsPerGraphValue);
                holder.tvcarbsPer = (TextView) convertview.findViewById(R.id.tvCarbsPerValueShow);
                holder.tvsodGraph = (TextView) convertview.findViewById(R.id.tvSodiumPerGraphValue);
                holder.tvsodPer = (TextView) convertview.findViewById(R.id.tvSodiumPerValueShow);
                holder.tvproGraph = (TextView) convertview.findViewById(R.id.tvProteinPerGraphValue);
                holder.tvproPer = (TextView) convertview.findViewById(R.id.tvProteinPerValueShow);
                holder.tvsatfatGraph = (TextView) convertview.findViewById(R.id.tvSatFatPerGraphValue);
                holder.tvsatfatPer = (TextView) convertview.findViewById(R.id.tvSatFatPerValueShow);
                holder.tvpervitA = (TextView) convertview.findViewById(R.id.tvVitminAPerValue);
                holder.tvpervitc = (TextView) convertview.findViewById(R.id.tvVitminBPerValue);
                holder.addfood = (ImageView) convertview.findViewById(R.id.addfood);
                holder.chk1 = (CheckBox) convertview.findViewById(R.id.ckbox1);
                holder.chk2 = (CheckBox) convertview.findViewById(R.id.ckbox2);
                holder.chk3 = (CheckBox) convertview.findViewById(R.id.ckbox3);
                holder.chk4 = (CheckBox) convertview.findViewById(R.id.ckbox4);
                holder.chk5 = (CheckBox) convertview.findViewById(R.id.ckbox5);
                holder.chk6 = (CheckBox) convertview.findViewById(R.id.ckbox6);
                holder.chk7 = (CheckBox) convertview.findViewById(R.id.ckbox7);
                holder.chk8 = (CheckBox) convertview.findViewById(R.id.ckbox8);
                holder.ok = (Button) convertview.findViewById(R.id.btnaddOk);
                holder.cancel = (Button) convertview.findViewById(R.id.btnaddCancel);
                holder.imgplus_slide_next = (ImageView) convertview.findViewById(R.id.img_left_view);
                holder.imgplus = (ImageView) convertview.findViewById(R.id.imageplus);
                holder.imgminus = (ImageView) convertview.findViewById(R.id.imageminus);
                holder.detailcalory = (TextView) convertview.findViewById(R.id.detailcalory);
                holder.detailcaloryone = (TextView) convertview.findViewById(R.id.detailcaory1);
                holder.detailcarbo = (TextView) convertview.findViewById(R.id.detailcarbo);
                holder.detailcarbo1 = (TextView) convertview.findViewById(R.id.detailcarbo1);
                holder.detailprotein = (TextView) convertview.findViewById(R.id.detailprotein);
                holder.detailprotein1 = (TextView) convertview.findViewById(R.id.detailprotein1);
                holder.detailfiber = (TextView) convertview.findViewById(R.id.detailfiber);
                holder.detailfiber1 = (TextView) convertview.findViewById(R.id.detailfiber1);
                holder.detailsodium = (TextView) convertview.findViewById(R.id.detailsodium);
                holder.detailsodium1 = (TextView) convertview.findViewById(R.id.detailsodium1);
                holder.edtserving_size_qty = (TextView) convertview.findViewById(R.id.servingg);
                holder.edtserving_size_qty.setTypeface(roboto_light);

                convertview.setTag(holder);
            }

            else {
                holder = (Viewholder) convertview.getTag();
            }

            holder.imgplus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    RelativeLayout tableParentRow = (RelativeLayout) v.getParent().getParent().getParent().getParent();
                    LinearLayout tablerow = (LinearLayout) v.getParent().getParent().getParent();
                    RelativeLayout vwParentRow1 = (RelativeLayout) v.getParent();
                    TextView edt = (TextView) vwParentRow1.findViewById(R.id.servingg);
                    TextView tvcal = (TextView) tableParentRow.findViewById(R.id.tvcaloriesValueShow);
                    TextView tvcal1 = (TextView) tablerow.findViewById(R.id.tvCaloriePerGraphValue);
                    TextView tvcal2 = (TextView) tablerow.findViewById(R.id.tvCaloriesPerValueShow);
                    TextView tvpro = (TextView) tableParentRow.findViewById(R.id.tvProteinValueShow);
                    TextView tvpro1 = (TextView) tablerow.findViewById(R.id.tvProteinPerGraphValue);
                    TextView tvpro2 = (TextView) tablerow.findViewById(R.id.tvProteinPerValueShow);
                    TextView tvcarbo = (TextView) tableParentRow.findViewById(R.id.tvCarbsValueShow);
                    TextView tvcarbo1 = (TextView) tablerow.findViewById(R.id.tvCarbsPerGraphValue);
                    TextView tvcarbo2 = (TextView) tablerow.findViewById(R.id.tvCarbsPerValueShow);
                    TextView tvsod = (TextView) tableParentRow.findViewById(R.id.tvSodiumValueShow);
                    TextView tvsod1 = (TextView) tablerow.findViewById(R.id.tvSodiumPerGraphValue);
                    TextView tvsod2 = (TextView) tablerow.findViewById(R.id.tvSodiumPerValueShow);
                    TextView tvcho = (TextView) tableParentRow.findViewById(R.id.tvCholestrolValueShow);
                    TextView tvcho1 = (TextView) tablerow.findViewById(R.id.tvCholestrolPerGraphValue);
                    TextView tvcho2 = (TextView) tablerow.findViewById(R.id.tvCholestrolPerValueShow);
                    TextView tvsat = (TextView) tableParentRow.findViewById(R.id.tvSatFatValueShow);
                    TextView tvsat1 = (TextView) tablerow.findViewById(R.id.tvSatFatPerGraphValue);
                    TextView tvsat2 = (TextView) tablerow.findViewById(R.id.tvSatFatPerValueShow);
                    TextView tvtotfat = (TextView) tableParentRow.findViewById(R.id.tvFatValueShow);
                    TextView tvtotfat1 = (TextView) tablerow.findViewById(R.id.tvFatPerGraphValue);
                    TextView tvtotfat2 = (TextView) tablerow.findViewById(R.id.tvFatPerValueShow);


                    counter = Integer.parseInt(edt.getText().toString());
                    if (counter > 0) {
                        counter++;
                        edt.setText(Integer.toString(counter));
                        System.out.println(counter + "counter");
                        if (!(Constants.NF_CALCIUM.equalsIgnoreCase("null") || Constants.NF_CALCIUM.equalsIgnoreCase("0"))) {
                        }
                    } else
                        Toast.makeText(Go.this, "Quantity can't less than 1", Toast.LENGTH_LONG).show();
                    Float i;
                    if (Integer.toString(counter).equals("") || Integer.toString(counter).equals(null)) {
                        i = 0.0f;
                    } else {
                        i = Float.valueOf(Integer.toString(counter));

                    }
                    if (i > 0) {
                        Float j = Float.valueOf(map.get(Constants.NF_SERVING_SIZE_QTY).toString());
                        Log.d("Values", "In grams calling");
                        if (!map.get("caloval").equals("null") && !map.get("caloval").equals(null)) {
                            Log.d("Values", "changed");
                            Float L = Float.valueOf(map.get("caloval").toString());
                            Float K = ((L / j)) * i;
                            Float per = (K / 2000) * 100;
                            tvcal2.setText("(" + String.valueOf(Math.round(per)) + "%)");
                            tvcal1.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                            tvcal.setText(String.format("%.2f", K) + "    K");
                        }
                        if (!map.get("proval").equals("null") && !map.get("proval").equals(null)) {
                            Float L = Float.valueOf(map.get("proval").toString());
                            Float K = ((L / j)) * i;
                            Float per = (K / 50) * 100;
                            tvpro2.setText("(" + String.valueOf(Math.round(per)) + "%)");
                            tvpro1.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                            tvpro.setText(String.format("%.2f", K) + "    g" + "  (" + String.format("%.1f", per) + "%)");

                        }
                        if (!map.get("fatval").equals("null") && !map.get("fatval").equals(null)) {
                            Float L = Float.valueOf(map.get("fatval").toString());
                            Float K = ((L / j)) * i;
                            Float per = (K / 65) * 100;
                            tvtotfat2.setText("(" + String.valueOf(Math.round(per)) + "%)");
                            tvtotfat1.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                            tvtotfat.setText(String.format("%.2f", K) + "    g");
                        }
                        if (!map.get(Constants.NF_SATURATED_FAT).equals("null") && !map.get(Constants.NF_SATURATED_FAT).equals(null)) {
                            Float L = Float.valueOf(map.get(Constants.NF_SATURATED_FAT).toString());
                            Float K = ((L / j)) * i;
                            Float per = (K / 24) * 100;
                            tvsat2.setText("(" + String.valueOf(Math.round(per)) + "%)");
                            tvsat1.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                            tvsat.setText(String.format("%.2f", K) + "    g");
                        }
                        if (!map.get(Constants.NF_CHOLESTEROL).equals("null") && !map.get(Constants.NF_CHOLESTEROL).equals(null)) {
                            Float L = Float.valueOf(map.get(Constants.NF_CHOLESTEROL).toString());
                            Float K = ((L / j)) * i;
                            Float per = (K / 300) * 100;
                            tvcho2.setText("(" + String.valueOf(Math.round(per)) + "%)");
                            tvcho1.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                            tvcho.setText(String.format("%.2f", K) + "    mg");

                        }
                        if (!map.get("sodval").equals("null") && !map.get("sodval").equals(null)) {
                            Float L = Float.valueOf(map.get("sodval").toString());
                            Float K = ((L / j)) * i;
                            Float per = (K / 2400) * 100;
                            tvsod2.setText("(" + String.valueOf(Math.round(per)) + "%)");
                            tvsod1.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                            tvsod.setText(String.format("%.2f", K) + "    mg" + "  (" + String.format("%.1f", per) + "%)");

                        }
                        if (!map.get("carbsval").equals("null") && !map.get("carbsval").equals(null)) {
                            Float L = Float.valueOf(map.get("carbsval").toString());
                            Float K = ((L / j)) * i;
                            Float per = (K / 300) * 100;
                            tvcarbo2.setText("(" + String.valueOf(Math.round(per)) + "%)");
                            tvcarbo1.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                            tvcarbo.setText(String.format("%.2f", K) + "    g" + "  (" + String.format("%.1f", per) + "%)");

                        }


                    }
                }
            });
            holder.imgminus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    RelativeLayout tableParentRow = (RelativeLayout) v.getParent().getParent().getParent().getParent();
                    LinearLayout tablerow = (LinearLayout) v.getParent().getParent().getParent();
                    RelativeLayout vwParentRow1 = (RelativeLayout) v.getParent();
                    TextView edt = (TextView) vwParentRow1.findViewById(R.id.servingg);
                    TextView tvcal = (TextView) tableParentRow.findViewById(R.id.tvcaloriesValueShow);
                    TextView tvcal1 = (TextView) tablerow.findViewById(R.id.tvCaloriePerGraphValue);
                    TextView tvcal2 = (TextView) tablerow.findViewById(R.id.tvCaloriesPerValueShow);
                    TextView tvpro = (TextView) tableParentRow.findViewById(R.id.tvProteinValueShow);
                    TextView tvpro1 = (TextView) tablerow.findViewById(R.id.tvProteinPerGraphValue);
                    TextView tvpro2 = (TextView) tablerow.findViewById(R.id.tvProteinPerValueShow);
                    TextView tvcarbo = (TextView) tableParentRow.findViewById(R.id.tvCarbsValueShow);
                    TextView tvcarbo1 = (TextView) tablerow.findViewById(R.id.tvCarbsPerGraphValue);
                    TextView tvcarbo2 = (TextView) tablerow.findViewById(R.id.tvCarbsPerValueShow);
                    TextView tvsod = (TextView) tableParentRow.findViewById(R.id.tvSodiumValueShow);
                    TextView tvsod1 = (TextView) tablerow.findViewById(R.id.tvSodiumPerGraphValue);
                    TextView tvsod2 = (TextView) tablerow.findViewById(R.id.tvSodiumPerValueShow);
                    TextView tvcho = (TextView) tableParentRow.findViewById(R.id.tvCholestrolValueShow);
                    TextView tvcho1 = (TextView) tablerow.findViewById(R.id.tvCholestrolPerGraphValue);
                    TextView tvcho2 = (TextView) tablerow.findViewById(R.id.tvCholestrolPerValueShow);
                    TextView tvsat = (TextView) tableParentRow.findViewById(R.id.tvSatFatValueShow);
                    TextView tvsat1 = (TextView) tablerow.findViewById(R.id.tvSatFatPerGraphValue);
                    TextView tvsat2 = (TextView) tablerow.findViewById(R.id.tvSatFatPerValueShow);
                    TextView tvtotfat = (TextView) tableParentRow.findViewById(R.id.tvFatValueShow);
                    TextView tvtotfat1 = (TextView) tablerow.findViewById(R.id.tvFatPerGraphValue);
                    TextView tvtotfat2 = (TextView) tablerow.findViewById(R.id.tvFatPerValueShow);
                    counter = Integer.parseInt(edt.getText().toString());
                    if (counter > 1) {
                        counter--;
                        edt.setText(Integer.toString(counter));
                        System.out.println(counter + "counter");
                        if (!(Constants.NF_CALCIUM.equalsIgnoreCase("null") || Constants.NF_CALCIUM.equalsIgnoreCase("0"))) {
                        }
                    } else
                        Toast.makeText(Go.this, "Quantity can't less than 1", Toast.LENGTH_LONG).show();
                    Float i;
                    if (Integer.toString(counter).equals("") || Integer.toString(counter).equals(null)) {
                        i = 0.0f;
                    } else {
                        i = Float.valueOf(Integer.toString(counter));

                    }
                    if (i > 0) {
                        Float j = Float.valueOf(map.get(Constants.NF_SERVING_SIZE_QTY).toString());
                        Log.d("Values", "In grams calling");
                        if (!map.get("caloval").equals("null") && !map.get("caloval").equals(null)) {
                            Log.d("Values", "changed");
                            Float L = Float.valueOf(map.get("caloval").toString());
                            Float K = ((L / j)) * i;
                            Float per = (K / 2000) * 100;
                            tvcal2.setText("(" + String.valueOf(Math.round(per)) + "%)");
                            tvcal1.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                            tvcal.setText(String.format("%.2f", K) + "    K");
                        }
                        if (!map.get("proval").equals("null") && !map.get("proval").equals(null)) {
                            Float L = Float.valueOf(map.get("proval").toString());
                            Float K = ((L / j)) * i;
                            Float per = (K / 50) * 100;
                            tvpro2.setText("(" + String.valueOf(Math.round(per)) + "%)");
                            tvpro1.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                            tvpro.setText(String.format("%.2f", K) + "    g" + "  (" + String.format("%.1f", per) + "%)");

                        }
                        if (!map.get("fatval").equals("null") && !map.get("fatval").equals(null)) {
                            Float L = Float.valueOf(map.get("fatval").toString());
                            Float K = ((L / j)) * i;
                            Float per = (K / 65) * 100;
                            tvtotfat2.setText("(" + String.valueOf(Math.round(per)) + "%)");
                            tvtotfat1.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                            tvtotfat.setText(String.format("%.2f", K) + "    g");
                        }
                        if (!map.get(Constants.NF_SATURATED_FAT).equals("null") && !map.get(Constants.NF_SATURATED_FAT).equals(null)) {
                            Float L = Float.valueOf(map.get(Constants.NF_SATURATED_FAT).toString());
                            Float K = ((L / j)) * i;
                            Float per = (K / 24) * 100;
                            tvsat2.setText("(" + String.valueOf(Math.round(per)) + "%)");
                            tvsat1.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                            tvsat.setText(String.format("%.2f", K) + "    g");
                        }
                        if (!map.get(Constants.NF_CHOLESTEROL).equals("null") && !map.get(Constants.NF_CHOLESTEROL).equals(null)) {
                            Float L = Float.valueOf(map.get(Constants.NF_CHOLESTEROL).toString());
                            Float K = ((L / j)) * i;
                            Float per = (K / 300) * 100;
                            tvcho2.setText("(" + String.valueOf(Math.round(per)) + "%)");
                            tvcho1.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                            tvcho.setText(String.format("%.2f", K) + "    mg");

                        }
                        if (!map.get("sodval").equals("null") && !map.get("sodval").equals(null)) {
                            Float L = Float.valueOf(map.get("sodval").toString());
                            Float K = ((L / j)) * i;
                            Float per = (K / 2400) * 100;
                            tvsod2.setText("(" + String.valueOf(Math.round(per)) + "%)");
                            tvsod1.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                            tvsod.setText(String.format("%.2f", K) + "    mg" + "  (" + String.format("%.1f", per) + "%)");

                        }
                        if (!map.get("carbsval").equals("null") && !map.get("carbsval").equals(null)) {
                            Float L = Float.valueOf(map.get("carbsval").toString());
                            Float K = ((L / j)) * i;
                            Float per = (K / 300) * 100;
                            tvcarbo2.setText("(" + String.valueOf(Math.round(per)) + "%)");
                            tvcarbo1.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                            tvcarbo.setText(String.format("%.2f", K) + "    g" + "  (" + String.format("%.1f", per) + "%)");

                        }


                    }
                }
            });
            holder.edtserving_size_qty.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    RelativeLayout vwParentRow = (RelativeLayout) v.getParent();
                    TextView child = (TextView) vwParentRow.getChildAt(1);

                    if (!map.get(Constants.NF_SERVING_SIZE_QTY).equals("null")) {
                        System.out.println("firstif");
                        int i = Math.round(Float.valueOf(map.get(Constants.NF_SERVING_SIZE_QTY).toString()));
                        if (i > 0) {
                            System.out.println("secondif");
                            Log.i("value of order", String.valueOf(i));
                            child.setText(String.valueOf(i));
                        } else {
                            System.out.println("firstelse");
                            child.setText("1");
                        }
                    } else {
                        System.out.println("secondelse");
                        child.setText("1");
                    }
                }
            });

            holder.edtserving_size_qty.setText("1");
            holder.detailpageitem.setText(map.get(Constants.ITEM_NAME).toString());
            if (!map.get(Constants.BRAND_NAME).equals("null"))
                holder.detailbrand.setText(map.get(Constants.BRAND_NAME).toString());

            if (!serving_size_unit.equals("null"))
                holder.tvserving_size_unit.setText(map.get(Constants.NF_SERVING_SIZE_QTY) + " " + map.get(Constants.NF_SERVING_SIZE_UNIT));


            if (!map.get("caloval").equals("null")) {
                Float L = Float.valueOf(map.get("caloval").toString());
                Float per = (L / 2000) * 100;
                holder.tvCaloriePer.setText("(" + String.valueOf(Math.round(per)) + "%)");
                holder.tvcalorieGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                holder.tvnf_calories.setText(String.valueOf(L) + "    K");
            }
            if (!map.get(Constants.NF_TOTAL_FAT).equals("null")) {
                Float L = Float.valueOf(map.get(Constants.NF_TOTAL_FAT).toString());
                Float per = (L / 65) * 100;
                holder.tvfatPer.setText("(" + String.valueOf(Math.round(per)) + "%)");
                holder.tvfatGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                holder.tvtotal_fat.setText(String.valueOf(L) + "    g");
            }
            if (!map.get(Constants.NF_SATURATED_FAT).equals("null")) {
                Float L = Float.valueOf(map.get(Constants.NF_SATURATED_FAT).toString());

                Float per = (L / 24) * 100;
                holder.tvfatPer.setText("(" + String.valueOf(Math.round(per)) + "%)");
                holder.tvfatGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                holder.tvsaturated_fat.setText(map.get(Constants.NF_SATURATED_FAT) + "    g");

            }
            if (!map.get("fatval").equals("null")) {

                Float L = Float.valueOf(map.get("fatval").toString());
                Float per = (L / 300) * 100;
                holder.tvCholestrolPer.setText("(" + String.valueOf(Math.round(per)) + "%)");
                holder.tvCholestrolGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                holder.tvnf_cholesterol.setText(String.valueOf(L) + "    mg");
            }
            if (!map.get("sodval").equals("null")) {
                Float L = Float.valueOf(map.get("sodval").toString());
                Float per = (L / 2400) * 100;
                holder.tvsodPer.setText("(" + String.valueOf(Math.round(per)) + "%)");
                holder.tvsodGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                holder.tvnf_sodium.setText(String.valueOf(L) + "    mg");
            }
            if (!map.get("carbsval").equals("null")) {
                Float L = Float.valueOf(map.get("carbsval").toString());
                Float per = (L / 300) * 100;
                holder.tvcarbsPer.setText("(" + String.valueOf(Math.round(per)) + "%)");
                holder.tvcarbsGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                holder.tvtotal_carbohydrates.setText(String.valueOf(L) + "    g");
            }
            if (!map.get("proval").equals("null")) {
                Float L = Float.valueOf(map.get("proval").toString());
                Float per = (L / 50) * 100;
                holder.tvproPer.setText("(" + String.valueOf(Math.round(per)) + "%)");
                holder.tvproGraph.setWidth(Integer.parseInt(String.valueOf(Math.round(per))) * 2);
                holder.tvnf_proteins.setText(String.valueOf(L) + "    g");
            }
            if (!map.get(Constants.NF_VITAMIN_A).equals("null"))
                holder.tvnf_vitamin_A.setText(map.get(Constants.NF_VITAMIN_A) + "%");
            else
                holder.tvnf_vitamin_A.setText("0" + "%");
            if (!map.get(Constants.NF_VITAMIN_C).equals("null"))
                holder.tvnf_vitamin_C.setText(map.get(Constants.NF_VITAMIN_C) + "%");
            else
                holder.tvnf_vitamin_C.setText("0" + "%");

            if (!map.get(Constants.NF_VITAMIN_A).equals("null")) {
                holder.tvpervitA.setWidth((int) (Float.parseFloat(map.get(Constants.NF_VITAMIN_A).toString()) * 2));
            }
            if (!map.get(Constants.NF_VITAMIN_C).equals("null")) {
                holder.tvpervitc.setWidth((int) (Float.parseFloat(map.get(Constants.NF_VITAMIN_C).toString()) * 2));
            }
            if (!map.get("caloval").toString().equals("null")) {
                holder.detailcaloryone.setText(String.format("%.0f", Float.valueOf(map.get("caloval").toString())));
            } else {
                holder.detailcaloryone.setText("0");
            }

            if (!map.get("carbsval").toString().equals("null")) {
                holder.detailcarbo1.setText(String.format("%.0f", Float.valueOf(map.get("carbsval").toString())));
            } else {
                holder.detailcarbo1.setText("0");
            }
            if (!map.get("proval").toString().equals("null")) {
                holder.detailprotein1.setText(String.format("%.0f", Float.valueOf(map.get("proval").toString())));
            } else {
                holder.detailprotein1.setText("0");
            }
            if (!map.get("sodval").toString().equals("null")) {
                holder.detailsodium1.setText(String.format("%.0f", Float.valueOf(map.get("sodval").toString())));
            } else {
                holder.detailsodium1.setText("0");
            }
            if (!map.get("fatval").toString().equals("null")) {
                holder.detailfiber1.setText(String.format("%.0f", Float.valueOf(map.get("fatval").toString())));
            } else {
                holder.detailfiber1.setText("0");
            }

            String cal1 = map.get(Constants.NF_CALORIES).toString();


            if (countExclaim(cal1) > 0) {

                holder.detailcalory.setMaxHeight(195);
                holder.detailcalory.setHeight(Integer.parseInt(cal1.substring(0, countExclaim(cal1))) * detailmultiplyfactor_cal);
            } else {
                if (cal1.equals("null")) {
                    holder.detailcalory.setHeight(1);
                } else {
                    holder.detailcalory.setHeight(Integer.parseInt(cal1));
                }
            }


            String Fib1 = map.get(Constants.NF_TOTAL_FAT).toString();
            if (countExclaim(Fib1) > 0) {
                Log.d("value of Fiber", Fib1.substring(0, countExclaim(Fib1)));
                holder.detailfiber.setMaxHeight(195);
                holder.detailfiber.setHeight(Integer.parseInt(Fib1.substring(0, countExclaim(Fib1))) * detailmultiplyfactor_fiber);
            } else {
                if (Fib1.equals("null")) {
                    holder.detailfiber.setHeight(1);

                } else {

                    holder.detailfiber.setHeight(Integer.parseInt(Fib1));
                }
            }

            String sodium1 = map.get(Constants.NF_SODIUM).toString();

            if (countExclaim(sodium1) > 0) {
                Log.d("value of sodium", sodium1.substring(0, countExclaim(sodium1)));
                holder.detailsodium.setMaxHeight(195);
                holder.detailsodium.setHeight(Integer.parseInt(sodium1.substring(0, countExclaim(sodium1))) * detailmultiplyfactor_sod);
            } else {
                if (sodium1.equals("null")) {
                    holder.detailsodium.setHeight(1);
                } else {
                    holder.detailsodium.setHeight(Integer.parseInt(sodium1));
                }
            }
            String carbs1 = map.get(Constants.NF_TOTAL_CARBOHYDRATES).toString();
            if (countExclaim(carbs1) > 0) {
                holder.detailcarbo.setMaxHeight(195);
                holder.detailcarbo.setHeight(Integer.parseInt(carbs1.substring(0, countExclaim(carbs1))) * detailmultiplyfactor_carb);
            } else {
                if (carbs1.equals("null")) {
                    holder.detailcarbo.setHeight(1);
                } else {
                    holder.detailcarbo.setHeight(Integer.parseInt(carbs1));
                }
            }

            String Prote1 = map.get(Constants.NF_PROTEINS).toString();

            if (countExclaim(Prote1) > 0) {
                holder.detailprotein.setMaxHeight(195);
                holder.detailprotein.setHeight(Integer.parseInt(Prote1.substring(0, countExclaim(Prote1))) * detailmultiplyfactor_pro);
            } else {
                if (Prote1.equals("null")) {
                    holder.detailprotein.setHeight(1);
                } else {

                    holder.detailprotein.setHeight(Integer.parseInt(Prote1));
                }
            }

            //for the textview desc
            Exactviewckbox = new Boolean[keys.size()];
            final String[] items;
            items = new String[keys.entrySet().size()];
            for (int i = 0; i < keys.entrySet().size(); i++) {
                items[i] = sharedPref.getString("cat" + String.valueOf(i), "DO");
            }

            switch (keys.entrySet().size()) {

                case 1:
                    holder.chk1.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk1.setText(items[0]);
                    holder.chk1.setChecked(checkboxshow1[position]);
                    break;
                case 2:
                    holder.chk1.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk1.setText(items[0]);
                    holder.chk2.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk2.setText(items[1]);
                    holder.chk1.setChecked(checkboxshow1[position]);
                    holder.chk2.setChecked(checkboxshow2[position]);
                    break;
                case 3:
                    holder.chk1.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk1.setText(items[0]);
                    holder.chk2.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk2.setText(items[1]);
                    holder.chk3.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk3.setText(items[2]);
                    holder.chk1.setChecked(checkboxshow1[position]);
                    holder.chk2.setChecked(checkboxshow2[position]);
                    holder.chk3.setChecked(checkboxshow3[position]);
                    break;
                case 4:
                    holder.chk1.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk1.setText(items[0]);
                    holder.chk2.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk2.setText(items[1]);
                    holder.chk3.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk3.setText(items[2]);
                    holder.chk4.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk4.setText(items[3]);
                    holder.chk1.setChecked(checkboxshow1[position]);
                    holder.chk2.setChecked(checkboxshow2[position]);
                    holder.chk3.setChecked(checkboxshow3[position]);
                    holder.chk4.setChecked(checkboxshow4[position]);
                    break;
                case 5:
                    holder.chk1.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk1.setText(items[0]);
                    holder.chk2.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk2.setText(items[1]);
                    holder.chk3.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk3.setText(items[2]);
                    holder.chk4.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk4.setText(items[3]);
                    holder.chk5.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk5.setText(items[4]);
                    holder.chk1.setChecked(checkboxshow1[position]);
                    holder.chk2.setChecked(checkboxshow2[position]);
                    holder.chk3.setChecked(checkboxshow3[position]);
                    holder.chk4.setChecked(checkboxshow4[position]);
                    holder.chk5.setChecked(checkboxshow5[position]);
                    break;
                case 6:
                    holder.chk1.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk1.setText(items[0]);
                    holder.chk2.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk2.setText(items[1]);
                    holder.chk3.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk3.setText(items[2]);
                    holder.chk4.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk4.setText(items[3]);
                    holder.chk5.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk5.setText(items[4]);
                    holder.chk6.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk6.setText(items[5]);
                    holder.chk1.setChecked(checkboxshow1[position]);
                    holder.chk2.setChecked(checkboxshow2[position]);
                    holder.chk3.setChecked(checkboxshow3[position]);
                    holder.chk4.setChecked(checkboxshow4[position]);
                    holder.chk5.setChecked(checkboxshow5[position]);
                    holder.chk6.setChecked(checkboxshow6[position]);
                    break;
                case 7:
                    holder.chk1.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk1.setText(items[0]);
                    holder.chk2.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk2.setText(items[1]);
                    holder.chk3.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk3.setText(items[2]);
                    holder.chk4.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk4.setText(items[3]);
                    holder.chk5.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk5.setText(items[4]);
                    holder.chk6.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk6.setText(items[5]);
                    holder.chk7.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk7.setText(items[6]);
                    holder.chk1.setChecked(checkboxshow1[position]);
                    holder.chk2.setChecked(checkboxshow2[position]);
                    holder.chk3.setChecked(checkboxshow3[position]);
                    holder.chk4.setChecked(checkboxshow4[position]);
                    holder.chk5.setChecked(checkboxshow5[position]);
                    holder.chk6.setChecked(checkboxshow6[position]);
                    holder.chk7.setChecked(checkboxshow7[position]);

                    break;

                case 8:
                    holder.chk1.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk1.setText(items[0]);
                    holder.chk2.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk2.setText(items[1]);
                    holder.chk3.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk3.setText(items[2]);
                    holder.chk4.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk4.setText(items[3]);
                    holder.chk5.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk5.setText(items[4]);
                    holder.chk6.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk6.setText(items[5]);
                    holder.chk7.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk7.setText(items[6]);
                    holder.chk8.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
                    holder.chk8.setText(items[7]);
                    holder.chk1.setChecked(checkboxshow1[position]);
                    holder.chk2.setChecked(checkboxshow2[position]);
                    holder.chk3.setChecked(checkboxshow3[position]);
                    holder.chk4.setChecked(checkboxshow4[position]);
                    holder.chk5.setChecked(checkboxshow5[position]);
                    holder.chk6.setChecked(checkboxshow6[position]);
                    holder.chk7.setChecked(checkboxshow7[position]);
                    holder.chk8.setChecked(checkboxshow8[position]);

                    break;

                default:
                    break;

            }


            holder.chk1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) {
                        indexvalue[0] = 0;
                        Log.e("msg  " + String.valueOf(0), String.valueOf(indexvalue[0]));
                    } else {
                        indexvalue[0] = 100;
                    }
                }
            });
            holder.chk2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) {
                        indexvalue[1] = 1;
                        Log.e("msg  " + String.valueOf(1), String.valueOf(indexvalue[1]));
                    } else {
                        indexvalue[1] = 100;
                    }
                }
            });

            holder.chk3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) {
                        indexvalue[2] = 2;
                        Log.e("msg  " + String.valueOf(2), String.valueOf(indexvalue[2]));
                    } else {
                        indexvalue[2] = 100;
                    }
                }
            });

            holder.chk4.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) {
                        indexvalue[3] = 3;
                        Log.e("msg  " + String.valueOf(3), String.valueOf(indexvalue[3]));
                    } else {
                        indexvalue[3] = 100;
                    }
                }
            });

            holder.chk5.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) {
                        indexvalue[4] = 4;
                        Log.e("msg  " + String.valueOf(4), String.valueOf(indexvalue[4]));
                    } else {
                        indexvalue[4] = 100;
                    }
                }
            });

            holder.chk6.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) {
                        indexvalue[5] = 5;
                        Log.e("msg  " + String.valueOf(5), String.valueOf(indexvalue[5]));
                    } else {
                        indexvalue[5] = 100;
                    }
                }
            });

            holder.chk7.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) {
                        indexvalue[6] = 6;
                        Log.e("msg  " + String.valueOf(6), String.valueOf(indexvalue[6]));
                    } else {
                        indexvalue[6] = 100;
                    }
                }
            });

            holder.chk8.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) {
                        indexvalue[7] = 7;
                        Log.e("msg  " + String.valueOf(7), String.valueOf(indexvalue[7]));
                    } else {
                        indexvalue[7] = 100;
                    }
                }
            });

            holder.ok.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);
            holder.cancel.setVisibility(HideAddview[position] ? View.VISIBLE : View.GONE);

            final int kl = position;
            //On Okay Click +ve
            holder.ok.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    HideAddview[kl] = false;
                    final HashMap<?, ?> map = list.get(position);


                    String item_name = map.get(Constants.ITEM_NAME).toString().replace('\'', ' ');
                    String brand_name = map.get(Constants.BRAND_NAME).toString().replace('\'', ' ');
                    Log.e(item_name, brand_name);
                    // TODO Auto-generated method stub
                    Cursor cursor = db.fetch_data(item_name, brand_name);
                    if (cursor.getCount() > 0) {
                        for (int u = 0; u < indexvalue.length; u++) {
                            Log.e("value of indexvalue", String.valueOf(indexvalue[u]));
                        }
                        if (
                                indexvalue[0] < 100 &&
                                        indexvalue[1] < 100 &&
                                        indexvalue[2] < 100 &&
                                        indexvalue[3] < 100 &&
                                        indexvalue[4] < 100 &&
                                        indexvalue[5] < 100 &&
                                        indexvalue[6] < 100 &&
                                        indexvalue[7] < 100
                                ) {


                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setMessage("Already Saved")
                                    .setPositiveButton("Replace", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {

                                            checkedItems[position] = true;
                                            viewText[position] = false;
                                            notifyDataSetChanged();
                                            Log.i("value of text box trey", ckboxTextAdd[position]);
                                            //	 holder.ckbox.setText(ckboxTextAdd[position]);
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            checkedItems[position] = false;
                                            viewText[position] = true;
                                            notifyDataSetChanged();

                                            Log.i("value of text box false", ckboxTextAdd[position]);
                                            //holder.ckbox.setText(ckboxTextAdd[position]);

                                        }
                                    });

                            AlertDialog alertDialog = builder.create();

                            alertDialog.show();


                        } else {
                            Log.e("Hello", "I AAM HERE");


                            if (checkboxshow1[position]) {
                                indexvalue[0] = 0;
                            }

                            if (checkboxshow2[position]) {
                                indexvalue[1] = 1;
                            }
                            if (checkboxshow3[position]) {
                                indexvalue[2] = 2;
                            }
                            if (checkboxshow4[position]) {
                                indexvalue[3] = 3;
                            }
                            if (checkboxshow5[position]) {
                                indexvalue[4] = 4;
                            }
                            if (checkboxshow6[position]) {
                                indexvalue[5] = 5;
                            }
                            if (checkboxshow7[position]) {
                                indexvalue[6] = 6;
                            }
                            if (checkboxshow8[position]) {
                                indexvalue[7] = 7;
                            }


                            if (db.updatePreference(item_name, brand_name, keys.entrySet().size(), indexvalue)) {

                                db.updatePreference(item_name, brand_name, keys.entrySet().size(), indexvalue);
                                checkedItems[position] = true;
                                Log.e("Hello", "I AAM HERE INSIDE");
                                viewText[position] = false;
                                notifyDataSetChanged();
                            }
                        }
                    } else {
                        notifyDataSetChanged();
                        if (
                                indexvalue[0] == 100 &&
                                        indexvalue[1] == 100 &&
                                        indexvalue[2] == 100 &&
                                        indexvalue[3] == 100 &&
                                        indexvalue[4] == 100 &&
                                        indexvalue[5] == 100 &&
                                        indexvalue[6] == 100 &&
                                        indexvalue[7] == 100
                                ) {
                            Log.d("status", "Nothing is choosen");
                            checkedItems[position] = false;
                            viewText[position] = true;
                            notifyDataSetChanged();
                        } else {

                            //Now Saving the view
                            Log.d("status", "Something is choosen");

                            checkedItems[position] = true;
                            viewText[position] = false;

                            notifyDataSetChanged();

                            Log.i("value of Calories", map.get(Constants.NF_CALORIES).toString());
                            String qty = null;
                            if (((Integer.valueOf(((String.format("%.0f", Float.valueOf(map.get(Constants.NF_SERVING_SIZE_QTY).toString()))))))) < 1) {
                                qty = String.valueOf((String.format("%.0f", Float.valueOf(map.get(Constants.NF_SERVING_SIZE_QTY).toString()) * 10)));
                            } else {
                                qty = String.valueOf((Float.valueOf(map.get(Constants.NF_SERVING_SIZE_QTY).toString())));
                            }
                            db.createSave(
                                    position
                                    , map.get(Constants.BRAND_NAME).toString().replace('\'', ' ')
                                    , map.get(Constants.ITEM_NAME).toString().replace('\'', ' ')
                                    , map.get("path").toString()
                                    , qty
                                    , map.get(Constants.NF_SERVING_SIZE_UNIT).toString()
                                    , map.get((Constants.NF_CALORIES)).toString()
                                    , map.get(Constants.NF_PROTEINS).toString()
                                    , map.get(Constants.NF_SODIUM).toString()
                                    , map.get(Constants.NF_TOTAL_FAT).toString()
                                    , map.get(Constants.NF_TOTAL_CARBOHYDRATES).toString()
                                    , map.get(Constants.NF_SATURATED_FAT).toString()
                                    , map.get(Constants.NF_CHOLESTEROL).toString()
                                    , map.get(Constants.NF_VITAMIN_A).toString()
                                    , map.get(Constants.NF_VITAMIN_C).toString()
                                    , map.get(Constants.NF_IRON).toString()
                                    , map.get(Constants.NF_CALCIUM).toString()
                                    , map.get(Constants.NF_SERVING_WEIGHT_GRAMS).toString()
                            );

                            UpdateMe(map.get(Constants.BRAND_NAME).toString().replace('\'', ' '), map.get(Constants.ITEM_NAME).toString().replace('\'', ' '), qty);

                        }


                    }
                    notifyDataSetChanged();
                }
            });

            holder.cancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    HideAddview[kl] = false;
                    notifyDataSetChanged();
                }
            });


            int[] listcolor = {R.color.ROW0, R.color.White}; //list of drawable background

            int colorPos = position % listcolor.length;
            convertview.setBackgroundColor(checkedItems[position] ? getResources().getColor(R.color.ROWDUPLICATE) : getResources().getColor(listcolor[colorPos]));
            if (map.get(Constants.ITEM_NAME).toString().length() > 15) {
                holder.tvbrand.setText(map.get(Constants.ITEM_NAME).toString().substring(0, 14) + "..");
            } else {
                holder.tvbrand.setText((CharSequence) map.get(Constants.ITEM_NAME));
            }
            if ((map.get(Constants.BRAND_NAME).toString()).length() > 15) {
                holder.title.setText((map.get(Constants.BRAND_NAME).toString()).substring(0, 14) + "..");
            } else {
                holder.title.setText(map.get(Constants.BRAND_NAME).toString());
            }


            //On the Row click if there is the duplicates in the view
            final int k = position;
            final ViewConfiguration vc = ViewConfiguration.get(activity);
            if (Hidemain[position]) {

                convertview.setBackgroundColor(getResources().getColor(R.color.DUPLICATE_GRAY));
                final View x = convertview;
                if (index < 0) {
                    right = false;
                    left = true;
                }
                if (index > duplicates.size()) {
                    right = true;
                    left = false;
                }

            } else {
                Log.i("Position", "No Duplicates Available");
            }
            try {


                {
                    try {
                        holder.serving.setText(

                                map.get(Constants.NF_SERVING_SIZE_QTY).toString() + " " + map.get(Constants.NF_SERVING_SIZE_UNIT).toString());


                        if (!map.get("carbsval").toString().equals("null")) {
                            holder.carbs_val.setText(String.format("%.0f", Float.valueOf(map.get("carbsval").toString())));
                        } else {
                            holder.carbs_val.setText("0");
                        }
                        if (!map.get("proval").toString().equals("null")) {
                            holder.proteins_val.setText(String.format("%.0f", Float.valueOf(map.get("proval").toString())));
                        } else {
                            holder.proteins_val.setText("0");
                        }
                        if (!map.get("caloval").toString().equals("null")) {
                            holder.calories_val.setText(String.format("%.0f", Float.valueOf(map.get("caloval").toString())));
                        } else {
                            holder.calories_val.setText("0");
                        }

                        String carbs = map.get(Constants.NF_TOTAL_CARBOHYDRATES).toString();
                        if (countExclaim(carbs) > 0) {
                            holder.carbs.setMaxHeight(195);
                            holder.carbs.setHeight(Integer.parseInt(carbs.substring(0, countExclaim(carbs))) * multiplyfactor_cal);
                        } else {
                            if (carbs.equals("null")) {
                                holder.carbs.setHeight(1);
                            } else {
                                holder.carbs.setHeight(Integer.parseInt(carbs));

                            }
                        }

                        String Prote = map.get(Constants.NF_PROTEINS).toString();

                        if (countExclaim(Prote) > 0) {
                            holder.proteins.setMaxHeight(195);
                            holder.proteins.setHeight(Integer.parseInt(Prote.substring(0, countExclaim(Prote))) * multiplyfactor_cal);
                        } else {
                            if (Prote.equals("null")) {
                                holder.proteins.setHeight(1);
                            } else {

                                holder.proteins.setHeight(Integer.parseInt(Prote));
                            }
                        }

                        String calo = map.get(Constants.NF_CALORIES).toString();


                        if (countExclaim(calo) > 0) {

                            holder.calories.setMaxHeight(195);
                            holder.calories.setHeight(Integer.parseInt(calo.substring(0, countExclaim(calo))) * multiplyfactor_cal);
                        } else {
                            if (calo.equals("null")) {
                                holder.calories.setHeight(1);
                            } else {
                                holder.calories.setHeight(Integer.parseInt(calo));
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } catch (NumberFormatException num) {
                num.printStackTrace();
            }

            convertview.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    int rowval = k;
                   // (((Go) activity)).intentMe(rowval);
                   ((FoldingCell) v).toggle(false);
                }
            });
            holder.addfood.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    System.out.println("hiiiiiiiiiiii");

                    if (viewText[position]) {
                        Log.i(String.valueOf(position), String.valueOf(viewText[position]));

                        checkedItems[position] = true;
                        viewText[position] = false;
                        notifyDataSetChanged();
                        //holder.ckbox.setText(ckboxTextAdd[position]);
                        Log.i("value of Somple List", list.get(position).toString());
                        Log.i("value of listitr", list.get(position).toString());
                        Log.i("value of text box", ckboxTextAdd[position]);
                        final HashMap<?, ?> map = list.get(position);
                        System.out.println("checkingmapinonlongclicklistener" + map);
                        map.get(Constants.NF_CALORIES);

                        String item_name = map.get(Constants.ITEM_NAME).toString().replace('\'', ' ');
                        String brand_name = map.get(Constants.BRAND_NAME).toString().replace('\'', ' ');
                        Cursor cursor = db.fetch_data(item_name, brand_name);
                        if (cursor.getCount() > 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setMessage("Already Saved")
                                    .setPositiveButton("Replace", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {

                                            checkedItems[position] = true;
                                            viewText[position] = false;
                                            notifyDataSetChanged();
                                            Log.i("value of text box trey", ckboxTextAdd[position]);
                                            //	 holder.ckbox.setText(ckboxTextAdd[position]);
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {


                                            checkedItems[position] = false;
                                            viewText[position] = true;
                                            notifyDataSetChanged();

                                            Log.i("value of text box false", ckboxTextAdd[position]);
                                            //holder.ckbox.setText(ckboxTextAdd[position]);

                                        }
                                    });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        } else {
                            final String[] items;
                            items = new String[keys.entrySet().size()];
                            int i = 0;
                            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                                Log.d("map values", entry.getKey() + ": " +
                                        entry.getValue().toString());

                                items[i] = entry.getValue().toString();
                                Log.i("items", String.valueOf(i) + entry.getValue().toString());
                                i++;

                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setCancelable(false);
                            builder.setTitle("Select Categories");
                            builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    // TODO Auto-generated method stub
                                    Log.i("which ", String.valueOf(which));
                                    if (isChecked) {
                                        String value = items[which].toString();

                                        if (which == 0) {
                                            indexvalue[0] = 0;
                                        }
                                        if (which == 1) {
                                            indexvalue[1] = 1;
                                        }
                                        if (which == 2) {
                                            indexvalue[2] = 2;
                                        }
                                        if (which == 3) {
                                            indexvalue[3] = 3;
                                        }
                                        if (which == 4) {
                                            indexvalue[4] = 4;
                                        }
                                        if (which == 5) {
                                            indexvalue[5] = 5;
                                        }
                                        if (which == 6) {
                                            indexvalue[6] = 6;
                                        }
                                        if (which == 7) {
                                            indexvalue[7] = 7;
                                        }
                                        if (which == 8) {
                                            indexvalue[8] = 8;
                                        }
                                        if (which == 9) {
                                            indexvalue[9] = 9;
                                        }
                                        if (which == 10) {
                                            indexvalue[10] = 10;
                                        }

                                        Log.i("value ", String.valueOf(value));
                                    }
                                }
                            })


                                    .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {


                                            checkedItems[position] = true;
                                            viewText[position] = false;
                                            notifyDataSetChanged();

                                            Log.i("value of Calories", map.get(Constants.NF_CALORIES).toString());
                                            String qty = null;
                                            if (((Integer.valueOf(((String.format("%.0f", Float.valueOf(map.get(Constants.NF_SERVING_SIZE_QTY).toString()))))))) < 1) {
                                                qty = String.valueOf((String.format("%.0f", Float.valueOf(map.get(Constants.NF_SERVING_SIZE_QTY).toString()) * 10)));
                                            } else {
                                                qty = String.valueOf((Float.valueOf(map.get(Constants.NF_SERVING_SIZE_QTY).toString())));
                                            }
                                            db.createSave(
                                                    position
                                                    , map.get(Constants.BRAND_NAME).toString().replace('\'', ' ')
                                                    , map.get(Constants.ITEM_NAME).toString().replace('\'', ' ')
                                                    , map.get("path").toString()
                                                    , qty
                                                    , map.get(Constants.NF_SERVING_SIZE_UNIT).toString()
                                                    , map.get((Constants.NF_CALORIES)).toString()
                                                    , map.get(Constants.NF_PROTEINS).toString()
                                                    , map.get(Constants.NF_SODIUM).toString()
                                                    , map.get(Constants.NF_TOTAL_FAT).toString()
                                                    , map.get(Constants.NF_TOTAL_CARBOHYDRATES).toString()
                                                    , map.get(Constants.NF_SATURATED_FAT).toString()
                                                    , map.get(Constants.NF_CHOLESTEROL).toString()
                                                    , map.get(Constants.NF_VITAMIN_A).toString()
                                                    , map.get(Constants.NF_VITAMIN_C).toString()
                                                    , map.get(Constants.NF_IRON).toString()
                                                    , map.get(Constants.NF_CALCIUM).toString()
                                                    , map.get(Constants.NF_SERVING_WEIGHT_GRAMS).toString()
                                            );
                                            UpdateMe(map.get(Constants.BRAND_NAME).toString().replace('\'', ' '), map.get(Constants.ITEM_NAME).toString().replace('\'', ' '), qty);
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {


                                            viewText[position] = true;
                                            checkedItems[position] = false;
                                            notifyDataSetChanged();

                                            Log.i("value of text box false", ckboxTextAdd[position]);
                                            //holder.ckbox.setText(ckboxTextAdd[position]);

                                        }
                                    });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }
                    } else {
                        Log.i(String.valueOf(position), String.valueOf(viewText[position]));
                        Log.i("value of text box false", ckboxTextAdd[position]);
                        checkedItems[position] = false;
                        viewText[position] = true;
                        notifyDataSetChanged();
                        //holder.ckbox.setText(ckboxTextAdd[position]);
                        db.deleteUnChecked(position);
                    }
                }

            });


            return convertview;
        }

        private class Viewholder extends Activity
        {
            int ref;
            TextView title;
            TextView servingsize;
            //TextView status;
            TextView carbs;
            TextView proteins;
            TextView calories;
            ImageView img_serve_size;
            TextView sodium;
            TextView Fiber;
            ImageView convertFlipFrom;
            TextView carbs_val;
            TextView proteins_val;
            TextView calories_val;
            TextView sodium_val;
            TextView serving;
            TextView Fiber_val;
            ImageView ckbox;
            TextView tvbrand,detailpageitem,detailbrand,detailcalory,detailcaloryone,detailcarbo,detailcarbo1,detailprotein,detailprotein1,detailfiber,detailfiber1,detailsodium,detailsodium1;
            TextView tvreferance;
            TextView imagetextview;
            //	TextView linearslider;
            TextView tvitem_name,tvbrand_name,tvserving_size_unit,tvserving_size_gram,tvnf_calories,tvnf_calories_from_fat,
                    tvtotal_fat,tvsaturated_fat,tvpoly_sat_fat,tvnf_cholesterol,tvnf_sodium,tvtotal_carbohydrates,tvnf_proteins,tvnf_vitamin_A,
                    tvnf_vitamin_C,tvnf_calcium,tvfrom,tvnf_iron,tvdietary_fiber,tvsugar,tvamountserving,tvnote;
            TextView edtserving_size_qty;
            TextView tvTitle,tvpervitA,tvpercal,tvpervitc,tvperiron;
            TextView tvcalorieGraph,tvCaloriePer,tvfatGraph,tvfatPer,tvCholestrolGraph,tvCholestrolPer,tvcarbsGraph,tvcarbsPer
                    ,tvsodGraph,tvsodPer,tvproGraph,tvproPer,tvsatfatGraph,tvsatfatPer;

            ImageView imgplus_slide_next,addfood,imgplus,imgminus;
            CheckBox chk1,chk2,chk3,chk4,chk5,chk6,chk7,chk8;
            Button ok,cancel;
            LinearLayout holeaddview;



        }
        public int countExclaim(String string) {
            int count = 0;
            if(string.indexOf(".")==-1)
            {
                count=0;
            }
            else
            {
                count=string.indexOf(".");
            }
            return count;
        }

        public int countExclaimItemName(String string) {
            int count = 0;
            if(string.indexOf("-")==-1)
            {
                count=0;
            }
            else
            {
                count=string.indexOf("-");
            }
            return count;
        }

    }

    public String retriveItemName(int position)
    {
        return item_name[position];
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if(this.isFinishing())
        {
            finish();
        }
        super.onBackPressed();
    }
}