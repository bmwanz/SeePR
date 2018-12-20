package com.maxscrub.bw.seepr.retrofitstuff;

import com.maxscrub.bw.seepr.model.ItemResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {

    @GET("/users/bmwanz/repos")
    Call<ItemResponse> getRepos();

}
