package com.example.skincap.ui;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.skincap.R;
import com.example.skincap.databinding.ActivityMainBinding;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    private static final int START_DESTINATION_ID = R.id.home_dashboard;
    private static final int[] MENU_ITEMS = new int[]{R.id.settings, R.id.notifs, R.id.info};

    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        setupNavigationBar();

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        navController.addOnDestinationChangedListener(this);
    }

    @Override
    public void onDestinationChanged(
            @NonNull NavController controller,
            @NonNull NavDestination destination,
            @Nullable Bundle arguments
    ) {

        final boolean isStartId = destination.getId() == START_DESTINATION_ID;
        binding.appIcon.setVisibility(isStartId ? View.VISIBLE : View.GONE);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(!isStartId);

        viewModel.setDestinationId(destination.getId());
    }

    private NavHostFragment getNavHostFragment() {
        return (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
    }

    private void setupNavigationBar() {
        navController = getNavHostFragment().getNavController();
        setupWithNavController(binding.navigationView, navController);
        setupActionBarWithNavController(this, navController, getTopLevelDestinations());
        binding.navigationView.setOnNavigationItemReselectedListener(item -> {
            // prevent the nav bar items from being
            // re-selected so it won't recreate the fragments.
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        onCreateOptionsMenuCreated();
        return true;
    }

    private AppBarConfiguration getTopLevelDestinations() {
        final Set<Integer> topLevelDestinations = new HashSet<Integer>() {
            {
                add(R.id.home_dashboard);
                add(R.id.camera_gallery);
                add(R.id.journal_list);
            }
        };

        return new AppBarConfiguration.Builder(topLevelDestinations).build();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void onCreateOptionsMenuCreated() {
        viewModel.getDestinationId().observe(this, destinationId -> {
            if (destinationId == null) {
                return;
            }
            for (int i = 0; i < MENU_ITEMS.length; i++) {
                binding.toolbar.getMenu()
                        .findItem(MENU_ITEMS[i])
                        .setVisible((i == 0) != (destinationId == START_DESTINATION_ID));
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notifs:
                navigateToFragment(R.id.to_notifications);
            case R.id.info:
                // todo add menu destinations
            case R.id.settings:
                // todo add menu destinations
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateToFragment(@IdRes int resId) {
        navController.navigate(resId);
    }
}