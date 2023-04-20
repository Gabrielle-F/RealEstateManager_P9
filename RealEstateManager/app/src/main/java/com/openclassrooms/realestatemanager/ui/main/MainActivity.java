package com.openclassrooms.realestatemanager.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.addProperty.AddPropertyActivity;
import com.openclassrooms.realestatemanager.ui.authentication.ChoiceClientOrAgentActivity;
import com.openclassrooms.realestatemanager.ui.propertiesList.PropertiesListFragment;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;

    private PropertiesListFragment propertiesListFragment = new PropertiesListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureToolbar();
        configureNavDrawer();

        configureNavDrawerOnItemClickListener();
        configureBottomAppBarOnItemClickListener();

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
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void configureToolbar() {
        toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configureNavDrawer() {
        drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavDrawerOnItemClickListener() {
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        signOut(getApplicationContext());
                        break;
                }
                return true;
            }
        });
    }

    private void configureBottomAppBarOnItemClickListener() {
        BottomAppBar bottomAppBar = findViewById(R.id.activity_main_bottom_app_bar);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.bottom_app_bar_edit_property) {
                    //TODO start EditPropertyActivity
                }
                return true;
            }
        });
    }

    private void signOut(Context context) {
        AuthUI.getInstance().signOut(context).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    startChoiceClientOrAgentActivity();
                    Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void startChoiceClientOrAgentActivity() {
        Intent intent = new Intent(this, ChoiceClientOrAgentActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.properties_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment_container_view, propertiesListFragment).commit();
                break;
        }
        return true;
    }
}
