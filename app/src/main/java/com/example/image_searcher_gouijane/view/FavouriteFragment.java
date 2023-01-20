package com.example.image_searcher_gouijane.view;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.image_searcher_gouijane.R;
import com.example.image_searcher_gouijane.adapters.FavoritesAdapter;
import com.example.image_searcher_gouijane.db.DatabaseHelper;
import com.example.image_searcher_gouijane.model.ImageModel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {
    private View view;
    private FavoritesAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favourite_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.listviewfavourites);
        DatabaseHelper mDbHelper = new DatabaseHelper(getContext());
        Cursor cursor = mDbHelper.fetchData(null, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavoritesAdapter(convertCursorToModelList(cursor));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DatabaseHelper mDbHelper = new DatabaseHelper(getContext());
        Cursor cursor = mDbHelper.fetchData(null, null);
        RecyclerView listView = view.findViewById(R.id.listviewfavourites);
        try {
//            ImageListAdapter adapter = new ImageListAdapter(this.getContext(), convertCursorToModelList(cursor), this);
//            listView.setAdapter(adapter);
            FavoritesAdapter adapter = new FavoritesAdapter(convertCursorToModelList(cursor));
            listView.setAdapter(adapter);
        }catch (Exception e){
           e.printStackTrace();
        }
    }

    public List<ImageModel> convertCursorToModelList(@NotNull Cursor cursor) {
        List<ImageModel> images = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst()) {
            do {
                // retrieve column values
                @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"));
                @SuppressLint("Range") String imageId = cursor.getString(cursor.getColumnIndex("imageId"));
                @SuppressLint("Range") String imageTitle = cursor.getString(cursor.getColumnIndex("imageTitle"));
                ImageModel imageModel = new ImageModel(imageId, imageTitle, imageUrl);
                images.add(imageModel);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return images;
    }
}
