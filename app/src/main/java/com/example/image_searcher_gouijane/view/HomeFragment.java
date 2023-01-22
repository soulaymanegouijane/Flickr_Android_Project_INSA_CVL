package com.example.image_searcher_gouijane.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.image_searcher_gouijane.R;

/**
 * @author Soulaymane GOUIJANE
 */

/**
 * This class initialiszes the Home fragment and it sets listeners to our categories buttons
 */
public class HomeFragment extends Fragment {
    View homeView;
    private ImageView catsCategoryBtn;
    private ImageView dogsCategoryBtn;
    private ImageView flowersCategoryBtn;
    private ImageView fashionCategoryBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.fragment_home, container, false);
        return homeView;
    }

    @Override
    public void onStart() {
        super.onStart();
        catsCategoryBtn = homeView.findViewById(R.id.catCategoryBtn);
        dogsCategoryBtn = homeView.findViewById(R.id.dogsCategoryBtn);
        flowersCategoryBtn = homeView.findViewById(R.id.flowersCategoryBtn);
        fashionCategoryBtn = homeView.findViewById(R.id.fashionCategoryBtn);
        catsCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("query", "cats");
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_navigation_home_to_navigation_search,bundle);
            }
        });
        dogsCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("query", "dogs");
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_navigation_home_to_navigation_search,bundle);
            }
        });
        flowersCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("query", "flowers");
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_navigation_home_to_navigation_search,bundle);
            }
        });
        fashionCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("query", "fashion");
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_navigation_home_to_navigation_search,bundle);
            }
        });
    }
}
