package com.Spry.nutritionix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.Spry.helper.ItemTouchHelperAdapter;
import com.Spry.helper.ItemTouchHelperViewHolder;
import com.Spry.helper.OnStartDragListener;
import com.Spry.helper.SimpleItemTouchHelperCallback;
import com.myapplication.R;
import com.spry.database.DbAdapter;



public class My_Daily_Plans extends ListFragment{

    DbAdapter dbAdapter;
    //DynamicListView lsview;
    ListView lsview;
    TextView txt1;
    private ItemTouchHelper mItemTouchHelper;
    //StableArrayAdapter adapter;
    ProgressDialog pDialog;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    SharedPreferences sharedPref,sharedpos;
    RecyclerListAdapter adapter;
    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    static int count=0;
    static Map<String,?> keys;
    private ArrayList<String> categorylist = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v= inflater.inflate(R.layout.activity_my__daily__plans, container, false);
        setHasOptionsMenu(true);
        return v;

    }
    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        adapter = new RecyclerListAdapter(this.getActivity(),categorylist);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        preparelistdata();
    }
    private void preparelistdata() {
        sharedPref = getContext().getSharedPreferences("Hello", Context.MODE_MULTI_PROCESS);
        sharedpos = getContext().getSharedPreferences("Position", Context.MODE_MULTI_PROCESS);
        keys = sharedPref.getAll();
        dbAdapter = new DbAdapter(this.getContext());
        dbAdapter.open();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            Log.d("map valuessssssss", entry.getKey() + ": " + entry.getValue().toString());
            categorylist.add(entry.getValue().toString());
        }
    }

    public void addItems(String Value) {

        SharedPreferences.Editor   editor = sharedPref.edit();
        editor.putString("cat"+String.valueOf(categorylist.size()),Value);
        editor.apply();
        Log.i("Hello value ",sharedPref.getString("cat"+String.valueOf(categorylist.size()),null));
        categorylist.add(sharedPref.getString("cat"+String.valueOf(listItems.size()),null));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my__daily__plans, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.addnewcategories) {
            CreateNewRow();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void CreateNewRow() {
        // TODO Auto-generated method stub
        ShowCustomDialog();
        //Toast.makeText(My_Daily_Plans.this, "Call Me", Toast.LENGTH_LONG).show();
    }

    private void ShowCustomDialog() {
        // TODO Auto-generated method stub
        final Dialog dialog=new Dialog(My_Daily_Plans.this.getContext());
        dialog.setTitle("Add Categories");
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_my_daily_meals);
        final EditText editText=(EditText)dialog.findViewById(R.id.dialog_edt_my_daily_meal);
        final Button btnok=(Button)dialog.findViewById(R.id.dialog_btn_ok_my_daily_meal);
        final Button btncancel=(Button)dialog.findViewById(R.id.dilaog_btn_cancel_my_dail_meal);

        //@Listener For the btn OK Click

        btnok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                //Creating Alter for the new added column
                SharedPreferences sharedPref;
                sharedPref = getContext().getSharedPreferences("Hello",Context.MODE_MULTI_PROCESS);
                Map<String,?> keys;
                keys=sharedPref.getAll();
                if(keys.entrySet().size()>7)
                {
                    Toast.makeText(getContext(), "You can't Add more categories \n Upgrade Your account", Toast.LENGTH_LONG).show();

                }
                else{
                    if(editText.getText().toString().equalsIgnoreCase(" ")||editText.getText().toString().equalsIgnoreCase(null))
                        Toast.makeText(My_Daily_Plans.this.getContext(), "Category name should not be blank", Toast.LENGTH_LONG).show();
                    else
                        addItems(editText.getText().toString());

                    dbAdapter.AlterAddClomunCat(("cat"+String.valueOf(categorylist.size()-1)));
                    dbAdapter.AlterAddClomunCatQty(("cat"+String.valueOf(categorylist.size()-1)+"qty"));
                }

                dialog.dismiss();
                //  Recall();
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

    }

    class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.MyViewHolder> implements ItemTouchHelperAdapter {

        private List<String> categoryList;
        private OnStartDragListener mDragStartListener;
        Activity activity;
        RecyclerListAdapter adapter;

        public RecyclerListAdapter(OnStartDragListener dragStartListener) {
            mDragStartListener = dragStartListener;


        }

        public RecyclerListAdapter(Activity activity ,List<String> moviesList) {
            this.categoryList = moviesList;
            this.activity = activity;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_my_daily_meal_plan, parent, false);
            MyViewHolder itemViewHolder = new MyViewHolder(itemView);
            return itemViewHolder;

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final int kposition = position;
            SharedPreferences sharedPrefText;
            sharedPrefText = activity.getSharedPreferences("Hello", Context.MODE_PRIVATE);
            holder.category.setText(sharedPrefText.getString("cat" + String.valueOf(kposition), "Deafult"));
            holder.icon.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    try {
                        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                            mDragStartListener.onStartDrag(holder);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });
            holder.delete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    SharedPreferences sharedPref;
                    sharedPref = activity.getSharedPreferences("Hello", Context.MODE_PRIVATE);
                    Map<String, ?> keys;
                    keys = sharedPref.getAll();

                    if (keys.entrySet().size() < 2) {
                        Toast.makeText(activity, "Atleast one Categories required", Toast.LENGTH_LONG).show();
                    } else {
                        if (sharedPref.contains("cat" + String.valueOf(kposition))) {
                            new UpdateData(kposition, keys.entrySet().size()).execute();
                            categorylist.remove(kposition);
                            notifyDataSetChanged();
                            Toast.makeText(activity, "deleted" + String.valueOf(kposition), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            });

            holder.edit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(activity);
                    dialog.setTitle("Edit Category");
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setContentView(R.layout.dialog_my_daily_meals);
                    final EditText editText = (EditText) dialog.findViewById(R.id.dialog_edt_my_daily_meal);
                    SharedPreferences sharedPrefs;

                    sharedPrefs = activity.getSharedPreferences("Hello", Context.MODE_PRIVATE);
                    Map<String, ?> keys;
                    final Map<String, ?> ss;
                    keys = sharedPrefs.getAll();
                    ss = sharedPrefs.getAll();
                    //String 	vals=(String) keys.get("cat"+String.valueOf(keys.entrySet().size()-kposition-1));
                    String vals = sharedPrefs.getString("cat" + String.valueOf(kposition), "Deafult");
                    Log.d("Value", vals);
                    editText.setText(vals);
                    final Button btnok = (Button) dialog.findViewById(R.id.dialog_btn_ok_my_daily_meal);
                    final Button btncancel = (Button) dialog.findViewById(R.id.dilaog_btn_cancel_my_dail_meal);

                    //@Listener For the btn OK Click

                    btnok.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            SharedPreferences sharedPrefs;
                            sharedPrefs = activity.getSharedPreferences("Hello", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPrefs.edit();
                            editor.putString("cat" + String.valueOf(kposition), editText.getText().toString());
                            editor.apply();
                            categorylist.set(kposition, editText.getText().toString());
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


                    Toast.makeText(activity, "edited" + String.valueOf(kposition), Toast.LENGTH_LONG).show();
                }
            });





        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
            public TextView category, icon;
            public ImageView delete,edit;

            public MyViewHolder(View view) {
                super(view);

                category = (TextView) view.findViewById(R.id.tvname_categories);
                icon = (TextView) view.findViewById(R.id.icon_entry);
                delete = (ImageView) view.findViewById(R.id.imgdele_my_daily);
                edit=(ImageView)view.findViewById(R.id.imgedit_my_daily);

            }

            @Override
            public void onItemSelected() {
                itemView.setBackgroundColor(Color.LTGRAY);
            }

            @Override
            public void onItemClear() {
                itemView.setBackgroundColor(0);
            }
        }

        @Override
        public int getItemCount() {
            return categoryList.size();
        }

        @Override
        public void onItemDismiss(int position) {
            categoryList.remove(position);
            notifyItemRemoved(position);
        }

        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            Collections.swap(categoryList, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
            String temp = categorylist.get(fromPosition);
            System.out.println("one"+temp);
            String temp2 = categoryList.get(toPosition);
            System.out.println("two"+temp2);
            SharedPreferences sharedprefrence = activity.getSharedPreferences("Hello", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedprefrence.edit();
            editor.putString("cat" + String.valueOf(toPosition), temp2);
            editor.putString("cat" + String.valueOf(fromPosition), temp);
            editor.apply();
            return true;
        }


    }

    public class UpdateData extends AsyncTask<String,String, String>
    {
        int val=0;
        int size=0;
        public UpdateData(int val,int size) {
            // TODO Auto-generated constructor stub
            this.val=val;
            this.size=size;
            Log.d("Value to be deleted ",String.valueOf(val));
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            pDialog = new ProgressDialog(My_Daily_Plans.this.getActivity(), AlertDialog.THEME_HOLO_DARK);
            pDialog.setMessage("Managing Preferences...");
            pDialog.setIndeterminate(true);
            pDialog.setIndeterminateDrawable(My_Daily_Plans.this.getResources().getDrawable(R.drawable.styeprogress));
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
                editor.apply();
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
                editor2.apply();
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
                editor3.apply();
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

            }
           /* if(!pDialog.isShowing()){
                My_Daily_Plans.this.getActivity().finish();
                My_Daily_Plans.this.startActivity(My_Daily_Plans.this.getActivity().getIntent());
            }*/
        }
    }
    @Override
    public void onPause() {
        super.onPause();

    }

}
