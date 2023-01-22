package com.example.image_searcher_gouijane.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import com.example.image_searcher_gouijane.adapters.ImageListAdapter;
import com.example.image_searcher_gouijane.model.ImageModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @author Soulaymane GOUIJANE
 */

/**
 * this class is created to handle the fetch of the Flickr API and it extends AsyncTask in order to perform tasks in background
 * and returns results that we manipulate with the onPostExecute function
 */
public class FlickrImages extends AsyncTask<String, Void, JSONArray> {
    private static final String API_KEY = "38b58d4d8a0bc07cf91a7a2c0dfdbb44";
    private static final String ENDPOINT = "https://www.flickr.com/services/rest/";
    private static final String SEARCH_METHOD = "?method=flickr.photos.search&api_key=";
    private static final String RECENT_METHOD = "?method=flickr.photos.getRecent&api_key=";
    private ListView listView;
    private Context context;

    public FlickrImages(ListView listView, Context context) {
        this.listView = listView;
        this.context = context;
    }
    @Override
    protected JSONArray doInBackground(String... strings) {
        String query = strings[0];
        JSONObject data;
        try{
            data =  fetchImagesFromAPI(SEARCH_METHOD, query);
            if (data.getString("stat").equals("fail")){
                data =  fetchImagesFromAPI(RECENT_METHOD, "");
            }
            JSONObject photos = data.getJSONObject("photos");
            return photos.getJSONArray("photo");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * this function is used to fetch data from the API
     * @param searchMethod
     * @param query
     * @return the JSONObject of the results
     * @throws IOException
     * @throws JSONException
     */
    private JSONObject fetchImagesFromAPI(String searchMethod, String query) throws IOException, JSONException {
        String url = ENDPOINT + searchMethod + API_KEY + "&format=json&nojsoncallback=1"+(searchMethod.equals(SEARCH_METHOD)? "&text=" + query : "");
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }
        return new JSONObject(json.toString());
    }

    @Override
    protected void onPostExecute(JSONArray jsonImages) {
        try {
            List<ImageModel> images = DataConverter.convertJSONArrayToList(jsonImages);
            if (images != null && images.size()!=0) {
                ImageListAdapter adapter = new ImageListAdapter(context, images);
                listView.setAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

}

