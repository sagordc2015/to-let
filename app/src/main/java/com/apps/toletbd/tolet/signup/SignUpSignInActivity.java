package com.apps.toletbd.tolet.signup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apps.toletbd.tolet.HomeActivity;
import com.apps.toletbd.tolet.R;
import com.apps.toletbd.tolet.language.LocaleHelper;
import com.apps.toletbd.tolet.signin.SignInActivity;

public class SignUpSignInActivity extends AppCompatActivity {

    private Button btn1, btn2, btn3;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_sign_in);

        btn1 = (Button) findViewById(R.id.sign_up);
        btn1.setOnClickListener(new ActionHandler());
        btn2 = (Button) findViewById(R.id.sign_in);
        btn2.setOnClickListener(new ActionHandler());
        btn3 = (Button) findViewById(R.id.close_log_reg);
        btn3.setOnClickListener(new ActionHandler());

        //===============================================| Getting SharedPreferences
        preferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn",false);

        if (isLoggedIn) {
            if(haveNetwork()) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            } else {
                alertDialog("Network connection is not available");
            }
        }
    }

    //===============================================| Language Change
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    //===============================================| Check Internet Connection
    private boolean haveNetwork() {
        boolean have_Wifi = false;
        boolean have_MobileData = false;

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] infos = manager.getAllNetworkInfo();
        for (NetworkInfo info : infos) {
            if (info.getTypeName().equalsIgnoreCase("WIFI")) {
                if (info.isConnected()) {
                    have_Wifi = true;
                }
            }
            if (info.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (info.isConnected()) {
                    have_MobileData = true;
                }
            }
        }
        return have_Wifi | have_MobileData;
    }

    //====================================================| Alert Message
    public void alertDialog(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private class ActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.sign_up) {
                if(haveNetwork()) {
                    Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(intent);
                } else {
                    alertDialog("Network connection is not available");
                }
            }
            if (v.getId() == R.id.sign_in) {
                if(haveNetwork()) {
                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(intent);
                } else {
                    alertDialog("Network connection is not available");
                }
            }
            if (v.getId() == R.id.close_log_reg) {
                if(haveNetwork()) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    alertDialog("Network connection is not available");
                }
            }
        }
    }
}
