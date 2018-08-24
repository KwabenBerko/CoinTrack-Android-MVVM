package com.kwabenaberko.cointrack.views.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kwabenaberko.cointrack.R;
import com.kwabenaberko.cointrack.models.Coin;
import com.kwabenaberko.cointrack.utils.Helpers;
import com.kwabenaberko.cointrack.views.CoinListDiffCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kwabena Berko on 2/3/2018.
 */

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.CustomViewHolder> {
    private Context mContext;
    private List<Coin> mCoins;

    private boolean isFirstLoad;

    public CoinListAdapter(Context context, List<Coin> coins){
        mContext = context;
        mCoins = coins;
        isFirstLoad = true;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.coin_list_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        final Coin coin = mCoins.get(position);
        holder.coinLongName.setText(coin.getLongName());
        holder.coinShortName.setText(coin.getShortName());
        holder.coin24HourChange.setText(
                String.format(
                        mContext.getString(R.string.percentage),
                        String.valueOf(coin.getPerc()))
        );
        if(coin.getPerc() < 0){
            holder.coin24HourChange.setTextColor(ContextCompat.getColor(mContext, R.color.danger));
            holder.coinArrow.setImageResource(R.drawable.arrow_down);
            holder.coinArrow.setColorFilter(ContextCompat.getColor(mContext, R.color.danger));
        }
        else{
            holder.coin24HourChange.setTextColor(ContextCompat.getColor(mContext, R.color.success));
            holder.coinArrow.setImageResource(R.drawable.arrow_up);
            holder.coinArrow.setColorFilter(ContextCompat.getColor(mContext, R.color.success));
        }

        holder.coinPrice.setText(
                String.format(
                        mContext.getString(R.string.priceInDollars),
                        Helpers.round(coin.getPrice())
                )
        );

        if(!isFirstLoad){
            if(coin.getPrice() < calculateAmount24HoursAgo(coin.getPrice(), coin.getPerc())){
                Helpers.playListItemTransitionAnimation(mContext, R.color.transition_danger, holder.coinListItemLayout);
                Helpers.playTextTransitionAnimation(mContext, R.color.danger, holder.coinPrice);
            }
            else if(coin.getPrice() > calculateAmount24HoursAgo(coin.getPrice(), coin.getPerc())){
                Helpers.playListItemTransitionAnimation(mContext, R.color.transition_success, holder.coinListItemLayout);
                Helpers.playTextTransitionAnimation(mContext, R.color.success, holder.coinPrice);
            }
        }

        Glide.with(mContext)
                .load("https://raw.githubusercontent.com/KwabenBerko/cryptocurrency-icons/master/128/color/" +
                coin.getShortName().toLowerCase() + ".png")
                .into(holder.coinImage);
    }

    @Override
    public int getItemCount() {
        return mCoins.size();
    }

    public void updateCoinList(List<Coin> coins){
        CoinListDiffCallback diffCallback = new CoinListDiffCallback(mCoins, coins);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        mCoins.clear();
        mCoins.addAll(coins);
        diffResult.dispatchUpdatesTo(this);
        isFirstLoad = false;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.coinListItemLayout) LinearLayout coinListItemLayout;
        @BindView(R.id.coinLongName) TextView coinLongName;
        @BindView(R.id.coinShortName) TextView coinShortName;
        @BindView(R.id.coin24HourChange) TextView coin24HourChange;
        @BindView(R.id.coinArrow) ImageView coinArrow;
        @BindView(R.id.coinImage) ImageView coinImage;
        @BindView(R.id.coinPrice) TextView coinPrice;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private double calculateAmount24HoursAgo(double currentAmount, double percentage){
        return ((percentage / 100) * currentAmount) + currentAmount;
    }
}
