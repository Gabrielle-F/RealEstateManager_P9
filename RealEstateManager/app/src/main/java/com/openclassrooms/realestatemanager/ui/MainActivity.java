package com.openclassrooms.realestatemanager.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding;
import com.openclassrooms.realestatemanager.databinding.ActivityMainToolbarBinding;
import com.openclassrooms.realestatemanager.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityMainToolbarBinding toolbar = binding.activityMainToolbarInclude;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        BottomAppBar bottomAppBar = binding.activityMainBottomAppBar;
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.bottom_app_bar_edit_property) {
                    //TODO start editPropertyActivity with the id of the selectedProperty
                    return true;
                }
                return false;
            }
        });
        return false;
    }
}
