package com.kwabenaberko.cointrack.di.modules;

import android.arch.lifecycle.ViewModel;

import com.kwabenaberko.cointrack.CoinListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CoinListViewModel.class)
    abstract ViewModel mainActivityViewModel(CoinListViewModel coinListViewModel);


}
