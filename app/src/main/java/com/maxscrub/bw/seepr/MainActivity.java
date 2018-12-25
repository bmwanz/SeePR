package com.maxscrub.bw.seepr;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    List<Item> pullList = null;

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

            Call<List<Item>> call = apiService.getPulls();

            call.enqueue(new Callback<List<Item>>() {
                @Override
                public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
//                    List<Item> items = response.body().getPulls();
                    pullList = response.body();
                    recyclerView.setAdapter(new ItemAdapter(getApplicationContext(), pullList));
                    recyclerView.smoothScrollToPosition(0);
                    swipeContainer.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<List<Item>> call, Throwable t) {
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
