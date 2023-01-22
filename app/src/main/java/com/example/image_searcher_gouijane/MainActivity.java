package com.example.image_searcher_gouijane;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.image_searcher_gouijane.databinding.ActivityMainBinding;
import com.example.image_searcher_gouijane.db.DatabaseHelper;
import com.example.image_searcher_gouijane.model.ImageModel;
import com.example.image_searcher_gouijane.utils.ImageDownloadManager;
import com.example.image_searcher_gouijane.view.FavouritesHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private Button homeButton;
    private Dialog myDialog;
    static NotificationBadge mBadge;
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
        myDialog = new Dialog(this);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        // getting item id to get its layout
        MenuItem item = menu.findItem(R.id.favourite_button);
        MenuItemCompat.setActionView(item, R.layout.notification_badge_layout);
        // getting the layout from menu option to set badge actions
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
        notifCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // what happens when icon is clicked
                System.out.println("----------------------Clicked-------------------- ");
            }
        });
        mBadge = notifCount.findViewById(R.id.badge_count);
        mBadge.setNumber(2);
        return true;
    }
    public void ShowPopup(View v) {
        //Getting all items
        DatabaseHelper dbHelper = new DatabaseHelper(v.getContext());
        LinearLayout imageItem = (LinearLayout) v.getParent();
        LinearLayout titleBox = (LinearLayout) imageItem.findViewById(R.id.titleAndFavBtnBox);
        TextView title = (TextView) titleBox.findViewById(R.id.title);
        ImageView photo = (ImageView) v.findViewById(R.id.favouriteImageView) ;
        ImageModel image = (ImageModel) photo.getTag();
        myDialog.setContentView(R.layout.show_image_pop_up);
        ImageButton favPopButton = (ImageButton) myDialog.findViewById(R.id.fav_pop_btn);
        ImageButton downloadPopButton = (ImageButton) myDialog.findViewById(R.id.download_pop_btn);
        ImageButton closeButton =(ImageButton) myDialog.findViewById(R.id.txtclose);
        ImageView showImage = (ImageView) myDialog.findViewById(R.id.showinpopup);
        TextView titleInPopUp = (TextView) myDialog.findViewById(R.id.showTitleInPopUp);
        titleInPopUp.setText(title.getText());
        favPopButton.setImageResource(dbHelper.checkPhotoExistenceById(image.getImageId())? R.drawable.ic_favourite : R.drawable.ic_unfavourite);
        Picasso.get().load(image.getImageUrl()).into(showImage);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        favPopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavouritesHandler fh = new FavouritesHandler((ImageButton) view, view.getContext());
                ImageModel im = new ImageModel(image.getImageId(), image.getImageTitle() ,image.getImageUrl() );
                fh.handleFavouriteButtonClick(im);
                favPopButton.setImageResource(dbHelper.checkPhotoExistenceById(image.getImageId())? R.drawable.ic_favourite : R.drawable.ic_unfavourite);
            }
        });
        downloadPopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ImageDownloadManager(v.getContext(), image.getImageTitle(), image.getImageUrl()).execute(image.getImageUrl());
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}