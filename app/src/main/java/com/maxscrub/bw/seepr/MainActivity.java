package com.maxscrub.bw.seepr;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.maxscrub.bw.seepr.model.Item;
import com.maxscrub.bw.seepr.model.ItemAdapter;
import com.maxscrub.bw.seepr.model.ItemResponse;
import com.maxscrub.bw.seepr.retrofitstuff.Client;
import com.maxscrub.bw.seepr.retrofitstuff.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Item item;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant(new Timber.DebugTree());

        initViews();
        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJSON();
                if (BuildConfig.DEBUG) {
                    Timber.d("SwipeContainer - PR refresh");
                }
            }
        });
    }

    private void initViews() {
        if (BuildConfig.DEBUG) {
            Timber.d("MainActivity initViews");
        }
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        loadJSON();
    }

    private void loadJSON() {
        try {
            Client client = new Client();
            Service apiService = client.getClient().create(Service.class);
            Call<ItemResponse> call = apiService.getPulls();

            call.enqueue(new Callback<ItemResponse>() {
                @Override
                public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                    List<Item> items = response.body().getPulls();
                    recyclerView.setAdapter(new ItemAdapter(getApplicationContext(), items));
                    recyclerView.smoothScrollToPosition(0);
                    swipeContainer.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<ItemResponse> call, Throwable t) {
                    if (BuildConfig.DEBUG) {
                        Timber.e("loadJSON onFailure - %s", t.getMessage());
                    }
                }

            });
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Timber.e("loadJSON catch exception - %s", e.getMessage());
            }
        }
    }
}
