package com.example.image_searcher_gouijane;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.image_searcher_gouijane.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_category, R.id.navigation_search, R.id.navigation_settings)
                .build();
        System.out.println("appBarConfiguration = " + appBarConfiguration);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        System.out.println("navController = " + navController);
        NavigationUI.setupWithNavController(navView, navController);
    }
    @Override
    public boolean onSupportNavigateUp() {
        /**
         *   NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
         *           return NavigationUI.navigateUp(navController, appBarConfiguration)
         *                             || super.onSupportNavigateUp();
         */
        NavController navController=Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        boolean isDarkModeEnabled = sharedPreferences.getBoolean("isDarkModeEnabled", false);
        AppCompatDelegate.setDefaultNightMode(isDarkModeEnabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }
}