package com.samboy.contactproject.app;

import android.app.Application;

import com.samboy.roto.Roto;

/**
 * Created by Hari Group Unity on 02-01-2018.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Roto.init(this);
    }
}
