package com.kwabenaberko.cointrack;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

public class MainActivityViewModel extends ViewModel {
    private CoinTrackRepository mCoinTrackRepository;
    private LiveData<List<Coin>> coinListLiveData;

    @Inject
    public MainActivityViewModel(CoinTrackRepository coinTrackRepository){
        mCoinTrackRepository = coinTrackRepository;
        coinListLiveData = mCoinTrackRepository.getCoinData();
    }

    public LiveData<List<Coin>> getCoinListLiveData(){
        return coinListLiveData;
    }

    public void refresh(){
        mCoinTrackRepository.refreshCoinListData();
    }
}
