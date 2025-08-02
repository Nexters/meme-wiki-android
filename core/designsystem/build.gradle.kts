plugins {
    id("mimu.android.library")
    id("mimu.android.library.compose")
}

android {
    namespace = "com.mimu_bird.designsystem"

    // 리소스 공유를 위한 설정
    buildFeatures {
        buildConfig = true
    }
}
