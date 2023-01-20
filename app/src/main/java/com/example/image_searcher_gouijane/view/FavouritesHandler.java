package com.example.image_searcher_gouijane.view;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageButton;
import com.example.image_searcher_gouijane.R;
import com.example.image_searcher_gouijane.db.DatabaseHelper;
import com.example.image_searcher_gouijane.model.ImageModel;

/**
 * @author Soulaymane GOUIJANE
 */

public class FavouritesHandler{
    private ImageButton favouriteButton;
    private Context context;

    public FavouritesHandler(ImageButton favouriteButton,
                             Context context){
        this.favouriteButton = favouriteButton;
        this.context = context;
    }

    public void handleFavouriteButtonClick(ImageModel image) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        if (!dbHelper.checkPhotoExistenceById(image.getImageId())){
            dbHelper.insertImageInDB(image);
        }else {
            dbHelper.deleteIfExistsById(image.getImageId());
        }
    }

    public void handleFavouriteCheckImage(ImageModel image) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        if(!dbHelper.checkPhotoExistenceById(image.getImageId())) {
            favouriteButton.setImageResource(R.drawable.ic_unfavourite);
        }else{
            favouriteButton.setImageResource(R.drawable.ic_favourite);
        }
    }
}
