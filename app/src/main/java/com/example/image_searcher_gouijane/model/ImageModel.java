package com.example.image_searcher_gouijane.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Soulaymane GOUIJANE
 */

/**
 * this class is a sort of DTO, i use it to transfer and manipulate data easily between objects
 */
public class ImageModel {
    private String dbId;
    private String imageId;
    private String imageTitle;
    private String imageUrl;
    private String imageFarm;
    private String imageServer;
    private String imageSecret;
    private Boolean isFavourite = false;

    /**
     * this constructor creates an Image Model from JSONObject that the flickr API returns
     * @param jsonObject this is the JSONObject that the API returns
     * @throws JSONException
     */
    public ImageModel(JSONObject jsonObject) throws JSONException {
        this.imageId = jsonObject.getString("id");
        this.imageTitle = jsonObject.getString("title");
        this.imageFarm = jsonObject.getString("farm");
        this.imageServer = jsonObject.getString("server");
        this.imageSecret = jsonObject.getString("secret");
        this.imageUrl = "https://farm"+imageFarm+".staticflickr.com/"+imageServer+"/"+imageId+"_"+imageSecret+".jpg";
    }

    public ImageModel(String imageId, String imageTitle, String imageUrl) {
        this.imageId = imageId;
        this.imageTitle = imageTitle;
        this.imageUrl = imageUrl;
    }


    public String getImageId() {
        return imageId;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }
}
