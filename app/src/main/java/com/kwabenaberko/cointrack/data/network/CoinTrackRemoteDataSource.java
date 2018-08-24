package com.kwabenaberko.cointrack.data.network;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.Gson;
import com.kwabenaberko.cointrack.models.Coin;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kwabena Berko on 8/23/2018.
 */

public class CoinTrackRemoteDataSource {
    public static final String REMOTE_DATA_SOURCE_TAG = CoinTrackRemoteDataSource.class.getSimpleName();

    private MutableLiveData<List<Coin>> coinListMutableLiveData;
    private MutableLiveData<Coin> updatedCoinMutableLiveData;
    private CoinTrackApiService mCoinTrackApiService;

    public CoinTrackRemoteDataSource(CoinTrackApiService coinTrackApiService){
        mCoinTrackApiService = coinTrackApiService;

        coinListMutableLiveData = new MutableLiveData<>();
        updatedCoinMutableLiveData = new MutableLiveData<>();
    }


    public MutableLiveData<List<Coin>> getCoinListData(){
        return coinListMutableLiveData;
    }

    public MutableLiveData<Coin> getUpdatedCoinData(){
        return updatedCoinMutableLiveData;
    }


    public void fetchCoinData(){
        mCoinTrackApiService.getFrontPageCoinData().enqueue(new Callback<List<Coin>>() {
            @Override
            public void onResponse(Call<List<Coin>> call, final Response<List<Coin>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        coinListMutableLiveData.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Coin>> call, Throwable t) {

            }
        });
    }

    public void getCoinRealTimeUpdates(){
        try {
            IO.Options options = new IO.Options();
            options.reconnection = true;
            options.reconnectionAttempts = 9999;
            options.forceNew = true;
            Socket socket = IO.socket("https://coincap.io", options);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(REMOTE_DATA_SOURCE_TAG, "Socket Connected.");
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(REMOTE_DATA_SOURCE_TAG, "Socket Disconnected.");
                }
            }).on("trades", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        Log.d(REMOTE_DATA_SOURCE_TAG, "Trade Data Received.");
                        Log.v(REMOTE_DATA_SOURCE_TAG, "Socket Thread: " + Thread.currentThread().getId());
                        Log.d(REMOTE_DATA_SOURCE_TAG, String.valueOf(args[0]));
                        JSONObject data = (JSONObject) args[0];
                        String coinData = data.getJSONObject("message").getJSONObject("msg").toString();
                        Log.d(REMOTE_DATA_SOURCE_TAG, coinData);
                        Coin coin = new Gson().fromJson(coinData, Coin.class);
                        updatedCoinMutableLiveData.postValue(coin);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
