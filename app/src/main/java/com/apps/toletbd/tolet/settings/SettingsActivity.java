package com.apps.toletbd.tolet.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apps.toletbd.tolet.R;
import com.apps.toletbd.tolet.language.LocaleHelper;

public class SettingsActivity extends AppCompatActivity {

    private Button bnBtn, enBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //==========================================| Change action bar title
        ActionBar bar = getSupportActionBar();
        bar.setTitle(getResources().getString(R.string.settings_title));

        //==========================================| Language change button
        bnBtn = (Button) findViewById(R.id.bangla_btn);
        bnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = LocaleHelper.setLocale(SettingsActivity.this, "bn");
                Resources resources = context.getResources();
                reload();
            }
        });
        enBtn = (Button) findViewById(R.id.english_btn);
        enBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = LocaleHelper.setLocale(SettingsActivity.this, "en");
                Resources resources = context.getResources();
                reload();
            }
        });
    }

    //===============================================| Language Change
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    //===============================================| For Activity Starting and Closing
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //===============================================| Restart Activity
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}
