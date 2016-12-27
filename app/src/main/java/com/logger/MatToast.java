package com.logger;

import android.content.Context;
import android.widget.Toast;

import com.myapplication.R;


/**
 * Created by sprydev5 on 16/6/15.
 */
public class MatToast {

    public  void ToastMessageSelection(Context context,String b_name,String t_name){


        String message=t_name+ R.string.from
                +b_name+ R.string.added;
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    public void ToastUIMessage(Context context,String textwantstotest){

        Toast.makeText(context,textwantstotest,Toast.LENGTH_LONG).show();
    }


}
