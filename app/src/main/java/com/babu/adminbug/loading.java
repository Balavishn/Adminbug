package com.babu.adminbug;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class loading {
    Activity activity;
    AlertDialog alertDialog;

    public loading(Activity activity) {
        this.activity = activity;
    }

    public void startdio(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_progress,null));
        builder.setCancelable(true);
        alertDialog=builder.create();
        alertDialog.show();
    }

    public void dismiss(){
        alertDialog.dismiss();
    }
}
