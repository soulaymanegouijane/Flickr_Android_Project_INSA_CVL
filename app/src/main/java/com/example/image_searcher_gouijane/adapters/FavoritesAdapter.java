package com.example.image_searcher_gouijane.adapters;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.image_searcher_gouijane.R;
import com.example.image_searcher_gouijane.db.DatabaseHelper;
import com.example.image_searcher_gouijane.model.ImageModel;
import com.example.image_searcher_gouijane.utils.ImageDownloadManager;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author Soulaymane GOUIJANE
 */

/**
 * this adapter is created to get the images from the Favourite Fragment and create for each image an image item with various informations
 * this adapter extends the RecycleView.Adapter because it's used to set and update instantly the content of a RecycleView
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private List<ImageModel> favoritedPhotos;

    /**
     * Constructor
     * @param favoritedPhotos those are the images that we fetch from the SQLlite Database
     */
    public FavoritesAdapter(List<ImageModel> favoritedPhotos) {
        this.favoritedPhotos = favoritedPhotos;
    }
    public FavoritesAdapter() {
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new FavoritesViewHolder(view, favoritedPhotos, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        ImageModel photo = favoritedPhotos.get(position);
        holder.bind(photo);
    }

    @Override
    public int getItemCount() {
        return favoritedPhotos.size();
    }

    static class FavoritesViewHolder extends RecyclerView.ViewHolder {
        private ImageView photoImageView;
        private ImageButton heartButton;
        private ImageButton downloadButton;
        private TextView imageTitle;
        private List<ImageModel> favoritedPhotos;
        private FavoritesAdapter adapter;
        Dialog myDialog;

        public FavoritesViewHolder(@NonNull View itemView,List<ImageModel> favouritePhotos, FavoritesAdapter adapter) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.favouriteImageView);
            this.adapter = adapter;
            this.favoritedPhotos = favouritePhotos;
            myDialog = new Dialog(itemView.getContext());
        }

        /**
         * this function is used to set the view of each one of the images
         * @param photo
         */
        public void bind(final ImageModel photo) {
                imageTitle = itemView.findViewById(R.id.title);
                Picasso.get().load(photo.getImageUrl()).into(photoImageView);
                photoImageView.setTag(photo);
                imageTitle.setText(photo.getImageTitle());
                heartButton = itemView.findViewById(R.id.favourite_button);
                downloadButton = itemView.findViewById(R.id.download_button);
                heartButton.setImageResource(R.drawable.ic_favourite);
                heartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    favoritedPhotos.remove(position);
                    DatabaseHelper mydbHelper = new DatabaseHelper(photoImageView.getContext());
                    mydbHelper.deleteIfExistsById(photo.getImageId());
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, favoritedPhotos.size());
                }
            });
                downloadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new ImageDownloadManager(view.getContext(),photo.getImageTitle(), photo.getImageUrl()).execute(photo.getImageUrl());
                    }
                });
        }
    }
}
