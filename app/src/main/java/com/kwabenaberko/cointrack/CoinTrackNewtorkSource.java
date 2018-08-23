package com.kwabenaberko.cointrack;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

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


public class CoinTrackNewtorkSource {

    private static final String COIN_NETWORK_SOURCE_TAG = CoinTrackNewtorkSource.class.getSimpleName();
    private MutableLiveData<List<Coin>> fetchedCoinsMutableLiveData;
    private MutableLiveData<Coin> updatedCoinMutableLiveData;
    private ApiService mApiService;

    public CoinTrackNewtorkSource(ApiService apiService){
        mApiService = apiService;
        updatedCoinMutableLiveData = new MutableLiveData<Coin>();
        fetchedCoinsMutableLiveData = new MutableLiveData<List<Coin>>();
    }

    MutableLiveData<List<Coin>> fetchCoinData(){
        mApiService.getFrontPageCoinData().enqueue(new Callback<List<Coin>>() {
            @Override
            public void onResponse(Call<List<Coin>> call, Response<List<Coin>> response) {
                if(response.isSuccessful()){
                    Log.d(COIN_NETWORK_SOURCE_TAG, "Data successfully fetched.");
                    fetchedCoinsMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Coin>> call, Throwable t) {

            }
        });

        return fetchedCoinsMutableLiveData;
    }


   void startCoinRealTimeUpdates(){
        try {
            Socket socket = IO.socket("https://coincap.io");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(COIN_NETWORK_SOURCE_TAG, "Socket Connected.");
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(COIN_NETWORK_SOURCE_TAG, "Socket Disconnected.");
                }
            }).on("trades", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(COIN_NETWORK_SOURCE_TAG, "Trade.");
                    Log.d(COIN_NETWORK_SOURCE_TAG, String.valueOf(args[0]));
                }
            });

            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
