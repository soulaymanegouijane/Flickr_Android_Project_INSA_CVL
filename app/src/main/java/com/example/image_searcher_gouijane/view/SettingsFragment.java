package com.example.image_searcher_gouijane.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.image_searcher_gouijane.R;

import java.util.zip.Inflater;

public class SettingsFragment extends Fragment {
    private View settingsView;
    private SharedPreferences sharedPreferences;
    private Switch theme_switcher;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingsView = inflater.inflate(R.layout.settings_fragment, container, false);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        theme_switcher = settingsView.findViewById(R.id.theme_switcher);
        theme_switcher.setChecked( sharedPreferences.getBoolean("isDarkModeEnabled", false));
        return settingsView;
    }

    @Override
    public void onStart() {
        super.onStart();
        theme_switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isDarkModeEnabled", true);
                    editor.apply();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isDarkModeEnabled", false);
                    editor.apply();
                }
            }
        });
    }
}
