package com.apps.toletbd.tolet.language;

import android.app.Application;
import android.content.Context;

public class Home extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

}
