package com.kwabenaberko.cointrack;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */
@Database(entities = {Coin.class}, version = 1, exportSchema = false)
public abstract class CoinTrackDatabase extends RoomDatabase {
    public abstract CoinTrackDao mCoinTrackDao();
}
