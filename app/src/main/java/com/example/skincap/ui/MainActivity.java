package com.example.skincap.ui;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
    private static final int[] MENU_ITEMS = new int[]{R.id.settings, R.id.notifs, R.id.info};

    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;
    private NavController navController;

    Button camera_button;
    Button gallery_button;
    ImageView view_image;

    public void camera_button(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
    }

    public void gallery_button(View view) {
            openGallery(2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        setupNavigationBar();

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        navController.addOnDestinationChangedListener(this);

        camera_button = findViewById(R.id.camera_button);
        view_image = findViewById(R.id.view_image);
        gallery_button = findViewById(R.id.gallery_button);


        //  For Camera Permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA },1);
        }

    }

    void openGallery(int requestCode) {
        Intent i = new Intent();
        i.setType("image/");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select an Image"), requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //  Camera
            if (requestCode == 1) {
                Bitmap captureImage = (Bitmap) data.getExtras().get("data");

                view_image.setImageBitmap(captureImage);
            }

            //  Gallery
            else if (requestCode == 2) {
                Uri selected_image = data.getData();

                if (null != selected_image){
                    view_image.setImageURI(selected_image);
                }
            }
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