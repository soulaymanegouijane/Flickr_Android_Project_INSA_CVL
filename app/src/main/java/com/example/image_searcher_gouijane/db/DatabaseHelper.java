package com.example.image_searcher_gouijane.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ImageButton;

import com.example.image_searcher_gouijane.model.ImageModel;

/**
 * @author Soulaymane GOUIJANE
 */

/**
 * I created this class in order to perform actions in the Database
 * Below you can find all actions that used in this application such as insert, delete, fetchData ...
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;
    private static final String[] PROJECTION =  {
            FavoriteContract.FavoriteEntry._ID,
            FavoriteContract.FavoriteEntry.COLUMN_IMAGE_ID,
            FavoriteContract.FavoriteEntry.COLUMN_IMAGE_URL,
            FavoriteContract.FavoriteEntry.COLUMN_IMAGE_TITLE};

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " +
                FavoriteContract.FavoriteEntry.TABLE_NAME + " (" +
                FavoriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY," +
                FavoriteContract.FavoriteEntry.COLUMN_IMAGE_ID + " TEXT," +
                FavoriteContract.FavoriteEntry.COLUMN_IMAGE_URL + " TEXT," +
                FavoriteContract.FavoriteEntry.COLUMN_IMAGE_TITLE + " TEXT)";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(db);
    }

    /**
     * this function fetches the data from the database
     * @param whereSelection this is an optional argument (it may be null) it represents the where conditions that we want to perform
     * @param whereSelectionArgs this is also an optional argument it represents the arguments that we pass to the where condition above
     * @return returns a cursor of results that will be conveted later to a List of Image Models
     */
    public Cursor fetchData(String whereSelection, String[] whereSelectionArgs){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                FavoriteContract.FavoriteEntry.TABLE_NAME,
                PROJECTION,
                whereSelection,
                whereSelectionArgs,
                null,
                null,
                null);
        return cursor;
    }

    /**
     * this function checks if an image exists in the database using its ID
     * @param imageId this is the id of the image that the function checks its existence
     * @return it returns a boolean (false --> image doesn't exist || true --> image exists)
     */
    public Boolean checkPhotoExistenceById(String imageId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = FavoriteContract.FavoriteEntry.COLUMN_IMAGE_ID + " = ?";
        String[] selectionArgs = { imageId };
        Cursor cursor = db.query(
                FavoriteContract.FavoriteEntry.TABLE_NAME,
                PROJECTION,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return cursor.getCount() != 0;
    }

    /**
     * this function checks the existence of an image using its ID and if the image exists, it deletes it
     * @param imageId this is the ID of the image that we want to delete
     */
    public void deleteIfExistsById(String imageId) {
        if (checkPhotoExistenceById(imageId)) {
            SQLiteDatabase db = this.getReadableDatabase();
            String whereClause = FavoriteContract.FavoriteEntry.COLUMN_IMAGE_ID + " = ?";
            String[] whereArgs = {imageId};
            db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, whereClause, whereArgs);
        }else{
            return;
        }
    }

    /**
     * this fucntion inserts a given Image(ImageModel) into database
     * @param image this is the ImageModel that the function inserts
     */
    public void insertImageInDB(ImageModel image){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_IMAGE_ID,image.getImageId());
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_IMAGE_URL, image.getImageUrl());
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_IMAGE_TITLE, image.getImageTitle());
        long newRowId = db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME, null, contentValues);
    }

}
