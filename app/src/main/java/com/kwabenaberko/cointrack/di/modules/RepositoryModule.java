package com.kwabenaberko.cointrack.di.modules;

import com.kwabenaberko.cointrack.ApiService;
import com.kwabenaberko.cointrack.CoinTrackDao;
import com.kwabenaberko.cointrack.CoinTrackNewtorkSource;
import com.kwabenaberko.cointrack.CoinTrackRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

@Module(includes = {NetModule.class, DatabaseModule.class})
public class RepositoryModule {
    @Provides
    @Singleton
    CoinTrackRepository provideCoinTrackRepository(CoinTrackDao coinTrackDao, ApiService apiService){
        return new CoinTrackRepository(coinTrackDao, apiService);
    }
}
