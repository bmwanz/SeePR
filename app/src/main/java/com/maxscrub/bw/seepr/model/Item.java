package com.maxscrub.bw.seepr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("id")
    @Expose
    private String prID;

    @SerializedName("number")
    @Expose
    private int prNumber;

    @SerializedName("state")
    @Expose
    private String prState;

    @SerializedName("title")
    @Expose
    private String prTitle;

    public Item(String prID, int prNumber, String prState, String prTitle) {
        this.prID = prID;
        this.prNumber = prNumber;
        this.prState = prState;
        this.prTitle = prTitle;
    }

    public String getPrID() {
        return prID;
    }

    public int getPrNumber() {
        return prNumber;
    }

    public String getPrState() {
        return prState;
    }

    public String getPrTitle() {
        return prTitle;
    }
}
