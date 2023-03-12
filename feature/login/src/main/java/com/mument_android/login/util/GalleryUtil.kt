package com.mument_android.login.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

class GalleryUtil(
    private val context: Context,
    private val requestPermissionLauncher: ActivityResultLauncher<String>,
) {
    fun aboutPermission(): Boolean {
        val manifestTarget =
            if (Build.VERSION.SDK_INT >= 33) Manifest.permission.READ_MEDIA_IMAGES
            else Manifest.permission.READ_EXTERNAL_STORAGE

        if (ContextCompat.checkSelfPermission(
                context,
                manifestTarget
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        } else if (ContextCompat.checkSelfPermission(
                context,
                manifestTarget
            ) == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissionLauncher.launch(manifestTarget)
        }
        return false
    }
}