package com.kwabenaberko.cointrack.di;

import com.kwabenaberko.cointrack.data.network.CoinTrackIntentService;
import com.kwabenaberko.cointrack.views.CoinListActivity;
import com.kwabenaberko.cointrack.di.modules.AppModule;
import com.kwabenaberko.cointrack.di.modules.DatabaseModule;
import com.kwabenaberko.cointrack.di.modules.NetModule;
import com.kwabenaberko.cointrack.di.modules.RepositoryModule;
import com.kwabenaberko.cointrack.di.modules.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        NetModule.class,
        ViewModelModule.class,
        RepositoryModule.class,
        DatabaseModule.class,
})

public interface AppComponent {
    void inject(CoinTrackIntentService coinTrackIntentService);
    void inject(CoinListActivity coinListActivity);
}
