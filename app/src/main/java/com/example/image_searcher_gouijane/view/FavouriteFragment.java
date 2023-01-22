package com.example.image_searcher_gouijane.view;

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
import com.example.image_searcher_gouijane.utils.DataConverter;

/**
 * @author Soulaymane GOUIJANE
 */

/**
 * This class initializes the Favourite fragment and fetches data from our Database and send it to the adapter
 * in order to display them
 */
public class FavouriteFragment extends Fragment {
    private View view;
    private FavoritesAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favourite_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.listviewfavourites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavoritesAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DatabaseHelper mDbHelper = new DatabaseHelper(getContext());
        Cursor cursor = mDbHelper.fetchData(null, null);
        RecyclerView recyclerView = view.findViewById(R.id.listviewfavourites);
        try {
            FavoritesAdapter adapter = new FavoritesAdapter(DataConverter.convertCursorToModelList(cursor));
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
           e.printStackTrace();
        }
    }


}
