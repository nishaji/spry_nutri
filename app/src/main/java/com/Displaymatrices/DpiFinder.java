package com.Displaymatrices;

import android.content.Context;

/**
 * Created by sprydev5 on 25/6/15.
 */
public class DpiFinder {
    Context context;
    //Constructor for the Getting the refrence
   public DpiFinder(Context context){
        this.context=context;
    }

    public String DisplyMat(){
        return String.valueOf(context.getResources().getDisplayMetrics().density);
    }
}
