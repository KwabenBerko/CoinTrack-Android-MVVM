package com.kwabenaberko.cointrack.views;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.kwabenaberko.cointrack.models.Coin;

import java.util.List;

/**
 * Created by Kwabena Berko on 8/24/2018.
 */

public class CoinListDiffCallback extends DiffUtil.Callback {

    private List<Coin> mOldCoinList;
    private List<Coin> mNewCoinList;

    public CoinListDiffCallback(List<Coin> oldCoinList, List<Coin> newCoinList){
        this.mOldCoinList = oldCoinList;
        this.mNewCoinList = newCoinList;
    }

    @Override
    public int getOldListSize() {
        return mOldCoinList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewCoinList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldCoinList.get(oldItemPosition).getId() == mNewCoinList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldCoinList.get(oldItemPosition).compareTo(mNewCoinList.get(newItemPosition)) == 0;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
