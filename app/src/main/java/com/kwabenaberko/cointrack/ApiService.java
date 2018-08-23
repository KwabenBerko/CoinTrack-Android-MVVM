package com.kwabenaberko.cointrack;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

public interface ApiService {
    @GET("/front")
    Call<List<Coin>> getFrontPageCoinData();
}
