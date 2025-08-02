plugins {
    id("mimu.android.feature")
    id("mimu.android.library.compose")
}

android {
    namespace = "com.mimu_bird.main"
}

dependencies {
    implementation(libs.coil)
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
}
