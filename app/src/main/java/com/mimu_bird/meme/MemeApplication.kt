package com.mimu_bird.meme

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import java.io.File
import java.util.Properties

@HiltAndroidApp
class MemeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // local.properties에서 카카오 앱 키 읽기
        val localProperties = Properties()
        val localPropertiesFile = File(filesDir.parentFile?.parentFile, "local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())
        }
        val kakaoAppKey = localProperties.getProperty("kakao.app.key") ?: ""
        
        KakaoSdk.init(this, kakaoAppKey)
    }
}