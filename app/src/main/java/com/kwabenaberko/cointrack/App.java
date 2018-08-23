package com.kwabenaberko.cointrack;

import android.app.Application;

import com.kwabenaberko.cointrack.di.AppComponent;
import com.kwabenaberko.cointrack.di.DaggerAppComponent;
import com.kwabenaberko.cointrack.di.modules.AppModule;
import com.kwabenaberko.cointrack.di.modules.NetModule;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

public class App extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("http://coincap.io"))
                .build();
    }

    public AppComponent getAppComponent(){
        return mAppComponent;
    }
}
