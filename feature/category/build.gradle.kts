import ext.implementation

plugins {
    id("mimu.android.feature")
    id("mimu.android.library.compose")
}

android {
    namespace = "com.seomseom.category"
}

dependencies {
    implementation(libs.coil)
    implementation(project(":core:common"))
    implementation(libs.androidx.compose.navigation)
    implementation(libs.hilt.navigation.compose)
}