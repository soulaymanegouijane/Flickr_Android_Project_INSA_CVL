package com.example.image_searcher_gouijane.db;

import android.provider.BaseColumns;

/**
 * @author Soulaymane GOUIJANE
 */

/**
 * this class represents the shape of the table in the Database
 */
public final class FavoriteContract {
    private FavoriteContract() {}

    public static class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_IMAGE_ID = "imageId";
        public static final String COLUMN_IMAGE_URL = "imageUrl";
        public static final String COLUMN_IMAGE_TITLE = "imageTitle";
    }
}
