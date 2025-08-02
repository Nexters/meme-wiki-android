plugins {
    id("mimu.android.library")
    id("mimu.android.hilt")
}

android {
    namespace = "com.mimu_bird.data"
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:domain"))

    implementation(libs.bundles.retrofit)
    implementation(libs.androidx.paging)
}