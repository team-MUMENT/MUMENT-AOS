package com.mument_android.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.login.databinding.ActivityLogInBinding

class LogInActivity : BaseActivity<ActivityLogInBinding>(R.layout.activity_log_in) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
    }
}