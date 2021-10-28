package com.example.skincap.ui;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
    private static final int[] MENU_ITEMS = new int[]{R.id.settings, R.id.info};

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

        //  For Camera Permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, 1);
        }
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
                add(R.id.library_skin);
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
                getMenuItem(i)
                        .setVisible((i == 0) != (destinationId == START_DESTINATION_ID));

                // Check the navigation destination explicitly by using its ID. If either the
                // current destination is a library or the journal bottom navigation fragment.
                // We have to hide all the menu items in every iteration.
                if (isJournalOrLibraryNav(destinationId)) {
                    getMenuItem(i).setVisible(false);
                }
            }
        });
    }

    private static boolean isJournalOrLibraryNav(@IdRes final int destinationId) {
        // Check if the destination ID is Journal or Library start destination.
        // @see res -> navigation folder.
        return destinationId == R.id.journal_list || destinationId == R.id.library_skin ||
                destinationId == R.id.camera_gallery || destinationId == R.id.infoFragment;
    }

    private MenuItem getMenuItem(final int index) {
        return binding.toolbar.getMenu().findItem(MENU_ITEMS[index]);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.info) {
            navigateToFragment(R.id.to_info);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void navigateToFragment(@IdRes int resId) {
        navController.navigate(resId);
    }
}