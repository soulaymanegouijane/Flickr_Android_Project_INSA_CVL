package com.example.image_searcher_gouijane.utils;


import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Soulaymane GOUIJANE
 */

/**
 * I used this class to download the image from its URL and it extends AsyncTask because it performs actions using the network
 * so we extend this class in order to allow it to execute the fetch in background
 */
public class ImageDownloadManager extends AsyncTask<String, Void, Bitmap> {
    private Context context;
    String url;
    String imageTitle;

    public ImageDownloadManager(Context context, String title, String url){
        this.context=context;
        this.url = url;
        this.imageTitle = title;
    }
    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            this.url=urls[0];
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * after the data has been fetched we use the onPostExecute method to write data to our local storage
     * @param result
     */
    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            FileOutputStream out = null;
            try {
                DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(this.url);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,imageTitle+".png");
                Long reference = downloadManager.enqueue(request);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}
}