package com.mimu_bird.meme

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MemeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "2809949a56a1ee4334ca8abf60e4a392")
    }
}