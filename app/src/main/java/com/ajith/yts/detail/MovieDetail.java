package com.ajith.yts.detail;

public class MovieDetail {

    private String quality;
    private String type;
    private String size;
    private String url;

    public MovieDetail(String quality, String type, String size, String url) {
        this.quality = quality;
        this.type = type;
        this.size = size;
        this.url = url;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
