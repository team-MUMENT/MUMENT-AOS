package com.mument_android.login.util

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import java.io.ByteArrayOutputStream

private var toast: Toast? = null
fun Context.shortToast(text: String) {
    if (toast == null)
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
    else
        toast!!.setText(text)
    toast?.show()
}

fun Bitmap.toByteArray(format: Bitmap.CompressFormat, quality: Int): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(format, quality, byteArrayOutputStream)
    return byteArrayOutputStream.toByteArray()
}