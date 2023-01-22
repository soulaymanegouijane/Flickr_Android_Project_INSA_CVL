package com.example.image_searcher_gouijane.utils;

import android.annotation.SuppressLint;
import android.database.Cursor;

import com.example.image_searcher_gouijane.model.ImageModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Soulaymane GOUIJANE
 */

/**
 * I created this abstract class in order to simplify DATA Converting
 */
public abstract class DataConverter {
    /**
     * this function is used to convert the cursor of the database to a List of  ImageModel
     * @param cursor this is the cursor that the Query of SQLLite returns
     * @return a list of ImageModels
     */
    public static List<ImageModel> convertCursorToModelList(@NotNull Cursor cursor) {
        List<ImageModel> images = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst()) {
            do {
                // retrieve column values
                @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"));
                @SuppressLint("Range") String imageId = cursor.getString(cursor.getColumnIndex("imageId"));
                @SuppressLint("Range") String imageTitle = cursor.getString(cursor.getColumnIndex("imageTitle"));
                ImageModel imageModel = new ImageModel(imageId, imageTitle, imageUrl);
                images.add(imageModel);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return images;
    }

    /**
     * This function converts the JSONArray to a list of ImageModel
     * @param jsonImages those represents the images that we get from the Flickr API
     * @return a List of ImageModels
     * @throws JSONException
     */
    public static List<ImageModel> convertJSONArrayToList(JSONArray jsonImages) throws JSONException {
        List<ImageModel> images = new ArrayList<>();
        if(jsonImages==null){
            return null;
        }
        for (int i = 0; i < jsonImages.length(); i++) {
            JSONObject jsonObject = jsonImages.getJSONObject(i);
            ImageModel imageModel = new ImageModel(jsonObject);
            images.add(imageModel);
        }
        return images;
    }
}
