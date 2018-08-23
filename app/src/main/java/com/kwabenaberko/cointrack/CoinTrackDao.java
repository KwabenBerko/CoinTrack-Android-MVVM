package com.kwabenaberko.cointrack;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Kwabena Berko on 8/22/2018.
 */

@Dao
public interface CoinTrackDao {

    @Query("SELECT * FROM coins")
    LiveData<List<Coin>> getAll();

    @Insert
    void insertAll(List<Coin> coins);

    @Query("Delete FROM coins")
    void deleteAll();

}
