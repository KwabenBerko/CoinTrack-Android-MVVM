package com.kwabenaberko.cointrack;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

public class CoinTrackRepository {
    private static final String COIN_REPO_TAG = CoinTrackRepository.class.getSimpleName();

    private CoinTrackDao mCoinTrackDao;
    private ApiService mApiService;

    public CoinTrackRepository(CoinTrackDao coinTrackDao, ApiService apiService){
        mApiService = apiService;
        mCoinTrackDao = coinTrackDao;

        refreshCoinListData();

    }

    public void refreshCoinListData(){
        mApiService.getFrontPageCoinData().enqueue(new Callback<List<Coin>>() {
            @Override
            public void onResponse(Call<List<Coin>> call, final Response<List<Coin>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mCoinTrackDao.deleteAll();
                                mCoinTrackDao.insertAll(response.body().subList(0, 10));
                                Log.d(COIN_REPO_TAG, "Data Changed.");
                            }
                        }).start();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Coin>> call, Throwable t) {

            }
        });
    }

    public LiveData<List<Coin>> getCoinData(){
        return mCoinTrackDao.getAll();
    }


}
