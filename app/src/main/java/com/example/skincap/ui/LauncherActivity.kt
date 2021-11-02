package com.example.skincap.ui

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.skincap.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private val ALL_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
)

class LauncherActivity : AppCompatActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
    ) { launchScreen() }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isPermissionGranted()) {
            showPermissionDialog()
        } else {
            launchScreen()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isPermissionGranted(): Boolean {
        var isGranted = false
        for (permission in ALL_PERMISSIONS) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false
                break
            } else {
                isGranted = true
            }
        }
        return isGranted
    }

    private fun showPermissionDialog() {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.request_permission))
                .setMessage(getString(R.string.permission_message))
                .setPositiveButton(R.string.allow_permission) { _, _ ->
                    requestPermissionLauncher.launch(ALL_PERMISSIONS)
                }
                .setCancelable(false)
                .show()
    }

    private fun launchScreen() {
        val sharedPreferences = getSharedPreferences("onBoarding Screen", MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean("firstTime", true)

        if (isFirstTime) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("firstTime", false)
            editor.apply()
            startActivity(Intent(this, OnBoarding::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}