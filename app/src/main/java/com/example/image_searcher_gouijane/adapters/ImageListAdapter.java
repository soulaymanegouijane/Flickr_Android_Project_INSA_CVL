package com.example.image_searcher_gouijane.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.image_searcher_gouijane.R;
import com.example.image_searcher_gouijane.db.DatabaseHelper;
import com.example.image_searcher_gouijane.model.ImageModel;
import com.example.image_searcher_gouijane.utils.ImageDownloadManager;
import com.example.image_searcher_gouijane.utils.FavouritesHandler;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * @author Soulaymane GOUIJANE
 */
/**
 * this adapter is created to get the images from the Search Fragment and create for each image an image item with various informations
 * this adapter extends the BaseAdapter because it's used to set and update the content of a ListView
 */
public class ImageListAdapter extends BaseAdapter {
    private List<ImageModel> images;
    private LayoutInflater inflater;
    private Context context;
    private ImageButton downloadButton;
    private ImageButton favouriteButton;
    private  TextView imageTitle;

    public ImageListAdapter(Context context, List<ImageModel> images) {
        this.context = context;
        this.images = images;
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
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.image_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.favouriteImageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageModel image = images.get(position);
        if(databaseHelper.checkPhotoExistenceById(image.getImageId())){
            image.setFavourite(true);
        }
            imageTitle = convertView.findViewById(R.id.title);
            favouriteButton = convertView.findViewById(R.id.favourite_button);
            downloadButton = convertView.findViewById(R.id.download_button);
            holder.imageView.setTag(image.getImageUrl());
            holder.imageView.setTag(image);
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
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ImageDownloadManager(view.getContext(), image.getImageTitle(), image.getImageUrl()).execute(image.getImageUrl());
            }
        });
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
    }

}
