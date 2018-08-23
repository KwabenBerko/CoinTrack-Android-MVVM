package com.kwabenaberko.cointrack.data.network;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kwabenaberko.cointrack.App;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

public class CoinTrackIntentService extends IntentService {

    private static final String COIN_SERVICE_TAG = CoinTrackIntentService.class.getSimpleName();

    public CoinTrackIntentService() {
        super(COIN_SERVICE_TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((App) getApplication()).getAppComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(COIN_SERVICE_TAG, "Socket Service Started.");
    }
}
