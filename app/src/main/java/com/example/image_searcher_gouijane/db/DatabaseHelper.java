package com.example.image_searcher_gouijane.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public Cursor fetchData(String shereSelection, String[] whereSelectionArgs){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                FavoriteContract.FavoriteEntry.TABLE_NAME,
                PROJECTION,
                shereSelection,
                whereSelectionArgs,
                null,
                null,
                null);
        return cursor;
    }

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
}
