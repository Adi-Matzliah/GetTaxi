package com.exercise.here.ui.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ProgressBar;

import com.exercise.here.R;
import com.exercise.here.application.App;
import com.exercise.here.base.BaseActivity;
import com.exercise.here.data.Taxi;
import com.exercise.here.ui.list.ItemClickListener;
import com.exercise.here.ui.list.TaxisRecyclerViewAdapter;
import com.exercise.here.ui.service.EtaUpdaterService;
import com.exercise.here.viewmodel.MainScreenViewModel;
import com.exercise.here.viewmodel.ViewModelFactory;
import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        ItemClickListener,
        ServiceConnection {

    @Nullable
    MainScreenViewModel mainViewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.contentLoadingProgressBar)
    ProgressBar progressBar;

    @Nullable
    EtaUpdaterService.LocalBinder binder;

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent(this).inject(this);
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainScreenViewModel.class);

        initRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(this);
        mainViewModel.loadTaxis();
        observableViewModel();
    }

    private void initRecyclerView() {
        TaxisRecyclerViewAdapter adapter = new TaxisRecyclerViewAdapter(mainViewModel, this, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void observableViewModel() {
        mainViewModel.getTaxis().observe(this, taxis -> {
            if(taxis != null && !taxis.isEmpty()) {
                handleRecyclerViewVisibility(false);
            }
        });
        mainViewModel.getIsLoading().observe(this, this::handleProgressBarVisibility);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, EtaUpdaterService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(this);
    }

    private void handleRecyclerViewVisibility(Boolean isLoading) {
        recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    private void handleProgressBarVisibility(Boolean isLoading) {
        if (!isLoading) {
            swipeRefreshLayout.setRefreshing(false);
        }
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onRefresh() {
        mainViewModel.fetchTaxis();
    }

    @Override
    public void onItemClick(Taxi item, int position) { }

    @Override
    public void onLongItemClick(Taxi item, int position) { }

    @Override
    public void onServiceConnected(ComponentName name, IBinder iBinder) {
        // We've bound to LocalService, cast the IBinder and get LocalService instance
        binder = (EtaUpdaterService.LocalBinder) iBinder;
        binder.getService().onUpdateEta();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}