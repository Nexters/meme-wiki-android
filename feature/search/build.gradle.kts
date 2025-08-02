plugins {
    id("mimu.android.feature")
    id("mimu.android.library.compose")
}

android {
    namespace = "com.meme.search"
}

dependencies {
    implementation(libs.coil)
    implementation(project(":core:designsystem"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.compose.paging)
}
