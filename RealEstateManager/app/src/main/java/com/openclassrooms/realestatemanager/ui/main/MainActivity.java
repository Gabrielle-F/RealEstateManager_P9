package com.openclassrooms.realestatemanager.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.addProperty.AddPropertyActivity;
import com.openclassrooms.realestatemanager.ui.authentication.ChoiceClientOrAgentActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.activity_main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPropertyActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user == null) {
            Intent intent = new Intent(this, ChoiceClientOrAgentActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        BottomAppBar bottomAppBar = findViewById(R.id.activity_main_bottom_app_bar);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_app_bar_activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bottom_app_bar_edit_property) {
            //TODO start EditPropertyFragment with id of the selected property
            return true;
        }
        if (id == android.R.id.home) {
            BottomNavigationDrawerFragment bottomNavDrawerFragment = new BottomNavigationDrawerFragment();
            bottomNavDrawerFragment.show(getSupportFragmentManager(), bottomNavDrawerFragment.getTag());
            return true;
        }
        return true;
    }
}
