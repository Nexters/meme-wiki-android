plugins {
    id("mimu.android.library")
    id("mimu.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.mimu.network"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.retrofit)
}