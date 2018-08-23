package com.kwabenaberko.cointrack.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.kwabenaberko.cointrack.CoinTrackDao;
import com.kwabenaberko.cointrack.CoinTrackDatabase;
import com.kwabenaberko.cointrack.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

@Module(includes = {AppModule.class})
public class DatabaseModule {

    @Provides
    @Singleton
    CoinTrackDatabase provideCoinTrackDatabase(Application application){
        return Room.databaseBuilder(
                application.getApplicationContext(),
                CoinTrackDatabase.class,
                application.getApplicationContext().getString(R.string.app_name))
                .build();
    }

    @Provides
    @Singleton
    CoinTrackDao provideCoinTrackDao(CoinTrackDatabase coinTrackDatabase){
        return coinTrackDatabase.mCoinTrackDao();
    }
}
