package com.kwabenaberko.cointrack.di.modules;

import com.kwabenaberko.cointrack.data.network.CoinTrackApiService;
import com.kwabenaberko.cointrack.data.network.CoinTrackRemoteDataSource;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kwabena Berko on 6/18/2018.
 */

@Module(includes = {AppModule.class})
public class NetModule {

    private String baseUrl;
    public NetModule(String baseUrl){
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }


    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(HttpLoggingInterceptor httpLoggingInterceptor){

        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

    }

    @Provides
    @Singleton
    CoinTrackApiService provideApiService(Retrofit retrofit){
        return retrofit.create(CoinTrackApiService.class);
    }

    @Provides
    @Singleton
    CoinTrackRemoteDataSource provideCoinTrackRemoteDataSource(CoinTrackApiService coinTrackApiService){
        return new CoinTrackRemoteDataSource(coinTrackApiService);
    }
}