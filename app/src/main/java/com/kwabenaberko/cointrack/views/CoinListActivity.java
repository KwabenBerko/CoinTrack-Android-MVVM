package com.kwabenaberko.cointrack.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.kwabenaberko.cointrack.App;
import com.kwabenaberko.cointrack.models.Coin;
import com.kwabenaberko.cointrack.R;
import com.kwabenaberko.cointrack.viewmodels.ViewModelFactory;
import com.kwabenaberko.cointrack.viewmodels.CoinListViewModel;
import com.kwabenaberko.cointrack.views.adapters.CoinListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoinListActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory mFactory;
    private CoinListViewModel viewModel;
    @BindView(R.id.coinsRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private CoinListAdapter mCoinListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        ((App) getApplication()).getAppComponent().inject(this);
        viewModel = ViewModelProviders.of(this, mFactory).get(CoinListViewModel.class);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        mSwipeRefreshLayout.setOnRefreshListener(mRefreshListener);
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark)
        );
        viewModel.getCoinListLiveData().observe(this, new Observer<List<Coin>>() {
            @Override
            public void onChanged(@Nullable List<Coin> coins) {
                if(coins != null){
                    if(mCoinListAdapter != null){
                        mCoinListAdapter.refill(coins);
                        mCoinListAdapter.notifyDataSetChanged();
                    }
                    else{
                        mCoinListAdapter = new CoinListAdapter(getApplicationContext(), coins);
                        mRecyclerView.setAdapter(mCoinListAdapter);
                    }
                }

                if(mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if(!mSwipeRefreshLayout.isRefreshing()){
                mSwipeRefreshLayout.setRefreshing(true);
            }
            viewModel.refresh();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
