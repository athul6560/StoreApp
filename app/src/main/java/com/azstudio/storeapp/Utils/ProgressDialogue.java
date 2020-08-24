package com.azstudio.storeapp.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ProgressDialogue {
    private ProgressDialog dialog;

    public ProgressDialogue(Context context) {
        dialog=new ProgressDialog(context);
    }

    public void show() {

        dialog.setTitle("Loading...");
        dialog.show();
    }

    public void dismiss() {


        dialog.dismiss();
    }
    public static boolean isNetworkAvilable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet

                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    // Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    // Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                }
                return true;
            } else {
                // not connected to the internet
                return false;
            }


        } catch (Exception e) {
            System.out.println("NetworkError" + e);
            return true;
        }

    }
}
