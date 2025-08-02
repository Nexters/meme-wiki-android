plugins {
    id("mimu.android.library")
    id("mimu.android.hilt")
}

android {
    namespace = "com.mimu_bird.domain"
}

dependencies {
    implementation(libs.androidx.paging)
}