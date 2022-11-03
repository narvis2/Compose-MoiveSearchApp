package com.example.moviesearchapp.view.screen.detail

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Message
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.compose.BackHandler
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviesearchapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailWebViewScreen(
    viewModel: DetailWebviewViewModel = hiltViewModel(),
    navController: NavController,
    url: String
) {
    val isLoading = viewModel.isLoading.collectAsState()
    val backEnable = remember {
        mutableStateOf(false)
    }

    var webView: WebView? = null

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.str_movie_detail))
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
            },
            backgroundColor = Color.White
        )
    }) {
        AndroidView(factory = {
            WebView(it).apply {
                initWebViewSetting(this)

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                webViewClient = object : WebViewClient() {
                    @Deprecated("Deprecated in Java")
                    override fun shouldOverrideUrlLoading(
                        view: WebView,
                        url: String,
                    ): Boolean {
                        return urlLoading(view, url, view.hitTestResult.type)
                    }

                    @TargetApi(Build.VERSION_CODES.N)
                    override fun shouldOverrideUrlLoading(
                        view: WebView,
                        request: WebResourceRequest,
                    ): Boolean {
                        val loadUrl = request.url.toString()
                        // 사용자가 웹뷰에서 클릭한 정보 얻어오기
                        val type = view.hitTestResult.type

                        return urlLoading(view, loadUrl, type)
                    }

                    private fun urlLoading(
                        view: WebView,
                        url: String,
                        type: Int
                    ): Boolean {
                        return if (!isLoading.value) {
                            // 클릭한 정보가 불분명한 타입이 아닐 경우에만 loadUrl 실행 후 true 반환
                            if (type > WebView.HitTestResult.UNKNOWN_TYPE) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    view.loadUrl(url)
                                }

                                true
                            } else {
                                false
                            }
                        } else {
                            false
                        }
                    }

                    @Deprecated(
                        "Deprecated in Java",
                        ReplaceWith(
                        "super.onReceivedError(view, errorCode, description, failingUrl)",
                        "android.webkit.WebViewClient"
                        )
                    )
                    @Suppress("DEPRECATION")
                    override fun onReceivedError(
                        view: WebView?,
                        errorCode: Int,
                        description: String,
                        failingUrl: String?,
                    ) {
                        super.onReceivedError(view, errorCode, description, failingUrl)
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError,
                    ) {
                        super.onReceivedError(view, request, error)
                    }

                    override fun onPageStarted(
                        view: WebView,
                        url: String,
                        favicon: Bitmap?,
                    ) {
                        super.onPageStarted(view, url, favicon)
                        backEnable.value = view.canGoBack()
                        viewModel.setIsLoading(true)
                    }

                    override fun onPageFinished(view: WebView?, url: String) {
                        super.onPageFinished(view, url)
                        viewModel.setIsLoading(false)
                    }
                }

                webChromeClient = object : WebChromeClient() {
                    override fun onCreateWindow(
                        view: WebView?,
                        isDialog: Boolean,
                        isUserGesture: Boolean,
                        resultMsg: Message,
                    ): Boolean {
                        val newWebView = WebView(it)
                        initWebViewSetting(newWebView)

                        return true
                    }

                    override fun onShowFileChooser(
                        webView: WebView,
                        filePathCallback: ValueCallback<Array<Uri>>,
                        fileChooserParams: FileChooserParams,
                    ): Boolean {
                        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
                    }
                }

                CoroutineScope(Dispatchers.Main).launch {
                    loadUrl(url)
                }

                webView = this
            }
        }, update = {
            webView = it
        })
    }

    BackHandler(enabled = backEnable.value) {
        webView?.goBack()
    }
}

@SuppressLint("SetJavaScriptEnabled")
fun initWebViewSetting(webView: WebView) {
    webView.settings.apply {
        javaScriptEnabled = true
        javaScriptCanOpenWindowsAutomatically = true
        useWideViewPort = true
        setSupportMultipleWindows(true)
        domStorageEnabled = true
        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        databaseEnabled = true
    }
}