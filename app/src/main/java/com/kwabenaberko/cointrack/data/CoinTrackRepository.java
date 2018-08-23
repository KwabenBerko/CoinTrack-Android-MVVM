package com.kwabenaberko.cointrack.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.kwabenaberko.cointrack.data.database.CoinTrackDao;
import com.kwabenaberko.cointrack.data.network.CoinTrackApiService;
import com.kwabenaberko.cointrack.models.Coin;

import java.net.URISyntaxException;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

public class CoinTrackRepository {
    private static final String COIN_REPO_TAG = CoinTrackRepository.class.getSimpleName();

    private Application mApplication;
    private CoinTrackDao mCoinTrackDao;
    private CoinTrackApiService mCoinTrackApiService;

    public CoinTrackRepository(Application application, CoinTrackDao coinTrackDao, CoinTrackApiService coinTrackApiService){
        mApplication = application;
        mCoinTrackApiService = coinTrackApiService;
        mCoinTrackDao = coinTrackDao;

    }

    public void refreshCoinListData(){
        mCoinTrackApiService.getFrontPageCoinData().enqueue(new Callback<List<Coin>>() {
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

    private void startCoinRealTimeUpdates(){
        try {
            Socket socket = IO.socket("https://coincap.io");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(COIN_REPO_TAG, "Socket Connected.");
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(COIN_REPO_TAG, "Socket Disconnected.");
                }
            }).on("trades", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(COIN_REPO_TAG, "Trade.");
                    Log.d(COIN_REPO_TAG, String.valueOf(args[0]));
                }
            });

            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public LiveData<List<Coin>> getCoinData(){
        return mCoinTrackDao.getAll();
    }


}
