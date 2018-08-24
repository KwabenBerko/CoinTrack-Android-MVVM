package com.kwabenaberko.cointrack.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kwabenaberko.cointrack.data.database.CoinTrackDao;
import com.kwabenaberko.cointrack.data.network.CoinTrackIntentService;
import com.kwabenaberko.cointrack.data.network.CoinTrackRemoteDataSource;
import com.kwabenaberko.cointrack.models.Coin;

import java.util.List;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

public class CoinTrackRepository {
    private static final String COIN_REPO_TAG = CoinTrackRepository.class.getSimpleName();

    private Application mApplication;
    private CoinTrackDao mCoinTrackDao;
    private CoinTrackRemoteDataSource mCoinTrackRemoteDataSource;
    private boolean socketServiceInitialized = false;

    public CoinTrackRepository(Application application, CoinTrackDao coinTrackDao, CoinTrackRemoteDataSource coinTrackRemoteDataSource){
        mApplication = application;
        mCoinTrackRemoteDataSource = coinTrackRemoteDataSource;
        mCoinTrackDao = coinTrackDao;

        mCoinTrackRemoteDataSource.getCoinListData().observeForever(new Observer<List<Coin>>() {
            @Override
            public void onChanged(@Nullable final List<Coin> coins) {
                if(coins != null){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mCoinTrackDao.deleteAll();
                            mCoinTrackDao.insertAll(coins.subList(0, 20));
                            Log.d(COIN_REPO_TAG, "Data Changed.");
                        }
                    }).start();
                }
            }
        });

        mCoinTrackRemoteDataSource.getUpdatedCoinData().observeForever(new Observer<Coin>() {
            @Override
            public void onChanged(@Nullable final Coin coin) {
                if(coin != null){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.v(COIN_REPO_TAG, "Received Updated Coin Data Via Websockets.");
                            Log.v(COIN_REPO_TAG, "Repo Thread: " + Thread.currentThread().getId());
                            Coin existingCoin = mCoinTrackDao.findByShortName(coin.getShortName());
                            if(existingCoin != null){
                                existingCoin.setCap24hrChange(coin.getCap24hrChange());
                                existingCoin.setLongName(coin.getLongName());
                                existingCoin.setShortName(coin.getShortName());
                                existingCoin.setPerc(coin.getPerc());
                                existingCoin.setPrice(coin.getPrice());
                                mCoinTrackDao.updateCoin(existingCoin);
                            }
                        }
                    }).start();
                }
            }
        });

    }

    public void refreshCoinListData(){
        mCoinTrackRemoteDataSource.fetchCoinData();
        if(!socketServiceInitialized){
            mApplication.startService(new Intent(mApplication.getApplicationContext(), CoinTrackIntentService.class));
            socketServiceInitialized = true;
        }
    }


    public LiveData<List<Coin>> getCoinData(){
        return mCoinTrackDao.getAll();
    }


}
