package com.example.myapp.model;

import com.google.gson.annotations.SerializedName;

public class PixabayImage {
    @SerializedName("id")
    private int id;

    @SerializedName("pageURL")
    private String page_url;

    @SerializedName("type")
    private String type;

    @SerializedName("tags")
    private String tags;

    @SerializedName("previewURL")
    private String preview_url;

    @SerializedName("previewWidth")
    private int preview_width;

    @SerializedName("previewHeight")
    private int preview_height;

    @SerializedName("webformatURL")
    private String webformat_url;

    @SerializedName("webformatWidth")
    private int webformat_width;

    @SerializedName("webformatHeight")
    private int webformat_height;

    @SerializedName("largeImageURL")
    private String image_url;

    @SerializedName("imageWidth")
    private int image_width;

    @SerializedName("imageHeight")
    private int image_height;

    @SerializedName("imageSize")
    private int image_size;

    @SerializedName("views")
    private int views;

    @SerializedName("downloads")
    private int downloads;

    @SerializedName("favorites")
    private int favorites;

    @SerializedName("likes")
    private int likes;

    @SerializedName("comments")
    private int comments;

    @SerializedName("user_id")
    private int user_id;

    @SerializedName("user")
    private String user;

    @SerializedName("userImageURL")
    private String user_image_url;

    public PixabayImage(int id, String page_url, String type, String tags, String preview_url, int preview_width, int preview_height, String webformat_url, int webformat_width, int webformat_height, String image_url, int image_width, int image_height, int image_size, int views, int downloads, int favorites, int likes, int comments, int user_id, String user, String user_image_url) {
        this.id = id;
        this.page_url = page_url;
        this.type = type;
        this.tags = tags;
        this.preview_url = preview_url;
        this.preview_width = preview_width;
        this.preview_height = preview_height;
        this.webformat_url = webformat_url;
        this.webformat_width = webformat_width;
        this.webformat_height = webformat_height;
        this.image_url = image_url;
        this.image_width = image_width;
        this.image_height = image_height;
        this.image_size = image_size;
        this.views = views;
        this.downloads = downloads;
        this.favorites = favorites;
        this.likes = likes;
        this.comments = comments;
        this.user_id = user_id;
        this.user = user;
        this.user_image_url = user_image_url;
    }

    public int getId() {
        return id;
    }

    public String getPage_url() {
        return page_url;
    }

    public String getType() {
        return type;
    }

    public String getTags() {
        return tags;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public int getPreview_width() {
        return preview_width;
    }

    public int getPreview_height() {
        return preview_height;
    }

    public String getWebformat_url() {
        return webformat_url;
    }

    public int getWebformat_width() {
        return webformat_width;
    }

    public int getWebformat_height() {
        return webformat_height;
    }

    public String getImage_url() {
        return image_url;
    }

    public int getImage_width() {
        return image_width;
    }

    public int getImage_height() {
        return image_height;
    }

    public int getImage_size() {
        return image_size;
    }

    public int getViews() {
        return views;
    }

    public int getDownloads() {
        return downloads;
    }

    public int getFavorites() {
        return favorites;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser() {
        return user;
    }

    public String getUser_image_url() {
        return user_image_url;
    }
}
