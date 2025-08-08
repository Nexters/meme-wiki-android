plugins {
    id("mimu.android.library")
    id("mimu.android.library.compose")
}

android {
    namespace = "com.mimu_bird.ui"
    
    // R 클래스 생성 설정
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.coil)
    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))
    implementation(libs.androidx.compose.navigation)
    implementation(libs.hilt.navigation.compose)
}
