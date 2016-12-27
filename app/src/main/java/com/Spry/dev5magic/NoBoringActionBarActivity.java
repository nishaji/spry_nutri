package com.Spry.dev5magic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Spry.nutritionix.ChartActivity;
import com.Spry.nutritionix.Constants;
import com.Spry.nutritionix.Daily_Goal;
import com.Spry.nutritionix.My_Daily_Plans;
import com.Spry.nutritionix.NutritionSearch;

import com.myapplication.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.spry.database.DbAdapter;

public class NoBoringActionBarActivity extends Fragment {

    private static final String TAG = "NoBoringActionBarActivity";
    private int mActionBarTitleColor;
    private int mActionBarHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private ListView mListView;
    private LinearLayout mHeaderPicture,mActualHeader;
    private ImageView mHeaderLogo;
    private View mHeader;
    private View mPlaceHolderView;
    static int universal=0;
    LinearLayout.LayoutParams layoutParams ;
    private AccelerateDecelerateInterpolator mSmoothInterpolator;
    DbAdapter MdbHelper;
    SharedPreferences sharedPref;
    static Map<String,?> keys;
    private ActionMode.Callback modeCallBack;
    static int counter;
    String Constraints[]={Constants.BRAND_NAME,Constants.ITEM_NAME,"path",Constants.NF_SERVING_SIZE_QTY,Constants.NF_SERVING_SIZE_UNIT
            ,Constants.NF_CALORIES,Constants.NF_PROTEINS,Constants.NF_SODIUM,Constants.NF_TOTAL_FAT,Constants.NF_TOTAL_CARBOHYDRATES
            ,Constants.NF_SATURATED_FAT,Constants.NF_CHOLESTEROL,Constants.NF_VITAMIN_A,Constants.NF_VITAMIN_C,Constants.NF_IRON
            ,Constants.NF_CALCIUM,Constants.NF_SERVING_WEIGHT_GRAMS};
    String [] item_name,brand_name,serving_size_qty,serving_size_unit,serving_size_gram,nf_calories,nf_calories_from_fat,
            total_fat,saturated_fat,poly_sat_fat,nf_cholesterol,nf_sodium,total_carbohydrates,nf_proteins,nf_vitamin_A,
            nf_vitamin_C,nf_calcium,nf_iron,nf_daietry_fiber,nf_sugar,serving_size_qty_meal;
    String [] mlitem_name,mlbrand_name,mlserving_size_qty,mlserving_size_unit,mlserving_size_gram,mlnf_calories,mlnf_calories_from_fat,
            mltotal_fat,mlsaturated_fat,mlpoly_sat_fat,mlnf_cholesterol,mlnf_sodium,mltotal_carbohydrates,mlnf_proteins,mlnf_vitamin_A,
            mlnf_vitamin_C,mlnf_calcium,mlnf_iron,mlnf_daietry_fiber,mlnf_sugar;

    static String slidecat2="l\nu\nc\nh",slidebreak="b\nr\ne\na\nk",slidecat3="d\ni\nn\nn\ne\nr";
    static String slideAftercat2="cat2",slideAfterbreak="cat1",slideAftercat3="cat3",slidetotal="T\no\nt\na\nl",slideAftertotal="Total Meal";
    TextView tvactiobartitle;
    String []value=new String[Constraints.length];
    Typeface roboto_light1;
    Typeface roboto_MediTypeface;
    ArrayList<HashMap<?, ?>> contactList;
    ListViewSaveAdapter2 listViewSaveAdapter;
    TextView tvTitem;
    TextView tvcal_value,tvfib_value,tvsod_value,tvcar_value,tvpro_value,Total_desc,tvvitA_value,tvvitC_value;
    TextView tvcal_value_graph,tvfib_value_graph,tvsod_value_graph,tvcar_value_graph,tvpro_value_graph,tvvitA_value_graph,tvvitC_value_graph;
    TextView tvmcal_value,tvmfib_value,tvmsod_value,tvmcar_value,tvmpro_value,tvmvitA_value,tvmvitC_value;
    TextView tvmcal_value_graph,tvmfib_value_graph,tvmsod_value_graph,tvmcar_value_graph,tvmpro_value_graph,tvmvitA_value_graph,tvmvitC_value_graph;
    Toast toast;
    Float firon,fcalcium,fvitA,fvitC;
    Float fcal=2000f,
            fpro=50f,
            ffat=65f,
            fsod=2400f,
            fcarbs=300f;
    DbAdapter dataadapter;
    DbAdapter db;


    View viewbreak,viewtotal,footer;
    static int iwidth;

    TextView MealSnapDesc;
    TextView mtcalplus,mtproplus,mtcarbsplus,mtfibplus,mtsodplus,mtvitaplus,mtvitcplus;
    TextView calplus,proplus,carbsplus,fibplus,sodplus,vitaplus,vitcplus;
    int multiplyfactor_cal=2,multiplyfactor_pro=2,multiplyfactor_sod=2,multiplyfactor_fat=2,multiplyfactor_carbs=2;
    View rootview;
    int previous = 0;
    int mark = 0;
    public static int xku=0;
    public static  String status_tabs="cat1";

    private RectF mRect1 = new RectF();
    private RectF mRect2 = new RectF();
    public static Button [] button;
    private AlphaForegroundColorSpan mAlphaForegroundColorSpan;
    private SpannableString mSpannableString;
    LinearLayout ll;
    private TypedValue mTypedValue = new TypedValue();
    SlidingUpPanelLayout slidingUpPanelLayout;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_save, container, false);
        slidingUpPanelLayout = (SlidingUpPanelLayout)v.findViewById(R.id.sliding_layout);
        ll = (LinearLayout) v.findViewById(R.id.tabsviewhome);
        return v;
    }

    // TODO Auto-generated method stub
    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        AddButton();
        if (Integer.parseInt(String.format("%.0f", fcal)) <= 1000) {
            multiplyfactor_cal = 4;
            multiplyfactor_pro = 4;
            multiplyfactor_sod = 4;
            multiplyfactor_fat = 4;
            multiplyfactor_carbs = 4;
        } else if (Integer.parseInt(String.format("%.0f", fcal)) >= 1001) {

            if (Integer.parseInt(String.format("%.0f", fcal)) <= 1600) {
                multiplyfactor_cal = 3;
                multiplyfactor_pro = 3;
                multiplyfactor_sod = 3;
                multiplyfactor_fat = 3;
                multiplyfactor_carbs = 3;
            }
        } else if (Integer.parseInt(String.format("%.0f", fcal)) >= 1601) {

            if (Integer.parseInt(String.format("%.0f", fcal)) <= 2000) {
                multiplyfactor_cal = 2;
                multiplyfactor_pro = 2;
                multiplyfactor_sod = 2;
                multiplyfactor_fat = 2;
                multiplyfactor_carbs = 2;
            }
        } else {

            multiplyfactor_cal = 2;
            multiplyfactor_pro = 2;
            multiplyfactor_sod = 2;
            multiplyfactor_fat = 2;
            multiplyfactor_carbs = 2;

        }

        roboto_light1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto_Light.ttf");
        roboto_MediTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto_Medium.ttf");
        Total_desc = (TextView) view.findViewById(R.id.lTotalserving_size_grams);
        Total_desc.setTextColor(getResources().getColor(R.color.SKY_BLUE_TABS));
        //Total_desc.setText("cat1 Total");
        contactList = new ArrayList<HashMap<?, ?>>();

        toast = new Toast(this.getActivity());
        MdbHelper = new DbAdapter(this.getActivity());
        MdbHelper.open();
        //Clear();
        mtcalplus = (TextView) view.findViewById(R.id.calplus);
        mtproplus = (TextView) view.findViewById(R.id.carbsplus);
        mtcarbsplus = (TextView) view.findViewById(R.id.proplus);
        mtfibplus = (TextView) view.findViewById(R.id.Fibplus);
        mtsodplus = (TextView) view.findViewById(R.id.Sodplus);
        mtvitaplus = (TextView) view.findViewById(R.id.vitaplus);
        mtvitcplus = (TextView) view.findViewById(R.id.vitcplus);
        //For the Sub total


        calplus = (TextView) view.findViewById(R.id.Tcalplus);
        proplus = (TextView) view.findViewById(R.id.Tcarbplus);
        carbsplus = (TextView) view.findViewById(R.id.Tproplus);
        fibplus = (TextView) view.findViewById(R.id.Tfibplus);
        sodplus = (TextView) view.findViewById(R.id.Tsodplus);
        vitaplus = (TextView) view.findViewById(R.id.Tvitaplus);
        vitcplus = (TextView) view.findViewById(R.id.Tvitcplus);


        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        iwidth = size.x;


        final View decorView = this.getActivity().getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(
                new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int i) {
                        int height = decorView.getHeight();

                    }
                });

        /////////////////////////////////////////////


        mListView = (ListView) view.findViewById(R.id.listview);
        mHeader = view.findViewById(R.id.header);
        mHeaderPicture = (LinearLayout) view.findViewById(R.id.tabsview);

        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(30, 20, 30, 0);

        mActualHeader = (LinearLayout) view.findViewById(R.id.label);
        mHeaderLogo = (ImageView) view.findViewById(R.id.header_logo);

        mActionBarTitleColor = getResources().getColor(R.color.title_color);

        mSpannableString = new SpannableString(getString(R.string.app_name));
        mAlphaForegroundColorSpan = new AlphaForegroundColorSpan(mActionBarTitleColor);

        callMe(Integer.parseInt(String.valueOf(0)));
        setupListView();
        setupMultipleChoiceListView();
        Delete();
    }
    private void setupMultipleChoiceListView() {
        // TODO Auto-generated method stub
        Log.d("Multiple", "you are in");
        //Setting the choice mode of the Listview to multiple
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        //now setting the multiple Choice Listener in android

        mListView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
            int nr=0;
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub

                listViewSaveAdapter.clearSelection();

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                nr = 0;
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.context_delete_action_bar, menu);

                return true;

            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // TODO Auto-generated method stub

                switch (item.getItemId()) {

                    case R.id.item_delete:
                        nr = 0;
                        Log.d("Delete is called","Hello Delete");

                        listViewSaveAdapter.DeleteSelection();
                        callMe(universal);
                        listViewSaveAdapter= new ListViewSaveAdapter2(NoBoringActionBarActivity.this.getActivity(),NoBoringActionBarActivity.this, contactList);
                        mListView.setAdapter(listViewSaveAdapter);
                        // listViewSaveAdapter.clearSelection();
                        Log.d("Delete is called","Deleted");

                        mode.finish();
                }
                return false;

            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // TODO Auto-generated method stub
                if (checked) {
                    nr++;
                    listViewSaveAdapter.setNewSelection(position-1, checked);
                } else {
                    nr--;

                    listViewSaveAdapter.removeSelection(position-1);
                }
                mode.setTitle(nr + "  selected");
            }
        });

    }

    //@methods to add the buttons in Tabs to create a new categories Layout
    @SuppressLint("NewApi")
    private void AddButton() {
        // TODO Auto-generated method stub

        sharedPref = this.getActivity().getSharedPreferences("Hello",Context.MODE_MULTI_PROCESS);
        keys = sharedPref.getAll();

        if(keys.entrySet().size()>0)
        {
            button=new Button[keys.entrySet().size()+1];
            Log.d("Value of key","values are size--key"+String.valueOf(keys.entrySet().size()));
            for(int i=0;i<keys.entrySet().size();i++)
            {
                button[i]=new Button(this.getActivity());
                button[i].setTextColor(getResources().getColor(R.color.MealUnselected));
                button[i].setBackground(getResources().getDrawable(R.drawable.meal_back_border));

                Display display=getActivity().getWindowManager().getDefaultDisplay();
                Point size=new Point();
                display.getSize(size);

                button[i].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.textsize_13));
                if(sharedPref.getString("cat"+ String.valueOf(i), null).length()>7)
                {
                    button[i].setText(sharedPref.getString("cat"+ String.valueOf(i), null).toString().substring(0, 6)+"..");
                }else{
                    button[i].setText(sharedPref.getString("cat"+ String.valueOf(i), null));
                }


                //button[i].setText(sharedPref.getString("cat"+ String.valueOf(i),null).toCharArray(), 0,sharedPref.getString("cat"+ String.valueOf(i),null).length()>5?6:sharedPref.getString("cat"+ String.valueOf(i),null).length());
                button[i].setId(i);

                button[i].setTextColor(getResources().getColor(R.color.btnsbackblack));
                button[i].setBackground(getResources().getDrawable(R.drawable.tabs_unactive_back));
                button[0].setTextColor(getResources().getColor(R.color.SKY_BLUE_TABS));
                button[0].setBackground(getResources().getDrawable(R.drawable.tabs_bottom_active_back));

                button[i].setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub]
                        callMe(Integer.parseInt(String.valueOf(v.getId())));
                        universal=v.getId();
                        listViewSaveAdapter=new ListViewSaveAdapter2(NoBoringActionBarActivity.this.getActivity(),NoBoringActionBarActivity.this, contactList);
                        mListView.setAdapter(listViewSaveAdapter);
                        for(int i=0;i<keys.entrySet().size();i++)
                        {
                            button[i].setTextColor(getResources().getColor(R.color.btnsbackblack));
                            button[i].setBackground(getResources().getDrawable(R.drawable.tabs_unactive_back));
                        }
                        button[v.getId()].setTextColor(getResources().getColor(R.color.SKY_BLUE_TABS));
                        button[v.getId()].setBackground(getResources().getDrawable(R.drawable.tabs_bottom_active_back));
                        Log.i("tab button", "Lunch Clicked");
                        try
                        {
                            Delete();
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                });
                if(keys.entrySet().size()>0 && keys.entrySet().size()<=4)
                {
                    Log.i("tab button", "2");
                    button[i].setLayoutParams(new LayoutParams(size.x/keys.entrySet().size(), android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

                }
                else
                {
                    Log.i("tab button", "3>");
                    button[i].setLayoutParams(new LayoutParams(356, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
                }
                ll.addView(button[i]);
            }
        }

    }
    private void setupListView() {
        listViewSaveAdapter=new ListViewSaveAdapter2(NoBoringActionBarActivity.this.getActivity(),NoBoringActionBarActivity.this, contactList);
        mPlaceHolderView = getActivity().getLayoutInflater().inflate(R.layout.view_header_placeholder, mListView, false);
        footer =  ((LayoutInflater)this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footerview, mListView, false);
        mListView.addHeaderView(mPlaceHolderView);
        mListView.addFooterView(footer);
        mListView.setAdapter(listViewSaveAdapter);
        listViewSaveAdapter.notifyDataSetChanged();
        mListView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            int mLastFirstVisibleItem = 0;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int scrollY = getScrollY();
                //sticky actionbar
                int totalheight=view.getHeight()*totalItemCount;

                int visibleheight=view.getHeight()*visibleItemCount;
                if (view.getId() == mListView.getId()) {
                    final int currentFirstVisibleItem = mListView.getFirstVisiblePosition();

                    mLastFirstVisibleItem = currentFirstVisibleItem;
                }
                int position =view.getFirstVisiblePosition();
                Log.i("totalheight", String.valueOf(totalheight)+"\t"+ String.valueOf(visibleheight)+"\t"+ String.valueOf(position));
                mHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
                float ratio = clamp(mHeader.getTranslationY() / mMinHeaderTranslation, 0.0f, 1.0f);
            }
        });
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min,Math.min(value, max));
    }
    private RectF getOnScreenRect(RectF rect, View view) {
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        return rect;
    }

    public int getScrollY() {
        View c = mListView.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mPlaceHolderView.getHeight();

        }
        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    public int getActionBarHeight() {
        if (mActionBarHeight != 0) {
            return mActionBarHeight;
        }
        getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
        mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, getResources().getDisplayMetrics());
        return mActionBarHeight;
    }

    public class ListViewSaveAdapter2 extends BaseAdapter{

        Viewholder holder;
        public ArrayList<HashMap<?, ?>>list;

        public ArrayList<Integer> add;
        Context activity;
        Fragment frg;
        boolean [] checkedItems;
        boolean [] checkedAddSub;
        boolean [] Hide,Hidemain,Hidden;
        String qtysavesearch[];



        String val_cal[],val_carb[],val_fib[],val_sod[],val_pro[];
        String val_cal_graph[],val_carb_graph[],val_fib_graph[],val_sod_graph[],val_pro_graph[];

        private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();



        public void setNewSelection(int position, boolean value) {
            mSelection.put(position, value);
            notifyDataSetChanged();
        }

        public boolean isPositionChecked(int position) {
            Boolean result = mSelection.get(position);
            return result == null ? false : result;
        }

        public Set<Integer> getCurrentCheckedPosition() {
            return mSelection.keySet();
        }

        public void removeSelection(int position) {
            mSelection.remove(position);

            notifyDataSetChanged();
        }
        public void removeSelectdItem(int position)
        {

            list.remove(position);
            notifyDataSetChanged();

        }

        public void clearSelection() {
            mSelection=new HashMap<Integer, Boolean>();
            notifyDataSetChanged();
        }

        public void DeleteSelection() {



            for(int i=0;i<list.size();)
            {


                if(mSelection.get(i)!=null && mSelection.get(i))
                {
                    Log.i("value removed", "Hello"+String.valueOf(i)+String.valueOf(mSelection.size()));

                    final HashMap<?, ?> map=list.get(i);
                    String item_name1 =map.get(Constants.ITEM_NAME).toString().replace('\'', ' ');
                    String brand_name1 =map.get(Constants.BRAND_NAME).toString().replace('\'',' ');
                    db.deleteCheckedItems(item_name1, brand_name1, map.get("status").toString());
                    Toast.makeText(activity, "Deleted  "+String.valueOf(i)+map.get("status").toString(), Toast.LENGTH_LONG).show();
                    mSelection.remove(i);
                    Clear();
                    notifyDataSetChanged();
                    Log.i("Size of main list After", String.valueOf(list.size()));
                    (((NoBoringActionBarActivity) frg)).Total_manipulation(universal);
                    (((NoBoringActionBarActivity) frg)).Total_Meal();
                    Log.i("value removed", "Hello"+String.valueOf(i)+String.valueOf(mSelection.size()));
                }
                else
                {
                    Log.i("value removed", "Hello not removed"+String.valueOf(i));

                    i++;
                }
            }
            notifyDataSetChanged();
        }
        public ListViewSaveAdapter2(Context activity, Fragment frg,ArrayList<HashMap<?, ?>>list) {
            // TODO Auto-generated constructor stub
            super();
            this.frg=frg;
            this.activity=activity;
            this.list=list;
            Log.d("**#####listaice",String.valueOf(list.size()));
            db=new DbAdapter(activity);
            db.open();
            checkedItems= new boolean[list.size()];
            checkedAddSub=new boolean[list.size()];
            Hide=new boolean[list.size()];
            Hidemain=new boolean[list.size()];
            Hidden=new boolean[list.size()];
            qtysavesearch=new String[list.size()];
            val_cal=new String[list.size()];
            val_carb=new String[list.size()];
            val_fib=new String[list.size()];
            val_sod=new String[list.size()];
            val_pro=new String[list.size()];
            val_cal_graph=new String[list.size()];
            val_carb_graph=new String[list.size()];
            val_fib_graph=new String[list.size()];
            val_sod_graph=new String[list.size()];
            val_pro_graph=new String[list.size()];

            for(int i=0;i<list.size();i++)
            {
                checkedItems[i]=false;
                checkedAddSub[i]=false;
                Hide[i]=false;
                Hidemain[i]=true;
                Hidden[i]=false;
            }

        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return (list.size()-getHiddenCount());
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
        private int getHiddenCount() {
            int count = 0;
            for(int i=0;i<list.size();i++)
                if(Hidden[i])
                    count++;
            return count;
        }
        private int getRealPosition(int position) {
            int hElements = getHiddenCountUpTo(position);
            int diff = 0;
            for(int i=0;i<hElements;i++) {
                diff++;
                if(position+diff>=list.size())
                {
                    diff=0;
                }
                else
                {
                    if(Hidden[position+diff])
                        i--;
                }
            }
            return (position + diff);


        }
        private int getHiddenCountUpTo(int location) {
            int count = 0;
            for(int i=0;i<=location;i++) {
                if(Hidden[i])
                    count++;
            }
            return count;
        }

        @Override
        public View getView(int position1, View convertview, ViewGroup parent) {

            //int position =getRealPosition(position1);
            int position =getRealPosition(position1);
            LayoutInflater layoutInflater=((Activity) activity).getLayoutInflater();
            final HashMap<?, ?> map=list.get(position);

            if(convertview==null)
            {
                convertview=layoutInflater.inflate(R.layout.listviewdetails,parent,false);
                holder = new Viewholder();

                holder.title=(TextView) convertview.findViewById(R.id.lserving_size_grams);
                holder.servingsize=(TextView) convertview.findViewById(R.id.ldata_serving_details);

                holder.lserving_qty=(TextView)convertview.findViewById(R.id.ldata_qty);

                holder.img_serve_size=(ImageView)convertview.findViewById(R.id.limage_serve_eqivalent);


                holder.calories_val=(TextView)convertview.findViewById(R.id.listTcal_desc_val);
                holder.calories=(TextView)convertview.findViewById(R.id.listTcaloriesview);

                holder.Fiber=(TextView)convertview.findViewById(R.id.listTFiberview);
                holder.Fiber_val=(TextView)convertview.findViewById(R.id.listTFiber_desc_val);

                holder.sodium_val=(TextView)convertview.findViewById(R.id.listTsodium_desc_val);
                holder.sodium=(TextView)convertview.findViewById(R.id.listTsodiumview);

                //the Name of carbs and proteins values are interchanged so do not get confused Here

                holder.proteins_val=(TextView)convertview.findViewById(R.id.listTcarbs_desc_val);
                holder.proteins=(TextView)convertview.findViewById(R.id.listTcarbsview);

                holder.carbs_val=(TextView)convertview.findViewById(R.id.listTprot_desc_val);
                holder.carbs=(TextView)convertview.findViewById(R.id.listTProtienview);
                holder.tvbrand=(TextView)convertview.findViewById(R.id.litem_brand_name);
                holder.sminus=(ImageView)convertview.findViewById(R.id.limg_right_view);
                holder.splus=(ImageView)convertview.findViewById(R.id.limg_left_view);
                holder.imgsetting=(ImageView)convertview.findViewById(R.id.limg_setting);
                convertview.setTag(holder);

            }
            else
            {

                holder=(Viewholder)convertview.getTag();
            }

            //convertview.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            Log.d("Selected row position","Position of getview "+String.valueOf(position1)+String.valueOf(mSelection.get(position1)));
            //@Here Changing the visual effect to show user the row which he/she selected from the list to delete
            int[] listcolor ={R.color.ROW0, R.color.White}; //list of drawable background

            int colorPos = position % listcolor.length;
            convertview.setBackgroundColor(isPositionChecked(position1)?getResources().getColor(R.color.LIST_CLICK):getResources().getColor(listcolor[colorPos]));
            //convertview.setBackgroundColor(isPositionChecked(position1)?getResources().getColor(android.R.color.holo_blue_light):getResources().getColor(R.color.layout_background));
            notifyDataSetChanged();
            // this is a selected position so make it red



            //@method for calling of the Action perform on teh Setting btn click
            final int klposition=position;

            holder.imgsetting.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    // TODO Auto-generated method stub
                    Log.d("","Image clicked");
                    //ContextActionbarGeneration();
                    Log.i("Clicked item for long","Time"+String.valueOf(listViewSaveAdapter.isPositionChecked(klposition)));
                    if(listViewSaveAdapter.isPositionChecked(klposition)){

                        listViewSaveAdapter.setNewSelection(klposition, false);
                        mListView.setItemChecked(klposition, listViewSaveAdapter.isPositionChecked(klposition));
                    }else{
                        listViewSaveAdapter.setNewSelection(klposition, true);

                        mListView.setItemChecked(klposition, listViewSaveAdapter.isPositionChecked(klposition));
                    }

                    listViewSaveAdapter.notifyDataSetChanged();

                }
            });


            //Setting Animations to the Delete Media


            if(map.get(Constants.ITEM_NAME).toString().length()>15){
                holder.title.setText(map.get(Constants.ITEM_NAME).toString().substring(0, 14)+"..");
            }
            else{
                holder.title.setText((CharSequence) map.get(Constants.ITEM_NAME));
            }
            if(("From "+ map.get(Constants.BRAND_NAME).toString()).length()>15){
                holder.tvbrand.setText(("From "+ map.get(Constants.BRAND_NAME).toString()).substring(0,14)+"..");
            }
            else{
                holder.tvbrand.setText("From "+ map.get(Constants.BRAND_NAME).toString());
            }

            final int k=position;

            //On the deleteview Image click Animation and deletion of the row

            holder.title.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    ((( NoBoringActionBarActivity) frg)).ClickMe(k);
                    notifyDataSetChanged();
                }
            });

            holder.servingsize.setText( map.get(Constants.NF_SERVING_SIZE_UNIT).toString());
            try{

                holder.splus.setOnClickListener(null);

                Log.i(map.get("status").toString()+"qty8888888", map.get(map.get("status").toString()+"qty").toString());

                holder.lserving_qty.setText(checkedAddSub[position] ? qtysavesearch[position] : map.get(map.get("status").toString()+"qty").toString());


            }
            catch(Exception e)
            {
                e.printStackTrace();
            }


            holder.img_serve_size.setImageResource(Integer.parseInt(map.get("path").toString()));

            holder.splus.setTag(R.id.limg_left_view,position);
            holder.sminus.setTag(R.id.limg_right_view,position);

			 /*
			  * Plus Btn click Listener
			  */
            ((( NoBoringActionBarActivity) frg)).Total_Meal();
            notifyDataSetChanged();
            holder.splus.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    final int position = (Integer) v.getTag(R.id.limg_left_view);
                    LinearLayout R1 = (LinearLayout)v.getParent();
                    Log.i("value of qty","Clicked on splus");
                    TextView Tv=(TextView)R1.findViewById(R.id.ldata_qty);
                    Log.i("value of qty",Tv.getText().toString());
                    counter=Math.round(Float.valueOf(Tv.getText().toString()));
                    if(counter>=1)
                    {	Float L=0f,K=0f,per=0f,Manip=0f,cal=0f,sod=0f,pro=0f,fib=0f,carbs=0f;
                        counter++;

                        if(map.get(Constants.NF_CALORIES).toString().equalsIgnoreCase("null"))
                        {
                            Manip=0f;
                        }
                        else
                        {
                            Manip=Float.valueOf(map.get(Constants.NF_CALORIES).toString());
                        }
                        L=Manip*(fcal/100)*counter/Float.valueOf(serving_size_qty[position]);

                        cal=Manip*counter/Float.valueOf(serving_size_qty[position]);
                        per=(L/fcal)*100;
                        val_cal[position]=Calculate(counter ,L);
                        val_cal_graph[position]=Calculate(counter ,per);
                        L=0f;K=0f;per=0f;
                        if(map.get(Constants.NF_TOTAL_CARBOHYDRATES).equals("null"))
                        {
                            Manip=0f;
                        }
                        else{
                            Manip=Float.valueOf(map.get(Constants.NF_TOTAL_CARBOHYDRATES).toString());
                        }

                        L=Manip*3*counter/Float.valueOf(serving_size_qty[position]);

                        carbs=Manip*counter/Float.valueOf(serving_size_qty[position]);
                        per=(L/300)*100;
                        val_carb[position]=Calculate(counter ,L);
                        val_carb_graph[position]=Calculate(counter ,per);
                        L=0f;K=0f;per=0f;
                        if(map.get(Constants.NF_PROTEINS).equals("null"))
                        {
                            Manip=0f;
                        }
                        else
                        {
                            Manip=Float.valueOf(map.get(Constants.NF_PROTEINS).toString());
                        }
                        L=Manip*(fpro/100)*counter/Float.valueOf(serving_size_qty[position]);

                        pro=Manip*counter/Float.valueOf(serving_size_qty[position]);
                        per=(L/fpro)*100;

                        val_pro[position]=Calculate(counter ,L);
                        val_pro_graph[position]=Calculate(counter ,per);
                        L=0f;K=0f;per=0f;

                        if(map.get(Constants.NF_TOTAL_FAT).equals("null"))
                        {
                            Manip=0f;
                        }else{
                            Manip=Float.valueOf(map.get(Constants.NF_TOTAL_FAT).toString());
                        }

                        L=Manip*(ffat/100)*counter/Float.valueOf(serving_size_qty[position]);

                        fib=Manip*counter/Float.valueOf(serving_size_qty[position]);
                        per=(L/ffat)*100;

                        val_fib[position]=Calculate(counter ,L);
                        val_fib_graph[position]=Calculate(counter ,per);
                        L=0f;K=0f;per=0f;
                        if(map.get(Constants.NF_SODIUM).equals("null"))
                        {
                            Manip=0f;
                        }
                        else
                        {
                            Manip=Float.valueOf(map.get(Constants.NF_SODIUM).toString());
                        }
                        L=Manip*(fsod/100)*counter/Float.valueOf(serving_size_qty[position]);

                        sod=Manip*counter/Float.valueOf(serving_size_qty[position]);
                        per=(L/fsod)*100;

                        val_sod[position]=Calculate(counter ,L);
                        val_sod_graph[position]=Calculate(counter ,per);

                        qtysavesearch[position]=String.valueOf(counter);
                        checkedAddSub[position]=true;
                        Log.i("serving size quantity", serving_size_qty[position]);
                        Log.i("serving size quantity", String.valueOf(counter));
                        Log.d("my status", map.get("status").toString());
                        SharedPreferences positionpref=getActivity().getSharedPreferences("Position", Context.MODE_PRIVATE);

                        for(int i=0;i<keys.entrySet().size();i++)
                        {
                            if(map.get("status").toString().equalsIgnoreCase(("cat"+String.valueOf(i))))
                            {
                                MdbHelper.updateitemsNoAction(brand_name[position], item_name[position], String.valueOf(counter),map.get("status").toString(),keys.entrySet().size());

                                ((( NoBoringActionBarActivity) frg)).Total_manipulation(i);
                                ((( NoBoringActionBarActivity) frg)).Total_Meal();
                                notifyDataSetChanged();
                            }
//							Intent in=new Intent(NoBoringActionBarActivity.this, NoBoringActionBarActivity.class);
//							 finish();
//							 startActivity(in);
//
                        }
                    }
                    else
                    {
                        checkedAddSub[position]=false;


                    }
                    notifyDataSetChanged();
                }

                private String Calculate(int counter, float fl) {
                    // TODO Auto-generated method stub
                    int x=Math.round(fl);
                    return  String.valueOf(x);
                }
            });


			 /*
			  * Method Listener For the Minus Button
			  */

            holder.sminus.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    final int position = (Integer) v.getTag(R.id.limg_right_view);
                    LinearLayout R1 = (LinearLayout)v.getParent();
                    Log.i("value of qty","Clicked on splus");
                    TextView Tv=(TextView)R1.findViewById(R.id.ldata_qty);
                    Log.i("value of qty",Tv.getText().toString());
                    counter=Math.round(Float.valueOf(Tv.getText().toString()));
                    if(counter>1)
                    {	Float L=0f,K=0f,per=0f,Manip=0f,cal=0f,sod=0f,pro=0f,fib=0f,carbs=0f;
                        counter--;

                        if(map.get(Constants.NF_CALORIES).toString().equalsIgnoreCase("null"))
                        {
                            Manip=0f;
                        }
                        else
                        {
                            Manip=Float.valueOf(map.get(Constants.NF_CALORIES).toString());
                        }
                        L=Manip*(fcal/100)*counter/Float.valueOf(serving_size_qty[position]);

                        cal=Manip*counter/Float.valueOf(serving_size_qty[position]);
                        per=(L/fcal)*100;
                        val_cal[position]=Calculate(counter ,L);
                        val_cal_graph[position]=Calculate(counter ,per);
                        L=0f;K=0f;per=0f;
                        if(map.get(Constants.NF_TOTAL_CARBOHYDRATES).equals("null"))
                        {
                            Manip=0f;
                        }
                        else{
                            Manip=Float.valueOf(map.get(Constants.NF_TOTAL_CARBOHYDRATES).toString());
                        }

                        L=Manip*3*counter/Float.valueOf(serving_size_qty[position]);

                        carbs=Manip*counter/Float.valueOf(serving_size_qty[position]);
                        per=(L/300)*100;
                        val_carb[position]=Calculate(counter ,L);
                        val_carb_graph[position]=Calculate(counter ,per);
                        L=0f;K=0f;per=0f;
                        if(map.get(Constants.NF_PROTEINS).equals("null"))
                        {
                            Manip=0f;
                        }
                        else
                        {
                            Manip=Float.valueOf(map.get(Constants.NF_PROTEINS).toString());
                        }
                        L=Manip*(fpro/100)*counter/Float.valueOf(serving_size_qty[position]);

                        pro=Manip*counter/Float.valueOf(serving_size_qty[position]);
                        per=(L/fpro)*100;

                        val_pro[position]=Calculate(counter ,L);
                        val_pro_graph[position]=Calculate(counter ,per);
                        L=0f;K=0f;per=0f;

                        if(map.get(Constants.NF_TOTAL_FAT).equals("null"))
                        {
                            Manip=0f;
                        }else{
                            Manip=Float.valueOf(map.get(Constants.NF_TOTAL_FAT).toString());
                        }

                        L=Manip*(ffat/100)*counter/Float.valueOf(serving_size_qty[position]);

                        fib=Manip*counter/Float.valueOf(serving_size_qty[position]);
                        per=(L/ffat)*100;

                        val_fib[position]=Calculate(counter ,L);
                        val_fib_graph[position]=Calculate(counter ,per);
                        L=0f;K=0f;per=0f;
                        if(map.get(Constants.NF_SODIUM).equals("null"))
                        {
                            Manip=0f;
                        }
                        else
                        {
                            Manip=Float.valueOf(map.get(Constants.NF_SODIUM).toString());
                        }
                        L=Manip*(fsod/100)*counter/Float.valueOf(serving_size_qty[position]);

                        sod=Manip*counter/Float.valueOf(serving_size_qty[position]);
                        per=(L/fsod)*100;

                        val_sod[position]=Calculate(counter ,L);
                        val_sod_graph[position]=Calculate(counter ,per);

                        qtysavesearch[position]=String.valueOf(counter);
                        checkedAddSub[position]=true;
                        Log.i("serving size quantity", serving_size_qty[position]);
                        Log.i("serving size quantity", String.valueOf(counter));
                        Log.d("my status", map.get("status").toString());

                        for(int i=0;i<keys.entrySet().size();i++)
                        {
                            if(map.get("status").toString().equalsIgnoreCase(("cat"+String.valueOf(i))))
                            {
                                MdbHelper.updateitemsNoAction(brand_name[position], item_name[position], String.valueOf(counter),map.get("status").toString(),keys.entrySet().size());

                                ((( NoBoringActionBarActivity) frg)).Total_manipulation(i);
                                ((( NoBoringActionBarActivity) frg)).Total_Meal();
                                notifyDataSetChanged();
                            }
                        }
                    }
                    else
                    {
                        checkedAddSub[position]=false;
                        Toast.makeText(activity, "Qty can't less than 1", Toast.LENGTH_LONG).show();

                    }
                    notifyDataSetChanged();
                }

                private String Calculate(int counter, float fl) {
                    // TODO Auto-generated method stub
                    int x=Math.round(fl);
                    return  String.valueOf(x);
                }
            });

            //On Check Box Check Events


            try{



                Float qty=1f;



                qty=Float.valueOf(map.get(map.get("status").toString()+"qty").toString())/Float.valueOf(map.get(Constants.NF_SERVING_SIZE_QTY).toString());
                qty=Float.valueOf(String.format("%.1f",qty));
                Log.d("***Value of Qty***",String.format("%.1f",qty));



                if(!map.get(Constants.NF_TOTAL_CARBOHYDRATES).toString().equals("null"))
                {
                    holder.carbs_val.setText(checkedAddSub[position] ? String.format("%.0f",Float.valueOf(val_carb[position])) : String.format("%.0f",Float.valueOf(map.get(Constants.NF_TOTAL_CARBOHYDRATES).toString())*(fcarbs/100)*qty).toString());
                    // holder.calories_val.setText(String.format("%.0f",Float.valueOf(map.get(Constants.NF_CALORIES).toString())));
                }
                else
                {
                    holder.carbs_val.setText(checkedAddSub[position] ? String.format("%.0f",Float.valueOf(val_carb[position])) : String.valueOf(0));
                }


                if(!map.get(Constants.NF_PROTEINS).toString().equals("null"))
                {
                    holder.proteins_val.setText(checkedAddSub[position] ? String.format("%.0f",Float.valueOf(val_pro[position])) : String.format("%.0f",Float.valueOf(map.get(Constants.NF_PROTEINS).toString())*(fpro/100)*qty).toString());
                    // holder.calories_val.setText(String.format("%.0f",Float.valueOf(map.get(Constants.NF_CALORIES).toString())));
                }
                else
                {
                    holder.proteins_val.setText(checkedAddSub[position] ? String.format("%.0f",Float.valueOf(val_pro[position])) : String.valueOf(0));
                }
                if(!map.get(Constants.NF_CALORIES).toString().equals("null"))
                {
                    holder.calories_val.setText(checkedAddSub[position] ? String.format("%.0f",Float.valueOf(val_cal[position])) : String.format("%.0f",Float.valueOf(map.get(Constants.NF_CALORIES).toString())*(fcal/100)*qty).toString());
                    // holder.calories_val.setText(String.format("%.0f",Float.valueOf(map.get(Constants.NF_CALORIES).toString())));
                }
                else
                {
                    holder.calories_val.setText(checkedAddSub[position] ? String.format("%.0f",Float.valueOf(val_cal[position])) : String.valueOf(0));
                }
                if(!map.get(Constants.NF_SODIUM).toString().equals("null"))
                {
                    holder.sodium_val.setText(checkedAddSub[position] ? String.format("%.0f",Float.valueOf(val_sod[position])) : String.format("%.0f",Float.valueOf(map.get(Constants.NF_SODIUM).toString())*(fsod/100)*qty).toString());
                    // holder.calories_val.setText(String.format("%.0f",Float.valueOf(map.get(Constants.NF_CALORIES).toString())));
                }
                else
                {
                    holder.sodium_val.setText(checkedAddSub[position] ? String.format("%.0f",Float.valueOf(val_sod[position])) : String.valueOf(0));
                }
                if(!map.get(Constants.NF_TOTAL_FAT).toString().equals("null"))
                {
                    holder.Fiber_val.setText(checkedAddSub[position] ? String.format("%.0f",Float.valueOf(val_fib[position])) : String.format("%.0f",Float.valueOf(map.get(Constants.NF_TOTAL_FAT).toString())*(ffat/100)*qty).toString());
                    // holder.calories_val.setText(String.format("%.0f",Float.valueOf(map.get(Constants.NF_CALORIES).toString())));
                }
                else
                {
                    holder.Fiber_val.setText(checkedAddSub[position] ? String.format("%.0f",Float.valueOf(val_fib[position])) : String.valueOf(0));
                }

                String Fib=  map.get(Constants.NF_TOTAL_FAT).toString();
                if(countExclaim(Fib)>0)
                {
                    Log.d("value of Fiber", Fib.substring(0,countExclaim(Fib)));
                    holder.Fiber.setMaxHeight(195);
                    //holder.Fiber.setHeight(Integer.parseInt(Fib.substring(0,countExclaim(Fib)))*2);
                    holder.Fiber.setHeight(checkedAddSub[position] ? Integer.parseInt(String.format("%.0f",Float.valueOf(val_fib_graph[position])*multiplyfactor_cal*qty)) : Integer.parseInt(String.format("%.0f",Float.valueOf(map.get(Constants.NF_TOTAL_FAT).toString())*multiplyfactor_cal*qty).toString()));
                }
                else
                {	if(Fib.equals("null"))
                {
                    holder.Fiber.setHeight(1);
                    //holder.Fiber.setHeight(checkedAddSub[position] ? Integer.parseInt(String.format("%.0f",Float.valueOf(val_fib_graph[position])*2)) : 1);
                }
                else{

                    holder.Fiber.setHeight(checkedAddSub[position] ? Integer.parseInt(String.format("%.0f",Float.valueOf(val_fib_graph[position])*multiplyfactor_cal*qty)) : Integer.parseInt(String.format("%.0f",Float.valueOf(map.get(Constants.NF_TOTAL_FAT).toString())*multiplyfactor_cal*qty).toString()));
                }
                }

                String sodium =  map.get(Constants.NF_SODIUM).toString();

                if(countExclaim(sodium)>0)
                {	Log.d("value of sodium", sodium.substring(0,countExclaim(sodium)));
                    holder.sodium.setMaxHeight(195);
                    //holder.sodium.setHeight((Integer)Integer.parseInt(sodium.substring(0,countExclaim(sodium)))*2);
                    holder.sodium.setHeight(checkedAddSub[position] ? Integer.parseInt(String.format("%.0f",Float.valueOf(val_sod_graph[position])*multiplyfactor_cal*qty)) : Integer.parseInt(String.format("%.0f",Float.valueOf(map.get(Constants.NF_SODIUM).toString())*multiplyfactor_cal*qty).toString()));
                }
                else
                {	if(sodium.equals("null"))
                {
                    holder.sodium.setHeight(1);
                }
                else{
                    holder.sodium.setHeight(Integer.parseInt(String.valueOf(Math.round(Float.valueOf(sodium)*qty))));
                }
                }
                String carbs=  map.get(Constants.NF_TOTAL_CARBOHYDRATES).toString();
                if(countExclaim(carbs)>0)
                {
                    holder.carbs.setMaxHeight(195);
                    //holder.carbs.setHeight(Integer.parseInt(carbs.substring(0,countExclaim(carbs)))*2);
                    holder.carbs.setHeight(checkedAddSub[position] ? Integer.parseInt(String.format("%.0f",Float.valueOf(val_carb_graph[position])*qty*2)) : Integer.parseInt(String.format("%.0f",Float.valueOf(map.get(Constants.NF_TOTAL_CARBOHYDRATES).toString())*multiplyfactor_cal*qty).toString()));
                }
                else
                {	if(carbs.equals("null"))
                {
                    holder.carbs.setHeight(1);
                }
                else
                {
                    holder.carbs.setHeight(Integer.parseInt(String.valueOf(Math.round(Float.valueOf(carbs)*qty))));
                    //holder.carbs.setWidth(i);
                    //	holder.carbs.setText(carbs);
                }
                }

                String Prote=  map.get(Constants.NF_PROTEINS).toString();

                if(countExclaim(Prote)>0)
                {
                    holder.proteins.setMaxHeight(195);
                    //	holder.proteins.setHeight(Integer.parseInt(Prote.substring(0,countExclaim(Prote)))*2);
                    holder.proteins.setHeight(checkedAddSub[position] ? Integer.parseInt(String.format("%.0f",Float.valueOf(val_pro_graph[position])*multiplyfactor_cal*qty)) : Integer.parseInt(String.format("%.0f",Float.valueOf(map.get(Constants.NF_PROTEINS).toString())*multiplyfactor_cal*qty).toString()));
                }
                else
                {	if(Prote.equals("null"))
                {
                    holder.proteins.setHeight(1);
                }
                else{

                    holder.proteins.setHeight(Integer.parseInt(String.valueOf(Math.round(Float.valueOf(Prote)*qty))));
                }
                }

                String calo=  map.get(Constants.NF_CALORIES).toString();

                if(countExclaim(calo)>0)
                {

                    holder.calories.setMaxHeight(195);
                    //	holder.calories.setHeight((Integer)Integer.parseInt(calo.substring(0,countExclaim(calo)))*2);
                    holder.calories.setHeight(checkedAddSub[position] ? Integer.parseInt(String.format("%.0f",Float.valueOf(val_cal_graph[position])*multiplyfactor_cal*qty)) : Integer.parseInt(String.format("%.0f",Float.valueOf(map.get(Constants.NF_CALORIES).toString())*multiplyfactor_cal*qty).toString()));
                }
                else
                {	if(calo.equals("null"))
                {
                    holder.calories.setHeight(1);
                }
                else{
                    //holder.calories.setWidth(i);
                    holder.calories.setHeight(Integer.parseInt(String.valueOf(Math.round(Float.valueOf(calo)*qty))));
                    //	holder.calories.setText(calo);
                }
                }

            }
            catch(NumberFormatException num)
            {
                num.printStackTrace();
            }

            final int z=position;

            return convertview;
        }


        /*
         * @class to define the references of the View in the listview row elements
         */
        private class Viewholder extends Activity
        {
            TextView title;
            TextView servingsize;
            //TextView status;

            TextView carbs;
            TextView proteins;
            TextView calories;
            ImageView img_serve_size;
            TextView sodium;
            TextView Fiber;
            TextView lserving_qty;
            TextView carbs_val;
            TextView proteins_val;
            TextView calories_val;
            TextView sodium_val;
            TextView Fiber_val;

            TextView tvbrand;
            ImageView sminus,splus,imgsetting;






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
    }


    public void Clear() {
//Clearing values for the text view of the sub-meals
        tvcal_value = (TextView) getView().findViewById(R.id.lTcal_desc_val);
        tvcal_value_graph = (TextView) getView().findViewById(R.id.lTcaloriesview);

        tvcar_value = (TextView) getView().findViewById(R.id.lTprot_desc_val);
        tvcar_value_graph = (TextView) getView().findViewById(R.id.lTcarbs_desc_val);

        tvpro_value = (TextView) getView().findViewById(R.id.lTcarbsview);
        tvpro_value_graph = (TextView) getView().findViewById(R.id.lTProtienview);

        tvsod_value = (TextView) getView().findViewById(R.id.lTsodium_desc_val);
        tvsod_value_graph = (TextView) getView().findViewById(R.id.lTsodiumview);

        tvfib_value = (TextView) getView().findViewById(R.id.lTFiber_desc_val);
        tvfib_value_graph = (TextView) getView().findViewById(R.id.lTFiberview);


        tvvitA_value_graph = (TextView) getView().findViewById(R.id.lvita);
        tvvitA_value = (TextView) getView().findViewById(R.id.lvita_desc);


        tvvitC_value_graph = (TextView) getView().findViewById(R.id.lvitc);
        tvvitC_value = (TextView) getView().findViewById(R.id.lvitc_desc);


        tvcal_value.setText("0");
        tvcal_value_graph.setHeight(1);

        tvcar_value.setText("0");
        tvcar_value_graph.setHeight(1);

        tvpro_value.setText("0");
        tvpro_value_graph.setHeight(1);

        tvsod_value.setText("0");
        tvsod_value_graph.setHeight(1);

        tvfib_value.setText("0");
        tvfib_value_graph.setHeight(1);

        tvvitA_value.setText("0");
        tvvitA_value_graph.setHeight(1);

        tvvitC_value.setText("0");
        tvvitC_value_graph.setHeight(1);

        //Clearing the Values for the Gross Nutrition of the Products
        tvmcal_value = (TextView) getView().findViewById(R.id.mlTcal_desc_val);
        tvmcal_value_graph = (TextView) getView().findViewById(R.id.mlTcaloriesview);

        tvmcar_value = (TextView) getView().findViewById(R.id.mlTprot_desc_val);
        tvmcar_value_graph = (TextView) getView().findViewById(R.id.mlTProtienview);

        tvmpro_value = (TextView) getView().findViewById(R.id.mlTcarbs_desc_val);
        tvmpro_value_graph = (TextView) getView().findViewById(R.id.mlTcarbsview);

        tvmsod_value = (TextView) getView().findViewById(R.id.mlTsodium_desc_val);
        tvmsod_value_graph = (TextView) getView().findViewById(R.id.mlTsodiumview);

        tvmfib_value = (TextView) getView().findViewById(R.id.mlTFiber_desc_val);
        tvmfib_value_graph = (TextView) getView().findViewById(R.id.mlTFiberview);

        tvmvitA_value_graph = (TextView) getView().findViewById(R.id.mlvita);
        tvmvitA_value = (TextView) getView().findViewById(R.id.mlvita_desc);


        tvmvitC_value_graph = (TextView) getView().findViewById(R.id.mlvitc);
        tvmvitC_value = (TextView) getView().findViewById(R.id.mlvitc_desc);

        tvmcal_value.setText("0");
        tvmcal_value_graph.setHeight(1);

        tvmcar_value.setText("0");
        tvmcar_value_graph.setHeight(1);

        tvmpro_value.setText("0");
        tvmpro_value_graph.setHeight(1);

        tvmsod_value.setText("0");
        tvmsod_value_graph.setHeight(1);

        tvmfib_value.setText("0");
        tvmfib_value_graph.setHeight(1);

        tvcar_value.setText("0");
        tvcar_value_graph.setHeight(1);

        tvpro_value.setText("0");
        tvpro_value_graph.setHeight(1);

        tvsod_value.setText("0");
        tvsod_value_graph.setHeight(1);

        tvmvitA_value.setText("0");
        tvmvitA_value_graph.setHeight(1);

        tvmvitC_value.setText("0");
        tvmvitC_value_graph.setHeight(1);

    }

    //call me function

    private void callMe(int j) {
        // TODO Auto-generated method stub

        contactList.clear();

        Clear();

        Cursor cursor = null;
        String status=null;
        MealSnapDesc=(TextView)getView().findViewById(R.id.lTotalserving_size_grams);
        MealSnapDesc.setText(sharedPref.getString("cat"+ String.valueOf(j), null));
        cursor=MdbHelper.fetch_allBreakfast(String.valueOf(j),Constants.CATEGORIES+String.valueOf(j));
        status=Constants.CATEGORIES+String.valueOf(j);
        new Constants();
        Log.d("VAL VAL", String.valueOf(j)+Constants.CATEGORIES+String.valueOf(j)+String.valueOf(cursor.getCount()));
        item_name=new String[cursor.getCount()];
        brand_name=new String[cursor.getCount()];
        serving_size_qty=new String[cursor.getCount()];
        serving_size_unit=new String[cursor.getCount()];
        serving_size_gram=new String[cursor.getCount()];
        nf_calories=new String[cursor.getCount()];
        nf_calories_from_fat=new String[cursor.getCount()];
        total_fat=new String[cursor.getCount()];
        saturated_fat=new String[cursor.getCount()];
        poly_sat_fat=new String[cursor.getCount()];
        nf_cholesterol=new String[cursor.getCount()];
        nf_sodium=new String[cursor.getCount()];
        total_carbohydrates=new String[cursor.getCount()];
        nf_proteins=new String[cursor.getCount()];
        nf_vitamin_A=new String[cursor.getCount()];
        nf_vitamin_C=new String[cursor.getCount()];
        nf_calcium=new String[cursor.getCount()];
        nf_iron=new String[cursor.getCount()];
        nf_daietry_fiber=new String[cursor.getCount()];
        nf_sugar=new String[cursor.getCount()];
        String catqty[][]=new String[keys.entrySet().size()][cursor.getCount()];
        String catqtySet[][]=new String[keys.entrySet().size()][cursor.getCount()];
        String cat[][]=new String[keys.entrySet().size()][cursor.getCount()];
        for(int i=0;i<keys.entrySet().size();i++)
        {
            for(int l1=0;l1<cursor.getCount();l1++)

                catqty[i][l1]=null;
        }

        Log.i("Number of article saved", String.valueOf(cursor.getCount()));
        int l=0;
        int curscout=cursor.getCount();
        if(cursor.getCount()>0)
        {

            if(cursor.moveToFirst())

            { do

            {

                item_name[l]=cursor.getString(cursor.getColumnIndex("pdt_name"));
                brand_name[l]=cursor.getString(cursor.getColumnIndex("b_name"));
                serving_size_gram[l]=cursor.getString(cursor.getColumnIndex("serving_gram"));
                serving_size_qty[l]=cursor.getString(cursor.getColumnIndex("qty"));

                Log.i("Hello bro check the qty", serving_size_qty[l]);

                serving_size_unit[l]=cursor.getString(cursor.getColumnIndex("unit"));
                nf_calories[l]=cursor.getString(cursor.getColumnIndex("calval"));
                nf_calcium[l]=cursor.getString(cursor.getColumnIndex("calciumval"));
                total_fat[l]=cursor.getString(cursor.getColumnIndex("fatval"));
                saturated_fat[l]=cursor.getString(cursor.getColumnIndex("satfatval"));
                total_carbohydrates[l]=cursor.getString(cursor.getColumnIndex("carbsval"));
                nf_cholesterol[l]=cursor.getString(cursor.getColumnIndex("cholestrolval"));
                nf_sodium[l]=cursor.getString(cursor.getColumnIndex("sodval"));
                nf_vitamin_A[l]=cursor.getString(cursor.getColumnIndex("vitA"));
                nf_vitamin_C[l]=cursor.getString(cursor.getColumnIndex("vitC"));
                nf_iron[l]=cursor.getString(cursor.getColumnIndex("ironval"));
                nf_proteins[l]=cursor.getString(cursor.getColumnIndex("proval"));

                for(int i=0;i<keys.entrySet().size();i++)
                {
                    for(int l1=0;l1<curscout;l1++)
                    {catqty[i][l1]=(cursor.getString(cursor.getColumnIndex("cat"+i+"qty")));
                        catqtySet[i][l1]=String.valueOf(Float.valueOf(cursor.getString(cursor.getColumnIndex("cat"+i+"qty")))/Float.valueOf(cursor.getString(cursor.getColumnIndex("qty"))));
                        cat[i][l1]=cursor.getString(cursor.getColumnIndex("cat"+i));
                    }
                }


                int k=2;
                HashMap<String, String> contact = new HashMap<String, String>();

                for(int i=0;i<Constraints.length;i++)
                {
                    value[i]=null;
                    value[i]=cursor.getString(k);
                    Log.d(cursor.getColumnName(k), value[i]);
                    contact.put(Constraints[i], value[i]);
                    Log.d(Constraints[i], value[i]);
                    k++;
                }


                for(int i=0;i<keys.entrySet().size();i++)
                {
                    for(int l1=0;l1<curscout;l1++)
                        contact.put(Constants.CATEGORIES + String.valueOf(i)+ "qty", catqty[i][l1]);


                }
                contact.put("status", status);
                contactList.add(l,contact);

                l++;
            }
            while(cursor.moveToNext());
                cursor.close();
            }



        }
        else
        {
            tvcal_value = (TextView) getView().findViewById(R.id.lTcal_desc_val);
            tvcal_value_graph = (TextView) getView().findViewById(R.id.lTcaloriesview);

            tvcar_value = (TextView) getView().findViewById(R.id.lTprot_desc_val);
            tvcar_value_graph = (TextView) getView().findViewById(R.id.lTProtienview);

            tvpro_value = (TextView) getView().findViewById(R.id.lTcarbs_desc_val);
            tvpro_value_graph = (TextView) getView().findViewById(R.id.lTcarbsview);

            tvsod_value = (TextView) getView().findViewById(R.id.lTsodium_desc_val);
            tvsod_value_graph = (TextView) getView().findViewById(R.id.lTsodiumview);

            tvfib_value = (TextView) getView().findViewById(R.id.lTFiber_desc_val);
            tvfib_value_graph = (TextView) getView().findViewById(R.id.lTFiberview);
            tvcal_value.setText("0");
            tvcal_value_graph.setHeight(1);

            tvcar_value.setText("0");
            tvcar_value_graph.setHeight(1);

            tvpro_value.setText("0");
            tvpro_value_graph.setHeight(1);

            tvsod_value.setText("0");
            tvsod_value_graph.setHeight(1);

            tvfib_value.setText("0");
            tvfib_value_graph.setHeight(1);


            calplus.setText("0");
            proplus.setText("0");
            carbsplus.setText("0");
            fibplus.setText("0");
            sodplus.setText("0");
            vitaplus.setText("0");
            vitcplus.setText("0");
            LinearLayout  layout=new LinearLayout(this.getActivity());
            //layout.setBackgroundResource(R.color.White);

            TextView  tv=new TextView(this.getActivity());
            // set the TextView properties like color, size etc
            tv.setTypeface(roboto_light1);
            tv.setTextSize(15);

            tv.setGravity(Gravity.CENTER_VERTICAL);

            // set the text you want to show in  Toast
            tv.setText("No meal yet addeded to this Categories");

            layout.addView(tv);

            //context is object of Context write "this" if you are an Activity
            // Set The layout as Toast View
            //toast.cancel();
            toast.setView(layout);
            toast.setDuration(Toast.LENGTH_LONG);

            // Position you toast here toast position is 50 dp from bottom you can give any integral value
            toast.setGravity(Gravity.BOTTOM, 0, 150);
            toast.show();
            Clear();
        }


        Total_manipulation(j);
        Total_Meal();

    }

    //Total seprate Meal caluculated

    public void ClickMe(int position)
    {

        Log.i("position of row clicked", String.valueOf(position));
        // TODO Auto-generated method stub
        Intent in = new Intent(NoBoringActionBarActivity.this.getActivity(),ChartActivity.class);
        in.putExtra(Constants.ITEM_NAME,item_name[position] );
        in.putExtra(Constants.BRAND_NAME, brand_name[position]);
        in.putExtra(Constants.NF_SERVING_SIZE_QTY,serving_size_qty[position]);
        in.putExtra(Constants.NF_SERVING_SIZE_UNIT,serving_size_unit[position]);
        in.putExtra(Constants.NF_SERVING_WEIGHT_GRAMS,serving_size_gram[position]);
        if(nf_calories[position].equals("null"))
        {
            in.putExtra(Constants.NF_CALORIES, nf_calories[position] );
        }
        else
        {
            in.putExtra(Constants.NF_CALORIES, String.valueOf(Float.valueOf(nf_calories[position])*(fcal/100)) );
        }
        //in.putExtra("nf_calories_fat",nf_calories_from_fat[position]);
        if(total_fat[position].equals("null"))
        {
            in.putExtra(Constants.NF_TOTAL_FAT,total_fat[position] );
        }
        else
        {
            in.putExtra(Constants.NF_TOTAL_FAT,String.valueOf(Float.valueOf(total_fat[position])*(ffat/100)) );
        }
        if(saturated_fat[position].equals("null"))
        {
            in.putExtra(Constants.NF_SATURATED_FAT, saturated_fat[position] );
        }
        else
        {
            in.putExtra(Constants.NF_SATURATED_FAT, String.valueOf(Float.valueOf(saturated_fat[position])*0.24f) );
        }
        //in.putExtra("poly_sat_fat", poly_sat_fat[position]);
        if(nf_cholesterol[position].equals("null"))
        {
            in.putExtra(Constants.NF_CHOLESTEROL, nf_cholesterol[position] );
        }
        else
        {
            in.putExtra(Constants.NF_CHOLESTEROL, String.valueOf(Float.valueOf(nf_cholesterol[position])*3) );
        }
        if(nf_sodium[position].equals("null"))
        {
            in.putExtra(Constants.NF_SODIUM,nf_sodium[position] );
        }
        else
        {
            in.putExtra(Constants.NF_SODIUM,String.valueOf(Float.valueOf(nf_sodium[position])*(fsod/100)) );
        }
        if(total_carbohydrates[position].equals("null"))
        {
            in.putExtra(Constants.NF_TOTAL_CARBOHYDRATES, total_carbohydrates[position] );
        }
        else
        {
            in.putExtra(Constants.NF_TOTAL_CARBOHYDRATES, String.valueOf(Float.valueOf(total_carbohydrates[position])*(fcarbs/100)) );
        }
        if(nf_proteins[position].equals("null"))
        {
            in.putExtra(Constants.NF_PROTEINS, nf_proteins[position] );
        }
        else
        {
            in.putExtra(Constants.NF_PROTEINS, String.valueOf(Float.valueOf(nf_proteins[position])*(fpro/100)) );
        }
        in.putExtra(Constants.NF_VITAMIN_A, nf_vitamin_A[position]);
        in.putExtra(Constants.NF_VITAMIN_C, nf_vitamin_C[position]);
        in.putExtra(Constants.NF_IRON, nf_iron[position]);
        in.putExtra(Constants.NF_CALCIUM, nf_calcium[position]);
        //in.putExtra("dietary_fiber", nf_daietry_fiber[position]);
        //in.putExtra("sugar", nf_sugar[position]);
        startActivity(in);

    }



    public void Total_manipulation(int j)
    {

        Cursor cursor = null;
        String status=null;

        cursor=MdbHelper.fetch_allBreakfast(String.valueOf(j),Constants.CATEGORIES+String.valueOf(j));

        status=Constants.CATEGORIES+String.valueOf(j);

        item_name=new String[cursor.getCount()];
        brand_name=new String[cursor.getCount()];
        serving_size_qty=new String[cursor.getCount()];
        serving_size_unit=new String[cursor.getCount()];
        serving_size_gram=new String[cursor.getCount()];
        nf_calories=new String[cursor.getCount()];
        nf_calories_from_fat=new String[cursor.getCount()];
        total_fat=new String[cursor.getCount()];
        saturated_fat=new String[cursor.getCount()];
        poly_sat_fat=new String[cursor.getCount()];
        nf_cholesterol=new String[cursor.getCount()];
        nf_sodium=new String[cursor.getCount()];
        total_carbohydrates=new String[cursor.getCount()];
        nf_proteins=new String[cursor.getCount()];
        nf_vitamin_A=new String[cursor.getCount()];
        nf_vitamin_C=new String[cursor.getCount()];
        nf_calcium=new String[cursor.getCount()];
        nf_iron=new String[cursor.getCount()];
        nf_daietry_fiber=new String[cursor.getCount()];
        nf_sugar=new String[cursor.getCount()];
        String catqty[][]=new String[keys.entrySet().size()][cursor.getCount()];
        String[][] cat=new String[keys.entrySet().size()][cursor.getCount()];
        Log.i("Number of article saved", String.valueOf(cursor.getCount()));
        int l=0;
        if(cursor.getCount()>0)
        {
            if(cursor.moveToFirst())

            { do

            {

                Log.i("value of l",String.valueOf(l));


                item_name[l]=cursor.getString(cursor.getColumnIndex("pdt_name"));
                brand_name[l]=cursor.getString(cursor.getColumnIndex("b_name"));
                serving_size_gram[l]=cursor.getString(cursor.getColumnIndex("serving_gram"));
                serving_size_qty[l]=cursor.getString(cursor.getColumnIndex("qty"));
                serving_size_unit[l]=cursor.getString(cursor.getColumnIndex("unit"));
                nf_calories[l]=cursor.getString(cursor.getColumnIndex("calval"));
                nf_calcium[l]=cursor.getString(cursor.getColumnIndex("calciumval"));
                total_fat[l]=cursor.getString(cursor.getColumnIndex("fatval"));
                saturated_fat[l]=cursor.getString(cursor.getColumnIndex("satfatval"));
                total_carbohydrates[l]=cursor.getString(cursor.getColumnIndex("carbsval"));
                nf_cholesterol[l]=cursor.getString(cursor.getColumnIndex("cholestrolval"));
                nf_sodium[l]=cursor.getString(cursor.getColumnIndex("sodval"));
                nf_vitamin_A[l]=cursor.getString(cursor.getColumnIndex("vitA"));
                nf_vitamin_C[l]=cursor.getString(cursor.getColumnIndex("vitC"));
                nf_iron[l]=cursor.getString(cursor.getColumnIndex("ironval"));
                nf_proteins[l]=cursor.getString(cursor.getColumnIndex("proval"));

                catqty[j][l]=String.valueOf(Float.valueOf(cursor.getString(cursor.getColumnIndex("cat"+j+"qty")))/Float.valueOf(cursor.getString(cursor.getColumnIndex("qty"))));
                cat[j][l]=cursor.getString(cursor.getColumnIndex("cat"+j));


                l++;
            }
            while(cursor.moveToNext());
                cursor.close();
            }

	/*
	* Manipulating the Total_values of the Graph
	*/
            tvcal_value = (TextView) getView().findViewById(R.id.lTcal_desc_val);
            tvcal_value_graph = (TextView) getView().findViewById(R.id.lTcaloriesview);

            tvcar_value = (TextView) getView().findViewById(R.id.lTprot_desc_val);
            tvcar_value_graph = (TextView) getView().findViewById(R.id.lTProtienview);

            tvpro_value = (TextView) getView().findViewById(R.id.lTcarbs_desc_val);
            tvpro_value_graph = (TextView) getView().findViewById(R.id.lTcarbsview);

            tvsod_value = (TextView) getView().findViewById(R.id.lTsodium_desc_val);
            tvsod_value_graph = (TextView) getView().findViewById(R.id.lTsodiumview);

            tvfib_value = (TextView) getView().findViewById(R.id.lTFiber_desc_val);
            tvfib_value_graph = (TextView) getView().findViewById(R.id.lTFiberview);

            tvvitA_value_graph = (TextView) getView().findViewById(R.id.lvita);
            tvvitA_value = (TextView) getView().findViewById(R.id.lvita_desc);


            tvvitC_value_graph = (TextView) getView().findViewById(R.id.lvitc);
            tvvitC_value = (TextView) getView().findViewById(R.id.lvitc_desc);



            Float totalcal=0f,totalpro=0f,tottalfib=0f,totalsod=0f,totalcarbs=0f,newqty[][];
            //tvTitem.setText(String.valueOf(cursor.getCount()));
            newqty=new Float[keys.entrySet().size()][cursor.getCount()];
            Float L=0f,per=0f;
            for(int m=0;m<keys.entrySet().size();m++){
                for(int i=0;i<cursor.getCount();i++)
                {
                    newqty[m][i]=0f;
                }
            }


            for(int x=0;x<cursor.getCount();x++)
            {
                if(!cat[j][x].equals("100"))
                {
                    if(status.equalsIgnoreCase("cat"+j))
                        newqty[j][x]=((Float.valueOf(catqty[j][x])));
                    Log.d("Total_fit", String.valueOf(newqty[j][x]));
                }
                else
                {

                }

            }


            //For Calories


            for(int x=0;x<cursor.getCount();x++)
            {
                if(status.equalsIgnoreCase("cat"+j))
                {
                    if(serving_size_qty[x].equals(catqty[j][x]))
                    {

                        if(nf_calories[x].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(nf_calories[x]))/Float.valueOf(serving_size_qty[x])+per;
                        }

                        Log.i("cal",nf_calories[x]);

                    }
                    else
                    {

                        if(nf_calories[x].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(nf_calories[x])*newqty[j][x])+per;
                        }

                        Log.i("cal",nf_calories[x]);
                    }

                }


            }

            Log.i("My L",String.valueOf(L));
            Log.i("cal",String.valueOf(newqty));
            per=Float.valueOf(String.format("%.1f",per));


            L=per*fcal/100;
            Log.d("per cal value",String.valueOf(per));
            Log.d("L cal value",String.valueOf(L));
            tvcal_value.setText(String.valueOf(Math.round(L)));
            tvcal_value_graph.setHeight( Integer.parseInt(String.format("%.0f",per*multiplyfactor_cal)));
            calplus.setText(String.format("%.0f",per)+"%");
            if(Integer.parseInt(String.format("%.0f",per))>=98)

            {
                tvcal_value.setTextColor(Color.WHITE);
                tvcal_value.setBackgroundColor(getResources().getColor(com.myapplication.R.color.LIGHTRED));
            }
            per=0f;

            //Total Carbohydrate Manipulation


            for(int x=0;x<cursor.getCount();x++)
            {
                if(status.equalsIgnoreCase("cat"+j))
                {
                    if(serving_size_qty[x].equals(catqty[j][x]))
                    {

                        if(total_carbohydrates[x].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(total_carbohydrates[x]))/Float.valueOf(serving_size_qty[x])+per;
                        }

                        Log.i("cal",nf_calories[x]);

                    }
                    else
                    {

                        if(total_carbohydrates[x].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(total_carbohydrates[x])*newqty[j][x])+per;
                        }

                        Log.i("cal",nf_calories[x]);
                    }

                }


            }


            Log.i("carbs per",String.valueOf(per));
            per=Float.valueOf(String.format("%.1f",per));
            //per=per*newqty;
            Log.i("carbs",String.valueOf(newqty));
            //newqty=1f;
            L=per*fcarbs/100;
            Log.i("per value",String.valueOf(per));
            Log.i("L value",String.valueOf(L));
            tvcar_value.setText(String.valueOf(Math.round(L)));
            carbsplus.setText(String.format("%.0f",per)+"%");
            if(Integer.parseInt(String.format("%.0f",per))>=98)
            {
                tvcar_value.setTextColor(Color.WHITE);
                tvcar_value.setBackgroundColor(getResources().getColor(com.myapplication.R.color.LIGHTRED));
            }

            tvcar_value_graph.setHeight( Integer.parseInt(String.format("%.0f",per*multiplyfactor_cal)));

            per=0f;
            //Total Fat Manipulation

            for(int x=0;x<cursor.getCount();x++)
            {
                if(status.equalsIgnoreCase("cat"+j))
                {
                    if(serving_size_qty[x].equals(catqty[j][x]))
                    {

                        if(total_fat[x].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(total_fat[x]))/Float.valueOf(serving_size_qty[x])+per;
                        }

                        Log.i("cal",total_fat[x]);

                    }
                    else
                    {

                        if(total_fat[x].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(total_fat[x])*newqty[j][x])+per;
                        }

                        Log.i("cal",total_fat[x]);
                    }

                }
            }




            Log.i("fats",String.valueOf(per));
            per=Float.valueOf(String.format("%.1f",per));
//			per=per*newqty;

            Log.i("fat",String.valueOf(newqty));
            //	newqty=1f;
            L=per*ffat/100;
            Log.i("per value",String.valueOf(per));

            fibplus.setText(String.format("%.0f",per)+"%");
            if(Integer.parseInt(String.format("%.0f",per))>=98)
            {
                tvfib_value.setTextColor(Color.WHITE);
                tvfib_value.setBackgroundColor(getResources().getColor(com.myapplication.R.color.LIGHTRED));
            }

            tvfib_value.setText(String.valueOf(Math.round(L)));
            tvfib_value_graph.setHeight( Integer.parseInt(String.format("%.0f",per*multiplyfactor_cal)));

            per=0f;
            //Manipulation For the Proteins Value

            for(int x=0;x<cursor.getCount();x++)
            {
                if(status.equalsIgnoreCase("cat"+j))
                {
                    if(serving_size_qty[x].equals(catqty[j][x]))
                    {

                        if(nf_proteins[x].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(nf_proteins[x]))/Float.valueOf(serving_size_qty[x])+per;
                        }

                        Log.i("cal",nf_proteins[x]);

                    }
                    else
                    {

                        if(nf_proteins[x].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(nf_proteins[x])*newqty[j][x])+per;
                        }

                        Log.i("cal",nf_proteins[x]);
                    }

                }


            }



            Log.i("pro per",String.valueOf(per));
            per=Float.valueOf(String.format("%.1f",per));
            //per=per*newqty;
            Log.i("pro",String.valueOf(newqty));
            //newqty=1f;
            L=per*fpro/100;
            Log.i("per value",String.valueOf(per));
            tvpro_value.setText(String.valueOf(Math.round(L)));
            proplus.setText(String.format("%.0f",per)+"%");
            if(Integer.parseInt(String.format("%.0f",per))>=98)
            {
                tvpro_value.setTextColor(Color.WHITE);
                tvpro_value.setBackgroundColor(getResources().getColor(com.myapplication.R.color.LIGHTRED));
            }

            tvpro_value_graph.setHeight( Integer.parseInt(String.format("%.0f",per*multiplyfactor_cal)));


            per=0f;
            //Total Manipulation for the NF_SODIUM


            for(int x=0;x<cursor.getCount();x++)
            {
                if(status.equalsIgnoreCase("cat"+j))
                {
                    if(serving_size_qty[x].equals(catqty[j][x]))
                    {

                        if(nf_sodium[x].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(nf_sodium[x]))/Float.valueOf(serving_size_qty[x])+per;
                        }

                        Log.i("cal",nf_sodium[x]);

                    }
                    else
                    {

                        if(nf_sodium[x].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(nf_sodium[x])*newqty[j][x])+per;
                        }

                        Log.i("cal",nf_sodium[x]);
                    }

                }


            }

            Log.i("sod per",String.valueOf(per));
            per=Float.valueOf(String.format("%.1f",per));
            //per=per*newqty;
            Log.i("sod",String.valueOf(newqty));
            L=per*fsod/100;
            Log.i("per value",String.valueOf(per));

            tvsod_value.setText(String.valueOf(Math.round(L)));

            sodplus.setText(String.format("%.0f",per)+"%");
            if(Integer.parseInt(String.format("%.0f",per))>=98)
            {
                tvsod_value.setTextColor(Color.WHITE);
                tvsod_value.setBackgroundColor(getResources().getColor(com.myapplication.R.color.LIGHTRED));
            }

            tvsod_value_graph.setHeight( Integer.parseInt(String.format("%.0f",per*multiplyfactor_cal)));


            per=0f;//Manipulation for the vitamin A


            for(int x=0;x<cursor.getCount();x++)
            {
                if(status.equalsIgnoreCase("cat"+j))
                {
                    if(serving_size_qty[x].equals(catqty[j][x]))
                    {

                        if(nf_vitamin_A[x].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(nf_vitamin_A[x]))/Float.valueOf(serving_size_qty[x])+per;
                        }

                        Log.i("cal",nf_vitamin_A[x]);

                    }
                    else
                    {

                        if(nf_vitamin_A[x].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(nf_vitamin_A[x])*newqty[j][x])+per;
                        }

                        Log.i("cal",nf_vitamin_A[x]);
                    }


                }

            }


            Log.i("sod per",String.valueOf(per));
            per=Float.valueOf(String.format("%.1f",per));
            //per=per*newqty;
            Log.i("sod",String.valueOf(newqty));
            //L=per*24;
            Log.i("per value",String.valueOf(per));

            tvvitA_value.setText(String.valueOf(Math.round(per)));
            vitaplus.setText(String.format("%.0f",per)+"%");
            if(Integer.parseInt(String.format("%.0f",per))>=98)
            {
                tvvitA_value.setTextColor(Color.WHITE);
                tvvitA_value.setBackgroundColor(getResources().getColor(com.myapplication.R.color.LIGHTRED));
            }

            tvvitA_value_graph.setHeight( Integer.parseInt(String.format("%.0f",per*multiplyfactor_cal)));




            per=0f;

//Manipulation for the Vit C


            for(int x=0;x<cursor.getCount();x++)
            {
                if(status.equalsIgnoreCase("cat"+j))
                {
                    if(serving_size_qty[x].equals(catqty[j][x]))
                    {

                        if(nf_vitamin_C[x].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(nf_vitamin_C[x]))/Float.valueOf(serving_size_qty[x])+per;
                        }

                        Log.i("cal",nf_vitamin_C[x]);

                    }
                    else
                    {

                        if(nf_vitamin_C[x].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(nf_vitamin_C[x])*newqty[j][x])+per;
                        }

                        Log.i("cal",nf_vitamin_C[x]);
                    }

                }


            }


            Log.i("sod per",String.valueOf(per));
            per=Float.valueOf(String.format("%.1f",per));
            //per=per*newqty;
            Log.i("sod",String.valueOf(newqty));
            //L=per*24;
            Log.i("per value",String.valueOf(per));
            tvvitC_value.setText(String.valueOf(Math.round(per)));
            vitcplus.setText(String.format("%.0f",per)+"%");
            if(Integer.parseInt(String.format("%.0f",per))>=98)
            {
                tvvitC_value.setTextColor(Color.WHITE);
                tvvitC_value.setBackgroundColor(getResources().getColor(com.myapplication.R.color.LIGHTRED));
            }

            tvvitC_value_graph.setHeight( Integer.parseInt(String.format("%.0f",per*multiplyfactor_cal)));

        }
        else
        {
            Clear();
            LinearLayout  layout=new LinearLayout(this.getActivity());
            //layout.setBackgroundResource(R.color.White);

            TextView  tv=new TextView(this.getActivity());
            // set the TextView properties like color, size etc
            tv.setTypeface(roboto_light1);
            tv.setTextSize(15);

            tv.setGravity(Gravity.CENTER_VERTICAL);

            // set the text you want to show in  Toast
            tv.setText("No meal yet addeded to this Categories");

            layout.addView(tv);

            //context is object of Context write "this" if you are an Activity
            // Set The layout as Toast View
            //toast.cancel();
            toast.setView(layout);
            toast.setDuration(Toast.LENGTH_LONG);

            // Position you toast here toast position is 50 dp from bottom you can give any integral value
            toast.setGravity(Gravity.BOTTOM, 0, 150);
            toast.show();

        }



    }


    //Calculating the Total_Meal
    public void Total_Meal()
    {

        try
        {
            Cursor cursor = null;
            cursor=MdbHelper.fetch_allSave();

            mlitem_name=new String[cursor.getCount()];
            mlbrand_name=new String[cursor.getCount()];
            mlserving_size_qty=new String[cursor.getCount()];
            mlserving_size_unit=new String[cursor.getCount()];
            mlserving_size_gram=new String[cursor.getCount()];
            mlnf_calories=new String[cursor.getCount()];
            mlnf_calories_from_fat=new String[cursor.getCount()];
            mltotal_fat=new String[cursor.getCount()];
            mlsaturated_fat=new String[cursor.getCount()];
            mlpoly_sat_fat=new String[cursor.getCount()];
            mlnf_cholesterol=new String[cursor.getCount()];
            mlnf_sodium=new String[cursor.getCount()];
            mltotal_carbohydrates=new String[cursor.getCount()];
            mlnf_proteins=new String[cursor.getCount()];
            mlnf_vitamin_A=new String[cursor.getCount()];
            mlnf_vitamin_C=new String[cursor.getCount()];
            mlnf_calcium=new String[cursor.getCount()];
            mlnf_iron=new String[cursor.getCount()];
            mlnf_daietry_fiber=new String[cursor.getCount()];
            mlnf_sugar=new String[cursor.getCount()];
            String catqty[][]=new String[keys.entrySet().size()][cursor.getCount()];
            String cat[][]=new String[keys.entrySet().size()][cursor.getCount()];
            Log.i("Number of article saved", String.valueOf(cursor.getCount()));
            int l=0;
            if(cursor.getCount()>0)
            {
                if(cursor.moveToFirst())

                { do

                {

                    HashMap<String, String> contact = new HashMap<String, String>();


                    Log.i("value of l",String.valueOf(l));


                    mlitem_name[l]=cursor.getString(cursor.getColumnIndex("pdt_name"));
                    mlbrand_name[l]=cursor.getString(cursor.getColumnIndex("b_name"));
                    mlserving_size_gram[l]=cursor.getString(cursor.getColumnIndex("serving_gram"));
                    mlserving_size_qty[l]=cursor.getString(cursor.getColumnIndex("qty"));
                    mlserving_size_unit[l]=cursor.getString(cursor.getColumnIndex("unit"));
                    mlnf_calories[l]=cursor.getString(cursor.getColumnIndex("calval"));
                    mlnf_calcium[l]=cursor.getString(cursor.getColumnIndex("calciumval"));
                    mltotal_fat[l]=cursor.getString(cursor.getColumnIndex("fatval"));
                    mlsaturated_fat[l]=cursor.getString(cursor.getColumnIndex("satfatval"));
                    mltotal_carbohydrates[l]=cursor.getString(cursor.getColumnIndex("carbsval"));
                    mlnf_cholesterol[l]=cursor.getString(cursor.getColumnIndex("cholestrolval"));
                    mlnf_sodium[l]=cursor.getString(cursor.getColumnIndex("sodval"));
                    mlnf_vitamin_A[l]=cursor.getString(cursor.getColumnIndex("vitA"));
                    mlnf_vitamin_C[l]=cursor.getString(cursor.getColumnIndex("vitC"));
                    mlnf_iron[l]=cursor.getString(cursor.getColumnIndex("ironval"));
                    mlnf_proteins[l]=cursor.getString(cursor.getColumnIndex("proval"));
                    for(int i=0;i<keys.entrySet().size();i++)
                    {
                        for(int l1=0;l1<cursor.getCount();l1++){
                            catqty[i][l]=String.valueOf(Float.valueOf(cursor.getString(cursor.getColumnIndex("cat"+i+"qty")))/Float.valueOf(cursor.getString(cursor.getColumnIndex("qty"))));
                            cat[i][l]=cursor.getString(cursor.getColumnIndex("cat"+i));
                        }
                    }
                    l++;
                }

                while(cursor.moveToNext());
                    cursor.close();
                }
	/*
	* Manipulating the Total_values of the Graph
	*/

                tvmcal_value = (TextView) getView().findViewById(R.id.mlTcal_desc_val);
                tvmcal_value_graph = (TextView) getView().findViewById(R.id.mlTcaloriesview);

                tvmcar_value = (TextView) getView().findViewById(R.id.mlTprot_desc_val);
                tvmcar_value_graph = (TextView) getView().findViewById(R.id.mlTProtienview);

                tvmpro_value = (TextView) getView().findViewById(R.id.mlTcarbs_desc_val);
                tvmpro_value_graph = (TextView) getView().findViewById(R.id.mlTcarbsview);

                tvmsod_value = (TextView) getView().findViewById(R.id.mlTsodium_desc_val);
                tvmsod_value_graph = (TextView) getView().findViewById(R.id.mlTsodiumview);

                tvmfib_value = (TextView) getView().findViewById(R.id.mlTFiber_desc_val);
                tvmfib_value_graph = (TextView) getView().findViewById(R.id.mlTFiberview);

                tvmvitA_value_graph = (TextView) getView().findViewById(R.id.mlvita);
                tvmvitA_value = (TextView) getView().findViewById(R.id.mlvita_desc);


                tvmvitC_value_graph = (TextView) getView().findViewById(R.id.mlvitc);
                tvmvitC_value = (TextView) getView().findViewById(R.id.mlvitc_desc);

                Float totalcal=0f,totalpro=0f,tottalfib=0f,totalsod=0f,totalcarbs=0f,break_qty=0f,cat2_qty=0f,cat3_qty=0f;
                //tvTitem.setText(String.valueOf(cursor.getCount()));
                Float L=0f,per=0f,total_qty[][];
                total_qty=new Float[keys.entrySet().size()][cursor.getCount()];
                Log.i("cursor count", String.valueOf(cursor.getCount()));
                for(int m=0;m<keys.entrySet().size();m++)
                {
                    for(int i=0;i<cursor.getCount();i++)
                    {
                        total_qty[m][i]=0f;
                    }
                }

                for(int m=0;m<keys.entrySet().size();m++){
                    for(int x=0;x<cursor.getCount();x++)
                    {

                        if(!cat[m][x].equals("100"))
                        {
                            total_qty[m][x]=((Float.valueOf(catqty[m][x])));
                            Log.d("Total_fit meal", String.valueOf(total_qty[m][x]));
                        }

                        else
                        {

                        }

                    }
                }


                //calories values
                for(int m=0;m<keys.entrySet().size();m++){
                    for(int x1=0;x1<cursor.getCount();x1++)
                    {
                        if(mlnf_calories[x1].equals("null"))
                        {
                            per=per+0f;

                        }else
                        {
                            if(total_qty[m][x1].equals("100"))
                            {

                            }
                            else{
                                per=(Float.valueOf(mlnf_calories[x1])*total_qty[m][x1])+per;
                            }

                        }

                        Log.i("cal",mlnf_calories[x1]);


                    }
                }
                Log.i("My L",String.valueOf(L));
                per=Float.valueOf(String.format("%.1f",per));
                L=per*fcal/100;
                Log.d("per cal value",String.valueOf(per));
                Log.d("L cal value",String.valueOf(L));
                //Log.d("Total qty",String.valueOf(total_qty));
                tvmcal_value.setText(String.format("%.0f",L));
                if(Integer.parseInt(String.format("%.0f",per))>=98)
                {
                    tvmcal_value.setTextColor(Color.WHITE);
                    tvmcal_value.setBackgroundColor(getResources().getColor(com.myapplication.R.color.LIGHTRED));
                }

                mtcalplus.setText(String.format("%.0f",per)+"%");
                tvmcal_value_graph.setHeight( Integer.parseInt(String.format("%.0f",per*multiplyfactor_cal)));
                per=0f;
                for(int m=0;m<keys.entrySet().size();m++){
                    for(int x1=0;x1<cursor.getCount();x1++)
                    {
                        if(mltotal_carbohydrates[x1].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(mltotal_carbohydrates[x1])*total_qty[m][x1])+per;
                        }
                        Log.i("carbs",mltotal_carbohydrates[x1]);

                    }
                }
                per=Float.valueOf(String.format("%.1f",per));
                L=per*3;
                Log.d("VAlue of NPE Per",String.valueOf(per));
                mtcarbsplus.setText(String.format("%.0f",per)+"%");
                Log.i("per value Total carbs",String.valueOf(per));
                Log.i("per value",String.valueOf(L));
                tvmcar_value.setText(String.format("%.0f",L));
                if(Integer.parseInt(String.format("%.0f",per))>=98)
                {	Log.i("helloo","HOW ARE U BROTHER");
                    Log.i("helloo",String.format("%.0f",per));
                    tvmcar_value.setTextColor(Color.WHITE);
                    tvmcar_value.setBackgroundColor(getResources().getColor(com.myapplication.R.color.LIGHTRED));
                }
                tvmcar_value_graph.setHeight( Integer.parseInt(String.format("%.0f",per*multiplyfactor_cal)));

                per=0f;
                for(int m=0;m<keys.entrySet().size();m++){
                    for(int x1=0;x1<cursor.getCount();x1++)
                    {
                        if(mltotal_fat[x1].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(mltotal_fat[x1])*total_qty[m][x1])+per;
                        }

                        Log.i("fat",mltotal_fat[x1]);
                    }
                }
                per=Float.valueOf(String.format("%.1f",per));
                L=per*ffat/100;
                Log.i("per value",String.valueOf(per));
                tvmfib_value.setText(String.format("%.0f",L));
                if(Integer.parseInt(String.format("%.0f",per))>=98)
                {
                    tvmfib_value.setTextColor(Color.WHITE);
                    tvmfib_value.setBackgroundColor(getResources().getColor(com.myapplication.R.color.LIGHTRED));
                }

                tvmfib_value_graph.setHeight( Integer.parseInt(String.format("%.0f",per*multiplyfactor_cal)));
                mtfibplus.setText(String.format("%.0f",per)+"%");
                per=0f;

                //Now For Proteins
                for(int m=0;m<keys.entrySet().size();m++){
                    for(int x1=0;x1<cursor.getCount();x1++)
                    {
                        if(mlnf_proteins[x1].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(mlnf_proteins[x1])*total_qty[m][x1])+per;
                        }

                        Log.i("pro",mlnf_proteins[x1]);

                    }
                }
                per=Float.valueOf(String.format("%.1f",per));
                L=per*fpro/100;
                Log.i("per value",String.valueOf(per));

                tvmpro_value.setText(String.format("%.0f",L));
                if(Integer.parseInt(String.format("%.0f",per))>=98)
                {
                    tvmpro_value.setTextColor(Color.WHITE);
                    tvmpro_value.setBackgroundColor(getResources().getColor(com.myapplication.R.color.LIGHTRED));
                }

                tvmpro_value_graph.setHeight( Integer.parseInt(String.format("%.0f",per*multiplyfactor_cal)));
                mtproplus.setText(String.format("%.0f",per)+"%");
                per=0f;

                //For the Sodium
                for(int m=0;m<keys.entrySet().size();m++){
                    for(int x1=0;x1<cursor.getCount();x1++)
                    {
                        if(mlnf_sodium[x1].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(mlnf_sodium[x1])*total_qty[m][x1])+per;
                        }

                        Log.i("sod",mlnf_sodium[x1]);
                    }
                }
                per=Float.valueOf(String.format("%.1f",per));
                L=per*fsod/100;
                Log.i("per value",String.valueOf(per));
                mtsodplus.setText(String.format("%.0f",per)+"%");
                tvmsod_value.setText(String.format("%.0f",L));
                if(Integer.parseInt(String.format("%.0f",per))>=98)
                {
                    tvmsod_value.setTextColor(Color.WHITE);
                    tvmsod_value.setBackgroundColor(getResources().getColor(com.myapplication.R.color.LIGHTRED));
                }

                tvmsod_value_graph.setHeight( Integer.parseInt(String.format("%.0f",per*multiplyfactor_cal)));

                per=0f;
                //For the Vitmin A
                for(int m=0;m<keys.entrySet().size();m++){
                    for(int x1=0;x1<cursor.getCount();x1++)
                    {
                        if(mlnf_vitamin_A[x1].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(mlnf_vitamin_A[x1])*total_qty[m][x1])+per;
                        }

                        Log.i("sod",mlnf_vitamin_A[x1]);
                    }
                }
                per=Float.valueOf(String.format("%.1f",per));
                //L=per*24;
                Log.i("per value",String.valueOf(per));

                tvmvitA_value.setText(String.format("%.0f",per));
                if(Integer.parseInt(String.format("%.0f",per))>=98)
                {
                    //tvmvitA_value.setTextColor(Color.WHITE);
                    tvmvitA_value.setBackgroundColor(getResources().getColor(com.myapplication.R.color.LIGHTRED));
                }

                tvmvitA_value_graph.setHeight( Integer.parseInt(String.format("%.0f",per*multiplyfactor_cal)));
                mtvitaplus.setText(String.format("%.0f",per)+"%");
                per=0f;
                //For thr Vitmin C
                for(int m=0;m<keys.entrySet().size();m++){
                    for(int x1=0;x1<cursor.getCount();x1++)
                    {
                        if(mlnf_vitamin_C[x1].equals("null"))
                        {
                            per=per+0f;
                        }else
                        {
                            per=(Float.valueOf(mlnf_vitamin_C[x1])*total_qty[m][x1])+per;
                        }

                        Log.i("sod",mlnf_vitamin_C[x1]);
                    }
                }
                per=Float.valueOf(String.format("%.1f",per));
                //L=per*24;
                Log.i("per value",String.valueOf(per));

                tvmvitC_value.setText(String.format("%.0f",per));
                if(Integer.parseInt(String.format("%.0f",per))>=98)
                {
                    tvmvitC_value.setTextColor(Color.WHITE);
                    tvmvitC_value.setBackgroundColor(getResources().getColor(com.myapplication.R.color.LIGHTRED));
                }

                tvmvitC_value_graph.setHeight( Integer.parseInt(String.format("%.0f",per*multiplyfactor_cal)));
                mtvitcplus.setText(String.format("%.0f",per)+"%");

            }
            else
            {
                Clear();
                LinearLayout  layout=new LinearLayout(this.getActivity());
                //layout.setBackgroundResource(R.color.White);

                TextView  tv=new TextView(this.getActivity());
                // set the TextView properties like color, size etc
                tv.setTypeface(roboto_light1);
                tv.setTextSize(15);

                tv.setGravity(Gravity.CENTER_VERTICAL);

                // set the text you want to show in  Toast
                tv.setText("No meal yet addeded to this Categories");

                layout.addView(tv);

                //context is object of Context write "this" if you are an Activity
                // Set The layout as Toast View
                //toast.cancel();
                toast.setView(layout);
                toast.setDuration(com.Spry.nutritionix.Constants.DURATION);

                // Position you toast here toast position is 50 dp from bottom you can give any integral value
                toast.setGravity(Gravity.BOTTOM, 0, 150);
                toast.show();

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void Delete()
    {	Log.i("Delete called", "UnderProcess");
        Cursor cursor=null;
        cursor=MdbHelper.fetch_allSave();
        String [][] x;
        x=new String[cursor.getCount()][keys.entrySet().size()];
        int i=0;


        if(cursor.getCount()>0)
        {
            if(cursor.moveToFirst())
            {
                do{


                    for(int l=0;l<keys.entrySet().size();l++)
                    {
                        x[i][l]=cursor.getString(cursor.getColumnIndex("cat"+String.valueOf(l)));
                        //Log.i("value of x[][]",x[i][l]);
                    }
                    i++;
                }
                while(cursor.moveToNext());
                cursor.close();
            }
        }
        String catval=" cat";
        String catqty="100";
        String And=" and ";

        String query="";
        int kl=0;
        for(int i1=0;i1<cursor.getCount();i1++)
        {
            query="";
            kl=0;
            for(int k=0;k<keys.entrySet().size();k++)
            {
                kl++;
                if(k==keys.entrySet().size()-1)
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
            if(kl==keys.entrySet().size())
            {
                Log.i("deleting ", "deleting ..."+query);
                Log.i("deleting ", "deleted");

                MdbHelper.DeleteRow(query);
            }



        }

    }
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }
}

