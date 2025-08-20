plugins {
    id("mimu.android.feature")
    id("mimu.android.library.compose")
}

android {
    namespace = "com.mimu_bird.detail"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))
    implementation(libs.androidx.compose.navigation)
    implementation(libs.kakaoAll)
}