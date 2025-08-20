package com.mimu_bird.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.mimu_bird.designsystem.R
import com.mimu_bird.designsystem.theme.Gray1
import com.mimu_bird.designsystem.theme.Gray10
import com.mimu_bird.detail.MemeDetailNavigationAction
import com.mimu_bird.detail.MemeDetailNavigator
import org.json.JSONObject

@Composable
fun MemeDetailScreen(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    memeId: Int,
    navController: NavController,
    navigator: MemeDetailNavigator
) {
    val url = remember(memeId) {
        "https://meme-wiki.net/meme/$memeId"
    }

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

                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            url: String?
                        ): Boolean {
                            Log.d("MemeDetailScreen", "카카오링크 처리: $url")

                            try {
                            } catch (e: Exception) {
                                Log.e("MemeDetailScreen", "카카오링크 처리 실패", e)
                                return false
                            }
                            return false
                        }
                    }
                    webChromeClient = WebChromeClient()

                    settings.let { // 세부 세팅 등록
                        it.javaScriptEnabled = true
                        it.domStorageEnabled = true
                        it.useWideViewPort = true
                        it.loadWithOverviewMode = true
                    }

                    addJavascriptInterface(
                        WebJavaScriptBridge(
                            onHandleScriptCode = {
                                runCatching {
                                    Log.d("MemeDetailScreen", "")
                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, url)
                                        type = "text/plain"
                                    }

                                    val shareIntent = Intent.createChooser(sendIntent, null)
                                    context.startActivity(shareIntent)
                                }
                            },
                            onHandleKakao = { title, image ->
                                val feedTemplate = FeedTemplate(
                                    content = Content(
                                        title = title,
                                        imageUrl = image,
                                        link = Link(
                                            webUrl = "https://meme-wiki.net/meme/${memeId}",
                                            mobileWebUrl = "https://meme-wiki.net/meme/${memeId}"
                                        )
                                    )
                                )
                                ShareClient.instance.shareDefault(
                                    context = context,
                                    feedTemplate
                                ) { result, error ->
                                    if (error != null) {
                                        // TODO Error Handling
                                    } else if (result != null){
                                        context.startActivity(result.intent)
                                    }
                                }
                            },
                            webView = this,
                            navigator = navigator,
                            id = memeId
                        ),
                        "wiki"
                    )
                }
            },
            update = {
                it.loadUrl(url)
                // JavaScript 실행은 CustomWebViewClient.onPageFinished에서 처리
            }
        )
    }
}

class WebJavaScriptBridge(
    private val onHandleScriptCode: () -> Unit,
    private val onHandleKakao: (String, String) -> Unit,
    private val webView: WebView,
    private val navigator: MemeDetailNavigator,
    private val id: Int
) {
    private val mainHandler = Handler(Looper.getMainLooper())

    @JavascriptInterface
    fun postMessage(code: String) {
        println("yeoonju : $code")

        try {
            val jsonObject = JSONObject(code)
            val type = jsonObject.getString("type")
            Log.d("MemeDetailScreen", "jsonObject:${jsonObject} type:${type}")

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
            when (type) {
                "CUSTOM_MEME" -> {
                    // CUSTOM_MEME 타입 이벤트 처리
                    Log.d("WebJavaScriptBridge", "CUSTOM_MEME 이벤트 수신")

                    try {
                        val data = jsonObject.getJSONObject("data")
                        val imgUrl = data.getString("image")
                        val title = data.getString("title")

                        Log.d("WebJavaScriptBridge", "CUSTOM_MEME - imgUrl: $imgUrl, title: $title")

                        // 메인 스레드에서 네비게이션 실행
                        mainHandler.post {
                            navigator.navigate(MemeDetailNavigationAction.NavigateToMyMeme(id = "${id}"))
                        }
                    } catch (e: Exception) {
                        Log.e(
                            "WebJavaScriptBridge",
                            "Failed to parse CUSTOM_MEME data: ${e.message}"
                        )
                    }
                }

                "SHARE_MEME" -> {
                    // SHARE_MEME 타입 이벤트 처리
                    Log.d("WebJavaScriptBridge", "SHARE_MEME 이벤트 수신")
                    onHandleScriptCode() // 기존 공유 로직 실행
                }

                "SHARE_KAKAO" -> {
                    val data = JSONObject(jsonObject.getString("data"))
                    onHandleKakao(
                        data.getString("title"),
                        data.getString("image")
                    )
                }

                else -> {
                    Log.d("WebJavaScriptBridge", "알 수 없는 이벤트 타입: $type")
                }
            }
        } catch (e: Exception) {
            Log.e("WebJavaScriptBridge", "Failed to parse JSON message: ${e.message}")
        }
    }
}