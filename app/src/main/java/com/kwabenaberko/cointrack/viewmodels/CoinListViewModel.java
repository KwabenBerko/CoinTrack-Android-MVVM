package com.kwabenaberko.cointrack.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.kwabenaberko.cointrack.models.Coin;
import com.kwabenaberko.cointrack.repository.CoinTrackRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

public class CoinListViewModel extends ViewModel {
    private CoinTrackRepository mCoinTrackRepository;
    private LiveData<List<Coin>> coinListLiveData;

    @Inject
    public CoinListViewModel(CoinTrackRepository coinTrackRepository){
        mCoinTrackRepository = coinTrackRepository;
        coinListLiveData = mCoinTrackRepository.getCoinData();
        refresh();
    }

    public LiveData<List<Coin>> getCoinListLiveData(){
        return coinListLiveData;
    }

    public void refresh(){
        mCoinTrackRepository.refreshCoinListData();
    }
}
