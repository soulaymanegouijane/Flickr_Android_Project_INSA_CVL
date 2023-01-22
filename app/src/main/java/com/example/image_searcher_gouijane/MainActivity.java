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

import com.example.image_searcher_gouijane.model.ImageModel;
import com.example.image_searcher_gouijane.utils.ImageDownloadManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private static Dialog myDialog;
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
        NavController navController=Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * in this on start method we check the shared preferences to get which mode has been enabled (dark/light mode)
     * and set the application theme to it
     */
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        boolean isDarkModeEnabled = sharedPreferences.getBoolean("isDarkModeEnabled", false);
        AppCompatDelegate.setDefaultNightMode(isDarkModeEnabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    /**
     * this function is used to display the popup that I used to show the image details
     * @param v this is the imageView that has been clicked
     */
    public static void showImageDetailsPopUp(View v) {
        //Getting all items
        LinearLayout imageItem = (LinearLayout) v.getParent();
        LinearLayout titleBox = (LinearLayout) imageItem.findViewById(R.id.titleAndFavBtnBox);
        TextView title = (TextView) titleBox.findViewById(R.id.title);
        ImageView photo = (ImageView) v.findViewById(R.id.favouriteImageView) ;
        ImageModel image = (ImageModel) photo.getTag();
        myDialog.setContentView(R.layout.show_image_pop_up);
        ImageButton downloadPopButton = (ImageButton) myDialog.findViewById(R.id.download_pop_btn);
        ImageButton closeButton =(ImageButton) myDialog.findViewById(R.id.txtclose);
        ImageView showImage = (ImageView) myDialog.findViewById(R.id.showinpopup);
        TextView titleInPopUp = (TextView) myDialog.findViewById(R.id.showTitleInPopUp);
        titleInPopUp.setText(title.getText());

        Picasso.get().load(image.getImageUrl()).into(showImage);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
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