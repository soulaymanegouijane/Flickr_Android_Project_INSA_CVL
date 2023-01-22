package com.example.image_searcher_gouijane.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import com.example.image_searcher_gouijane.utils.FlickrImages;
import com.example.image_searcher_gouijane.R;

import java.util.Objects;

/**
 * this class initializes the search fragment and sets onTextListner to our search bar in order to get the text and perform searches
 */
public class SearchFragment extends Fragment {
    private SearchView searchView;

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        performSearch(Objects.isNull(bundle)?
                "":
                bundle.getString("query"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if(query.equals("")) onQueryTextSubmit("");
                performSearch(query);
                return false;
            }
        });
        return view;
    }

    /**
     * This function performs the search and send the results to the adapter in order to update the listView with results
     * @param searchTerm this is the string searched by the user
     */
    public void performSearch(String searchTerm) {
        new FlickrImages(getView().findViewById(R.id.listview), getContext()).execute(searchTerm);
    }


}
