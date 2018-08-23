package com.kwabenaberko.cointrack.di.modules;

import android.app.Application;

import com.kwabenaberko.cointrack.data.network.CoinTrackApiService;
import com.kwabenaberko.cointrack.data.database.CoinTrackDao;
import com.kwabenaberko.cointrack.data.CoinTrackRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

@Module(includes = {AppModule.class, NetModule.class, DatabaseModule.class})
public class RepositoryModule {
    @Provides
    @Singleton
    CoinTrackRepository provideCoinTrackRepository(Application application, CoinTrackDao coinTrackDao, CoinTrackApiService coinTrackApiService){
        return new CoinTrackRepository(application, coinTrackDao, coinTrackApiService);
    }
}
