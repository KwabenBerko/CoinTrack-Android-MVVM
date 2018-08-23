package com.kwabenaberko.cointrack.data.network;

import com.kwabenaberko.cointrack.models.Coin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

public interface CoinTrackApiService {
    @GET("/front")
    Call<List<Coin>> getFrontPageCoinData();
}
