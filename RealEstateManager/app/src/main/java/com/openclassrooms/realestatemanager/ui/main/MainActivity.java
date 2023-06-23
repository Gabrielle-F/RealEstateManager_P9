package com.openclassrooms.realestatemanager.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.openclassrooms.realestatemanager.DaggerHiltApplication;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.addProperty.AddEditPropertyActivity;
import com.openclassrooms.realestatemanager.ui.authentication.LogInActivity;
import com.openclassrooms.realestatemanager.ui.propertiesList.PropertiesListFragment;
import com.openclassrooms.realestatemanager.ui.searchProperties.SearchPropertiesFragment;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        AddEditPropertyActivity.OnPropertyAddedOrUpdatedListener, SearchPropertiesFragment.OnParametersSelected {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private final PropertiesListFragment propertiesListFragment = new PropertiesListFragment();
    private final SearchPropertiesFragment searchPropertiesFragment = new SearchPropertiesFragment();
    private Boolean userClient;

    private Boolean userAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_main);

            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment_container_view, propertiesListFragment).commit();

            userClient = getIntent().getBooleanExtra("client", false);
            userAgent = getIntent().getBooleanExtra("agent", true);
            searchPropertiesFragment.setOnParametersSelectedListener(this);

            configureToolbar();
            configureBottomBar();
            configureNavDrawer();

            configureNavDrawerOnItemClickListener();
            updateNavigationViewInfo();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_toolbar:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment_container_view, searchPropertiesFragment).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void configureToolbar() {
        toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configureNavDrawer() {
        drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
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
                    case R.id.currency_exchange:
                        showCurrencyDialog();
                        break;
                }
                return true;
            }
        });
    }

    private void updateNavigationViewInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        NavigationView navView = findViewById(R.id.navigation_view);
        View navHeader = navView.getHeaderView(0);
        TextView userEmail = navHeader.findViewById(R.id.nav_header_user_email);
        TextView userName = navHeader.findViewById(R.id.nav_header_user_name);
        if(user != null) {
            userEmail.setText(user.getEmail());
            userName.setText(user.getDisplayName());
        }
    }

    private void configureBottomBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bottom_bar_properties_list) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment_container_view, propertiesListFragment).commit();
                    return true;
                }
                if (id == R.id.bottom_bar_add_property) {
                    Intent intent = new Intent(getApplicationContext(), AddEditPropertyActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    private void signOut(Context context) {
        AuthUI.getInstance().signOut(context).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    startLogInActivity();
                    Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void startLogInActivity() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    private void showCurrencyDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Choisissez une devise");
        final String[] currencies = {"Dollar", "Euro"};
        final DaggerHiltApplication daggerHilt = (DaggerHiltApplication) getApplication();
        dialog.setItems(currencies, (dialog1, which) -> {
            String selectedCurrency = currencies[which];
            if (Objects.equals(selectedCurrency, "Euro")) {
                daggerHilt.appPreferences.setSelectedCurrency("Euro");
            } else {
                daggerHilt.appPreferences.setSelectedCurrency("Dollar");
            }
            propertiesListFragment.updateCurrency(selectedCurrency);
        });
        dialog.setNegativeButton("Annuler", (dialog12, which) -> dialog12.dismiss());
        dialog.create().show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //TODO : use it for Toolbar
        return true;
    }

    @Override
    public void onPropertyAddedOrUpdated() {

    }

    @Override
    public void filterList(int minPrice, int maxPrice, int minArea, int maxArea, @Nullable String city, @Nullable List<String> types,
                           @Nullable List<Integer> rooms, @Nullable Boolean availability, @Nullable String startDate, @Nullable String endDate,
                           @Nullable List<Integer> numberOfPictures, @Nullable String agentName, boolean school, boolean restaurants,
                           boolean playground, boolean supermarket, boolean shoppingArea, boolean cinema) {
        propertiesListFragment.getFilteredList(minPrice, maxPrice, minArea, maxArea, city, types, rooms, availability, startDate, endDate, numberOfPictures,
                agentName, school, restaurants, playground, supermarket, shoppingArea, cinema);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment_container_view, propertiesListFragment).commit();
    }
}
