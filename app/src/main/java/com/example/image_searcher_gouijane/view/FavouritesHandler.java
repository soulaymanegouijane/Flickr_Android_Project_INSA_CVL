package com.example.image_searcher_gouijane.view;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.FragmentManager;

import com.example.image_searcher_gouijane.R;
import com.example.image_searcher_gouijane.adapters.ImageListAdapter;
import com.example.image_searcher_gouijane.db.DatabaseHelper;
import com.example.image_searcher_gouijane.db.FavoriteContract;
import com.example.image_searcher_gouijane.model.ImageModel;

import org.json.JSONException;

public class FavouritesHandler{
    private ImageButton favouriteButton;
    private Context context;
    private SQLiteDatabase db;

    public FavouritesHandler(ImageButton favouriteButton,
                             Context context){
        this.favouriteButton = favouriteButton;
        this.context = context;
    }

    public void handleFavouriteButtonClick(ImageModel image) {
        DatabaseHelper mDbHelper = new DatabaseHelper(context);
        db = mDbHelper.getWritableDatabase();
        // checking if the image doesn't exist already
        String selection = FavoriteContract.FavoriteEntry.COLUMN_IMAGE_ID + " = ?";
        String[] selectionArgs = { image.getImageId() };
        Cursor cursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if ((cursor.getCount() == 0)){
            ContentValues contentValues = new ContentValues();
            contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_IMAGE_ID,image.getImageId());
            contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_IMAGE_URL, image.getImageUrl());
            contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_IMAGE_TITLE, image.getImageTitle());
            long newRowId = db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME, null, contentValues);
        }else {
            String whereClause = FavoriteContract.FavoriteEntry.COLUMN_IMAGE_ID + " = ?";
            String[] whereArgs = { image.getImageId() };
            int deletedRows = db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, whereClause, whereArgs);
        }
        db.close();
    }

    public void handleFavouriteCheckImage(ImageModel image) {
        DatabaseHelper mDbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_IMAGE_ID};
        String selection = FavoriteContract.FavoriteEntry.COLUMN_IMAGE_ID + " = ?";
        String[] selectionArgs = { image.getImageId() };
        Cursor cursor = db.query(
                FavoriteContract.FavoriteEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null);
        if(cursor.getCount() == 0) {
            favouriteButton.setImageResource(R.drawable.ic_unfavourite);
        }else{
            favouriteButton.setImageResource(R.drawable.ic_favourite);
        }
    }
}
