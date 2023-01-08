package com.mument_android.login.util

import android.content.Context
import android.widget.Toast

private var toast: Toast? = null
fun Context.shortToast(text: String) {
    if (toast == null)
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
    else
        toast!!.setText(text)
    toast?.show()
}