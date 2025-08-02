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
}
