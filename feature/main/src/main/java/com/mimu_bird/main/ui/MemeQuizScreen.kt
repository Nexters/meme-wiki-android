package com.mimu_bird.main.ui

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.mimu_bird.designsystem.R
import com.mimu_bird.designsystem.theme.Gray1
import com.mimu_bird.designsystem.theme.Gray10
import com.mimu_bird.main.navigation.MainNavigationAction
import com.mimu_bird.main.navigation.MainNavigator
import org.json.JSONObject

@Composable
fun MemeQuizScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    navigator: MainNavigator
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = Gray10)
            .windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            // 뒤로가기 버튼
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left_24),
                    contentDescription = "뒤로가기",
                    tint = Gray1,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
            }
        },
        containerColor = Gray10
    ) { innerPadding ->
        AndroidView(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Gray10),
            factory = {
                WebView(it).apply {
                    this.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    webChromeClient = WebChromeClient()
                    settings.let {
                        it.javaScriptEnabled = true
                        it.domStorageEnabled = true
                        it.useWideViewPort = true
                        it.loadWithOverviewMode = true
                    }

                    addJavascriptInterface(
                        MemeQuizWebJavaScriptBridge(
                            onHandleScriptCode = {
                                runCatching {
                                    Log.d("MemeQuizScreen", "")
                                }
                            },
                            webView = this,
                            navigator = navigator,
                        ),
                        "wiki"
                    )
                }
            },
            update = {
                it.loadUrl("https://meme-wiki.net/")
            }
        )
    }
}

class MemeQuizWebJavaScriptBridge(
    private val onHandleScriptCode: () -> Unit,
    private val webView: WebView,
    private val navigator: MainNavigator,
) {
    private val mainHandler = Handler(Looper.getMainLooper())

    @JavascriptInterface
    fun postMessage(code: String) {
        println("yeoonju : $code")

        try {
            val jsonObject = JSONObject(code)
            val type = jsonObject.getString("type")
            Log.d("MemeQuizeScreen", "jsonObject:${jsonObject} type:${type}")

            if (type == "WEB_ENTERED") {
                // 메인 스레드에서 JavaScript 실행
                mainHandler.post {
                    webView.evaluateJavascript(
                        "window.onNativeEntered({\"type\":\"APP_ENTERED\"});"
                    ) { result ->
                        Log.d("MemeDetailScreen", "APP_ENTERED script result: $result")
                    }
                }
            }

            if (type == "SHOW_MORE_MEMES") {
                Log.d("MemeQuizScreen", "메인으로 네비게이팅")
                // 메인 스레드에서 네비게이션 실행
                mainHandler.post {
                    navigator.navigate(MainNavigationAction.NavigateToMain)
                }
            }

        } catch (e: Exception) {
            Log.e("WebJavaScriptBridge", "Failed to parse JSON message: ${e.message}")
        }
    }
}
