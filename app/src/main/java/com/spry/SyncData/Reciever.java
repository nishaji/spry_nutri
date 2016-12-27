package com.spry.SyncData;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by sprydev5 on 9/6/15.
 */
public class Reciever extends BroadcastReceiver {
    Context context;

    void Reciever(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int resultCode = bundle.getInt("result");
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(context,
                        "Download complete. Download URI: " + " HEllo Testing the World of possibility",
                        Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context, "Download failed",
                        Toast.LENGTH_LONG).show();

            }
        }

    }
}
