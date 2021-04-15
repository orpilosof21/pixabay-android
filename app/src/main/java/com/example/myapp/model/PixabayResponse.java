package com.example.myapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PixabayResponse {
    @SerializedName("total")
    private int total;

    @SerializedName("totalHits")
    private int total_hits;

    @SerializedName("hits")
    private List<PixabayImage> hits;

    public PixabayResponse(int total, int total_hits, List<PixabayImage> hits) {
        this.total = total;
        this.total_hits = total_hits;
        this.hits = hits;
    }

    public int getTotal() {
        return total;
    }

    public int getTotal_hits() {
        return total_hits;
    }

    public List<PixabayImage> getHits() {
        return hits;
    }
}
