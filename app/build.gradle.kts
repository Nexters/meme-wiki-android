plugins {
    id("mimu.android.application")
    id("mimu.android.application.compose")
}

android {
    namespace = "com.mimu_bird.meme"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(project(":feature:search"))
}