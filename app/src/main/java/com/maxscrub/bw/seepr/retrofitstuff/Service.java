package com.maxscrub.bw.seepr.retrofitstuff;

import com.maxscrub.bw.seepr.model.Item;
import com.maxscrub.bw.seepr.model.ItemResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {

    @GET("/repos/bmwanz/SeePR/pulls?state=open")
    Call<List<Item>> getPulls();

}
