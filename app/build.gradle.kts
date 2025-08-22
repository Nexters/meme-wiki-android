plugins {
    id("mimu.android.application")
    id("mimu.android.application.compose")
}

android {
    namespace = "com.mimu_bird.meme"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":feature:search"))
    implementation(project(":feature:category"))
    implementation(project(":feature:main"))
    implementation(project(":feature:mymeme"))
    implementation(project(":feature:detail"))
    implementation(project(":core:ui"))
    implementation(project(":core:common"))

    implementation(libs.androidx.compose.paging)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.kakaoAll)

    // 스플래시 화면 라이브러리
    implementation(libs.androidx.splash)
}