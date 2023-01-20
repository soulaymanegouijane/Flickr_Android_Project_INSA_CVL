package com.example.image_searcher_gouijane.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.image_searcher_gouijane.R;
import com.example.image_searcher_gouijane.db.DatabaseHelper;
import com.example.image_searcher_gouijane.db.FavoriteContract;
import com.example.image_searcher_gouijane.model.ImageModel;
import com.example.image_searcher_gouijane.view.FavouriteFragment;
import com.example.image_searcher_gouijane.view.FavouritesHandler;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ImageListAdapter extends BaseAdapter {
    private List<ImageModel> images;
    private LayoutInflater inflater;
    private Context context;
    private ImageButton favouriteButton;
    private  TextView imageTitle;
    private FavouriteFragment fragment;
    public ImageListAdapter(Context context, List<ImageModel> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }
    public ImageListAdapter(Context context, List<ImageModel> images, FavouriteFragment fragment) {
        this.context = context;
        this.images = images;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
            return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.image_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.favouriteImageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
            ImageModel image = checkExistence(images.get(position));
            imageTitle = convertView.findViewById(R.id.title);
            favouriteButton = convertView.findViewById(R.id.favourite_button);
        Picasso.get().load(image.getImageUrl()).into(holder.imageView);
        imageTitle.setText(image.getImageTitle());
        new FavouritesHandler(favouriteButton, context).handleFavouriteCheckImage(image);
        favouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new FavouritesHandler(favouriteButton, context).handleFavouriteButtonClick(image);
                    notifyDataSetChanged();
                }
            });
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
    }
    private ImageModel checkExistence(ImageModel image){
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
        if(cursor.getCount() == 0) return image;
        image.setFavourite(true);
        return image;
    }

}
