package com.maxscrub.bw.seepr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemResponse {

    @SerializedName("pulls")
    @Expose
    private List<Item> pulls;

    public List<Item> getPulls() {
        return pulls;
    }

}
