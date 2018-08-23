package com.kwabenaberko.cointrack.repository.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.kwabenaberko.cointrack.models.Coin;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */
@Database(entities = {Coin.class}, version = 1, exportSchema = false)
public abstract class CoinTrackDatabase extends RoomDatabase {
    public abstract CoinTrackDao mCoinTrackDao();
}
