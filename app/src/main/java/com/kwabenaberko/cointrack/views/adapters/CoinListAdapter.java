package com.kwabenaberko.cointrack.views.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kwabenaberko.cointrack.R;
import com.kwabenaberko.cointrack.models.Coin;
import com.kwabenaberko.cointrack.utils.Helpers;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kwabena Berko on 2/3/2018.
 */

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.CustomViewHolder> {
    private Context mContext;
    private List<Coin> mCoins;

    public CoinListAdapter(Context context, List<Coin> coins){
        mContext = context;
        mCoins = coins;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.coin_list_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Coin coin = mCoins.get(position);
        holder.coinLongName.setText(coin.getLongName());
        holder.coinShortName.setText(coin.getShortName());
        holder.coin24HourChange.setText(
                String.format(
                        mContext.getString(R.string.percentage),
                        String.valueOf(coin.getPerc()))
        );
        holder.coin24HourChange.setTextColor(ContextCompat.getColor(mContext, R.color.depreciate));
        holder.coinPrice.setText(
                String.format(
                        mContext.getString(R.string.priceInDollars),
                        Helpers.round(coin.getPrice())
                )
        );
        holder.coinPrice.setTextColor(ContextCompat.getColor(mContext, R.color.appreciate));
        Glide.with(mContext)
                .load("https://raw.githubusercontent.com/KwabenBerko/cryptocurrency-icons/master/128/color/" +
                coin.getShortName().toLowerCase() + ".png")
                .into(holder.coinImage);
    }

    @Override
    public int getItemCount() {
        return mCoins.size();
    }

    public void refill(List<Coin> coins){
        mCoins = coins;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.coinLongName) TextView coinLongName;
        @BindView(R.id.coinShortName) TextView coinShortName;
        @BindView(R.id.coin24HourChange) TextView coin24HourChange;
        @BindView(R.id.coinImage) ImageView coinImage;
        @BindView(R.id.coinPrice) TextView coinPrice;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
