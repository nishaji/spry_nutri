package com.Spry.nutritionix;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Spry.helper.ItemTouchHelperAdapter;
import com.Spry.helper.ItemTouchHelperViewHolder;
import com.Spry.helper.OnStartDragListener;
import com.myapplication.R;
import com.spry.database.DbAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by sprydev5 on 1/7/16.
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.MyViewHolder> implements ItemTouchHelperAdapter {

    private List<Category> categoryList;
    private OnStartDragListener mDragStartListener;
    Activity activity;
    RecyclerListAdapter adapter;
    public RecyclerListAdapter(Activity activity, OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
        this.activity=activity;

    }

    public RecyclerListAdapter(List<Category> moviesList) {
        this.categoryList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_my_daily_meal_plan, parent, false);
        MyViewHolder itemViewHolder = new MyViewHolder(itemView);
        return itemViewHolder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Category movie = categoryList.get(position);
        holder.category.setText(movie.getCategory());
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

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        public TextView category, icon;
        public ImageView delete;

        public MyViewHolder(View view) {
            super(view);
            category = (TextView) view.findViewById(R.id.tvname_categories);
            icon = (TextView) view.findViewById(R.id.icon_entry);
            delete = (ImageView) view.findViewById(R.id.imgdele_my_daily);

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
        System.out.println("moving");
        Collections.swap(categoryList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public class UpdateData extends AsyncTask<String, String, String> {

        int val = 0;
        int size = 0;

        public UpdateData(int val, int size) {
            // TODO Auto-generated constructor stub
            this.val = val;
            this.size = size;
            Log.d("Value to be deleted ", String.valueOf(val));
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}

