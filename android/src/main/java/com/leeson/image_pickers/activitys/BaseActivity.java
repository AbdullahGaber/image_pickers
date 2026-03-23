package com.leeson.image_pickers.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle; // Added
import android.provider.Settings;
import android.view.View; // Added
import android.view.WindowInsets; // Added
import android.view.WindowInsetsController; // Added

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by lisen on 2018/4/12.
 * Modified for Fullscreen/No Nav Bar in 2026
 */
@SuppressWarnings("all")
public abstract class BaseActivity extends AppCompatActivity {

    private int REQUEST_CODE_PERMISSION = 0x00001;

    // --- ADDED THIS SECTION ---
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Logic for Android 11 (API 30) through Android 16
            getWindow().setDecorFitsSystemWindows(false);
            WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null) {
                // Hides the navigation bar
                controller.hide(WindowInsets.Type.navigationBars());
                // Makes the bar reappear only temporarily on swipe
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else {
            // Legacy logic for older Android versions
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION 
                          | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
    // ---------------------------

    /**
     * 请求权限
     * ... (rest of your existing code stays exactly the same)
     */
    public void requestPermission(String[] permissions, int requestCode) {
        this.REQUEST_CODE_PERMISSION = requestCode;
        if (checkPermissions(permissions)) {
            permissionSuccess(REQUEST_CODE_PERMISSION);
        } else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
        }
    }

    // ... (Keep all other methods: checkPermissions, getDeniedPermissions, etc. as they were)
}
