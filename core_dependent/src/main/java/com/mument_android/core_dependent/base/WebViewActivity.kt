package com.mument_android.core_dependent.base

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mument_android.core_dependent.R
import com.mument_android.core_dependent.databinding.ActivityWebViewBinding

class WebViewActivity : BaseActivity<ActivityWebViewBinding>(R.layout.activity_web_view) {

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadUrlFromIntent()
        initWebView()
        initToolbar()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // 웹뷰 back 가능하도록
        return if ((keyCode == KeyEvent.KEYCODE_BACK) && binding.wvContent.canGoBack()) {
            binding.wvContent.goBack()
            true
        } else {
            return super.onKeyDown(keyCode, event)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 툴바 홈키로 액티비티 종료
        return if(item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            return super.onOptionsItemSelected(item)
        }

        /*
        // 툴바 홈키로 액티비티 종료
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

         */
    }

    private fun loadUrlFromIntent() {
        this.url = intent.getStringExtra("url")
    }

    private fun initWebView() {

        // 커스텀 webChromeClient 사용 -> html의 title 로드될 때 웹뷰 상단 툴바의 타이틀에 적용
        val customWebChromeClient = BaseWebChromeClient()
        customWebChromeClient.titleData.observe(this) {
            supportActionBar?.title = customWebChromeClient.titleData.value
        }


        with (binding.wvContent) {

            // 웹뷰 설정
            with (settings) {

                loadWithOverviewMode = true     // WebView 화면크기에 맞추도록 설정
                useWideViewPort = true  // wide viewport 설정

                setSupportZoom(true)  // 줌 설정 여부
                builtInZoomControls = false  // 줌 확대/축소 버튼 여부

                javaScriptEnabled = true // 자바스크립트 사용 여부
                javaScriptCanOpenWindowsAutomatically = true  // 자바스크립트가 window.open() 사용할 수 있도록 설정

                setSupportMultipleWindows(true)     // 멀티 윈도우 사용 여부

                domStorageEnabled = true        // 로컬 스토리지 사용여부
            }

            webViewClient = BaseWebViewClient()
            webChromeClient = customWebChromeClient

        }

        if (url != null)
            binding.wvContent.loadUrl(url!!)
    }


    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)

        // 뒤로가기 (홈) 버튼 활성화
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class BaseWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (view != null && url != null)
                view.loadUrl(url)
            return true
        }
    }

    class BaseWebChromeClient : WebChromeClient() {
        private val _titleData = MutableLiveData<String>()
        val titleData: LiveData<String>
            get() = _titleData

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            if (title != null && title.isNotEmpty())
                _titleData.value = title!!
        }
    }
}