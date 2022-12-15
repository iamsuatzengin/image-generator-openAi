package com.suatzengin.imagegeneratorwithopenai.data.network.model;

import java.util.List;

public class ImageResponse {
    private Long created;
    private List<Data> data;

    public Long getCreated() {
        return created;
    }

    public List<Data> getData() {
        return data;
    }

}
