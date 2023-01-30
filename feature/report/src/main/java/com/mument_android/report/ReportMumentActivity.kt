package com.mument_android.report

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mument_android.report.databinding.ActivityReportMumentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReportMumentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportMumentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportMumentBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}